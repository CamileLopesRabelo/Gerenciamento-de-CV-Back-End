package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.CurriculoDTO;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.Curriculo;
import com.dbc.curriculocv.exceptions.FileStorageException;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.dbc.curriculocv.repository.CurriculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CurriculoService {
    private final CurriculoRepository curriculoRepository;
    private final CandidatoRepository candidatoRepository;
    private final ObjectMapper objectMapper;

    public CurriculoDTO uploadCurriculo(MultipartFile file, Integer idCandidato) throws RegraDeNegocioException {
        Candidato byId = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        String fileName = StringUtils.cleanPath(new BCryptPasswordEncoder().encode(idCandidato + "_" + byId.getNome()) + ".pdf");

        if(!file.getContentType().equalsIgnoreCase("application/pdf")){
            throw new RegraDeNegocioException("Arquivo enviado deve ser no formato pdf");
        }

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("O arquivo tem um nome inválido " + fileName);
            }

            Curriculo curriculo = curriculoRepository.getCurriculoByIdCandidato(idCandidato);

            if (curriculo == null) {
                curriculo = new Curriculo();
            }

            curriculo.setSize(file.getSize());
            curriculo.setFileName(fileName);
            curriculo.setFileType(file.getContentType());
            curriculo.setData(file.getBytes());
            curriculo.setCandidato(byId);

            Curriculo save = curriculoRepository.save(curriculo);
            CurriculoDTO curriculoDTO = objectMapper.convertValue(save, CurriculoDTO.class);
            curriculoDTO.setFileDownloadUri(
                    ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/download-curriculo/")
                            .path(save.getIdCurriculo().toString())
                            .toUriString()
            );
            return curriculoDTO;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Transactional
    public ResponseEntity<Resource> downloadCurriculo(Integer idCandidato) {
        Curriculo curriculo = curriculoRepository.getCurriculoByIdCandidato(idCandidato);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(curriculo.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + curriculo.getFileName() + "\"")
                .body(new ByteArrayResource(curriculo.getData()));
    }
}

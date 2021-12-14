package com.dbc.curriculocv.service;

import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.Curriculo;
import com.dbc.curriculocv.exceptions.FileStorageException;
import com.dbc.curriculocv.exceptions.MyFileNotFoundException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.dbc.curriculocv.repository.CurriculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurriculoService {
    private final CurriculoRepository curriculoRepository;
    private final CandidatoRepository candidatoRepository;

    public Curriculo storeFile(MultipartFile file, Integer idCandidato) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Optional<Candidato> byId = candidatoRepository.findById(idCandidato);

            Curriculo curriculo = curriculoRepository.getCurriculoByIdCandidato(idCandidato);

            if (curriculo == null) {
                curriculo = new Curriculo();
            }

            curriculo.setSize(file.getSize());
            curriculo.setFileName(fileName);
            curriculo.setFileType(file.getContentType());
            curriculo.setData(file.getBytes());
            curriculo.setCandidato(byId.get());
            return curriculoRepository.save(curriculo);

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Curriculo getFile(Integer fileId) {
        return curriculoRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

}

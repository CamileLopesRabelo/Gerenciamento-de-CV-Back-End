package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.CurriculoDTO;
import com.dbc.curriculocv.entity.Curriculo;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.CurriculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
public class CurriculoController {
    private final ObjectMapper objectMapper;
    private final CurriculoService curriculoService;

    private static final Logger logger = LoggerFactory.getLogger(CurriculoController.class);

    @PostMapping("/uploadFile")
    public CurriculoDTO uploadFile(@RequestPart("file") MultipartFile file, Integer idCandidato) throws RegraDeNegocioException {
        Curriculo curriculo = curriculoService.storeFile(file,idCandidato);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(curriculo.getIdCurriculo().toString())
                .toUriString();

        return objectMapper.convertValue(curriculo,CurriculoDTO.class);
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId) {
        // Carregar arquivo do banco de dados
        Curriculo curriculo = curriculoService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(curriculo.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + curriculo.getFileName() + "\"")
                .body(new ByteArrayResource(curriculo.getData()));
    }

}

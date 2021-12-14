package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.CurriculoDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.CurriculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/curriculo")
@Validated
@RequiredArgsConstructor
public class CurriculoController {
    private final CurriculoService curriculoService;

    @PostMapping("/upload-curriculo")
    public CurriculoDTO uploadCurriculo(@RequestPart("file") MultipartFile file, @RequestParam("idCandidato") Integer idCandidato) throws RegraDeNegocioException {
        return curriculoService.uploadCurriculo(file, idCandidato);
    }

    @GetMapping("/download-curriculo/{idCandidato}")
    public ResponseEntity<Resource> downloadCurriculo(@PathVariable Integer idCandidato) {
        return curriculoService.doanloadCurriculo(idCandidato);
    }
}

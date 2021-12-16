package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.CurriculoDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.CurriculoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Curriculo cadastrado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou candidato não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Faz upload do curriculo")
    @PostMapping("/upload-curriculo/{idCandidato}")
    public CurriculoDTO uploadCurriculo(@RequestPart("file") MultipartFile file, @PathVariable("idCandidato") Integer idCandidato) throws RegraDeNegocioException {
        return curriculoService.uploadCurriculo(file, idCandidato);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Curriculo recuperado"),
            @ApiResponse(code = 400, message = "candidato não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Recupera o curriculo")
    @GetMapping("/download-curriculo/{idCandidato}")
    public ResponseEntity<Resource> downloadCurriculo(@PathVariable Integer idCandidato) {
        return curriculoService.doanloadCurriculo(idCandidato);
    }
}

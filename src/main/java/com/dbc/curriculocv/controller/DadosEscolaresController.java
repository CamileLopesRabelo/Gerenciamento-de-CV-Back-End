package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.CandidatoCreateDTO;
import com.dbc.curriculocv.dto.CandidatoDTO;
import com.dbc.curriculocv.dto.DadosEscolaresCreateDTO;
import com.dbc.curriculocv.dto.DadosEscolaresDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.DadosEscolaresService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dados-escolares")
@Validated
@RequiredArgsConstructor
public class DadosEscolaresController {
    private final DadosEscolaresService dadosEscolaresService;

    @GetMapping
    public List<DadosEscolaresDTO> list() {
        return dadosEscolaresService.list();
    }

    @PostMapping
    public DadosEscolaresDTO create(@RequestParam Integer idCandidato, @RequestBody @Valid DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        return dadosEscolaresService.create(idCandidato, dadosEscolaresCreateDTO);
    }

    @PutMapping
    public DadosEscolaresDTO update(@RequestParam Integer idDadosEscolares, @RequestBody @Valid DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        return dadosEscolaresService.update(idDadosEscolares, dadosEscolaresCreateDTO);
    }

    @DeleteMapping
    public void delete(@RequestParam Integer idDadosEscolares) throws RegraDeNegocioException {
        dadosEscolaresService.delete(idDadosEscolares);
    }
}

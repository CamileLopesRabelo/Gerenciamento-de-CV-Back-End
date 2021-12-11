package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.CandidatoCreateDTO;
import com.dbc.curriculocv.dto.CandidatoDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.CandidatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/candidato")
@Validated
@RequiredArgsConstructor
public class CandidatoController {
    private final CandidatoService candidatoService;

    @GetMapping
    public List<CandidatoDTO> list(@RequestParam(value = "idCandidato", required = false) Integer idCandidato) {
        return candidatoService.list(idCandidato);
    }

    @PostMapping
    public CandidatoDTO create(@RequestBody @Valid CandidatoCreateDTO candidatoCreateDTO) {
        return candidatoService.create(candidatoCreateDTO);
    }

    @PutMapping
    public CandidatoDTO update(@RequestParam Integer idCandidato, @RequestBody @Valid CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException {
        return candidatoService.update(idCandidato, candidatoCreateDTO);
    }

    @DeleteMapping
    public void delete(@RequestParam Integer idCandidato) throws RegraDeNegocioException {
        candidatoService.delete(idCandidato);
    }
}

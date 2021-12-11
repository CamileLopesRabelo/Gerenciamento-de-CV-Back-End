package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.VagaCandidatoDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.VagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/vaga")
@Validated
@RequiredArgsConstructor
public class VagaController {
    private final VagaService vagaService;

    @PostMapping
    public void updateTable() {
        vagaService.updateTable();
    }

    @PostMapping("/vincular-candidato")
    public VagaCandidatoDTO vincularCandidatoAVaga(@RequestParam @Valid Integer idCandidato, @RequestParam @Valid Integer idVaga) throws RegraDeNegocioException {
        return vagaService.vincularCandidatoAVaga(idCandidato, idVaga);
    }
}

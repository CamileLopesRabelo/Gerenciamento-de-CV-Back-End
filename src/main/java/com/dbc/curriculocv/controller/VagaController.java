package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.VagaCandidatoDTO;
import com.dbc.curriculocv.dto.VagaDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.VagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vaga")
@Validated
@RequiredArgsConstructor
public class VagaController {
    private final VagaService vagaService;

    @PostMapping("/vincular-candidato")
    public VagaCandidatoDTO vincularCandidatoAVaga(@RequestParam @Valid Integer idCandidato, @RequestParam @Valid Integer idVaga) throws RegraDeNegocioException {
        return vagaService.vincularCandidatoAVaga(idCandidato, idVaga);
    }

    @GetMapping
    public List<VagaDTO> list(@RequestParam(value = "idVaga", required = false) Integer idVaga) throws RegraDeNegocioException {
        return vagaService.list(idVaga);
    }

    @GetMapping("/vagas-candidatos")
    public List<VagaCandidatoDTO> listVagaCandidato(@RequestParam(value = "idVaga", required = false) Integer idVaga) throws RegraDeNegocioException {
        return vagaService.listVagaCandidato(idVaga);
    }
}

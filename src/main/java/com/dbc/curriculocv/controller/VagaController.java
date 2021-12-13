package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.VagaCandidatoDTO;
import com.dbc.curriculocv.dto.VagaDTO;
import com.dbc.curriculocv.dto.VagasDTO;
import com.dbc.curriculocv.dto.VagasFiltradasDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.VagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vaga")
@Validated
@RequiredArgsConstructor
@Slf4j
public class VagaController {
    private final VagaService vagaService;

    @GetMapping
    public List<VagaDTO> list() {
       log.info("listando vagas do postgrees");
       List<VagaDTO> vagasListadas = vagaService.list();
       log.info("vagas listadas com sucesso");
        return vagasListadas;
    }

//    @PostMapping
//    public void updateTable() {
//        vagaService.updateTable();
//    }

    @PostMapping("/vincular-candidato")
    public VagaCandidatoDTO vincularCandidatoAVaga(@RequestParam @Valid Integer idCandidato, @RequestParam @Valid Integer idVaga) throws RegraDeNegocioException {
        return vagaService.vincularCandidatoAVaga(idCandidato, idVaga);
    }

}

package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.VagaCandidatoDTO;
import com.dbc.curriculocv.dto.VagaCandidatoPaginadaDTO;
import com.dbc.curriculocv.dto.VagaDTO;
import com.dbc.curriculocv.dto.VagaPaginadaDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.VagaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Candidato Vinculado a vaga"),
            @ApiResponse(code = 400, message = "Dados inconsistentes, candidato ou vaga não encontrados"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Vincula um candidato a vaga")
    @PostMapping("/vincular-candidato")
    public VagaCandidatoDTO vincularCandidatoAVaga(@RequestParam @Valid Integer idCandidato, @RequestParam @Valid Integer idVaga) throws RegraDeNegocioException {
        return vagaService.vincularCandidatoAVaga(idCandidato, idVaga);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista vagas sem candidatos"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista as vagas SEM os candidatos ou apenas um, específicado pelo identificador")
    @GetMapping
    public List<VagaDTO> list(@RequestParam(value = "idVaga", required = false) Integer idVaga) throws RegraDeNegocioException {
        return vagaService.list(idVaga);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista vagas com candidatos"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista as vagas COM os candidatos ou apenas um, específicado pelo identificador")
    @GetMapping("/vagas-candidatos")
    public List<VagaCandidatoDTO> listVagaCandidato(@RequestParam(value = "idVaga", required = false) Integer idVaga) throws RegraDeNegocioException {
        return vagaService.listVagaCandidato(idVaga);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Candidato desnvinculado da vaga"),
            @ApiResponse(code = 400, message = "Dados inconsistentes, candidato ou vaga não encontrados"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Desvincula um candidato da vaga")
    @DeleteMapping("/desvincular-candidato")
    public VagaCandidatoDTO desvincularCandidatoAVaga(@RequestParam @Valid Integer idCandidato, @RequestParam @Valid Integer idVaga) throws RegraDeNegocioException {
        return vagaService.desvincularCandidatoVaga(idCandidato, idVaga);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista vagas sem candidato paginada"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista as vagas SEM os candidatos paginada")
    @GetMapping("/vaga-paginada")
    public VagaPaginadaDTO listaVagasPaginada(@RequestParam(value = "pagina") Integer pagina, @RequestParam(value = "quantidade") Integer quantidade) {
        return vagaService.listaVagasPaginada(pagina, quantidade);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista vagas com candidato paginada"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista as vagas COM os candidatos paginada")
    @GetMapping("/vaga-candidato-paginada")
    public VagaCandidatoPaginadaDTO listaVagasCandidatosPaginada(@RequestParam(value = "pagina") Integer pagina, @RequestParam(value = "quantidade") Integer quantidade) {
        return vagaService.listaVagasCandidatoPaginada(pagina, quantidade);
    }
}

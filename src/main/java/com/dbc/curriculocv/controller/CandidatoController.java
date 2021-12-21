package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.*;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.CandidatoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista carregada"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista todos os candidatos cadastrados ou apenas um, específicado pelo identificador")
    @GetMapping
    public List<CandidatoDTO> list(@RequestParam(value = "idCandidato", required = false) Integer idCandidato) throws RegraDeNegocioException {
        return candidatoService.list(idCandidato);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista carregada"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista todos os candidatos cadastrados com os dados completos ou apenas um, específicado pelo identificador")
    @GetMapping("/dados-completos")
    public List<CandidatoDadosExperienciasDTO> listCandidatosDadosExperiencias(@RequestParam(value = "idCandidato", required = false) Integer idCandidato) throws RegraDeNegocioException {
        return candidatoService.listCandidatosDadosExperiencias(idCandidato);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Candidato criado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Cria um candidato")
    @PostMapping
    public CandidatoDTO create(@RequestBody @Valid CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException {
        return candidatoService.create(candidatoCreateDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Candidato criado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou candidato não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Atualiza um candidato")
    @PutMapping
    public CandidatoDTO update(@RequestParam Integer idCandidato, @RequestBody @Valid CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException {
        return candidatoService.update(idCandidato, candidatoCreateDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Candidato deletado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou candidato não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Deleta um candidato")
    @DeleteMapping
    public void delete(@RequestParam Integer idCandidato) throws RegraDeNegocioException {
        candidatoService.delete(idCandidato);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de candidatos paginada"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista os candidatos paginada")
    @GetMapping("/candidato-paginada")
    public CandidatoPaginadaDTO listaCandidatosPaginada(@RequestParam(value = "pagina") Integer pagina,
                                                        @RequestParam(value = "quantidade") Integer quantidade) {
        return candidatoService.listaCandidatosPaginada(pagina, quantidade);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista candidatos completos paginada"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista os candidatos completos paginada")
    @GetMapping("/candidato-completos-paginada")
    public CandidatoDadosExperienciasPaginadaDTO listaCandidatosDadosExperienciasPaginada(@RequestParam(value = "pagina") Integer pagina,
                                                                                          @RequestParam(value = "quantidade") Integer quantidade) {
        return candidatoService.listaCandidatosDadosExperiencias(pagina, quantidade);
    }
}

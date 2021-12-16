package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.CandidatoCreateDTO;
import com.dbc.curriculocv.dto.CandidatoDTO;
import com.dbc.curriculocv.dto.DadosEscolaresCreateDTO;
import com.dbc.curriculocv.dto.DadosEscolaresDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.DadosEscolaresService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista dados escolares"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista os dados escolares")
    @GetMapping
    public List<DadosEscolaresDTO> list() {
        return dadosEscolaresService.list();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dado escolar criado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou candidato não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Cria um dado escolar")
    @PostMapping
    public DadosEscolaresDTO create(@RequestParam Integer idCandidato, @RequestBody @Valid DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        return dadosEscolaresService.create(idCandidato, dadosEscolaresCreateDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dado escolar atualizado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou dado escolar não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Atualiza um dado escolar")
    @PutMapping
    public DadosEscolaresDTO update(@RequestParam Integer idDadosEscolares, @RequestBody @Valid DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        return dadosEscolaresService.update(idDadosEscolares, dadosEscolaresCreateDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dado escolar deletado"),
            @ApiResponse(code = 400, message = "Dado escolar não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Deleta um dado escolar")
    @DeleteMapping
    public void delete(@RequestParam Integer idDadosEscolares) throws RegraDeNegocioException {
        dadosEscolaresService.delete(idDadosEscolares);
    }
}

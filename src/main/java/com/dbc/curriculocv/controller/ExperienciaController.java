package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.ExperienciasCreateDTO;
import com.dbc.curriculocv.dto.ExperienciasDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.ExperienciasService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/experiencias")
@Validated
@RequiredArgsConstructor
public class ExperienciaController {
    private final ExperienciasService experienciasService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista experiências"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Lista as experiências")
    @GetMapping
    public List<ExperienciasDTO> list() {
        return experienciasService.list();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Experiência criado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou candidato não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Cria uma experiência")
    @PostMapping
    public ExperienciasDTO create(@RequestParam Integer idCandidato, @RequestBody @Valid ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException {
        return experienciasService.create(idCandidato, experienciasCreateDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Experiência atualizada"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou experiência não encontrada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Atualiza uma experiência")
    @PutMapping
    public ExperienciasDTO update(@RequestParam Integer idExperiencia, @RequestBody @Valid ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException {
        return experienciasService.update(idExperiencia, experienciasCreateDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Experiência deletada"),
            @ApiResponse(code = 400, message = "Experiência não encontrada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Deleta uma experiência")
    @DeleteMapping
    public void delete(@RequestParam Integer idExperiencia) throws RegraDeNegocioException {
        experienciasService.delete(idExperiencia);
    }
}

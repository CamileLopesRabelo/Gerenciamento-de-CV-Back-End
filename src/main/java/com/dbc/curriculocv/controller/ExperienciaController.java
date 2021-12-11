package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.ExperienciasCreateDTO;
import com.dbc.curriculocv.dto.ExperienciasDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.ExperienciasService;
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

    @GetMapping
    public List<ExperienciasDTO> list() {
        return experienciasService.list();
    }

    @PostMapping
    public ExperienciasDTO create(@RequestParam Integer idCandidato, @RequestBody @Valid ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException {
        return experienciasService.create(idCandidato, experienciasCreateDTO);
    }

    @PutMapping
    public ExperienciasDTO update(@RequestParam Integer idExperiencia, @RequestBody @Valid ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException {
        return experienciasService.update(idExperiencia, experienciasCreateDTO);
    }

    @DeleteMapping
    public void delete(@RequestParam Integer idExperiencia) throws RegraDeNegocioException {
        experienciasService.delete(idExperiencia);
    }
}

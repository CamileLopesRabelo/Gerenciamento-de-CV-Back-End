package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.VagasFiltradasDTO;
import com.dbc.curriculocv.service.VagasCompleoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/vagas-compleo")
@Validated
@RequiredArgsConstructor
@Slf4j
public class VagasCompleoController {
    private final VagasCompleoService vagasCompleoService;

    @GetMapping
    public VagasFiltradasDTO list() {
        return vagasCompleoService.list();
    }
}

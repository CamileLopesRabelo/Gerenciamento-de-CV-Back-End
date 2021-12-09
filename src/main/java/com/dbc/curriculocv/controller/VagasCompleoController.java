package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.client.VagasCompleoClient;
import com.dbc.curriculocv.dto.VagasCompleoDTO;
import com.dbc.curriculocv.service.VagasCompleoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vagas-compleo")
@Validated
@RequiredArgsConstructor
@Slf4j
public class VagasCompleoController {

    private final VagasCompleoService vagasCompleoService;


    @GetMapping
    public List<Document> list() {
        return vagasCompleoService.list();
    }


}

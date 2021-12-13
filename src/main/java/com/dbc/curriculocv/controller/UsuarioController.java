package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.UsuarioCreateDTO;
import com.dbc.curriculocv.dto.UsuarioDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("/create-cadastrador")
    public UsuarioDTO create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return usuarioService.create(usuarioCreateDTO);
    }

    @GetMapping
    public UsuarioDTO retrieveUser(){
        return usuarioService.retrieveUser();
    }
}

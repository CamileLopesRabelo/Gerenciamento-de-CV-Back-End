package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.UsuarioCreateDTO;
import com.dbc.curriculocv.dto.UsuarioDTO;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cadastrador criado"),
            @ApiResponse(code = 400, message = "Dados inconsistentes"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Cria um usuário cadastrador")
    @PostMapping("/create-cadastrador")
    public UsuarioDTO create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return usuarioService.create(usuarioCreateDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Recupera cadastrador"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    @ApiOperation("Recupera o usuário")
    @GetMapping
    public UsuarioDTO retrieveUser(){
        return usuarioService.retrieveUser();
    }
}

package com.dbc.curriculocv.controller;

import com.dbc.curriculocv.dto.LoginDTO;
import com.dbc.curriculocv.entity.Usuario;
import com.dbc.curriculocv.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken user =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsuario(),
                        loginDTO.getSenha()
                );
        Authentication authenticate = authenticationManager.authenticate(user);
        return tokenService.generateToken((Usuario) authenticate.getPrincipal());
    }
}

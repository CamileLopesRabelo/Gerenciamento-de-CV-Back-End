package com.dbc.curriculocv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {

    @ApiModelProperty(value = "Nome do usuario")
    private String nome;

    @ApiModelProperty(value = "Email")
    private String email;

    @ApiModelProperty(value = "Senha")
    private String senha;
}

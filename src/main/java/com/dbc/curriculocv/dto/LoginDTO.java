package com.dbc.curriculocv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {
    @NotNull
    @NotEmpty
    @NotBlank
    @ApiModelProperty("Usuário")
    private String usuario;

    @NotNull
    @NotEmpty
    @NotBlank
    @ApiModelProperty("Senha do usuário")
    private String senha;
}

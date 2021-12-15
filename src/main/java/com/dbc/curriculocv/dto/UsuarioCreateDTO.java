package com.dbc.curriculocv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {
    @NotNull
    @NotEmpty
    @NotBlank
    @ApiModelProperty("Nome completo do usuario")
    private String nome;

    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    @ApiModelProperty("E-mail do usuário")
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    @ApiModelProperty("Senha do usuário")
    private String senha;
}

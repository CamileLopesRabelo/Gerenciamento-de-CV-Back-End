package com.dbc.curriculocv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoDTO {

    @ApiModelProperty("idCandidato")
    private Integer idCandidato;

    @ApiModelProperty("nome")
    private String nome;

    @ApiModelProperty("cpf")
    private String cpf;

    @ApiModelProperty("dataNascimento")
    private LocalDate dataNascimento;

    @ApiModelProperty("logradouro")
    private String logradouro;

    @ApiModelProperty("complemento")
    private String complemento;

    @ApiModelProperty("telefone")
    private String telefone;
    private String cargo;
    private String senioridade;
}

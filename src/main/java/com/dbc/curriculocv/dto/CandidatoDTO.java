package com.dbc.curriculocv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoDTO {
    @ApiModelProperty("Identificador do candidato")
    private Integer idCandidato;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty("Nome completo do candidato")
    private String nome;

    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 números")
    @Pattern(regexp = "^[0-9]+$", message = "O CPF deve conter apenas números")
    @ApiModelProperty("CPF do candidato, deve conter 11 números")
    private String cpf;

    @NotNull
    @Past
    @ApiModelProperty("Data de nascimento do candidato, deve ser uma data passada")
    private LocalDate dataNascimento;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty("Logradouro do endereço do candidato. (Ex. Rua Assis Brasil) ")
    private String logradouro;

    @NotNull
    @Positive
    @ApiModelProperty("Número do endereço do candidato")
    private Integer numero;

    @ApiModelProperty("Complemento do endereço do candidato, pode ser nulo")
    private String complemento;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty("Telefone de contato do candidato")
    private String telefone;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty("Cargo do candidato")
    private String cargo;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty("Senioridade do candidato")
    private String senioridade;
}

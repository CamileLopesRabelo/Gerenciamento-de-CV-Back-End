package com.dbc.curriculocv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoDTO {
    private Integer idCandidato;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String logradouro;
    private String complemento;
    private String telefone;
    private String cargo;
    private String senioridade;
}

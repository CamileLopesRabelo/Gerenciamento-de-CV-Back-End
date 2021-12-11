package com.dbc.curriculocv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DadosEscolaresDTO {
    private Integer idDadosEscolares;
    private String instituicao;
    private Date dataInicio;
    private Date dataFim;
    private String descricao;
}

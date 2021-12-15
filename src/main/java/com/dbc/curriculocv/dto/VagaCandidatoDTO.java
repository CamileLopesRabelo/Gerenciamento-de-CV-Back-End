package com.dbc.curriculocv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaCandidatoDTO {
    @ApiModelProperty("Dados da Vaga")
    private VagaDTO vaga;

    @ApiModelProperty("Lista de candidatos vinculados a vaga")
    private List<CandidatoDTO> candidatos;
}

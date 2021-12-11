package com.dbc.curriculocv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaCandidatoDTO {
    private VagaDTO vaga;
    private List<CandidatoDTO> candidatos;
}

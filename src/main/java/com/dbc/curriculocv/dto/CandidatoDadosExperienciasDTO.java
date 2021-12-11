package com.dbc.curriculocv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoDadosExperienciasDTO {
    private CandidatoDTO candidato;
    private List<DadosEscolaresDTO> dadosEscolares;
    private List<ExperienciasDTO> experiencias;
}

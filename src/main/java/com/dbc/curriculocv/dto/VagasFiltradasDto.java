package com.dbc.curriculocv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagasFiltradasDto {
    private List<VagasCompleoDTO> vagaGeralList;

    @ApiModelProperty(value = "Total de Vagas")
    private Integer totalDeVagas;
}

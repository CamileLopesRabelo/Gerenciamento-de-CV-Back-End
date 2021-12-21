package com.dbc.curriculocv.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VagasFiltradasDTO {
    @ApiModelProperty("Lista de vagas")
    private List<VagasCompleoDTO> vagaGeralList;

    @ApiModelProperty(value = "Total de Vagas")
    private Integer totalDeVagas;
}

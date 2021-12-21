package com.dbc.curriculocv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoPaginadaDTO {
    @ApiModelProperty("Lista de candidatos")
    private List<CandidatoDTO> candidatos;

    @ApiModelProperty("Total de candidatos")
    private Long candidatosTotal;

    @ApiModelProperty("Total de páginas")
    private Integer paginasTotal;

    @ApiModelProperty("Página atual")
    private Integer paginaAtual;

    @ApiModelProperty("Quantidade de candidatos mostrado na página atual")
    private Integer quantidadeCandidatosNaPaginaAtual;
}

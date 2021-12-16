package com.dbc.curriculocv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaPaginadaDTO {
    @ApiModelProperty("Lista de vagas")
    private List<VagaDTO> vagas;

    @ApiModelProperty("Total de vagas")
    private Long vagasTotal;

    @ApiModelProperty("Total de páginas")
    private Integer paginasTotal;

    @ApiModelProperty("Página atual")
    private Integer paginaAtual;

    @ApiModelProperty("Quantidade mostrada na página atual")
    private Integer quantidadeVagasNaPaginaAtual;
}

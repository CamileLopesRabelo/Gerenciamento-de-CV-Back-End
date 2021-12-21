package com.dbc.curriculocv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaDTO {
    @ApiModelProperty("Identificador da vaga")
    private Integer id;

    @ApiModelProperty("Titulo da vaga")
    @JsonProperty("Titulo")
    private String titulo;

    @ApiModelProperty("Status da vaga")
    @JsonProperty("Status")
    private String status;

    @ApiModelProperty("Responsavel da vaga")
    @JsonProperty("Responsavel")
    private String responsavel;

    @ApiModelProperty("PCD da vaga")
    @JsonProperty("PCD")
    private Boolean pcd;

    @ApiModelProperty("Estado da vaga")
    @JsonProperty("Estado")
    private String estado;

    @ApiModelProperty("Data de Abertura da vaga")
    @JsonProperty("DataAbertura")
    private Date dataAbertura;

    @ApiModelProperty("Cliente da vaga")
    @JsonProperty("Cliente")
    private String cliente;

    @ApiModelProperty("Cidade da vaga")
    @JsonProperty("Cidade")
    private String cidade;

    @ApiModelProperty("Analista da vaga")
    @JsonProperty("Analista")
    private String analista;
}

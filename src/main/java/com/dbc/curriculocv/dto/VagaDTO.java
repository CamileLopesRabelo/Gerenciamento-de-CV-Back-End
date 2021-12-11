package com.dbc.curriculocv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaDTO {
    private Integer id;
    @JsonProperty("Titulo")
    private String titulo;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Responsavel")
    private String responsavel;
    @JsonProperty("PCD")
    private Boolean pcd;
    @JsonProperty("Estado")
    private String estado;
    @JsonProperty("DataAbertura")
    private Date dataAbertura;
    @JsonProperty("Cliente")
    private String cliente;
    @JsonProperty("Cidade")
    private String cidade;
    @JsonProperty("Analista")
    private String analista;
}

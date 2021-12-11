package com.dbc.curriculocv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagasCompleoDTO {
    private Integer id;
    @JsonProperty("DataAbertura")
    private Date dataAbertura;
    @JsonProperty("Titulo")
    private String titulo;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Cliente")
    private String cliente;
    @JsonProperty("Responsavel")
    private String responsavel;
    @JsonProperty("Analista")
    private String analista;
    @JsonProperty("PCD")
    private Boolean pcd;
    @JsonProperty("Cidade")
    private String cidade;
    @JsonProperty("Estado")
    private String estado;
}

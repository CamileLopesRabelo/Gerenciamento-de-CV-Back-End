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
    private Date DataAbertura;
    @JsonProperty("Titulo")
    private String Titulo;
    @JsonProperty("Status")
    private String Status;
    @JsonProperty("Cliente")
    private String Cliente;
    @JsonProperty("Responsavel")
    private String Responsavel;
    @JsonProperty("Analista")
    private String Analista;
    @JsonProperty("PCD")
    private Boolean PCD;
    @JsonProperty("Cidade")
    private String Cidade;
    @JsonProperty("Estado")
    private String Estado;
//    private List<String> candidatos;


}

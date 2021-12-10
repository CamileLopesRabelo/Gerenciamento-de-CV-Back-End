package com.dbc.curriculocv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagasCompleoDTO {
    private Integer id;
    private LocalDate DataAbertura;
    private String Titulo;
    private String Status;
    private String Cliente;
    private String Responsável;
    private String Analista;
    private Boolean PCD;
    private String Cidade;
    private String Estado;
    private List<String> candidatos;

}

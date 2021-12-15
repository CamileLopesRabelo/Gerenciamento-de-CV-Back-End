package com.dbc.curriculocv.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.bson.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagasDTO {
    @ApiModelProperty("Lista de vagas, vindas da API Compleo")
    private List<VagasCompleoDTO> vagaGeralList;

    @ApiModelProperty("Total de vagas")
    private Integer total;

    @ApiModelProperty("Total de páginas")
    private Integer paginas;

    @ApiModelProperty("Página atual")
    private Integer pagina;

    @ApiModelProperty("Quantidade mostrada na página atual")
    private Integer Quantidade;
}

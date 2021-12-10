package com.dbc.curriculocv.dto;

import lombok.*;
import org.bson.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagasDto {
    private List<VagasCompleoDTO> vagaGeralList;
    private Integer total;
    private Integer paginas;
    private Integer pagina;
    private Integer Quantidade;
}

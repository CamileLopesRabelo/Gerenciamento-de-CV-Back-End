package com.dbc.curriculocv.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurriculoDTO {
    private Integer idCurriculo;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}

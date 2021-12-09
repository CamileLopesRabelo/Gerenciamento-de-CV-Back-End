package com.dbc.curriculocv.client;

import com.dbc.curriculocv.dto.VagasCompleoDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(value="dados-pessoais", url=" https://api.compleo.com.br/api/relatorios/listarRelatorioVagasGeral")
@Headers("Content-Type: application/json")

public interface VagasCompleoClient {

    @RequestLine("GET /dados-pessoais")
    List<VagasCompleoDTO> listar();



}

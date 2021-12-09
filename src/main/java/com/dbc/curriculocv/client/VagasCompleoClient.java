package com.dbc.curriculocv.client;

import feign.Headers;
import feign.RequestLine;
import org.bson.Document;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(value="vagas-compleo", url="https://api.compleo.com.br")
@Headers("Content-Type: application/json")


public interface VagasCompleoClient {

    @RequestLine("GET /api/relatorios/listarRelatorioCandidatosVaga")
    @Headers("auth-token: {hzL2rHXeERNR}")
    List<Document> listar();



}

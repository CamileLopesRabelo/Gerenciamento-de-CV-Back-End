package com.dbc.curriculocv.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.bson.Document;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value="vagas-compleo", url="https://api.compleo.com.br")
@Headers({ "Content-Type: application/json", "auth-token: {token}" })
public interface VagasCompleoClient {

    @RequestLine("GET /api/relatorios/listarRelatorioCandidatosVaga?Pagina={Pagina}&Quantidade={Quantidade}")
    Document listar(@Param("token") String token,@Param("Pagina") Integer pagina,
                    @Param("Quantidade") Integer quantidade);
    }

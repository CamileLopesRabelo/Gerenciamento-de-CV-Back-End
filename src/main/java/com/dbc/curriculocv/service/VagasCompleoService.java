package com.dbc.curriculocv.service;

import com.dbc.curriculocv.client.VagasCompleoClient;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class VagasCompleoService {

    private final VagasCompleoClient vagasCompleoClient;

    public Document list(Integer pagina, Integer quantidade) {
        Document ret = vagasCompleoClient.listar("hzL2rHXeERNR",pagina,quantidade);
        return ret;
    }


}

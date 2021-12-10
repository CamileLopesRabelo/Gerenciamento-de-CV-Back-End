package com.dbc.curriculocv.service;

import com.dbc.curriculocv.client.VagasCompleoClient;
import com.dbc.curriculocv.dto.VagasCompleoDTO;
import com.dbc.curriculocv.dto.VagasDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VagasCompleoService {

    private final VagasCompleoClient vagasCompleoClient;
    private final ObjectMapper objectMapper;

    public VagasDto list(Integer pagina, Integer quantidade) {
        VagasDto ret = vagasCompleoClient.listar("hzL2rHXeERNR",pagina,quantidade);
        System.out.println(ret);
        List<VagasDto> todasAsVagas = new ArrayList<>();

        //TODO 1- buscar todas as paginas de vagas,2-  filtrar por status em aberto, 3- retornar vagas dto
        return ret;
    }


}

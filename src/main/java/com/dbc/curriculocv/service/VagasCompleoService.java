package com.dbc.curriculocv.service;

import com.dbc.curriculocv.client.VagasCompleoClient;
import com.dbc.curriculocv.dto.VagasCompleoDTO;
import com.dbc.curriculocv.dto.VagasDto;
import com.dbc.curriculocv.dto.VagasFiltradasDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class VagasCompleoService {

    private final VagasCompleoClient vagasCompleoClient;
    private final ObjectMapper objectMapper;
    private final String TOKEN = "hzL2rHXeERNR";
    private final Integer PAGINA = 1;
    private final Integer QUANTIDADE = 100;

    public VagasFiltradasDto list() {
        VagasDto ret = vagasCompleoClient.listar(TOKEN,PAGINA,QUANTIDADE); //faz a consulta na pagina 1

        VagasDto tempVagas = new VagasDto();
        List<VagasCompleoDTO> lista = new ArrayList<>();

        //Percorre todas as paginas de acordo com o recebido na primeira requisição,
        for(int paginaAtual = (PAGINA + 1); paginaAtual <= ret.getPaginas(); paginaAtual++ ) {

            //faz a consulta na pagina atual de acordo com o indice
            tempVagas = vagasCompleoClient.listar(TOKEN, paginaAtual, QUANTIDADE);

            //armazena as vagas da pagina atual
            lista = tempVagas.getVagaGeralList();

            //concatena as vagas da pagina atual com as das paginas anteriores
            ret.setVagaGeralList(concatenaVagas(ret, lista).getVagaGeralList());
        }
        //setando as vagas filtradas com status Aberto
        ret.setVagaGeralList(filtrarVagas(ret));


        VagasFiltradasDto vagasFiltradasDto = objectMapper.convertValue(ret,VagasFiltradasDto.class);
        vagasFiltradasDto.setTotalDeVagas(ret.getVagaGeralList().size());
        return vagasFiltradasDto;
    }


     //concatena as vagas da pagina atual com as das paginas anteriores
    public VagasDto concatenaVagas(VagasDto principal, List<VagasCompleoDTO> lista) {
        principal.getVagaGeralList().addAll(lista);
        return principal;
    }

    //filtra as vagas pelo status "Aberto"
    public List<VagasCompleoDTO> filtrarVagas(VagasDto ret) {
        List<VagasCompleoDTO> listaFiltrada = new ArrayList<>();

        for(int i = 0; i < ret.getVagaGeralList().size(); i++) {
            if (ret.getVagaGeralList().get(i).getStatus().equals("Aberta")) {
                listaFiltrada.add(ret.getVagaGeralList().get(i));
            }
        }
        return listaFiltrada;
    }


}

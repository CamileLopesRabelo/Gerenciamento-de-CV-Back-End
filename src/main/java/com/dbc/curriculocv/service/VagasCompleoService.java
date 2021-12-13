package com.dbc.curriculocv.service;

import com.dbc.curriculocv.client.VagasCompleoClient;
import com.dbc.curriculocv.dto.VagasCompleoDTO;
import com.dbc.curriculocv.dto.VagasDTO;
import com.dbc.curriculocv.dto.VagasFiltradasDTO;
import com.dbc.curriculocv.entity.Vaga;
import com.dbc.curriculocv.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VagasCompleoService {
    private final VagaRepository vagaRepository;
    private final VagasCompleoClient vagasCompleoClient;
    private final ObjectMapper objectMapper;
    private final String TOKEN = "hzL2rHXeERNR";
    private final Integer PAGINA = 1;
    private final Integer QUANTIDADE = 100;

    public VagasFiltradasDTO list() {
        VagasDTO ret = vagasCompleoClient.listar(TOKEN,PAGINA,QUANTIDADE); //faz a consulta na pagina 1

        VagasDTO tempVagas;
        List<VagasCompleoDTO> lista;

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


        VagasFiltradasDTO vagasFiltradasDto = objectMapper.convertValue(ret, VagasFiltradasDTO.class);
        vagasFiltradasDto.setTotalDeVagas(ret.getVagaGeralList().size());
        return vagasFiltradasDto;
    }


     //concatena as vagas da pagina atual com as das paginas anteriores
    public VagasDTO concatenaVagas(VagasDTO principal, List<VagasCompleoDTO> lista) {
        principal.getVagaGeralList().addAll(lista);
        return principal;
    }

    //filtra as vagas pelo status "Aberto" e id existente no banco
    public List<VagasCompleoDTO> filtrarVagas(VagasDTO ret) {
        List<VagasCompleoDTO> listaFiltrada = new ArrayList<>();
        List<Vaga> vagas = vagaRepository.findAll();
        for (int j = 0; j < ret.getVagaGeralList().size(); j++) {
            for(int i = 0; i < vagas.size(); i++) {
                if (!listaFiltrada.contains(ret.getVagaGeralList().get(j))
                        && ((ret.getVagaGeralList().get(j).getId().equals(vagas.get(i).getId())
                        || ret.getVagaGeralList().get(j).getStatus().equals("Aberta")))) {
                    listaFiltrada.add(ret.getVagaGeralList().get(j));
                }
            }

        }

        return listaFiltrada;
    }
}

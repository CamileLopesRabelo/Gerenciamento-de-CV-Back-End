package com.dbc.curriculocv.service;

import com.dbc.curriculocv.client.VagasCompleoClient;
import com.dbc.curriculocv.dto.VagasCompleoDTO;
import com.dbc.curriculocv.dto.VagasDTO;
import com.dbc.curriculocv.dto.VagasFiltradasDTO;
import com.dbc.curriculocv.entity.Vaga;
import com.dbc.curriculocv.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@DisplayName("VagasCompleoServiceTest")
public class VagasCompleoServiceTest {

    @InjectMocks
    private VagasCompleoService vagasCompleoService;

    @Mock
    private VagasCompleoClient vagasCompleoClient;

    @Mock
    private VagaRepository vagaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void beforeEach() {
        ReflectionTestUtils.setField(vagasCompleoService, "objectMapper",objectMapper);
    }

    @Test
    @DisplayName("deve listar as vagas da api do compleo")
    public void listarVagasCompleo() {

        List<Vaga> vagas = new ArrayList<>();
        List<VagasCompleoDTO> listaDeVagasCompleo = new ArrayList<>();
        VagasDTO vagasDTO = new VagasDTO();
        vagasDTO.setVagaGeralList(listaDeVagasCompleo);
        vagasDTO.setPaginas(10);
        vagasDTO.setPagina(1);
        vagasDTO.setQuantidade(10);

        when(vagasCompleoClient.listar(ArgumentMatchers.any(),ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(vagasDTO);
        when(vagaRepository.findAll()).thenReturn(vagas);
        VagasFiltradasDTO list = vagasCompleoService.list();

        assertNotNull(list);
        assertEquals(vagasDTO.getVagaGeralList(),list.getVagaGeralList());

    }

    @Test
    @DisplayName("deve filtrar as vagas que estão com status em aberto no compleo")
    public void filtrarVagas() {
        VagasDTO vagaDTO = new VagasDTO();
        List<Vaga> vagas = new ArrayList<>();

        when(vagaRepository.findAll()).thenReturn(vagas);

        List<VagasCompleoDTO> listaFiltrada = vagasCompleoService.filtrarVagas(vagaDTO);

        assertNotNull(listaFiltrada);
    }


}

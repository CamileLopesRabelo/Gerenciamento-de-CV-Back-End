package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.DadosEscolaresCreateDTO;
import com.dbc.curriculocv.dto.DadosEscolaresDTO;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.DadosEscolares;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.dbc.curriculocv.repository.DadosEscolaresRepository;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@DisplayName("DadosEscolaresServiceTest")
public class DadosEscolaresServiceTest {

    @InjectMocks
    private DadosEscolaresService dadosEscolaresService;

    @Mock
    private DadosEscolaresRepository dadosEscolaresRepository;

    @Mock
    private CandidatoRepository candidatoRepository;


    private final ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void beforeEach() {
        ReflectionTestUtils.setField(dadosEscolaresService, "objectMapper",objectMapper);
    }

    @Test
    @DisplayName("Deve listar os dados escolares")
    public void listarDadosEscolares() {
        DadosEscolares dadosEscolares = new DadosEscolares();
        dadosEscolares.setInstituicao("José de Borba Vasconcelos");
        List<DadosEscolares> listDadosEscolares = new ArrayList<>();
        listDadosEscolares.add(dadosEscolares);

        when(dadosEscolaresRepository.findAll()).thenReturn(listDadosEscolares);

        List<DadosEscolaresDTO> list = dadosEscolaresService.list();

        assertNotNull(list);
        assertEquals("José de Borba Vasconcelos",list.get(0).getInstituicao());
    }

    @Test
    @DisplayName("Deve criar dados escolares")
    public void criarDadosEscolares() throws RegraDeNegocioException {
        Candidato candidato = new Candidato();
        candidato.setIdCandidato(1);

        DadosEscolares dadosEscolares = new DadosEscolares();
        dadosEscolares.setInstituicao("coelgio 1");
        DadosEscolaresCreateDTO dadosEscolaresCreateDTO = new DadosEscolaresCreateDTO();

        when(candidatoRepository.findById(candidato.getIdCandidato())).thenReturn(Optional.of(candidato));
        when(dadosEscolaresRepository.save(ArgumentMatchers.any())).thenReturn(dadosEscolares);

        DadosEscolaresDTO dadosEscolaresDTO = dadosEscolaresService.create(candidato.getIdCandidato(), dadosEscolaresCreateDTO);

        assertNotNull(dadosEscolaresDTO);
        assertEquals("coelgio 1",dadosEscolaresDTO.getInstituicao());
    }

    @Test
    @DisplayName("deve alterar os dados escolares")
    public void alterarDadosEscolares() throws RegraDeNegocioException {
        DadosEscolares dadosEscolares = new DadosEscolares();
        dadosEscolares.setIdDadosEscolares(1);
        DadosEscolares save = new DadosEscolares();
        DadosEscolaresCreateDTO dadosEscolaresCreateDTO = new DadosEscolaresCreateDTO();

        when(dadosEscolaresRepository.findById(dadosEscolares.getIdDadosEscolares())).thenReturn(Optional.of(dadosEscolares));
        when(dadosEscolaresRepository.save(dadosEscolares)).thenReturn(save);


        DadosEscolaresDTO update = dadosEscolaresService.update(dadosEscolares.getIdDadosEscolares(), dadosEscolaresCreateDTO);

        assertNotNull(update);
    }
}

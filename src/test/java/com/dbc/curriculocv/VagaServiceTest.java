package com.dbc.curriculocv;

import com.dbc.curriculocv.dto.VagaCandidatoDTO;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.Vaga;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.dbc.curriculocv.repository.VagaRepository;
import com.dbc.curriculocv.service.VagaService;
import com.dbc.curriculocv.service.VagasCompleoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@DisplayName("VagaServiceTest")
public class VagaServiceTest {

    @Mock
    private VagaRepository vagaRepository;

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private VagasCompleoService vagasCompleoService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private VagaService vagaService;


    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void VerificarVincularCandidatoAVagaEstaVinculando() throws RegraDeNegocioException {
        Candidato candidato = new Candidato();
        candidato.setIdCandidato(1);
        Vaga vaga = new Vaga();
        vaga.setCandidatos(new HashSet<>());
        vaga.setId(1);
        Vaga vagaSave = new Vaga();
        vagaSave.setId(1);
        Set<Candidato> candidatos = vaga.getCandidatos();
        candidatos.add(candidato);
        vagaSave.setCandidatos(candidatos);


        when(candidatoRepository.findById(1)).thenReturn(java.util.Optional.of(candidato));
        when(vagaRepository.findById(1)).thenReturn(java.util.Optional.of(vaga));
        when(vagaRepository.save(vaga)).thenReturn(vagaSave);
        VagaCandidatoDTO vagaCandidatoDTO = vagaService.vincularCandidatoAVaga(candidato.getIdCandidato(), vaga.getId());

        Assertions.assertTrue(vagaCandidatoDTO.getCandidatos().contains(candidato));
//        verify(vagaService, Mockito.times(1)).vincularCandidatoAVaga(1,1);


    }

}

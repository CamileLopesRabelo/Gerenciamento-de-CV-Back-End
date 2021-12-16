package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.CandidatoDTO;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;

import static org.mockito.Mockito.*;


@DisplayName("CandidatoServiceTest")
public class CandidatoServiceTest {

    @InjectMocks
    private CandidatoService candidatoService;

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("deve listar varios candidatos")
    public void listarCandidatos () throws RegraDeNegocioException {
        candidatoService.list(null);
        verify(candidatoRepository,Mockito.times(1)).findAll();
        verify(candidatoRepository,Mockito.never()).findById(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("deve retornar um candidato")
    public void listarCandidatoPorId () throws RegraDeNegocioException {
        Integer candidatoId = 3;

        Candidato candidato = mock(Candidato.class);

        when((candidatoRepository.findById(ArgumentMatchers.eq(candidatoId)))).thenReturn(Optional.ofNullable(candidato));

        candidatoService.list(candidatoId);
        verify(candidatoRepository,Mockito.times(1)).findById(ArgumentMatchers.eq(candidatoId));
        verify(candidatoRepository,Mockito.never()).findAll();
    }

}

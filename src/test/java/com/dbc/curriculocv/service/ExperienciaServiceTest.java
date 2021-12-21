package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.ExperienciasCreateDTO;
import com.dbc.curriculocv.dto.ExperienciasDTO;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.Experiencias;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.dbc.curriculocv.repository.ExperienciasRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DisplayName("ExperienciaServiceTest")
public class ExperienciaServiceTest {

    @InjectMocks
    private ExperienciasService experienciasService;

    @Mock
    private ExperienciasRepository experienciasRepository;

    @Mock
    private CandidatoRepository candidatoRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void beforeEach() {
        ReflectionTestUtils.setField(experienciasService, "objectMapper",objectMapper);
    }

    @Test
    @DisplayName("deve listar as experiencias")
    public void listarExperiencias() {
        List<Experiencias> experiencias = new ArrayList<>();

        when(experienciasRepository.findAll()).thenReturn(experiencias);

        List<ExperienciasDTO> list = experienciasService.list();

        assertNotNull(list);
    }

    @Test
    @DisplayName("deve criar uma experiencia")
    public void criarExperiencia() throws RegraDeNegocioException {
        Candidato candidato = new Candidato();
        candidato.setIdCandidato(1);
        Experiencias experiencias = new Experiencias();
        experiencias.setNomeEmpresa("DBC Company");
        ExperienciasCreateDTO experienciasCreateDTO = new ExperienciasCreateDTO();

        when(candidatoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(candidato));
        when(experienciasRepository.save(ArgumentMatchers.any())).thenReturn(experiencias);

        ExperienciasDTO experienciasDTO = experienciasService.create(candidato.getIdCandidato(),experienciasCreateDTO);

        assertNotNull(experienciasDTO);
        assertEquals("DBC Company",experienciasDTO.getNomeEmpresa());
    }

    @Test
    @DisplayName("deve alterar os dados da experiencia")
    public void alterarExperiencias() throws RegraDeNegocioException {
        Experiencias experiencias =  new Experiencias();
        experiencias.setIdExperiencia(1);
        Experiencias experienciasSave = new Experiencias();
        experienciasSave.setNomeEmpresa("DBC Company");

        ExperienciasCreateDTO experienciasCreateDTO = new ExperienciasCreateDTO();
        experiencias.setNomeEmpresa("DBC Company");

        when(experienciasRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(experiencias));
        when(experienciasRepository.save(ArgumentMatchers.any())).thenReturn(experienciasSave);

        ExperienciasDTO update = experienciasService.update(experiencias.getIdExperiencia(), experienciasCreateDTO);

        assertNotNull(update);
        assertEquals("DBC Company",update.getNomeEmpresa());

    }

    @Test
    @DisplayName("deve deletar experiencias")
    public void deletarExperiencias() throws RegraDeNegocioException {
        Experiencias experiencias = new Experiencias();
        experiencias.setIdExperiencia(1);

        when(experienciasRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(experiencias));
        doNothing().when(experienciasRepository).delete(experiencias);

        experienciasService.delete(experiencias.getIdExperiencia());

        verify(experienciasRepository,times(1)).delete(ArgumentMatchers.any());
        verify(experienciasRepository,times(1)).findById(ArgumentMatchers.any());
    }

}

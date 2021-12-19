package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.*;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.Vaga;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DisplayName("CandidatoServiceTest")
public class VagaServiceTest {

    @InjectMocks
    private VagaService vagaService;

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private VagasCompleoService vagasCompleoService;

    @Mock
    private VagaRepository vagaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        ReflectionTestUtils.setField(vagaService,"objectMapper",objectMapper);
    }

    @Test
    @DisplayName("deve atualizar as vagas existentes")
    public void updateTableExistentes() throws RegraDeNegocioException {
        List<VagasCompleoDTO> vagasCompleoDTOS = new ArrayList<>();
        vagasCompleoDTOS.add(new VagasCompleoDTO());
        VagasFiltradasDTO vagasFiltradasDTO = new VagasFiltradasDTO();
        vagasFiltradasDTO.setTotalDeVagas(1);
        vagasFiltradasDTO.setVagaGeralList(vagasCompleoDTOS);
        Vaga vaga = new Vaga();

        Vaga vagaSalva = new Vaga();

        when(vagasCompleoService.list()).thenReturn(vagasFiltradasDTO);
        when(vagaRepository.existsById(ArgumentMatchers.any())).thenReturn(true);
        when(vagaRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(vaga));
        when(vagaRepository.save(ArgumentMatchers.any())).thenReturn(vagaSalva);

        vagaService.updateTable();

        verify(vagasCompleoService,times(1)).list();
        verify(vagaRepository,times(1)).existsById(ArgumentMatchers.any());
        verify(vagaRepository,times(1)).findById(ArgumentMatchers.any());
        verify(vagaRepository,times(1)).save(ArgumentMatchers.any());

    }

    @Test
    @DisplayName("cria as novas vagas no banco")
    public void updateTable() throws RegraDeNegocioException {
        List<VagasCompleoDTO> vagasCompleoDTOS = new ArrayList<>();
        vagasCompleoDTOS.add(new VagasCompleoDTO());
        VagasFiltradasDTO vagasFiltradasDTO = new VagasFiltradasDTO();
        vagasFiltradasDTO.setTotalDeVagas(1);
        vagasFiltradasDTO.setVagaGeralList(vagasCompleoDTOS);
        Vaga vaga = new Vaga();

        Vaga vagaSalva = new Vaga();

        when(vagasCompleoService.list()).thenReturn(vagasFiltradasDTO);
        when(vagaRepository.existsById(ArgumentMatchers.any())).thenReturn(false);
        when(vagaRepository.save(ArgumentMatchers.any())).thenReturn(vagaSalva);

        vagaService.updateTable();

        verify(vagasCompleoService,times(1)).list();
        verify(vagaRepository,times(1)).existsById(ArgumentMatchers.any());
        verify(vagaRepository,never()).findById(ArgumentMatchers.any());
        verify(vagaRepository,times(1)).save(ArgumentMatchers.any());

    }

    @Test
    @DisplayName("deve vincular um candidato a vaga")
    public void vincularCandidatoVaga() throws RegraDeNegocioException {
        Candidato candidato = new Candidato();
        candidato.setIdCandidato(1);
        candidato.setNome("camile");
        CandidatoDTO candidatoDTO = objectMapper.convertValue(candidato, CandidatoDTO.class);


        Set<Candidato> listaCandidatos = new HashSet<>();
        listaCandidatos.add(candidato);
        Vaga vaga = new Vaga();
        vaga.setId(1);
        vaga.setCandidatos(listaCandidatos);
        Vaga vagaSalva = new Vaga();
        vagaSalva.setCandidatos(listaCandidatos);



        when(candidatoRepository.findById(candidato.getIdCandidato())).thenReturn(Optional.of(candidato));
        when(vagaRepository.findById(vaga.getId())).thenReturn(Optional.of(vaga));
        when(vagaRepository.save(vaga)).thenReturn(vagaSalva);

        VagaCandidatoDTO vagaCandidatoDTO = vagaService.vincularCandidatoAVaga(candidato.getIdCandidato(), vaga.getId());

        assertNotNull(vagaCandidatoDTO);
        assertEquals(vagaSalva.getId(),vagaCandidatoDTO.getVaga().getId());
        assertTrue(vagaCandidatoDTO.getCandidatos().contains(candidatoDTO));
    }

    @Test
    @DisplayName("deve desvincular o candidato da vaga")
    public void desvincularCandidatoVaga() throws RegraDeNegocioException {
        Candidato candidato1 = new Candidato();
        candidato1.setIdCandidato(1);
        Set<Candidato> candidatos = new HashSet<>();
        Vaga vaga = new Vaga();
        vaga.setId(1);
        vaga.setCandidatos(candidatos);
        Vaga vagaSave = new Vaga();
        vagaSave.setCandidatos(new HashSet<>());

        when(candidatoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(candidato1));
        when(vagaRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(vaga));
        when(vagaRepository.save(vaga)).thenReturn(vagaSave);

        VagaCandidatoDTO vagaCandidatoDTO = vagaService.desvincularCandidatoVaga(candidato1.getIdCandidato(), vaga.getId());

        assertNotNull(vagaCandidatoDTO);
        assertFalse(vagaCandidatoDTO.getCandidatos().contains(candidato1));
    }

    @Test
    @DisplayName("deve listar a vaga pelo id")
    public void listById() throws RegraDeNegocioException {
        Vaga vaga = new Vaga();
        vaga.setId(5);

        when(vagaRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(vaga));

        List<VagaDTO> list = vagaService.list(vaga.getId());

        assertNotNull(list);
        assertEquals(5,list.get(0).getId());
    }

    @Test
    @DisplayName("deve listar todas as vagas")
    public void list() throws RegraDeNegocioException {
        List<Vaga> vagas = new ArrayList<>();

        when(vagaRepository.findAll()).thenReturn(vagas);
        List<VagaDTO> list = vagaService.list(null);

        assertNotNull(list);
    }

    @Test
    @DisplayName("listar vaga por pagina")
    public void listPagina() {
        List<Vaga> vagas = new ArrayList<>();
        vagas.add(new Vaga());
        Page<Vaga> vagaPage = new Page<Vaga>() {
            @Override
            public Iterator<Vaga> iterator() {
                return null;
            }

            @Override
            public int getTotalPages() {
                return vagas.size();
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return vagas.size();
            }

            @Override
            public int getNumberOfElements() {
                return vagas.size();
            }

            @Override
            public List<Vaga> getContent() {
                return vagas;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return Sort.by("id");
            }

            @Override
            public boolean isFirst() {
                return true;
            }

            @Override
            public boolean isLast() {
                return true;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public <U> Page<U> map(Function<? super Vaga, ? extends U> converter) {
                return null;
            }
        };

        when(vagaRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(vagaPage);

        VagaPaginadaDTO vagaPaginadaDTO = vagaService.listaVagasPaginada(1, 2);

        assertNotNull(vagaPaginadaDTO);
        assertEquals(1,vagaPaginadaDTO.getVagas().size());
    }


}

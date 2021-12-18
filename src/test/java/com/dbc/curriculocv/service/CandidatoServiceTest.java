package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.*;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.DadosEscolares;
import com.dbc.curriculocv.entity.Experiencias;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.*;
import java.util.function.Function;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DisplayName("CandidatoServiceTest")
public class CandidatoServiceTest {

    @InjectMocks
    private CandidatoService candidatoService;

    @Mock
    private CandidatoRepository candidatoRepository;


    private final ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void beforeEach() {
        ReflectionTestUtils.setField(candidatoService, "objectMapper",objectMapper);
    }


    @Test
    @DisplayName("deve listar varios candidatos")
    public void listarCandidatos () throws RegraDeNegocioException {
        List<CandidatoDTO> list = candidatoService.list(null);

        assertNotNull(list);

    }

    @Test
    @DisplayName("deve retornar um candidato")
    public void listarCandidatoPorId () throws RegraDeNegocioException {
        Candidato candidato = new Candidato();
        candidato.setIdCandidato(1);
        candidato.setNome("camile");

        when((candidatoRepository.findById(ArgumentMatchers.eq(candidato.getIdCandidato())))).thenReturn(Optional.ofNullable(candidato));

        List<CandidatoDTO> listaDeCandidatos = candidatoService.list(candidato.getIdCandidato());

        assertNotNull(listaDeCandidatos);
        assertEquals("camile",listaDeCandidatos.get(0).getNome());
    }

    @Test
    @DisplayName("deve criar candidato")
    public void criarCandidato() throws RegraDeNegocioException {
        CandidatoCreateDTO candidatoCreateDto = new CandidatoCreateDTO();
        candidatoCreateDto.setCpf("12345678910");
        Candidato candidatoSave = new Candidato();
        candidatoSave.setIdCandidato(1);
        candidatoSave.setCpf("12345678910");

        when(candidatoRepository.existsByCpf(candidatoCreateDto.getCpf())).thenReturn(false);
        when(candidatoRepository.save(ArgumentMatchers.any())).thenReturn(candidatoSave);


        CandidatoDTO candidatoDTO = candidatoService.create(candidatoCreateDto);
        assertNotNull(candidatoDTO);
        assertEquals(1,candidatoDTO.getIdCandidato());
        assertEquals("12345678910",candidatoDTO.getCpf());
    }

    @Test(expected = RegraDeNegocioException.class)
    @DisplayName("deve retornar exessão ao tentar criar candidato")
    public void criarCandidatoComCpfExistente() throws RegraDeNegocioException {
        CandidatoCreateDTO candidatoCreateDTO =  mock(CandidatoCreateDTO.class);
        candidatoCreateDTO.setCpf("01829834675");

        when(candidatoRepository.existsByCpf(candidatoCreateDTO.getCpf())).thenReturn(true);

        candidatoService.create(candidatoCreateDTO);

    }

    @Test
    @DisplayName("deve alterar os dados do candidato sem modificar o cpf")
    public void updateCandidatoSemMudancaNoCPF() throws RegraDeNegocioException {
        Candidato candidato = new Candidato();
        candidato.setIdCandidato(1);
        CandidatoCreateDTO candidatoCreateDTO = new CandidatoCreateDTO();
        candidatoCreateDTO.setCpf("12345678910");
        Candidato candidatoAlterado = new Candidato();
        candidatoAlterado.setNome("camile");

        when(candidatoRepository.findById(eq(candidato.getIdCandidato()))).thenReturn(Optional.of(candidato));
        when(candidatoRepository.existsByCpf(candidatoCreateDTO.getCpf())).thenReturn(false);
        when(candidatoRepository.save(candidato)).thenReturn(candidatoAlterado);

        CandidatoDTO update = candidatoService.update(candidato.getIdCandidato(), candidatoCreateDTO);

        assertNotNull(update);
        assertEquals("camile", update.getNome());
    }



    @Test(expected = RegraDeNegocioException.class)
    @DisplayName("deve alterar os dados do candidato e o cpf")
    public void updateCandidatoComMudancaNoCPFJaCadastrado() throws RegraDeNegocioException {
        Candidato candidato = new Candidato();
        candidato.setIdCandidato(1);
        CandidatoCreateDTO candidatoCreateDTO = new CandidatoCreateDTO();
        candidatoCreateDTO.setCpf("12345678910");
        Candidato candidatoAlterado = new Candidato();
        candidatoAlterado.setNome("camile");

        when(candidatoRepository.findById(eq(candidato.getIdCandidato()))).thenReturn(Optional.of(candidato));
        when(candidatoRepository.existsByCpf(candidatoCreateDTO.getCpf())).thenReturn(true);

        candidatoService.update(candidato.getIdCandidato(), candidatoCreateDTO);
    }

    @Test
    @DisplayName("deve deletar")
    public void deleteCandidato() throws RegraDeNegocioException {
        Candidato candidato = mock(Candidato.class);
        candidato.setIdCandidato(1);
        Candidato ret = mock(Candidato.class);

        when(candidatoRepository.findById(ArgumentMatchers.eq(candidato.getIdCandidato()))).thenReturn(Optional.of(ret));
        doNothing().when(candidatoRepository).delete(ret);

        candidatoService.delete(ret.getIdCandidato());

        verify(candidatoRepository,times(1)).findById(ArgumentMatchers.eq(candidato.getIdCandidato()));
        verify(candidatoRepository,times(1)).delete(ret);
    }

    @Test
    @DisplayName("deve listar as experiencias do candidato")
    public void listarExperienciasDosCandidatos() throws RegraDeNegocioException {
        List<CandidatoDadosExperienciasDTO> experiencias = candidatoService.listCandidatosDadosExperiencias(null);
        assertNotNull(experiencias);

    }

    @Test
    @DisplayName("deve retornar as experiencias de um candidato")
    public void listarExperienciasDeUmCandidato () throws RegraDeNegocioException {
        Candidato candidato = new Candidato();
        candidato.setIdCandidato(1);
        candidato.setCargo("Talent Aquisition");

        when((candidatoRepository.findById(candidato.getIdCandidato()))).thenReturn(Optional.ofNullable(candidato));

        List<CandidatoDTO> list = candidatoService.list(candidato.getIdCandidato());

        assertNotNull(list);
        assertEquals("Talent Aquisition",list.get(0).getCargo());
    }

    @Test
    @DisplayName("deve listar candidatos por pagina")
    public void listaCandidatosPorPagina() {
        List<Candidato> candidatos = new ArrayList<>();
        candidatos.add(new Candidato());
        Page<Candidato> candidatoPage =  new Page<Candidato>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return candidatos.size();
            }

            @Override
            public <U> Page<U> map(Function<? super Candidato, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return candidatos.size();
            }

            @Override
            public int getNumberOfElements() {
                return candidatos.size();
            }

            @Override
            public List<Candidato> getContent() {
                return candidatos;
            }

            @Override
            public boolean hasContent() {
                return true;
            }

            @Override
            public Sort getSort() {
                return  Sort.by("cpf");
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
            public Iterator<Candidato> iterator() {
                return null;
            }
        };

        when(candidatoRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(candidatoPage);

        CandidatoPaginadaDTO candidatoPaginadaDTO = candidatoService.listaCandidatosPaginada(1, 2);

        assertNotNull(candidatoPaginadaDTO);
        assertEquals(1,candidatoPaginadaDTO.getCandidatos().size());

    }

    @Test
    @DisplayName("deve listar experiencias dos candidatos por pagina")
    public void listarExperienciasPorPagina() {
        Experiencias experiencias1 = new Experiencias();
        experiencias1.setNomeEmpresa("DBC Company");
        Set<Experiencias> experiencias = new HashSet<>();
        experiencias.add(experiencias1);

        Set<DadosEscolares> dadosEscolares = new HashSet<>();
        DadosEscolares dadosEscolares1 = new DadosEscolares();
        dadosEscolares1.setInstituicao("Joaquim Barroso Neto");
        dadosEscolares.add(dadosEscolares1);

        Candidato candidato = new Candidato();
        candidato.setIdCandidato(1);
        candidato.setDadosEscolares(dadosEscolares);
        candidato.setExperiencias(experiencias);

        List<Candidato> candidatos = new ArrayList<>();
        candidatos.add(candidato);
        Page<Candidato> candidatoPage =  new Page<Candidato>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return candidatos.size();
            }

            @Override
            public <U> Page<U> map(Function<? super Candidato, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return candidatos.size();
            }

            @Override
            public int getNumberOfElements() {
                return candidatos.size();
            }

            @Override
            public List<Candidato> getContent() {
                return candidatos;
            }

            @Override
            public boolean hasContent() {
                return true;
            }

            @Override
            public Sort getSort() {
                return  Sort.by("cpf");
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
            public Iterator<Candidato> iterator() {
                return null;
            }
        };

        when(candidatoRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(candidatoPage);

        CandidatoDadosExperienciasPaginadaDTO candidatoDadosExperienciasPaginadaDTO = candidatoService.listaCandidatosDadosExperiencias(1, 1);

        assertNotNull(candidatoDadosExperienciasPaginadaDTO);
        assertEquals("Joaquim Barroso Neto",candidatoDadosExperienciasPaginadaDTO.getCandidatos().get(0).getDadosEscolares().get(0).getInstituicao());
        assertEquals("DBC Company",candidatoDadosExperienciasPaginadaDTO.getCandidatos().get(0).getExperiencias().get(0).getNomeEmpresa());
    }



}

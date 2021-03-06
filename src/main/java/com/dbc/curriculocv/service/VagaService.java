package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.*;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.Vaga;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.dbc.curriculocv.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VagaService {
    private final VagaRepository vagaRepository;
    private final CandidatoRepository candidatoRepository;
    private final VagasCompleoService vagasCompleoService;
    private final ObjectMapper objectMapper;

    public void updateTable() throws RegraDeNegocioException {
        VagasFiltradasDTO vagasFiltradasDTO = vagasCompleoService.list();
        for (int i = 0; i < vagasFiltradasDTO.getTotalDeVagas(); i++) {
            if (vagaRepository.existsById(vagasFiltradasDTO.getVagaGeralList().get(i).getId())) {
                VagasCompleoDTO vagasCompleoDTO = vagasFiltradasDTO.getVagaGeralList().get(i);
                Vaga vaga = objectMapper.convertValue(vagasCompleoDTO, Vaga.class);
                Vaga banco = vagaRepository.findById(vaga.getId())
                        .orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada"));
                vaga.setCandidatos(banco.getCandidatos());
                vagaRepository.save(vaga);
            } else {
                VagasCompleoDTO vagasCompleoDTO = vagasFiltradasDTO.getVagaGeralList().get(i);
                Vaga vaga = objectMapper.convertValue(vagasCompleoDTO, Vaga.class);
                vagaRepository.save(vaga);
            }
        }
    }

    @Transactional
    public VagaCandidatoDTO vincularCandidatoAVaga(Integer idCandidato, Integer idVaga) throws RegraDeNegocioException {
        Candidato candidatoEntity = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        Vaga vagaEntity = vagaRepository.findById(idVaga).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada"));
        Set<Candidato> candidatos = vagaEntity.getCandidatos();
        candidatos.add(candidatoEntity);
        vagaEntity.setCandidatos(candidatos);
        Vaga vagaSave = vagaRepository.save(vagaEntity);
        return new VagaCandidatoDTO(
                objectMapper.convertValue(vagaSave, VagaDTO.class),
                vagaSave.getCandidatos()
                        .stream()
                        .map(candidato -> objectMapper.convertValue(candidato, CandidatoDTO.class))
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public VagaCandidatoDTO desvincularCandidatoVaga(Integer idCandidato, Integer idVaga) throws RegraDeNegocioException {
        Candidato candidatoEntity = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        Vaga vagaEntity = vagaRepository.findById(idVaga).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada"));
        Set<Candidato> candidatos = vagaEntity.getCandidatos();
        candidatos.remove(candidatoEntity);
        vagaEntity.setCandidatos(candidatos);
        Vaga vagaSave = vagaRepository.save(vagaEntity);
        return new VagaCandidatoDTO(
                objectMapper.convertValue(vagaSave, VagaDTO.class),
                vagaSave.getCandidatos()
                        .stream()
                        .map(candidato -> objectMapper.convertValue(candidato, CandidatoDTO.class))
                        .collect(Collectors.toList())
        );
    }

    public List<VagaDTO> list(Integer idVaga) throws RegraDeNegocioException {
        List<VagaDTO> vagaById = new ArrayList<>();
        if (idVaga == null) {
            return vagaRepository.findAll()
                    .stream()
                    .map(vaga -> objectMapper.convertValue(vaga, VagaDTO.class))
                    .collect(Collectors.toList());
        }
        Vaga vagaEntity = vagaRepository.findById(idVaga).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada"));
        vagaById.add(objectMapper.convertValue(vagaEntity, VagaDTO.class));
        return vagaById;
    }

    @Transactional
    public List<VagaCandidatoDTO> listVagaCandidato(Integer idVaga) throws RegraDeNegocioException {
        List<VagaCandidatoDTO> vagaById = new ArrayList<>();
        if (idVaga == null) {
            return vagaRepository.findAll()
                    .stream()
                    .map(vaga -> {
                        VagaCandidatoDTO vagaCandidatoDTO = new VagaCandidatoDTO();
                        vagaCandidatoDTO.setVaga(objectMapper.convertValue(vaga, VagaDTO.class));
                        vagaCandidatoDTO.setCandidatos(
                                vaga.getCandidatos()
                                        .stream()
                                        .map(candidato -> objectMapper.convertValue(candidato, CandidatoDTO.class))
                                        .collect(Collectors.toList())
                        );
                        return vagaCandidatoDTO;
                    })
                    .collect(Collectors.toList());
        }
        Vaga vagaEntity = vagaRepository.findById(idVaga).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada"));
        vagaById.add(new VagaCandidatoDTO(
                objectMapper.convertValue(vagaEntity, VagaDTO.class),
                vagaEntity.getCandidatos()
                        .stream()
                        .map(candidato -> objectMapper.convertValue(candidato, CandidatoDTO.class))
                        .collect(Collectors.toList())
        ));
        return vagaById;
    }

    public VagaPaginadaDTO listaVagasPaginada(Integer pagina, Integer quantidade) {
        Pageable pageable = PageRequest.of(pagina, quantidade);
        Page<Vaga> paginacao = vagaRepository.findAll(pageable);
        return new VagaPaginadaDTO(
                paginacao.getContent().stream().map(vaga -> objectMapper.convertValue(vaga, VagaDTO.class)).collect(Collectors.toList()),
                paginacao.getTotalElements(),
                paginacao.getTotalPages(),
                paginacao.getPageable().getPageNumber(),
                paginacao.getPageable().getPageSize()
        );
    }

    @Transactional
    public VagaCandidatoPaginadaDTO listaVagasCandidatoPaginada(Integer pagina, Integer quantidade) {
        Pageable pageable = PageRequest.of(pagina, quantidade);
        Page<Vaga> paginacao = vagaRepository.findAll(pageable);
        return new VagaCandidatoPaginadaDTO(
                paginacao.getContent().stream().map(vaga -> {
                    VagaCandidatoDTO vagaCandidatoDTO = new VagaCandidatoDTO();
                    vagaCandidatoDTO.setVaga(objectMapper.convertValue(vaga, VagaDTO.class));
                    vagaCandidatoDTO.setCandidatos(
                            vaga.getCandidatos()
                                    .stream()
                                    .map(candidato -> objectMapper.convertValue(candidato, CandidatoDTO.class))
                                    .collect(Collectors.toList())
                    );
                    return vagaCandidatoDTO;
                }).collect(Collectors.toList()),
                paginacao.getTotalElements(),
                paginacao.getTotalPages(),
                paginacao.getPageable().getPageNumber(),
                paginacao.getPageable().getPageSize()
        );
    }
}

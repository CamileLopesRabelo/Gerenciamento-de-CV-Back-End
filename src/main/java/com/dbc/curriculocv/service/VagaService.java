package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.*;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.Vaga;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.dbc.curriculocv.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void updateTable() {
        VagasFiltradasDTO vagasSemFiltro = vagasCompleoService.list();
        for (int i = 0; i < vagasSemFiltro.getTotalDeVagas(); i++) {
            VagasCompleoDTO vagasCompleoDTO = vagasSemFiltro.getVagaGeralList().get(i);
            Vaga vaga = objectMapper.convertValue(vagasCompleoDTO, Vaga.class);
            vagaRepository.save(vaga);
        }
    }

    public List<VagaDTO> list() {
        return vagaRepository.findAll().stream()
                .map(vagas -> objectMapper.convertValue(vagas,VagaDTO.class))
                .collect(Collectors.toList());
    }

    public VagaCandidatoDTO vincularCandidatoAVaga(Integer idCandidato, Integer idVaga) throws RegraDeNegocioException {
        Candidato candidatoentity = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        Vaga vagaEntity = vagaRepository.findById(idVaga).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada"));
        Set<Candidato> candidatos = vagaEntity.getCandidatos();
        candidatos.add(candidatoentity);
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
}

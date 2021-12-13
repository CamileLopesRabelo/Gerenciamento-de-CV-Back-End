package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.*;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.Vaga;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.dbc.curriculocv.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VagaService {
    private final VagaRepository vagaRepository;
    private final CandidatoRepository candidatoRepository;
    private final VagasCompleoService vagasCompleoService;
    private final ObjectMapper objectMapper;

    public void updateTable() throws RegraDeNegocioException {
        VagasFiltradasDTO vagasSemFiltro = vagasCompleoService.list();
        log.info("Vagas compleo listada com sucesso!");
        for (int i = 0; i < vagasSemFiltro.getTotalDeVagas(); i++) {
            if (vagaRepository.existsById(vagasSemFiltro.getVagaGeralList().get(i).getId())) {
                VagasCompleoDTO vagasCompleoDTO = vagasSemFiltro.getVagaGeralList().get(i);
                Vaga vaga = objectMapper.convertValue(vagasCompleoDTO, Vaga.class);
                Vaga banco = vagaRepository.findById(vaga.getId()).orElseThrow(() -> new RegraDeNegocioException("Vaga não encontrada"));
                vaga.setCandidatos(banco.getCandidatos());
                vagaRepository.save(vaga);
                System.out.println(i + "Vaga existente atualizada");
            } else {
                VagasCompleoDTO vagasCompleoDTO = vagasSemFiltro.getVagaGeralList().get(i);
                Vaga vaga = objectMapper.convertValue(vagasCompleoDTO, Vaga.class);
                vagaRepository.save(vaga);
                System.out.println("Nova vaga adicionada");
            }
        }
        log.info("vagas em aberto salva no postgrees");
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
}

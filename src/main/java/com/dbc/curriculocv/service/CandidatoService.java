package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.CandidatoCreateDTO;
import com.dbc.curriculocv.dto.CandidatoDTO;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidatoService {
    private final CandidatoRepository candidatoRepository;
    private final ObjectMapper objectMapper;

    public List<CandidatoDTO> list(Integer idCandidato) {
        if (idCandidato == null) {
            return candidatoRepository.findAll()
                    .stream()
                    .map(candidato -> objectMapper.convertValue(candidato, CandidatoDTO.class))
                    .collect(Collectors.toList());
        }
        return candidatoRepository.findById(idCandidato)
                .stream()
                .map(candidato -> objectMapper.convertValue(candidato, CandidatoDTO.class))
                .collect(Collectors.toList());
    }

    public CandidatoDTO create(CandidatoCreateDTO candidatoCreateDTO) { //TODO FAZER EXCEPTION DO CPF ERRO DUPLICATE KEY
        Candidato entity = objectMapper.convertValue(candidatoCreateDTO, Candidato.class);
        Candidato save = candidatoRepository.save(entity);
        return objectMapper.convertValue(save, CandidatoDTO.class);
    }

    public CandidatoDTO update(Integer idCandidato, CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException {
        Candidato entity = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        entity.setNome(candidatoCreateDTO.getNome());
        entity.setCpf(candidatoCreateDTO.getCpf());
        entity.setComplemento(candidatoCreateDTO.getComplemento());
        entity.setDataNascimento(candidatoCreateDTO.getDataNascimento());
        entity.setNumero(candidatoCreateDTO.getNumero());
        entity.setTelefone(candidatoCreateDTO.getTelefone());
        Candidato update = candidatoRepository.save(entity);
        return objectMapper.convertValue(update, CandidatoDTO.class);
    }

    public void delete(Integer idCandidato) throws RegraDeNegocioException {
        Candidato candidato = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        candidatoRepository.delete(candidato);
    }
}

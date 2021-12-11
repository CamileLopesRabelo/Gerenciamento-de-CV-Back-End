package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.DadosEscolaresCreateDTO;
import com.dbc.curriculocv.dto.DadosEscolaresDTO;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.DadosEscolares;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.dbc.curriculocv.repository.DadosEscolaresRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DadosEscolaresService {
    private final DadosEscolaresRepository dadosEscolaresRepository;
    private final CandidatoRepository candidatoRepository;
    private final ObjectMapper objectMapper;

    public List<DadosEscolaresDTO> list() {
        return dadosEscolaresRepository.findAll()
                .stream()
                .map(dadosEscolares -> objectMapper.convertValue(dadosEscolares, DadosEscolaresDTO.class))
                .collect(Collectors.toList());
    }

    public DadosEscolaresDTO create(Integer idCandidato, DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        DadosEscolares entity = objectMapper.convertValue(dadosEscolaresCreateDTO, DadosEscolares.class);
        Candidato candidato = candidatoRepository.findById(idCandidato). orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        entity.setCandidato(candidato);
        DadosEscolares save = dadosEscolaresRepository.save(entity);
        return objectMapper.convertValue(save, DadosEscolaresDTO.class);
    }

    public DadosEscolaresDTO update(Integer idDadosEscolares, DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        DadosEscolares entity = dadosEscolaresRepository.findById(idDadosEscolares).orElseThrow(() -> new RegraDeNegocioException("Dado escolar não encontrado"));
        entity.setInstituicao(dadosEscolaresCreateDTO.getInstituicao());
        entity.setDataInicio(dadosEscolaresCreateDTO.getDataInicio());
        entity.setDataFim(dadosEscolaresCreateDTO.getDataFim());
        entity.setDescricao(dadosEscolaresCreateDTO.getDescricao());
        DadosEscolares update = dadosEscolaresRepository.save(entity);
        return objectMapper.convertValue(update, DadosEscolaresDTO.class);
    }

    public void delete(Integer idDadosEscolares) throws RegraDeNegocioException {
        DadosEscolares dadosEscolares = dadosEscolaresRepository.findById(idDadosEscolares).orElseThrow(() -> new RegraDeNegocioException("Dado Escolar não encontrado"));
        dadosEscolaresRepository.delete(dadosEscolares);
    }
}

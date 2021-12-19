package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.ExperienciasCreateDTO;
import com.dbc.curriculocv.dto.ExperienciasDTO;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.entity.Experiencias;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.dbc.curriculocv.repository.ExperienciasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienciasService {
    private final ExperienciasRepository experienciasRepository;
    private final CandidatoRepository candidatoRepository;
    private final ObjectMapper objectMapper;

    public List<ExperienciasDTO> list() {
        return experienciasRepository.findAll()
                .stream()
                .map(experiencia -> objectMapper.convertValue(experiencia, ExperienciasDTO.class))
                .collect(Collectors.toList());
    }

    public ExperienciasDTO create(Integer idCandidato, ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException {
        Experiencias entity = objectMapper.convertValue(experienciasCreateDTO, Experiencias.class);
        Candidato candidato = candidatoRepository.findById(idCandidato). orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        entity.setCandidato(candidato);
        Experiencias save = experienciasRepository.save(entity);
        return objectMapper.convertValue(save, ExperienciasDTO.class);
    }

    public ExperienciasDTO update(Integer idExperiencia, ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException {
        Experiencias entity = experienciasRepository.findById(idExperiencia).orElseThrow(() -> new RegraDeNegocioException("Experiência não encontrada"));
        entity.setNomeEmpresa(experienciasCreateDTO.getNomeEmpresa());
        entity.setDataInicio(experienciasCreateDTO.getDataInicio());
        entity.setDataFim(experienciasCreateDTO.getDataFim());
        entity.setDescricao(experienciasCreateDTO.getDescricao());
        Experiencias update = experienciasRepository.save(entity);
        return objectMapper.convertValue(update, ExperienciasDTO.class);
    }

    public void delete(Integer idExperiencia) throws RegraDeNegocioException {
        Experiencias experiencias = experienciasRepository.findById(idExperiencia).orElseThrow(() -> new RegraDeNegocioException("Experiência não encontrada"));
        experienciasRepository.delete(experiencias);
    }
}

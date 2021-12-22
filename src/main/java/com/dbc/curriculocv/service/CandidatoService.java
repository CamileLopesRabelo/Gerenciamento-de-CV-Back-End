package com.dbc.curriculocv.service;

import com.dbc.curriculocv.dto.*;
import com.dbc.curriculocv.entity.Candidato;
import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
import com.dbc.curriculocv.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidatoService {
    private final CandidatoRepository candidatoRepository;
    private final ObjectMapper objectMapper;

    public List<CandidatoDTO> list(Integer idCandidato) throws RegraDeNegocioException {
        List<CandidatoDTO>  candidatoByID = new ArrayList<>();
        if (idCandidato == null) {
            return candidatoRepository.findAll()
                    .stream()
                    .map(candidato -> objectMapper.convertValue(candidato, CandidatoDTO.class))
                    .collect(Collectors.toList());
        }
        Candidato candidatoEntity = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        candidatoByID.add(objectMapper.convertValue(candidatoEntity, CandidatoDTO.class));
        return candidatoByID;
    }

    public CandidatoDTO create(CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException {
        if (candidatoRepository.existsByCpf(candidatoCreateDTO.getCpf())) {
            throw new RegraDeNegocioException("CPF já cadastrado");
        }
        Candidato entity = objectMapper.convertValue(candidatoCreateDTO, Candidato.class);
        Candidato save = candidatoRepository.save(entity);
        return objectMapper.convertValue(save, CandidatoDTO.class);
    }

    public CandidatoDTO update(Integer idCandidato, CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException {
        Candidato entity = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        entity.setNome(candidatoCreateDTO.getNome());
        if (!candidatoCreateDTO.getCpf().equals(entity.getCpf())) {
            if (candidatoRepository.existsByCpf(candidatoCreateDTO.getCpf())) {
                throw new RegraDeNegocioException("CPF já cadastrado");
            }
            entity.setCpf(candidatoCreateDTO.getCpf());
        }
        entity.setLogradouro(candidatoCreateDTO.getLogradouro());
        entity.setComplemento(candidatoCreateDTO.getComplemento());
        entity.setDataNascimento(candidatoCreateDTO.getDataNascimento());
        entity.setNumero(candidatoCreateDTO.getNumero());
        entity.setTelefone(candidatoCreateDTO.getTelefone());
        entity.setSenioridade(candidatoCreateDTO.getSenioridade());
        entity.setCargo(candidatoCreateDTO.getCargo());
        Candidato update = candidatoRepository.save(entity);
        return objectMapper.convertValue(update, CandidatoDTO.class);
    }

    public void delete(Integer idCandidato) throws RegraDeNegocioException {
        Candidato candidato = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        candidatoRepository.delete(candidato);
    }

    public List<CandidatoDadosExperienciasDTO> listCandidatosDadosExperiencias(Integer idCandidato) throws RegraDeNegocioException {
        List<CandidatoDadosExperienciasDTO> candidatoById = new ArrayList<>();
        if(idCandidato == null) {
            return candidatoRepository.findAll()
                    .stream()
                    .map(this::setCandidatoDadosExperienciasDTO)
                    .collect(Collectors.toList());
        }
        Candidato candidatoEntity = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        candidatoById.add(setCandidatoDadosExperienciasDTO(candidatoEntity));
        return candidatoById;
    }

    public CandidatoDadosExperienciasDTO setCandidatoDadosExperienciasDTO(Candidato candidato) {
        CandidatoDadosExperienciasDTO candidatoDadosExperienciasDTO = new CandidatoDadosExperienciasDTO();
        candidatoDadosExperienciasDTO.setCandidato(objectMapper.convertValue(candidato, CandidatoDTO.class));
        candidatoDadosExperienciasDTO.setDadosEscolares(
                candidato.getDadosEscolares()
                        .stream()
                        .map(dadoEscolar -> objectMapper.convertValue(dadoEscolar, DadosEscolaresDTO.class))
                        .collect(Collectors.toList())
        );
        candidatoDadosExperienciasDTO.setExperiencias(
                candidato.getExperiencias()
                        .stream()
                        .map(experiencia -> objectMapper.convertValue(experiencia, ExperienciasDTO.class))
                        .collect(Collectors.toList())
        );
        return candidatoDadosExperienciasDTO;
    }

    public CandidatoPaginadaDTO listaCandidatosPaginada(Integer pagina, Integer quantidade) {
        Pageable pageable = PageRequest.of(pagina, quantidade);
        Page<Candidato> paginacao = candidatoRepository.findAll(pageable);
        return new CandidatoPaginadaDTO(
                paginacao.getContent().stream().map(candidato -> objectMapper.convertValue(candidato, CandidatoDTO.class)).collect(Collectors.toList()),
                paginacao.getTotalElements(),
                paginacao.getTotalPages(),
                paginacao.getPageable().getPageNumber(),
                paginacao.getPageable().getPageSize()
        );
    }

    public CandidatoDadosExperienciasPaginadaDTO listCandidatosDadosExperienciasPaginada(Integer pagina, Integer quantidade) {
        Pageable pageable = PageRequest.of(pagina, quantidade);
        Page<Candidato> paginacao = candidatoRepository.findAll(pageable);
        return new CandidatoDadosExperienciasPaginadaDTO(
                paginacao.getContent()
                        .stream()
                        .map(this::setCandidatoDadosExperienciasDTO)
                        .collect(Collectors.toList()),
                paginacao.getTotalElements(),
                paginacao.getTotalPages(),
                paginacao.getPageable().getPageNumber(),
                paginacao.getPageable().getPageSize()
        );
    }
}
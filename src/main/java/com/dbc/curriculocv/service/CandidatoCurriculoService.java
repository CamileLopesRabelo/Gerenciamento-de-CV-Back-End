package com.dbc.curriculocv.service;

//import com.dbc.curriculocv.entity.Candidato;
//import com.dbc.curriculocv.entity.Curriculo;
//import com.dbc.curriculocv.exceptions.RegraDeNegocioException;
//import com.dbc.curriculocv.repository.CandidatoRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class CandidatoCurriculoService {
//    private final CandidatoRepository candidatoRepository;
//    private final Candidato candidato;
//    private final CandidatoService candidatoService;
//    private final Curriculo curriculo;
//
//
//    public void VincularFileAoCandidato(MultipartFile file, Integer idCandidato) throws RegraDeNegocioException {
//
//        Optional<Candidato> byId = candidatoRepository.findById(idCandidato);
//        if (byId==null) {
//        throw  new RegraDeNegocioException("Candidato não encontrado");
//        } else {
//
//            candidato.setCurriculo(curriculo.getIdCurriculo());
//        }
//
//    }
//}

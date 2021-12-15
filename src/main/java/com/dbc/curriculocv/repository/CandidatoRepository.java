package com.dbc.curriculocv.repository;

import com.dbc.curriculocv.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Integer> {
    boolean existsByCpf(String cpf);
}

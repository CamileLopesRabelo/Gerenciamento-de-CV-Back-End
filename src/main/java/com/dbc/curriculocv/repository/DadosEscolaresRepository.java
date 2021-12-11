package com.dbc.curriculocv.repository;

import com.dbc.curriculocv.entity.DadosEscolares;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosEscolaresRepository extends JpaRepository<DadosEscolares, Integer> {
}

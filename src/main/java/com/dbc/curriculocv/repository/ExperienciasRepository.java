package com.dbc.curriculocv.repository;

import com.dbc.curriculocv.entity.Experiencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienciasRepository extends JpaRepository<Experiencias, Integer> {
}

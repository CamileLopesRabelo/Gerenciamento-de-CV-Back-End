package com.dbc.curriculocv.repository;

import com.dbc.curriculocv.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Integer> {
    boolean existsById(Integer id);
}

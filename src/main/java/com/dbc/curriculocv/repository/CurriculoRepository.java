package com.dbc.curriculocv.repository;

import com.dbc.curriculocv.entity.Curriculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculoRepository extends JpaRepository<Curriculo, Integer> {

    @Query(value = "select * from curriculo c where fk_candidato = :idCandidato",nativeQuery = true)
    Curriculo getCurriculoByIdCandidato(Integer idCandidato);
}

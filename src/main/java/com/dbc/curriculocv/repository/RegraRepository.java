package com.dbc.curriculocv.repository;


import com.dbc.curriculocv.entity.Regra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegraRepository extends JpaRepository<Regra, Integer> {

}
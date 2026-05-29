package com.centrotreinamento.bjj.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centrotreinamento.bjj.domain.Treino;

public interface TreinoRepository extends JpaRepository<Treino, UUID> {
    
}

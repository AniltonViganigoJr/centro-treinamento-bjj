package com.centrotreinamento.bjj.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centrotreinamento.bjj.domain.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
    
}

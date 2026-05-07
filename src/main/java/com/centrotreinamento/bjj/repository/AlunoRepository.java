package com.centrotreinamento.bjj.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.domain.enums.Faixa;

public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
    
    Optional<Aluno> findByEmail(String email);

    List<Aluno> findByFaixa(Faixa faixa);

    List<Aluno> findByAtivoTrue();
}

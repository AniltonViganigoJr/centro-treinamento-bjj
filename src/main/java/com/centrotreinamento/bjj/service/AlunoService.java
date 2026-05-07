package com.centrotreinamento.bjj.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.domain.enums.Faixa;
import com.centrotreinamento.bjj.exception.AlunoNaoEncontradoException;
import com.centrotreinamento.bjj.repository.AlunoRepository;

@Service
public class AlunoService {
    
    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno cadastrar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> listar() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(UUID id) {
        return alunoRepository.findById(id).
               orElseThrow(AlunoNaoEncontradoException::new); 
    }

    public Aluno adicionarGrau(UUID id) {
        Aluno aluno = buscarPorId(id);
        aluno.adicionarGrau();
        return alunoRepository.save(aluno);
    }

    public Aluno graduarFaixa(UUID id, String faixa) {
        if (faixa == null || faixa.isBlank()) {
            throw new IllegalArgumentException("Faixa inválida");
        }

        Aluno aluno = buscarPorId(id);
        Faixa novaFaixa = Faixa.converter(faixa);
        aluno.graduarFaixa(novaFaixa);
        return alunoRepository.save(aluno);
    }

    public void deletar(UUID id) {
        Aluno aluno = buscarPorId(id);
        alunoRepository.delete(aluno);
    }
}

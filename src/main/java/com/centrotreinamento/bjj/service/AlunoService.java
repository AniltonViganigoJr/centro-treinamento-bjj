package com.centrotreinamento.bjj.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.domain.enums.Faixa;
import com.centrotreinamento.bjj.dto.response.AlunoResponseDTO;
import com.centrotreinamento.bjj.exception.AlunoNaoEncontradoException;
import com.centrotreinamento.bjj.mapper.AlunoMapper;
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

    public List<AlunoResponseDTO> listar() {
        return alunoRepository.findAll()
                .stream()
                .map(AlunoMapper::toResponseDTO)
                .toList();
    }

    public AlunoResponseDTO buscarPorId(UUID id) {
        return AlunoMapper.toResponseDTO(buscarEntidadePorId(id));
    }

    public Aluno adicionarGrau(UUID id) {
        Aluno aluno = buscarEntidadePorId(id);
        aluno.adicionarGrau();
        return alunoRepository.save(aluno);
    }

    public Aluno graduarFaixa(UUID id, String faixa) {
        if (faixa == null || faixa.isBlank()) {
            throw new IllegalArgumentException("Faixa inválida");
        }

        Aluno aluno = buscarEntidadePorId(id);
        Faixa novaFaixa = Faixa.converter(faixa);
        aluno.graduarFaixa(novaFaixa);
        return alunoRepository.save(aluno);
    }

    public void deletar(UUID id) {
        Aluno aluno = buscarEntidadePorId(id);
        alunoRepository.delete(aluno);
    }

    private Aluno buscarEntidadePorId(UUID id) {
        return alunoRepository.findById(id)
                .orElseThrow(AlunoNaoEncontradoException::new);
    }
}

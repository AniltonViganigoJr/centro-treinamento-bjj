package com.centrotreinamento.bjj.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.domain.Treino;
import com.centrotreinamento.bjj.dto.request.TreinoRequestDTO;
import com.centrotreinamento.bjj.dto.response.TreinoResponseDTO;
import com.centrotreinamento.bjj.mapper.TreinoMapper;
import com.centrotreinamento.bjj.repository.AlunoRepository;
import com.centrotreinamento.bjj.repository.TreinoRepository;

@Service
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final AlunoRepository alunoRepository;

    public TreinoService(TreinoRepository treinoRepository, AlunoRepository alunoRepository) {
        this.treinoRepository = treinoRepository;
        this.alunoRepository = alunoRepository;
    }

    public Treino criarTreino(TreinoRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        Treino treino = new Treino(
                dto.data(),
                dto.tipoTreino(),
                dto.duracao(),
                dto.descricao(),
                aluno);

        aluno.adicionarTreino(treino);

        return treinoRepository.save(treino);
    }

    public List<TreinoResponseDTO> listarTreinos() {
        return treinoRepository.findAll()
                .stream()
                .map(TreinoMapper::toResponseDTO)
                .toList();
    }

    public TreinoResponseDTO buscarPorId(UUID id) {
        return TreinoMapper.toResponseDTO(buscarEntidadePorId(id));
    }

    public void removerTreino(UUID id) {
        Treino treino = buscarEntidadePorId(id);
        treino.getAluno().removerTreino(treino);
        treinoRepository.delete(treino);
        
    }

    private Treino buscarEntidadePorId(UUID id) {
        return treinoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treino não encontrado"));
    }
}

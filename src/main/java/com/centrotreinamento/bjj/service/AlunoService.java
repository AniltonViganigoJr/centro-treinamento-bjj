package com.centrotreinamento.bjj.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.domain.enums.Faixa;
import com.centrotreinamento.bjj.dto.request.AlunoRequestDTO;
import com.centrotreinamento.bjj.dto.response.AlunoMetricasDTO;
import com.centrotreinamento.bjj.dto.response.AlunoResponseDTO;
import com.centrotreinamento.bjj.exception.AlunoNaoEncontradoException;
import com.centrotreinamento.bjj.integration.TreinoMetricasClient;
import com.centrotreinamento.bjj.mapper.AlunoMapper;
import com.centrotreinamento.bjj.repository.AlunoRepository;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    
    private final TreinoMetricasClient treinoMetricasClient;

    public AlunoService(
            AlunoRepository alunoRepository,
            TreinoMetricasClient treinoMetricasClient) {
        this.alunoRepository = alunoRepository;
        this.treinoMetricasClient = treinoMetricasClient;
    }

    public AlunoResponseDTO cadastrar(AlunoRequestDTO dto) {
        Aluno aluno = AlunoMapper.toEntity(dto);
        Aluno alunoSalvo = alunoRepository.save(aluno);
        return buscarPorId(alunoSalvo.getId());
    }

    public List<AlunoResponseDTO> listar() {
        return alunoRepository.findAll()
                .stream()
                .map(AlunoMapper::toResponseDTO)
                .map(this::enriquecerMetricas)
                .toList();
    }

    public AlunoResponseDTO buscarPorId(UUID id) {
        return enriquecerMetricas(AlunoMapper.toResponseDTO(buscarEntidadePorId(id)));
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

    private AlunoResponseDTO enriquecerMetricas(AlunoResponseDTO alunoResponseDTO) {
        AlunoMetricasDTO metricas = treinoMetricasClient.obterMetricas(alunoResponseDTO.id());
        return new AlunoResponseDTO(
            alunoResponseDTO.id(),
            alunoResponseDTO.nome(),
            alunoResponseDTO.email(),
            alunoResponseDTO.telefone(),
            alunoResponseDTO.dataMatricula(),
            alunoResponseDTO.ativo(),
            alunoResponseDTO.faixa(),
            alunoResponseDTO.graus(), 
            metricas);
    }
}

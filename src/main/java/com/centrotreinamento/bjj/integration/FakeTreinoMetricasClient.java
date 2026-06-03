package com.centrotreinamento.bjj.integration;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.dto.response.AlunoMetricasDTO;
import com.centrotreinamento.bjj.exception.AlunoNaoEncontradoException;
import com.centrotreinamento.bjj.repository.AlunoRepository;

@Component
public class FakeTreinoMetricasClient implements TreinoMetricasClient {

    private final AlunoRepository alunoRepository;

    public FakeTreinoMetricasClient(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public AlunoMetricasDTO obterMetricas(UUID alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(AlunoNaoEncontradoException::new);

        LocalDate hoje = LocalDate.now();

        return new AlunoMetricasDTO(
            aluno.obterTotalTreinosRealizados(),
            aluno.obterQuantidadeTreinosNoMes(hoje.getMonthValue(),
            hoje.getYear()),
            aluno.obterQuantidadeTreinosSemanal(hoje));
    }

}

package com.centrotreinamento.bjj.mapper;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.dto.request.AlunoRequestDTO;
import com.centrotreinamento.bjj.dto.response.AlunoResponseDTO;

public class AlunoMapper {
    
    private AlunoMapper(){}

    public static Aluno toEntity(AlunoRequestDTO dto) {

        return new Aluno(
            dto.nome(),
            dto.idade(),
            dto.email(),
            dto.telefone()
        );
    }

    public static AlunoResponseDTO toResponseDTO(Aluno aluno) {
        return new AlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getDataMatricula(),
                aluno.isAtivo(),
                aluno.getFaixa(),
                aluno.getGraus(),
                null
            );
    }
}

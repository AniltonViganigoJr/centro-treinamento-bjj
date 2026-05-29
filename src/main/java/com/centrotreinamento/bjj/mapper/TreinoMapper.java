package com.centrotreinamento.bjj.mapper;

import com.centrotreinamento.bjj.domain.Treino;
import com.centrotreinamento.bjj.dto.response.TreinoResponseDTO;

public class TreinoMapper {

    public static TreinoResponseDTO toResponseDTO(Treino treino) {

        return new TreinoResponseDTO(
                treino.getId(), 
                treino.getData(), 
                treino.getTipoTreino(), 
                treino.getDuracao(),
                treino.getDescricao(), 
                treino.getAluno().getId());
    }
}

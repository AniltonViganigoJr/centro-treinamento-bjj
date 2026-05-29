package com.centrotreinamento.bjj.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.centrotreinamento.bjj.domain.enums.TipoTreino;

public record TreinoResponseDTO(

    UUID id,
    LocalDate data,
    TipoTreino tipoTreino,
    int duracao,
    String descricao,
    UUID alunoId

) {}

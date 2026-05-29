package com.centrotreinamento.bjj.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import com.centrotreinamento.bjj.domain.enums.TipoTreino;

public record TreinoRequestDTO (
    LocalDate data,
    TipoTreino tipoTreino,
    int duracao,
    String descricao,
    UUID alunoId
){}

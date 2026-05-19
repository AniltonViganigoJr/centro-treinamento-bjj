package com.centrotreinamento.bjj.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.centrotreinamento.bjj.domain.enums.Faixa;

public record AlunoResponseDTO(
    UUID id,
    String nome,
    String email,
    String telefone,
    LocalDate dataMatricula,
    boolean ativo,
    Faixa faixa,
    int graus
) {}

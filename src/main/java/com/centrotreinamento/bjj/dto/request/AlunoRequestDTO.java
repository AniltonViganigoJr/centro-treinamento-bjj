package com.centrotreinamento.bjj.dto.request;

import java.util.UUID;

import com.centrotreinamento.bjj.domain.enums.Faixa;

public record AlunoRequestDTO(
    UUID id,
    String nome,
    int idade,
    String email,
    String telefone,
    Faixa faixa,
    int graus
) {}

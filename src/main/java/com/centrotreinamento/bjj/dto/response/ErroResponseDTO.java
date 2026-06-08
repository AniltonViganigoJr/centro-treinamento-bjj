package com.centrotreinamento.bjj.dto.response;

import java.time.LocalDateTime;

public record ErroResponseDTO (
    int status,
    String mensagem,
    LocalDateTime timestamp
) {}

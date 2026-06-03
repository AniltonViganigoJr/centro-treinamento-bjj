package com.centrotreinamento.bjj.integration;

import java.util.UUID;

import com.centrotreinamento.bjj.dto.response.AlunoMetricasDTO;

public interface TreinoMetricasClient {
    AlunoMetricasDTO obterMetricas(UUID alunoId);
}

package com.centrotreinamento.bjj.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.centrotreinamento.bjj.domain.enums.Faixa;
import com.centrotreinamento.bjj.dto.response.AlunoMetricasDTO;
import com.centrotreinamento.bjj.dto.response.AlunoResponseDTO;
import com.centrotreinamento.bjj.service.AlunoService;

@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlunoService alunoService;


    private UUID gerarId() {
        return UUID.randomUUID();
    }

    private AlunoResponseDTO instanciarAlunoDTO() {
        AlunoMetricasDTO metricas = new AlunoMetricasDTO(
            100, 
            15, 
            2);

        return new AlunoResponseDTO(
            gerarId(), 
            "Aluno Mock Test", 
            "alunomocktest@centrotreinamentobjj.com", 
            "51999999999",
            LocalDate.of(2026, 5, 4),
            true, 
            Faixa.BRANCA, 
            0,
            metricas);
    }

    @Test
    void deveListarAlunos() throws Exception {

        AlunoResponseDTO aluno = instanciarAlunoDTO();

        when(alunoService.listar()).thenReturn(List.of(aluno));

        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(aluno.id().toString()))
                .andExpect(jsonPath("$[0].nome").value(aluno.nome()))
                .andExpect(jsonPath("$[0].email").value(aluno.email()))
                .andExpect(jsonPath("$[0].telefone").value(aluno.telefone()))
                .andExpect(jsonPath("$[0].ativo").value(aluno.ativo()))
                .andExpect(jsonPath("$[0].dataMatricula").value(aluno.dataMatricula().toString()))
                .andExpect(jsonPath("$[0].faixa").value(aluno.faixa().toString().toUpperCase()))
                .andExpect(jsonPath("$[0].graus").value(aluno.graus()))
                .andExpect(jsonPath("$[0].metricas.totalTreinos").value(aluno.metricas().totalTreinos()))
                .andExpect(jsonPath("$[0].metricas.totalTreinosMensal").value(aluno.metricas().totalTreinosMensal()))
                .andExpect(jsonPath("$[0].metricas.totalTreinosSemanal").value(aluno.metricas().totalTreinosSemanal()));
    }
    
}

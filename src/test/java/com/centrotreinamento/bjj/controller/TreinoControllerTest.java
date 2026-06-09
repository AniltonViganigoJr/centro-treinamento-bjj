package com.centrotreinamento.bjj.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.domain.Treino;
import com.centrotreinamento.bjj.domain.enums.TipoTreino;
import com.centrotreinamento.bjj.dto.request.TreinoRequestDTO;
import com.centrotreinamento.bjj.dto.response.TreinoResponseDTO;
import com.centrotreinamento.bjj.security.CustomUserDetailsService;
import com.centrotreinamento.bjj.security.JwtService;
import com.centrotreinamento.bjj.service.TreinoService;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TreinoController.class)
public class TreinoControllerTest {

    private Aluno criarAlunoValido() {

        return new Aluno(
                "Aluno Mock",
                18,
                "alunomock@mocktest.com.br",
                "51999655541");
    }

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;
    
    @MockitoBean
    private TreinoService treinoService;

    private TreinoResponseDTO instanciarTreinoResponseDTO() {
        Aluno aluno = criarAlunoValido();
        LocalDate hoje = LocalDate.now();
        return new TreinoResponseDTO(
                UUID.randomUUID(),
                hoje,
                TipoTreino.GI,
                90,
                "Treino de Lapela",
                aluno.getId());

    }

    @Test
    void deveListarTreinos() throws Exception {
        TreinoResponseDTO treinoResponseDTO = instanciarTreinoResponseDTO();
        when(treinoService.listarTreinos()).thenReturn(List.of(treinoResponseDTO));
        mockMvc.perform(get("/treinos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].descricao").value(treinoResponseDTO.descricao()));
    }

    @Test
    void deveBuscarTreinoPorId() throws Exception {
        TreinoResponseDTO treinoResponseDTO = instanciarTreinoResponseDTO();

        when(treinoService.buscarPorId(treinoResponseDTO.id())).thenReturn(treinoResponseDTO);

        mockMvc.perform(get("/treinos/{id}", treinoResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(treinoResponseDTO.id().toString()))
                .andExpect(jsonPath("$.descricao")
                        .value(treinoResponseDTO.descricao()))
                .andExpect(jsonPath("$.tipoTreino")
                        .value(treinoResponseDTO.tipoTreino().toString()));

    }

    @Test
    void deveCriarTreino() throws Exception {
        LocalDate hoje = LocalDate.now();
        TreinoRequestDTO treinoRequestDTO = new TreinoRequestDTO(
                hoje,
                TipoTreino.NOGI,
                90,
                "Chave de calcanhar",
                UUID.randomUUID());

        Treino treino = new Treino(
                treinoRequestDTO.data(),
                treinoRequestDTO.tipoTreino(),
                treinoRequestDTO.duracao(),
                treinoRequestDTO.descricao(),
                criarAlunoValido());

        when(treinoService.criarTreino(treinoRequestDTO)).thenReturn(treino);

        mockMvc.perform(post("/treinos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "data":"%s",
                            "tipoTreino":"NOGI",
                            "duracao":90,
                            "descricao":"Chave de calcanhar",
                            "alunoId":"%s"
                        }
                        """.formatted(
                        hoje,
                        treinoRequestDTO.alunoId())))
                .andExpect(status().isCreated());

    }

    @Test
    void deveRemoverTreino() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/treinos/{id}", id))
                .andExpect(status().isNoContent());

        verify(treinoService).removerTreino(id);
    }
}

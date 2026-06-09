package com.centrotreinamento.bjj.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.domain.Treino;
import com.centrotreinamento.bjj.domain.enums.TipoTreino;
import com.centrotreinamento.bjj.dto.request.TreinoRequestDTO;
import com.centrotreinamento.bjj.dto.response.TreinoResponseDTO;
import com.centrotreinamento.bjj.repository.AlunoRepository;
import com.centrotreinamento.bjj.repository.TreinoRepository;

@ExtendWith(MockitoExtension.class)
public class TreinoServiceTest {

    private Aluno criarAlunoValido() {

        return new Aluno(
                "Aluno Mock",
                18,
                "alunomock@mocktest.com.br",
                "51999655541");
    }

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private TreinoRepository treinoRepository;

    @InjectMocks
    private TreinoService treinoService;

    @Test
    void deveCriarTreino() {
        Aluno aluno = criarAlunoValido();
        LocalDate hoje = LocalDate.now();
        TreinoRequestDTO treinoRequestDTO = new TreinoRequestDTO(
                hoje,
                TipoTreino.GI,
                90,
                "Drills de Passagem de Guarda",
                aluno.getId());

        when(alunoRepository.findById(aluno.getId()))
                .thenReturn(Optional.of(aluno));

        when(treinoRepository.save(any(Treino.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Treino treino = treinoService.criarTreino(treinoRequestDTO);

        assertNotNull(treino);

        assertEquals(treinoRequestDTO.data(), treino.getData());
        assertEquals(treinoRequestDTO.tipoTreino(), treino.getTipoTreino());
        assertEquals(treinoRequestDTO.duracao(), treino.getDuracao());
        assertEquals(treinoRequestDTO.descricao(), treino.getDescricao());
        assertEquals(treinoRequestDTO.alunoId(), treino.getAluno().getId());
        assertEquals(aluno, treino.getAluno());

        verify(treinoRepository).save(any(Treino.class));
    }

    @Test
    void deveListarTreinos() {
        Aluno aluno = criarAlunoValido();
        LocalDate hoje = LocalDate.now();

        Treino treino1 = new Treino(
                hoje,
                TipoTreino.GI,
                90,
                "Drills de Passagem de Guarda",
                aluno);

        Treino treino2 = new Treino(
                hoje.minusDays(1),
                TipoTreino.NOGI,
                90,
                "Treino de Leg lock",
                aluno);

        when(treinoRepository.findAll())
                .thenReturn(List.of(treino1, treino2));

        List<TreinoResponseDTO> treinos = treinoService.listarTreinos();

        assertEquals(2, treinos.size());
        assertEquals(treino1.getDescricao(), treinos.get(0).descricao());
        assertEquals(treino2.getDescricao(), treinos.get(1).descricao());

        verify(treinoRepository).findAll();
    }

    @Test
    void deveBuscarTreinoPorId() {
        Aluno aluno = criarAlunoValido();
        LocalDate hoje = LocalDate.now();

        Treino treino = new Treino(
                hoje,
                TipoTreino.GI,
                90,
                "Drills de Passagem de Guarda",
                aluno);

        when(treinoRepository.findById(treino.getId()))
                .thenReturn(Optional.of(treino));

        TreinoResponseDTO response = treinoService.buscarPorId(treino.getId());

        assertNotNull(response);
        assertEquals(treino.getDescricao(), response.descricao());
        verify(treinoRepository).findById(treino.getId());

    }

    @Test
    void deveRemoverTreino() {
        Aluno aluno = criarAlunoValido();
        LocalDate hoje = LocalDate.now();

        Treino treino = new Treino(
                hoje,
                TipoTreino.GI,
                90,
                "Drills de Passagem de Guarda",
                aluno);

        when(treinoRepository.findById(treino.getId()))
                .thenReturn(Optional.of(treino));
        
        treinoService.removerTreino(treino.getId());

        verify(treinoRepository).delete(treino);

    }
}

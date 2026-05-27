package com.centrotreinamento.bjj.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import com.centrotreinamento.bjj.domain.enums.TipoTreino;

public class TreinoTest {

    private static final String DESCRICAO = "Treino sobre Guarda X";
    private static final int DURACAO = 90;

    private Aluno criarAlunoValido() {
        return new Aluno(
                "Aluno Mock",
                18,
                "alunomock@mocktest.com.br",
                "51999655541");
    }

    private Treino criarTreino() {
        return new Treino(
                LocalDate.now(),
                TipoTreino.GI,
                DURACAO,
                DESCRICAO,
                criarAlunoValido());
    }

    @Test
    void deveCriarTreinoComDadosValidos() {
        Treino treino = criarTreino();

        assertEquals(TipoTreino.GI, treino.getTipoTreino());
        assertEquals(DESCRICAO, treino.getDescricao());
        assertEquals(DURACAO, treino.getDuracao());
        assertNotNull(treino.getAluno());
    }

    @Test
    void naoDeveCriarTreinoComDataFutura() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Treino(
                        LocalDate.now().plusDays(1),
                        TipoTreino.GI,
                        DURACAO,
                        DESCRICAO,
                        criarAlunoValido()));

        assertEquals("Data inválida", exception.getMessage());
    }

    @Test
    void naoDeveCriarTreinoComDescricaoVazia() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Treino(
                        LocalDate.now(),
                        TipoTreino.GI,
                        DURACAO,
                        "",
                        criarAlunoValido()));

        assertEquals("Descrição é obrigatória", exception.getMessage());
    }

    @Test
    void naoDeveCriarTreinoComDescricaoEmBranco() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Treino(
                        LocalDate.now(),
                        TipoTreino.GI,
                        DURACAO,
                        "     ",
                        criarAlunoValido()));

        assertEquals("Descrição é obrigatória", exception.getMessage());
    }

    @Test
    void deveAtualizarDescricao() {
        Treino treino = criarTreino();

        treino.atualizarDescricao("Guarda aberta");
        assertEquals("Guarda aberta", treino.getDescricao());
    }

    @Test
    void naoDeveAtualizarDescricaoComValorInvalido() {
        Treino treino = criarTreino();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> treino.atualizarDescricao(null));
        assertEquals("Descrição é obrigatória", exception.getMessage());
    }

    @Test
    void naoDeveAtualizarDescricaoComValorEmBranco() {
        Treino treino = criarTreino();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> treino.atualizarDescricao("     "));
        assertEquals("Descrição é obrigatória", exception.getMessage());
    }

    @Test
    void naoDeveCriarTreinoComAlunoNulo() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Treino(
                        LocalDate.now(),
                        TipoTreino.GI,
                        DURACAO,
                        DESCRICAO,
                        null));
        assertEquals("Aluno é obrigatório", exception.getMessage());
    }

    @Test
    void naoDeveCriarTreinoComTipoTreinoNulo() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Treino(
                        LocalDate.now(),
                        null,
                        DURACAO,
                        DESCRICAO,
                        criarAlunoValido()));
        assertEquals("Tipo de Treino é obrigatório", exception.getMessage());
    }

    @Test
    void naoDeveCriarTreinoComDuracaoIgualZero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Treino(
                        LocalDate.now(),
                        TipoTreino.NOGI,
                        0,
                        DESCRICAO,
                        criarAlunoValido()));
        assertEquals("Duração inválida", exception.getMessage());
    }

    @Test
    void naoDeveCriarTreinoComDuracaoMenorQueZero() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Treino(
                        LocalDate.now(),
                        TipoTreino.NOGI,
                        -90,
                        DESCRICAO,
                        criarAlunoValido()));
        assertEquals("Duração inválida", exception.getMessage());
    }

    @Test
    void naoDeveAlterarDuracaoComValorInvalido() {
        Treino treino = criarTreino();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> treino.alterarDuracao(-20));
        assertEquals("Duração inválida", exception.getMessage());
    }

    @Test
    void deveAlterarDuracao() {
        Treino treino = criarTreino();

        treino.alterarDuracao(120);
        assertEquals(120, treino.getDuracao());
    }
}

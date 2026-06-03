package com.centrotreinamento.bjj.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import com.centrotreinamento.bjj.domain.enums.Faixa;
import com.centrotreinamento.bjj.domain.enums.TipoTreino;

public class AlunoTest {

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

    private List<Treino> criarListaTreinos() {
        List<Treino> treinos = new ArrayList<>();

        LocalDate hoje = LocalDate.now();

        Treino treino1 = new Treino(
                hoje,
                TipoTreino.GI,
                DURACAO,
                DESCRICAO,
                criarAlunoValido());

        Treino treino2 = new Treino(
                hoje,
                TipoTreino.GI,
                DURACAO,
                "Passagem de guarda",
                criarAlunoValido());

        Treino treino3 = new Treino(
                hoje,
                TipoTreino.GI,
                DURACAO,
                "Pontuações",
                criarAlunoValido());

        treinos.add(treino1);
        treinos.add(treino2);
        treinos.add(treino3);

        return treinos;

    }

    private void adicionarQuatroGraus(Aluno aluno) {
        aluno.adicionarGrau();
        aluno.adicionarGrau();
        aluno.adicionarGrau();
        aluno.adicionarGrau();
    }

    @Test
    void deveAdicionarTreino() {

        Aluno aluno = criarAlunoValido();

        Treino treino = criarTreino();

        aluno.adicionarTreino(treino);

        assertEquals(1, aluno.getTreinos().size());
        assertTrue(aluno.getTreinos().contains(treino));
    }

    @Test
    void deveRemoverTreino() {

        Aluno aluno = criarAlunoValido();

        Treino treino = criarTreino();

        aluno.adicionarTreino(treino);
        aluno.removerTreino(treino);

        assertEquals(0, aluno.getTreinos().size());
        assertFalse(aluno.getTreinos().contains(treino));
    }

    @Test
    void deveRetornarQuantidadeDeTreinosNoMes() {

        Aluno aluno = criarAlunoValido();

        List<Treino> treinos = criarListaTreinos();

        treinos.forEach(t -> aluno.adicionarTreino(t));

        LocalDate hoje = LocalDate.now();

        int quantidade = aluno.obterQuantidadeTreinosNoMes(
                hoje.getMonthValue(),
                hoje.getYear());

        assertEquals(3, quantidade);
    }

    @Test
    void deveRetornarQuantidadeDeTreinosSemanal() {
        Aluno aluno = criarAlunoValido();
        LocalDate hoje = LocalDate.now();
        Treino treino1 = new Treino(
                hoje.minusDays(2),
                TipoTreino.GI,
                DURACAO,
                "Reposição de guarda",
                aluno);

        Treino treino2 = new Treino(
                hoje.minusDays(1),
                TipoTreino.GI,
                DURACAO,
                "Reposição de guarda",
                aluno);

        Treino treino3 = new Treino(
                hoje.minusWeeks(1),
                TipoTreino.GI,
                DURACAO,
                "Reposição de guarda",
                aluno);

        Treino treino4 = new Treino(
                hoje,
                TipoTreino.GI,
                DURACAO,
                "Reposição de guarda",
                aluno);

        aluno.adicionarTreino(treino1);
        aluno.adicionarTreino(treino2);
        aluno.adicionarTreino(treino3);
        aluno.adicionarTreino(treino4);

        int quantidade = aluno.obterQuantidadeTreinosSemanal(hoje);

        assertEquals(2, quantidade);
    }

    @Test
    void deveRetornarTotalTreinosRealizados() {
        Aluno aluno = criarAlunoValido();

        List<Treino> treinos = criarListaTreinos();

        LocalDate hoje = LocalDate.now();

        Treino treino4 = new Treino(
                hoje.minusYears(2),
                TipoTreino.GI,
                DURACAO,
                "Reposição de guarda",
                aluno);

        treinos.add(treino4);

        treinos.forEach(t -> aluno.adicionarTreino(t));

        int quantidade = aluno.obterTotalTreinosRealizados();

        assertEquals(4, quantidade);
    }

    @Test
    void naoDeveAdicionarTreinoNulo() {

        Aluno aluno = criarAlunoValido();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> aluno.adicionarTreino(null));
        assertEquals("Treino é obrigatório", exception.getMessage());
    }

    @Test
    void naoDeveRemoverTreinoNulo() {

        Aluno aluno = criarAlunoValido();

        Treino treino = criarTreino();

        aluno.adicionarTreino(treino);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> aluno.removerTreino(null));
        assertEquals("Treino é obrigatório", exception.getMessage());
    }

    @Test
    void deveGraduarAlunoQuandoRegrasForemValidadas() {

        Aluno aluno = criarAlunoValido();

        adicionarQuatroGraus(aluno);

        aluno.graduarFaixa(Faixa.AZUL);

        assertEquals(Faixa.AZUL, aluno.getFaixa());
    }

    @Test
    void naoDevePularFaixa() {

        Aluno aluno = criarAlunoValido();

        adicionarQuatroGraus(aluno);

        assertThrows(IllegalStateException.class, () -> aluno.graduarFaixa(Faixa.ROXA));
    }

    @Test
    void naoDeveAdicionarGrauAlunoInativo() {

        Aluno aluno = criarAlunoValido();

        aluno.desativar();

        assertThrows(IllegalStateException.class, () -> aluno.adicionarGrau());
    }

    @Test
    void naoDeveAceitarFaixaNula() {

        Aluno aluno = criarAlunoValido();

        adicionarQuatroGraus(aluno);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aluno.graduarFaixa(null));

        assertEquals("Nova faixa não pode ser nula", exception.getMessage());
    }

    @Test
    void naoDeveAceitarNomeNulo() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Aluno(
                        null,
                        18,
                        "alunomock@mocktest.com.br",
                        "51999655541"));

        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    void naoDeveAceitarEmailNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Aluno("Aluno Mock", 20, null, "51999998877"));

        assertEquals("E-mail é obrigatório", exception.getMessage());
    }

    @Test
    void naoDeveAceitarIdadeMenorQuatroAnos() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Aluno("Aluno Mock", 0, "alunomock@mocktest.com.br", "51999998877"));

        assertEquals("Idade inválida", exception.getMessage());
    }

    @Test
    void naoDeveAceitarMaisQuatroGraus() {

        Aluno aluno = criarAlunoValido();

        adicionarQuatroGraus(aluno);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aluno.adicionarGrau());

        assertEquals("Aluno não pode ultrapassar 4 graus na faixa atual", exception.getMessage());
    }

    @Test
    void deveResetarGrausAoGraduar() {

        Aluno aluno = criarAlunoValido();

        adicionarQuatroGraus(aluno);

        aluno.graduarFaixa(Faixa.AZUL);

        assertEquals(0, aluno.getGraus());
    }

    @Test
    void naoDeveGraduarSemQuatroGraus() {

        Aluno aluno = criarAlunoValido();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> aluno.graduarFaixa(Faixa.AZUL));

        assertEquals("Aluno precisa ter 4 graus para mudar de faixa", exception.getMessage());
    }

    @Test
    void naoDeveGraduarParaMesmaFaixa() {

        Aluno aluno = criarAlunoValido();

        adicionarQuatroGraus(aluno);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> aluno.graduarFaixa(Faixa.BRANCA));

        assertEquals("Aluno já está nessa faixa", exception.getMessage());
    }

    @Test
    void naoDeveGraduarAlunoMenorDeIdadeParaFaixaAzul() {

        Aluno aluno = new Aluno("Aluno Mock", 15, "alunomock@mocktest.com.br", "51998887715");

        adicionarQuatroGraus(aluno);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> aluno.graduarFaixa(Faixa.AZUL));

        assertEquals("Aluno não possui idade mínima para essa graduação", exception.getMessage());
    }
}

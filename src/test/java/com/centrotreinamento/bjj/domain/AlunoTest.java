package com.centrotreinamento.bjj.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.centrotreinamento.bjj.domain.enums.Faixa;

public class AlunoTest {

    private Aluno criarAlunoValido() {
        return new Aluno(
            "Aluno Mock", 
            18, 
            "alunomock@mocktest.com.br", 
            "51999655541");
    }

    private void adicionarQuatroGraus(Aluno aluno) {
        aluno.adicionarGrau();
        aluno.adicionarGrau();
        aluno.adicionarGrau();
        aluno.adicionarGrau();
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

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> aluno.graduarFaixa(null));
        
        assertEquals("Nova faixa não pode ser nula", exception.getMessage());
    }

    @Test
    void naoDeveAceitarNomeNulo() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> new Aluno(
            null, 
            18, 
            "alunomock@mocktest.com.br", 
            "51999655541")
        );

        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    void naoDeveAceitarEmailNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Aluno("Aluno Mock", 20,null , "51999998877")
        );

        assertEquals("E-mail é obrigatório", exception.getMessage());
    }

    @Test
    void naoDeveAceitarIdadeMenorQuatroAnos() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> new Aluno("Aluno Mock", 0, "alunomock@mocktest.com.br", "51999998877")
        );

        assertEquals("Idade inválida", exception.getMessage());
    }

    @Test
    void naoDeveAceitarMaisQuatroGraus() {
        
        Aluno aluno = criarAlunoValido();

        adicionarQuatroGraus(aluno);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> aluno.adicionarGrau()
        );

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
            () -> aluno.graduarFaixa(Faixa.AZUL)
        );
        
        assertEquals("Aluno precisa ter 4 graus para mudar de faixa", exception.getMessage());
    }
    
    @Test
    void naoDeveGraduarParaMesmaFaixa() {
        
        Aluno aluno = criarAlunoValido();
        
        adicionarQuatroGraus(aluno);
        
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> aluno.graduarFaixa(Faixa.BRANCA)
        );
        
        assertEquals("Aluno já está nessa faixa", exception.getMessage());
    }
    
    @Test
    void naoDeveGraduarAlunoMenorDeIdadeParaFaixaAzul() {
        
        Aluno aluno = new Aluno("Aluno Mock", 15, "alunomock@mocktest.com.br", "51998887715");

        adicionarQuatroGraus(aluno);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> aluno.graduarFaixa(Faixa.AZUL)
        );

        assertEquals("Aluno não possui idade mínima para essa graduação", exception.getMessage());
    }
}

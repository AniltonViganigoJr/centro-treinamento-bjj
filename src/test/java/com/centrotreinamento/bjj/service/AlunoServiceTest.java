package com.centrotreinamento.bjj.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.domain.enums.Faixa;
import com.centrotreinamento.bjj.exception.AlunoNaoEncontradoException;
import com.centrotreinamento.bjj.repository.AlunoRepository;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {
    
    private UUID gerarIdValido() {
        return UUID.randomUUID();
    }

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

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @Test
    void deveCadastrarAluno() {

        Aluno aluno = criarAlunoValido();

        when(alunoRepository.save(any())).thenReturn(aluno);

        Aluno resultado = alunoService.cadastrar(aluno);

        assertEquals(aluno, resultado);
        verify(alunoRepository).save(aluno);
    }

    @Test
    void deveBuscarAlunoPorID() {

        UUID id = gerarIdValido();

        Aluno aluno = criarAlunoValido();

        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        Aluno resultado = alunoService.buscarPorId(id);

        assertEquals(aluno, resultado);
    }

    @Test
    void naoDeveBuscarAlunoInexistente() {
        
        UUID id = gerarIdValido();

        when(alunoRepository.findById(id)).thenReturn(Optional.empty());

        AlunoNaoEncontradoException exception = assertThrows(AlunoNaoEncontradoException.class, 
            () -> alunoService.buscarPorId(id)
        );

        assertEquals("Aluno não encontrado", exception.getMessage());

        verify(alunoRepository).findById(id);
    }

    @Test
    void deveAdicionarGrau() {

        UUID id = gerarIdValido();

        Aluno aluno = criarAlunoValido();
        
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));
        
        when(alunoRepository.save(aluno)).thenReturn(aluno);
        
        Aluno resultado = alunoService.adicionarGrau(id);
        
        assertEquals(1, resultado.getGraus());
        
        verify(alunoRepository).findById(id);
        verify(alunoRepository).save(aluno);
        
    }
    
    @Test
    void deveGraduarFaixa() {
        
        UUID id = gerarIdValido();
    
        Aluno aluno = criarAlunoValido();

        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        adicionarQuatroGraus(aluno);

        when(alunoRepository.save(aluno)).thenReturn(aluno);
        
        Aluno resultado = alunoService.graduarFaixa(id, "Azul");

        assertEquals(Faixa.AZUL, resultado.getFaixa());

        verify(alunoRepository).findById(id);
        verify(alunoRepository).save(aluno);
    }

    @Test
    void naoDeveGraduarFaixaInvalida() {

        UUID id = gerarIdValido();

        Aluno aluno = criarAlunoValido();

        adicionarQuatroGraus(aluno);

        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, 
            () -> alunoService.graduarFaixa(id,"rosa")
        );

        assertEquals("Faixa inválida", exception.getMessage());

        verify(alunoRepository).findById(id);
    }
}

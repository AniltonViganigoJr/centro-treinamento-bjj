package com.centrotreinamento.bjj.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.domain.enums.Faixa;

@DataJpaTest
public class AlunoRepositoryTest {

    private Aluno criarAlunoValido() {
        return new Aluno(
            "Aluno Mock",
            20, 
            "alunomock@alunomock.com.br", 
            "55987452355");
    }
    
    private void adicionarQuatroGraus(Aluno aluno) {
        aluno.adicionarGrau();
        aluno.adicionarGrau();
        aluno.adicionarGrau();
        aluno.adicionarGrau();
    }
    
    @Autowired
    private AlunoRepository alunoRepository;
    
    @Test
    void deveSalvarAluno() {
    
        Aluno aluno = criarAlunoValido();
    
        Aluno alunoSalvo = alunoRepository.save(aluno);

        assertNotNull(alunoSalvo.getId());
    }

    @Test
    void deveBuscarAlunoPorId() {
        
        Aluno aluno = criarAlunoValido();
        Aluno alunoSalvo = alunoRepository.save(aluno);
        
        UUID id = alunoSalvo.getId();

        Optional<Aluno> resultado = alunoRepository.findById(id);

        assertTrue(resultado.isPresent());
        
        Aluno alunoEncontrado = resultado.get();
        
        assertEquals("Aluno Mock", alunoEncontrado.getNome());
    }

    @Test
    void deveListarAlunos() {
        Aluno aluno = criarAlunoValido();
        
        alunoRepository.save(aluno);
        
        List<Aluno> resultado = alunoRepository.findAll();
        assertEquals(1, resultado.size());
        assertFalse(resultado.isEmpty());
        
        Aluno alunoEncontrado = resultado.get(0);
        assertEquals("Aluno Mock", alunoEncontrado.getNome());
    }
    
    @Test
    void deveDeletarAluno() {
        
        Aluno aluno = criarAlunoValido();

        Aluno alunoSalvo  = alunoRepository.save(aluno);

        UUID id = alunoSalvo.getId();
        
        alunoRepository.deleteById(id);

        Optional<Aluno> resultado = alunoRepository.findById(id);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveBuscarAlunoPorEmail() {
        
        Aluno aluno = criarAlunoValido();

        Aluno alunoSalvo = alunoRepository.save(aluno);
        
        Optional<Aluno> resultado = alunoRepository.findByEmail(alunoSalvo.getEmail());
        
        assertTrue(resultado.isPresent());
        
        Aluno alunoEncontrado = resultado.get();
        
        assertEquals("Aluno Mock", alunoEncontrado.getNome());
        assertEquals(alunoSalvo.getEmail(), alunoEncontrado.getEmail());
        assertEquals(alunoSalvo.getId(), alunoEncontrado.getId());
    }
    
    @Test
    void deveListarAlunosAtivos() {
        
        Aluno alunoAtivo = criarAlunoValido();
        
        Aluno alunoInativo = new Aluno(
            "Aluno Mock Inativo",
            30, 
            "alunomockinativo@mock.com.br", 
            "48978456633");
    
        alunoInativo.desativar();

        alunoRepository.save(alunoAtivo);
        alunoRepository.save(alunoInativo);

        List<Aluno> resultado = alunoRepository.findByAtivoTrue();
        
        assertEquals(1, resultado.size());

        Aluno alunoEncontrado = resultado.get(0);

        assertTrue(alunoEncontrado.isAtivo());
        assertEquals(alunoAtivo.getId(), alunoEncontrado.getId());

    }

    @Test
    void deveBuscarAlunosPorFaixa() {

        Aluno alunoIniciante = criarAlunoValido();

        
        Aluno alunoGraduado = new Aluno(
            "Aluno Mock Graduado",
            30, 
            "alunomockgraduado@mock.com.br", 
            "48978456633");
            
        adicionarQuatroGraus(alunoGraduado);
            
        alunoGraduado.graduarFaixa(Faixa.AZUL);
            
        alunoRepository.save(alunoIniciante);
        alunoRepository.save(alunoGraduado);
        
        List<Aluno> resultado = alunoRepository.findByFaixa(Faixa.AZUL);
        assertEquals(1,resultado.size());

        Aluno alunoEncontrado = resultado.get(0);

        assertEquals(alunoGraduado.getId(), alunoEncontrado.getId());
        assertEquals(alunoGraduado.getNome(), alunoEncontrado.getNome());
        assertEquals(alunoGraduado.getFaixa(), alunoEncontrado.getFaixa());
    }

}

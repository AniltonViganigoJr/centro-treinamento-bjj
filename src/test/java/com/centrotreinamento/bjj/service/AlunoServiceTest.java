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
import org.springframework.test.util.ReflectionTestUtils;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.domain.enums.Faixa;
import com.centrotreinamento.bjj.dto.request.AlunoRequestDTO;
import com.centrotreinamento.bjj.dto.response.AlunoMetricasDTO;
import com.centrotreinamento.bjj.dto.response.AlunoResponseDTO;
import com.centrotreinamento.bjj.exception.AlunoNaoEncontradoException;
import com.centrotreinamento.bjj.integration.TreinoMetricasClient;
import com.centrotreinamento.bjj.repository.AlunoRepository;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    private UUID gerarId() {
        return UUID.randomUUID();
    }

    private Aluno criarAlunoValido() {
        Aluno aluno = new Aluno(
                "Aluno Mock",
                18,
                "alunomock@mocktest.com.br",
                "51999655541");
        ReflectionTestUtils.setField(aluno, "id", gerarId());
        return aluno;
    }

    private void adicionarQuatroGraus(Aluno aluno) {
        aluno.adicionarGrau();
        aluno.adicionarGrau();
        aluno.adicionarGrau();
        aluno.adicionarGrau();
    }

    private AlunoMetricasDTO criarMetricas() {
        return new AlunoMetricasDTO(
                150,
                1,
                1);
    }

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @Mock
    private TreinoMetricasClient treinoMetricasClient;

    @Test
    void deveCadastrarAluno() {

        Aluno aluno = criarAlunoValido();

        AlunoRequestDTO dto = new AlunoRequestDTO(aluno.getId(),
                aluno.getNome(),
                aluno.getIdade(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getFaixa(),
                aluno.getGraus());

        when(alunoRepository.save(any(Aluno.class)))
                .thenReturn(aluno);

        when(alunoRepository.findById(aluno.getId()))
                .thenReturn(Optional.of(aluno));

        when(treinoMetricasClient.obterMetricas(any()))
                .thenReturn(new AlunoMetricasDTO(0, 0, 0));

        AlunoResponseDTO resultado = alunoService.cadastrar(dto);

        assertEquals(aluno.getId(), resultado.id());
        verify(alunoRepository).save(any(Aluno.class));
    }

    @Test
    void deveBuscarAlunoPorID() {

        Aluno aluno = criarAlunoValido();

        when(alunoRepository.findById(aluno.getId())).thenReturn(Optional.of(aluno));

        when(treinoMetricasClient.obterMetricas(aluno.getId()))
                .thenReturn(criarMetricas());

        AlunoResponseDTO resultado = alunoService.buscarPorId(aluno.getId());

        assertEquals(aluno.getId(), resultado.id());
        assertEquals(150, resultado.metricas().totalTreinos());

        verify(alunoRepository).findById(aluno.getId());
        verify(treinoMetricasClient).obterMetricas(aluno.getId());
    }

    @Test
    void naoDeveBuscarAlunoInexistente() {

        UUID id = gerarId();

        when(alunoRepository.findById(id)).thenReturn(Optional.empty());

        AlunoNaoEncontradoException exception = assertThrows(AlunoNaoEncontradoException.class,
                () -> alunoService.buscarPorId(id));

        assertEquals("Aluno não encontrado", exception.getMessage());

        verify(alunoRepository).findById(id);
    }

    @Test
    void deveAdicionarGrau() {

        Aluno aluno = criarAlunoValido();

        when(alunoRepository.findById(aluno.getId())).thenReturn(Optional.of(aluno));

        when(alunoRepository.save(aluno)).thenReturn(aluno);

        Aluno resultado = alunoService.adicionarGrau(aluno.getId());

        assertEquals(1, resultado.getGraus());

        verify(alunoRepository).findById(aluno.getId());
        verify(alunoRepository).save(aluno);

    }

    @Test
    void deveGraduarFaixa() {

        Aluno aluno = criarAlunoValido();

        when(alunoRepository.findById(aluno.getId())).thenReturn(Optional.of(aluno));

        adicionarQuatroGraus(aluno);

        when(alunoRepository.save(aluno)).thenReturn(aluno);

        Aluno resultado = alunoService.graduarFaixa(aluno.getId(), "Azul");

        assertEquals(Faixa.AZUL, resultado.getFaixa());

        verify(alunoRepository).findById(aluno.getId());
        verify(alunoRepository).save(aluno);
    }

    @Test
    void naoDeveGraduarFaixaInvalida() {

        Aluno aluno = criarAlunoValido();

        adicionarQuatroGraus(aluno);

        when(alunoRepository.findById(aluno.getId())).thenReturn(Optional.of(aluno));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> alunoService.graduarFaixa(aluno.getId(), "rosa"));

        assertEquals("Faixa inválida", exception.getMessage());

        verify(alunoRepository).findById(aluno.getId());
    }
}

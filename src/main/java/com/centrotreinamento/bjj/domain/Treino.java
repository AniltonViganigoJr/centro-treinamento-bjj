package com.centrotreinamento.bjj.domain;

import java.time.LocalDate;
import java.util.UUID;

import com.centrotreinamento.bjj.domain.enums.TipoTreino;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "treinos")
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private TipoTreino tipoTreino;

    private int duracao;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    public Treino() {
    }

    public Treino(
            LocalDate data,
            TipoTreino tipoTreino,
            int duracao,
            String descricao,
            Aluno aluno) {

        validarData(data);
        validarTipoTreino(tipoTreino);
        validarDuracao(duracao);
        validarDescricao(descricao);
        validarAluno(aluno);

        this.data = data;
        this.tipoTreino = tipoTreino;
        this.duracao = duracao;
        this.descricao = descricao;
        this.aluno = aluno;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoTreino getTipoTreino() {
        return tipoTreino;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Treino other = (Treino) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public void atualizarDescricao(String novaDescricao) {
        validarDescricao(novaDescricao);
        this.descricao = novaDescricao;
    }

    public void alterarDuracao(int novaDuracao) {
        validarDuracao(novaDuracao);
        this.duracao = novaDuracao;
    }

    
    private void validarData(LocalDate data) {
        if (data == null || data.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data inválida");
        }
    }
    
    private void validarTipoTreino(TipoTreino tipoTreino) {
        if (tipoTreino == null) {
            throw new IllegalArgumentException("Tipo de Treino é obrigatório");
        }
    }
    
    private void validarDuracao(int duracao) {
        if (duracao <= 0) {
            throw new IllegalArgumentException("Duração inválida");
        }
    }
    
    private void validarDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }
    }

    private void validarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno é obrigatório");
        }
    }

}

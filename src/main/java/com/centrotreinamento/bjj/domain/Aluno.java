package com.centrotreinamento.bjj.domain;

import java.time.LocalDate;
import java.util.UUID;

import com.centrotreinamento.bjj.domain.enums.Faixa;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Aluno {
    
    @Id
    @GeneratedValue
    private UUID id;
    private String nome;
    private int idade;
    private String email;
    private String telefone;
    
    @Enumerated(EnumType.STRING)
    private Faixa faixa;
    private int graus;
    private LocalDate dataMatricula;
    private boolean ativo;

    public Aluno () {}

    public Aluno(String nome, int idade, String email, String telefone) {
        
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (idade < 4) {
            throw new IllegalArgumentException("Idade inválida");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail é obrigatório");
        }
        
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.telefone = telefone;
        this.faixa = Faixa.BRANCA;
        this.graus = 0;
        this.dataMatricula = LocalDate.now();
        this.ativo = true;
    }

    public void graduarFaixa(Faixa novaFaixa) {
        
        if (novaFaixa == null) {
            throw new IllegalArgumentException("Nova faixa não pode ser nula");
        }
        
        if (!ativo) {
            throw new IllegalStateException("Aluno inativo não pode ser graduado");
        }

        if (this.faixa == novaFaixa) {
            throw new IllegalStateException("Aluno já está nessa faixa");
        }

        if (this.graus < 4) {
            throw new IllegalStateException("Aluno precisa ter 4 graus para mudar de faixa");
        }

        if (!this.faixa.podeAvancarParaProximaFaixa(novaFaixa)) {
            throw new IllegalStateException("Graduação inválida: deve seguir a ordem das faixas");
        }

        if (!novaFaixa.idadePermitida(this.idade)) {
            throw new IllegalArgumentException("Aluno não possui idade mínima para essa graduação");
        }

        this.faixa = novaFaixa;
        this.graus = 0;
    }

    public void adicionarGrau() {
        if (!ativo) {
            throw new IllegalStateException("Aluno inativo não pode ser graduado");
        }

        if (this.graus >= 4) {
            throw new IllegalArgumentException("Aluno não pode ultrapassar 4 graus na faixa atual");
        }

        this.graus++;
    }

    public void desativar() {
        this.ativo = false;
    }
    
    public void ativar() {
        this.ativo = true;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public String getEmail() {
        return email;
    }

    public Faixa getFaixa() {
        return faixa;
    }

    public int getGraus() {
        return graus;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
}

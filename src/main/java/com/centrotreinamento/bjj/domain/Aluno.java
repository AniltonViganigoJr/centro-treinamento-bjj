package com.centrotreinamento.bjj.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.centrotreinamento.bjj.domain.enums.Faixa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Aluno {

    @Id
    @GeneratedValue
    private UUID id;
    private String nome;
    private int idade;
    private String email;
    private String telefone;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    private Faixa faixa;
    private int graus;
    private LocalDate dataMatricula;
    private boolean ativo;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<Treino> treinos = new ArrayList<>();

    public Aluno() {
    }

    public Aluno(String nome, int idade, String email, String telefone) {

        validarNome(nome);
        validarIdade(idade);
        validarEmail(email);
        validarTelefone(telefone);

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

        validarNovaFaixa(novaFaixa);
        validarAlunoAtivo();
        validarMesmaFaixa(novaFaixa);
        validarQuantidadeGraus();
        validarOrdemGraduacao(novaFaixa);
        validarIdadeMinina(novaFaixa);

        this.faixa = novaFaixa;
        this.graus = 0;
    }

    public void adicionarGrau() {

        validarAlunoAtivo();
        validarQuatroGraus();

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

    public String getTelefone() {
        return telefone;
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

    public List<Treino> getTreinos() {
        return List.copyOf(treinos);
    }

    public void removerTreino(Treino treino) {
        validarTreino(treino);
        this.treinos.remove(treino);
    }

    public void adicionarTreino(Treino treino) {
        validarTreino(treino);
        this.treinos.add(treino);
    }

    public int obterTotalTreinosRealizados() {
        return treinos.size();
    }

    public int obterQuantidadeTreinosSemanal(LocalDate referencia) {
        LocalDate inicioSemana = referencia.minusDays(referencia.getDayOfWeek().getValue() -1);
        LocalDate finalSemana = inicioSemana.plusDays(6);

        return (int) treinos.stream()
                        .filter(t -> !t.getData().isBefore(inicioSemana) && 
                        !t.getData().isAfter(finalSemana)).count();
    }

    public int obterQuantidadeTreinosNoMes(int mes, int ano) {
        validarMes(mes);
        validarAno(ano);

        return (int) treinos.stream()
                .filter(t -> t.getData().getMonthValue() == mes &&
                        t.getData().getYear() == ano)
                .count();
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
        Aluno other = (Aluno) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    private void validarMes(int mes) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mês inválido");
        }
    }

    private void validarAno(int ano) {
        if (ano <= 0) {
            throw new IllegalArgumentException("Ano inválido");
        }
    }

    private void validarTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail é obrigatório");
        }
    }

    private void validarIdade(int idade) {
        if (idade < 4) {
            throw new IllegalArgumentException("Idade inválida");
        }
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
    }

    private void validarTreino(Treino treino) {
        if (treino == null) {
            throw new IllegalArgumentException("Treino é obrigatório");
        }
    }

    private void validarIdadeMinina(Faixa novaFaixa) {
        if (!novaFaixa.idadePermitida(this.idade)) {
            throw new IllegalArgumentException("Aluno não possui idade mínima para essa graduação");
        }
    }

    private void validarOrdemGraduacao(Faixa novaFaixa) {
        if (!this.faixa.podeAvancarParaProximaFaixa(novaFaixa)) {
            throw new IllegalStateException("Graduação inválida: deve seguir a ordem das faixas");
        }
    }

    private void validarQuantidadeGraus() {
        if (this.graus < 4) {
            throw new IllegalStateException("Aluno precisa ter 4 graus para mudar de faixa");
        }
    }

    private void validarMesmaFaixa(Faixa novaFaixa) {
        if (this.faixa == novaFaixa) {
            throw new IllegalStateException("Aluno já está nessa faixa");
        }
    }

    private void validarAlunoAtivo() {
        if (!ativo) {
            throw new IllegalStateException("Aluno inativo não pode realizar essa operação");
        }
    }

    private void validarNovaFaixa(Faixa novaFaixa) {
        if (novaFaixa == null) {
            throw new IllegalArgumentException("Nova faixa não pode ser nula");
        }
    }

    private void validarQuatroGraus() {
        if (this.graus >= 4) {
            throw new IllegalArgumentException("Aluno não pode ultrapassar 4 graus na faixa atual");
        }
    }

}

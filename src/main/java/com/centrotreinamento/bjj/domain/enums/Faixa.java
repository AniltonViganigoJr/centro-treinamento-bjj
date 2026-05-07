package com.centrotreinamento.bjj.domain.enums;

public enum Faixa {
    BRANCA(0),
    AZUL(16),
    ROXA(18),
    MARROM(18),
    PRETA(18);

    private final int idadeMinina;

    Faixa(int idadeMinima) {
        this.idadeMinina = idadeMinima;
    }

    public boolean podeAvancarParaProximaFaixa(Faixa novaFaixa) {
        return novaFaixa.ordinal() == this.ordinal() + 1;
    }

    public boolean idadePermitida(int idade) {
        return idade >= this.idadeMinina;
    }

    public static Faixa converter(String valor) {

        try {
            return Faixa.valueOf(valor.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Faixa inválida");
        }
    }
}

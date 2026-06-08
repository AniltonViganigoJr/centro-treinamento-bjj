package com.centrotreinamento.bjj.exception;

public class TreinoNaoEncontradoException extends RuntimeException{
    
    public TreinoNaoEncontradoException() {
        super("Treino não encontrado");
    }
}

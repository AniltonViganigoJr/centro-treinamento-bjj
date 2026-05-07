package com.centrotreinamento.bjj.exception;

public class AlunoNaoEncontradoException extends RuntimeException{
    
    public AlunoNaoEncontradoException() {
        super("Aluno não encontrado");
    }
}

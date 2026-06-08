package com.centrotreinamento.bjj.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.centrotreinamento.bjj.dto.response.ErroResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(AlunoNaoEncontradoException.class)
        public ResponseEntity<ErroResponseDTO> tratarErroAlunoNaoEncontrado(
                        AlunoNaoEncontradoException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ErroResponseDTO(
                                                HttpStatus.NOT_FOUND.value(),
                                                ex.getMessage(),
                                                LocalDateTime.now()));
        }

        @ExceptionHandler(TreinoNaoEncontradoException.class)
        public ResponseEntity<ErroResponseDTO> tratarErroTreinoNaoEncontrado(
                        TreinoNaoEncontradoException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ErroResponseDTO(
                                                HttpStatus.NOT_FOUND.value(),
                                                ex.getMessage(),
                                                LocalDateTime.now()));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErroResponseDTO> tratarIllegalArgumentException(
                        IllegalArgumentException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ErroResponseDTO(
                                                HttpStatus.BAD_REQUEST.value(),
                                                ex.getMessage(),
                                                LocalDateTime.now()));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErroResponseDTO> tratarErroGenerico(
                        Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ErroResponseDTO(
                                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                "Erro interno do servidor",
                                                LocalDateTime.now()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErroResponseDTO> tratarErroArgumentNotValid(
                        MethodArgumentNotValidException ex) {

                String mensagem = ex.getBindingResult()
                                .getFieldError()
                                .getDefaultMessage();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ErroResponseDTO(
                                                HttpStatus.BAD_REQUEST.value(),
                                                mensagem,
                                                LocalDateTime.now()));
        }
}

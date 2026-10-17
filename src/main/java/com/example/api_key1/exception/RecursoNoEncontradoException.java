package com.example.api_key1.exception;

import lombok.Getter;

@Getter
public class RecursoNoEncontradoException extends RuntimeException {

    private final Integer tokensRestantes;

    public RecursoNoEncontradoException(String message, Integer tokensRestantes) {
        super(message);
        this.tokensRestantes = tokensRestantes;
    }
}
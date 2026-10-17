package com.example.api_key1.exception;

import lombok.Getter;

@Getter
public class SaldoInsuficienteException extends RuntimeException {

    private final Integer tokensRestantes;

    public SaldoInsuficienteException(String message, Integer tokensRestantes) {
        super(message);
        this.tokensRestantes = tokensRestantes;
    }
}
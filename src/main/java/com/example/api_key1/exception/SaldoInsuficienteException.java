package com.example.api_key1.exception;

import lombok.Getter;

// Sin tokens disponibles -> 403 (requisitos 8 y 11)
@Getter
public class SaldoInsuficienteException extends RuntimeException {

    private final Integer tokensRestantes;

    public SaldoInsuficienteException(String mensaje, Integer tokensRestantes) {
        super(mensaje);
        this.tokensRestantes = tokensRestantes;
    }
}

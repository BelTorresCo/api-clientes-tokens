package com.example.api_key1.exception;

import lombok.Getter;

// Cliente no encontrado -> 404 (no se descuenta token)
@Getter
public class RecursoNoEncontradoException extends RuntimeException {

    private final Integer tokensRestantes;

    public RecursoNoEncontradoException(String mensaje, Integer tokensRestantes) {
        super(mensaje);
        this.tokensRestantes = tokensRestantes;
    }
}

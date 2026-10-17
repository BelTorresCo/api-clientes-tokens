package com.example.api_key1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

// Respuesta JSON estándar: status, success, mensaje, tokensRestantes y data (requisito 10)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        int status,
        boolean success,
        String mensaje,
        Integer tokensRestantes,
        T data
) {

    public static <T> ApiResponse<T> ok(String mensaje, Integer tokensRestantes, T data) {
        return new ApiResponse<>(200, true, mensaje, tokensRestantes, data);
    }

    public static <T> ApiResponse<T> error(int status, String mensaje, Integer tokensRestantes) {
        return new ApiResponse<>(status, false, mensaje, tokensRestantes, null);
    }
}

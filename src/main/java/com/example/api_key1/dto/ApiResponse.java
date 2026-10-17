package com.example.api_key1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

// Formato de todas las respuestas: status, success, message, tokensRestantes, data
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        int status,
        boolean success,
        String message,
        Integer tokensRestantes,
        T data
) {

    public static <T> ApiResponse<T> ok(String message, Integer tokensRestantes, T data) {
        return new ApiResponse<>(200, true, message, tokensRestantes, data);
    }

    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, false, message, null, null);
    }

    public static <T> ApiResponse<T> error(int status, String message, Integer tokensRestantes) {
        return new ApiResponse<>(status, false, message, tokensRestantes, null);
    }
}
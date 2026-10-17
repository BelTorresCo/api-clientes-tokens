package com.example.api_key1.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// Respuesta 401 en JSON cuando faltan los headers o son incorrectos
@Component
public class JsonAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        boolean faltanHeaders = request.getHeader(ApiKeyTokenFilter.HEADER_API_KEY) == null
                || request.getHeader(ApiKeyTokenFilter.HEADER_TOKEN) == null;

        String mensaje = faltanHeaders
                ? "Debe enviar los headers X-API-KEY y X-TOKEN"
                : "Credenciales inválidas: la API Key o el Token no son correctos";

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                "{\"status\":401,\"success\":false,\"message\":\"" + mensaje + "\"}");
    }
}
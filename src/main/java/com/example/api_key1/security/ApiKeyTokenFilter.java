package com.example.api_key1.security;

import com.example.api_key1.repository.UsuarioApiRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// Valida en cada petición los headers x-api-key y Authorization: Bearer <token> (requisito 7)
public class ApiKeyTokenFilter extends OncePerRequestFilter {

    public static final String HEADER_API_KEY = "x-api-key";
    public static final String HEADER_AUTH = "Authorization";
    private static final String BEARER = "Bearer ";

    private final UsuarioApiRepository usuarioApiRepository;

    public ApiKeyTokenFilter(UsuarioApiRepository usuarioApiRepository) {
        this.usuarioApiRepository = usuarioApiRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String apiKey = request.getHeader(HEADER_API_KEY);
        String auth = request.getHeader(HEADER_AUTH);

        if (apiKey != null && auth != null && auth.startsWith(BEARER)) {
            String token = auth.substring(BEARER.length()).trim();

            usuarioApiRepository.findByApiKeyAndTokenAndEstado(apiKey.trim(), token, "ACTIVO")
                    .ifPresent(usuario -> {
                        var autenticacion = new UsernamePasswordAuthenticationToken(
                                usuario.getNombreUsuario(), null,
                                List.of(new SimpleGrantedAuthority("ROLE_API")));
                        SecurityContextHolder.getContext().setAuthentication(autenticacion);
                    });
        }

        filterChain.doFilter(request, response);
    }
}

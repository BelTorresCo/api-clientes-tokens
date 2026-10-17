package com.example.api_key1.security;

import com.example.api_key1.repository.UsersRepository;
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

// Lee los headers X-API-KEY y X-TOKEN y valida que existan en la tabla users
public class ApiKeyTokenFilter extends OncePerRequestFilter {

    public static final String HEADER_API_KEY = "X-API-KEY";
    public static final String HEADER_TOKEN = "X-TOKEN";

    private final UsersRepository usersRepository;

    public ApiKeyTokenFilter(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String apiKey = request.getHeader(HEADER_API_KEY);
        String token = request.getHeader(HEADER_TOKEN);

        if (apiKey != null && token != null) {
            usersRepository.findByApikeyAndTokenAndActivoTrue(apiKey.trim(), token.trim())
                    .ifPresent(usuario -> {
                        var autenticacion = new UsernamePasswordAuthenticationToken(
                                usuario.getUsername(),
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_API")));
                        SecurityContextHolder.getContext().setAuthentication(autenticacion);
                    });
        }

        filterChain.doFilter(request, response);
    }
}
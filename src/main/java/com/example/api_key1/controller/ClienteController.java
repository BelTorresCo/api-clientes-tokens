package com.example.api_key1.controller;

import com.example.api_key1.dto.ApiResponse;
import com.example.api_key1.dto.ClienteResponse;
import com.example.api_key1.dto.ResultadoConsulta;
import com.example.api_key1.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    // GET /api/clientes/{id}   con headers X-API-KEY y X-TOKEN
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> obtenerPorId(@PathVariable("id") Long id,
                                                                     Principal principal) {
        ResultadoConsulta resultado = clienteService.consultarCliente(id, principal.getName());

        return ResponseEntity.ok()
                .header("X-Tokens-Restantes", String.valueOf(resultado.tokensRestantes()))
                .body(ApiResponse.ok("Consulta exitosa", resultado.tokensRestantes(), resultado.cliente()));
    }
}
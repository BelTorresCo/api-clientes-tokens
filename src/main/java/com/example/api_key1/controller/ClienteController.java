package com.example.api_key1.controller;

import com.example.api_key1.dto.ApiResponse;
import com.example.api_key1.dto.ClienteDTO;
import com.example.api_key1.dto.ResultadoConsulta;
import com.example.api_key1.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

// CONTROLADOR - GET /api/clientes/{id}  (requisito 6)
// Headers: x-api-key: MI_API_KEY_2026   y   Authorization: Bearer TOKEN_SEGURO_123456
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteDTO>> obtenerPorId(@PathVariable("id") Long id,
                                                                Principal principal) {
        ResultadoConsulta r = clienteService.consultarPorId(id, principal.getName());
        return ResponseEntity.ok(
                ApiResponse.ok("Consulta realizada correctamente", r.tokensRestantes(), r.cliente()));
    }
}

package com.example.api_key1.service;

import com.example.api_key1.dto.ClienteDTO;
import com.example.api_key1.dto.ResultadoConsulta;
import com.example.api_key1.entity.Cliente;
import com.example.api_key1.entity.UsuarioApi;
import com.example.api_key1.exception.RecursoNoEncontradoException;
import com.example.api_key1.exception.SaldoInsuficienteException;
import com.example.api_key1.repository.ClienteRepository;
import com.example.api_key1.repository.UsuarioApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// SERVICIO - lógica de la consulta con control de tokens
@Service
@RequiredArgsConstructor
public class ClienteService {

    private static final String SIN_SALDO =
            "Ha agotado sus tokens. No puede consumir la API hasta recargar su saldo.";

    private final ClienteRepository clienteRepository;
    private final UsuarioApiRepository usuarioApiRepository;

    @Transactional
    public ResultadoConsulta consultarPorId(Long id, String nombreUsuario) {
        UsuarioApi usuario = usuarioApiRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new SaldoInsuficienteException("Usuario no encontrado", 0));

        // 1. Verificar que tenga tokens antes de consultar (requisitos 8 y 11)
        if (usuario.getTokensDisponibles() <= 0) {
            throw new SaldoInsuficienteException(SIN_SALDO, 0);
        }

        // 2. Buscar el cliente; si no existe NO se descuenta (requisito 9)
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un cliente con ID " + id + ". No se descontó ningún token.",
                        usuario.getTokensDisponibles()));

        // 3. Consulta correcta: descontar 1 token (requisito 9)
        if (usuarioApiRepository.descontarToken(usuario.getId()) == 0) {
            throw new SaldoInsuficienteException(SIN_SALDO, 0);
        }

        int restantes = usuarioApiRepository.obtenerSaldo(usuario.getId());
        return new ResultadoConsulta(ClienteDTO.desde(cliente), restantes);
    }
}

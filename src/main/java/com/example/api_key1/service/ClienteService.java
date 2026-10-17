package com.example.api_key1.service;

import com.example.api_key1.dto.ClienteResponse;
import com.example.api_key1.dto.ResultadoConsulta;
import com.example.api_key1.entity.ClienteEntity;
import com.example.api_key1.entity.UsersEntity;
import com.example.api_key1.exception.RecursoNoEncontradoException;
import com.example.api_key1.exception.SaldoInsuficienteException;
import com.example.api_key1.repository.ClienteRepository;
import com.example.api_key1.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public ResultadoConsulta consultarCliente(Long idCliente, String username) {
        UsersEntity usuario = usersRepository.findByUsername(username)
                .orElseThrow(() -> new SaldoInsuficienteException("Usuario no encontrado", 0));

        // 1. Bloquear si no tiene tokens
        if (usuario.getSaldoTokens() <= 0) {
            throw new SaldoInsuficienteException(
                    "Ha alcanzado su límite de consumo. No tiene tokens disponibles.", 0);
        }

        // 2. Buscar el cliente (si no existe, no se descuenta)
        ClienteEntity cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un cliente con ID " + idCliente + ". No se descontó ningún token.",
                        usuario.getSaldoTokens()));

        // 3. Descontar 1 token
        int filas = usersRepository.descontarUnToken(usuario.getId());
        if (filas == 0) {
            throw new SaldoInsuficienteException(
                    "Ha alcanzado su límite de consumo. No tiene tokens disponibles.", 0);
        }

        // 4. Devolver el cliente y el saldo que queda
        int restantes = usersRepository.obtenerSaldo(usuario.getId());
        return new ResultadoConsulta(ClienteResponse.desde(cliente), restantes);
    }
}
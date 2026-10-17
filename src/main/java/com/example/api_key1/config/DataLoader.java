package com.example.api_key1.config;

import com.example.api_key1.entity.Cliente;
import com.example.api_key1.entity.UsuarioApi;
import com.example.api_key1.repository.ClienteRepository;
import com.example.api_key1.repository.UsuarioApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

// Registra los datos mínimos si las tablas están vacías (requisito 4)
// y completa el correo de los clientes que lo tengan vacío.
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioApiRepository usuarioApiRepository;
    private final ClienteRepository clienteRepository;

    private static final Map<String, String> CORREOS = Map.of(
            "12345678", "juan.quispe@correo.com",
            "87654321", "maria.flores@correo.com",
            "45678912", "luis.ramos@correo.com",
            "70123456", "rosa.huaman@correo.com",
            "10293847", "pedro.sanchez@correo.com");

    @Override
    public void run(String... args) {
        if (usuarioApiRepository.count() == 0) {
            usuarioApiRepository.save(usuario("admin_techservices", "MI_API_KEY_2026", "TOKEN_SEGURO_123456", 10));
            usuarioApiRepository.save(usuario("soporte_techservices", "API_KEY_SOPORTE_2026", "TOKEN_SOPORTE_654321", 1));
        }
        if (clienteRepository.count() == 0) {
            clienteRepository.save(cliente("Juan Carlos", "Quispe Mamani", "12345678", "987654321"));
            clienteRepository.save(cliente("María Elena", "Flores García", "87654321", "912345678"));
            clienteRepository.save(cliente("Luis Alberto", "Ramos Chávez", "45678912", "923456789"));
            clienteRepository.save(cliente("Rosa Isabel", "Huamán Torres", "70123456", "934567890"));
            clienteRepository.save(cliente("Pedro José", "Sánchez Vargas", "10293847", "945678901"));
        }
        // Completa correos vacíos
        for (Cliente c : clienteRepository.findAll()) {
            if (c.getCorreo() == null && CORREOS.containsKey(c.getDni())) {
                c.setCorreo(CORREOS.get(c.getDni()));
                clienteRepository.save(c);
            }
        }
    }

    private UsuarioApi usuario(String nombre, String apiKey, String token, int tokens) {
        UsuarioApi u = new UsuarioApi();
        u.setNombreUsuario(nombre);
        u.setApiKey(apiKey);
        u.setToken(token);
        u.setTokensDisponibles(tokens);
        u.setEstado("ACTIVO");
        return u;
    }

    private Cliente cliente(String nombres, String apellidos, String dni, String telefono) {
        Cliente c = new Cliente();
        c.setNombres(nombres);
        c.setApellidos(apellidos);
        c.setDni(dni);
        c.setCorreo(CORREOS.get(dni));
        c.setTelefono(telefono);
        return c;
    }
}
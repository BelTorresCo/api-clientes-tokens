package com.example.api_key1.config;

import com.example.api_key1.entity.ClienteEntity;
import com.example.api_key1.entity.UsersEntity;
import com.example.api_key1.repository.ClienteRepository;
import com.example.api_key1.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Si las tablas están vacías (base nueva en la nube), carga los datos de prueba.
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsersRepository usersRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public void run(String... args) {
        if (usersRepository.count() == 0) {
            usersRepository.save(usuario("demo", "ak_demo_7f3c9a1e5b2d4f60", "tk_demo_9b8e7d6c5a4f3e21", 10));
            usersRepository.save(usuario("limite", "ak_lim_3d5e7f9a1b2c4d60", "tk_lim_8a7b6c5d4e3f2a10", 1));
            usersRepository.save(usuario("sinsaldo", "ak_cero_1a2b3c4d5e6f7a80", "tk_cero_0f9e8d7c6b5a4f30", 0));
        }

        if (clienteRepository.count() == 0) {
            clienteRepository.save(cliente("Juan Carlos", "Quispe Mamani", "12345678",
                    "juan.quispe@correo.com", "987654321", "Av. Los Incas 123, Lima", LocalDate.of(2024, 3, 14)));
            clienteRepository.save(cliente("María Elena", "Flores García", "87654321",
                    "maria.flores@correo.com", "912345678", "Calle Lima 456, Ica", LocalDate.of(2024, 7, 22)));
            clienteRepository.save(cliente("Luis Alberto", "Ramos Chávez", "45678912",
                    "luis.ramos@correo.com", "923456789", "Jr. Mercaderes 789, Arequipa", LocalDate.of(2025, 1, 5)));
            clienteRepository.save(cliente("Rosa Isabel", "Huamán Torres", "70123456",
                    "rosa.huaman@correo.com", "934567890", "Calle Plateros 321, Cusco", LocalDate.of(2025, 5, 30)));
            clienteRepository.save(cliente("Pedro José", "Sánchez Vargas", "10293847",
                    "pedro.sanchez@correo.com", "945678901", "Av. Grau 555, Piura", LocalDate.of(2025, 9, 18)));
        }
    }

    private UsersEntity usuario(String username, String apikey, String token, int saldo) {
        UsersEntity u = new UsersEntity();
        u.setUsername(username);
        u.setApikey(apikey);
        u.setToken(token);
        u.setSaldoTokens(saldo);
        u.setActivo(true);
        u.setFechaCreacion(LocalDateTime.now());
        return u;
    }

    private ClienteEntity cliente(String nombres, String apellidos, String dni, String email,
                                  String telefono, String direccion, LocalDate fecha) {
        ClienteEntity c = new ClienteEntity();
        c.setNombres(nombres);
        c.setApellidos(apellidos);
        c.setDni(dni);
        c.setEmail(email);
        c.setTelefono(telefono);
        c.setDireccion(direccion);
        c.setFechaRegistro(fecha);
        return c;
    }
}
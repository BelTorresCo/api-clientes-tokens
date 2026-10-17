package com.example.api_key1.dto;

import com.example.api_key1.entity.Cliente;

// Datos del cliente que se devuelven en "data"
public record ClienteDTO(
        Long id,
        String nombres,
        String apellidos,
        String dni,
        String correo,
        String telefono
) {
    public static ClienteDTO desde(Cliente c) {
        return new ClienteDTO(c.getId(), c.getNombres(), c.getApellidos(),
                c.getDni(), c.getCorreo(), c.getTelefono());
    }
}

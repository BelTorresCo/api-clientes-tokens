package com.example.api_key1.dto;

import com.example.api_key1.entity.ClienteEntity;

public record ClienteResponse(
        Long id,
        String nombres,
        String apellidos,
        String dni,
        String email,
        String telefono,
        String direccion,
        String fechaRegistro
) {

    public static ClienteResponse desde(ClienteEntity c) {
        return new ClienteResponse(
                c.getId(),
                c.getNombres(),
                c.getApellidos(),
                c.getDni(),
                c.getEmail(),
                c.getTelefono(),
                c.getDireccion(),
                c.getFechaRegistro() == null ? null : c.getFechaRegistro().toString()
        );
    }
}
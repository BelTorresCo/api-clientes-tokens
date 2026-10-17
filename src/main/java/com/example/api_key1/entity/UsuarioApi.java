package com.example.api_key1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// MODELO - Tabla usuarios_api (requisito 2)
@Entity
@Table(name = "usuarios_api")
@Getter
@Setter
@NoArgsConstructor
public class UsuarioApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;

    @Column(name = "api_key", nullable = false, unique = true, length = 100)
    private String apiKey;

    @Column(name = "token", nullable = false, length = 150)
    private String token;

    @Column(name = "tokens_disponibles", nullable = false)
    private Integer tokensDisponibles;

    // ACTIVO o INACTIVO
    @Column(name = "estado", nullable = false, length = 10)
    private String estado;
}

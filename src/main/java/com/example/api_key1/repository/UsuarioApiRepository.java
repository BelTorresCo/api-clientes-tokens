package com.example.api_key1.repository;

import com.example.api_key1.entity.UsuarioApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// REPOSITORIO - acceso a la tabla usuarios_api
@Repository
public interface UsuarioApiRepository extends JpaRepository<UsuarioApi, Long> {

    // Busca un usuario con esa API Key, ese Token y estado ACTIVO (requisito 7)
    Optional<UsuarioApi> findByApiKeyAndTokenAndEstado(String apiKey, String token, String estado);

    Optional<UsuarioApi> findByNombreUsuario(String nombreUsuario);

    // Descuenta 1 token solo si tiene saldo (requisito 9). Devuelve 1 si descontó, 0 si no.
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE UsuarioApi u SET u.tokensDisponibles = u.tokensDisponibles - 1 "
            + "WHERE u.id = :id AND u.tokensDisponibles > 0")
    int descontarToken(@Param("id") Long id);

    @Query("SELECT u.tokensDisponibles FROM UsuarioApi u WHERE u.id = :id")
    Integer obtenerSaldo(@Param("id") Long id);
}

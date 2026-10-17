package com.example.api_key1.repository;

import com.example.api_key1.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    // Busca un usuario activo con esa API Key Y ese Token
    Optional<UsersEntity> findByApikeyAndTokenAndActivoTrue(String apikey, String token);

    Optional<UsersEntity> findByUsername(String username);

    // Descuenta 1 token solo si el saldo es mayor que 0 (nunca queda negativo)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE UsersEntity u SET u.saldoTokens = u.saldoTokens - 1 "
            + "WHERE u.id = :id AND u.saldoTokens > 0")
    int descontarUnToken(@Param("id") Long id);

    @Query("SELECT u.saldoTokens FROM UsersEntity u WHERE u.id = :id")
    Integer obtenerSaldo(@Param("id") Long id);
}
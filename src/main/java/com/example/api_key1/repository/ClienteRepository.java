package com.example.api_key1.repository;

import com.example.api_key1.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// REPOSITORIO - acceso a la tabla clientes
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

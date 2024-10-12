package com.spring.aeropuerto.tarea.repository;

import com.spring.aeropuerto.tarea.entity.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, Long> {
}

package com.example.back.users.repository;

import com.example.back.users.model.CitaPeliculasCine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaPeliculaRepository extends JpaRepository<CitaPeliculasCine, Long> {
    // Aquí puedes agregar métodos personalizados si los necesitas
}

package com.example.back.users.repository;

import com.example.back.users.model.CitaPelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaPeliculaRepository extends JpaRepository<CitaPelicula, Long> {
    // Aquí puedes agregar métodos personalizados si los necesitas
}

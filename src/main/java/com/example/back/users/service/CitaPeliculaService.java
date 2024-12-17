package com.example.back.users.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back.users.model.CitaPelicula;
import com.example.back.users.repository.CitaPeliculaRepository;

@Service
public class CitaPeliculaService {

    @Autowired
    private CitaPeliculaRepository citaPeliculaRepository;

    // Obtener todas las citas de películas
    public List<CitaPelicula> getAllCitas() {
        return citaPeliculaRepository.findAll();
    }

    // Registrar una nueva cita de película
    public String registerCita(CitaPelicula citaPelicula) {
        if (citaPelicula.getNombrePelicula() == null || citaPelicula.getHorarioFuncion() == null) {
            return "Los campos nombre de película y horario de función son obligatorios.";
        }

        // Guardar la cita en la base de datos
        citaPeliculaRepository.save(citaPelicula);
        return "Cita de película registrada exitosamente.";
    }

    // Obtener una cita específica por ID
    public Optional<CitaPelicula> getCitaById(Long id) {
        return citaPeliculaRepository.findById(id);
    }
}

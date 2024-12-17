package com.example.back.users.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back.users.model.CitaPeliculasCine;
import com.example.back.users.repository.CitaPeliculaRepository;
@Service
public class CitaPeliculaService {

    @Autowired
    private CitaPeliculaRepository citaPeliculaRepository;

    // Obtener todas las citas de películas
    public List<CitaPeliculasCine> getAllCitas() {
        return citaPeliculaRepository.findAll();
    }

    // Registrar una nueva cita de película
    public String registerCita(CitaPeliculasCine citaPelicula) {
        if (citaPelicula.getNombrePelicula() == null || 
            citaPelicula.getFechaFuncion() == null || 
            citaPelicula.getHoraFuncion() == null || 
            citaPelicula.getEntradas() <= 0) {
            return "Los campos nombre, fecha, hora y entradas son obligatorios.";
        }
    
        // El precio total ya no se calcula en el backend
        // Solo guardamos la información relevante (entradas, nombre, fecha, hora)

        citaPeliculaRepository.save(citaPelicula);
        return "Cita de película registrada exitosamente";
    }

    // Obtener una cita específica por ID
    public Optional<CitaPeliculasCine> getCitaById(Long id) {
        return citaPeliculaRepository.findById(id);
    }
}

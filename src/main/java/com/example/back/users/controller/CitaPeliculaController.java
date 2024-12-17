package com.example.back.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.back.users.model.CitaPeliculasCine;
import com.example.back.users.service.CitaPeliculaService;

@RestController
@RequestMapping("api-citas")
@CrossOrigin(origins = "http://localhost:8081")
public class CitaPeliculaController {

    @Autowired
    private CitaPeliculaService citaPeliculaService;

    // Obtener todas las citas de películas
    @GetMapping("/all")
    public ResponseEntity<List<CitaPeliculasCine>> getAllCitas() {
        List<CitaPeliculasCine> citas = citaPeliculaService.getAllCitas();
        return ResponseEntity.ok(citas);
    }

    // Registrar una nueva cita de película
    @PostMapping("/register")
    public ResponseEntity<String> registerCita(@RequestBody CitaPeliculasCine citaPelicula) {
        try {
            // Validamos que los campos requeridos estén presentes
            if (citaPelicula.getNombrePelicula() == null || 
                citaPelicula.getFechaFuncion() == null || 
                citaPelicula.getHoraFuncion() == null) {
                return ResponseEntity.badRequest().body("Los campos nombre, fecha y hora son obligatorios.");
            }
    
            String result = citaPeliculaService.registerCita(citaPelicula);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Captura cualquier excepción y devuelve un mensaje de error 500
            return ResponseEntity.status(500).body("Error al registrar la cita: " + e.getMessage());
        }
    }

    // Obtener una cita específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCitaById(@PathVariable Long id) {
        return citaPeliculaService.getCitaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

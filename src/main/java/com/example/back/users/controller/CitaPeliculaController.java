package com.example.back.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.back.users.model.CitaPelicula;
import com.example.back.users.service.CitaPeliculaService;

@RestController
@RequestMapping("api-citas")
@CrossOrigin(origins = "http://localhost:8081")
public class CitaPeliculaController {

    @Autowired
    private CitaPeliculaService citaPeliculaService;

    // Obtener todas las citas de películas
    @GetMapping("/all")
    public ResponseEntity<List<CitaPelicula>> getAllCitas() {
        List<CitaPelicula> citas = citaPeliculaService.getAllCitas();
        return ResponseEntity.ok(citas);
    }

    // Registrar una nueva cita de película
    @PostMapping("/register")
    public ResponseEntity<String> registerCita(@RequestBody CitaPelicula citaPelicula) {
        String result = citaPeliculaService.registerCita(citaPelicula);
        if (result.contains("exitosamente")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
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

package com.example.back.users.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class CitaPeliculasCine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombrePelicula;

    @Column(nullable = false)
    private LocalDate fechaFuncion;

    @Column(nullable = false)
    private LocalTime horaFuncion;

    @Column(nullable = false)
    private int entradas;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public LocalDate getFechaFuncion() {
        return fechaFuncion;
    }

    public void setFechaFuncion(LocalDate fechaFuncion) {
        this.fechaFuncion = fechaFuncion;
    }

    public LocalTime getHoraFuncion() {
        return horaFuncion;
    }

    public void setHoraFuncion(LocalTime horaFuncion) {
        this.horaFuncion = horaFuncion;
    }

    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    // Ya no es necesario el método de calcular total
    // El precio total solo se calculará en el frontend
}

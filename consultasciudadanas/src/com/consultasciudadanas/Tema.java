package com.consultasciudadanas;

import java.util.ArrayList;
import java.util.List;

public class Tema {
    private int id;
    private String nombre;
    private List<Pregunta> preguntas;

    public Tema(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.preguntas = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public List<Pregunta> getPreguntas() { return preguntas; }

    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    @Override
    public String toString() {
        return "Tema " + id + ": " + nombre;
    }
}

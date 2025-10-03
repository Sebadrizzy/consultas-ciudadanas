package com.consultasciudadanas;

import java.util.HashMap;
import java.util.Map;

public class Tema {
    private int id;
    private String nombre;
    private Map<String, Pregunta> preguntas;
    public Tema(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.preguntas = new HashMap<>();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Map<String, Pregunta> getPreguntas() { return preguntas; }

    public boolean agregarPregunta(Pregunta pregunta) {
        if (preguntas.containsKey(pregunta.getEnunciado())) {
            System.out.println("⚠️ Pregunta duplicada: " + pregunta.getEnunciado());
            return false;
        }
        preguntas.put(pregunta.getEnunciado(), pregunta);
        return true;
    }

    @Override
    public String toString() {
        return "Tema " + id + ": " + nombre;
    }
}

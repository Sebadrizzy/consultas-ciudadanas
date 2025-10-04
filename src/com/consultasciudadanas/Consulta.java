package com.consultasciudadanas;

import java.util.HashMap;
import java.util.Map;

public class Consulta {
    private int id;
    private String titulo;
    private String fecha;
    private Map<String, Tema> temas; 

    public Consulta(int id, String titulo, String fecha) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        this.temas = new HashMap<>();
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getFecha() { return fecha; }
    public Map<String, Tema> getTemas() { return temas; }

    public boolean agregarTema(Tema tema) {
        if (temas.containsKey(tema.getNombre())) {
            System.out.println("⚠️ Tema duplicado: " + tema.getNombre());
            return false;
        }
        temas.put(tema.getNombre(), tema);
        return true;
    }

    // Sobrecarga: recibe solo id y nombre
    public boolean agregarTema(int id, String nombre) {
        Tema tema = new Tema(id, nombre);
        return agregarTema(tema); // reutiliza el original
    }

    @Override
    public String toString() {
        return "Consulta " + id + ": " + titulo + " (" + fecha + ")";
    }
}

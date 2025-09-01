package com.consultasciudadanas;

import java.util.ArrayList;
import java.util.List;

public class Consulta {
    private int id;
    private String titulo;
    private String fecha;
    private List<Tema> temas;

    public Consulta(int id, String titulo, String fecha) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        this.temas = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getFecha() { return fecha; }
    public List<Tema> getTemas() { return temas; }

    public void agregarTema(Tema tema) {
        temas.add(tema);
    }

    @Override
    public String toString() {
        return "Consulta " + id + ": " + titulo + " (" + fecha + ")";
    }
}

package com.consultasciudadanas;

import java.util.ArrayList;
import java.util.List;

public class Pregunta {
    private int id;
    private String enunciado;
    private List<Voto> votos;

    public Pregunta(int id, String enunciado) {
        this.id = id;
        this.enunciado = enunciado;
        this.votos = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getEnunciado() { return enunciado; }
    public List<Voto> getVotos() { return votos; }

    public void agregarVoto(Voto voto) {
        votos.add(voto);
    }

    public void mostrarResultados() {
        System.out.println("Resultados de la pregunta: " + enunciado);
        votos.stream()
             .map(Voto::getRespuesta)
             .distinct()
             .forEach(r -> {
                 long count = votos.stream().filter(v -> v.getRespuesta().equals(r)).count();
                 System.out.println(r + ": " + count + " votos");
             });
    }

    @Override
    public String toString() {
        return "Pregunta " + id + ": " + enunciado;
    }
}

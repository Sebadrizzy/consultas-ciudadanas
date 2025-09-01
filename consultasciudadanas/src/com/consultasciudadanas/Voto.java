package com.consultasciudadanas;

public class Voto {
    private Ciudadano ciudadano;
    private String respuesta;

    public Voto(Ciudadano ciudadano, String respuesta) {
        this.ciudadano = ciudadano;
        this.respuesta = respuesta;
    }

    public Ciudadano getCiudadano() { return ciudadano; }
    public String getRespuesta() { return respuesta; }

    @Override
    public String toString() {
        return ciudadano.getNombre() + " votó: " + respuesta;
    }
}

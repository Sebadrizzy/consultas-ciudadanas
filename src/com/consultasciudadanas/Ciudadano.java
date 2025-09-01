package com.consultasciudadanas;

public class Ciudadano {
    private String rut;
    private String nombre;
    private String comuna;
    private int edad;

    public Ciudadano(String rut, String nombre, String comuna, int edad) {
        this.rut = rut;
        this.nombre = nombre;
        this.comuna = comuna;
        this.edad = edad;
    }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getComuna() { return comuna; }
    public void setComuna(String comuna) { this.comuna = comuna; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    @Override
    public String toString() {
        return nombre + " (" + rut + "), " + edad + " años, comuna: " + comuna;
    }
}

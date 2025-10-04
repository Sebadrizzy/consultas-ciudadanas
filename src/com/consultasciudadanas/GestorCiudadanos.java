package com.consultasciudadanas;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GestorCiudadanos {
    private Map<String, Ciudadano> ciudadanos;
    private Scanner sc;

    public GestorCiudadanos(Scanner sc) {
        this.ciudadanos = new HashMap<>();
        this.sc = sc;
    }

    public void crearCiudadano() {
        String rut;
        do {
            System.out.print("Ingrese RUT (Debe tener de 8 a 9 caracteres sin guion, ej: 123456789): ");
            rut = sc.nextLine().trim();
            if (rut.length() < 8 || rut.length() > 9) {
                System.out.println("El RUT debe tener de 8 a 9 caracteres.");
            } else if (ciudadanos.containsKey(rut)) {
                System.out.println("El RUT " + rut + " ya está registrado. Ingrese un RUT único.");
                rut = "";
            }
        } while (rut.length() < 8 || rut.length() > 9);

        String nombre = Validar.leerNoVacio("Ingrese nombre: ", sc);
        String comuna = Validar.leerNoVacio("Ingrese comuna: ", sc);
        int edad = Validar.leerEntero("Ingrese edad: ", sc);

        Ciudadano c = new Ciudadano(rut, nombre, comuna, edad);
        ciudadanos.put(rut, c);
        System.out.println("✅ Ciudadano creado: " + c);
    }

    public void mostrarCiudadanos() {
        if (ciudadanos.isEmpty()) {
            System.out.println("⚠️ No hay ciudadanos registrados.");
        } else {
            ciudadanos.values().forEach(System.out::println);
        }
    }

    public Ciudadano buscarCiudadano(String rut) {
        return ciudadanos.get(rut);
    }
}

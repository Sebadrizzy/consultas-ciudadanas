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
            System.out.print("Ingrese RUT (Debe tener de 7 a 9 caracteres sin guion, ej: 123456789): ");
            rut = sc.nextLine().trim();
            if (rut.length() < 7 || rut.length() > 9) {
                System.out.println("⚠️ El RUT debe tener de 7 a 9 caracteres.");
            }
        } while (rut.length() < 7 || rut.length() > 9);

        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine().trim();

        System.out.print("Ingrese comuna: ");
        String comuna = sc.nextLine().trim();

        int edad = leerEntero("Ingrese edad: ");

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

    // ✅ método auxiliar para validar enteros
    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Debe ingresar un número válido.");
            }
        }
    }
}

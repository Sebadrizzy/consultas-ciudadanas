package com.consultasciudadanas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GestorCiudadanos {
    private Map<String, Ciudadano> ciudadanos;
    private Scanner sc;

    public GestorCiudadanos(Scanner sc) {
        this.ciudadanos = new HashMap<>();
        this.sc = sc;
    }

    public void crearCiudadano() {
        String rut = null;
        do {
            try {
                System.out.print("Ingrese RUT (8-9 dígitos sin puntos ni guion): ");
                rut = sc.nextLine().trim();

                if (!rut.matches("\\d{8,9}")) {
                    throw new RUTInvalidoException("El RUT debe tener 8 o 9 dígitos numéricos (sin puntos ni guion).");
                }

                if (ciudadanos.containsKey(rut)) {
                    throw new RUTInvalidoException("El RUT " + rut + " ya está registrado.");
                }

            } catch (RUTInvalidoException e) {
                System.out.println("Error: " + e.getMessage());
                rut = null;
            }
        } while (rut == null);

        // Continúa con nombre, comuna, edad...
        String nombre = Validar.leerNoVacio("Ingrese nombre: ", sc);
        String comuna = Validar.leerNoVacio("Ingrese comuna: ", sc);
        int edad = Validar.leerEntero("Ingrese edad: ", sc);

        Ciudadano c = new Ciudadano(rut, nombre, comuna, edad);
        ciudadanos.put(rut, c);
        System.out.println("Ciudadano creado: " + c);
    }

    public void mostrarCiudadanos() {
        if (ciudadanos.isEmpty()) {
            System.out.println("No hay ciudadanos registrados.");
        } else {
            ciudadanos.values().forEach(System.out::println);
        }
    }

    public Ciudadano buscarCiudadano(String rut) {
        return ciudadanos.get(rut);
    }

    // Cargar ciudadanos de datos iniciales
    public void agregarCiudadano(Ciudadano ciudadano) {
        ciudadanos.put(ciudadano.getRut(), ciudadano);
    }

    public Map<String, Ciudadano> getCiudadanos() {
        return ciudadanos;
    }

    public List<Ciudadano> getCiudadanosPorComuna(String comuna) {
        return ciudadanos.values().stream()
                .filter(c -> c.getComuna().equalsIgnoreCase(comuna.trim()))
                .collect(Collectors.toList());
    }

    public boolean eliminarCiudadano(String rut) {
        if (ciudadanos.containsKey(rut)) {
            ciudadanos.remove(rut);
            return true;
        }
        return false;
    }

    public boolean editarCiudadano(String rut, String nombre, String comuna, int edad) {
        Ciudadano ciudadano = ciudadanos.get(rut);
        if (ciudadano != null) {
            ciudadano.setNombre(nombre);
            ciudadano.setComuna(comuna);
            ciudadano.setEdad(edad);
            return true;
        }
        return false;
    }

    public List<Ciudadano> buscarCiudadanos(String criterio) {
        String criterioLower = criterio.toLowerCase().trim();
        return ciudadanos.values().stream()
                .filter(c -> c.getRut().contains(criterio) ||
                        c.getNombre().toLowerCase().contains(criterioLower))
                .collect(Collectors.toList());
    }

    public List<Ciudadano> buscarCiudadanos(String comuna, int edadMin, int edadMax) {
        return ciudadanos.values().stream()
                .filter(c -> comuna == null || comuna.isEmpty() ||
                        c.getComuna().toLowerCase().contains(comuna.toLowerCase().trim()))
                .filter(c -> (edadMin == 0 || c.getEdad() >= edadMin))
                .filter(c -> (edadMax == 0 || c.getEdad() <= edadMax))
                .collect(Collectors.toList());
    }
}

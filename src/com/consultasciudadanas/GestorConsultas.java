package com.consultasciudadanas;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GestorConsultas {
    private Map<Integer, Consulta> consultas;
    private Scanner sc;

    public GestorConsultas(Scanner sc) {
        this.consultas = new HashMap<>();
        this.sc = sc;
    }

    public void crearConsulta() {
        int id = leerEntero("Ingrese ID de la consulta: ");
        System.out.print("Ingrese título: ");
        String titulo = sc.nextLine().trim();
        System.out.print("Ingrese fecha (dd-mm-aaaa): ");
        String fecha = sc.nextLine().trim();

        Consulta consulta = new Consulta(id, titulo, fecha);
        consultas.put(id, consulta);
        System.out.println("✅ Consulta creada: " + consulta);
    }

    public void mostrarConsultas() {
        if (consultas.isEmpty()) {
            System.out.println("⚠️ No hay consultas registradas.");
        } else {
            consultas.values().forEach(System.out::println);
        }
    }

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
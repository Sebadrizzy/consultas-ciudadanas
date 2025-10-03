package com.consultasciudadanas;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        GestorCiudadanos gestorCiudadanos = new GestorCiudadanos(sc);
        GestorConsultas gestorConsultas = new GestorConsultas(sc);

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Ingrese una opción: ", sc);

            switch (opcion) {
                case 1:
                    gestorCiudadanos.crearCiudadano();
                    break;
                case 2:
                    gestorCiudadanos.mostrarCiudadanos();
                    break;
                case 3:
                    gestorConsultas.crearConsulta();
                    break;
                case 4:
                    gestorConsultas.mostrarConsultas();
                    break;
                case 0:
                    System.out.println("👋 Saliendo del sistema...");
                    break;
                default:
                    System.out.println("⚠️ Opción inválida");
            }

        } while (opcion != 0);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Crear ciudadano");
        System.out.println("2. Mostrar ciudadanos");
        System.out.println("3. Crear consulta");
        System.out.println("4. Mostrar consultas");
        System.out.println("0. Salir");
    }

    private static int leerEntero(String mensaje, Scanner sc) {
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

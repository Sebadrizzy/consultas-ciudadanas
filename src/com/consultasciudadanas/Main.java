package com.consultasciudadanas;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        GestorCiudadanos gestorCiudadanos = new GestorCiudadanos(sc);
        GestorConsultas gestorConsultas = new GestorConsultas(sc);

        DatosIniciales datosIniciales = new DatosIniciales(gestorCiudadanos, gestorConsultas);
        datosIniciales.inicializarDatos();


        // Inicializar persistencia
        PersistenciaDatos persistencia = new PersistenciaDatos(gestorCiudadanos, gestorConsultas);
        persistencia.cargarDatos();

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
                    menuConsultas(gestorConsultas, gestorCiudadanos, sc);
                    break;
                case 0:
                    System.out.println("👋 Saliendo del sistema...");
                    persistencia.guardarDatos();
                    break;
                default:
                    System.out.println("⚠️ Opción inválida");
            }

        } while (opcion != 0);

        sc.close();
    }

    // ===== MENÚS =====

    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Crear ciudadano");
        System.out.println("2. Mostrar ciudadanos");
        System.out.println("3. Gestionar consultas");
        System.out.println("0. Salir");
    }

    private static void menuConsultas(GestorConsultas gestorConsultas, GestorCiudadanos gestorCiudadanos, Scanner sc) {
        int opcion;
        do {
            System.out.println("\n=== MENÚ CONSULTAS ===");
            System.out.println("1. Crear consulta");
            System.out.println("2. Mostrar consultas");
            System.out.println("3. Agregar tema a consulta");
            System.out.println("4. Listar temas de una consulta");
            System.out.println("5. Agregar pregunta a un tema");
            System.out.println("6. Listar preguntas de un tema");
            System.out.println("7. Agregar votos a una pregunta");
            System.out.println("0. Volver al menú principal");

            opcion = leerEntero("Ingrese una opción: ", sc);

            switch (opcion) {
                case 1:
                    gestorConsultas.crearConsulta();
                    break;
                case 2:
                    gestorConsultas.mostrarConsultas();
                    break;
                case 3:
                    gestorConsultas.agregarTemaAConsulta();
                    break;
                case 4:
                    gestorConsultas.listarTemasDeConsulta();
                    break;
                case 5:
                    gestorConsultas.agregarPreguntaATema();
                    break;
                case 6:
                    gestorConsultas.listarPreguntasDeTema();
                    break;
                case 7:
                    gestorConsultas.agregarVoto(gestorCiudadanos);
                    break;
                case 0:
                    System.out.println("🔙 Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("⚠️ Opción inválida");
            }

        } while (opcion != 0);
    }

    // ===== UTIL =====

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

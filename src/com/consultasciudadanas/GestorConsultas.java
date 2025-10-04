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
        int id = Validar.leerEntero("Ingrese id de la consulta: ", sc);
        String titulo = Validar.leerNoVacio("Ingrese titulo: ", sc);
        String fecha = Validar.leerFechaValida("Ingrese fecha (dd-mm-aaaa): ", sc);

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

    public Consulta buscarConsulta(int id) {
        return consultas.get(id);
    }

    public void agregarTemaAConsulta() {
        int idConsulta = leerEntero("Ingrese ID de la consulta: ");
        Consulta consulta = buscarConsulta(idConsulta);
        if (consulta == null) {
            System.out.println("⚠️ Consulta no encontrada.");
            return;
        }

        int idTema = leerEntero("Ingrese ID del tema: ");
        System.out.print("Ingrese nombre del tema: ");
        String nombre = sc.nextLine().trim();

        Tema tema = new Tema(idTema, nombre);
        if (consulta.agregarTema(tema)) {
            System.out.println("✅ Tema agregado a la consulta.");
        }
    }

    public void listarTemasDeConsulta() {
        int idConsulta = leerEntero("Ingrese ID de la consulta: ");
        Consulta consulta = buscarConsulta(idConsulta);
        if (consulta == null) {
            System.out.println("⚠️ Consulta no encontrada.");
            return;
        }

        if (consulta.getTemas().isEmpty()) {
            System.out.println("⚠️ No hay temas en esta consulta.");
        } else {
            consulta.getTemas().values().forEach(System.out::println);
        }
    }

    // ======== PREGUNTAS ========

    public void agregarPreguntaATema() {
        int idConsulta = leerEntero("Ingrese ID de la consulta: ");
        Consulta consulta = buscarConsulta(idConsulta);
        if (consulta == null) {
            System.out.println("⚠️ Consulta no encontrada.");
            return;
        }

        System.out.print("Ingrese nombre del tema: ");
        String nombreTema = sc.nextLine().trim();
        Tema tema = consulta.getTemas().get(nombreTema);
        if (tema == null) {
            System.out.println("⚠️ Tema no encontrado en esta consulta.");
            return;
        }

        int idPregunta = leerEntero("Ingrese ID de la pregunta: ");
        System.out.print("Ingrese enunciado de la pregunta: ");
        String enunciado = sc.nextLine().trim();

        Pregunta pregunta = new Pregunta(idPregunta, enunciado);
        if (tema.agregarPregunta(pregunta)) {
            System.out.println("✅ Pregunta agregada al tema.");
        }
    }

    public void listarPreguntasDeTema() {
        int idConsulta = leerEntero("Ingrese ID de la consulta: ");
        Consulta consulta = buscarConsulta(idConsulta);
        if (consulta == null) {
            System.out.println("⚠️ Consulta no encontrada.");
            return;
        }

        System.out.print("Ingrese nombre del tema: ");
        String nombreTema = sc.nextLine().trim();
        Tema tema = consulta.getTemas().get(nombreTema);
        if (tema == null) {
            System.out.println("⚠️ Tema no encontrado en esta consulta.");
            return;
        }

        if (tema.getPreguntas().isEmpty()) {
            System.out.println("⚠️ No hay preguntas en este tema.");
        } else {
            tema.getPreguntas().values().forEach(System.out::println);
        }
    }

    public void agregarVoto(GestorCiudadanos gestorCiudadanos) {
        // Buscar ciudadano
        System.out.print("Ingrese RUT del ciudadano: ");
        String rut = sc.nextLine().trim();
        Ciudadano ciudadano = gestorCiudadanos.buscarCiudadano(rut);
        if (ciudadano == null) {
            System.out.println("⚠️ No existe un ciudadano con ese RUT.");
            return;
        }

        // Buscar consulta
        int idConsulta = leerEntero("Ingrese ID de la consulta: ");
        Consulta consulta = buscarConsulta(idConsulta);
        if (consulta == null) {
            System.out.println("⚠️ Consulta no encontrada.");
            return;
        }

        // Buscar tema
        System.out.print("Ingrese nombre del tema: ");
        String nombreTema = sc.nextLine().trim();
        Tema tema = consulta.getTemas().get(nombreTema);
        if (tema == null) {
            System.out.println("⚠️ Tema no encontrado en esta consulta.");
            return;
        }

        // Buscar pregunta
        System.out.print("Ingrese enunciado de la pregunta: ");
        String enunciado = sc.nextLine().trim();
        Pregunta pregunta = tema.getPreguntas().get(enunciado);
        if (pregunta == null) {
            System.out.println("⚠️ Pregunta no encontrada en este tema.");
            return;
        }

        // Registrar voto
        System.out.print("Ingrese su respuesta: ");
        String respuesta = sc.nextLine().trim();
        Voto voto = new Voto(ciudadano, respuesta);
        pregunta.agregarVoto(voto);
        System.out.println("✅ Voto registrado correctamente.");
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

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
        System.out.println("Consulta creada: " + consulta);
    }

    public void mostrarConsultas() {
        if (consultas.isEmpty()) {
            System.out.println("No hay consultas registradas.");
        } else {
            consultas.values().forEach(System.out::println);
        }
    }

    public Consulta buscarConsulta(int id) {
        return consultas.get(id);
    }

    public void agregarTemaAConsulta() {
        int idConsulta = Validar.leerEntero("Ingrese ID de la consulta: ");
        Consulta consulta = buscarConsulta(idConsulta);
        if (consulta == null) {
            System.out.println("Consulta no encontrada.");
            return;
        }

        int idTema = Validar.leerEntero("Ingrese ID del tema: ");
        String nombre = Validar.leerNoVacio("Ingrese nombre del tema: ", sc);

        Tema tema = new Tema(idTema, nombre);
        if (consulta.agregarTema(tema)) {
            System.out.println("Tema agregado a la consulta.");
        }
    }

    public void listarTemasDeConsulta() {
        int idConsulta = Validar.leerEntero("Ingrese ID de la consulta: ");
        Consulta consulta = buscarConsulta(idConsulta);
        if (consulta == null) {
            System.out.println("Consulta no encontrada.");
            return;
        }

        if (consulta.getTemas().isEmpty()) {
            System.out.println("No hay temas en esta consulta.");
        } else {
            consulta.getTemas().values().forEach(System.out::println);
        }
    }

    // ======== PREGUNTAS ========

    public void agregarPreguntaATema() {
        int idConsulta = Validar.leerEntero("Ingrese ID de la consulta: ");
        Consulta consulta = buscarConsulta(idConsulta);
        if (consulta == null) {
            System.out.println("Consulta no encontrada.");
            return;
        }

        String nombre = Validar.leerNoVacio("Ingrese nombre del tema: ", sc);
        Tema tema = consulta.getTemas().get(nombreTema);
        if (tema == null) {
            System.out.println("Tema no encontrado en esta consulta.");
            return;
        }

        int idPregunta = Validar.leerEntero("Ingrese ID de la pregunta: ");
        String enunciado = Validar.leerNoVacio("Ingrese enunciado de la pregunta: ", sc);

        Pregunta pregunta = new Pregunta(idPregunta, enunciado);
        if (tema.agregarPregunta(pregunta)) {
            System.out.println("Pregunta agregada al tema.");
        }
    }

    public void listarPreguntasDeTema() {
        int idConsulta = Validar.leerEntero("Ingrese ID de la consulta: ");
        Consulta consulta = buscarConsulta(idConsulta);
        if (consulta == null) {
            System.out.println("Consulta no encontrada.");
            return;
        }

        String nombre = Validar.leerNoVacio("Ingrese nombre del tema: ", sc);
        Tema tema = consulta.getTemas().get(nombreTema);
        if (tema == null) {
            System.out.println("Tema no encontrado en esta consulta.");
            return;
        }

        if (tema.getPreguntas().isEmpty()) {
            System.out.println("No hay preguntas en este tema.");
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
            System.out.println("No existe un ciudadano con ese RUT.");
            return;
        }

        // Buscar consulta
        int idConsulta = Validar.leerEntero("Ingrese ID de la consulta: ");
        Consulta consulta = buscarConsulta(idConsulta);
        if (consulta == null) {
            System.out.println("Consulta no encontrada.");
            return;
        }

        // Buscar tema
        String nombreTema = Validar.leerNoVacio("Ingrese nombre del tema: ", sc);
        Tema tema = consulta.getTemas().get(nombreTema);
        if (tema == null) {
            System.out.println("Tema no encontrado en esta consulta.");
            return;
        }

        // Buscar pregunta
        String enunciado = Validar.leerNoVacio("Ingrese enunciado de la pregunta: ", sc);
        Pregunta pregunta = tema.getPreguntas().get(enunciado);
        if (pregunta == null) {
            System.out.println("Pregunta no encontrada en este tema.");
            return;
        }

        // Registrar voto
        String respuesta = Validar.leerNoVacio("Ingrese su respuesta: ", sc);
        Voto voto = new Voto(ciudadano, respuesta);
        pregunta.agregarVoto(voto);
        System.out.println("Voto registrado correctamente.");
    }   
}

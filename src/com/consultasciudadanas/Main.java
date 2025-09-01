package com.consultasciudadanas;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Ciudadano> ciudadanos = new ArrayList<>();
        List<Consulta> consultas = new ArrayList<>();

        try {
            while (true) {
                System.out.println("\n--- MENÚ ---");
                System.out.println("1. Registrar ciudadano");
                System.out.println("2. Crear consulta");
                System.out.println("3. Votar en una pregunta");
                System.out.println("4. Ver resultados");
                System.out.println("5. Ver votantes y consultas");
                System.out.println("0. Salir");
                System.out.print("Opción: ");
                int op = sc.nextInt();
                sc.nextLine();

                if (op == 0) break;

                switch (op) {
                case 1:
                	
                    String rut = "";
                    while (true) {
                        System.out.print("RUT (solo números, si termina en K se convertirá en 0): ");
                        rut = sc.nextLine().trim().toUpperCase();

                        if (rut.endsWith("K")) {
                            rut = rut.substring(0, rut.length() - 1) + "0";
                        }

                        if (rut.matches("\\d+")) {
                            break; 
                        } else {
                            System.out.println("RUT inválido. Solo números o K al final permitido.");
                        }
                    }

                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Comuna: ");
                    String comuna = sc.nextLine();

                    int edad = 0;
                    while (true) {
                        System.out.print("Edad: ");
                        try {
                            edad = sc.nextInt();
                            if (edad < 0) {
                                System.out.println("Ingrese una edad válida.");
                                continue;
                            }
                            sc.nextLine();
                            break; 
                        } catch (InputMismatchException e) {
                            System.out.println("Error: debe ingresar un número entero.");
                            sc.nextLine();
                        }
                    }

                    ciudadanos.add(new Ciudadano(rut, nombre, comuna, edad));
                    System.out.println("Ciudadano registrado correctamente.");
                    break;


                    case 2:
                        System.out.print("ID Consulta: ");
                        int idc = sc.nextInt(); sc.nextLine();
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();
                        System.out.print("Fecha: ");
                        String fecha = sc.nextLine();
                        Consulta consulta = new Consulta(idc, titulo, fecha);
                        consultas.add(consulta);

                        System.out.print("¿Cuántos temas? ");
                        int nt = sc.nextInt(); sc.nextLine();
                        for (int i=0; i<nt; i++) {
                            System.out.print("ID Tema: ");
                            int idt = sc.nextInt(); sc.nextLine();
                            System.out.print("Nombre tema: ");
                            String tnombre = sc.nextLine();
                            Tema tema = new Tema(idt, tnombre);
                            consulta.agregarTema(tema);

                            System.out.print("¿Cuántas preguntas? ");
                            int np = sc.nextInt(); sc.nextLine();
                            for (int j=0; j<np; j++) {
                                System.out.print("ID Pregunta: ");
                                int idp = sc.nextInt(); sc.nextLine();
                                System.out.print("Enunciado: ");
                                String en = sc.nextLine();
                                tema.agregarPregunta(new Pregunta(idp, en));
                            }
                        }
                        break;
                    case 3:
                        if (consultas.isEmpty() || ciudadanos.isEmpty()) {
                            System.out.println("Debe existir al menos un ciudadano y una consulta.");
                            break;
                        }
                        System.out.println("Seleccione ciudadano: ");
                        for (int i=0; i<ciudadanos.size(); i++)
                            System.out.println((i+1) + ". " + ciudadanos.get(i));
                        int ci = sc.nextInt()-1; sc.nextLine();

                        System.out.println("Seleccione consulta: ");
                        for (int i=0; i<consultas.size(); i++)
                            System.out.println((i+1) + ". " + consultas.get(i));
                        int co = sc.nextInt()-1; sc.nextLine();
                        Consulta cons = consultas.get(co);

                        for (Tema tema : cons.getTemas()) {
                            for (Pregunta p : tema.getPreguntas()) {
                                System.out.println(p.getEnunciado() + " (SÍ/NO): ");
                                String resp = sc.nextLine();
                                p.agregarVoto(new Voto(ciudadanos.get(ci), resp));
                            }
                        }
                        break;
                    case 4:
                        if (consultas.isEmpty()) {
                            System.out.println("No existen consultas registradas.");
                        } else {
                            for (Consulta con : consultas) {
                                System.out.println("Consulta: " + con.getTitulo());
                                boolean tieneVotos = false;
                                for (Tema tema : con.getTemas()) {
                                    System.out.println("  Tema: " + tema.getNombre());
                                    for (Pregunta p : tema.getPreguntas()) {
                                        System.out.println("    Pregunta: " + p.getEnunciado());
                                        if (p.getVotos().isEmpty()) {
                                            System.out.println("      No hay votos aún.");
                                        } else {
                                            tieneVotos = true;
                                            p.mostrarResultados();
                                        }
                                    }
                                }
                                if (!tieneVotos) {
                                    System.out.println("  Ninguna pregunta de esta consulta tiene votos.");
                                }
                            }
                        }
                        break;

                    case 5:
                        if (consultas.isEmpty()) {
                            System.out.println("No existen consultas registradas.");
                        } else if (ciudadanos.isEmpty()) {
                            System.out.println("No existen ciudadanos registrados.");
                        } else {
                            System.out.println("Votantes y consultas:");
                            for (Consulta con : consultas) {
                                System.out.println("Consulta: " + con.getTitulo());
                                boolean tieneVotos = false;
                                for (Tema tema : con.getTemas()) {
                                    System.out.println("  Tema: " + tema.getNombre());
                                    for (Pregunta p : tema.getPreguntas()) {
                                        System.out.println("    Pregunta: " + p.getEnunciado());
                                        if (p.getVotos().isEmpty()) {
                                            System.out.println("      No hay votos aún.");
                                        } else {
                                            tieneVotos = true;
                                            for (Voto v : p.getVotos()) {
                                                System.out.println("      Votante: " + v.getCiudadano().getNombre() + " - Respuesta: " + v.getRespuesta());
                                            }
                                        }
                                    }
                                }
                                if (!tieneVotos) {
                                    System.out.println("  Ninguna pregunta de esta consulta tiene votos.");
                                }
                            }
                        }
                        break;

                }
            }
        } finally {
            sc.close();
        }
    }
}

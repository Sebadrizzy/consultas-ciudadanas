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
                System.out.println("0. Salir");
                System.out.print("Opción: ");
                int op = sc.nextInt();
                sc.nextLine();

                if (op == 0) break;

                switch (op) {
                    case 1:
                        System.out.print("RUT: ");
                        String rut = sc.nextLine();
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Comuna: ");
                        String comuna = sc.nextLine();
                        System.out.print("Edad: ");
                        int edad = sc.nextInt(); sc.nextLine();
                        ciudadanos.add(new Ciudadano(rut, nombre, comuna, edad));
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
                        for (Consulta con : consultas) {
                            System.out.println(con);
                            for (Tema tema : con.getTemas()) {
                                System.out.println("  " + tema);
                                for (Pregunta p : tema.getPreguntas()) {
                                    p.mostrarResultados();
                                }
                            }
                        }
                        break;
                }
            }
        } finally {
            sc.close(); // Cerramos el Scanner al final
        }
    }
}


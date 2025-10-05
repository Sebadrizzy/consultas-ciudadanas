package com.consultasciudadanas;

import java.io.*;
import java.util.List;

public class PersistenciaDatos {
    private static final String DIR = "datos/"; // Carpeta de datos

    private GestorCiudadanos gestorCiudadanos;
    private GestorConsultas gestorConsultas;

    public PersistenciaDatos(GestorCiudadanos gestorCiudadanos, GestorConsultas gestorConsultas) {
        this.gestorCiudadanos = gestorCiudadanos;
        this.gestorConsultas = gestorConsultas;

    }

    public void cargarDatos() {
        cargarCiudadanos();
        cargarConsultas();
        cargarVotos();
        System.out.println("✅ Datos cargados correctamente al iniciar.");
    }

    public void guardarDatos() {
        guardarCiudadanos();
        guardarConsultas();
        guardarVotos();
        System.out.println("✅ Todos los datos han sido guardados en archivos CSV.");
    }

    private void cargarCiudadanos() {
        try (BufferedReader br = new BufferedReader(new FileReader(DIR + "ciudadanos.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    String rut = datos[0];
                    String nombre = datos[1];
                    String comuna = datos[2];
                    int edad = Integer.parseInt(datos[3]);
                    Ciudadano c = new Ciudadano(rut, nombre, comuna, edad);
                    gestorCiudadanos.agregarCiudadano(c);
                }
            }
        } catch (IOException e) {
            System.out.println("No se encontró archivo ciudadanos.csv. Se creará uno nuevo en " + DIR);
        } catch (NumberFormatException e) {
            System.out.println("Error al leer edad en ciudadanos.csv.");
        }
    }

    private void guardarCiudadanos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DIR + "ciudadanos.csv"))) {
            for (Ciudadano c : gestorCiudadanos.getCiudadanos().values()) {
                pw.println(c.getRut() + "," + c.getNombre() + "," + c.getComuna() + "," + c.getEdad());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar ciudadanos.csv: " + e.getMessage());
        }
    }

    private void cargarConsultas() {
        try (BufferedReader br = new BufferedReader(new FileReader(DIR + "consultas.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    int id = Integer.parseInt(datos[0]);
                    String titulo = datos[1];
                    String fecha = datos[2];
                    Consulta c = new Consulta(id, titulo, fecha);
                    gestorConsultas.agregarConsulta(c);
                }
            }
        } catch (IOException e) {
            System.out.println("No se encontró archivo consultas.csv. Se creará uno nuevo en " + DIR);
        } catch (NumberFormatException e) {
            System.out.println("Error al leer ID en consultas.csv.");
        }
    }

    private void guardarConsultas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DIR + "consultas.csv"))) {
            for (Consulta c : gestorConsultas.getConsultas().values()) {
                pw.println(c.getId() + "," + c.getTitulo() + "," + c.getFecha());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar consultas.csv: " + e.getMessage());
        }
    }

    private void cargarVotos() {
        try (BufferedReader br = new BufferedReader(new FileReader(DIR + "votos.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    String rut = datos[0];
                    int idConsulta = Integer.parseInt(datos[1]);
                    String nombreTema = datos[2];
                    String enunciadoPregunta = datos[3];
                    String respuesta = datos[4];

                    Ciudadano ciudadano = gestorCiudadanos.getCiudadanos().get(rut);
                    if (ciudadano == null)
                        continue;

                    Consulta consulta = gestorConsultas.getConsultas().get(idConsulta);
                    if (consulta == null)
                        continue;

                    Tema tema = consulta.getTemas().get(nombreTema);
                    if (tema == null)
                        continue;

                    Pregunta pregunta = tema.getPreguntas().get(enunciadoPregunta);
                    if (pregunta == null)
                        continue;

                    pregunta.agregarVoto(new Voto(ciudadano, respuesta));
                }
            }
        } catch (IOException e) {
            System.out.println("No se encontró archivo votos.csv. Se creará uno nuevo en " + DIR);
        } catch (NumberFormatException e) {
            System.out.println("Error al leer ID en votos.csv.");
        }
    }

    private void guardarVotos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DIR + "votos.csv"))) {
            for (Consulta c : gestorConsultas.getConsultas().values()) {
                for (Tema t : c.getTemas().values()) {
                    for (Pregunta p : t.getPreguntas().values()) {
                        for (Voto v : p.getVotos()) {
                            pw.println(v.getCiudadano().getRut() + "," +
                                    c.getId() + "," +
                                    t.getNombre() + "," +
                                    p.getEnunciado() + "," +
                                    v.getRespuesta());
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar votos.csv: " + e.getMessage());
        }
    }

    public void generarReporteVotos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_votos.txt"))) {
            bw.write("REPORTE DE VOTOS - SISTEMA DE CONSULTAS CIUDADANAS\n");
            bw.write("==================================================\n\n");

            int totalVotos = 0;

            for (Consulta consulta : gestorConsultas.getConsultas().values()) {
                bw.write("CONSULTA: " + consulta.getTitulo() + " (" + consulta.getFecha() + ")\n");
                bw.write("ID: " + consulta.getId() + "\n\n");

                for (Tema tema : consulta.getTemas().values()) {
                    bw.write("  TEMA: " + tema.getNombre() + "\n\n");

                    for (Pregunta pregunta : tema.getPreguntas().values()) {
                        bw.write("    PREGUNTA: " + pregunta.getEnunciado() + "\n\n");

                        List<Voto> votos = pregunta.getVotos();
                        totalVotos += votos.size();

                        for (Voto voto : votos) {
                            Ciudadano c = voto.getCiudadano();
                            bw.write("      - Ciudadano: " + c.getNombre() +
                                    " (RUT: " + c.getRut() +
                                    ") | Respuesta: " + voto.getRespuesta() + "\n");
                        }

                        bw.write("\n");
                    }
                }
                bw.write("--------------------------------------------------\n\n");
            }

            bw.write("TOTAL DE VOTOS REGISTRADOS: " + totalVotos + "\n");
            System.out.println("Reporte generado: reporte_votos.txt");

        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }
}

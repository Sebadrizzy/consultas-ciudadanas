package com.consultasciudadanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InterfazGrafica extends JFrame {
    private GestorCiudadanos gestorCiudadanos;
    private GestorConsultas gestorConsultas;
    private DefaultTableModel tableModel;
    private JTable table;
    private PersistenciaDatos persistencia;

    public InterfazGrafica(GestorCiudadanos gestorCiudadanos, GestorConsultas gestorConsultas) {
        this.gestorCiudadanos = gestorCiudadanos;
        this.gestorConsultas = gestorConsultas;
        this.persistencia = new PersistenciaDatos(gestorCiudadanos, gestorConsultas);

        // Configuración de la ventana
        setTitle("Sistema de Consultas Ciudadanas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear tabla
        String[] columnas = { "RUT", "Nombre", "Comuna", "Edad" };
        tableModel = new DefaultTableModel(columnas, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar datos iniciales
        actualizarTabla();

        // Crear menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menuCiudadanos = new JMenu("Ciudadanos");
        JMenu menuConsultas = new JMenu("Consultas");
        JMenu menuArchivo = new JMenu("Archivo");

        // === Opciones del menú Ciudadanos ===
        JMenuItem crearCiudadano = new JMenuItem("Crear Ciudadano");
        JMenuItem mostrarCiudadanos = new JMenuItem("Mostrar Ciudadanos");
        JMenuItem editarCiudadano = new JMenuItem("Editar Ciudadano");
        JMenuItem eliminarCiudadano = new JMenuItem("Eliminar Ciudadano");

        crearCiudadano.addActionListener(e -> crearCiudadano());
        mostrarCiudadanos.addActionListener(e -> actualizarTabla());
        editarCiudadano.addActionListener(e -> editarCiudadano());
        eliminarCiudadano.addActionListener(e -> eliminarCiudadano());

        menuCiudadanos.add(crearCiudadano);
        menuCiudadanos.add(mostrarCiudadanos);
        menuCiudadanos.add(editarCiudadano);
        menuCiudadanos.add(eliminarCiudadano);

        // Filtrar por comuna
        JMenuItem filtrarPorComuna = new JMenuItem("Filtrar por Comuna");
        filtrarPorComuna.addActionListener(e -> filtrarPorComuna());
        menuCiudadanos.add(filtrarPorComuna);

        // === Generar Reporte de Votos ===
        JMenuItem generarReporte = new JMenuItem("Generar Reporte de Votos");
        generarReporte.addActionListener(e -> generarReporte());
        menuArchivo.add(generarReporte);

        // Buscar ciudadano
        JMenuItem buscarCiudadano = new JMenuItem("Buscar Ciudadano");
        buscarCiudadano.addActionListener(e -> buscarCiudadano());
        menuCiudadanos.add(buscarCiudadano);

        // Búsqueda avanzada
        JMenuItem buscarAvanzado = new JMenuItem("Búsqueda Avanzada");
        buscarAvanzado.addActionListener(e -> buscarAvanzado());
        menuCiudadanos.add(buscarAvanzado);

        // === Opciones del menú Consultas ===
        JMenuItem crearConsulta = new JMenuItem("Crear Consulta");
        JMenuItem agregarTema = new JMenuItem("Agregar Tema");
        JMenuItem agregarPregunta = new JMenuItem("Agregar Pregunta");
        JMenuItem agregarVoto = new JMenuItem("Agregar Voto");
        JMenuItem eliminarConsulta = new JMenuItem("Eliminar Consulta");
        JMenuItem eliminarTema = new JMenuItem("Eliminar Tema");
        JMenuItem eliminarPregunta = new JMenuItem("Eliminar Pregunta");

        crearConsulta.addActionListener(e -> crearConsulta());
        agregarTema.addActionListener(e -> agregarTema());
        agregarPregunta.addActionListener(e -> agregarPregunta());
        agregarVoto.addActionListener(e -> agregarVoto());
        eliminarConsulta.addActionListener(e -> eliminarConsulta());
        eliminarTema.addActionListener(e -> eliminarTema());
        eliminarPregunta.addActionListener(e -> eliminarPregunta());

        menuConsultas.add(crearConsulta);
        menuConsultas.add(agregarTema);
        menuConsultas.add(agregarPregunta);
        menuConsultas.add(agregarVoto);
        menuConsultas.addSeparator();
        menuConsultas.add(eliminarConsulta);
        menuConsultas.add(eliminarTema);
        menuConsultas.add(eliminarPregunta);

        // === Opción del menú Archivo ===
        JMenuItem salir = new JMenuItem("Salir");
        salir.addActionListener(e -> {
            PersistenciaDatos persistencia = new PersistenciaDatos(gestorCiudadanos, gestorConsultas);
            persistencia.guardarDatos();
            System.exit(0);
        });
        menuArchivo.add(salir);

        // Añadir menús a la barra
        menuBar.add(menuCiudadanos);
        menuBar.add(menuConsultas);
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
    }

    // === ACTUALIZAR TABLA ===
    private void actualizarTabla() {
        tableModel.setRowCount(0); // Limpiar tabla
        for (Ciudadano c : gestorCiudadanos.getCiudadanos().values()) {
            Object[] fila = { c.getRut(), c.getNombre(), c.getComuna(), c.getEdad() };
            tableModel.addRow(fila);
        }
    }

    // === CREAR CIUDADANO ===
    private void crearCiudadano() {
        JTextField rutField = new JTextField();
        JTextField nombreField = new JTextField();
        JTextField comunaField = new JTextField();
        JTextField edadField = new JTextField();

        Object[] message = {
                "RUT:", rutField,
                "Nombre:", nombreField,
                "Comuna:", comunaField,
                "Edad:", edadField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Crear Ciudadano", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String rut = rutField.getText().trim();
                String nombre = nombreField.getText().trim();
                String comuna = comunaField.getText().trim();
                int edad = Integer.parseInt(edadField.getText().trim());

                if (!rut.matches("\\d{8,9}")) {
                    JOptionPane.showMessageDialog(this,
                            "El RUT debe tener 8 o 9 dígitos numéricos (sin puntos ni guion).", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (gestorCiudadanos.getCiudadanos().containsKey(rut)) {
                    JOptionPane.showMessageDialog(this, "El RUT ya está registrado.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Ciudadano c = new Ciudadano(rut, nombre, comuna, edad);
                gestorCiudadanos.agregarCiudadano(c);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Ciudadano agregado correctamente.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Edad debe ser un número válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // === EDITAR CIUDADANO ===
    private void editarCiudadano() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un ciudadano para editar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String rut = (String) tableModel.getValueAt(selectedRow, 0);
        Ciudadano ciudadano = gestorCiudadanos.buscarCiudadano(rut);

        JTextField nombreField = new JTextField(ciudadano.getNombre());
        JTextField comunaField = new JTextField(ciudadano.getComuna());
        JTextField edadField = new JTextField(String.valueOf(ciudadano.getEdad()));

        Object[] message = {
                "Nombre:", nombreField,
                "Comuna:", comunaField,
                "Edad:", edadField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Editar Ciudadano", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = nombreField.getText().trim();
                String comuna = comunaField.getText().trim();
                int edad = Integer.parseInt(edadField.getText().trim());

                if (nombre.isEmpty() || comuna.isEmpty() || edad < 1) {
                    JOptionPane.showMessageDialog(this, "Todos los campos deben ser válidos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (gestorCiudadanos.editarCiudadano(rut, nombre, comuna, edad)) {
                    actualizarTabla();
                    JOptionPane.showMessageDialog(this, "Ciudadano actualizado correctamente.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "La edad debe ser un número válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Buscar ciudadano por RUT o nombre
    private void buscarCiudadano() {
        JTextField input = new JTextField();
        Object[] message = { "Ingrese RUT o nombre del ciudadano:", input };

        int option = JOptionPane.showConfirmDialog(this, message, "Buscar Ciudadano", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String criterio = input.getText().trim();
            if (criterio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El criterio de búsqueda no puede estar vacío.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Ciudadano> resultados = gestorCiudadanos.buscarCiudadanos(criterio);

            // Crear ventana con resultados
            JFrame resultFrame = new JFrame("Resultados de búsqueda");
            resultFrame.setSize(700, 300);
            resultFrame.setLocationRelativeTo(this);
            resultFrame.setLayout(new BorderLayout());

            String[] columnas = { "RUT", "Nombre", "Comuna", "Edad" };
            DefaultTableModel model = new DefaultTableModel(columnas, 0);
            JTable table = new JTable(model);
            JScrollPane scroll = new JScrollPane(table);
            resultFrame.add(scroll, BorderLayout.CENTER);

            for (Ciudadano c : resultados) {
                Object[] fila = { c.getRut(), c.getNombre(), c.getComuna(), c.getEdad() };
                model.addRow(fila);
            }

            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(resultFrame, "No se encontraron ciudadanos con el criterio: " + criterio,
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }

            resultFrame.setVisible(true);
        }
    }

    // === ELIMINAR CIUDADANO ===
    private void eliminarCiudadano() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un ciudadano para eliminar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String rut = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar al ciudadano con RUT " + rut + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (gestorCiudadanos.eliminarCiudadano(rut)) {
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Ciudadano eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Ciudadano no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // === CREAR CONSULTA ===
    private void crearConsulta() {
        JTextField idField = new JTextField();
        JTextField tituloField = new JTextField();
        JTextField fechaField = new JTextField();

        Object[] message = {
                "ID Consulta:", idField,
                "Título:", tituloField,
                "Fecha (dd-mm-aaaa):", fechaField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Crear Consulta", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String titulo = tituloField.getText().trim();
                String fecha = fechaField.getText().trim();

                if (titulo.isEmpty() || !fecha.matches("\\d{2}-\\d{2}-\\d{4}")) {
                    JOptionPane.showMessageDialog(this, "Título y fecha válida son obligatorios.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (gestorConsultas.getConsultas().containsKey(id)) {
                    JOptionPane.showMessageDialog(this, "Ya existe una consulta con ese ID.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Consulta consulta = new Consulta(id, titulo, fecha);
                gestorConsultas.agregarConsulta(consulta);
                JOptionPane.showMessageDialog(this, "Consulta creada correctamente.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID debe ser un número válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // === AGREGAR TEMA ===
    private void agregarTema() {
        JTextField idConsultaField = new JTextField();
        JTextField idTemaField = new JTextField();
        JTextField nombreTemaField = new JTextField();

        Object[] message = {
                "ID Consulta:", idConsultaField,
                "ID Tema:", idTemaField,
                "Nombre Tema:", nombreTemaField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Agregar Tema", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int idConsulta = Integer.parseInt(idConsultaField.getText().trim());
                int idTema = Integer.parseInt(idTemaField.getText().trim());
                String nombre = nombreTemaField.getText().trim();

                Consulta consulta = gestorConsultas.buscarConsulta(idConsulta);
                if (consulta == null) {
                    JOptionPane.showMessageDialog(this, "Consulta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Tema tema = new Tema(idTema, nombre);
                if (consulta.agregarTema(tema)) {
                    JOptionPane.showMessageDialog(this, "Tema agregado correctamente.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "IDs deben ser números válidos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // === AGREGAR PREGUNTA ===
    private void agregarPregunta() {
        JTextField idConsultaField = new JTextField();
        JTextField temaField = new JTextField();
        JTextField idPreguntaField = new JTextField();
        JTextField enunciadoField = new JTextField();

        Object[] message = {
                "ID Consulta:", idConsultaField,
                "Nombre Tema:", temaField,
                "ID Pregunta:", idPreguntaField,
                "Enunciado:", enunciadoField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Agregar Pregunta", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int idConsulta = Integer.parseInt(idConsultaField.getText().trim());
                String nombreTema = temaField.getText().trim();
                int idPregunta = Integer.parseInt(idPreguntaField.getText().trim());
                String enunciado = enunciadoField.getText().trim();

                Consulta consulta = gestorConsultas.buscarConsulta(idConsulta);
                if (consulta == null) {
                    JOptionPane.showMessageDialog(this, "Consulta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Tema tema = consulta.getTemas().get(nombreTema);
                if (tema == null) {
                    JOptionPane.showMessageDialog(this, "Tema no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Pregunta pregunta = new Pregunta(idPregunta, enunciado);
                if (tema.agregarPregunta(pregunta)) {
                    JOptionPane.showMessageDialog(this, "Pregunta agregada correctamente.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID de pregunta debe ser válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // === AGREGAR VOTO ===
    private void agregarVoto() {
        JTextField rutField = new JTextField();
        JTextField idConsultaField = new JTextField();
        JTextField temaField = new JTextField();
        JTextField preguntaField = new JTextField();
        JTextField respuestaField = new JTextField();

        Object[] message = {
                "RUT Ciudadano:", rutField,
                "ID Consulta:", idConsultaField,
                "Tema:", temaField,
                "Pregunta:", preguntaField,
                "Respuesta:", respuestaField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Agregar Voto", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String rut = rutField.getText().trim();
            Ciudadano ciudadano = gestorCiudadanos.buscarCiudadano(rut);
            if (ciudadano == null) {
                JOptionPane.showMessageDialog(this, "Ciudadano no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int idConsulta = Integer.parseInt(idConsultaField.getText().trim());
                Consulta consulta = gestorConsultas.buscarConsulta(idConsulta);
                if (consulta == null) {
                    JOptionPane.showMessageDialog(this, "Consulta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nombreTema = temaField.getText().trim();
                Tema tema = consulta.getTemas().get(nombreTema);
                if (tema == null) {
                    JOptionPane.showMessageDialog(this, "Tema no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String enunciado = preguntaField.getText().trim();
                Pregunta pregunta = tema.getPreguntas().get(enunciado);
                if (pregunta == null) {
                    JOptionPane.showMessageDialog(this, "Pregunta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String respuesta = respuestaField.getText().trim();
                Voto voto = new Voto(ciudadano, respuesta);
                pregunta.agregarVoto(voto);
                JOptionPane.showMessageDialog(this, "Voto registrado correctamente.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID de consulta debe ser válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // === ELIMINAR CONSULTA ===
    private void eliminarConsulta() {
        JTextField idField = new JTextField();
        Object[] message = { "ID de la consulta a eliminar:", idField };

        int option = JOptionPane.showConfirmDialog(this, message, "Eliminar Consulta", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Consulta consulta = gestorConsultas.buscarConsulta(id);
                if (consulta == null) {
                    JOptionPane.showMessageDialog(this, "Consulta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Eliminar la consulta '" + consulta.getTitulo() + "'?", "Confirmar",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    gestorConsultas.getConsultas().remove(id);
                    JOptionPane.showMessageDialog(this, "Consulta eliminada correctamente.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID debe ser un número válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // === ELIMINAR TEMA ===
    private void eliminarTema() {
        JTextField idConsultaField = new JTextField();
        JTextField temaField = new JTextField();

        Object[] message = {
                "ID Consulta:", idConsultaField,
                "Nombre del Tema:", temaField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Eliminar Tema", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int idConsulta = Integer.parseInt(idConsultaField.getText().trim());
                String nombreTema = temaField.getText().trim();

                Consulta consulta = gestorConsultas.buscarConsulta(idConsulta);
                if (consulta == null) {
                    JOptionPane.showMessageDialog(this, "Consulta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Tema tema = consulta.getTemas().remove(nombreTema);
                if (tema != null) {
                    JOptionPane.showMessageDialog(this, "Tema eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Tema no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID de consulta debe ser válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // === ELIMINAR PREGUNTA ===
    private void eliminarPregunta() {
        JTextField idConsultaField = new JTextField();
        JTextField temaField = new JTextField();
        JTextField preguntaField = new JTextField();

        Object[] message = {
                "ID Consulta:", idConsultaField,
                "Nombre Tema:", temaField,
                "Enunciado Pregunta:", preguntaField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Eliminar Pregunta", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int idConsulta = Integer.parseInt(idConsultaField.getText().trim());
                String nombreTema = temaField.getText().trim();
                String enunciado = preguntaField.getText().trim();

                Consulta consulta = gestorConsultas.buscarConsulta(idConsulta);
                if (consulta == null) {
                    JOptionPane.showMessageDialog(this, "Consulta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Tema tema = consulta.getTemas().get(nombreTema);
                if (tema == null) {
                    JOptionPane.showMessageDialog(this, "Tema no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Pregunta pregunta = tema.getPreguntas().remove(enunciado);
                if (pregunta != null) {
                    JOptionPane.showMessageDialog(this, "Pregunta eliminada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Pregunta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID de consulta debe ser un número válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void filtrarPorComuna() {
        JTextField comunaField = new JTextField();
        Object[] message = { "Ingrese la comuna a filtrar:", comunaField };

        int option = JOptionPane.showConfirmDialog(this, message, "Filtrar por Comuna", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String comuna = comunaField.getText().trim();
            if (comuna.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La comuna no puede estar vacía.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Ciudadano> filtrados = gestorCiudadanos.getCiudadanosPorComuna(comuna);

            // Crear nueva ventana con resultados
            JFrame resultFrame = new JFrame("Ciudadanos de " + comuna);
            resultFrame.setSize(600, 400);
            resultFrame.setLocationRelativeTo(this);
            resultFrame.setLayout(new BorderLayout());

            String[] columnas = { "RUT", "Nombre", "Comuna", "Edad" };
            DefaultTableModel model = new DefaultTableModel(columnas, 0);
            JTable table = new JTable(model);
            JScrollPane scroll = new JScrollPane(table);
            resultFrame.add(scroll, BorderLayout.CENTER);

            for (Ciudadano c : filtrados) {
                Object[] fila = { c.getRut(), c.getNombre(), c.getComuna(), c.getEdad() };
                model.addRow(fila);
            }

            if (filtrados.isEmpty()) {
                JOptionPane.showMessageDialog(resultFrame,
                        "No se encontraron ciudadanos en la comuna '" + comuna + "'.", "Sin resultados",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            resultFrame.setVisible(true);
        }
    }

    private void generarReporte() {
        int option = JOptionPane.showConfirmDialog(this,
                "¿Desea generar el reporte de votos en 'reporte_votos.txt'?",
                "Generar Reporte",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            persistencia.generarReporteVotos();
            JOptionPane.showMessageDialog(this, "Reporte generado correctamente.\nArchivo: reporte_votos.txt", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Búsqueda avanzada por comuna y edad
    private void buscarAvanzado() {
        JTextField comunaField = new JTextField();
        JTextField edadMinField = new JTextField("0");
        JTextField edadMaxField = new JTextField("0");

        Object[] message = {
                "Comuna (opcional):", comunaField,
                "Edad mínima (0 para ignorar):", edadMinField,
                "Edad máxima (0 para ignorar):", edadMaxField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Búsqueda Avanzada", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String comuna = comunaField.getText().trim();
            int edadMin = 0, edadMax = 0;

            try {
                String minText = edadMinField.getText().trim();
                String maxText = edadMaxField.getText().trim();
                edadMin = minText.isEmpty() ? 0 : Integer.parseInt(minText);
                edadMax = maxText.isEmpty() ? 0 : Integer.parseInt(maxText);

                if (edadMin < 0 || edadMax < 0) {
                    JOptionPane.showMessageDialog(this, "La edad no puede ser negativa.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingrese edades válidas o 0 para ignorar.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Ciudadano> resultados = gestorCiudadanos.buscarCiudadanos(
                    comuna.isEmpty() ? null : comuna,
                    edadMin,
                    edadMax);

            JFrame resultFrame = new JFrame("Búsqueda Avanzada - Resultados");
            resultFrame.setSize(700, 400);
            resultFrame.setLocationRelativeTo(this);
            resultFrame.setLayout(new BorderLayout());

            String[] columnas = { "RUT", "Nombre", "Comuna", "Edad" };
            DefaultTableModel model = new DefaultTableModel(columnas, 0);
            JTable table = new JTable(model);
            JScrollPane scroll = new JScrollPane(table);
            resultFrame.add(scroll, BorderLayout.CENTER);

            for (Ciudadano c : resultados) {
                Object[] fila = { c.getRut(), c.getNombre(), c.getComuna(), c.getEdad() };
                model.addRow(fila);
            }

            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(resultFrame,
                        "No se encontraron ciudadanos con los criterios especificados.",
                        "Sin resultados",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            resultFrame.setVisible(true);
        }
    }

}

package com.consultasciudadanas;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        GestorCiudadanos gestorCiudadanos = new GestorCiudadanos(null);
        GestorConsultas gestorConsultas = new GestorConsultas(null);

        // Cargar datos iniciales
        DatosIniciales datosIniciales = new DatosIniciales(gestorCiudadanos, gestorConsultas);
        datosIniciales.inicializarDatos();

        // Cargar datos desde archivos CSV
        PersistenciaDatos persistencia = new PersistenciaDatos(gestorCiudadanos, gestorConsultas);
        persistencia.cargarDatos();

        // Iniciar interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            InterfazGrafica interfaz = new InterfazGrafica(gestorCiudadanos, gestorConsultas);
            interfaz.setVisible(true);
        });
    }
}

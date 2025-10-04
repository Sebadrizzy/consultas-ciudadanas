package com.consultasciudadanas;

public class DatosIniciales {
    private GestorCiudadanos gestorCiudadanos;
    private GestorConsultas gestorConsultas;

    public DatosIniciales(GestorCiudadanos gestorCiudadanos, GestorConsultas gestorConsultas) {
        this.gestorCiudadanos = gestorCiudadanos;
        this.gestorConsultas = gestorConsultas;
    }

    public void inicializarDatos() {
        // Crear ciudadanos
        Ciudadano c1 = new Ciudadano("123456789", "Ana Pérez", "Santiago", 30);
        Ciudadano c2 = new Ciudadano("987654321", "Luis Gómez", "Valparaíso", 25);
        Ciudadano c3 = new Ciudadano("112233445", "Carla Díaz", "Concepción", 40);
        
        // Usar métodos públicos
        gestorCiudadanos.agregarCiudadano(c1);
        gestorCiudadanos.agregarCiudadano(c2);
        gestorCiudadanos.agregarCiudadano(c3);

        // Crear consulta
        Consulta consulta = new Consulta(1, "Plan de Desarrollo Urbano", "03-10-2025");
        gestorConsultas.agregarConsulta(consulta);

        // Crear tema
        Tema tema = new Tema(1, "Transporte Público");
        consulta.agregarTema(tema);

        // Crear pregunta
        Pregunta pregunta = new Pregunta(1, "¿Está de acuerdo con la ampliación del metro?");
        tema.agregarPregunta(pregunta);

        // Registrar votos
        pregunta.agregarVoto(new Voto(c1, "Sí"));
        pregunta.agregarVoto(new Voto(c2, "No"));
        pregunta.agregarVoto(new Voto(c3, "Sí"));
    }
}

package com.consultasciudadanas;

import java.util.Scanner;

public class Validar {
    public static String leerNoVacio(String mensaje, Scanner sc) {
        String valor;
        do {
            System.out.print(mensaje);
            valor = sc.nextLine().trim();
            if (valor.isEmpty()) {
                System.out.println("El campo no puede estar vacío. Intente de nuevo.");
            }else if(valor.length()>20 || valor.length()<3){
                System.out.println("El campo debe tener entre 3 y 20 caracteres. Intente de nuevo.");
                valor="";
            }
        } while (valor.isEmpty());
        return valor;
    }

    public static int leerEntero(String mensaje, Scanner sc) {
        int valor;
        while (true) {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(sc.nextLine().trim());
                if (valor < 1) {
                    System.out.println("Debe ser valor entero positivo minimo 1. Intente de nuevo.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }

    public static String leerFechaValida(String mensaje, Scanner sc) {
        String fecha;
        do {
            System.out.print(mensaje);
            fecha = sc.nextLine().trim();
            if (!fecha.matches("\\d{2}-\\d{2}-\\d{4}")) {
                System.out.println("Formato inválido. Use dd-mm-aaaa.");
                fecha = "";
            }
        } while (fecha.isEmpty());
        return fecha;
    }
}

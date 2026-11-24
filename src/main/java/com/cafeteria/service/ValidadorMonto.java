package com.cafeteria.service;

import java.util.regex.Pattern;

/**
 * Convierte el texto que escribe el usuario en un monto en pesos colombianos.
 *
 * Reglas:
 * - Solo pesos enteros (el peso colombiano no usa centavos).
 * - Se aceptan los puntos como separador de miles: 25000, 25.000, 1.500.000.
 * - Se rechaza todo lo demas: 25.00, 25,00, 5000,3000, -500, letras, vacio.
 */
public final class ValidadorMonto {

    /** Solo digitos (25000) o grupos de miles separados por punto (25.000, 1.500.000). */
    private static final Pattern FORMATO_PESOS = Pattern.compile("\\d+|\\d{1,3}(\\.\\d{3})+");

    /** Mensaje que se muestra cuando el monto no tiene un formato valido. */
    public static final String MENSAJE_FORMATO =
            "Monto invalido. Escribe pesos enteros, sin centavos (ejemplo: 25000 o 25.000).";

    private ValidadorMonto() {
    }

    /**
     * @param texto lo que el usuario escribio en el campo de monto
     * @return el monto en pesos
     * @throws IllegalArgumentException si el texto no es un monto valido
     */
    public static long convertir(String texto) {
        String limpio = (texto == null) ? "" : texto.trim();
        if (limpio.isEmpty()) {
            throw new IllegalArgumentException("Escribe el monto total del pedido.");
        }
        if (!FORMATO_PESOS.matcher(limpio).matches()) {
            throw new IllegalArgumentException(MENSAJE_FORMATO);
        }
        try {
            return Long.parseLong(limpio.replace(".", ""));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El monto es demasiado grande.");
        }
    }
}

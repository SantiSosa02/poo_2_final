package com.cafeteria.service;

import com.cafeteria.model.ReglaDescuento;

/**
 * Logica de negocio para aplicar una regla de descuento a un total.
 * No sabe nada de Swing ni de la base de datos: solo recibe una
 * ReglaDescuento (clase o lambda) y un total, y calcula.
 */
public class CalculadoraDescuento {

    private final ReglaDescuento regla;

    public CalculadoraDescuento(ReglaDescuento regla) {
        this.regla = regla;
    }

    public double obtenerDescuento(double totalPedido) {
        if (totalPedido < 0) {
            throw new IllegalArgumentException("El total del pedido no puede ser negativo");
        }
        return regla.calcularDescuento(totalPedido);
    }

    public double obtenerTotalConDescuento(double totalPedido) {
        return totalPedido - obtenerDescuento(totalPedido);
    }
}

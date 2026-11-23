package com.cafeteria;

import com.cafeteria.model.*;
import com.cafeteria.service.CalculadoraDescuento;

import java.util.Arrays;
import java.util.List;

/**
 * Demo de consola para la Unidad I: arma un pedido con distintos
 * Producto (Bebida y Comida) y muestra el polimorfismo en accion
 * (cada uno calcula su propio subtotal con calcularSubtotal()).
 *
 * La GUI (VentanaPrincipal) no usa esta clase directamente porque el
 * enunciado pide que la GUI trabaje solo con un monto total, pero esta
 * demo deja ver que el diseno de clases de la Unidad I si funciona.
 */
public class DemoPedido {

    public static void main(String[] args) {
        List<Producto> pedido = Arrays.asList(
                new Bebida("Cafe americano", 3.5, 2, Bebida.Tamano.MEDIANO),
                new Bebida("Jugo de naranja", 4.0, 1, Bebida.Tamano.GRANDE),
                new Comida("Sandwich de pollo", 6.5, 1, Comida.Categoria.PLATO_PRINCIPAL),
                new Comida("Brownie", 2.5, 2, Comida.Categoria.POSTRE)
        );

        System.out.println("=== Pedido ===");
        double total = 0;
        for (Producto p : pedido) {
            System.out.println(p); // usa describir() + calcularSubtotal() de forma polimorfica
            total += p.calcularSubtotal();
        }

        CalculadoraDescuento calculadora = new CalculadoraDescuento(ReglasDescuento.ESTANDAR);
        double descuento = calculadora.obtenerDescuento(total);
        double totalFinal = calculadora.obtenerTotalConDescuento(total);

        System.out.printf("%nTotal: $%.2f%n", total);
        System.out.printf("Descuento (regla estandar): $%.2f%n", descuento);
        System.out.printf("Total a pagar: $%.2f%n", totalFinal);
    }
}

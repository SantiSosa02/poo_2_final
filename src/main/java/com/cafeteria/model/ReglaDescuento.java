package com.cafeteria.model;

/**
 * Contrato que debe cumplir cualquier regla de descuento.
 *
 * Es una interfaz funcional a proposito: permite implementarla con una
 * clase normal (POO clasica) o con una expresion lambda (programacion
 * funcional), segun lo que pida la Unidad II.
 *
 * Para agregar una regla de descuento nueva NO hay que tocar esta interfaz
 * ni la clase que la usa (CalculadoraDescuento): basta con escribir una
 * nueva implementacion (lambda o clase). Eso es el principio abierto/cerrado.
 */
@FunctionalInterface
public interface ReglaDescuento {

    /**
     * @param totalPedido valor total del pedido antes de descuento
     * @return el valor del descuento (no el total con descuento restado)
     */
    double calcularDescuento(double totalPedido);
}

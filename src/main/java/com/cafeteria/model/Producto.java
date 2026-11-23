package com.cafeteria.model;

/**
 * Clase base de todo lo que se puede pedir en la cafeteria.
 * Bebida y Comida heredan de aqui (herencia + polimorfismo).
 */
public abstract class Producto {

    private final String nombre;
    private final double precioUnitario;
    private int cantidad;

    protected Producto(String nombre, double precioUnitario, int cantidad) {
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /** Subtotal de este producto (precio x cantidad). Cada subclase puede ajustarlo. */
    public double calcularSubtotal() {
        return precioUnitario * cantidad;
    }

    /** Texto corto para mostrar en la GUI o en consola. */
    public abstract String describir();

    @Override
    public String toString() {
        return describir() + String.format(" - $%.2f", calcularSubtotal());
    }
}

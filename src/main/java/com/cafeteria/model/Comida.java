package com.cafeteria.model;

/** Comidas (sandwiches, postres, platos...). Hereda de Producto. */
public class Comida extends Producto {

    public enum Categoria { ENTRADA, PLATO_PRINCIPAL, POSTRE }

    private final Categoria categoria;

    public Comida(String nombre, double precioUnitario, int cantidad, Categoria categoria) {
        super(nombre, precioUnitario, cantidad);
        this.categoria = categoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public String describir() {
        return "Comida: " + getNombre() + " (" + categoria + ") x" + getCantidad();
    }
}

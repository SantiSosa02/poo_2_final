package com.cafeteria.model;

/** Bebidas (cafe, jugos, gaseosas...). Hereda de Producto. */
public class Bebida extends Producto {

    public enum Tamano { PEQUENO, MEDIANO, GRANDE }

    private final Tamano tamano;

    public Bebida(String nombre, double precioUnitario, int cantidad, Tamano tamano) {
        super(nombre, precioUnitario, cantidad);
        this.tamano = tamano;
    }

    public Tamano getTamano() {
        return tamano;
    }

    @Override
    public double calcularSubtotal() {
        // las bebidas grandes tienen un recargo del 10% sobre el precio base
        double recargo = (tamano == Tamano.GRANDE) ? 1.10 : 1.0;
        return super.calcularSubtotal() * recargo;
    }

    @Override
    public String describir() {
        return "Bebida: " + getNombre() + " (" + tamano + ") x" + getCantidad();
    }
}

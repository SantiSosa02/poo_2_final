package com.cafeteria.model;

/**
 * Reglas de descuento ya definidas, cada una como una expresion lambda que
 * implementa ReglaDescuento. Si se necesita una regla nueva, se agrega aqui
 * (o en cualquier otra parte del codigo) sin modificar ReglaDescuento ni
 * CalculadoraDescuento: eso es justamente el principio abierto/cerrado.
 */
public final class ReglasDescuento {

    private ReglasDescuento() {
    }

    /** Regla pedida en el enunciado: total > $10 -> 15% de descuento; si no, 0%. */
    public static final ReglaDescuento ESTANDAR =
            total -> total > 10.0 ? total * 0.15 : 0.0;

    /** Ejemplo de regla adicional: 5% fijo para clientes frecuentes. */
    public static final ReglaDescuento CLIENTE_FRECUENTE =
            total -> total * 0.05;

    /** Ejemplo de regla adicional: $2 fijos de descuento en pedidos de $20 o mas. */
    public static final ReglaDescuento PEDIDO_GRANDE =
            total -> total >= 20.0 ? 2.0 : 0.0;

    /** Sin descuento, util como valor por defecto. */
    public static final ReglaDescuento SIN_DESCUENTO = total -> 0.0;
}

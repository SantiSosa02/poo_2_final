package com.cafeteria.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Reglas de descuento ya definidas, cada una como una expresion lambda que
 * implementa ReglaDescuento. Todos los montos estan en pesos colombianos (COP).
 *
 * Para agregar una regla nueva: se escribe otra lambda y se registra en
 * disponibles(). No hay que modificar ReglaDescuento, CalculadoraDescuento
 * ni la ventana: eso es justamente el principio abierto/cerrado.
 */
public final class ReglasDescuento {

    private ReglasDescuento() {
    }

    // --- Valores de la regla estandar ---
    /** El descuento estandar aplica a pedidos de mas de este valor. */
    public static final double MONTO_MINIMO_ESTANDAR = 10_000;
    /** Porcentaje del descuento estandar (15%). */
    public static final double PORCENTAJE_ESTANDAR = 0.15;

    // --- Valores de las reglas adicionales ---
    public static final double PORCENTAJE_CLIENTE_FRECUENTE = 0.05;
    public static final double MONTO_MINIMO_PEDIDO_GRANDE = 20_000;
    public static final double DESCUENTO_FIJO_PEDIDO_GRANDE = 2_000;

    /** Regla del enunciado: total > $10.000 -> 15% de descuento; si no, 0%. */
    public static final ReglaDescuento ESTANDAR =
            total -> total > MONTO_MINIMO_ESTANDAR ? total * PORCENTAJE_ESTANDAR : 0.0;

    /** 5% de descuento en cualquier pedido, para clientes frecuentes. */
    public static final ReglaDescuento CLIENTE_FRECUENTE =
            total -> total * PORCENTAJE_CLIENTE_FRECUENTE;

    /** $2.000 fijos de descuento en pedidos de $20.000 o mas. */
    public static final ReglaDescuento PEDIDO_GRANDE =
            total -> total >= MONTO_MINIMO_PEDIDO_GRANDE ? DESCUENTO_FIJO_PEDIDO_GRANDE : 0.0;

    /** Sin descuento. */
    public static final ReglaDescuento SIN_DESCUENTO = total -> 0.0;

    /**
     * Reglas que se muestran en el selector de la ventana, en orden.
     * La clave es el nombre visible; el valor es la regla (lambda).
     */
    public static Map<String, ReglaDescuento> disponibles() {
        Map<String, ReglaDescuento> reglas = new LinkedHashMap<>();
        reglas.put("Estandar (15% en pedidos de mas de $10.000)", ESTANDAR);
        reglas.put("Cliente frecuente (5% siempre)", CLIENTE_FRECUENTE);
        reglas.put("Pedido grande ($2.000 desde $20.000)", PEDIDO_GRANDE);
        reglas.put("Sin descuento", SIN_DESCUENTO);
        return Collections.unmodifiableMap(reglas);
    }
}

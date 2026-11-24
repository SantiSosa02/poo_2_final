package com.cafeteria.gui;

import com.cafeteria.model.ReglasDescuento;
import com.cafeteria.persistence.ConexionBD;
import com.cafeteria.service.CalculadoraDescuento;
import com.cafeteria.service.ValidadorMonto;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Ventana principal de la aplicacion: un JFrame con un JPanel que tiene
 * un JTextField para el monto, un JButton para calcular y un JTextArea
 * donde se muestran el resultado y los mensajes de conexion.
 */
public class VentanaPrincipal extends JFrame {

    private final JTextField campoMonto = new JTextField(10);
    private final JButton botonCalcular = new JButton("Calcular Descuento");
    private final JTextArea areaResultado = new JTextArea(10, 30);

    private final CalculadoraDescuento calculadora = new CalculadoraDescuento(ReglasDescuento.ESTANDAR);
    private final ConexionBD conexionBD = new ConexionBD();
    private final NumberFormat moneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    public VentanaPrincipal() {
        super("Cafeteria - Calculadora de Descuentos");
        moneda.setMaximumFractionDigits(0); // pesos colombianos: sin centavos
        construirInterfaz();
        probarConexionInicial();
    }

    private void construirInterfaz() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 420);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel panelEntrada = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEntrada.add(new JLabel("Monto total del pedido (COP):"));
        panelEntrada.add(campoMonto);
        panelEntrada.add(botonCalcular);

        areaResultado.setEditable(false);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(areaResultado);

        panel.add(panelEntrada, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        botonCalcular.addActionListener(e -> calcularDescuento());
        campoMonto.addActionListener(e -> calcularDescuento());

        setContentPane(panel);
    }

    private void probarConexionInicial() {
        String mensaje = conexionBD.probarConexion();
        areaResultado.append(mensaje + "\n");
    }

    /** Obtiene el monto, aplica la logica lambda y muestra el resultado (evento del boton). */
    private void calcularDescuento() {
        long total;
        try {
            total = ValidadorMonto.convertir(campoMonto.getText());
        } catch (IllegalArgumentException ex) {
            areaResultado.append(ex.getMessage() + "\n");
            return;
        }

        double descuento = calculadora.obtenerDescuento(total);
        double totalFinal = calculadora.obtenerTotalConDescuento(total);

        areaResultado.append(String.format(
                "Total pedido: %s | Descuento aplicado: %s | Total a pagar: %s%n",
                moneda.format(total), moneda.format(descuento), moneda.format(totalFinal)));

        String mensajeBD = conexionBD.guardarPedido(total, descuento, totalFinal);
        areaResultado.append(mensajeBD + "\n");
    }
}

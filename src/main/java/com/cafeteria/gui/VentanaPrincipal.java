package com.cafeteria.gui;

import com.cafeteria.model.ReglaDescuento;
import com.cafeteria.model.ReglasDescuento;
import com.cafeteria.persistence.ConexionBD;
import com.cafeteria.service.CalculadoraDescuento;
import com.cafeteria.service.ValidadorMonto;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * Ventana principal de la aplicacion: un JFrame con un JPanel que tiene
 * un selector de regla de descuento, un JTextField para el monto, un JButton
 * para calcular y un JTextArea donde se muestran el resultado y los mensajes
 * de conexion.
 */
public class VentanaPrincipal extends JFrame {

    private static final Locale COLOMBIA = new Locale("es", "CO");

    /** Reglas disponibles: nombre visible -> regla (lambda). */
    private final Map<String, ReglaDescuento> reglas = ReglasDescuento.disponibles();

    private final JComboBox<String> selectorRegla = new JComboBox<>(reglas.keySet().toArray(new String[0]));
    private final JTextField campoMonto = new JTextField(10);
    private final JButton botonCalcular = new JButton("Calcular Descuento");
    private final JTextArea areaResultado = new JTextArea(12, 40);

    private final ConexionBD conexionBD = new ConexionBD();
    private final NumberFormat moneda = NumberFormat.getCurrencyInstance(COLOMBIA);
    private final NumberFormat porcentaje = NumberFormat.getPercentInstance(COLOMBIA);

    public VentanaPrincipal() {
        super("Cafeteria - Calculadora de Descuentos");
        moneda.setMaximumFractionDigits(0);     // pesos colombianos: sin centavos
        porcentaje.setMaximumFractionDigits(1); // ej. 6,7 %
        construirInterfaz();
        probarConexionInicial();
    }

    private void construirInterfaz() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 440);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel filaRegla = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaRegla.add(new JLabel("Regla de descuento:"));
        filaRegla.add(selectorRegla);

        JPanel filaMonto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaMonto.add(new JLabel("Monto total del pedido (COP):"));
        filaMonto.add(campoMonto);
        filaMonto.add(botonCalcular);

        JPanel panelEntrada = new JPanel(new GridLayout(2, 1));
        panelEntrada.add(filaRegla);
        panelEntrada.add(filaMonto);

        areaResultado.setEditable(false);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(areaResultado);

        panel.add(panelEntrada, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        botonCalcular.addActionListener(e -> calcularDescuento());
        campoMonto.addActionListener(e -> calcularDescuento());
        // Al cambiar de regla con un monto ya escrito, se recalcula de inmediato
        selectorRegla.addActionListener(e -> {
            if (!campoMonto.getText().trim().isEmpty()) {
                calcularDescuento();
            }
        });

        setContentPane(panel);
    }

    private void probarConexionInicial() {
        String mensaje = conexionBD.probarConexion();
        areaResultado.append(mensaje + "\n");
    }

    /**
     * Evento del boton: 1) obtiene el monto del JTextField, 2) aplica la regla
     * (lambda) elegida en el selector y 3) muestra el resultado en la GUI.
     */
    private void calcularDescuento() {
        long total;
        try {
            total = ValidadorMonto.convertir(campoMonto.getText());
        } catch (IllegalArgumentException ex) {
            areaResultado.append(ex.getMessage() + "\n");
            return;
        }

        String nombreRegla = (String) selectorRegla.getSelectedItem();
        CalculadoraDescuento calculadora = new CalculadoraDescuento(reglas.get(nombreRegla));

        double descuento = calculadora.obtenerDescuento(total);
        double totalFinal = calculadora.obtenerTotalConDescuento(total);
        double porcentajeAplicado = (total == 0) ? 0 : descuento / total;

        areaResultado.append(String.format(
                "Total pedido: %s | Regla: %s | Descuento (%s): %s | Total a pagar: %s%n",
                moneda.format(total), nombreCorto(nombreRegla), porcentaje.format(porcentajeAplicado),
                moneda.format(descuento), moneda.format(totalFinal)));

        String mensajeBD = conexionBD.guardarPedido(total, descuento, totalFinal);
        areaResultado.append(mensajeBD + "\n");
    }

    /** "Cliente frecuente (5% siempre)" -> "Cliente frecuente". */
    private static String nombreCorto(String nombreRegla) {
        int parentesis = nombreRegla.indexOf(" (");
        return parentesis < 0 ? nombreRegla : nombreRegla.substring(0, parentesis);
    }
}

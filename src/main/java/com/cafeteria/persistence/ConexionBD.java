package com.cafeteria.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Maneja la conexion a una base de datos SQLite local (cafeteria.db).
 * Si el archivo no existe, SQLite lo crea solo al conectarse.
 */
public class ConexionBD {

    private static final String URL = "jdbc:sqlite:cafeteria.db";

    /**
     * Intenta conectarse a la BD y crear la tabla de pedidos si no existe.
     * Usa try-with-resources para que la conexion se cierre sola.
     *
     * @return un mensaje listo para mostrar en la GUI con el resultado.
     */
    public String probarConexion() {
        try (Connection conexion = DriverManager.getConnection(URL)) {
            crearTablaSiNoExiste(conexion);
            return "Conexion a la base de datos establecida correctamente (" + URL + ").";
        } catch (SQLException e) {
            return "No fue posible conectar a la base de datos: " + e.getMessage();
        }
    }

    private void crearTablaSiNoExiste(Connection conexion) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS pedidos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "total REAL NOT NULL, " +
                "descuento REAL NOT NULL, " +
                "total_final REAL NOT NULL, " +
                "fecha TEXT DEFAULT CURRENT_TIMESTAMP)";
        try (Statement st = conexion.createStatement()) {
            st.execute(sql);
        }
    }

    /**
     * Guarda el resultado de un calculo de descuento en la tabla pedidos.
     * También usa try-with-resources; si algo falla, se captura la
     * excepcion y se devuelve un mensaje en vez de dejar que la app se caiga.
     */
    public String guardarPedido(double total, double descuento, double totalFinal) {
        String sql = "INSERT INTO pedidos (total, descuento, total_final) VALUES (?, ?, ?)";
        try (Connection conexion = DriverManager.getConnection(URL);
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setDouble(1, total);
            ps.setDouble(2, descuento);
            ps.setDouble(3, totalFinal);
            ps.executeUpdate();
            return "Pedido guardado en la base de datos.";
        } catch (SQLException e) {
            return "No se pudo guardar el pedido: " + e.getMessage();
        }
    }
}

package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Credenciales: cooperativa1 y 12345678
    private static final String URL = "jdbc:mysql://localhost:3306/cooperativa1?serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASS = "12345678";

    public static Connection getConexion() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            // En un entorno real, aquí lanzarías una excepción de negocio.
        }
        return connection;
    }

    public static void closeConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
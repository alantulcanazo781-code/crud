package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    // ... Asegúrate de que los parámetros de URL, USER y PASSWORD estén correctos ...
    private static final String URL = "jdbc:mysql://localhost:3306/cooperativa1?useSSL=false&serverTimezone=UTC"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "12345678"; // ⚠️ ¡Tu Contraseña aquí!

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Driver moderno
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver JDBC. Asegúrate de incluir la librería Connector/J.");
            throw new SQLException("Driver de BD no encontrado.", e);
        }
    }
}
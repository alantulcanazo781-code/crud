package controlador; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Clase utilitaria con el método estático para obtener la conexión.
 */
public class ConexionBDD {
    
    // Configuración de la Base de Datos (confirmada por ti)
    private static final String URL = "jdbc:mysql://localhost:3306/cooperativa?autoReconnect=true&useSSL=false";
    private static final String USER = "root"; 
    private static final String PASS = "12345678"; 

    // Método estático para obtener la conexión (patrón correcto)
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // 1. Cargar el Driver MODERNO
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            
            // 2. Establecer la conexión
            conn = DriverManager.getConnection(URL, USER, PASS);
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "❌ ERROR: Driver MySQL no encontrado. Verifique el POM o las Librerías.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ ERROR CRÍTICO DE CONEXIÓN SQL. \nVerifique MySQL server activo y credenciales. \nMensaje: " + e.getMessage());
        }
        return conn;
    }
}
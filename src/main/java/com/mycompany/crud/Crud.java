package com.mycompany.crud;

import vista.UsuarioVista;
import controlador.ControladorUsuario;
import controlador.ConexionBDD; // Importamos la clase de conexión
import java.sql.Connection;

/**
 * Clase principal que contiene el método main para iniciar la aplicación.
 */
public class Crud { 

    public static void main(String[] args) {
        
        // 1. **TEST DE CONEXIÓN EXPLICITO**
        // Llama a getConnection. Esto inmediatamente imprime el mensaje de éxito 
        // ("✅ ÉXITO: Conectado a la base de datos cooperativa.") si funciona,
        // o un JOptionPane de error si falla.
        Connection testConn = ConexionBDD.getConnection();
        
        if (testConn == null) {
            System.err.println("❌❌ INICIO FALLIDO. La conexión no es válida. La aplicación no se ejecutará.");
            // Detenemos la ejecución si la conexión no se pudo establecer.
            return; 
        }
        
        // 2. **SI EL TEST FUE EXITOSO**, procedemos a la lógica MVC y UI.
        UsuarioVista vista = new UsuarioVista();
        ControladorUsuario controlador = new ControladorUsuario(vista);
        
        vista.setControlador(controlador);
        controlador.iniciar(); // Carga la interfaz de usuario ("ingresar datos")
    }
}
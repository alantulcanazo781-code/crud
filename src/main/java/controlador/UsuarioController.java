package controlador;


import modelo.Usuario;
import modelo.ConexionBD; 
import java.sql.*;


public class UsuarioController {
    
    // ... (Código de ValidacionUtil omitido por brevedad, se mantiene igual) ...

    public UsuarioController() {
        // Inicialización
    }

    // --- Lógica para btnCrear (Se mantiene igual) ---
    public String crearUsuario(String nombres, String apellidos, String cedula, String edadStr, String email) {
        // ... (Validaciones y código de INSERT se mantienen igual) ...
        // [CÓDIGO DE CREAR ANTERIOR]
        
        // 1. Validaciones de Datos
        if (!ValidacionUtil.esSoloTexto(nombres) || !ValidacionUtil.esSoloTexto(apellidos)) {
            return "Error: Nombre y Apellido deben contener solo letras.";
        }
        if (!ValidacionUtil.esEdadValida(edadStr)) {
            return "Error: La edad debe ser un número válido.";
        }
        if (!ValidacionUtil.esEmailValido(email)) {
            return "Error: El formato del email no es válido.";
        }
        if (!ValidacionUtil.esCedulaValida(cedula)) {
            return "Error: El número de cédula no es válido según el algoritmo.";
        }
        
        // 2. Ejecución SQL (Lógica DAO integrada)
        String sql = "INSERT INTO usuarios (nombres, apellidos, cedula, edad, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int edad = Integer.parseInt(edadStr);
            
            ps.setString(1, nombres);
            ps.setString(2, apellidos);
            ps.setString(3, cedula);
            ps.setInt(4, edad);
            ps.setString(5, email);

            if (ps.executeUpdate() > 0) {
                return "Usuario creado exitosamente.";
            } else {
                return "Error DB: No se pudo crear el usuario.";
            }

        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) { 
                return "Error DB: Cédula o Email ya se encuentran registrados.";
            }
            System.err.println("Error DB (CREAR): " + e.getMessage());
            return "Error interno al guardar en la base de datos: " + e.getMessage();
        }
    }

    // --- Lógica para btnBuscar (Se mantiene igual) ---
    public Usuario buscarUsuarioPorCedula(String cedula) {
        // ... (Validaciones y código de SELECT se mantienen igual) ...
        // [CÓDIGO DE BUSCAR ANTERIOR]
        if (!ValidacionUtil.esCedulaValida(cedula)) {
            return null; 
        }
        
        String sql = "SELECT id, nombres, apellidos, edad, email FROM usuarios WHERE cedula = ?";
        Usuario usuario = null;

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        cedula, 
                        rs.getInt("edad"),
                        rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB (BUSCAR): " + e.getMessage());
        }
        return usuario;
    }
    
    // --- Lógica para btnActualizar (Se mantiene igual) ---
    public String actualizarUsuario(String nombres, String apellidos, String cedula, String edadStr, String email) {
        // ... (Validaciones y código de UPDATE se mantienen igual) ...
        // [CÓDIGO DE ACTUALIZAR ANTERIOR]
        if (!ValidacionUtil.esSoloTexto(nombres) || !ValidacionUtil.esSoloTexto(apellidos) || 
            !ValidacionUtil.esEdadValida(edadStr) || !ValidacionUtil.esEmailValido(email) || 
            !ValidacionUtil.esCedulaValida(cedula)) {
            return "Error: Los datos ingresados para la actualización no son válidos.";
        }
        
        String sql = "UPDATE usuarios SET nombres = ?, apellidos = ?, edad = ?, email = ? WHERE cedula = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int edad = Integer.parseInt(edadStr);

            ps.setString(1, nombres);
            ps.setString(2, apellidos);
            ps.setInt(3, edad);
            ps.setString(4, email);
            ps.setString(5, cedula); 

            if (ps.executeUpdate() > 0) {
                return "Usuario actualizado exitosamente.";
            } else {
                return "Error DB: No se pudo actualizar el usuario (Registro no encontrado).";
            }

        } catch (SQLException e) {
            System.err.println("Error DB (ACTUALIZAR): " + e.getMessage());
            return "Error interno al actualizar en la base de datos.";
        }
    }

    // --- NUEVO: Lógica para btnBorrar (Implementación de Eliminar) ---
    public String eliminarUsuario(String cedula) {
        // 1. Validación (Usamos la cédula cargada en el formulario)
        if (!ValidacionUtil.esCedulaValida(cedula)) {
            return "Error: Cédula no válida para eliminar.";
        }

        // 2. Ejecución SQL
        String sql = "DELETE FROM usuarios WHERE cedula = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cedula);

            if (ps.executeUpdate() > 0) {
                return "Usuario eliminado exitosamente.";
            } else {
                return "Error DB: No se pudo eliminar el usuario (Registro no encontrado).";
            }

        } catch (SQLException e) {
            System.err.println("Error DB (ELIMINAR): " + e.getMessage());
            return "Error interno al eliminar de la base de datos.";
        }
    }
}
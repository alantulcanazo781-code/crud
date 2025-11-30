package controlador;

import vista.UsuarioVista; 
import modelo.Usuario; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane; 

/**
 * Controlador que maneja la lógica de negocio y usa la conexión a la BDD.
 */
public class ControladorUsuario {
    
    private final UsuarioVista vista;
    
    // Uso de la conexión estática
    Connection conectado = ConexionBDD.getConnection(); 
    
    PreparedStatement ejecutar;
    ResultSet res; 
    
    // CONSTRUCTOR
    public ControladorUsuario(UsuarioVista vista) {
        this.vista = vista;
        // Muestra error en consola si la conexión falló
        if (conectado == null) {
            System.err.println("❌ Error CRÍTICO: El controlador no pudo inicializar la conexión.");
        }
    }
    
    // MÉTODO PARA INICIAR LA VISTA
    public void iniciar() {
        vista.setTitle("FORMULARIO DE GESTIÓN DE USUARIOS");
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }
    
    // 1. CREAR (INSERT)
    public void crearUsuario() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(vista.getCampoNombre());
        nuevoUsuario.setApellido(vista.getCampoApellido());
        nuevoUsuario.setEmail(vista.getCampoEmail());
        nuevoUsuario.setContrasena(vista.getCampoClave());
        
        if (nuevoUsuario.getNombre().isEmpty() || nuevoUsuario.getEmail().isEmpty() || nuevoUsuario.getContrasena().isEmpty()) {
            vista.mostrarMensaje("⚠️ Faltan campos obligatorios.");
            return;
        }

        String sentenciaSQL = "INSERT INTO usuario (nombre, apellido, email, contrasena, rol) VALUES (?, ?, ?, ?, 'socio')"; 

        try {
            ejecutar = conectado.prepareStatement(sentenciaSQL);
            ejecutar.setString(1, nuevoUsuario.getNombre());
            ejecutar.setString(2, nuevoUsuario.getApellido());
            ejecutar.setString(3, nuevoUsuario.getEmail());
            ejecutar.setString(4, nuevoUsuario.getContrasena());
            
            int resu = ejecutar.executeUpdate();
            
            if (resu > 0) {
                vista.mostrarMensaje("✅ Usuario Creado con éxito.");
                vista.limpiarCampos();
            } else {
                vista.mostrarMensaje("❌ El Usuario no ha sido creado.");
            }
            ejecutar.close();
            
        } catch (SQLException e) {
            vista.mostrarMensaje("❌ Error al crear usuario: " + e.getMessage());
        }
    }
    
    // 2. BUSCAR (READ)
    public void buscarUsuario() {
        String idStr = vista.getCampoId();
        if (idStr.isEmpty()) {
            vista.mostrarMensaje("️ Por favor, ingrese el ID a buscar.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            String sentenciaSQL = "SELECT id, nombre, apellido, email, contrasena FROM usuario WHERE id = ?";
            
            ejecutar = conectado.prepareStatement(sentenciaSQL);
            ejecutar.setInt(1, id);
            res = ejecutar.executeQuery();
            
            if (res.next()) {
                vista.setCampos(
                    res.getInt("id"), 
                    res.getString("nombre"),
                    res.getString("apellido"),
                    res.getString("email"),
                    res.getString("contrasena") 
                );
                vista.mostrarMensaje("✅ Usuario encontrado.");
            } else {
                vista.mostrarMensaje("❌ Usuario con ID " + id + " no encontrado.");
                vista.limpiarCampos();
            }
            ejecutar.close();
            
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("⚠️ ERROR: El ID debe ser un número entero válido.");
        } catch (SQLException e) {
            vista.mostrarMensaje("❌ Error al buscar usuario: " + e.getMessage());
        }
    }
    
    // 3. ACTUALIZAR (UPDATE)
    public void actualizarUsuario() {
        Usuario usuarioAActualizar = new Usuario();
        
        String idStr = vista.getCampoId();
        if (idStr.isEmpty()) {
            vista.mostrarMensaje("️ Por favor, ingrese el ID a actualizar.");
            return;
        }
        
        try {
            usuarioAActualizar.setId(Integer.parseInt(idStr));
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("⚠️ ERROR: El ID debe ser un número entero válido para actualizar.");
            return;
        }
        
        usuarioAActualizar.setNombre(vista.getCampoNombre());
        usuarioAActualizar.setApellido(vista.getCampoApellido());
        usuarioAActualizar.setEmail(vista.getCampoEmail());
        usuarioAActualizar.setContrasena(vista.getCampoClave());
        
        if (usuarioAActualizar.getNombre().isEmpty() || usuarioAActualizar.getEmail().isEmpty() || usuarioAActualizar.getContrasena().isEmpty()) {
            vista.mostrarMensaje("⚠️ Faltan campos obligatorios para actualizar.");
            return;
        }

        String sentenciaSQL = "UPDATE usuario SET nombre=?, apellido=?, email=?, contrasena=? WHERE id=?";

        try {
            ejecutar = conectado.prepareStatement(sentenciaSQL);
            ejecutar.setString(1, usuarioAActualizar.getNombre());
            ejecutar.setString(2, usuarioAActualizar.getApellido());
            ejecutar.setString(3, usuarioAActualizar.getEmail());
            ejecutar.setString(4, usuarioAActualizar.getContrasena());
            ejecutar.setInt(5, usuarioAActualizar.getId()); 
            
            int resu = ejecutar.executeUpdate();
            
            if (resu > 0) {
                vista.mostrarMensaje("✅ Usuario Actualizado con éxito.");
                vista.limpiarCampos();
            } else {
                vista.mostrarMensaje("❌ El Usuario con ID " + usuarioAActualizar.getId() + " no existe o no se ha podido actualizar.");
            }
            ejecutar.close();
            
        } catch (SQLException e) {
            vista.mostrarMensaje("❌ Error al actualizar usuario: " + e.getMessage());
        }
    }
    
    // 4. ELIMINAR (DELETE)
    public void eliminarUsuario() {
        String idStr = vista.getCampoId();
        if (idStr.isEmpty()) {
            vista.mostrarMensaje("️ Por favor, ingrese el ID del usuario a eliminar.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            String sentenciaSQL = "DELETE FROM usuario WHERE id = ?";
            
            ejecutar = conectado.prepareStatement(sentenciaSQL);
            ejecutar.setInt(1, id);
            
            int resu = ejecutar.executeUpdate();
            
            if (resu > 0) {
                vista.mostrarMensaje("✅ Usuario con ID " + id + " Eliminado con éxito.");
                vista.limpiarCampos();
            } else {
                vista.mostrarMensaje("❌ El Usuario con ID " + id + " no existe o no se ha podido eliminar.");
            }
            ejecutar.close();
            
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("⚠️ ERROR: El ID debe ser un número entero válido.");
        } catch (SQLException e) {
            vista.mostrarMensaje("❌ Error al eliminar usuario: " + e.getMessage());
        }
    }
}
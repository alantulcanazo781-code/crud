package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {

    // 1. ATRIBUTOS
    private int id;
    private String nombres;
    private String apellidos;
    private String cedula;
    private int edad;
    private String email;

    // 2. CONSTRUCTORES
    public Usuario() {
    }

    public Usuario(int id, String nombres, String apellidos, String cedula, int edad, String email) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.edad = edad;
        this.email = email;
    }

    // 3. GETTERS Y SETTERS
    public int getId() { return id; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCedula() { return cedula; }
    public int getEdad() { return edad; }
    public String getEmail() { return email; }

    public void setId(int id) { this.id = id; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setEmail(String email) { this.email = email; }

    // 4. MÉTODOS CRUD (Lógica de Negocio y BD)

    public boolean crear() {
        // ⚠️ CORREGIDO: Usamos la tabla 'usuarios' (en plural)
        String sql = "INSERT INTO usuarios (nombres, apellidos, cedula, edad, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, this.nombres);
            ps.setString(2, this.apellidos);
            ps.setString(3, this.cedula);
            ps.setInt(4, this.edad);
            ps.setString(5, this.email);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean buscar(String cedulaABuscar) {
        // ⚠️ CORREGIDO: Usamos la tabla 'usuarios' (en plural)
        String sql = "SELECT id, nombres, apellidos, edad, email FROM usuarios WHERE cedula = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cedulaABuscar);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    this.id = rs.getInt("id");
                    this.nombres = rs.getString("nombres");
                    this.apellidos = rs.getString("apellidos");
                    this.cedula = cedulaABuscar; 
                    this.edad = rs.getInt("edad");
                    this.email = rs.getString("email");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return false;
    }

    public boolean actualizar() {
        // ⚠️ CORREGIDO: Usamos la tabla 'usuarios' (en plural)
        String sql = "UPDATE usuarios SET nombres = ?, apellidos = ?, cedula = ?, edad = ?, email = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, this.nombres);
            ps.setString(2, this.apellidos);
            ps.setString(3, this.cedula);
            ps.setInt(4, this.edad);
            ps.setString(5, this.email);
            ps.setInt(6, this.id); // Condición WHERE

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar() {
        // ⚠️ CORREGIDO: Usamos la tabla 'usuarios' (en plural)
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, this.id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}
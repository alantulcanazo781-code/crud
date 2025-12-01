package modelo;

public class Usuario {
    private int id; 
    private String nombres;
    private String apellidos;
    private String cedula;
    private int edad;
    private String email;

    // Constructor sin ID (para CREAR)
    public Usuario(String nombres, String apellidos, String cedula, int edad, String email) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.edad = edad;
        this.email = email;
    }

    // Constructor con ID (para LEER/ACTUALIZAR)
    public Usuario(int id, String nombres, String apellidos, String cedula, int edad, String email) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.edad = edad;
        this.email = email;
    }
    
    // --- Getters y Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
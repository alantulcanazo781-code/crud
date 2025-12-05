package controlador;

import modelo.Usuario;
import vista.UsuarioVista;

public class ControladorUsuario {

    private final UsuarioVista vista;
    private final Usuario modelo;

    public ControladorUsuario(UsuarioVista vista, Usuario modelo) {
        this.vista = vista;
        this.modelo = modelo;
        vista.agregarListeners(this);
    }

    public void iniciar() {
        vista.setTitle("Gestión de Usuarios MVC");
        vista.setLocationRelativeTo(null); 
        vista.setVisible(true);
    }

    public void crearUsuario() {
        String nombres = vista.getCampoNombres();
        String apellidos = vista.getCampoApellidos();
        String cedula = vista.getCampoCedula();
        String edadStr = vista.getCampoEdad();
        String email = vista.getCampoEmail();
        
        if (nombres.isEmpty() || cedula.isEmpty() || edadStr.isEmpty()) {
            vista.mostrarMensaje("❌ Error: Los campos Nombres, Cédula y Edad son obligatorios.");
            return;
        }
        
        try {
            int edad = Integer.parseInt(edadStr);
            
            modelo.setNombres(nombres);
            modelo.setApellidos(apellidos);
            modelo.setCedula(cedula);
            modelo.setEdad(edad);
            modelo.setEmail(email);

            if (modelo.crear()) {
                vista.mostrarMensaje("✅ Usuario Creado con éxito.");
                vista.limpiarCampos();
            } else {
                vista.mostrarMensaje("❌ Error al crear usuario. (La Cédula o Email podrían ya existir en la BD).");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("❌ Error: El campo Edad debe ser un número entero válido.");
        }
    }

    public void buscarUsuario() {
        String cedulaABuscar = vista.getCampoCedula();
        
        if (cedulaABuscar.isEmpty()) {
            vista.mostrarMensaje("⚠ Por favor, ingrese la Cédula del usuario a buscar.");
            return;
        }

        if (modelo.buscar(cedulaABuscar)) {
            vista.setCampos(
                modelo.getId(),
                modelo.getNombres(),
                modelo.getApellidos(),
                modelo.getCedula(),
                modelo.getEdad(),
                modelo.getEmail()
            );
            vista.mostrarMensaje("🔍 Usuario encontrado con ID: " + modelo.getId());
        } else {
            vista.mostrarMensaje("❌ Error: No se encontró ningún usuario con la Cédula: " + cedulaABuscar);
            vista.limpiarCampos();
            vista.getTxtCedula().setText(cedulaABuscar); // Deja la cédula para que el usuario pueda intentarlo de nuevo
        }
    }

    public void actualizarUsuario() {
        String idStr = vista.getCampoId();
        if (idStr.isEmpty()) {
            vista.mostrarMensaje("⚠ Error: Primero debe buscar un usuario antes de actualizarlo.");
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            String nombres = vista.getCampoNombres();
            String apellidos = vista.getCampoApellidos();
            String cedula = vista.getCampoCedula();
            int edad = Integer.parseInt(vista.getCampoEdad());
            String email = vista.getCampoEmail();

            modelo.setId(id);
            modelo.setNombres(nombres);
            modelo.setApellidos(apellidos);
            modelo.setCedula(cedula);
            modelo.setEdad(edad);
            modelo.setEmail(email);

            if (modelo.actualizar()) {
                vista.mostrarMensaje("🔄 Usuario actualizado con éxito.");
                vista.limpiarCampos();
            } else {
                vista.mostrarMensaje("❌ Error al actualizar usuario. (Podría haber una cédula o email duplicado).");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("❌ Error de formato: Asegúrese de que ID y Edad sean números válidos.");
        }
    }

    public void eliminarUsuario() {
        String idStr = vista.getCampoId();
        if (idStr.isEmpty()) {
            vista.mostrarMensaje("⚠ Error: Primero debe buscar un usuario para poder eliminarlo.");
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            modelo.setId(id);
            
            if (modelo.eliminar()) {
                vista.mostrarMensaje("🗑️ Usuario con ID " + id + " eliminado con éxito.");
                vista.limpiarCampos();
            } else {
                vista.mostrarMensaje("❌ Error al eliminar usuario.");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("❌ Error: El campo ID debe ser un número válido.");
        }
    }
// Dentro de controlador/ControladorUsuario.java

private boolean validarCedulaEcuatoriana(String cedula) {
    // 1. Longitud
    if (cedula.length() != 10) {
        return false;
    }

    // 2. Comprobar que solo contenga dígitos
    try {
        Long.valueOf(cedula);
    } catch (NumberFormatException e) {
        return false;
    }

    // 3. Obtener el dígito verificador y el código provincial
    int digitoVerificador = Integer.parseInt(cedula.substring(9, 10));
    int codigoProvincia = Integer.parseInt(cedula.substring(0, 2));

    // 4. Validar código de provincia (de 01 a 24)
    if (codigoProvincia < 1 || codigoProvincia > 24) {
        return false;
    }

    // 5. Aplicar el algoritmo de chequeo (coeficientes: 2, 1, 2, 1, ...)
    int total = 0;
    int[] coeficientes = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };

    for (int i = 0; i < 9; i++) {
        int digito = Integer.parseInt(cedula.substring(i, i + 1));
        int valor = digito * coeficientes[i];
        
        // Si el resultado es > 9, se le resta 9 (p. ej., 12 -> 3)
        if (valor >= 10) {
            valor -= 9;
        }
        total += valor;
    }

    // 6. Calcular el dígito de control
    // Se busca la decena superior (p. ej., si total es 23, la decena es 30)
    int decenaSuperior = ((total / 10) + 1) * 10;
    int digitoControl = decenaSuperior - total;

    // Si el dígito de control es 10, debe ser 0.
    if (digitoControl == 10) {
        digitoControl = 0;
    }
    
    // 7. Comparar con el dígito verificador original
    return digitoControl == digitoVerificador;
}
}
package controlador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    );
    
    public static boolean esSoloTexto(String texto) {
        return texto != null && !texto.trim().isEmpty() && texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    public static boolean esEmailValido(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean esEdadValida(String edadStr) {
        try {
            int edad = Integer.parseInt(edadStr);
            return edad > 0 && edad <= 120; 
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // --- Implementación del Algoritmo de Cédula Ecuatoriana ---
    public static boolean esCedulaValida(String cedula) {
        if (cedula == null || cedula.length() != 10 || !cedula.matches("\\d+")) {
            return false; 
        }

        try {
            int provincia = Integer.parseInt(cedula.substring(0, 2));
            if (provincia < 1 || provincia > 24) return false;

            int digitoVerificador = Integer.parseInt(cedula.substring(9, 10));
            int suma = 0;
            
            for (int i = 0; i < 9; i++) {
                int digito = Integer.parseInt(cedula.substring(i, i + 1));
                if (i % 2 == 0) { 
                    digito = digito * 2;
                    if (digito > 9) digito -= 9; 
                }
                suma += digito;
            }

            int residuo = suma % 10;
            int verificadorCalculado = (residuo == 0) ? 0 : 10 - residuo;

            return verificadorCalculado == digitoVerificador;

        } catch (NumberFormatException e) {
            return false; 
        }
    }
}
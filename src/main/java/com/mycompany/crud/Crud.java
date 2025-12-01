package com.mycompany.crud; // El nombre de tu paquete principal

import vista.UsuarioVista; // Nota: el paquete de la vista está en minúsculas

import javax.swing.SwingUtilities;

public class Crud { // Tu clase principal
    /**
     * El método principal que inicia la aplicación de escritorio.
     * @param args
     */
    public static void main(String[] args) {
        // Asegura que la GUI se ejecute de forma segura en el hilo de Swing.
        SwingUtilities.invokeLater(() -> {
            new UsuarioVista().setVisible(true);
        });
    }
}
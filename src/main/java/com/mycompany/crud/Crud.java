package com.mycompany.crud;

import controlador.ControladorUsuario;
import modelo.Usuario;
import vista.UsuarioVista;

public class Crud {
    
    public static void main(String[] args) {
        
        // 1. Crear la instancia del Modelo
        Usuario modeloUsuario = new Usuario();
        
        // 2. Crear la instancia de la Vista
        UsuarioVista vistaUsuario = new UsuarioVista();
        
        // 3. Crear el Controlador, inyectando la Vista y el Modelo
        ControladorUsuario controlador = new ControladorUsuario(vistaUsuario, modeloUsuario);
        
        // 4. Iniciar la aplicación
        controlador.iniciar();
    }
}
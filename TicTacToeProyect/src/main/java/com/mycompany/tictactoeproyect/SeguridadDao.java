/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tictactoeproyect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class SeguridadDao {
    
        private Connection connection;

    // Constructor para inicializar la conexión
    public SeguridadDao(Connection connection) {
        this.connection = connection;
    }
    
    //Login
    public String verificarUsuario(String email, String password) {
    String sql = "SELECT * FROM Usuario WHERE email = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, email); 

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Usuario encontrado, verificar contraseña
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(password)) { 
                    return "Inicio de sesión exitoso.";
                } else {
                    return "Contraseña incorrecta.";
                }
            } else {
                return "Correo no encontrado.";
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al verificar usuario: " + e.getMessage());
        return "Error en el sistema. Inténtalo de nuevo más tarde.";
    }
}
    //Sign Up
    
    public boolean usuarioExiste(String email) {
    String sql = "SELECT 1 FROM Usuario WHERE email = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, email);
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next(); // Retorna true si encuentra un usuario con el correo
        }
    } catch (SQLException e) {
        System.err.println("Error al verificar si el usuario existe: " + e.getMessage());
        return false;
    }
}

    
    public String registrarUsuario(String nombre, String email, String password, String rol) {
    if (usuarioExiste(email)) {
        return "El correo ya está registrado.";
    }

    String sql = "INSERT INTO Usuario (nombre, email, password, rol, fecha_creacion) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, nombre);
        stmt.setString(2, email);
        stmt.setString(3, password); 
        stmt.setString(4, rol);

        int filasInsertadas = stmt.executeUpdate();
        if (filasInsertadas > 0) {
            return "Usuario registrado exitosamente.";
        } else {
            return "No se pudo registrar al usuario.";
        }
    } catch (SQLException e) {
        System.err.println("Error al registrar usuario: " + e.getMessage());
        return "Error en el sistema. Inténtalo de nuevo más tarde.";
    }
}


    
}

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
public class JuegoDao {
    private Connection connection;
    
    public JuegoDao(Connection connection) {
        this.connection = connection;
    }    

    public boolean existePartida(String codigo) {
        boolean existe = false;
        String sql = "SELECT 1 FROM partida WHERE codigo_partida = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Establecer el parámetro de la consulta
            stmt.setString(1, codigo);
            
            // Ejecutar la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    existe = true;  // Si se encuentra una fila, la partida existe
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores de conexión y consulta
        }
        
        return existe; // Devuelve true si la partida existe, false si no
    }
    
}

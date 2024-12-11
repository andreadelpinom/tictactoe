/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tictactoeproyect.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 public Map<String, Map<String, Boolean>> obtenerPreguntasConRespuestas(String codigoPartida) {
    Map<String, Map<String, Boolean>> preguntasRespuestas = new HashMap<>();
    String query = "SELECT p.texto_pregunta, r.texto_respuesta, r.es_correcta " +
                   "FROM partida_pregunta pp " +
                   "JOIN pregunta p ON pp.id_pregunta = p.id_pregunta " +
                   "JOIN respuesta r ON p.id_pregunta = r.id_pregunta " +
                   "WHERE pp.id_partida = (SELECT id_partida FROM partida WHERE codigo_partida = ?)";

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, codigoPartida);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String textoPregunta = rs.getString("texto_pregunta");
                String textoRespuesta = rs.getString("texto_respuesta");
                boolean esCorrecta = rs.getBoolean("es_correcta");

                // Obtener el mapa de respuestas para la pregunta específica
                Map<String, Boolean> respuestas = preguntasRespuestas.computeIfAbsent(textoPregunta, k -> new HashMap<>());
                
                // Agregar la respuesta con su valor de corrección (true/false)
                respuestas.put(textoRespuesta, esCorrecta);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return preguntasRespuestas;
}

}

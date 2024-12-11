/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tictactoeproyect.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ASUS
 */
public class ConexionBD {
    Connection conexion = null;
    
    
    String url = "jdbc:postgresql://ep-tight-thunder-a5wysx3d.us-east-2.aws.neon.tech/TicTacToeBD?user=TicTacToeBD_owner&password=2yOp4frzWLnM&sslmode=require";
    
    public Connection obtenerConexcionBasePostgres(){
        try{
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(url);
            System.out.print("Si se realizo la conexion");        
        }catch (Exception e){
            System.out.print("fallo la conexion");
        }
        return conexion;
    };
}

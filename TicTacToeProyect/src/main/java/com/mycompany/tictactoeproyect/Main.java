/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tictactoeproyect;
import javax.swing.JFrame;


/**
 *
 * @author ASUS
 */
public class Main {
    public static void main(String[] args){
        
        ConexionBD conexion = new ConexionBD();
        conexion.obtenerConexcionBasePostgres();  
        
        JFrame frame = new JFrame("Tic Tac Toe - Inicio de Sesión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500); 
        frame.setResizable(false);

        LogIn logInPanel = new LogIn(conexion); 

        frame.add(logInPanel);

        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
        
    }
}

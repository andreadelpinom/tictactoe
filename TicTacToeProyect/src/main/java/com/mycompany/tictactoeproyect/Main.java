/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tictactoeproyect;

/**
 *
 * @author ASUS
 */
public class Main {
    public static void main(String[] args){
        ConexionBD obj = new ConexionBD();
        obj.obtenerConexcionBasePostgres();
    }
}

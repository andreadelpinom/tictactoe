package newGame.interfacesProject;

import java.net.Socket;
import java.sql.SQLException;

import com.mycompany.tictactoeproyect.dao.SeguridadDao;
import newGame.dao.*;

import java.io.*;

//toda la logica del client

//para manejar desde el lado de la interfaz

public class SocketClient   {

    public final String HOST = "127.0.0.1"; 
    public final int PORT = 2020;
    

    public SocketClient() {
        
        
    }

    public boolean establecerConexion(){
        boolean respuesta = true;
        try (Socket socket = new Socket(HOST, PORT); 
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
    
            System.out.println("Conectado al servidor.");
        }
        catch (IOException e) {
            e.printStackTrace();
            respuesta = false;
        }
        return respuesta;
    }

   
    
    

    //CREAR DE CONTRASEÑA Y EMAIL
    //PARTIDA
    //MARCACION
    //PRGUNTAS
    //DEJAR ABIERTA LA CONEXION, PARA QUE SIEMPRE SE MANTENGA ASI 

}
    



    


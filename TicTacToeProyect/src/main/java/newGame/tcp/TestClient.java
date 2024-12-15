package newGame.tcp;

import newGame.dto.LoginDTO;
import newGame.dto.Request;
import newGame.dto.Response;
import newGame.dto.UserDTO;

import java.io.*;
import java.net.*;

public class TestClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 2020);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Conectado al servidor.");

            // Crear un nuevo usuario para registrar (prueba de SIGN_UP)
            /// Prueba de registro de usuario (SIGN_UP)
            /// UserDTO newUser = new UserDTO();
            /// newUser.setName("usuario34");
            /// newUser.setPassword("1234");
            /// newUser.setEmail("nuevomundo@ddafsadfas.com");
            /// newUser.setRol("estudiante");
            /// Request request = new Request("SIGN_UP", newUser);
            /// 
            LoginDTO current_user = new LoginDTO();

            
            current_user.setPassword("1234");
            current_user.setEmail("nuevomundo@ddafsadfas.com");
            Request request2 = new Request("LOGIN", current_user);



            // Enviar solicitud
            //out.writeObject(request);
            //System.out.println("Solicitud de registro enviada 1.");
            out.writeObject(request2);
            System.out.println("Solicitud de registro enviada 2.");

            // Leer respuesta del servidor
            Response response = (Response) in.readObject();
            System.out.println("Respuesta del servidor: " + response.getMessage());

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}


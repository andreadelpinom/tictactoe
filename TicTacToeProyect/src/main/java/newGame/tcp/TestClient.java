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
            // Prueba de registro de usuario (SIGN_UP)
            UserDTO newUser = new UserDTO();
            newUser.setName("usuario3");
            newUser.setPassword("123");
            newUser.setEmail("nuevo@ddafsadfas.com");
            newUser.setRol("estudiante");
            Request request = new Request("SIGN_UP", newUser);

            // Enviar solicitud
            out.writeObject(request);
            System.out.println("Solicitud de registro enviada.");

            // Leer respuesta del servidor
            Response response = (Response) in.readObject();
            System.out.println("Respuesta del servidor: " + response.getMessage());

//            // Prueba de login
//            LoginDTO login = new LoginDTO();
//            login.setEmail("mel@gmail.com");  // Asegúrate de configurar un usuario válido
//            login.setPassword("123");  // Configura una contraseña para el login
//
//            Request request = new Request("LOGIN", login);
//
//            // Enviar solicitud de login
//            out.writeObject(request);
//            System.out.println("Solicitud de login enviada.");
//
//            // Leer respuesta del servidor
//            Response response = (Response) in.readObject();
//            System.out.println("Respuesta del servidor: " + response.getMessage());

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}

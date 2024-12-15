package newGame.tcp;

import newGame.dto.UserDTO;
import java.io.*;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 2020;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            // Crear un objeto UserDTO
            UserDTO user = new UserDTO("username", "user@mail.com", "password", "user");

            // Enviar el objeto UserDTO al servidor
            oos.writeObject(user);

            // Esperar la respuesta del servidor
            String serverResponse = (String) ois.readObject();
            System.out.println("Respuesta del servidor: " + serverResponse);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}
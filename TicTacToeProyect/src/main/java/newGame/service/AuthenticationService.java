package newGame.service;

import newGame.dto.LoginDTO;
import newGame.dto.Request;
import newGame.dto.Response;
import newGame.dto.UserDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Servicio para manejar la autenticación y el registro de usuarios.
 */
public class AuthenticationService {

    /**
     * Método para autenticar al usuario.
     *
     * @param email    correo electrónico del usuario
     * @param password contraseña del usuario
     * @return true si la autenticación es exitosa, false si ocurre algún error
     */
    public boolean autenticarUsuario(String email, String password) {
        try (Socket socket = new Socket("localhost", 2020);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            LoginDTO login = new LoginDTO();
            login.setEmail(email);
            login.setPassword(password);
            Request request = new Request("LOGIN", login);

            // Enviar solicitud de login
            out.writeObject(request);
            System.out.println("Solicitud de login enviada.");

            // Leer respuesta del servidor
            Response response = (Response) in.readObject();
            System.out.println("Respuesta del servidor: " + response.getMessage());
            if (response.getMessage().equals("Inicio de sesión exitoso.")){
                return true;
            }
            return false;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método para registrar un nuevo usuario.
     *
     * @param userDto datos del usuario a registrar
     * @return true si el registro es exitoso, false si ocurre algún error
     */
    public boolean registrarUsuario(UserDTO userDto) {
        try (Socket socket = new Socket("localhost", 2020);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Crear la solicitud de registro con los datos del usuario
            Request request = new Request("SIGN_UP", userDto);

            // Enviar solicitud de registro
            out.writeObject(request);
            System.out.println("Solicitud de registro enviada.");

            // Leer respuesta del servidor
            Response response = (Response) in.readObject();
            System.out.println("Respuesta del servidor: " + response.getMessage());

            if (response.getMessage().equals("Usuario registrado exitosamente.")){
                return true;
            }
            return false;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}

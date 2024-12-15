package newGame.tcp;
import newGame.dto.LoginDTO;
import newGame.dto.Request;
import newGame.dto.Response;

import java.io.*;
import java.net.*;


public class ClientHandler implements Runnable {
    private Socket socket;
    private Server server;
    private InterfaceManager interfaceManager;
    

    public ClientHandler(Socket socket, Server server, InterfaceManager interfaceManager) {
        this.socket = socket;
        this.server = server;
        this.interfaceManager = interfaceManager;
    }

    @Override
    public void run() {
        try (
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Cliente conectado: " + socket.getInetAddress());

            LoginDTO current_user = new LoginDTO();

             // Example of using the InterfaceManager to access the interfaces
             
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Ingresa el Email: ");
            String email = reader.readLine();  // Read the email input from the user

            // Assuming current_user has setEmail() method to save the email
            current_user.setEmail(email);

            System.out.println("Ingresa el Password: ");
            String password = reader.readLine();  // Read the password input from the user

            // Assuming current_user has setPassword() method to save the password
            current_user.setPassword(password);
 
             // Use InterfaceManager to authenticate the user
             if (interfaceManager.getLogging().authenticateUser(email, password)) {
                 // If authentication is successful, process the request
                 Response response = Server.processRequest(current_user);
 
                 // Optionally log the action using InterfaceManager
                 interfaceManager.getLogging().logAction("User " + current_user + " processed request: " + current_user);
 
                 // Send the response to the client
                 out.writeObject(response);
             } else {
                 // If authentication fails, send an error response
                 Response errorResponse = new Response("Authentication failed");
                 out.writeObject(errorResponse);
             }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al manejar la comunicación con el cliente: " + e.getMessage());
        }
    }

    
}

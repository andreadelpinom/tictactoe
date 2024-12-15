package newGame.tcp;
import newGame.dto.LoginDTO;
import newGame.dto.Request;
import newGame.dto.Response;
import newGame.logic.*;

import java.io.*;
import java.net.*;
import java.util.UUID;

//Sirve para manejar desde el lado del servidor


public class ClientHandler implements Runnable {
    private Socket socket;
    private Server server;
    private BufferedReader input;
    private PrintWriter output;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Cliente conectado2: " + socket.getInetAddress());

            // Recibir la solicitud del cliente
            Request request = (Request) in.readObject();
            System.out.println(request.getOperation());

            // Enviar la solicitud al servidor para manejarla
            Response response = server.processRequest(request);


            // Enviar la respuesta al cliente
            out.writeObject(response);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al manejar la comunicación con el cliente: " + e.getMessage());
        }
    }

    //Manejador de comunicación Servidor con Interfaces






   
}

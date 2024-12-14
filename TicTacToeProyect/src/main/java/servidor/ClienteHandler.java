/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;
import java.io.*;
import java.net.*;

public class ClienteHandler implements Runnable {
    private Socket socketCliente;
    private PrintWriter out;
    private BufferedReader in;

    public ClienteHandler(Socket socket) {
        this.socketCliente = socket;
    }

    @Override
    public void run() {
        try {
            // Configurar streams para la comunicación con el cliente
            out = new PrintWriter(socketCliente.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

            // Enviar un mensaje de bienvenida
            out.println("Bienvenido al servidor Tic Tac Toe!");

            // Lógica para recibir mensajes del cliente
            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                System.out.println("Mensaje recibido del cliente: " + mensaje);

                // Aquí puedes agregar la lógica del juego, como enviar preguntas o recibir respuestas
                // Por ejemplo, podrías responder al cliente:
                out.println("Mensaje recibido: " + mensaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketCliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

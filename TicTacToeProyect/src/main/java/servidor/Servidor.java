/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Servidor {
    private static final int PUERTO = 12345;
    private static ExecutorService poolDeClientes = Executors.newFixedThreadPool(10); // Pool para manejar clientes

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            while (true) {
                Socket socketCliente = serverSocket.accept(); // Aceptar conexión de cliente
                System.out.println("Nuevo cliente conectado: " + socketCliente.getInetAddress().getHostAddress());

                // Asignar el manejo de este cliente a un nuevo hilo
                poolDeClientes.submit(new ClienteHandler(socketCliente));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            poolDeClientes.shutdown();  // Aseguramos cerrar el pool de clientes cuando el servidor termine
        }
    }
    
    public void iniciar() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            while (true) {
                Socket socketCliente = serverSocket.accept(); // Aceptar conexión de cliente
                System.out.println("Nuevo cliente conectado: " + socketCliente.getInetAddress().getHostAddress());

                // Asignar el manejo de este cliente a un nuevo hilo
                poolDeClientes.submit(new ClienteHandler(socketCliente));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newGame.interfacesProject;

/**
 *
 * @author ASUS
 */
import java.io.*;
import java.util.*;

public class GestorPreguntas {

    public static List<Map<String, Object>> leerPreguntasDesdeArchivo(String rutaArchivo) throws IOException {
        List<Map<String, Object>> preguntas = new ArrayList<>();

        File archivo = new File(rutaArchivo);
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int contador = 1; // Para identificar preguntas en errores
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(","); // Separar por comas
                if (partes.length != 5) { // Validar que haya 5 elementos
                    throw new IllegalArgumentException("Formato incorrecto en la línea " + contador + ": " + linea);
                }

                // Crear un mapa para representar la pregunta
                Map<String, Object> pregunta = new HashMap<>();
                pregunta.put("texto", partes[0].trim()); // Texto de la pregunta
                pregunta.put("opciones", new String[]{
                        partes[1].trim(), 
                        partes[2].trim(), 
                        partes[3].trim() // Opciones
                });

                try {
                    int correcta = Integer.parseInt(partes[4].trim());
                    if (correcta < 1 || correcta > 3) { // Validar índice correcto
                        throw new IllegalArgumentException("Índice de respuesta correcta inválido en la línea " + contador + ": " + linea);
                    }
                    pregunta.put("correcta", correcta); // Índice de la respuesta correcta (1-3)
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Índice de respuesta correcta no es un número en la línea " + contador + ": " + linea);
                }

                // Añadir la pregunta a la lista
                preguntas.add(pregunta);
                contador++;
            }
        }

        return preguntas; // Retornar lista de preguntas
    }

//    public static void main(String[] args) {
//        try {
//            String ruta = "C:\\Users\\maxxi\\OneDrive\\Documentos\\NetBeansProjects\\tictactoe\\TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\Preguntastxt.txt"; // Ruta del archivo TXT
//            List<Map<String, Object>> preguntas = leerPreguntasDesdeArchivo(ruta);
//
//            // Ejemplo: Mostrar las preguntas leídas
//            for (Map<String, Object> pregunta : preguntas) {
//                System.out.println("Pregunta: " + pregunta.get("texto"));
//                String[] opciones = (String[]) pregunta.get("opciones");
//                System.out.println("Opciones: ");
//                for (int i = 0; i < opciones.length; i++) {
//                    System.out.println((i + 1) + ") " + opciones[i]);
//                }
//                System.out.println("Respuesta correcta: Opción " + pregunta.get("correcta"));
//                System.out.println("-------------");
//            }
//
//        } catch (IOException e) {
//            System.err.println("Error al leer el archivo: " + e.getMessage());
//        } catch (IllegalArgumentException e) {
//            System.err.println("Error en el formato del archivo: " + e.getMessage());
//        }
//    }
}

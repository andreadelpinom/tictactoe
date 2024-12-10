package com.mycompany.tictactoeproyect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


public class InterfaceCreation extends JFrame {
    private JLabel tituloLabel;
    private JButton subirArchivoButton;
    private JTextArea estadoArea;
    private JPanel mainPanel;
    private PantallaMaestroDao pantallaMaestroDao;
    private GestorPreguntas gestorpreguntas;
    private int idUsuario;
    private String codigo;
    private Connection connection;

    public InterfaceCreation(int idUsuario) {
        this.idUsuario= idUsuario;
        setUndecorated(true);
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(18, 32, 47));
        setVisible(true);  // Hacer visible la ventana

                
        //Clases asociadas 
        ConexionBD conexionBD = new ConexionBD();
        connection = conexionBD.obtenerConexcionBasePostgres();
        pantallaMaestroDao= new PantallaMaestroDao(connection);
        gestorpreguntas = new GestorPreguntas();

        // Botón para cerrar la ventana
        JButton closeButton = new JButton("X");
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Dialog", Font.BOLD, 16));
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setPreferredSize(new Dimension(50, 30));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePanel.setOpaque(false);
        closePanel.add(closeButton);
        add(closePanel, BorderLayout.NORTH);

        tituloLabel = new JLabel("Crear Partida", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(tituloLabel);

        // Botón para subir archivo
        subirArchivoButton = new JButton("Subir Preguntas y Opciones");
        subirArchivoButton.setBackground(new Color(61, 176, 225));
        subirArchivoButton.setForeground(Color.WHITE);
        subirArchivoButton.setFont(new Font("Dialog", Font.PLAIN, 16));
        subirArchivoButton.setFocusPainted(false);
        subirArchivoButton.addActionListener(e -> {
            try {
                subirArchivo();
            } catch (SQLException ex) {
                Logger.getLogger(InterfaceCreation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(subirArchivoButton);
        add(mainPanel, BorderLayout.CENTER);

        // Área de texto inicializada
        estadoArea = new JTextArea();
        estadoArea.setEditable(false);
        estadoArea.setLineWrap(true);
        estadoArea.setWrapStyleWord(true);
        estadoArea.setFont(new Font("Dialog", Font.BOLD, 16));
        estadoArea.setForeground(Color.WHITE);
        estadoArea.setBackground(new Color(18, 32, 47));
    }
    
    //Genera Codigo de la Partida
    
    public  String generarCodigo(int longitud, Connection connection) throws SQLException {
        boolean existeCodigo = true;

        // Definir el conjunto de caracteres permitidos
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Crear una instancia de SecureRandom para generar números aleatorios
        SecureRandom random = new SecureRandom();

        // Crear un StringBuilder para construir el código
        StringBuilder codigoGenerado = new StringBuilder(longitud);

        // Generar códigos hasta que uno sea único
        while (existeCodigo) {
            codigoGenerado.setLength(0);  // Limpiar el contenido del StringBuilder
            for (int i = 0; i < longitud; i++) {
                int indice = random.nextInt(caracteres.length());
                codigoGenerado.append(caracteres.charAt(indice));
            }

            codigo = codigoGenerado.toString();

            // Verificar si el código ya existe en la base de datos
            String sqlVerificarCodigo = "SELECT COUNT(*) FROM Partida WHERE codigo_partida = ?";
            try (PreparedStatement stmtVerificar = connection.prepareStatement(sqlVerificarCodigo)) {
                stmtVerificar.setString(1, codigo);
                try (ResultSet rs = stmtVerificar.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        // El código no existe, es único
                        existeCodigo = false;
                    }
                }
            }
        }

        return codigo;  // Retorna el código único
    }

    

    // Sube el archivo y cambia la interfaz
    private void subirArchivo() throws SQLException {
        codigo = generarCodigo(6,connection);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo de preguntas");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));

        int resultado = fileChooser.showOpenDialog(null); // 'null' si no hay una ventana padre
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try {
                // Llamar al método para leer las preguntas desde el archivo
                List<Map<String, Object>> preguntas = GestorPreguntas.leerPreguntasDesdeArchivo(archivo.getAbsolutePath());
            
                for (Map<String, Object> pregunta : preguntas) {
                //System.out.println("Pregunta: " + pregunta.get("texto"));
                String[] opciones = (String[]) pregunta.get("opciones");
                System.out.println("Opciones: ");
                for (int i = 0; i < opciones.length; i++) {
                    System.out.println((i + 1) + ") " + opciones[i]);
                }
                System.out.println("Respuesta correcta: Opción " + pregunta.get("correcta"));
                System.out.println("-------------");
                }
                
                
                pantallaMaestroDao.crearPartida(codigo, idUsuario);
                
                pantallaMaestroDao.insertarPreguntasYRespuestasYAsociarPartida(preguntas,codigo);

            
            } catch (IOException e) {
                e.printStackTrace(); // Imprime la excepción en la consola
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error en el formato del archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, 
                "Archivo cargado con éxito: " + archivo.getName(),
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);

            // Aquí puedes continuar con el flujo de trabajo, como procesar el archivo.
            System.out.println("Archivo seleccionado: " + archivo.getAbsolutePath());
        } else {
            // Mensaje si el usuario cancela la selección
            JOptionPane.showMessageDialog(null, 
                "No se seleccionó ningún archivo.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
        }
        //Agregar lógica para subir archivo a la BD
                

        tituloLabel.setText("Código:"+ codigo);
        tituloLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        remove(subirArchivoButton);

        estadoArea.setText("Esperando...");

        remove(mainPanel);
    
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
        newPanel.setOpaque(false);
        newPanel.add(Box.createVerticalStrut(40));
        newPanel.add(tituloLabel);
        newPanel.add(Box.createVerticalStrut(20));
        
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textPanel.setOpaque(false);
        textPanel.add(estadoArea);
        
        newPanel.add(textPanel);
        add(newPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    // Método Partida en Curso
    public void partidaEnCurso() {
        tituloLabel.setForeground(Color.RED);
        estadoArea.setText("Partida en curso. No cierre esta ventana...");
    }

    // Método GameOver para actualizar la interfaz con el nombre y el mensaje
    public void GameOver() {
        tituloLabel.setText("Anthony Mora"); //Cambiar según el usuario que ganó
        tituloLabel.setForeground(Color.GREEN);
        estadoArea.setText("¡Ha ganado con 5 respuestas correctas!"); //Cambiar según el usuario que ganó 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceCreation(1);  // Llamada al constructor de la clase
            }
        });

    }
}

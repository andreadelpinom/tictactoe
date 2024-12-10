package com.mycompany.tictactoeproyect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceCreation extends JFrame {
    private JLabel tituloLabel;
    private JButton subirArchivoButton;
    private JTextArea estadoArea;
    private JPanel mainPanel;

    public InterfaceCreation() {
        setUndecorated(true);
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(18, 32, 47));
        
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
        subirArchivoButton.addActionListener(e -> subirArchivo());

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

    // Sube el archivo y cambia la interfaz
    private void subirArchivo() {
        
        //Agregar lógica para subir archivo a la BD

        tituloLabel.setText("Código: v5Xfsd");
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
        SwingUtilities.invokeLater(() -> {
            InterfaceCreation ventana = new InterfaceCreation();
            ventana.setVisible(true);
        });
    }
}

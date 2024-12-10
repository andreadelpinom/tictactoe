package com.mycompany.tictactoeproyect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceCode extends JFrame {
    private JLabel tituloLabel;
    private JTextField code;
    private JButton guardarButton;
    private String codigoIngresado;

    public InterfaceCode() {
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
        closeButton.addActionListener(e -> System.exit(0));

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePanel.setOpaque(false);
        closePanel.add(closeButton);
        add(closePanel, BorderLayout.NORTH);

        tituloLabel = new JLabel("Ingresar Código", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        tituloLabel.setForeground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(tituloLabel);

        // Cuadro de texto
        code = new JTextField();
        code.setPreferredSize(new Dimension(250, 30)); 
        code.setFont(new Font("Dialog", Font.PLAIN, 16));
        code.setBackground(new Color(41, 54, 63));
        code.setForeground(Color.WHITE);
        code.setCaretColor(Color.WHITE);
        code.setHorizontalAlignment(JTextField.CENTER);

        // Botón de guardar
        guardarButton = new JButton("Unirse");
        guardarButton.setBackground(new Color(61, 176, 225));
        guardarButton.setForeground(Color.WHITE);
        guardarButton.setFont(new Font("Dialog", Font.PLAIN, 16));
        guardarButton.setFocusPainted(false);
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCodigo();
                cambiarInterfaz();
            }
        });

        mainPanel.add(Box.createVerticalStrut(20)); 
        mainPanel.add(code);
        mainPanel.add(Box.createVerticalStrut(20)); 
        mainPanel.add(guardarButton);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(mainPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    // Método para guardar el código ingresado
    private void guardarCodigo() {
        codigoIngresado = code.getText();
        
        // Agregar lógica para enviar el código al servidor o realizar otra acción
    }

    private void cambiarInterfaz() {
        Container parent = code.getParent();
        parent.remove(code);
        parent.remove(guardarButton);

        tituloLabel.setText("Esperando rival...");

        parent.revalidate();
        parent.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceCode ventana = new InterfaceCode();
            ventana.setVisible(true);
        });
    }
}

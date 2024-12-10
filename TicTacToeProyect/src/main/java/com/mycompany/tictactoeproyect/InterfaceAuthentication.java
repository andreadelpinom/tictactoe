package com.mycompany.tictactoeproyect;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;

public class InterfaceAuthentication extends JFrame {
    private JPanel panel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JComboBox<String> categoryBox;
    private JLabel toggleLabel;
    private boolean isLogin = true;
    private String email;
    private String password;
    private String category;
    private String nombre;
    private SeguridadDao seguridadDao;

    public InterfaceAuthentication() {
        setUndecorated(true);
        setTitle("TicTacToe Authentication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 470);
        setLocationRelativeTo(null);
        setResizable(false);
        
        ConexionBD conexionBD = new ConexionBD();
        Connection connection = conexionBD.obtenerConexcionBasePostgres();
        seguridadDao = new SeguridadDao(connection);
        
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(20, 25, 40));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        // Botón de cierre
        JButton closeButton = new JButton("X");
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("Dialog", Font.BOLD, 14));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(255, 0, 0));
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setBounds(0, 0, 50, 30);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> System.exit(0));
        panel.add(closeButton);

        // Logo
        //JLabel titleLabel = new JLabel(new ImageIcon("TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\LogoT.png"));
        JLabel titleLabel = new JLabel(new ImageIcon(".\\TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\LogoT.png"));

        titleLabel.setBounds(25, 80, 350, 50);
        panel.add(titleLabel);

        // Botón Toggle (Sign Up/Login)
        toggleLabel = new JLabel("Sign Up", SwingConstants.RIGHT);
        toggleLabel.setForeground(Color.WHITE);
        toggleLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        toggleLabel.setBounds(305, 10, 80, 20);
        toggleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                toggleAuth();
            }
        });
        panel.add(toggleLabel);

        // Campo Email
        emailField = new JTextField();
        styleTextField(emailField, "Email");
        emailField.setBounds(50, 170, 300, 30);
        panel.add(emailField);

        // Campo Password
        passwordField = new JPasswordField();
        styleTextField(passwordField, "Password");
        passwordField.setBounds(50, 220, 300, 30);
        panel.add(passwordField);

        // Cuadro de Categoría (solo en Sign Up)
        categoryBox = new JComboBox<>(new String[]{"profesor", "estudiante"});
        styleComboBox(categoryBox);
        categoryBox.setBounds(50, 270, 300, 30);
        categoryBox.setVisible(false); // Oculto en Login
        panel.add(categoryBox);

        // Botón Login/Sign Up
        loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 19));
        loginButton.setBounds(125, 330, 150, 40);
        panel.add(loginButton);
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (isLogin) { // Solo ejecuta Login si estamos en modo Login
                    System.out.println("AAa"); //prueba
                                // Obtener los datos ingresados
                    String email = emailField.getText().trim();
                    String password = new String(passwordField.getPassword()).trim();

                    // Validar que no estén vacíos
                    if (email.isEmpty() || password.isEmpty() || email.equals("Email") || password.equals("Password")) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingresa tu correo y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                // Llamar al DAO para verificar las credenciales
                String resultado = seguridadDao.verificarUsuario(email, password);
                System.out.println(resultado);
                // Mostrar mensaje basado en el resultado
                switch (resultado) {
                    case "Inicio de sesión exitoso.":
                        JOptionPane.showMessageDialog(null, resultado, "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
                        // Aquí puedes redirigir al usuario a la siguiente ventana
                    break;
                    case "Contraseña incorrecta.":
                    case "Correo no encontrado.":
                        JOptionPane.showMessageDialog(null, resultado, "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + resultado, "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }

                    //Método desde la clase ConexionBD que valide los datos en la tabla Usuario
                        
                } else { // Solo ejecuta registrar() si estamos en modo Sign Up
                    registrar();

                    //Método desde la clase ConexionBD que agregue los datos a la tabla Usuario
                }
            }
        });

        // Pie de página
        JLabel footerLabel = new JLabel("NetPioneers", SwingConstants.CENTER);
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        footerLabel.setBounds(0, 420, 400, 30);
        panel.add(footerLabel);

        add(panel);

        SwingUtilities.invokeLater(() -> panel.requestFocusInWindow());
    }

    private void styleTextField(JTextField field, String placeholder) {
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        field.setCaretColor(Color.WHITE);

        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void styleComboBox(JComboBox<String> box) {
        box.setOpaque(false);
        box.setForeground(Color.WHITE);
        box.setFont(new Font("Dialog", Font.PLAIN, 16));
        box.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        box.setBackground(new Color(20, 25, 40));
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("Dialog", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 122, 204));
        button.setBorder(new RoundedBorder(20));
    }

    private void toggleAuth() {
        isLogin = !isLogin;
        if (isLogin) {
            toggleLabel.setText("Sign Up");
            loginButton.setText("Login");
            categoryBox.setVisible(false);
        } else {
            toggleLabel.setText("Login");
            loginButton.setText("Sign Up");
            categoryBox.setVisible(true);
        }
    }

    private void registrar() {
        // Obtener los valores de los campos
        email = emailField.getText().trim();
        password = new String(passwordField.getPassword()).trim();
        category = (String) categoryBox.getSelectedItem();

        // Validar los datos
        if (email.isEmpty() || password.isEmpty() || email.equals("Email") || password.equals("Password")) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Pedir nombre
        String nombre = JOptionPane.showInputDialog(this, 
            "Ingresa tu nombre:", 
            "Información adicional", 
            JOptionPane.QUESTION_MESSAGE);

        // Verificar si el usuario presionó Cancelar
        if (nombre == null) {
            JOptionPane.showMessageDialog(this, "Operación cancelada.", "Cancelación", JOptionPane.INFORMATION_MESSAGE);
            return; // Termina el proceso
        }

        // Eliminar espacios al inicio y final
        nombre = nombre.trim();

        // Verificar si el nombre está vacío
        while (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No ingresaste tu nombre.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            nombre = JOptionPane.showInputDialog(this, 
                "Ingresa tu nombre:", 
                "Información adicional", 
                JOptionPane.QUESTION_MESSAGE);

            if (nombre == null) { // Manejo de cancelar en el bucle
                JOptionPane.showMessageDialog(this, "Operación cancelada.", "Cancelación", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            nombre = nombre.trim();
        }
        // Llamar al DAO para registrar el usuario
        String resultado = seguridadDao.registrarUsuario(nombre,email, password, category);

        // Mostrar el resultado del registro
        JOptionPane.showMessageDialog(this, resultado, "Resultado", resultado.contains("exitosamente") ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);

        // Si fue exitoso, volver al modo Login
        if (resultado.contains("exitosamente")) {
            toggleAuth();
        }

        // Limpiar los campos después del registro
        emailField.setText("Email");
        passwordField.setText("Password");
        categoryBox.setSelectedIndex(0);  
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceAuthentication frame = new InterfaceAuthentication();
            frame.setVisible(true);
        });
    }

    // Clase para bordes redondeados
    private static class RoundedBorder extends AbstractBorder {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(c.getForeground());
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
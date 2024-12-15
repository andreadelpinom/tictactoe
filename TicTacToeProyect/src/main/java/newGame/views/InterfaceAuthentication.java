package newGame.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import newGame.dto.LoginDTO;
import newGame.dto.Request;
import newGame.dto.UserDTO;
import newGame.service.AuthenticationService;
import newGame.tcp.ClientHandler;

public class InterfaceAuthentication extends JFrame {
    private JPanel panel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JComboBox<String> categoryBox;
    private JLabel toggleLabel;
    private boolean isLogin = true;

    private AuthenticationService authService;

    public InterfaceAuthentication() {
        // Inicialización del servicio de autenticación con el cliente
        authService = new AuthenticationService();

        // Configuración de la ventana
        setUndecorated(true);
        setTitle("TicTacToe Authentication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 470);
        setLocationRelativeTo(null);
        setResizable(false);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(20, 25, 40));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        addComponents();
        add(panel);

        SwingUtilities.invokeLater(() -> panel.requestFocusInWindow());
    }

    private void addComponents() {
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

        // Cuadro de Categoría
        categoryBox = new JComboBox<>(new String[]{"profesor", "estudiante"});
        styleComboBox(categoryBox);
        categoryBox.setBounds(50, 270, 300, 30);
        categoryBox.setVisible(false);
        panel.add(categoryBox);

        // Botón Login/Sign Up
        loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 19));
        loginButton.setBounds(125, 330, 150, 40);
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleAuthAction();
            }
        });
        panel.add(loginButton);

        // Pie de página
        JLabel footerLabel = new JLabel("NetPioneers", SwingConstants.CENTER);
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        footerLabel.setBounds(0, 420, 400, 30);
        panel.add(footerLabel);
    }

    private void handleAuthAction() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // Validación de campos vacíos
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (isLogin) {
            boolean success = authService.autenticarUsuario(email, password);
            JOptionPane.showMessageDialog(this, success ? "Login Successful" : "Invalid Credentials");
        } else {
            String category = (String) categoryBox.getSelectedItem();
            if (category == null || category.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a category.");
                return;
            }
            UserDTO newUser = new UserDTO();
            newUser.setName("prueba2");
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setRol( (String) categoryBox.getSelectedItem());

            boolean success = authService.registrarUsuario(newUser);
            JOptionPane.showMessageDialog(this, success ? "Sign Up Successful" : "Registration Failed");
        }
    }

    private void toggleAuth() {
        isLogin = !isLogin;
        toggleLabel.setText(isLogin ? "Sign Up" : "Login");
        loginButton.setText(isLogin ? "Login" : "Sign Up");
        categoryBox.setVisible(!isLogin);
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
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            ClientHandler clientHandler = new ClientHandler(); // Debes inicializar el ClientHandler correctamente
            InterfaceAuthentication frame = new InterfaceAuthentication();
            frame.setVisible(true);
        });
    }
}

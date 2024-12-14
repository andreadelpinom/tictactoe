package com.mycompany.tictactoeproyect.logicaNegocio;

import com.mycompany.tictactoeproyect.dao.JuegoDao;
import com.mycompany.tictactoeproyect.db.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class InterfaceGame extends JFrame {
    private JPanel panelIzquierdo;
    private JPanel panelDerecho;
    private JLabel turnoLabel;
    private JLabel username;
    private JLabel logoLabel;
    private JButton[][] botonesTablero;
    private JLabel user1Label, user2Label;
    private JLabel puntuacion1, puntuacion2;
    private int puntos = 0;
    private JuegoDao juegoDao;
    private String codigo;

    
    public InterfaceGame(String codigo) {
        setUndecorated(true);
        setTitle("Tic Tac Toe - NetPioneers");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        
        //Clases asociadas
        ConexionBD conexionBD = new ConexionBD();
        Connection connection = conexionBD.obtenerConexcionBasePostgres();
        juegoDao= new JuegoDao(connection);
        this.codigo = codigo;

        // Panel Izquierdo
        panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BorderLayout());
        JPanel puntuacionesPanel = new JPanel(new GridLayout(2, 3));
        panelIzquierdo.setBackground(new Color(20, 25, 40));

        user1Label = new JLabel("USER_1", SwingConstants.CENTER);
        user1Label.setForeground(Color.WHITE);
        user1Label.setFont(new Font("Dialog", Font.BOLD, 14));

        puntuacion1 = new JLabel("0", SwingConstants.CENTER);
        puntuacion1.setForeground(Color.WHITE);
        puntuacion1.setFont(new Font("Dialog", Font.BOLD, 56));

        JLabel separador = new JLabel("-", SwingConstants.CENTER);
        separador.setForeground(Color.WHITE);
        separador.setFont(new Font("Dialog", Font.BOLD, 42));

        puntuacion2 = new JLabel("0", SwingConstants.CENTER);
        puntuacion2.setForeground(Color.WHITE);
        puntuacion2.setFont(new Font("Dialog", Font.BOLD, 56));

        user2Label = new JLabel("USER_2", SwingConstants.CENTER);
        user2Label.setForeground(Color.WHITE);
        user2Label.setFont(new Font("Dialog", Font.BOLD, 14));

        puntuacionesPanel.setBackground(new Color(20, 25, 40));

        puntuacionesPanel.add(user1Label);
        puntuacionesPanel.add(new JLabel(""));
        puntuacionesPanel.add(user2Label);
        puntuacionesPanel.add(puntuacion1);
        puntuacionesPanel.add(separador);
        puntuacionesPanel.add(puntuacion2);

        JPanel contenedorTablero = new JPanel(new GridBagLayout());
        contenedorTablero.setOpaque(false);
        contenedorTablero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel tableroPanel = new JPanel(new GridLayout(3, 3)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\maxxi\\OneDrive\\Documentos\\NetBeansProjects\\tictactoe\\TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\Tabla.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        tableroPanel.setOpaque(false);
        tableroPanel.setPreferredSize(new Dimension(425, 425));
        contenedorTablero.add(tableroPanel);

        botonesTablero = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botonesTablero[i][j] = new JButton();
                botonesTablero[i][j].setContentAreaFilled(false);
                botonesTablero[i][j].setOpaque(false);
                botonesTablero[i][j].setBorderPainted(false);
                botonesTablero[i][j].setFocusPainted(false);
                botonesTablero[i][j].setContentAreaFilled(false);

                botonesTablero[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        JButton boton = (JButton) e.getSource();
                        boton.setContentAreaFilled(true);
                        boton.setBackground(new Color(70, 130, 180));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        JButton boton = (JButton) e.getSource();
                        boton.setContentAreaFilled(false);
                    }
                });

                botonesTablero[i][j].addActionListener(new TableroListener(i, j));
                tableroPanel.add(botonesTablero[i][j]);
            }
        }

        panelIzquierdo.add(puntuacionesPanel, BorderLayout.NORTH);
        panelIzquierdo.add(contenedorTablero, BorderLayout.CENTER);

        // Panel Derecho
        panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBackground(new Color(20, 25, 40));

        turnoLabel = new JLabel("Turno", SwingConstants.CENTER);
        turnoLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        turnoLabel.setForeground(Color.GRAY);
        turnoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        username = new JLabel("Anthony Mora", SwingConstants.CENTER);
        username.setFont(new Font("Dialog", Font.BOLD, 24));
        username.setForeground(Color.WHITE);
        username.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoLabel = new JLabel(new ImageIcon("C:\\Users\\maxxi\\OneDrive\\Documentos\\NetBeansProjects\\tictactoe\\TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\LogoNetPioneers.png"));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelDerecho.add(Box.createVerticalStrut(60));
        panelDerecho.add(turnoLabel);
        panelDerecho.add(username);
        panelDerecho.add(Box.createVerticalStrut(50));
        panelDerecho.add(logoLabel);
        panelDerecho.add(Box.createVerticalGlue());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerLocation(500);
        splitPane.setEnabled(false);
        add(splitPane);

        setVisible(true);
    }

    private class TableroListener implements ActionListener {
        private int fila, columna;
        private JPanel preguntaPanel;

        public TableroListener(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        logoLabel.setVisible(false);
        preguntaPanel = new JPanel();
        preguntaPanel.setLayout(new GridBagLayout());
        preguntaPanel.setBackground(new Color(20, 25, 40));
            //preguntaPanel.setPreferredSize(new Dimension(600, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 30, 0);

        // Obtener pregunta y respuestas dinámicamente
        Map<String, Map<String, Boolean>> preguntasRespuestas = juegoDao.obtenerPreguntasConRespuestas(codigo); // Método para obtener preguntas
        String preguntaActual = preguntasRespuestas.keySet().iterator().next();
        System.err.println(preguntaActual);
        Map<String, Boolean> opciones = preguntasRespuestas.get(preguntaActual);

        // Crear JTextArea para mostrar la pregunta
        JTextArea pregunta = new JTextArea(preguntaActual);
        pregunta.setForeground(Color.WHITE);
        pregunta.setFont(new Font("Dialog", Font.BOLD, 24));
        pregunta.setBackground(new Color(20, 25, 40));
        pregunta.setEditable(false);
        pregunta.setFocusable(false);
        pregunta.setLineWrap(true);
        pregunta.setWrapStyleWord(true);
            //pregunta.setPreferredSize(new Dimension(600, 50));

        // Agregar pregunta
        gbc.gridy = 0;
        preguntaPanel.add(pregunta, gbc);

        // Crear opciones dinámicamente
        ButtonGroup opcionesGroup = new ButtonGroup();
        List<JRadioButton> botonesOpciones = new ArrayList<>();

        int y = 1; // Para las posiciones dinámicas de las opciones
        gbc.insets = new Insets(5, 0, 5, 0);
        for (Map.Entry<String, Boolean> opcion : opciones.entrySet()) {
            JRadioButton opcionButton = new JRadioButton(opcion.getKey());
            opcionButton.setFont(new Font("Dialog", Font.BOLD, 18));
            botonesOpciones.add(opcionButton);
            opcionesGroup.add(opcionButton);

            gbc.gridy = y++;
            preguntaPanel.add(opcionButton, gbc);
        }

        // Botón Confirmar
        JButton confirmar = new JButton("Confirmar");
        confirmar.setFont(new Font("Dialog", Font.BOLD, 18));
        gbc.gridy = y;
        gbc.insets = new Insets(20, 0, 0, 0);
            //confirmar.setPreferredSize(new Dimension(600, 30));
        preguntaPanel.add(confirmar, gbc);

        // Agregar acción al botón Confirmar
        confirmar.addActionListener(event -> {
            // Validar la opción seleccionada
            String respuestaSeleccionada = botonesOpciones.stream()
                .filter(JRadioButton::isSelected)
                .map(AbstractButton::getText)
                .findFirst()
                .orElse(null);

            if (respuestaSeleccionada == null) {
                JOptionPane.showMessageDialog(InterfaceGame.this, "Por favor selecciona una respuesta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar si la respuesta es correcta
            boolean esCorrecta = opciones.getOrDefault(respuestaSeleccionada, false);

            if (esCorrecta) {
                JOptionPane.showMessageDialog(InterfaceGame.this, "Respuesta Correcta. :)", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                puntos++;
                puntuacion1.setText("" + puntos);
                botonesTablero[fila][columna].setIcon(new ImageIcon("TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\X.png"));
                botonesTablero[fila][columna].setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(InterfaceGame.this, "Respuesta Incorrecta, sigue estudiando. ;)", "Resultado", JOptionPane.ERROR_MESSAGE);
            }

            // Validar el estado del juego y cambiar el turno
            verificarEstadoJuego();
            cambiarTurno();
            mostrarInterfazInicial();
        });

        // Actualizar la interfaz
        panelDerecho.removeAll();
        panelDerecho.add(Box.createVerticalStrut(60));
        panelDerecho.add(turnoLabel);
        panelDerecho.add(username);
        panelDerecho.add(Box.createVerticalStrut(0));
        panelDerecho.add(preguntaPanel);
        panelDerecho.revalidate();
        panelDerecho.repaint();
    }


        private void cambiarTurno() {
            if (username.getText().equals("Anthony Mora")) {
                username.setText("User_2");
            } else {
                username.setText("Anthony Mora");
            }
        }

        private void mostrarInterfazInicial() {
            panelDerecho.removeAll();
            panelDerecho.add(Box.createVerticalStrut(60));
            panelDerecho.add(turnoLabel);
            panelDerecho.add(username);
            panelDerecho.add(Box.createVerticalStrut(50));

            logoLabel = new JLabel(new ImageIcon("C:\\Users\\maxxi\\OneDrive\\Documentos\\NetBeansProjects\\tictactoe\\TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\LogoT.png"));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panelDerecho.add(logoLabel);
            panelDerecho.add(Box.createVerticalGlue());
            panelDerecho.revalidate();
            panelDerecho.repaint();
        }
    }

    private void verificarEstadoJuego() {
        // Matriz lógica para almacenar el estado del tablero
        int[][] tableroLogico = new int[3][3];
    
        // Rellenar la matriz con el estado actual del tablero (1 para jugador 1, 2 para jugador 2, 0 para vacío)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Icon icon = botonesTablero[i][j].getIcon();
                if (icon != null) {
                    if (icon.toString().contains("TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\X.png")) { // Reemplaza con el nombre de tu archivo para el jugador 1
                        tableroLogico[i][j] = 1;
                    } else if (icon.toString().contains("TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\O.png")) { // Reemplaza con el nombre de tu archivo para el jugador 2
                        tableroLogico[i][j] = 2;
                    }
                }
            }
        }
    
        // Verificar combinaciones ganadoras
        for (int i = 0; i < 3; i++) {
            // Verificar filas
            if (tableroLogico[i][0] == tableroLogico[i][1] && tableroLogico[i][1] == tableroLogico[i][2] && tableroLogico[i][0] != 0) {
                anunciarGanador(tableroLogico[i][0]);
                return;
            }
            // Verificar columnas
            if (tableroLogico[0][i] == tableroLogico[1][i] && tableroLogico[1][i] == tableroLogico[2][i] && tableroLogico[0][i] != 0) {
                anunciarGanador(tableroLogico[0][i]);
                return;
            }
        }
        // Verificar diagonales
        if (tableroLogico[0][0] == tableroLogico[1][1] && tableroLogico[1][1] == tableroLogico[2][2] && tableroLogico[0][0] != 0) {
            anunciarGanador(tableroLogico[0][0]);
            return;
        }
        if (tableroLogico[0][2] == tableroLogico[1][1] && tableroLogico[1][1] == tableroLogico[2][0] && tableroLogico[0][2] != 0) {
            anunciarGanador(tableroLogico[0][2]);
            return;
        }
    
        // Verificar si el tablero está lleno (empate)
        boolean tableroLleno = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tableroLogico[i][j] == 0) {
                    tableroLleno = false;
                    break;
                }
            }
        }
    
        if (tableroLleno) {
            determinarGanadorPorPuntos();
        }
    }
    
    private void anunciarGanador(int jugador) {
        if (jugador == 1) {
            // Acción si gana el jugador 1
            //JOptionPane.showMessageDialog(this, "Jugador 1 gana", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            
            panelDerecho.removeAll();
            panelDerecho.add(Box.createVerticalStrut(60));
            turnoLabel.setText("¡Has Ganado!");
            turnoLabel.setForeground(Color.WHITE);
            panelDerecho.add(turnoLabel);
            panelDerecho.add(username); //Modificar username segun jugador
            panelDerecho.add(Box.createVerticalStrut(50));

            logoLabel = new JLabel(new ImageIcon("C:\\Users\\maxxi\\OneDrive\\Documentos\\NetBeansProjects\\tictactoe\\TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\LogoNetPioneers.png"));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panelDerecho.add(logoLabel);
            panelDerecho.add(Box.createVerticalGlue());
            panelDerecho.revalidate();
            panelDerecho.repaint();

        } else if (jugador == 2) {
            // Acción si gana el jugador 2
            //JOptionPane.showMessageDialog(this, "Jugador 2 gana", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            
            panelDerecho.removeAll();
            panelDerecho.add(Box.createVerticalStrut(60));
            turnoLabel.setText("¡Has Ganado!");
            turnoLabel.setForeground(Color.WHITE);
            panelDerecho.add(turnoLabel);
            panelDerecho.add(username); //Modificar username segun jugador
            panelDerecho.add(Box.createVerticalStrut(50));

            logoLabel = new JLabel(new ImageIcon("C:\\Users\\maxxi\\OneDrive\\Documentos\\NetBeansProjects\\tictactoe\\TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\LogoNetPioneers.png"));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panelDerecho.add(logoLabel);
            panelDerecho.add(Box.createVerticalGlue());
            panelDerecho.revalidate();
            panelDerecho.repaint();

        }
    }
    
    private void determinarGanadorPorPuntos() {
        int puntosJugador1 = Integer.parseInt(puntuacion1.getText());
        int puntosJugador2 = Integer.parseInt(puntuacion2.getText());
    
        if (puntosJugador1 > puntosJugador2) {
            // Acción si el jugador 1 gana por puntos
            JOptionPane.showMessageDialog(this, "¡Jugador 1 gana por puntos!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            
            panelDerecho.removeAll();
            panelDerecho.add(Box.createVerticalStrut(60));
            turnoLabel.setText("¡Has Ganado!");
            turnoLabel.setForeground(Color.WHITE);
            panelDerecho.add(turnoLabel);
            panelDerecho.add(username); //Modificar username segun jugador
            panelDerecho.add(Box.createVerticalStrut(50));

            logoLabel = new JLabel(new ImageIcon("C:\\Users\\maxxi\\OneDrive\\Documentos\\NetBeansProjects\\tictactoe\\TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\LogoNetPioneers.png"));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panelDerecho.add(logoLabel);
            panelDerecho.add(Box.createVerticalGlue());
            panelDerecho.revalidate();
            panelDerecho.repaint();

        } else if (puntosJugador2 > puntosJugador1) {
            // Acción si el jugador 2 gana por puntos
            JOptionPane.showMessageDialog(this, "¡Jugador 2 gana por puntos!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            
            panelDerecho.removeAll();
            panelDerecho.add(Box.createVerticalStrut(60));
            turnoLabel.setText("¡Has Ganado!");
            turnoLabel.setForeground(Color.WHITE);
            panelDerecho.add(turnoLabel);
            panelDerecho.add(username); //Modificar username segun jugador
            panelDerecho.add(Box.createVerticalStrut(50));

            logoLabel = new JLabel(new ImageIcon("C:\\Users\\maxxi\\OneDrive\\Documentos\\NetBeansProjects\\tictactoe\\TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\LogoNetPioneers.png"));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panelDerecho.add(logoLabel);
            panelDerecho.add(Box.createVerticalGlue());
            panelDerecho.revalidate();
            panelDerecho.repaint();

        } else {
            // Acción si hay un empate
            JOptionPane.showMessageDialog(this, "¡Es un empate!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            
            //¿Pueden empatar?

            panelDerecho.removeAll();
            panelDerecho.add(Box.createVerticalStrut(60));
            turnoLabel.setText(" ");
            turnoLabel.setForeground(Color.WHITE);
            panelDerecho.add(turnoLabel);
            username.setText("¡EMPATE!");
            panelDerecho.add(username); //Se debe mostrar EMPATE en ambos clientes
            panelDerecho.add(Box.createVerticalStrut(50));

            logoLabel = new JLabel(new ImageIcon("C:\\Users\\maxxi\\OneDrive\\Documentos\\NetBeansProjects\\tictactoe\\TicTacToeProyect\\src\\main\\java\\com\\mycompany\\tictactoeproyect\\LogoNetPioneers.png"));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panelDerecho.add(logoLabel);
            panelDerecho.add(Box.createVerticalGlue());
            panelDerecho.revalidate();
            panelDerecho.repaint();

        }
    }
}

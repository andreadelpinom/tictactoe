package newGame.interfacesProject;

import com.mycompany.tictactoeproyect.dao.JuegoDao;
import com.mycompany.tictactoeproyect.db.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.in;
import static java.lang.System.out;
import java.net.Socket;
import java.sql.Connection;

public class InterfaceCode extends JFrame {
    private JLabel tituloLabel;
    private JTextField code;
    private JButton guardarButton;
    private String codigoIngresado;
    private JuegoDao juegodao;

     private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    public InterfaceCode() {
        setUndecorated(true);
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(18, 32, 47));
        
        //Clases relacionadas
        ConexionBD conexionBD = new ConexionBD();
        Connection connection = conexionBD.obtenerConexcionBasePostgres();
        juegodao= new JuegoDao(connection);

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
                String codigoPartida = code.getText(); // Obtén el código de la partida
                // Llamar al método que verifica si la partida existe
                boolean existe = juegodao.existePartida(codigoPartida);
                if (existe) {
                    cambiarInterfaz();
                } else {

                    JOptionPane.showMessageDialog(null, "La partida no existe, verifique bien el codigo.");
                }
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
        /*Container parent = code.getParent();
        parent.remove(code);
        parent.remove(guardarButton);

        tituloLabel.setText("Esperando rival...");

        parent.revalidate();
        parent.repaint();*/
        // Llamar al método que se conecta al servidor al inicializar la ventana
        conectarAlServidor();
        InterfaceGame interfacegame=  new InterfaceGame(code.getText());
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            InterfaceCode ventana = new InterfaceCode();
//            ventana.setVisible(true);
//        });
//    }
    
    private void conectarAlServidor() {
        try {
            // Establecer conexión con el servidor
            socket = new Socket("localhost", 12345); // Dirección IP y puerto del servidor
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar un mensaje inicial al servidor
            out.println("Conexión exitosa desde el cliente");

            // Iniciar el hilo para leer mensajes del servidor
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String mensaje;
                        while ((mensaje = in.readLine()) != null) {
                            System.out.println("Mensaje del servidor: " + mensaje);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con el servidor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

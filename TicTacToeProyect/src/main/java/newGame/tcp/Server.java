package newGame.tcp;

import newGame.dto.LoginDTO;
import newGame.dto.Request;
import newGame.dto.Response;
import newGame.dto.UserDTO;
import newGame.interfacesProject.InterfaceAuthentication;
import newGame.interfacesProject.InterfaceGame;
import newGame.logic.GameSession;

import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import newGame.interfacesProject.*;;


public class Server {
    private static final int PORT = 2020;
    private Map<String, GameSession> gameSessions = new HashMap<>();  // Mapa para manejar sesiones de juego
    private DatabaseHandler databaseHandler;//clase encargada de interactuar con la base de datos
    private InterfaceManager interfaceManager;

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }

    public Server() {
        this.databaseHandler = new DatabaseHandler();  // Inicializar el handler de base de datos
        // Initialize all the interfaces
        InterfaceAuthentication logging = new InterfaceAuthentication();
        InterfaceGame game = new InterfaceGame(null);
        InterfaceCode code = new InterfaceCode();
        InterfaceCreation creation = new InterfaceCreation(PORT);
        GestorPreguntas gestorPreguntas = new GestorPreguntas();
        
        // Create InterfaceManager with all the interfaces
        this.interfaceManager = new InterfaceManager(logging, game, code,creation,gestorPreguntas);
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor en espera de conexiones...");
            ExecutorService executorService = Executors.newFixedThreadPool(10); // Usa un pool de 10 hilos

            while (true) {
                Socket socket = serverSocket.accept();  // El servidor espera y acepta conexiones de los clientes
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                // Crear una nueva sesión de juego para cada cliente
                String sessionId = UUID.randomUUID().toString();  // Crear un ID único para la sesión
                GameSession gameSession = new GameSession(sessionId);  // Crear la sesión
                gameSessions.put(sessionId, gameSession);  // Agregar la sesión al mapa

                // Crear un nuevo manejador de cliente para manejar esta conexión
                ClientHandler clientHandler = new ClientHandler(socket, this);
                executorService.submit(clientHandler);  // Enviar el manejador al pool de hilos
            }
        } catch (IOException e) {
            System.err.println("Error al aceptar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    // Método para procesar la solicitud y manejar la base de datos
//    public Response processRequest(Request request) {
//        Response response = null;
//
//        if ("SIGN_UP".equals(request.getOperation())) {
//            // Registro de usuario
//            UserDTO user = (UserDTO) request.getPayload();
//            String result = databaseHandler.signUp(user);
//            response = new Response(true, result, null);
//        } else if ("LOGIN".equals(request.getOperation())) {
//            // Login de usuario
//            LoginDTO login = (LoginDTO) request.getPayload();
//            String result = databaseHandler.login(login);
//            response = new Response(true, result, null);
//        }

// Aquí podrías agregar otros casos para manejar más operaciones

// Método para procesar las solicitudes del cliente
    public Response processRequest(Request request) {
        switch (request.getOperation()) {
            case "SIGN_UP":
                return handleSignUp((UserDTO) request.getPayload());  // Registrar un nuevo usuario
            case "LOGIN":
                return handleLogin((LoginDTO) request.getPayload());  // Verificar el login
            case "CREAR_PARTIDA":
                return handleCrearPartida(request.getPayload());
            case "INSERTAR_PREGUNTAS":
                return handleInsertarPreguntasYRespuestas(request.getPayload());
            case "EXISTE_PARTIDA":
                return handleExistePartida(request.getPayload());
            case "OBTENER_PREGUNTAS":
                return handleObtenerPreguntas(request.getPayload());
            default:
                return new Response(false, "Operación no soportada", null);
        }
    }

    // Manejar el registro de un nuevo usuario
    private Response handleSignUp(UserDTO user) {
        String result = databaseHandler.signUp(user);
        if (result.equals("Usuario registrado correctamente")) {
            return new Response(true, result, null);
        } else {
            return new Response(false, result, null);
        }
    }

    // Manejar el inicio de sesión
    private Response handleLogin(LoginDTO login) {
        String result = databaseHandler.login(login);
        if (result.equals("Login exitoso")) {
            return new Response(true, result, null);
        } else {
            return new Response(false, result, null);
        }
    }

    // Manejar la creación de una nueva partida llamando a la base de datos
    private Response handleCrearPartida(Object payload) {
        try {
            Map<String, Object> params = (Map<String, Object>) payload;
            String codigoPartida = (String) params.get("codigoPartida");
            int idProfesor = (Integer) params.get("idProfesor");

            databaseHandler.crearPartida(codigoPartida, idProfesor);
            return new Response(true, "Partida creada correctamente", null);
        } catch (SQLException e) {
            return new Response(false, "Error al crear la partida: " + e.getMessage(), null);
        }
    }

    // Manejar la inserción de preguntas y respuestas para una partida
    private Response handleInsertarPreguntasYRespuestas(Object payload) {
        try {
            Map<String, Object> params = (Map<String, Object>) payload;
            List<Map<String, Object>> preguntas = (List<Map<String, Object>>) params.get("preguntas");
            String codigoPartida = (String) params.get("codigoPartida");

            databaseHandler.insertarPreguntasYRespuestasYAsociarPartida(preguntas, codigoPartida);
            return new Response(true, "Preguntas y respuestas insertadas correctamente", null);
        } catch (SQLException e) {
            return new Response(false, "Error al insertar preguntas y respuestas: " + e.getMessage(), null);
        }
    }

    // Manejar la verificación de si una partida existe
    private Response handleExistePartida(Object payload) {
        String codigo = (String) payload;
        boolean existe = databaseHandler.existePartida(codigo);
        if (existe) {
            return new Response(true, "La partida existe", null);
        } else {
            return new Response(false, "La partida no existe", null);
        }
    }

    // Manejar la obtención de preguntas con sus respuestas
    private Response handleObtenerPreguntas(Object payload) {
        String codigoPartida = (String) payload;
        Map<String, Map<String, Boolean>> preguntasConRespuestas = databaseHandler.obtenerPreguntasConRespuestas(codigoPartida);
        if (preguntasConRespuestas != null) {
            return new Response(true, "Preguntas obtenidas correctamente", preguntasConRespuestas);
        } else {
            return new Response(false, "No se encontraron preguntas para la partida", null);
        }
    }

    
}

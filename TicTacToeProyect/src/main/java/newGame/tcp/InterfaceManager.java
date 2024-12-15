package newGame.tcp;

import newGame.interfacesProject.*;


public class InterfaceManager {
    private InterfaceAuthentication logging;
    private InterfaceGame game;
    private InterfaceCode code;
    private InterfaceCreation creation;
    private GestorPreguntas gestorPreguntas;

    public InterfaceManager(InterfaceAuthentication logging, InterfaceGame game, InterfaceCode code, InterfaceCreation connection,GestorPreguntas gestorPreguntas) {
        this.logging = logging;
        this.game = game;
        this.code = code;
        this.creation = creation;
        this.gestorPreguntas = gestorPreguntas;
    }

    public InterfaceAuthentication getLogging() {
        return logging;
    }

    public InterfaceGame getGame() {
        return game;
    }

    public InterfaceCode getCode() {
        return code;
    }

    public GestorPreguntas getGestorPreguntas() {
        return gestorPreguntas;
    }

    public boolean authenticateUser(String username, String password) {
        // Simulating authentication with hardcoded credentials
        String correctUsername = "user1";
        String correctPassword = "password123";
        
        return username.equals(correctUsername) && password.equals(correctPassword);
    }

}


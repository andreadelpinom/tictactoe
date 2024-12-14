package newGame.logic;

import java.util.ArrayList;
import java.util.List;

public class GameSession {

    private String sessionId;  // ID único de la sesión
    private List<String> players;  // Lista de jugadores en la sesión
    private String gameState;  // Estado del juego (puede ser "EN_JUEGO", "FINALIZADO", etc.)

    public GameSession(String sessionId) {
        this.sessionId = sessionId;
        this.players = new ArrayList<>();
        this.gameState = "INICIADO";  // El juego comienza en estado INICIADO
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void addPlayer(String player) {
        this.players.add(player);
    }

    public void removePlayer(String player) {
        this.players.remove(player);
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    // Puedes agregar más métodos aquí para controlar las reglas del juego, etc.
}

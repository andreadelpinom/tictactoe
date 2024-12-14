package newGame.tcp;

import newGame.dao.JuegoDao;
import newGame.dao.MaestroDao;
import newGame.dao.SeguridadDao;
import newGame.dto.LoginDTO;
import newGame.dto.UserDTO;
import newGame.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    private final MaestroDao maestroDao;
    private final SeguridadDao seguridadDao;
    private final JuegoDao juegoDao;

    public DatabaseHandler() {
        Connection connection = databaseConnection.obtenerConexionBasePostgres();
        this.seguridadDao = new SeguridadDao(connection);
        this.maestroDao = new MaestroDao(connection);
        this.juegoDao = new JuegoDao(connection);
    }

    public String login(LoginDTO login) {
        return seguridadDao.verificarUsuario(login);
    }

    public String signUp(UserDTO user) {
        return seguridadDao.registrarUsuario(user);
    }

    // Método para crear una nueva partida
    public void crearPartida(String codigoPartida, int idProfesor) throws SQLException {
        maestroDao.crearPartida(codigoPartida, idProfesor);
    }

    // Método para insertar preguntas y respuestas y asociarlas a una partida
    public void insertarPreguntasYRespuestasYAsociarPartida(List<Map<String, Object>> preguntas, String codigoPartida) throws SQLException {
        maestroDao.insertarPreguntasYRespuestasYAsociarPartida(preguntas, codigoPartida);
    }

    // Método para verificar si una partida existe
    public boolean existePartida(String codigo) {
        return juegoDao.existePartida(codigo);
    }

    // Método para obtener las preguntas con sus respuestas para una partida
    public Map<String, Map<String, Boolean>> obtenerPreguntasConRespuestas(String codigoPartida) {
        return juegoDao.obtenerPreguntasConRespuestas(codigoPartida);
    }
}

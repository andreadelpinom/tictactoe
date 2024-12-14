package newGame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class MaestroDao {

    private final Connection connection;

    public MaestroDao(Connection connection) {
        this.connection = connection;
    }


    public void crearPartida(String codigoPartida, int idProfesor) throws SQLException {
        String sql = "INSERT INTO Partida (codigo_partida, id_profesor, estado, fecha_creacion) VALUES (?, ?, 'activa', CURRENT_TIMESTAMP)";

        try (PreparedStatement stmtPartida = connection.prepareStatement(sql)) {
            stmtPartida.setString(1, codigoPartida); // Código único de la partida
            stmtPartida.setInt(2, idProfesor);      // ID del profesor

            int filasInsertadas = stmtPartida.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println("Partida creada exitosamente con código: " + codigoPartida);
            } else {
                System.out.println("Error al crear la partida.");
            }
        }
    }
    public void insertarPreguntasYRespuestasYAsociarPartida(List<Map<String, Object>> preguntas, String codigoPartida) throws SQLException {
        // Usar autocommit = false para manejar la transacción
        connection.setAutoCommit(false);

        try {
            // Preparar la consulta para insertar preguntas
            String sqlPregunta = "INSERT INTO Pregunta (texto_pregunta) VALUES (?)";
            PreparedStatement stmtPregunta = connection.prepareStatement(sqlPregunta, Statement.RETURN_GENERATED_KEYS);

            // Preparar la consulta para insertar respuestas
            String sqlRespuesta = "INSERT INTO Respuesta (id_pregunta, texto_respuesta, es_correcta) VALUES (?, ?, ?)";
            PreparedStatement stmtRespuesta = connection.prepareStatement(sqlRespuesta);

            // Preparar la consulta para obtener el id_partida
            String sqlPartida = "SELECT id_partida FROM Partida WHERE codigo_partida = ?";
            int idPartida = -1;

            try (PreparedStatement stmtPartida = connection.prepareStatement(sqlPartida)) {
                stmtPartida.setString(1, codigoPartida);
                try (ResultSet rs = stmtPartida.executeQuery()) {
                    if (rs.next()) {
                        idPartida = rs.getInt("id_partida");
                    } else {
                        throw new SQLException("No se encontró la partida con el código: " + codigoPartida);
                    }
                }
            }

            // Preparar la consulta para insertar la asociación entre partida y preguntas
            String sqlInsertarAsociacion = "INSERT INTO Partida_Pregunta (id_partida, id_pregunta) VALUES (?, ?)";
            PreparedStatement stmtInsertarAsociacion = connection.prepareStatement(sqlInsertarAsociacion);

            // Iterar sobre las preguntas y respuestas
            for (Map<String, Object> pregunta : preguntas) {
                String textoPregunta = (String) pregunta.get("texto");
                String[] opciones = (String[]) pregunta.get("opciones");
                int correcta = (int) pregunta.get("correcta");

                // Insertar la pregunta
                stmtPregunta.setString(1, textoPregunta);
                stmtPregunta.executeUpdate();

                // Obtener el ID de la pregunta insertada (el ID generado automáticamente)
                ResultSet generatedKeys = stmtPregunta.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idPregunta = generatedKeys.getInt(1); // Obtener el id_pregunta generado automáticamente

                    // Insertar las respuestas
                    for (int i = 0; i < opciones.length; i++) {
                        String textoRespuesta = opciones[i];
                        boolean esCorrecta = (i + 1) == correcta; // Determinar si la opción es correcta

                        stmtRespuesta.setInt(1, idPregunta);
                        stmtRespuesta.setString(2, textoRespuesta);
                        stmtRespuesta.setBoolean(3, esCorrecta);
                        stmtRespuesta.executeUpdate();
                    }

                    // Asociar la pregunta a la partida
                    stmtInsertarAsociacion.setInt(1, idPartida);
                    stmtInsertarAsociacion.setInt(2, idPregunta);
                    stmtInsertarAsociacion.executeUpdate();
                }
            }

            // Confirmar la transacción
            connection.commit();
            System.out.println("Preguntas y respuestas insertadas y asociadas correctamente a la partida.");
        } catch (SQLException e) {
            // Si ocurre algún error, revertir la transacción
            connection.rollback();
            throw new SQLException("Error al insertar preguntas, respuestas o asociar a la partida", e);
        } finally {
            // Restaurar autocommit
            connection.setAutoCommit(true);
        }
    }
}

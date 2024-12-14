package newGame.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://ep-tight-thunder-a5wysx3d.us-east-2.aws.neon.tech/TicTacToeBD?user=TicTacToeBD_owner&password=2yOp4frzWLnM&sslmode=require";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String ERROR_MESSAGE = "Error al establecer la conexión con la base de datos.";

    // Método para obtener la conexión a la base de datos
    public Connection obtenerConexionBasePostgres() {
        Connection conexion = null;
        try {
            // Cargar el driver
            Class.forName(DRIVER);
            // Establecer la conexión
            conexion = DriverManager.getConnection(URL);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException | ClassNotFoundException e) {
            // Manejo adecuado de la excepción
            System.err.println(ERROR_MESSAGE);
            e.printStackTrace(); // Para detalles adicionales sobre el error
        }
        return conexion;
    }

    // Método para cerrar la conexión
    public void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }
}

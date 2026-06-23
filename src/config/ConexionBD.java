package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de configuración para la conexión a la base de datos MySQL
 * Implementa el patrón Singleton para garantizar una única instancia de conexión
 */
public class ConexionBD {

    private static final String DB_HOST = getEnv("DB_HOST", "localhost");
    private static final String DB_PORT = getEnv("DB_PORT", "3306");
    private static final String DB_NAME = getEnv("DB_NAME", "gestion_donacion_alimentos");
    private static final String URL = getEnv(
        "DB_URL",
        "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
    );
    private static final String USUARIO = getEnv("DB_USER", "root");
    private static final String PASSWORD = getEnv("DB_PASSWORD", "root");

    private static Connection instancia = null;

    // Constructor privado: evita instanciación directa
    private ConexionBD() {}

    private static String getEnv(String nombre, String valorPorDefecto) {
        String valor = System.getenv(nombre);
        return valor == null || valor.isBlank() ? valorPorDefecto : valor;
    }

    /**
     * Retorna la conexión activa. Si no existe o está cerrada, crea una nueva.
     *
     * @return Connection objeto de conexión JDBC
     * @throws SQLException si no se puede establecer la conexión
     */
    public static Connection getConexion() throws SQLException {
        if (instancia == null || instancia.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                instancia = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("Conexión establecida correctamente.");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL no encontrado: " + e.getMessage());
            }
        }
        return instancia;
    }

    /**
     * Cierra la conexión activa si existe y está abierta.
     */
    public static void cerrarConexion() {
        try {
            if (instancia != null && !instancia.isClosed()) {
                instancia.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}

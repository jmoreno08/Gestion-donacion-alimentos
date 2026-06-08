package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de configuración para la conexión a la base de datos MySQL
 * Implementa el patrón Singleton para garantizar una única instancia de conexión
 */
public class ConexionBD {

    // Para Integrante 3 – BD: por favor reemplazar estos tres valores con los datos reales
    private static final String URL      = "jdbc:mysql://localhost:3306/NOMBRE_BASE_DE_DATOS";
    private static final String USUARIO  = "USUARIO_MYSQL";
    private static final String PASSWORD = "CONTRASEÑA_MYSQL";

    private static Connection instancia = null;

    // Constructor privado: evita instanciación directa
    private ConexionBD() {}

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

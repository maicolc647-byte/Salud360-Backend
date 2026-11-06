package app.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión JDBC directa con SQL Server (Instancia SQLEXPRESS01, Base de Datos Sistema_360).
 */
public class DatabaseConnection {

    // Cadena de conexión hacia la instancia local SQLEXPRESS01 y la BD Sistema_360
    private static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS01;databaseName=Sistema_360;encrypt=true;trustServerCertificate=true;";

    // Credenciales del usuario 'sa'
    private static final String USER = "sa";
    private static final String PASSWORD = "Tu contraseña aca"; // Reemplazar por tu clave de 'sa'

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error: No se encontró el driver JDBC de SQL Server: " + e.getMessage());
        }
    }

    // Método de prueba rápida de conexión
    public static void main(String[] args) {
        System.out.println("Intentando conectar a SQL Server (Sistema_360)...");
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("==========================================");
                System.out.println("¡CONEXIÓN EXITOSA A SQL SERVER! 🎉");
                System.out.println("Conectado a: " + conn.getCatalog());
                System.out.println("==========================================");
            }
        } catch (SQLException e) {
            System.err.println("==========================================");
            System.err.println("❌ ERROR DE CONEXIÓN:");
            System.err.println(e.getMessage());
            System.err.println("==========================================");
        }
    }
}

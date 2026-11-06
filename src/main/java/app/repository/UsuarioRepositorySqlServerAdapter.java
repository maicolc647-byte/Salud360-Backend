package app.repository;

import app.configuration.DatabaseConnection;
import app.domain.Usuario;
import app.domain.enums.RolUsuario;
import app.service.outputports.UsuarioRepositoryPort;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Adaptador de Persistencia para Paciente en SQL Server (Sistema_360).
 */
public class UsuarioRepositorySqlServerAdapter implements UsuarioRepositoryPort {

    @Override
    public Usuario guardar(Usuario usuario) {
        String sql = "INSERT INTO Paciente (Id_Paciente, Nombre, Documento, Fecha_nacimiento, Email, Telefono, Direccion, contrasena) VALUES (?, ?, ?, GETDATE(), ?, ?, 'Medellín', ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String soloNumeros = usuario.getId().replaceAll("[^0-9]", "");
            int idPaciente = soloNumeros.isEmpty() ? (int)(System.currentTimeMillis() % 10000) : Integer.parseInt(soloNumeros);

            stmt.setInt(1, idPaciente);
            stmt.setString(2, usuario.getNombreCompleto());
            stmt.setString(3, usuario.getId());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getContrasena());

            stmt.executeUpdate();
            return usuario;

        } catch (SQLException e) {
            System.err.println("Error al guardar paciente en SQL Server: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        String sql = "SELECT * FROM Paciente WHERE Email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String passBD = rs.getString("contrasena") != null ? rs.getString("contrasena") : "1234";

                Usuario u = new Usuario(
                        String.valueOf(rs.getInt("Id_Paciente")),
                        rs.getString("Nombre"),
                        "",
                        rs.getString("Email"),
                        rs.getString("Telefono"),
                        passBD,
                        RolUsuario.PACIENTE
                );
                return Optional.of(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar paciente por correo: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        String sql = "SELECT * FROM Paciente WHERE Id_Paciente = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(id.replaceAll("[^0-9]", "")));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String passBD = rs.getString("contrasena") != null ? rs.getString("contrasena") : "1234";

                Usuario u = new Usuario(
                        String.valueOf(rs.getInt("Id_Paciente")),
                        rs.getString("Nombre"),
                        "",
                        rs.getString("Email"),
                        rs.getString("Telefono"),
                        passBD,
                        RolUsuario.PACIENTE
                );
                return Optional.of(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar paciente por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Paciente";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String passBD = rs.getString("contrasena") != null ? rs.getString("contrasena") : "1234";

                Usuario u = new Usuario(
                        String.valueOf(rs.getInt("Id_Paciente")),
                        rs.getString("Nombre"),
                        "",
                        rs.getString("Email"),
                        rs.getString("Telefono"),
                        passBD,
                        RolUsuario.PACIENTE
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pacientes desde SQL Server: " + e.getMessage());
        }
        return lista;
    }
}

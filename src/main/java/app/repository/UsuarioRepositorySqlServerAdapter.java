package app.repository;

import app.configuration.DatabaseConnection;
import app.domain.Usuario;
import app.domain.enums.RolUsuario;
import app.service.outputports.UsuarioRepositoryPort;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepositorySqlServerAdapter implements UsuarioRepositoryPort {

    @Override
    public Usuario guardar(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (id, nombre, apellido, correo, telefono, contrasena, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getId());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getTelefono());
            ps.setString(6, usuario.getContrasena());
            ps.setString(7, usuario.getRol().name());
            ps.executeUpdate();
            return usuario;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar usuario en SQL Server: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        String sql = "SELECT * FROM Usuarios WHERE correo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por correo: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        String sql = "SELECT * FROM Usuarios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios: " + e.getMessage(), e);
        }
        return lista;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getString("id"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo"),
                rs.getString("telefono"),
                rs.getString("contrasena"),
                RolUsuario.valueOf(rs.getString("rol"))
        );
    }
}

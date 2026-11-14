package app.repository;

import app.configuration.DatabaseConnection;
import app.domain.Medicamento;
import app.service.outputports.FarmaciaRepositoryPort;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FarmaciaRepositorySqlServerAdapter implements FarmaciaRepositoryPort {

    @Override
    public Medicamento guardar(Medicamento medicamento) {
        String sql = "INSERT INTO Medicamentos (nombre, presentacion, stock) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medicamento.getNombre());
            ps.setString(2, medicamento.getPresentacion());
            ps.setInt(3, medicamento.getStock());
            ps.executeUpdate();
            return medicamento;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar medicamento en SQL Server: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Medicamento> buscarPorId(int id) {
        String sql = "SELECT * FROM Medicamentos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearMedicamento(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar medicamento: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Medicamento> listarTodos() {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Medicamentos ORDER BY nombre ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearMedicamento(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar inventario: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public void actualizarStock(int id, int nuevoStock) {
        String sql = "UPDATE Medicamentos SET stock = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nuevoStock);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar stock en SQL Server: " + e.getMessage(), e);
        }
    }

    private Medicamento mapearMedicamento(ResultSet rs) throws SQLException {
        return new Medicamento(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("presentacion"),
                rs.getInt("stock")
        );
    }
}

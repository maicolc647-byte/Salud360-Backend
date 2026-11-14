package app.repository;

import app.configuration.DatabaseConnection;
import app.domain.CitaMedica;
import app.domain.enums.EstadoCita;
import app.service.outputports.CitaRepositoryPort;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CitaRepositorySqlServerAdapter implements CitaRepositoryPort {

    @Override
    public CitaMedica guardar(CitaMedica cita) {
        String sql = "INSERT INTO CitasMedicas (pacienteId, medicoId, fechaHora, motivo, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cita.getPacienteId());
            ps.setString(2, cita.getMedicoId());
            ps.setString(3, cita.getFechaHora());
            ps.setString(4, cita.getMotivo());
            ps.setString(5, cita.getEstado().name());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    cita.setId(keys.getInt(1));
                }
            }
            return cita;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar cita en SQL Server: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<CitaMedica> buscarPorId(int id) {
        String sql = "SELECT * FROM CitasMedicas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearCita(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cita por ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<CitaMedica> listarTodas() {
        List<CitaMedica> lista = new ArrayList<>();
        String sql = "SELECT * FROM CitasMedicas ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCita(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar citas: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public List<CitaMedica> listarPorPaciente(String pacienteId) {
        List<CitaMedica> lista = new ArrayList<>();
        String sql = "SELECT * FROM CitasMedicas WHERE pacienteId = ? ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pacienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCita(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar citas del paciente: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public void actualizarEstado(int id, EstadoCita nuevoEstado) {
        String sql = "UPDATE CitasMedicas SET estado = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado.name());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estado de cita: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existeCruceHorario(String medicoId, String fechaHora) {
        String sql = "SELECT COUNT(*) FROM CitasMedicas WHERE medicoId = ? AND fechaHora = ? AND estado <> 'CANCELADA'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medicoId);
            ps.setString(2, fechaHora);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar cruce de horario: " + e.getMessage(), e);
        }
        return false;
    }

    private CitaMedica mapearCita(ResultSet rs) throws SQLException {
        return new CitaMedica(
                rs.getInt("id"),
                rs.getString("pacienteId"),
                rs.getString("medicoId"),
                rs.getString("fechaHora"),
                rs.getString("motivo"),
                EstadoCita.valueOf(rs.getString("estado"))
        );
    }
}

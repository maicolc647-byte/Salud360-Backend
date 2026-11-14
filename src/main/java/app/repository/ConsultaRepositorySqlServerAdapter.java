package app.repository;

import app.configuration.DatabaseConnection;
import app.domain.ConsultaMedica;
import app.service.outputports.ConsultaRepositoryPort;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepositorySqlServerAdapter implements ConsultaRepositoryPort {

    @Override
    public ConsultaMedica guardar(ConsultaMedica consulta) {
        String sql = "INSERT INTO ConsultasMedicas (citaId, pacienteId, medicoId, diagnostico, tratamiento, fechaRegistro) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, consulta.getCitaId());
            ps.setString(2, consulta.getPacienteId());
            ps.setString(3, consulta.getMedicoId());
            ps.setString(4, consulta.getDiagnostico());
            ps.setString(5, consulta.getTratamiento());
            ps.setString(6, consulta.getFechaRegistro());
            ps.executeUpdate();
            return consulta;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar consulta médica en SQL Server: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ConsultaMedica> listarPorPaciente(String pacienteId) {
        List<ConsultaMedica> lista = new ArrayList<>();
        String sql = "SELECT * FROM ConsultasMedicas WHERE pacienteId = ? ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pacienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearConsulta(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar historial clínico: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public List<ConsultaMedica> listarTodas() {
        List<ConsultaMedica> lista = new ArrayList<>();
        String sql = "SELECT * FROM ConsultasMedicas ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearConsulta(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar consultas médicas: " + e.getMessage(), e);
        }
        return lista;
    }

    private ConsultaMedica mapearConsulta(ResultSet rs) throws SQLException {
        return new ConsultaMedica(
                rs.getInt("id"),
                rs.getInt("citaId"),
                rs.getString("pacienteId"),
                rs.getString("medicoId"),
                rs.getString("diagnostico"),
                rs.getString("tratamiento"),
                rs.getString("fechaRegistro")
        );
    }
}

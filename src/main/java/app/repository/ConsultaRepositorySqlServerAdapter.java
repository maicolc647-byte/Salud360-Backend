package app.repository;

import app.configuration.DatabaseConnection;
import app.domain.ConsultaMedica;
import app.service.outputports.ConsultaRepositoryPort;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultaRepositorySqlServerAdapter implements ConsultaRepositoryPort {

    @Override
    public ConsultaMedica guardar(ConsultaMedica consulta) {
        String sql = "INSERT INTO Consulta (Id_Cita, Motivo_Consulta, Observaciones) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, Integer.parseInt(consulta.getIdCita().replaceAll("[^0-9]", "")));
            stmt.setString(2, consulta.getDiagnostico());
            stmt.setString(3, consulta.getTratamiento());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                consulta.setIdConsulta(String.valueOf(rs.getInt(1)));
            }

            return consulta;

        } catch (SQLException e) {
            System.err.println("Error al guardar consulta en SQL Server: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<ConsultaMedica> buscarPorId(String idConsulta) {
        String sql = "SELECT * FROM Consulta WHERE Id_Consulta = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(idConsulta.replaceAll("[^0-9]", "")));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ConsultaMedica c = new ConsultaMedica(
                        String.valueOf(rs.getInt("Id_Cita")),
                        rs.getString("Motivo_Consulta"),
                        rs.getString("Observaciones")
                );
                c.setIdConsulta(String.valueOf(rs.getInt("Id_Consulta")));
                return Optional.of(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar consulta por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<ConsultaMedica> listarTodas() {
        List<ConsultaMedica> lista = new ArrayList<>();
        String sql = "SELECT * FROM Consulta";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ConsultaMedica c = new ConsultaMedica(
                        String.valueOf(rs.getInt("Id_Cita")),
                        rs.getString("Motivo_Consulta"),
                        rs.getString("Observaciones")
                );
                c.setIdConsulta(String.valueOf(rs.getInt("Id_Consulta")));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar consultas de SQL Server: " + e.getMessage());
        }
        return lista;
    }
}

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
        String sql = "INSERT INTO Citas (Id_Paciente, Id_Profesional, Fecha, Hora, Estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int idPaciente = Integer.parseInt(cita.getIdPaciente().replaceAll("[^0-9]", ""));
            int idMedico = Integer.parseInt(cita.getIdMedico().replaceAll("[^0-9]", ""));

            stmt.setInt(1, idPaciente);
            stmt.setInt(2, idMedico);
            stmt.setDate(3, Date.valueOf(cita.getFecha()));
            stmt.setTime(4, Time.valueOf(cita.getHora()));
            stmt.setString(5, cita.getEstado().name());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cita.setIdCita(String.valueOf(rs.getInt(1)));
            }

            return cita;

        } catch (SQLException e) {
            System.err.println("Error al guardar la cita en SQL Server: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<CitaMedica> buscarPorId(String idCita) {
        String sql = "SELECT * FROM Citas WHERE Id_Cita = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(idCita.replaceAll("[^0-9]", "")));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                CitaMedica cita = new CitaMedica(
                        String.valueOf(rs.getInt("Id_Paciente")),
                        String.valueOf(rs.getInt("Id_Profesional")),
                        rs.getDate("Fecha").toLocalDate(),
                        rs.getTime("Hora").toLocalTime()
                );
                cita.setIdCita(String.valueOf(rs.getInt("Id_Cita")));
                try {
                    cita.setEstado(EstadoCita.valueOf(rs.getString("Estado").toUpperCase()));
                } catch (Exception e) {
                    cita.setEstado(EstadoCita.PROGRAMADA);
                }
                return Optional.of(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cita por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<CitaMedica> listarTodas() {
        List<CitaMedica> lista = new ArrayList<>();
        String sql = "SELECT * FROM Citas";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CitaMedica cita = new CitaMedica(
                        String.valueOf(rs.getInt("Id_Paciente")),
                        String.valueOf(rs.getInt("Id_Profesional")),
                        rs.getDate("Fecha").toLocalDate(),
                        rs.getTime("Hora").toLocalTime()
                );
                cita.setIdCita(String.valueOf(rs.getInt("Id_Cita")));
                try {
                    cita.setEstado(EstadoCita.valueOf(rs.getString("Estado").toUpperCase()));
                } catch (Exception e) {
                    cita.setEstado(EstadoCita.PROGRAMADA);
                }
                lista.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas desde SQL Server: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<CitaMedica> buscarPorPaciente(String idPaciente) {
        List<CitaMedica> lista = new ArrayList<>();
        String sql = "SELECT * FROM Citas WHERE Id_Paciente = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(idPaciente.replaceAll("[^0-9]", "")));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CitaMedica cita = new CitaMedica(
                        String.valueOf(rs.getInt("Id_Paciente")),
                        String.valueOf(rs.getInt("Id_Profesional")),
                        rs.getDate("Fecha").toLocalDate(),
                        rs.getTime("Hora").toLocalTime()
                );
                cita.setIdCita(String.valueOf(rs.getInt("Id_Cita")));
                lista.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar citas por paciente: " + e.getMessage());
        }
        return lista;
    }
}

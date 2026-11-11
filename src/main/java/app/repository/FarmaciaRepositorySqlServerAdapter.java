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
        String sql = "INSERT INTO Medicamentos (Id_Medicamento, Nombre, Laboratorio) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int idMed = Integer.parseInt(medicamento.getIdMedicamento().replaceAll("[^0-9]", ""));

            stmt.setInt(1, idMed);
            stmt.setString(2, medicamento.getNombre());
            stmt.setString(3, medicamento.getLaboratorio() != null ? medicamento.getLaboratorio() : "Genérico");

            stmt.executeUpdate();
            return medicamento;

        } catch (SQLException e) {
            System.err.println("Error al guardar medicamento en SQL Server: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<Medicamento> buscarPorId(String idMedicamento) {
        String sql = "SELECT * FROM Medicamentos WHERE Id_Medicamento = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(idMedicamento.replaceAll("[^0-9]", "")));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Medicamento m = new Medicamento(
                        String.valueOf(rs.getInt("Id_Medicamento")),
                        rs.getString("Nombre"),
                        rs.getString("Laboratorio"),
                        100
                );
                return Optional.of(m);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar medicamento por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Medicamento> listarTodos() {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Medicamentos";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medicamento m = new Medicamento(
                        String.valueOf(rs.getInt("Id_Medicamento")),
                        rs.getString("Nombre"),
                        rs.getString("Laboratorio"),
                        50
                );
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar medicamentos desde SQL Server: " + e.getMessage());
        }
        return lista;
    }
}

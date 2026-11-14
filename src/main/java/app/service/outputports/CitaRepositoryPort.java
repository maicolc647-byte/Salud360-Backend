package app.service.outputports;

import app.domain.CitaMedica;
import app.domain.enums.EstadoCita;
import java.util.List;
import java.util.Optional;

public interface CitaRepositoryPort {
    CitaMedica guardar(CitaMedica cita);
    Optional<CitaMedica> buscarPorId(int id);
    List<CitaMedica> listarTodas();
    List<CitaMedica> listarPorPaciente(String pacienteId);
    void actualizarEstado(int id, EstadoCita nuevoEstado);
    boolean existeCruceHorario(String medicoId, String fechaHora);
}

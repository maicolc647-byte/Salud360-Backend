package app.service.outputports;

import app.domain.CitaMedica;
import java.util.List;
import java.util.Optional;

public interface CitaRepositoryPort {
    CitaMedica guardar(CitaMedica cita);
    Optional<CitaMedica> buscarPorId(String idCita);
    List<CitaMedica> listarTodas();
    List<CitaMedica> buscarPorPaciente(String idPaciente);
}

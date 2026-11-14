package app.service;

import app.domain.CitaMedica;
import app.domain.ConsultaMedica;
import app.domain.enums.EstadoCita;
import app.helpers.DatosValidador;
import app.service.outputports.CitaRepositoryPort;
import app.service.outputports.ConsultaRepositoryPort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsultaServiceImpl {
    private final ConsultaRepositoryPort consultaRepo;
    private final CitaRepositoryPort citaRepo;

    public ConsultaServiceImpl(ConsultaRepositoryPort consultaRepo, CitaRepositoryPort citaRepo) {
        this.consultaRepo = consultaRepo;
        this.citaRepo = citaRepo;
    }

    public ConsultaMedica registrarConsulta(int citaId, String diagnostico, String tratamiento) {
        if (!DatosValidador.esTextoValido(diagnostico) || !DatosValidador.esTextoValido(tratamiento)) {
            throw new IllegalArgumentException("El diagnóstico y el tratamiento son obligatorios.");
        }
        CitaMedica cita = citaRepo.buscarPorId(citaId)
                .orElseThrow(() -> new IllegalArgumentException("No existe la cita médica con ID: " + citaId));
        if (cita.getEstado() == EstadoCita.CANCELADA) {
            throw new IllegalStateException("No se puede atender una cita cancelada.");
        }

        String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        ConsultaMedica consulta = new ConsultaMedica(
                citaId,
                cita.getPacienteId(),
                cita.getMedicoId(),
                diagnostico,
                tratamiento,
                fechaActual
        );
        ConsultaMedica guardada = consultaRepo.guardar(consulta);
        citaRepo.actualizarEstado(citaId, EstadoCita.COMPLETADA);
        return guardada;
    }

    public List<ConsultaMedica> consultarHistorialPaciente(String pacienteId) {
        return consultaRepo.listarPorPaciente(pacienteId);
    }

    public List<ConsultaMedica> listarTodas() {
        return consultaRepo.listarTodas();
    }
}

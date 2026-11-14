package app.service;

import app.domain.CitaMedica;
import app.domain.enums.EstadoCita;
import app.helpers.DatosValidador;
import app.service.outputports.CitaRepositoryPort;
import app.service.outputports.UsuarioRepositoryPort;

import java.util.List;
import java.util.Optional;

public class CitaServiceImpl {
    private final CitaRepositoryPort citaRepo;
    private final UsuarioRepositoryPort usuarioRepo;

    public CitaServiceImpl(CitaRepositoryPort citaRepo, UsuarioRepositoryPort usuarioRepo) {
        this.citaRepo = citaRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public CitaMedica agendarCita(String pacienteId, String medicoId, String fechaHora, String motivo) {
        if (!DatosValidador.esTextoValido(pacienteId) || !DatosValidador.esTextoValido(medicoId) || !DatosValidador.esTextoValido(fechaHora)) {
            throw new IllegalArgumentException("Paciente, médico y fecha/hora son obligatorios.");
        }
        if (usuarioRepo.buscarPorId(pacienteId).isEmpty()) {
            throw new IllegalArgumentException("No existe un paciente registrado con ID: " + pacienteId);
        }
        if (usuarioRepo.buscarPorId(medicoId).isEmpty()) {
            throw new IllegalArgumentException("No existe un médico registrado con ID: " + medicoId);
        }
        if (citaRepo.existeCruceHorario(medicoId, fechaHora)) {
            throw new IllegalArgumentException("El médico ya tiene una cita programada en esa fecha y hora.");
        }
        CitaMedica nuevaCita = new CitaMedica(pacienteId, medicoId, fechaHora, motivo);
        return citaRepo.guardar(nuevaCita);
    }

    public void confirmarCita(int citaId) {
        CitaMedica cita = citaRepo.buscarPorId(citaId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la cita con ID: " + citaId));
        if (cita.getEstado() == EstadoCita.CANCELADA || cita.getEstado() == EstadoCita.COMPLETADA) {
            throw new IllegalStateException("No se puede confirmar una cita en estado: " + cita.getEstado());
        }
        citaRepo.actualizarEstado(citaId, EstadoCita.CONFIRMADA);
    }

    public void cancelarCita(int citaId) {
        CitaMedica cita = citaRepo.buscarPorId(citaId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la cita con ID: " + citaId));
        if (cita.getEstado() == EstadoCita.COMPLETADA) {
            throw new IllegalStateException("No se puede cancelar una cita que ya fue completada.");
        }
        citaRepo.actualizarEstado(citaId, EstadoCita.CANCELADA);
    }

    public List<CitaMedica> listarTodas() {
        return citaRepo.listarTodas();
    }

    public List<CitaMedica> consultarPorPaciente(String pacienteId) {
        return citaRepo.listarPorPaciente(pacienteId);
    }

    public Optional<CitaMedica> buscarPorId(int id) {
        return citaRepo.buscarPorId(id);
    }
}

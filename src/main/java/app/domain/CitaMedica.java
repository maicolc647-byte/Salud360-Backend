package app.domain;

import app.domain.enums.EstadoCita;
import java.time.LocalDate;
import java.time.LocalTime;

public class CitaMedica {
    private String idCita;
    private String idPaciente;
    private String idMedico;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoCita estado;

    public CitaMedica(String idPaciente, String idMedico, LocalDate fecha, LocalTime hora) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = EstadoCita.PROGRAMADA;
    }

    public String getIdCita() { return idCita; }
    public void setIdCita(String idCita) { this.idCita = idCita; }

    public String getIdPaciente() { return idPaciente; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }

    public String getIdMedico() { return idMedico; }
    public void setIdMedico(String idMedico) { this.idMedico = idMedico; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }
}

package app.domain;

import app.domain.enums.EstadoCita;

public class CitaMedica {
    private int id;
    private String pacienteId;
    private String medicoId;
    private String fechaHora;
    private String motivo;
    private EstadoCita estado;

    public CitaMedica(int id, String pacienteId, String medicoId, String fechaHora, String motivo, EstadoCita estado) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estado = estado;
    }

    public CitaMedica(String pacienteId, String medicoId, String fechaHora, String motivo) {
        this(0, pacienteId, medicoId, fechaHora, motivo, EstadoCita.PROGRAMADA);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPacienteId() { return pacienteId; }
    public String getMedicoId() { return medicoId; }
    public String getFechaHora() { return fechaHora; }
    public String getMotivo() { return motivo; }
    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }
}

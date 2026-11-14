package app.domain;

public class ConsultaMedica {
    private int id;
    private int citaId;
    private String pacienteId;
    private String medicoId;
    private String diagnostico;
    private String tratamiento;
    private String fechaRegistro;

    public ConsultaMedica(int id, int citaId, String pacienteId, String medicoId, String diagnostico, String tratamiento, String fechaRegistro) {
        this.id = id;
        this.citaId = citaId;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.fechaRegistro = fechaRegistro;
    }

    public ConsultaMedica(int citaId, String pacienteId, String medicoId, String diagnostico, String tratamiento, String fechaRegistro) {
        this(0, citaId, pacienteId, medicoId, diagnostico, tratamiento, fechaRegistro);
    }

    public int getId() { return id; }
    public int getCitaId() { return citaId; }
    public String getPacienteId() { return pacienteId; }
    public String getMedicoId() { return medicoId; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public String getFechaRegistro() { return fechaRegistro; }
}

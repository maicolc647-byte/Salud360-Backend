package app.domain;

public class ConsultaMedica {
    private String idConsulta;
    private String idCita;
    private String diagnostico;
    private String tratamiento;

    public ConsultaMedica(String idCita, String diagnostico, String tratamiento) {
        this.idCita = idCita;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }

    public String getIdConsulta() { return idConsulta; }
    public void setIdConsulta(String idConsulta) { this.idConsulta = idConsulta; }

    public String getIdCita() { return idCita; }
    public void setIdCita(String idCita) { this.idCita = idCita; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
}

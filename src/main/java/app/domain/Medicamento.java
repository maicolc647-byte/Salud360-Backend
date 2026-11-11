package app.domain;

public class Medicamento {
    private String idMedicamento;
    private String nombre;
    private String laboratorio;
    private int stock;

    public Medicamento(String idMedicamento, String nombre, String laboratorio, int stock) {
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.laboratorio = laboratorio;
        this.stock = stock;
    }

    public String getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(String idMedicamento) { this.idMedicamento = idMedicamento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getLaboratorio() { return laboratorio; }
    public void setLaboratorio(String laboratorio) { this.laboratorio = laboratorio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}

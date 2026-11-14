package app.domain;

public class Medicamento {
    private int id;
    private String nombre;
    private String presentacion;
    private int stock;

    public Medicamento(int id, String nombre, String presentacion, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.stock = stock;
    }

    public Medicamento(String nombre, String presentacion, int stock) {
        this(0, nombre, presentacion, stock);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPresentacion() { return presentacion; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}

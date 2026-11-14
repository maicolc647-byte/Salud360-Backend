package app.domain;

public abstract class Persona {
    protected String id;
    protected String nombre;
    protected String apellido;
    protected String correo;
    protected String telefono;

    public Persona(String id, String nombre, String apellido, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}

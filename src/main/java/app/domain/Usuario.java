package app.domain;

import app.domain.enums.RolUsuario;

public class Usuario extends Persona {
    private String contrasena;
    private RolUsuario rol;

    public Usuario(String id, String nombre, String apellido, String correo, String telefono, String contrasena, RolUsuario rol) {
        super(id, nombre, apellido, correo, telefono);
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getContrasena() { return contrasena; }
    public RolUsuario getRol() { return rol; }
}

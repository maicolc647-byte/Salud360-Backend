package app.service;

import app.domain.Usuario;
import app.service.outputports.UsuarioRepositoryPort;
import java.util.List;
import java.util.Optional;

public class UsuarioServiceImpl {
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public UsuarioServiceImpl(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        Optional<Usuario> existente = usuarioRepositoryPort.buscarPorCorreo(usuario.getCorreo());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con este correo: " + usuario.getCorreo());
        }
        return usuarioRepositoryPort.guardar(usuario);
    }

    public Optional<Usuario> iniciarSesion(String correo, String contrasena) {
        Optional<Usuario> opt = usuarioRepositoryPort.buscarPorCorreo(correo);
        if (opt.isPresent() && opt.get().getContrasena().equals(contrasena)) {
            return opt;
        }
        return Optional.empty();
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepositoryPort.listarTodos();
    }
}

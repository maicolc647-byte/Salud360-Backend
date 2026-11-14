package app.service;

import app.domain.Usuario;
import app.helpers.DatosValidador;
import app.service.outputports.UsuarioRepositoryPort;

import java.util.List;
import java.util.Optional;

public class UsuarioServiceImpl {
    private final UsuarioRepositoryPort usuarioRepo;

    public UsuarioServiceImpl(UsuarioRepositoryPort usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        if (!DatosValidador.esTextoValido(usuario.getId()) || !DatosValidador.esTextoValido(usuario.getNombre())) {
            throw new IllegalArgumentException("El documento y el nombre son obligatorios.");
        }
        if (!DatosValidador.esCorreoValido(usuario.getCorreo())) {
            throw new IllegalArgumentException("El formato del correo electrónico no es válido.");
        }
        if (usuarioRepo.buscarPorCorreo(usuario.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado en el sistema.");
        }
        if (usuarioRepo.buscarPorId(usuario.getId()).isPresent()) {
            throw new IllegalArgumentException("El documento/ID ya se encuentra registrado.");
        }
        return usuarioRepo.guardar(usuario);
    }

    public Optional<Usuario> iniciarSesion(String correo, String contrasena) {
        return usuarioRepo.buscarPorCorreo(correo)
                .filter(u -> u.getContrasena().equals(contrasena));
    }

    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepo.buscarPorId(id);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepo.listarTodos();
    }
}

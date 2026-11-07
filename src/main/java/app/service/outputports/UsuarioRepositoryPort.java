package app.service.outputports;

import app.domain.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorCorreo(String correo);
    Optional<Usuario> buscarPorId(String id);
    List<Usuario> listarTodos();
}
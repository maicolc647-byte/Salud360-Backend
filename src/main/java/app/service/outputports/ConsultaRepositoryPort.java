package app.service.outputports;

import app.domain.ConsultaMedica;
import java.util.List;
import java.util.Optional;

public interface ConsultaRepositoryPort {
    ConsultaMedica guardar(ConsultaMedica consulta);
    Optional<ConsultaMedica> buscarPorId(String idConsulta);
    List<ConsultaMedica> listarTodas();
}

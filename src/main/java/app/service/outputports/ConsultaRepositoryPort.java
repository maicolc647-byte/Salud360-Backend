package app.service.outputports;

import app.domain.ConsultaMedica;
import java.util.List;

public interface ConsultaRepositoryPort {
    ConsultaMedica guardar(ConsultaMedica consulta);
    List<ConsultaMedica> listarPorPaciente(String pacienteId);
    List<ConsultaMedica> listarTodas();
}

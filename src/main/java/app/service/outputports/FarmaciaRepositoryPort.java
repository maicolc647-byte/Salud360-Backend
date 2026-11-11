package app.service.outputports;

import app.domain.Medicamento;
import java.util.List;
import java.util.Optional;

public interface FarmaciaRepositoryPort {
    Medicamento guardar(Medicamento medicamento);
    Optional<Medicamento> buscarPorId(String idMedicamento);
    List<Medicamento> listarTodos();
}

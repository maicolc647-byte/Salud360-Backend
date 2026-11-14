package app.service;

import app.domain.Medicamento;
import app.helpers.DatosValidador;
import app.service.outputports.FarmaciaRepositoryPort;

import java.util.List;

public class FarmaciaServiceImpl {
    private final FarmaciaRepositoryPort farmaciaRepo;

    public FarmaciaServiceImpl(FarmaciaRepositoryPort farmaciaRepo) {
        this.farmaciaRepo = farmaciaRepo;
    }

    public Medicamento registrarMedicamento(String nombre, String presentacion, int stockInicial) {
        if (!DatosValidador.esTextoValido(nombre) || !DatosValidador.esTextoValido(presentacion)) {
            throw new IllegalArgumentException("Nombre y presentación son obligatorios.");
        }
        if (stockInicial < 0) {
            throw new IllegalArgumentException("El stock inicial no puede ser negativo.");
        }
        return farmaciaRepo.guardar(new Medicamento(nombre, presentacion, stockInicial));
    }

    public void despacharMedicamento(int medicamentoId, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a despachar debe ser mayor a cero.");
        }
        Medicamento med = farmaciaRepo.buscarPorId(medicamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado con ID: " + medicamentoId));
        if (med.getStock() < cantidad) {
            throw new IllegalStateException("Stock insuficiente. Disponible: " + med.getStock() + " unidades.");
        }
        farmaciaRepo.actualizarStock(medicamentoId, med.getStock() - cantidad);
    }

    public List<Medicamento> listarInventario() {
        return farmaciaRepo.listarTodos();
    }
}

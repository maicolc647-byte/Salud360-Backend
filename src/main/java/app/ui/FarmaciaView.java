package app.ui;

import app.domain.Medicamento;
import app.service.FarmaciaServiceImpl;

import java.util.List;
import java.util.Scanner;

public class FarmaciaView {
    private final FarmaciaServiceImpl farmaciaService;
    private final Scanner scanner;

    public FarmaciaView(FarmaciaServiceImpl farmaciaService) {
        this.farmaciaService = farmaciaService;
        this.scanner = new Scanner(System.in);
    }

    public void registrarMedicamento() {
        System.out.println("\n--- REGISTRAR MEDICAMENTO EN INVENTARIO ---");
        System.out.print("Nombre del medicamento: ");
        String nombre = scanner.nextLine();
        System.out.print("Presentación (ej. Tabletas 500mg, Jarabe 120ml): ");
        String presentacion = scanner.nextLine();
        System.out.print("Cantidad inicial en stock: ");
        try {
            int stock = Integer.parseInt(scanner.nextLine());
            farmaciaService.registrarMedicamento(nombre, presentacion, stock);
            System.out.println("✅ ¡Medicamento registrado exitosamente en SQL Server!");
        } catch (NumberFormatException e) {
            System.err.println("❌ La cantidad debe ser un número entero.");
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    public void despacharMedicamento() {
        System.out.println("\n--- DESPACHO DE MEDICAMENTOS (FÓRMULA MÉDICA) ---");
        listarInventario();
        System.out.print("Ingrese el ID del medicamento a despachar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Cantidad de unidades a entregar: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            farmaciaService.despacharMedicamento(id, cantidad);
            System.out.println("✅ ¡Medicamento despachado y stock actualizado en SQL Server!");
        } catch (NumberFormatException e) {
            System.err.println("❌ Ingrese valores numéricos válidos.");
        } catch (Exception e) {
            System.err.println("❌ Error en despacho: " + e.getMessage());
        }
    }

    public void listarInventario() {
        System.out.println("\n--- INVENTARIO ACTUAL DE FARMACIA ---");
        List<Medicamento> lista = farmaciaService.listarInventario();
        if (lista.isEmpty()) {
            System.out.println("No hay medicamentos registrados en farmacia.");
        } else {
            for (Medicamento m : lista) {
                System.out.println("ID: " + m.getId() + " | " + m.getNombre()
                        + " (" + m.getPresentacion() + ") | Stock disponible: " + m.getStock());
            }
        }
    }
}

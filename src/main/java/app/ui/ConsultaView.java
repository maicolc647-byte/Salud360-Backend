package app.ui;

import app.domain.ConsultaMedica;
import app.service.ConsultaServiceImpl;

import java.util.List;
import java.util.Scanner;

public class ConsultaView {
    private final ConsultaServiceImpl consultaService;
    private final Scanner scanner;

    public ConsultaView(ConsultaServiceImpl consultaService) {
        this.consultaService = consultaService;
        this.scanner = new Scanner(System.in);
    }

    public void registrarConsulta() {
        System.out.println("\n--- ATENCIÓN Y REGISTRO DE CONSULTA MÉDICA ---");
        System.out.print("Ingrese el ID de la Cita Médica atendida: ");
        try {
            int citaId = Integer.parseInt(scanner.nextLine());
            System.out.print("Diagnóstico clínico: ");
            String diagnostico = scanner.nextLine();
            System.out.print("Tratamiento / Fórmula médica: ");
            String tratamiento = scanner.nextLine();

            consultaService.registrarConsulta(citaId, diagnostico, tratamiento);
            System.out.println("✅ ¡Consulta médica registrada y cita #" + citaId + " marcada como COMPLETADA!");
        } catch (NumberFormatException e) {
            System.err.println("❌ El ID de la cita debe ser numérico.");
        } catch (Exception e) {
            System.err.println("❌ Error al registrar consulta: " + e.getMessage());
        }
    }

    public void consultarHistorialClinico() {
        System.out.println("\n--- HISTORIAL CLÍNICO DEL PACIENTE ---");
        System.out.print("Ingrese el documento (ID) del paciente: ");
        String pacienteId = scanner.nextLine();
        List<ConsultaMedica> historial = consultaService.consultarHistorialPaciente(pacienteId);
        if (historial.isEmpty()) {
            System.out.println("No existen consultas médicas registradas para este paciente.");
        } else {
            for (ConsultaMedica cm : historial) {
                System.out.println("Consulta #" + cm.getId() + " (Cita #" + cm.getCitaId() + ") | Fecha: " + cm.getFechaRegistro()
                        + " | Médico: " + cm.getMedicoId()
                        + "\n   -> Diagnóstico: " + cm.getDiagnostico()
                        + "\n   -> Tratamiento: " + cm.getTratamiento());
            }
        }
    }
}

package app.ui;

import app.domain.CitaMedica;
import app.service.CitaServiceImpl;

import java.util.List;
import java.util.Scanner;

public class CitaView {
    private final CitaServiceImpl citaService;
    private final Scanner scanner;

    public CitaView(CitaServiceImpl citaService) {
        this.citaService = citaService;
        this.scanner = new Scanner(System.in);
    }

    public void agendarCita() {
        System.out.println("\n--- AGENDAR CITA MÉDICA ---");
        System.out.print("ID del Paciente: ");
        String pacienteId = scanner.nextLine();
        System.out.print("ID / Tarjeta Profesional del Médico: ");
        String medicoId = scanner.nextLine();
        System.out.print("Fecha y Hora (ej. 2026-11-15 10:00): ");
        String fechaHora = scanner.nextLine();
        System.out.print("Motivo de la consulta: ");
        String motivo = scanner.nextLine();

        try {
            CitaMedica cita = citaService.agendarCita(pacienteId, medicoId, fechaHora, motivo);
            System.out.println("✅ ¡Cita agendada exitosamente! ID de Cita: " + cita.getId() + " | Estado: " + cita.getEstado());
        } catch (Exception e) {
            System.err.println("❌ Error al agendar cita: " + e.getMessage());
        }
    }

    public void confirmarCita() {
        System.out.println("\n--- CONFIRMAR ASISTENCIA A CITA ---");
        System.out.print("Ingrese el ID de la Cita a confirmar: ");
        try {
            int citaId = Integer.parseInt(scanner.nextLine());
            citaService.confirmarCita(citaId);
            System.out.println("✅ ¡Cita #" + citaId + " confirmada en SQL Server!");
        } catch (NumberFormatException e) {
            System.err.println("❌ Debe ingresar un número de ID válido.");
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    public void cancelarCita() {
        System.out.println("\n--- CANCELAR CITA MÉDICA ---");
        System.out.print("Ingrese el ID de la Cita a cancelar: ");
        try {
            int citaId = Integer.parseInt(scanner.nextLine());
            citaService.cancelarCita(citaId);
            System.out.println("✅ Cita #" + citaId + " cancelada correctamente.");
        } catch (NumberFormatException e) {
            System.err.println("❌ Debe ingresar un número de ID válido.");
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    public void listarTodasLasCitas() {
        System.out.println("\n--- AGENDA GENERAL DE CITAS MÉDICAS ---");
        List<CitaMedica> citas = citaService.listarTodas();
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas.");
        } else {
            for (CitaMedica c : citas) {
                System.out.println("Cita #" + c.getId() + " | Paciente: " + c.getPacienteId()
                        + " | Médico: " + c.getMedicoId() + " | Fecha: " + c.getFechaHora()
                        + " | Estado: [" + c.getEstado() + "] | Motivo: " + c.getMotivo());
            }
        }
    }

    public void consultarCitasPorPaciente() {
        System.out.println("\n--- CONSULTAR CITAS POR PACIENTE ---");
        System.out.print("Ingrese el documento (ID) del paciente: ");
        String pacienteId = scanner.nextLine();
        List<CitaMedica> citas = citaService.consultarPorPaciente(pacienteId);
        if (citas.isEmpty()) {
            System.out.println("No se encontraron citas para el paciente " + pacienteId);
        } else {
            for (CitaMedica c : citas) {
                System.out.println("Cita #" + c.getId() + " | Médico: " + c.getMedicoId()
                        + " | Fecha: " + c.getFechaHora() + " | Estado: [" + c.getEstado() + "] | Motivo: " + c.getMotivo());
            }
        }
    }
}

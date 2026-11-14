package app.ui;

import java.util.Scanner;

public class CliUserInterface {
    private final UserView userView;
    private final CitaView citaView;
    private final ConsultaView consultaView;
    private final FarmaciaView farmaciaView;
    private final Scanner scanner;

    public CliUserInterface(UserView userView, CitaView citaView, ConsultaView consultaView, FarmaciaView farmaciaView) {
        this.userView = userView;
        this.citaView = citaView;
        this.consultaView = consultaView;
        this.farmaciaView = farmaciaView;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion = -1;
        do {
            System.out.println("\n====================================================");
            System.out.println("        SISTEMA INTEGRAL DE SALUD360 - MENÚ         ");
            System.out.println("====================================================");
            System.out.println("--- MÓDULO DE USUARIOS Y ACCESO (EP1) ---");
            System.out.println(" 1. Registrar Paciente");
            System.out.println(" 2. Registrar Médico Especialista");
            System.out.println(" 3. Iniciar Sesión (Login)");
            System.out.println(" 4. Listar Usuarios Registrados");
            System.out.println("--- MÓDULO DE CITAS MÉDICAS (EP2) ---");
            System.out.println(" 5. Agendar Nueva Cita Médica");
            System.out.println(" 6. Confirmar Asistencia a Cita");
            System.out.println(" 7. Cancelar Cita Médica");
            System.out.println(" 8. Consultar Citas por Paciente");
            System.out.println(" 9. Ver Agenda General de Citas");
            System.out.println("--- MÓDULO DE CONSULTA E HISTORIA CLÍNICA (EP3) ---");
            System.out.println("10. Atender Cita y Registrar Consulta Médica");
            System.out.println("11. Consultar Historial Clínico de Paciente");
            System.out.println("--- MÓDULO DE FARMACIA E INVENTARIO (EP4) ---");
            System.out.println("12. Registrar Medicamento en Inventario");
            System.out.println("13. Despachar Medicamento (Descontar Stock)");
            System.out.println("14. Ver Inventario de Farmacia");
            System.out.println("----------------------------------------------------");
            System.out.println(" 0. Salir del Sistema");
            System.out.print("Seleccione una opción: ");

            try {
                String input = scanner.nextLine();
                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1 -> userView.registrarPaciente();
                    case 2 -> userView.registrarMedico();
                    case 3 -> userView.iniciarSesion();
                    case 4 -> userView.listarUsuarios();
                    case 5 -> citaView.agendarCita();
                    case 6 -> citaView.confirmarCita();
                    case 7 -> citaView.cancelarCita();
                    case 8 -> citaView.consultarCitasPorPaciente();
                    case 9 -> citaView.listarTodasLasCitas();
                    case 10 -> consultaView.registrarConsulta();
                    case 11 -> consultaView.consultarHistorialClinico();
                    case 12 -> farmaciaView.registrarMedicamento();
                    case 13 -> farmaciaView.despacharMedicamento();
                    case 14 -> farmaciaView.listarInventario();
                    case 0 -> System.out.println("¡Gracias por utilizar el Sistema Integral Salud360!");
                    default -> System.out.println("⚠️ Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, ingrese un número entero válido.");
            }
        } while (opcion != 0);
    }
}

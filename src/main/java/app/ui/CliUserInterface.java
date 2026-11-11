package app.ui;

import java.util.Scanner;

public class CliUserInterface {
    private final UserView userView;
    private final Scanner scanner;

    public CliUserInterface(UserView userView) {
        this.userView = userView;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion = -1;
        do {
            System.out.println("\n----------------------------------------");
            System.out.println("   SISTEMA DE SALUD360 - MENÚ PRINCIPAL");
            System.out.println("----------------------------------------");
            System.out.println("1. Registrar Paciente");
            System.out.println("2. Registrar Médico Especialista");
            System.out.println("3. Iniciar Sesión (Login)");
            System.out.println("4. Listar Usuarios Registrados");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                String input = scanner.nextLine();
                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1 -> userView.registrarPaciente();
                    case 2 -> userView.registrarMedico();
                    case 3 -> userView.iniciarSesion();
                    case 4 -> userView.listarUsuarios();
                    case 0 -> System.out.println("¡Gracias por utilizar Salud360!");
                    default -> System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número entero válido.");
            }
        } while (opcion != 0);
    }
}

package app;

import app.configuration.Config;
import app.ui.CliUserInterface;

/**
 * Clase principal de entrada a la aplicación Salud360.
 */
public class Application {

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema Backend Salud360 con SQL Server...");
        CliUserInterface cli = Config.getCliUserInterface();
        cli.mostrarMenu();
    }
}

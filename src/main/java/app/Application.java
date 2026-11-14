package app;

import app.configuration.Config;
import app.ui.CliUserInterface;

public class Application {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema Backend Salud360 con SQL Server...");
        CliUserInterface cli = Config.getCliUserInterface();
        cli.mostrarMenu();
    }
}


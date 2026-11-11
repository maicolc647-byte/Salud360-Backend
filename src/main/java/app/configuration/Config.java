package app.configuration;

import app.domain.Usuario;
import app.domain.enums.RolUsuario;
import app.repository.*;
import app.service.*;
import app.service.outputports.*;
import app.ui.*;

/**
 * Contenedor de Inyección de Dependencias para Salud360.
 */
public class Config {

    private static UsuarioRepositoryPort usuarioRepo;
    private static CitaRepositoryPort citaRepo;
    private static ConsultaRepositoryPort consultaRepo;
    private static FarmaciaRepositoryPort farmaciaRepo;

    private static UsuarioServiceImpl usuarioService;
    private static UserView userView;
    private static CliUserInterface cliUserInterface;

    public static CliUserInterface getCliUserInterface() {
        if (cliUserInterface == null) {

            // 1. Adaptadores de Persistencia SQL Server (Sistema_360)
            usuarioRepo = new UsuarioRepositorySqlServerAdapter();
            citaRepo = new CitaRepositorySqlServerAdapter();
            consultaRepo = new ConsultaRepositorySqlServerAdapter();
            farmaciaRepo = new FarmaciaRepositorySqlServerAdapter();

            // 2. Servicios de Dominio
            usuarioService = new UsuarioServiceImpl(usuarioRepo);

            // 3. Vistas e Interfaz
            userView = new UserView(usuarioService);
            cliUserInterface = new CliUserInterface(userView);

            // 4. Datos Semilla (Con captura de excepciones para no romper la ejecución si el correo ya existe)
            cargarDatosSemilla();
        }
        return cliUserInterface;
    }

    private static void cargarDatosSemilla() {
        try {
            usuarioService.registrarUsuario(new Usuario("999", "Prueba", "Semilla", "semilla@gmail.com", "3000000000", "1234", RolUsuario.PACIENTE));
        } catch (IllegalArgumentException e) {
            System.out.println("ℹ️ Nota: Los datos semilla ya se encuentran registrados en SQL Server.");
        }
    }
}

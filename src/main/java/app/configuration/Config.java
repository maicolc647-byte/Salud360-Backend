package app.configuration;

import app.domain.Usuario;
import app.domain.enums.RolUsuario;
import app.repository.*;
import app.service.*;
import app.service.outputports.*;
import app.ui.*;

/**
 * Contenedor de Inyección de Dependencias para Salud360 (5 Épicas Integradas).
 */
public class Config {

    private static UsuarioRepositoryPort usuarioRepo;
    private static CitaRepositoryPort citaRepo;
    private static ConsultaRepositoryPort consultaRepo;
    private static FarmaciaRepositoryPort farmaciaRepo;

    private static UsuarioServiceImpl usuarioService;
    private static CitaServiceImpl citaService;
    private static ConsultaServiceImpl consultaService;
    private static FarmaciaServiceImpl farmaciaService;

    private static UserView userView;
    private static CitaView citaView;
    private static ConsultaView consultaView;
    private static FarmaciaView farmaciaView;
    private static CliUserInterface cliUserInterface;

    public static CliUserInterface getCliUserInterface() {
        if (cliUserInterface == null) {
            // 1. Adaptadores de Persistencia SQL Server (Sistema_360)
            usuarioRepo = new UsuarioRepositorySqlServerAdapter();
            citaRepo = new CitaRepositorySqlServerAdapter();
            consultaRepo = new ConsultaRepositorySqlServerAdapter();
            farmaciaRepo = new FarmaciaRepositorySqlServerAdapter();

            // 2. Servicios de Dominio (Lógica de Negocio)
            usuarioService = new UsuarioServiceImpl(usuarioRepo);
            citaService = new CitaServiceImpl(citaRepo, usuarioRepo);
            consultaService = new ConsultaServiceImpl(consultaRepo, citaRepo);
            farmaciaService = new FarmaciaServiceImpl(farmaciaRepo);

            // 3. Vistas e Interfaz de Consola (Adaptadores Primarios)
            userView = new UserView(usuarioService);
            citaView = new CitaView(citaService);
            consultaView = new ConsultaView(consultaService);
            farmaciaView = new FarmaciaView(farmaciaService);

            cliUserInterface = new CliUserInterface(userView, citaView, consultaView, farmaciaView);

            // 4. Datos Semilla iniciales para pruebas inmediatas
            cargarDatosSemilla();
        }
        return cliUserInterface;
    }

    private static void cargarDatosSemilla() {
        try {
            usuarioService.registrarUsuario(new Usuario("999", "Carlos", "Gomez", "paciente@salud360.com", "3001112233", "1234", RolUsuario.PACIENTE));
            usuarioService.registrarUsuario(new Usuario("888", "Dra. Laura", "Martinez", "medico@salud360.com", "3004445566", "1234", RolUsuario.MEDICO));
            farmaciaService.registrarMedicamento("Acetaminofén", "Tabletas 500mg", 100);
            farmaciaService.registrarMedicamento("Ibuprofeno", "Cápsulas 400mg", 60);
            System.out.println("✅ Datos semilla iniciales registrados en SQL Server.");
        } catch (Exception e) {
            System.out.println("ℹ️ Nota: Los datos semilla ya se encuentran registrados en SQL Server.");
        }
    }
}

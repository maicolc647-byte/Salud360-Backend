package app.ui;

import app.domain.Usuario;
import app.domain.enums.RolUsuario;
import app.service.UsuarioServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserView {
    private final UsuarioServiceImpl usuarioService;
    private final Scanner scanner;

    public UserView(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
        this.scanner = new Scanner(System.in);
    }

    public void registrarPaciente() {
        System.out.println("\n--- REGISTRO DE PACIENTE ---");
        System.out.print("Ingrese número de documento (ID): ");
        String id = scanner.nextLine();
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese el correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String contrasena = scanner.nextLine();

        try {
            Usuario u = new Usuario(id, nombre, apellido, correo, telefono, contrasena, RolUsuario.PACIENTE);
            usuarioService.registrarUsuario(u);
            System.out.println("✅ ¡Paciente registrado exitosamente en SQL Server!");
        } catch (Exception e) {
            System.err.println("❌ Error al registrar paciente: " + e.getMessage());
        }
    }

    public void registrarMedico() {
        System.out.println("\n--- REGISTRO DE MÉDICO ESPECIALISTA ---");
        System.out.print("Ingrese número de tarjeta profesional (ID): ");
        String id = scanner.nextLine();
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese el correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String contrasena = scanner.nextLine();

        try {
            Usuario u = new Usuario(id, nombre, apellido, correo, telefono, contrasena, RolUsuario.MEDICO);
            usuarioService.registrarUsuario(u);
            System.out.println("✅ ¡Médico especialista registrado exitosamente en SQL Server!");
        } catch (Exception e) {
            System.err.println("❌ Error al registrar médico: " + e.getMessage());
        }
    }

    public void iniciarSesion() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Ingrese su correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        Optional<Usuario> opt = usuarioService.iniciarSesion(correo, contrasena);
        if (opt.isPresent()) {
            Usuario u = opt.get();
            System.out.println("🎉 ¡Bienvenido(a) " + u.getNombreCompleto() + "! Rol: " + u.getRol());
        } else {
            System.err.println("❌ Credenciales incorrectas o usuario no registrado.");
        }
    }

    public void listarUsuarios() {
        System.out.println("\n--- LISTA DE PACIENTES Y USUARIOS EN SQL SERVER ---");
        List<Usuario> lista = usuarioService.obtenerTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay usuarios registrados en la base de datos.");
        } else {
            for (Usuario u : lista) {
                System.out.println("ID: " + u.getId() + " | Nombre: " + u.getNombreCompleto() + " | Email: " + u.getCorreo() + " | Tel: " + u.getTelefono());
            }
        }
    }
}

package app.helpers;

public class DatosValidador {
    public static String limpiarDocumento(String documento) {
        if (documento == null) return "";
        return documento.replaceAll("[^0-9]", "");
    }

    public static boolean esCorreoValido(String correo) {
        return correo != null && correo.contains("@") && correo.contains(".");
    }

    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}

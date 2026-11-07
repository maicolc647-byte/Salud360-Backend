package app.helpers;

import java.util.regex.Pattern;


public class DatosValidador {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");


    public static String limpiarDocumento(String documento) {
        if (documento == null) return "";
        return documento.replaceAll("[^0-9]", "");
    }



    public static boolean esEmailValido(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }


    public static boolean esTarjetaProfesionalValida(String tarjeta) {
        if (tarjeta == null) return false;
        String tarjetaLimpia = tarjeta.trim();
        return !tarjetaLimpia.isEmpty() && tarjetaLimpia.length() >= 4;
    }
}
package Exceptions;

public class ContrasenaNoValidaExcepcion extends Exception {
    private String contrasena1;
    private String contrasena2;

    public ContrasenaNoValidaExcepcion(String  contrasena1, String  contrasena2){
        super("Las contraseñas no coinciden. Entrada incorrecta");
        this.contrasena1 = contrasena1;
        this.contrasena2 = contrasena2;
    }

    public String toString(){
        return "Excepcion Usuario <" + contrasena1 + ", " + contrasena2 + ">";
    }

}

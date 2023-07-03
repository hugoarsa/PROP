package Exceptions;

public class ContrasenaVaciaException extends Exception {

    public ContrasenaVaciaException(){
        super("La contrasena entrada es vacía");
    }

    public String toString(){
        return "Excepcion Usuario";
    }

}

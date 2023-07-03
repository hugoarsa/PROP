package Exceptions;

public class AutorNoExisteException extends Exception {
    private String filename;
    public AutorNoExisteException(String s) {
        super("Error en autor " + s + ", autor no existe");
        filename = s;
    }
    public String toString() {
        return "Excepción CjtAutores" + filename;
    }
}

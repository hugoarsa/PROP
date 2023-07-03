package Exceptions;

public class UsuarioNoExisteException extends Exception {
    private String username;

    public UsuarioNoExisteException(String username){
        super("El usuario con username: <" + username + "> no existe");
        this.username = username;
    }

    public String toString(){
        return "Excepcion Archivo <" + username + ">";
    }

}

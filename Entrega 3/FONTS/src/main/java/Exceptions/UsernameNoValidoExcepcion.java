package Exceptions;

public class UsernameNoValidoExcepcion extends Exception {
    private String username;

    public UsernameNoValidoExcepcion(String username){
        super("El username: <" + username + "> ya existe");
        this.username = username;
    }

    public String toString(){
        return "Excepcion Usuario <" + username + ">";
    }

}

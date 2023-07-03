package Exceptions;

import Domain.ExpBooleana;

public class ComillasException extends Exception {

    private String exp;


    public ComillasException(String exp){
        super("La expresion tiene unas comillas abiertas al final");
        this.exp = exp;

    }

    public String toString(){
        return "Excepcion Comillas abiertas en Expresion \"" + exp + "\"";
    }
}

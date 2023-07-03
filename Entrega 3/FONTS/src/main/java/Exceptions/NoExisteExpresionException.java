package Exceptions;

import Domain.ExpBooleana;

public class NoExisteExpresionException extends Exception {

    private String exp;


    public NoExisteExpresionException(String exp){
        super("La expresion no existe dentro del controlador");
        this.exp = exp;
    }

    public String toString(){
        return "Excepcion no existe expresion en Expresion Controlador \"" + exp + "\"";
    }
}

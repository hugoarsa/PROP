package Exceptions;

import Domain.ExpBooleana;

public class CaracterIlegalException extends Exception {

    private String exp;

    public CaracterIlegalException(String exp, char c){
        super("La expresion contiene un caracter ilegal de entre el conjunto no aceptado de { [ . : ? ] + }");
        this.exp = exp;
    }

    public String toString(){
        return "Excepcion caracter ilegal en Expresion \"" + exp + "\"";
    }
}

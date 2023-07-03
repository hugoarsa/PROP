package Exceptions;

import Domain.ExpBooleana;

public class MalParentesisException extends Exception {

    private String exp;


    public MalParentesisException(String exp){
        super("La expresion no esta bien parentizada");
        this.exp = exp;

    }

    public String toString(){
        return "Excepcion Mal Uso de Parentesis en  Expresion \"" + exp + "\"";
    }
}

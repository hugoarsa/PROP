package Exceptions;

import Domain.ExpBooleana;

public class MalUsoOperandosException extends Exception {

    private String exp;


    public MalUsoOperandosException (String exp){
        super("La expresion no usa bien sus operandos");
        this.exp = exp;

    }

    public String toString(){
        return "Excepcion Mal Uso de Operandos Expresion";
    }
}

package Exceptions;

import Domain.ExpBooleana;

public class FormatoInadecuadoException extends Exception {

    private String path;


    public FormatoInadecuadoException(String path){
        super("El archivo en \"" + path + "\" tiene un formato inadecuado");
        this.path = path;
    }

    public String toString(){
        return "Excepcion el formado es inadecuado en el archivo \"" + path + "\"";
    }
}
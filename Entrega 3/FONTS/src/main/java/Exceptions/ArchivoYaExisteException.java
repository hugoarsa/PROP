package Exceptions;

import Domain.Archivo;

public class ArchivoYaExisteException extends Exception {
    private String titulo;
    private String autor;

    public ArchivoYaExisteException(String titulo, String autor){
        super("El archivo con <titulo, autor>: <" + titulo + ", " + autor +"> ya existe");
        this.titulo = titulo;
        this.autor = autor;
    }

    public String toString(){
        return "Excepcion Archivo <" + titulo + ", " + autor + ">";
    }

}

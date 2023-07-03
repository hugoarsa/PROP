package Exceptions;

import Domain.Archivo;

public class ArchivoNoExisteException extends Exception {
    private String titulo;
    private String autor;

    public ArchivoNoExisteException(String titulo, String autor){
        super("El archivo con <titulo, autor>: <" + titulo + ", " + autor +"> no existe");
        this.titulo = titulo;
        this.autor = autor;
    }

}

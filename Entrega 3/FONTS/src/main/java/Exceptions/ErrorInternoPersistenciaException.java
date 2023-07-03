package Exceptions;

public class ErrorInternoPersistenciaException extends Exception {

    public ErrorInternoPersistenciaException(String mssg){
        super(mssg);
    }

    public String toString(){
        return "ERROR: No se ha podido tratar el Archivo correctamente";
    }
}


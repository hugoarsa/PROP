package Exceptions;

public class AtributoNuloException extends Exception {

    String atributo;

    public AtributoNuloException(String atributo){
        super("Error: El atributo " + atributo + " esta vacío o contiene carácteres ilegales");
        this.atributo = atributo;
    }

    public String toString(){
        return "Excepcion formato atributo <" + atributo + ">";
    }

}


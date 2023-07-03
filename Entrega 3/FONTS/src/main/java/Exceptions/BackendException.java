package Exceptions;

public class BackendException extends Exception{
    private String cerr;
    private String mssg;

    public BackendException(String cerr, String mssg){
        super(cerr);
        this.cerr = cerr;
        this.mssg = mssg;
    }

    public String toString() {
        return mssg;
    }
}

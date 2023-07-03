package Domain;

import java.util.*;
import java.io.Serializable;

public class VectorMEV implements java.io.Serializable{

    /**
     * Mapa que registra para cada palabra su núm. apariciones
     */
    private Map<String, Integer> TermCounter;
    /**
     * Entero que registra el tamaño del texto en núm. total de palabras
     */
    private int size;

    /**
     * Divide el String de entrada en las palabras que lo forman, registrando el
     * número de apariciones y lo guarda en una nueva instancia de VectorMEV
     * @param texto
     */
    public VectorMEV(String texto){
        TermCounter = new HashMap<String, Integer>();
        texto = texto.toLowerCase();
        String[] stringArray = texto.split("[ ,.;:!?\\n]+");
        size = stringArray.length;
        for (String aux : stringArray){
            Integer val = TermCounter.get(aux);
            if(val != null)
                TermCounter.put(aux, val + 1);
            else
                TermCounter.put(aux, 1);
        }
    }

    /**
     * Devuelve un mapa que contiene las palabras del texto y, para cada una,
     * su número de apariciones
     * @return TermCounter
     */
    public Map<String, Integer> consulta_pesos_TC(){
        return TermCounter;
    }
    /**
     * Devuelve el tamaño del texto en número de palabras
     * @return size
     */
    public Integer consulta_size(){
        return size;
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof VectorMEV))
            return false;
        VectorMEV other = (VectorMEV)o;
        boolean mapEquals = (this.TermCounter == null && other.TermCounter == null)
                || (this.TermCounter != null && this.TermCounter.equals(other.TermCounter));
        boolean sizeEquals = this.size == other.size;
        return mapEquals && sizeEquals;
    }
}
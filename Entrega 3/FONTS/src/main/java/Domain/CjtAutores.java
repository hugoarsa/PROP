package Domain;

import java.io.IOException;
import java.util.*;
import java.lang.String;

import Exceptions.AutorNoExisteException;
import Persistence.GestorAutor;

public class CjtAutores {

    /**
     * Map que contiene como clave la clave primaria de un autor (su nombre) y tiene por contenido la cantidad de
     * veces que este aparece en los documentos
     */
    private Map<String, Integer> autores; //Map<String, int>
    /**
     * Referencia a la clase GestorAutor para servirse de sus funcionalidades y persistir los autores
     */
    private GestorAutor GA;
    /**
     * Declaración para singleton
     */
    private static CjtAutores INSTANCE;

    /**
     * Creadora que inicializa el map según la persistencia
     * @exception IOException Salta cuando hay errores de lectura
     * @exception ClassNotFoundException La clase no existe
     */
    private CjtAutores() throws IOException, ClassNotFoundException {
            GA = new GestorAutor();
            autores = GA.get_autores();
    }

    /**
     * Función necesaria para el singleton
     * @exception IOException Salta cuando hay errores de lectura
     * @exception ClassNotFoundException La clase no existe
     */
    public static CjtAutores getINSTANCE() throws IOException, ClassNotFoundException {
        if(INSTANCE == null)
            INSTANCE = new CjtAutores();
        return INSTANCE;
    }

    /**
     * Inserta un autor en el conjunto. Si ya existía, se incrementa el contador de títulos que tiene ese autor.
     * Sino, se añade el nuevo autor con un contador de 1 (de momento solo tiene 1 título asociado). El contador
     * de títulos representa cuántos títulos tienen a "autor" como su autor.
     * @param autor
     * @exception IOException Salta cuando hay errores de lectura
     */
    public void insertar_autor(String autor) throws IOException {
        Integer val = autores.get(autor);
        if(val != null){
            autores.put(autor, val+1);
            GA.guardar_autor(autor, val+1);
        }
        else{
            autores.put(autor, 1);
            GA.guardar_autor(autor, 1);
        }
    }

    /**
     * Elimina una instancia de autor del registro del sistema.
     * Se decrementa en 1 el contador de títulos del autor de entrada, en caso
     * que éste fuera a volverse 0, se elimina el autor del registro. (Se considera
     * que ya no hay archivos que tengan a "autor" como autor y, por lo tanto, no es importante
     * en el sistema)
     * @param autor
     * @exception AutorNoExisteException Autor no existe en el conjunto
     * @exception IOException Salta cuando hay errores de lectura
     */
    public void eliminar_autor(String autor) throws AutorNoExisteException, IOException {
        Integer val = autores.get(autor);
        if(val == null){
            throw new AutorNoExisteException(autor);
        }
        else{
            if(val > 1) {
                autores.put(autor, val-1);
                GA.guardar_autor(autor, val-1);
            }
            else {
                autores.remove(autor);
                GA.eliminar_autor(autor);
            }
        }
    }

    /**
     * Devuelve el conjunto de autores existentes en el sistema
     * @return cjt_autores
     */
    public Set<String> listar_autores(String prefijo) {
        //Hacer un set con los nombres de los autores.
        SortedSet<String> cjt_autores;
        cjt_autores = new TreeSet<String>();
        int length = prefijo.length();
        for (Map.Entry<String, Integer> entry : autores.entrySet()) {
            if(entry.getKey().substring(0,length).equals(prefijo)) cjt_autores.add(entry.getKey());
        }
        return cjt_autores;
    }

    /**
     * Vacía el map autores.
     */
    public void clearCache() {
        autores = new HashMap<String, Integer>();
    }
}

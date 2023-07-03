package Persistence;

import Domain.CjtAutores;
import java.util.*;
import java.io.*;

public class GestorAutor {

    /**
     * Directorio donde guardamos los archivos
     */
    private String path;
    /**
     * Referencia al gestor para leer y escribir de forma basica
     */
    private GestorIO IO;

    /**
     * Creadora que inicializa el path segun es necesario e inicializa las referencias a clases de input output
     */
    public GestorAutor(){
        path = "./bin/AutorSer/";
        IO = new GestorIO();
        IO.create_dir(path);
    }

    /**
     * Guarda un autor en persistencia
     * @param autor autor a guardar
     * @param val numero de instancias de este en el sistema
     */
    public void guardar_autor(String autor, Integer val) throws IOException {
        String filename = autor + ".ser";

        IO.guardar_objeto(path + filename,val);
    }

    /**
     * Elimina el autor de nombre el parametro
     * @param autor nombre del autor a eliminar
     */
    public void eliminar_autor(String autor){
        String filename = autor + ".ser";

        IO.eliminar_objeto(path + filename);
    }

    /**
     * Función que devuelve todos los autores para cargar la persistencia on boot
     * @return autoresResult
     * @throws IOException excepcion de entrada y salida
     * @throws ClassNotFoundException clase no encontrada
     */
    public Map<String, Integer> get_autores() throws IOException, ClassNotFoundException {
        Map<String, Integer> autoresResult = new HashMap<String, Integer>();
        File dir = new File(path);
        String files[] = dir.list();
        for(String filename : files){
            String autor = filename.split(".ser")[0];

            Integer val = (Integer)IO.cargar_objeto(path + filename);

            autoresResult.put(autor, val);
        }
        return autoresResult;
    }

    /**
     * Borra los autores de persistencia
     */
    public void clear_autores(){
        IO.clear_objetos(path);
    }
}

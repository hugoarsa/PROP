package Persistence;

import Domain.Usuario;

import java.io.File;
import java.util.*;
import java.io.*;

public class GestorIO {

    public GestorIO(){

    }

    /**
     * Crea un directorio segun el path
     * @param path path del directorio a crear
     */
    public void create_dir(String path){
        File file = new File(path);
        file.mkdirs();
    }

    /**
     * Guarda un objeto generico obj serializado en la localizacion path
     * @param path lugar en el que guardar
     * @param obj documento a serializar
     * @throws IOException error de lectura y escritura
     */
    public void guardar_objeto(String path, Object obj) throws IOException {
        FileOutputStream file = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(file);

        out.writeObject(obj);

        out.close();
        file.close();
    }

    /**
     * Carga un objeto generico del path dado
     * @param path path del objeto a cargar
     * @return result objeto a cargar
     * @throws IOException error de lectura y escritura
     * @throws ClassNotFoundException clase no encontrada
     */
    public Object cargar_objeto(String path) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(file);

        Object result = in.readObject();

        in.close();
        file.close();
        return result;
    }

    /**
     * Elimina el objeto objetivo
     * @param path path del objeto a eliminar
     */
    public void eliminar_objeto(String path){
        File file = new File(path);
        if(file.delete())
            System.out.println("successful deletion");
        else
            System.out.println("failed deletion");
    }

    /**
     * Elimina todos los objetos de un determinado path
     * @param path path del que eliminar todos los objetos
     */
    public void clear_objetos(String path){
        File dir = new File(path);
        File filesList[] = dir.listFiles();
        for(File file : filesList){
            file.delete();
        }
    }
}

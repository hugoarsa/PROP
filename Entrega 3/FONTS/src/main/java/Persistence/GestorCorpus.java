package Persistence;

import Domain.Pair;
import Domain.Usuario;
import Domain.VectorMEV;
import java.util.*;
import java.io.*;

public class GestorCorpus {
    /**
     * Path donde se guarda la informacion de corpus
     */
    private String path;
    /**
     * Path donde se guardan las frecuencias de palabras
     */
    private String dirDC;
    /**
     * Path donde se guardan los vectores individuales
     */
    private String dirArchivoVector;
    /**
     * Instancia al gestor de input output basico
     */
    private GestorIO IO;

    /**
     * Inicilaiza el sistema de carpetas para que el corpus funcione de forma adecuada
     */
    public GestorCorpus(){
        path = "./bin/CorpusSer/";
        dirDC = "DC/";
        dirArchivoVector = "archivoVector/";
        IO = new GestorIO();
        IO.create_dir(path + dirDC);
        IO.create_dir(path + dirArchivoVector);
    }

    /**
     * Guardar frecuencias de palabras
     * @param word palabra a guardar
     * @param count cuantas veces aparece
     * @throws IOException error lectura escritura
     */
    public void guardar_DC(String word, Integer count) throws IOException {
        String filename = word + ".ser";

        IO.guardar_objeto(path + dirDC + filename,count);
    }

    /**
     * Elimina una palabra de las frecuencias guardadas
     * @param word palabra a eliminar
     */
    public void eliminar_DC(String word) {
        String filename = word + ".ser";

        IO.eliminar_objeto(path + dirDC + filename);
    }

    /**
     * Guarda un archivo con su vector
     * @param titulo titulo del archivo
     * @param autor autor del archivo
     * @param vec vector a guardar
     * @throws IOException error de lectura y escritura
     */
    public void guardar_archivoVector(String titulo, String autor, VectorMEV vec) throws IOException {
        String filename = titulo + "@" + autor + ".ser";

        IO.guardar_objeto(path + dirArchivoVector + filename,vec);
    }

    /**
     * Elimina un archivo con su vector
     * @param titulo titulo del archivo
     * @param autor autor del archivo
     */
    public void eliminar_archivoVector(String titulo, String autor){
        String filename = titulo + "@" + autor + ".ser";

        IO.eliminar_objeto(path + dirArchivoVector + filename);
    }

    /**
     * Guarda el tamaño de la suma de los documentos
     * @param size tamaño de los documentos
     * @throws IOException error de lectura y escritura
     */
    public void guardar_sizeDocSum(Integer size) throws IOException {
        String filename = "sizeDocSum.ser";

        IO.guardar_objeto(path + filename,size);
    }

    /**
     * Consigue todas las palabras con sus frecuencias
     * @return DCResult mapa con todas las palabras y sus frecuencias
     * @throws IOException error de lectura y escritura
     * @throws ClassNotFoundException clase no encontrada
     */
    public Map<String, Integer> get_DC() throws IOException, ClassNotFoundException {
        Map<String, Integer> DCResult = new HashMap<>();
        File dir = new File(path + dirDC);
        String files[] = dir.list();
        for(String filename : files){
            String word = filename.split(".ser")[0];

            Integer count = (Integer)IO.cargar_objeto(path + dirDC + filename);

            DCResult.put(word, count);
        }
        return DCResult;
    }

    /**
     * Consigue todos los archivos y sus vectores
     * @return archivoVectorResult mapa con todos los archivos y sus vectores
     * @throws IOException error de lectura y escritura
     * @throws ClassNotFoundException clase no encontrada
     */
    public Map<Pair<String, String>, VectorMEV> get_archivoVector() throws IOException, ClassNotFoundException {
        Map<Pair<String, String>, VectorMEV> archivoVectorResult = new HashMap<>();
        File dir = new File(path + dirArchivoVector);
        String files[] = dir.list();
        for(String filename : files){
            String titulo = filename.split("@|.ser")[0];
            String autor = filename.split("@|.ser")[1];

            VectorMEV vec = (VectorMEV)IO.cargar_objeto(path + dirArchivoVector + filename);

            archivoVectorResult.put(new Pair<String, String>(titulo, autor), vec);
        }
        return archivoVectorResult;
    }

    /**
     * Pide el numero del documento
     * @return num Integer que dice el tamaño del documento
     * @throws IOException error de lectura y escritura
     * @throws ClassNotFoundException clase no encontrada
     */
    public Integer get_sizeDocSum() throws IOException, ClassNotFoundException {
        if(new File(path + "sizeDocSum.ser").exists()) {
            Integer num = (Integer)IO.cargar_objeto(path + "sizeDocSum.ser");
            return num;
        }
        else return 0;
    }

    /**
     * Limpia el corpus
     */
    public void clear_corpus(){
        File sizeFile = new File(path + "sizeDocSum.ser");
        sizeFile.delete();

        IO.clear_objetos(path + dirDC);
        IO.clear_objetos(path + dirArchivoVector);
    }
}

package Persistence;

import Domain.ExpBooleana;
import java.util.*;
import java.io.*;
import Domain.Pair;

public class GestorBooleana {
    /**
     * Path en el cual se guardan las expresiones booleanas
     */
    private String path;
    /**
     * Referencia a un gestor input output para facilitar la lectura del codigo y el reciclaje de este
     */
    private GestorIO IO;

    /**
     * Inicializa el gestor de persistencia con el path adecuado y crea los directorios necesarios para
     * el correcto funcionamiento de la persistencia
     */
    public GestorBooleana(){
        path = "./bin/BooleanaSer/";
        IO = new GestorIO();
        IO.create_dir(path);
    }

    /**
     * Guarda una expresion exp en persistencia en el directorio ./bin/BooleanaSer/ con el nombre de archivo segun
     * el id de la expresion en particular con un archivo .ser
     * @param exp Expresion a guardar en el sistema con el nombre de archivo segun su clave primaria indexada
     * @throws IOException prblema de lectura escritura
     */
    public void guardar_expresion(ExpBooleana exp) throws IOException {
        String filename = exp.to_Id() + ".ser";

        IO.guardar_objeto(path + filename,exp);
    }

    /**
     * Carga una expresion de persistencia del directorio ./bin/BooleanaSer/ con el nombre de archivo segun
     * el id proporcionado en el parametro ident
     * @param ident id de la expresion a cargar
     * @return exp
     * @throws IOException error en lectura escritura
     * @throws ClassNotFoundException no encuentra la clase
     */
    public ExpBooleana cargar_expresion(Integer ident) throws IOException, ClassNotFoundException {
        String filename = ident + ".ser";
        ExpBooleana exp = (ExpBooleana)IO.cargar_objeto(path + filename);
        return exp;
    }

    /**
     * Elimina una expresion de persistencia del directorio ./bin/BooleanaSer/ con el nombre de archivo segun
     * el id proporcionado en el parametro ident
     * @param ident id de la expresion a eliminar
     */
    public void eliminar_expresion(Integer ident){
        String filename = ident + ".ser";

        IO.eliminar_objeto(path + filename);
    }

    /**
     * Elimina todas las expresiones de persistencia del directorio ./bin/BooleanaSer/ con fines de limpieza
     * durante el testing o si se quiere hacer un borrado masivo
     */
    public void clear_expresiones(){
        IO.clear_objetos(path);
    }

    /**
     * Carga todas todas las expresiones de persistencia del directorio ./bin/BooleanaSer/ con fin de restaurar
     * la persistencia una vez se carga la aplicacion en primer lugar
     * @return expresiones map que contiene todos los elementos necesarios para que las expresiones booleanas funcionen
     */
    public Pair<Map<String, ExpBooleana>,Pair<Queue<Integer>,Integer>> get_all() throws IOException, ClassNotFoundException {

        Map<String, ExpBooleana> expresiones = new HashMap<String, ExpBooleana>();

        Queue<Integer> disp = new PriorityQueue<Integer>();

        //cargo los nombres de los archivos del directorio
        File dir = new File(path);
        String[] filenames = dir.list();

        ArrayList<Integer> id_bools = new ArrayList<Integer>();

        //por cada file en el directorio la añado
        for(String filename: filenames){
            id_bools.add(Integer.parseInt(filename.split(".ser")[0]));

            ExpBooleana result = (ExpBooleana)IO.cargar_objeto(path + filename);

            expresiones.put(result.to_String(), result);
        }

        Collections.sort(id_bools);

        Iterator<Integer> iter
                = id_bools.iterator();

        Integer old_number = 0, current_number = 0, max_number = 1;

        //mientras queden por insertar ids fragmentados los meto en el queue
        while(iter.hasNext()) {
            current_number = iter.next();
            System.out.println("current number is " + current_number);
            while (!Objects.equals(old_number + 1, current_number)) {
                System.out.println("old number is " + old_number + " and we add " + (old_number+1));
                disp.add(old_number + 1);
                old_number = old_number + 1;
            }
            old_number = current_number;
            max_number = old_number;
        }
        max_number = current_number+1;
        System.out.println("max number is: " + max_number);
        disp.add(max_number);

        Pair<Map<String, ExpBooleana>,Pair<Queue<Integer>,Integer>> P = new Pair<>(expresiones,new Pair<>(disp,max_number));

        return P;
    }
}
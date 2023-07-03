package Domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Domain.Archivo;
import Domain.Corpus;
import Domain.Pair;
import Domain.ExpBooleana;
import Exceptions.*;
import Persistence.GestorArchivo;

public class ArchivoCtrl {
    /**
     * Map que contiene como clave la clave primaria de un archivo (su título y autor) y un pair con
     * el archivo en sí y un booleano según si este tiene su contenido en memoria o en disco
     */
    private Map<Pair<String, String>, Pair<Archivo, Boolean>> archivos;
    /**
     * Referencia a la clase corpus para servirse de sus funcionalidades y realizar búsquedas
     */
    private Corpus corpus;
    /**
     * Referencia a la clase CjtAutores para servirse de sus funcionalidades y realizar algunas queries
     */
    private CjtAutores autores;
    /**
     * Referencia a la clase GestorArchivo para servirse de sus funcionalidades y persistir los archivos
     */
    private GestorArchivo GA;

    /**
     * Función creadora básica que genera las relaciones de clase y persiste los elementos necesarios
     */
    public ArchivoCtrl() throws Exception{
        GA = new GestorArchivo();
        archivos = GA.get_all_ids();
        corpus = new Corpus();
        autores = CjtAutores.getINSTANCE();
    }

    /**
     * Se crea un archivo con los valores asignados y se añade al Set, además de persistirse de forma dinamica para
     * asegurar la consistencia siempre
     * @param titulo
     * @param autor
     * @param formato
     * @param content
     * @exception ArchivoYaExisteException ya existe el archivo
     * @exception FormatoInadecuadoException el formato de expresion no es adedcuado
     * @exception IOException error al leer o escribir
     * @exception ErrorInternoPersistenciaException error interno al guardar archivo
     */
    public void alta_archivo(String titulo, String autor, String formato, String content) throws ArchivoYaExisteException, FormatoInadecuadoException, IOException, ErrorInternoPersistenciaException, AtributoNuloException {
        if(archivo_valido(titulo, autor, formato, content)) {
            Pair<String, String> archivoID = new Pair<String, String>(titulo, autor);
            if (archivos.containsKey(new Pair<String, String>(titulo, autor)))
                throw new ArchivoYaExisteException(titulo, autor);
            Archivo archivo = new Archivo(titulo, autor, formato, content);
            archivos.put(archivoID, new Pair<Archivo, Boolean>(archivo, true));
            GA.guardar_archivo(archivo);
            corpus.anadir_archivo(titulo, autor, content);
        }
    }

    /**
     * El archivo con claves título y autor actualiza sus atributos con los parámetros correspondientes. Si el parametro
     * es null, no es necesario actualizar pues no se ha solicitado una modificación de ese atributo. Esta función no
     * provoca un cambio de formato
     * @param titulo_ant
     * @param autor_ant
     * @param titulo
     * @param autor
     * @param content
     * @exception ArchivoNoExisteException no existe el archivo
     * @exception ArchivoYaExisteException ya existe el archivo
     * @exception FormatoInadecuadoException el formato de expresion no es adedcuado
     * @exception IOException error al leer o escribir
     * @exception ErrorInternoPersistenciaException error interno al guardar archivo
     */
    public void editar_archivo(String titulo_ant, String autor_ant, String titulo, String autor, String content) throws ArchivoNoExisteException, ArchivoYaExisteException, IOException, FormatoInadecuadoException, ErrorInternoPersistenciaException, AtributoNuloException {
        if(archivo_valido(titulo, autor, "txt", content)) {
            if (!archivos.containsKey(new Pair<>(titulo_ant, autor_ant)))
                throw new ArchivoNoExisteException(titulo_ant, autor_ant);
            else if (archivos.containsKey(new Pair<>(titulo, autor)) && !archivo_igual(titulo_ant, autor_ant, titulo, autor))
                throw new ArchivoYaExisteException(titulo, autor);
            else {
                Archivo archivo = get_archivo(titulo_ant, autor_ant);
                Pair<String, String> p1 = new Pair<String, String>(titulo_ant, autor_ant);
                archivos.remove(p1);
                corpus.borrar_archivo(titulo_ant, autor_ant);
                GA.eliminar_archivo(archivo.consulta_titulo(), archivo.consulta_autor(), archivo.consulta_formato());

                archivo.modificar(titulo, autor, archivo.consulta_formato(), content);
                Pair<String, String> p2 = new Pair<String, String>(titulo, autor);
                archivos.put(p2, new Pair<Archivo, Boolean>(archivo, true));
                corpus.anadir_archivo(titulo, autor, content);
                GA.guardar_archivo(archivo);
            }
        }
    }

    /**
     * El archivo con claves título y autor actualiza sus atributos cambia su formato segun el formato proporcionado
     * @param titulo
     * @param autor
     * @param formato
     * @exception ArchivoNoExisteException no existe el archivo
     * @exception FormatoInadecuadoException el formato de expresion no es adedcuado
     * @exception IOException error al leer o escribir
     * @exception ErrorInternoPersistenciaException error interno al guardar archivo
     */
    public void cambiar_formato(String titulo, String autor, String formato) throws ArchivoNoExisteException, FormatoInadecuadoException, IOException, ErrorInternoPersistenciaException {
        Archivo archivo = get_archivo(titulo, autor);
        if(!archivos.get(new Pair<>(titulo, autor)).second()){
            archivo = GA.cargar_archivo(titulo, autor, archivo.consulta_formato());
        }
        GA.eliminar_archivo(archivo.consulta_titulo(), archivo.consulta_autor(), archivo.consulta_formato());
        archivo.modificar(titulo, autor, formato, archivo.consulta_contenido());
        Pair<String, String> id = new Pair<String, String>(titulo, autor);
        archivos.put(id, new Pair<Archivo, Boolean>(archivo, true));
        GA.guardar_archivo(archivo);
    }

    /**
     * El archivo con claves título y autor es quitado del set
     * @param titulo
     * @param autor
     * @exception ArchivoNoExisteException el archivo no existe
     * @exception IOException error al leer o escribir
     */
    public void borrar_archivo(String titulo, String autor) throws ArchivoNoExisteException, IOException {
        Archivo archivo = get_archivo(titulo, autor);
        Pair<String, String> p = new Pair<String, String>(titulo, autor);
        archivos.remove(p);
        corpus.borrar_archivo(titulo, autor);
        GA.eliminar_archivo(archivo.consulta_titulo(), archivo.consulta_autor(), archivo.consulta_formato());
    }

    /**
     * Consultora del formato de un archivo con claves primarias título y autor
     * @param titulo
     * @param autor
     * @return archivo.formato
     * @exception ArchivoNoExisteException el archivo no existe
     */
    public String consulta_formato_archivo(String titulo, String autor) throws ArchivoNoExisteException {
        Archivo archivo = get_archivo(titulo, autor);
        return archivo.consulta_formato();
    }

    /**
     * Consultora del contenido de un archivo con claves primarias título y autor
     * @param titulo
     * @param autor
     * @return archivo.contenido
     * @exception ArchivoNoExisteException no existe el archivo
     * @exception FileNotFoundException no se encontro el archivo
     * @exception IOException error al leer o escribir
     * @exception ErrorInternoPersistenciaException error interno al guardar archivo
     */
    public String consulta_contenido_archivo(String titulo, String autor) throws ArchivoNoExisteException, FormatoInadecuadoException, IOException, ErrorInternoPersistenciaException {
        Archivo archivo = get_archivo(titulo, autor);
        if(!archivos.get(new Pair<>(titulo, autor)).second()){
            archivo = GA.cargar_archivo(titulo, autor, archivo.consulta_formato());
        }
        return archivo.consulta_contenido();
    }

    /**
     * Consultora del contenido de un archivo con claves primarias título y autor
     * @param titulo
     * @param autor
     * @return archivo.contenido
     * @exception ArchivoNoExisteException no existe el archivo
     * @exception FileNotFoundException no se encontro el archivo
     */
    public String get_raw_archivo(String titulo, String autor) throws ArchivoNoExisteException, FileNotFoundException {
        Archivo archivo = get_archivo(titulo, autor);
        return GA.get_raw(titulo, autor, archivo.consulta_formato());
    }

    /**
     * Funcion que se encarga de importar el archivo del directorio path a nuestro sistema.
     *
     * @param path (lugar de origen del archivo a importar)
     * @exception FormatoInadecuadoException el formato no es bueno
     * @exception IOException error en entrada y salida
     * @exception ErrorInternoPersistenciaException error al guardar persistencia
     */
    public void importar_archivo(String path) throws FormatoInadecuadoException, IOException, ErrorInternoPersistenciaException, ArchivoYaExisteException {
        Archivo arc = GA.importar_archivo(path);
        archivos.put(new Pair<>(arc.consulta_titulo(), arc.consulta_autor()), new Pair<>(arc, true));
        corpus.anadir_archivo(arc.consulta_titulo(), arc.consulta_autor(), arc.consulta_contenido());
        autores.insertar_autor(arc.consulta_autor());
    }

    /**
     * Funcion que se encarga de exportar el archivo con clave primaria titulo y autor al directorio destino path.
     *
     * @param path (lugar de destino de la exportación)
     * @param titulo clave primaria conjunta de archivo a exportar
     * @param autor clave primaria conjunta de archivo a exportar
     * @exception FormatoInadecuadoException el formato no es bueno
     * @exception IOException error en entrada y salida
     * @exception ErrorInternoPersistenciaException error al guardar persistencia
     */
    public void exportar_archivo(String titulo, String autor, String path) throws FormatoInadecuadoException, IOException, ErrorInternoPersistenciaException {
        if(!archivos.get(new Pair<>(titulo, autor)).second()){
            Archivo arc = archivos.get(new Pair<>(titulo, autor)).first();
            GA.cargar_archivo(titulo, autor, arc.consulta_formato());
        }
        Archivo arc = archivos.get(new Pair<>(titulo, autor)).first();
        System.out.println("Exportar archivo PATH: " + path);
        GA.exportar_archivo(arc, path + "/");
    }

    /**
     * Se listan todos los archivos que tengan el parametro autor como autor
     * @param autor
     * @return returnList
     */
    public ArrayList<Pair<String, String>> listar_por_autor(String autor) {
        ArrayList<Archivo> auxList = new ArrayList<Archivo>();
        for(Map.Entry<Pair<String, String>, Pair<Archivo, Boolean>> entry : archivos.entrySet()){
            Archivo a = entry.getValue().first();
            if(a.consulta_autor().equals(autor)) auxList.add(a);
        }
        ArrayList<Pair<String, String>> returnList = build_return_list(auxList);
        return returnList;
    }

    /**
     * Se listan todos los archivos que contengan el parametro titulo en su titulo
     * @param titulo
     * @return returnList
     */
    public ArrayList<Pair<String, String>> listar_por_titulo(String titulo) {
        ArrayList<Archivo> auxList = new ArrayList<Archivo>();
        for(Map.Entry<Pair<String, String>, Pair<Archivo, Boolean>> entry : archivos.entrySet()) {
            Archivo a = entry.getValue().first();
            String tit = a.consulta_titulo();
            if (tit.contains(titulo)) auxList.add(a);
        }
        ArrayList<Pair<String, String>> returnList = build_return_list(auxList);
        return returnList;
    }

    /**
     * Lista n archivos parecidos al archivo identificado por el título y autor entrados.
     * @param titulo
     * @param autor
     * @param n
     * @param weightMode
     * @return returnList
     * @exception ArchivoNoExisteException El archivo no existe
     */
    public ArrayList<Pair<String, String>> listar_por_semejanza(String titulo, String autor, int n, int weightMode) throws ArchivoNoExisteException {
        Archivo archivo = get_archivo(titulo, autor);
        ArrayList<Archivo> auxList = new ArrayList<Archivo>();
        SortedMap<Double, Archivo> cosineMap = new TreeMap<Double, Archivo>(Collections.reverseOrder());
        for(Map.Entry<Pair<String, String>, Pair<Archivo, Boolean>> entry : archivos.entrySet()) {
            Archivo a = entry.getValue().first();
            if(!a.equals(archivo)){
                Double cosine = corpus.comparar_archivos(a.consulta_titulo(), a.consulta_autor(), archivo.consulta_titulo(), archivo.consulta_autor(), weightMode);
                if(cosine > 0.0) cosineMap.put(cosine, a);
            }
        }
        int i = 0;
        for(Map.Entry<Double, Archivo> entry : cosineMap.entrySet()){
            if(i < n) auxList.add(entry.getValue());
            else break;
            i++;
        }
        ArrayList<Pair<String, String>> returnList = build_return_list(auxList);
        return returnList;
    }

    /**
     * Lista k archivos cuyo contenido cumple con la query entrada
     * @param query
     * @param k
     * @param weightMode
     * @return returnList
     * @exception ArchivoNoExisteException El archivo no existe
     */
    public ArrayList<Pair<String, String>> listar_por_query(String query, int k, int weightMode) throws ArchivoNoExisteException {
        ArrayList<Archivo> auxList = new ArrayList<Archivo>();
        Map<Double, Archivo> cosineMap = new TreeMap<Double, Archivo>(Collections.reverseOrder());
        for(Map.Entry<Pair<String, String>, Pair<Archivo, Boolean>> entry : archivos.entrySet()) {
            Archivo a = entry.getValue().first();
            Double cosine = corpus.comparar_query(a.consulta_titulo(), a.consulta_autor(), query, weightMode);
            if(cosine > 0.0) cosineMap.put(cosine, a);
        }
        int i = 0;
        for(Map.Entry<Double, Archivo> entry : cosineMap.entrySet()){
            if(i < k) auxList.add(entry.getValue());
            else break;
            i++;
        }
        ArrayList<Pair<String, String>> returnList = build_return_list(auxList);
        return returnList;
    }

    /**
     * Lista los archivos que tienen una frase que cumple la expresión entrada
     * @param expresion
     * @return returnList
     */
    public ArrayList<Pair<String, String>> listar_por_bool(ExpBooleana expresion) throws FormatoInadecuadoException, IOException, ErrorInternoPersistenciaException {
        //System.out.println("DEBUG 1");
        ArrayList<Archivo> auxList = new ArrayList<Archivo>();
        for(Map.Entry<Pair<String, String>, Pair<Archivo, Boolean>> entry : archivos.entrySet()){
            Archivo a = entry.getValue().first();
            if(!entry.getValue().second()){
                a = GA.cargar_archivo(a.consulta_titulo(), a.consulta_autor(), a.consulta_formato());
            }
            String[] frases = a.consulta_contenido().split("[.:?!]+");
            for(String frase : frases){
                if(expresion.match(frase)){
                    auxList.add(a);
                    break;
                }
            }
        }
        ArrayList<Pair<String, String>> returnList = build_return_list(auxList);
        return returnList;
    }

    /**
     * Retorna un archivo con claves título y autor
     * @param titulo
     * @param autor
     * @return arch_ret
     * @exception ArchivoNoExisteException El archivo no existe
     */
    public Archivo get_archivo(String titulo, String autor) throws ArchivoNoExisteException {
        Pair<String, String> archivoID = new Pair<String, String>(titulo, autor);
        if(!archivos.containsKey(archivoID)) {
            throw new ArchivoNoExisteException(titulo, autor);
        }
        else {
            Archivo arch_ret = archivos.get(archivoID).first();
            return arch_ret;
        }
    }

    /**
     * Comprueba si el archivo con claves titulo y autor existe. Si existe retorna true, en caso contrario devuelve false
     * @param titulo
     * @param autor
     * @return existe
     */
    public boolean existe_archivo(String titulo, String autor) {
        boolean existe = false;
        Pair<String, String> archivoID = new Pair<String, String>(titulo, autor);
        if(archivos.containsKey(archivoID)) existe = true;
        return existe;
    }

    /**
     * Comprueba que el archivo identificado por la claves titulo y autor están registrados en el corpus
     * @param titulo
     * @param autor
     * @return existe
     */
    public boolean existe_archivo_corpus(String titulo, String autor) {
        boolean existe = corpus.existe_archivo(titulo, autor);
        return existe;
    }

    /**
     * Gestiona la ordenación y formatación de la lista de retorno
     * @param list
     * @return resultSet
     */
    private ArrayList<Pair<String, String>> build_return_list(ArrayList<Archivo> list){
        ArrayList<Pair<String, String>> resultSet = new ArrayList<>();
        for(Archivo aux: list){
            Pair<String, String> tituloAutor = new Pair<String, String>(aux.consulta_titulo(), aux.consulta_autor());
            resultSet.add(tituloAutor);
        }
        return resultSet;
    }

    private boolean archivo_igual(String titulo, String autor, String titulo2, String autor2){
        return titulo.equals(titulo2) && autor.equals(autor2);
    }

    private boolean archivo_valido(String titulo, String autor, String formato, String content) throws AtributoNuloException{
        if(titulo.equals("") || caracter_ilegal(titulo)) {
            throw new AtributoNuloException("titulo");
        }
        else if(autor.equals("") || caracter_ilegal(autor)){
            throw new AtributoNuloException("autor");
        }
        else if(formato.equals("")){
            throw new AtributoNuloException("formato");
        }
        else if(content.equals("")){
            throw new AtributoNuloException("contenido");
        }
        else{
            return true;
        }
    }

    private boolean caracter_ilegal(String s){
        String il = "<>:\"|?*\\/@";
        char[] ilegal = il.toCharArray();
        for(char c : ilegal) if(s.contains(c + "")) return true;
        return false;
    }
}
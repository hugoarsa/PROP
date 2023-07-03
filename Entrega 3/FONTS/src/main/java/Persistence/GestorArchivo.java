package Persistence;

import Domain.Archivo;
import Domain.ExpBooleana;
import Domain.Pair;

import java.util.*;
import java.io.*;

import Exceptions.ErrorInternoPersistenciaException;
import Exceptions.FormatoInadecuadoException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class GestorArchivo {

    /**
     * Directorio donde guardamos los archivos
     */
    private String path;
    /**
     * Referencia al gestor para leer y escribir de forma basica
     */
    GestorIO IO;

    /**
     * Referencia a los parsers de cada tipo para leer y escribir archivos
     */
    ParserTXT TXT;
    /**
     * Referencia a los parsers de cada tipo para leer y escribir archivos
     */
    ParserXML XML;
    /**
     * Referencia a los parsers de cada tipo para leer y escribir archivos
     */
    ParserPROP PROP;


    /**
     * Inicializa el gestor de persistencia con el path adecuado y crea los directorios necesarios para
     * el correcto funcionamiento de la persistencia
     */
    public GestorArchivo(){
        path = "./data/Archivos/";

        IO = new GestorIO();
        IO.create_dir(path);

        TXT = new ParserTXT();
        XML = new ParserXML();
        PROP = new ParserPROP();
    }

    /**
     * Guarda un archivo particular en el formato deseado en persistencia a partir del objeto
     * que se pasa como parametro en archivo
     * @param archivo Archivo a guardar en el elemento
     */
    public void guardar_archivo(Archivo archivo) throws FormatoInadecuadoException, IOException, ErrorInternoPersistenciaException {
        try {
            guardar_arch_pars(archivo, path);
        }
        catch (ParserConfigurationException  e){
            throw new ErrorInternoPersistenciaException(e.getMessage());
        }
        catch (TransformerException e){
            throw new ErrorInternoPersistenciaException(e.getMessage());
        }
    }

    /**
     * Guarda un archivo particular en el formato deseado en persistencia a partir del objeto
     * que se pasa como parametro en archivo
     * @param titulo Titulo del elemento a consultar
     * @param autor Autor del elemento a consultar
     */
    public Archivo cargar_archivo(String titulo, String autor,String formato) throws FormatoInadecuadoException, IOException, ErrorInternoPersistenciaException {
        try {
            Archivo a;
            String _path = path + titulo + "@" + autor + "." + formato;
            switch (formato) {
                case "txt":
                    a = TXT.cargar_archivo(_path);
                    break;
                case "xml":
                    a = XML.cargar_archivo(_path);
                    break;
                case "prop":
                    a = PROP.cargar_archivo(_path);
                    break;
                default:
                    throw new FormatoInadecuadoException(_path);
            }
            return a;
        }
        catch(ParserConfigurationException e){
            throw new ErrorInternoPersistenciaException(e.getMessage());
        }
        catch(SAXException e){
            throw new ErrorInternoPersistenciaException(e.getMessage());
        }
    }

    /**
     * Obtiene el contenido plano del archivo
     * @param titulo Titulo del elemento a consultar
     * @param autor Autor del elemento a consultar
     * @param formato Formato del elemento a consultar
     */
    public String get_raw(String titulo, String autor, String formato) throws FileNotFoundException {
        File file = new File(path + titulo + "@" + autor + "." + formato);
        Scanner sc = new Scanner(file);

        sc.useDelimiter("\\Z");
        String result = sc.next();
        sc.close();
        return result;
    }

    /**
     * Elimina el archivo con estos parametros
     * @param titulo Titulo del elemento
     * @param autor Autor del elemento
     * @param formato Formato del elemento
     */
    public void eliminar_archivo(String titulo, String autor,String formato) {
        String filename = titulo + "@" + autor + "." + formato;
        IO.eliminar_objeto(path + filename);
    }

    /**
     * Importamos el archivo del directorio path
     * @param path path desde el cual importaremos el archivo
     */
    public Archivo importar_archivo(String path) throws FormatoInadecuadoException, IOException, ErrorInternoPersistenciaException {
        try {
            int size = path.split("\\.").length;
            String formato = path.split("\\.")[size - 1];
            Archivo a;
            switch (formato) {
                case "txt":
                    a = TXT.cargar_archivo(path);
                    break;
                case "xml":
                    a = XML.cargar_archivo(path);
                    break;
                case "prop":
                    a = PROP.cargar_archivo(path);
                    break;
                default:
                    throw new FormatoInadecuadoException(path);
            }

            guardar_archivo(a);
            return a;
        }
        catch(ParserConfigurationException e){
            throw new ErrorInternoPersistenciaException(e.getMessage());
        }
        catch(SAXException e){
            throw new ErrorInternoPersistenciaException(e.getMessage());
        }
    }

    /**
     * Exportamos el archivo arc al directorio _path
     * @param arc archivo a exportar
     * @param _path path desde el cual importaremos el archivo
     */
    public void exportar_archivo(Archivo arc, String _path) throws FormatoInadecuadoException, IOException, ErrorInternoPersistenciaException {
        try {
            guardar_arch_pars(arc, _path);
        }
        catch(ParserConfigurationException e){
            throw new ErrorInternoPersistenciaException(e.getMessage());
        }
        catch(TransformerException e){
            throw new ErrorInternoPersistenciaException(e.getMessage());
        }
    }

    /**
     * Guarda un archivo particular en el formato deseado en persistencia a partir del objeto
     * que se pasa como parametro en archivo
     * @return archivos Map con la información de archivo sin el contenido
     */
    public Map<Pair<String, String>,Pair<Archivo, Boolean>> get_all_ids(){
        Map<Pair<String, String>,Pair<Archivo, Boolean>> archivos = new HashMap<Pair<String, String>,Pair<Archivo, Boolean>>();

        //cargo los nombres de los archivos del directorio
        File dir = new File(path);
        String[] filenames = dir.list();

        String current_title, current_author, formato, aux;

        //por cada file en el directorio la añado
        for(String filename: filenames){
            formato = filename.split("\\.")[1];
            aux = filename.split("\\.")[0];
            current_title = aux.split("@")[0];
            current_author = aux.split("@")[1];

            archivos.put(new Pair<>(current_title,current_author), new Pair<>(new Archivo(current_title,current_author,formato,""),false));
        }
        return archivos;
    }

    /**
     * Elimina todos los archivos del directorio
     */
    public void clear_archivos() throws Exception{
        IO.clear_objetos(path);
    }

    private void guardar_arch_pars(Archivo arc, String _path) throws IOException, ParserConfigurationException, TransformerException, FormatoInadecuadoException {
        String formato = arc.consulta_formato();
        switch (formato) {
            case "txt": TXT.guardar_archivo(arc, _path);
                break;
            case "xml": XML.guardar_archivo(arc, _path);
                break;
            case "prop": PROP.guardar_archivo(arc, _path);
                break;
            default: throw new FormatoInadecuadoException(_path);
        }
    }
}

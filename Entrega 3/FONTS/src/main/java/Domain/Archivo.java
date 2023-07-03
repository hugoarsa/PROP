package Domain;


import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.lang.String;
import Domain.Formato;
import Exceptions.AtributoNuloException;

public class Archivo {

    /**
     * Este es el título del archivo, el cual no puede tener caracteres ilegales pues formara parte del título
     */
    private String titulo;
    /**
     * Este es el autor del archivo, el cual no puede tener caracteres ilegales pues formara parte del título
     */
    private String autor;
    /**
     * Este es el formato del archivo, el cual puede ser .txt, .xml o el formato prop-ietario de nuestro programa .prop
     */
    private Formato formato;
    /**
     * Este es el contenido de dentro del archivo en question
     */
    private String contenido;

    /**
     * Se asignan lo valores de los atributos de archivo
     * @param titulo
     * @param autor
     * @param formato
     * @param content
     */
    public Archivo(String titulo, String autor, String formato, String content) {
            settitulo(titulo);
            setautor(autor);
            setformato(formato);
            setcontenido(content);
    }

    //MODIFICADORAS

    /**
     * El archivo actualiza sus atributos con los parámetros correspondientes. Si el parametro es null,
     * no es necesario actualizar pues no se ha solicitado una modificacion de ese atributo
     * @param titulo
     * @param autor
     * @param formato
     * @param content
     */
    public void modificar(String titulo, String autor, String formato, String content) {
        settitulo(titulo);
        setautor(autor);
        setformato(formato);
        setcontenido(content);
    }

    //CONSULTORAS
    /**
     * Devuelve el atributo del titulo
     * @return titulo
     */
    public String consulta_titulo() {
        return titulo;
    }

    /**
     * Devuelve el atributo del autor
     * @return autor
     */
    public String consulta_autor() {
        return autor;
    }

    /**
     * Devuelve el atributo del contenido
     * @return contenido
     */
    public String consulta_contenido() {
        return contenido;
    }

    /**
     * Devuelve el atributo de extension de formato
     * @return extension
     */
    public String consulta_formato() {
        if(formato == Formato.prop) return "prop";
        else if(formato == Formato.xml) return "xml";
        else return "txt";
    }

    /**
     * Comprueba si el formato entrado es valido
     * @param formato
     * @return valido
     */
    private Boolean formato_valido(String formato) {
        Boolean valido = true;
        valido = (formato ==  "prop" || formato ==  "txt" || formato ==  "xml");
        return valido;
    }

    //PRIVADAS
    /**
     * Se actualiza el contenido del archivo
     * @param content
     */
    private void setcontenido(String content) {
        contenido = content;
    }

    /**
     * Se actualiza el titulo del archivo
     * @param titulo
     */
    private void settitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Se actualiza el nombre del autor del archivo. Si se trata de una modificación se "elimina" el autor del conjunto.
     * @param autor
     */
    private void setautor(String autor) {
        this.autor = autor;
    }

    /**
     * La extension del archivo se actualiza con un formato valido entrado
     * @param formato
     */
    private void setformato(String formato) {
        if (formato.equals("prop")) this.formato = Formato.prop;
        else if( formato.equals("txt"))  this.formato = Formato.txt;
        else if(formato.equals("xml"))  this.formato = Formato.xml;
    }



    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof Archivo))
            return false;
        Archivo other = (Archivo)o;
        boolean tituloEquals = (this.titulo == null && other.titulo == null)
                || (this.titulo != null && this.titulo.equals(other.titulo));
        boolean autorEquals = (this.autor == null && other.autor == null)
                || (this.autor != null && this.autor.equals(other.autor));
        boolean formatoEquals = (this.formato == null && other.formato == null)
                || (this.formato != null && this.formato.equals(other.formato));
        boolean contenidoEquals = (this.contenido == null && other.contenido == null)
                || (this.contenido != null && this.contenido.equals(other.contenido));
        return tituloEquals && autorEquals && formatoEquals && contenidoEquals;
    }

    @Override
    public final int hashCode(){
        int result = 17;
        if(titulo != null){
            result = 31 * result + titulo.hashCode();
        }
        if(autor != null){
            result = 31 * result + autor.hashCode();
        }
        if(formato != null){
            result = 31 * result + formato.hashCode();
        }
        if(contenido != null){
            result = 31 * result + contenido.hashCode();
        }
        return result;
    }
}

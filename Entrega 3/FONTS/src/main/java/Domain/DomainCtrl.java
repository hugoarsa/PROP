package Domain;

import java.io.IOException;
import java.util.*;
import java.util.Set;
import java.util.HashSet;
import java.lang.Exception;
import Exceptions.*;


public class DomainCtrl {

    private static DomainCtrl INSTANCE;
    /**
     * Referencia a la clase ArchivoCtrl para gestionar conjuntos de archivos en el sistema.
     */
    private ArchivoCtrl archivoCtrl;
    /**
     * Referencia a la clase ExpBooleanaCtrl para gestionar conjuntos de expresiones booleanas en búsquedas.
     */
    private ExpBooleanaCtrl expresionCtrl;
    /**
     * Referencia a la clase UsuarioCtrl para gestionar conjuntos de usuarios en nuestra app.
     */
    private UsuarioCtrl usuarioCtrl;
    /**
     * Referencia a la clase CjtAutores para gestionar conjuntos de autores de archivos.
     */
    private CjtAutores autores;

    /**
     * Creadora vacía que inicializa los controladores y conjuntos.
     */
    private DomainCtrl() throws BackendException{
        try{
            archivoCtrl = new ArchivoCtrl();
            expresionCtrl = new ExpBooleanaCtrl();
            usuarioCtrl = new UsuarioCtrl();
            autores = CjtAutores.getINSTANCE();
        }
        catch(Exception e){
            tratar_excepciones(e);
        }
    }

    public static DomainCtrl getINSTANCE() throws BackendException {
        if(INSTANCE == null)
            INSTANCE = new DomainCtrl();
        return INSTANCE;
    }

    /**
     * Consultora encargada de devolver el nombre de un usuario de username el parámetro.
     * @param username username del que extraer el nombre
     * @return nombre
     */
    public String consulta_nombre_usuario(String username) throws BackendException{
        try {
            String nombre = usuarioCtrl.consulta_nombre_usuario(username);
            return nombre;
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
        return null;
    }

    // PERFIL

    /**
     * Comprueba si el username existe y la contraseña corresponde a éste.
     *
     * @param username username para comprobar
     * @param contrasena contraseña a comprobar
     * @return verificado
     */
    public Boolean verificar_perfil(String username, String contrasena) throws BackendException{
        try {
            Boolean verificado = usuarioCtrl.verificar_perfil(username, contrasena);
            return verificado;
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
        return false;
    }

    /**
     * Crea un nuevo usuario con username, contraseña y nombre asignados.
     *
     * @param username usuario del perfil a crear
     * @param contrasena1 contraseña inicial
     * @param contrasena2 : contraseña de validación (repetición de contrasena1)
     * @param nombre nombre de pila del usuario
     */
    public boolean crear_perfil(String username, String contrasena1, String contrasena2, String nombre) throws BackendException{
        try {
            return usuarioCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
        return false;
    }

    /**
     * Modifica un perfil identificado por 'username_ant'.
     *
     * @param username_ant nombre de usuario antiguo
     * @param username nuevo nombre de usuario
     * @param nombre nuevo nombre de pila
     */
    public void modificar_perfil(String username_ant, String username, String nombre) throws BackendException{
        try {
            usuarioCtrl.modificar_perfil(username_ant, username, nombre);
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
    }

    /**
     * Canvia la contraseña de un perfil identificado por 'username'.
     *
     * @param username
     * @param contrasena
     * @param nueva_contrasena
     */
    public void cambiar_contrasena(String username, String contrasena, String nueva_contrasena) throws BackendException{
        try {
            usuarioCtrl.cambiar_contrasena(username, contrasena, nueva_contrasena);
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
    }

    /**
     * Elimina el usuario identificado por 'username'.
     *
     * @param username
     */
    public boolean borrar_perfil(String username, String contrasena) throws BackendException{
        try {
            if(usuarioCtrl.verificar_perfil(username, contrasena)) {
                usuarioCtrl.borrar_perfil(username);
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
        return false;
    }

    /**
     * Crea un nuevo archivo con sus atributos correspondientes no nulos. Añade a la lista de autores el nuevo autor
     *
     * @param titulo
     * @param autor
     * @param formato
     * @param contenido
     */
    public void alta_archivo(String titulo, String autor, String formato, String contenido) throws BackendException{
        try {
            archivoCtrl.alta_archivo(titulo, autor, formato, contenido);
            autores.insertar_autor(autor);
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
    }

    /**
     * Edita el archivo seleccionado según la nueva información introducida.
     * Si el título se modifica, se modifica el conjunto de autores y su información correspondiente.
     * Si el autor se modifica, se elimina del conjunto de autores la información del archivo del autor antiguo y se
     * añade ésta junto con el nuevo autor.
     * No se puede cambiar el formato en este caso.
     *
     * @param titulo_ant clave primaria conjunta de archivo anterior
     * @param autor_ant clave primaria conjunta de archivo anterior
     * @param titulo clave primaria conjunta de archivo nueva
     * @param autor clave primaria conjunta de archivo nueva
     * @param contenido nuevo contenido
     */
    public void editar_archivo(String titulo_ant, String autor_ant, String titulo, String autor, String contenido) throws BackendException{
        try {
            archivoCtrl.editar_archivo(titulo_ant, autor_ant, titulo, autor, contenido);
            if (!autor_ant.equals(autor)) {
                autores.eliminar_autor(autor_ant);
                autores.insertar_autor(autor);
            }
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
    }

    /**
     * Cambia el formato del archivo con titulo y autor como los parametros proporcionados.
     *
     * @param titulo
     * @param autor
     * @param formato
     */
    public void cambiar_formato_archivo(String titulo, String autor, String formato) throws BackendException{
        try {
            archivoCtrl.cambiar_formato( titulo, autor, formato);
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
    }

    /**
     * Borra el archivo seleccionado y comprueba si aún hay algún archivo que contenga el mismo nombre de autor que
     * el recién eliminado. Si ya no consta en ningún archivo entonces se elimina del listado de autores.
     *
     * @param titulo clave primaria conjunta de archivo
     * @param autor clave primaria conjunta de archivo
     */
    public void borrar_archivo(String titulo, String autor) throws BackendException{
        try {
            archivoCtrl.borrar_archivo(titulo, autor);
            autores.eliminar_autor(autor);
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }

    }

    /**
     * Consulta el formato del archivo identificado por el título y autor indicado
     *
     * @param titulo clave primaria conjunta de archivo
     * @param autor clave primaria conjunta de archivo
     * @return formato
     */
    public String consulta_formato_archivo(String titulo, String autor) throws BackendException{
        try {
            String formato = archivoCtrl.consulta_formato_archivo(titulo, autor);
            return formato;
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
        return null;
    }

    /**
     * Consulta el contenido del archivo identificado por el título y autor indicado
     *
     * @param titulo clave primaria conjunta de archivo
     * @param autor clave primaria conjunta de archivo
     * @return contenido
     */
    public String consulta_contenido_archivo(String titulo, String autor) throws BackendException{
        try {
            String contenido = archivoCtrl.consulta_contenido_archivo(titulo, autor);
            return contenido;
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
        return null;
    }

    /**
     * Funcion que permite obtener el contenido raw de un archivo en caracteres.
     *
     * @param titulo clave primaria conjunta de archivo
     * @param autor clave primaria conjunta de archivo
     * @return raw
     */
    public String get_raw_archivo(String titulo, String autor) throws BackendException{
        try {
            String raw = archivoCtrl.get_raw_archivo(titulo, autor);
            return raw;
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
        return null;
    }

    /**
     * Funcion que se encarga de importar el archivo del directorio path a nuestro sistema.
     *
     * @param path (lugar de origen del archivo a importar)
     */
    public void importar_archivo(String path) throws BackendException{
        try {
            archivoCtrl.importar_archivo(path);
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
    }

    /**
     * Funcion que se encarga de exportar el archivo con clave primaria titulo y autor al directorio destino path.
     *
     * @param path (lugar de destino de la exportación)
     * @param titulo clave primaria conjunta de archivo a exportar
     * @param autor clave primaria conjunta de archivo a exportar
     */
    public void exportar_archivo(String titulo, String autor, String path) throws BackendException{
        try {
            archivoCtrl.exportar_archivo(titulo, autor, path);
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
    }

    /**
     * Consulta el listado de todos los autores con el prefijo indicado existentes en el sistema
     *
     * @param prefijo prefijo del autor con el que filtramos
     * @return lista_autores
     */
    public Set<String> listar_autores(String prefijo) {
        Set<String> lista_autores = autores.listar_autores(prefijo);
        return lista_autores;
    }

    /**
     * Consulta el listado de todos los archivos cuyo autor corresponda a: 'autor'
     *
     * @param autor
     * @return listado
     */
    public ArrayList<Pair<String, String>> listar_por_autor(String autor) {
        ArrayList<Pair<String, String>> listado = archivoCtrl.listar_por_autor(autor);
        return listado;
    }

    /**
     * Consulta el listado de todos los archivos cuyo título corresponda a 'infijo'
     *
     * @param infijo
     * @return listado
     */
    public ArrayList<Pair<String, String>> listar_por_titulo(String infijo) {
        ArrayList<Pair<String, String>> listado = archivoCtrl.listar_por_titulo(infijo);
        return listado;
    }

    /**
     * Consulta el listado de x archivos similares en contenido al archivo identificado por 'titulo' y 'autor'
     *
     * @param titulo
     * @param autor
     * @param n (número de archivos a listar por semejanza)
     * @param weightMode
     * @return listado
     */
    public ArrayList<Pair<String, String>> listar_por_semejanza(String titulo, String autor, int n, int weightMode) throws BackendException{
        try {
            ArrayList<Pair<String, String>> listado =  archivoCtrl.listar_por_semejanza(titulo, autor, n, weightMode);
            return listado;
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
        return null;
    }

    /**
     * Consulta el listado de k archivos cuyo contenido cumple con la misma query que la entrada.
     *
     * @param query
     * @param k
     * @param weightMode
     * @return listado
     */
    public ArrayList<Pair<String, String>> listar_por_query(String query, int k, int weightMode) throws BackendException{
        try {
            ArrayList<Pair<String, String>> listado = archivoCtrl.listar_por_query(query, k, weightMode);
            return listado;
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
        return null;
    }

    /**
     * Consulta el listado de todos los archivos que cumplan con la expresión booleana indicada. Si la expresión
     * no existía previamente, se crea.
     *
     * @param expresion
     * @return listado
     */
    public ArrayList<Pair<String, String>> listar_por_bool(String expresion) throws BackendException{
        try {
            System.out.println("Expresion: " + expresion);
            expresionCtrl.alta_expresion(expresion);
            ArrayList<Pair<String, String>> listado = archivoCtrl.listar_por_bool(expresionCtrl.consultar_expresion(expresion));
            return listado;
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
        return null;
    }

    /**
     * Retorna un archivo con claves titulo y autor
     * @param titulo
     * @param autor
     * @return archivo
     */
    public Archivo get_archivo(String titulo, String autor) throws BackendException{
        try {
            Archivo archivo = archivoCtrl.get_archivo(titulo, autor);
            return archivo;
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
        return null;
    }

    /**
     *  Se comprueba que exista el archivo con claves titulo y autor en ArchivoCtrl.
     * @param titulo
     * @param autor
     * @return existe
     */
    public boolean existe_archivo(String titulo, String autor) {
        boolean existe = archivoCtrl.existe_archivo(titulo, autor);
        return existe;
    }

    /**
     *  Se comprueba que exista el archivo con claves titulo y autor en el corpus.
     * @param titulo
     * @param autor
     * @return existe
     */
    public boolean existe_archivo_corpus(String titulo, String autor) {
        boolean existe = archivoCtrl.existe_archivo_corpus(titulo, autor);
        return existe;
    }

    /**
     * Crea y guarda una expresión booleana 'expresion'
     *
     * @param expresion
     */
    public void alta_expresion(String expresion) throws BackendException{
        try{
            expresionCtrl.alta_expresion(expresion);
        }
        catch (Exception e) {
            tratar_excepciones(e);

        }
    }

    /**
     * Sustituye la expresion booleana 'expresion_ant' por una nueva
     *
     * @param expresion_ant
     * @param expresion
     */
    public void modificar_expresion(String expresion_ant, String expresion) throws BackendException{
        try {
            expresionCtrl.borrar_expresion(expresion_ant);
            expresionCtrl.alta_expresion(expresion);
        }
        catch (Exception e) {
            tratar_excepciones(e);

        }
    }

    /**
     * Borra la expresión booleana seleccionada
     *
     * @param expresion
     */
    public void borrar_expresion(String expresion) throws BackendException{
        try{
            expresionCtrl.borrar_expresion(expresion);
        }
        catch (Exception e) {
            tratar_excepciones(e);
        }
    }

    /**
     * Consulta la lista de todas las expresiones booleanas existentes en el sistema
     *
     * @return listado_expresiones
     */
    public Set<String> listar_expresiones() {
        Set<String> listado_expresiones = expresionCtrl.listar_expresiones();
        return listado_expresiones;
    }

    public void clearCache() throws BackendException{
        try {
            autores.clearCache();
            expresionCtrl = new ExpBooleanaCtrl();
            usuarioCtrl = new UsuarioCtrl();
            archivoCtrl = new ArchivoCtrl();
        }
        catch (Exception e){
            tratar_excepciones(e);
        }
    }

    private void tratar_excepciones(Exception e) throws BackendException{
        System.out.println(" ***: getMessage(): " + e.getMessage());
        System.out.println(" ***: toString(): " + e.toString());
        e.printStackTrace();
        if(e instanceof ArchivoNoExisteException){
            throw new BackendException("101", e.getMessage());
        }
        else if(e instanceof ArchivoYaExisteException){
            throw new BackendException("102", e.getMessage());
        }
        else if(e instanceof AutorNoExisteException){
            throw new BackendException("401", e.getMessage());
        }
        else if(e instanceof CaracterIlegalException){
            throw new BackendException("202", e.getMessage());
        }
        else if(e instanceof ComillasException){
            throw new BackendException("205", e.getMessage());
        }
        else if(e instanceof ContrasenaNoValidaExcepcion){
            throw new BackendException("303", e.getMessage());
        }
        else if(e instanceof ErrorInternoPersistenciaException){
            throw new BackendException("502", e.getMessage());
        }
        else if(e instanceof FormatoInadecuadoException){
            throw new BackendException("501", e.getMessage());
        }
        else if(e instanceof MalParentesisException){
            throw new BackendException("203", e.getMessage());
        }
        else if(e instanceof MalUsoOperandosException){
            throw new BackendException("204", e.getMessage());
        }
        else if(e instanceof NoExisteExpresionException){
            throw new BackendException("201", e.getMessage());
        }
        else if(e instanceof UsernameNoValidoExcepcion){
            throw new BackendException("302", e.getMessage());
        }
        else if(e instanceof UsuarioNoExisteException){
            throw new BackendException("301", e.getMessage());
        }
        else if(e instanceof ContrasenaVaciaException){
            throw new BackendException("304", e.getMessage());
        }
        else if(e instanceof IOException){
            throw new BackendException("601", e.getMessage());
        }
        else if(e instanceof ClassNotFoundException){
            throw new BackendException("602", e.getMessage());
        }
        else if(e instanceof AtributoNuloException){
            throw new BackendException("103", e.getMessage());
        }
        else{
            throw new BackendException("701", "Error Desconocido");
        }
    }
}
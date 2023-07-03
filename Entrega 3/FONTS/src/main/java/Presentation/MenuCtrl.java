package Presentation;

import java.util.ArrayList;
import java.util.Set;
import Domain.Pair;
import Exceptions.BackendException;

public class MenuCtrl {
    public static MenuCtrl INSTANCE = new MenuCtrl();
    
    private VistaMenu vistaMenu;
    private VistaEditor vistaEditor;
    private VistaVisualizador vistaVisualizador;
    private VistaPerfil vistaPerfil;
    private VistaExpBooleana vistaExpBooleana;
    private VistaAutores vistaAutores;
    private VistaQuery vistaQuery;
    private VistaComparador vistaComparador;
    private VistaCrearArchivo vistaCrearArchivo;

    private String user;

    private MenuCtrl() {
        user = "";
        vistaPerfil = new VistaPerfil();
    }

    /**
     * Crea una vistaMenu si todavía no se ha creado una. Abre el menu.
     */
    public void abrirMenu() {
        if(vistaMenu == null) vistaMenu = new VistaMenu();
        vistaMenu.hacerVisible();
    }

    /**
     * Guarda en el sistema el nombre del usuario que ha iniciado sesión en el sistema.
     * @param username
     */
    public void setUser(String username){
        user = username;
    }

    /**
     * Lista todas las expresiones existentes en el sistema.
     * @return resultado
     */
    public Set<String> listarExpresiones() {
        Set<String> resultado = PresentationCtrl.INSTANCE.listarExpresiones();
        return resultado;
    }

    /**
     * Consulta el contenido del archivo identificado por los parámetros 'titulo' y 'autor'.
     * @param titulo
     * @param autor
     * @return content
     */
    public String consultaArchivo(String titulo, String autor) {
        String content = PresentationCtrl.INSTANCE.consultaArchivo(titulo, autor);
        return content;
    }

    /**
     * Consulta el raw del archivo identificado por los parámetros 'titulo' y 'autor'.
     * @param titulo
     * @param autor
     * @return raw
     */
    public String get_raw(String titulo, String autor) {
        String raw = PresentationCtrl.INSTANCE.get_raw(titulo, autor);
        return raw;
    }

    /**
     * Crea un nuevo archivo identificado por 'titulo' y 'autor'.
     * @param titulo
     * @param autor
     * @param formato
     * @param contenido
     */
    public void altaArchivo(String titulo, String autor, String formato, String contenido) {
        try {
            PresentationCtrl.INSTANCE.altaArchivo(titulo,autor,formato, contenido);
        }
        catch (BackendException e) {
            if(e.getMessage().equals("102")) {
                tratarConflictoArc(titulo, autor, titulo, autor, contenido, 0);
            }
            else{
                VistaDialogo.INSTANCE.mensajeError(e.toString());
            }
        }
    }

    /**
     * Elimina el archivo identificado por los parámetros 'titulo' y 'autor'.
     * @param titulo
     * @param autor
     */
    public void borrarArchivo(String titulo, String autor) {
        PresentationCtrl.INSTANCE.borrarArchivo(titulo, autor);
    }

    /**
     * Modifica el archivo identificado por los parámetros 'titulo' y 'autor' con los nuevos parámetros (tituloIn, autorIn, contenidoIn).
     * @param titulo
     * @param autor
     * @param tituloIn
     * @param autorIn
     * @param contenidoIn
     */
    public void editarArchivo(String titulo, String autor, String tituloIn, String autorIn, String contenidoIn) {
        try {
            PresentationCtrl.INSTANCE.editarArchivo(titulo, autor, tituloIn, autorIn, contenidoIn);
        }
        catch (BackendException e) {
            if(e.getMessage().equals("102")) {
                tratarConflictoArc(titulo, autor, tituloIn, autorIn, contenidoIn, 1);
            }
            else{
                VistaDialogo.INSTANCE.mensajeError(e.toString());
            }
        }
    }

    /**
     * Modifica los datos (nombre de usuario y/o nombre) del usuario identificado por 'username' con los nuevos valores introducidos.
     * @param username
     * @param nombre
     * @return modificado (true: sí, false: no)
     */
    public Boolean modificarPerfil(String username, String nombre) {
        String user_ant = user;
        try {
            PresentationCtrl.INSTANCE.modificarPerfil(user, username, nombre);
            setUser(username);
            return true;
        }
        catch(BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
            setUser(user_ant);
            return false;
        }
    }

    /**
     * Elimina el usuario identificado por el nombre de usuario 'username'.
     * @param username
     * @param contrasena
     * @return borrado (true: sí, false: no)
     */
    public Boolean borrarPerfil(String username, String contrasena) {
        return PresentationCtrl.INSTANCE.borrarPerfil(username, contrasena);
    }

    /**
     * Da de alta una nueva expresión booleana.
     * @param expresion
     */
    public void anadirExpresion(String expresion) {
        PresentationCtrl.INSTANCE.anadirExpresion(expresion);
    }

    /**
     * Cambia la contraseña del usuario identificado por 'username' por la nueva entrada.
     * @param username
     * @param contrasena
     */
    public void cambiarContrasena(String username, String contrasena, String new_contra){
        PresentationCtrl.INSTANCE.cambiarContrasena(username, contrasena, new_contra);
    }

    /**
     * Borra la expresión booleana 'expresion'.
     * @param expresion
     */
    public void borrarExpresion(String expresion) {
        PresentationCtrl.INSTANCE.borrarExpresion(expresion);
    }

    /**
     * Consulta el listado de 'num' archivos cuyo contenido cumple con la misma query que la entrada.
     * @param query
     * @param num
     * @param wMode
     * @return listado
     */
    public ArrayList<Pair<String, String>> listarPorQuery(String query, Integer num, Integer wMode) {
        ArrayList<Pair<String, String>> listado = PresentationCtrl.INSTANCE.listarPorQuery(query, num, wMode);
        return listado;
    }

    /**
     * Consulta el listado de 'num' archivos similares en contenido al archivo identificado por 'titulo' y 'autor'.
     * @param titulo
     * @param autor
     * @param num (número de archivos a listar por semejanza)
     * @param wMode
     * @return listado
     */
    public ArrayList<Pair<String, String>> listarPorSemejanza(String titulo, String autor, Integer num, Integer wMode) {
        ArrayList<Pair<String, String>> listado = PresentationCtrl.INSTANCE.listarPorSemejanza(titulo, autor, num, wMode);
        return listado;
    }

    /**
     * Consulta el listado de todos los archivos que cumplan con la expresión booleana indicada.
     * Si la expresión no existía previamente, se crea.
     * @param expresion
     * @return listado
     */
    public ArrayList<Pair<String,String>> listarPorBool(String expresion) {
        ArrayList<Pair<String,String>> listado = PresentationCtrl.INSTANCE.listarPorBool(expresion);
        return listado;
    }

    /**
     * Abre el menú con los resultados de la búsqueda por la expresion booleana 'expresion'
     * @param expresion
     */
    public void abrirMenuBool(String expresion) {
        vistaMenu.hacerVisible();
        vistaMenu.abrirMenuBool(expresion);
    }

    /**
     * Consulta el listado de todos los autores existentes en el sistema que contienen el prefijo indicado.
     * @param prefijo
     * @return listado
     */
    public Set<String> listarAutores(String prefijo) {
        Set<String> listado = PresentationCtrl.INSTANCE.listarAutores(prefijo);
        return listado;
    }

    /**
     * Crea una VistaEditor si todavía no se ha creado una. Abre el editor de archivo con los datos correspondientes a éste.
     * @param titulo
     * @param autor
     */
    public void abrirEditor(String titulo, String autor) {
        if (vistaEditor == null) vistaEditor = new VistaEditor();
        vistaEditor.setArchivo(titulo, autor);
        vistaEditor.hacerVisible();
    }

    /**
     * Crea una VistaAutores si todavía no se ha creado una. Abre el buscador de autores.
     */
    public void abrirAutores() {
        if(vistaAutores == null) vistaAutores = new VistaAutores();
        vistaAutores.hacerVisible();
    }

    /**
     * Crea una VistaVisualizador si todavía no se ha creado una. Abre el visualizador de archivos con los datos del
     * archivo identificado por 'titulo' y 'autor'.
     * @param titulo
     * @param autor
     */
    public void abrirVisualizador(String titulo, String autor) {
        if(vistaVisualizador == null) vistaVisualizador = new VistaVisualizador();
        vistaVisualizador.setArchivo(titulo, autor);
        vistaVisualizador.hacerVisible();
    }

    /**
     * Crea una VistaQuery si todavía no se ha creado una. Abre el buscador de archivos por query.
     */
    public void abrirVistaQuery() {
        if (vistaQuery == null) vistaQuery = new VistaQuery();
        vistaQuery.hacerVisible();
    }

    /**
     * Crea una VistaComparador si todavía no se ha creado una. Abre el buscador de archivos por semejanza.
     */
    public void abrirComparador() {
        if (vistaComparador == null) vistaComparador = new VistaComparador();
        vistaComparador.hacerVisible();
    }

    /**
     * Crea una VistaGestorExpresion si todavía no se ha creado una. Abre la vista de expresiones booleanas.
     */
    public void abrirGestorExpresion() {
        if(vistaExpBooleana == null) vistaExpBooleana = new VistaExpBooleana();
        vistaExpBooleana.hacerVisible();
    }

    /**
     * Crea una VistaPerfil si todavía no se ha creado una. Abre el visualizador de datos del perfil identificado por
     * el nombre de usuario 'user'.
     */
    public void abrirPerfil() {
        vistaPerfil.setUsuario(user,PresentationCtrl.INSTANCE.consultarNombre(user));
        vistaPerfil.hacerVisible();
    }

    /**
     * Crea una VistaCrearArchivo si todavía no se ha creado una. Abre un editor de archivos nuevos.
     */
    public void abrirCreadorArchivo() {
        if(vistaCrearArchivo == null) vistaCrearArchivo = new VistaCrearArchivo();
        vistaCrearArchivo.hacerVisible();
    }

    /**
     * Cambia el formato del archivo seleccionado.
     * @param titulo
     * @param autor
     * @param formato
     */
    public void cambiarFormato(String titulo, String autor, String formato) {
        PresentationCtrl.INSTANCE.cambiarFormato(titulo,autor, formato);
    }

    /**
     * Exporta el archivo seleccionado en el sistema al directorio del equipo correspondiente al 'path'.
     * @param path
     * @param titulo
     * @param autor
     */
    public void exportarArchivo(String titulo, String autor, String path) {
        PresentationCtrl.INSTANCE.exportarArchivo(titulo, autor, path);
    }

    /**
     * Importa el archivo seleccionado en el sistema al equipo.
     * @param path
     */
    public void importarArchivo(String path) {
        PresentationCtrl.INSTANCE.importarArchivo(path);
    }

    /**
     * Consulta el listado de todos los archivos cuyo título corresponda a 'infijo' y por el orden indicado.
     * @param infijo
     * @return listado de archivos
     */
    public ArrayList<Pair<String,String>> listarPorTitulo(String infijo){
        return PresentationCtrl.INSTANCE.listarPorTitulo(infijo);
    }

    /**
     * Consulta el listado de todos los archivos cuyo autor contenga 'prefijo' y por el orden indicado.
     * @param prefijo
     * @return listado de archivos
     */
    public ArrayList<Pair<String,String>> listarPorAutor(String prefijo) {
        return PresentationCtrl.INSTANCE.listarPorAutores(prefijo);
    }

    /**
     * Cierra la sesión del usuario y retorna a la vista principal.
     */
    public void cerrar() {
        user = "";
        PresentationCtrl.INSTANCE.abrirVistaPrincipal();
    }

    /**
     * Trata los conflictos entre archivos que pueden producirse durante la creación o edición de archivos
     * @param titulo
     * @param autor
     * @param tituloIn
     * @param autorIn
     * @param contenidoIn
     * @param modo Indica en qué contexto se ha producido el conflicto (0: crear archivo, 1: editar archivo)
     */
    private void tratarConflictoArc(String titulo, String autor, String tituloIn, String autorIn, String contenidoIn, int modo) {
        Integer opcion = VistaDialogo.INSTANCE.excConflictoArc();
        if(opcion == 0) {
            try {
                if(modo == 1) PresentationCtrl.INSTANCE.borrarArchivo(titulo, autor);
                PresentationCtrl.INSTANCE.editarArchivo(tituloIn, autorIn, tituloIn, autorIn, contenidoIn);
            }
            catch (BackendException e){
                VistaDialogo.INSTANCE.mensajeError(e.toString());
            }
        }
        else {
            vistaEditor.setArchivo(titulo, autor);
        }
    }

}
package Presentation;

import Domain.DomainCtrl;

import java.util.ArrayList;
import java.util.Set;
import Domain.Pair;
import Exceptions.BackendException;

public class PresentationCtrl {
    public static PresentationCtrl INSTANCE = new PresentationCtrl();
    private VistaPrincipal vistaPrincipal = new VistaPrincipal();
    private VistaRegistro vistaRegistro = new VistaRegistro();
    private DomainCtrl domain;

    /**
     * Constructora.
     */
    private PresentationCtrl() {
            try {
                domain = DomainCtrl.getINSTANCE();
            }
            catch (BackendException e) {
                VistaDialogo.INSTANCE.mensajeError(e.toString());
            }
    }

    /**
     * Abre la vista de inicio de sesión.
     */
    public void abrirVistaPrincipal() {
        vistaPrincipal.hacerVisible();
    }

    /**
     * Elimina el archivo identificado por los parámetros 'titulo' y 'autor'.
     * @param titulo
     * @param autor
     */
    public void borrarArchivo(String titulo, String autor) {
        try {
            domain.borrar_archivo(titulo, autor);
        }
        catch (BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
    }

    /**
     * Consulta el listado de todos los archivos cuyo título corresponda a 'infijo'.
     * @param infijo
     * @return listado
     */
    public ArrayList<Pair<String, String>> listarPorTitulo(String infijo) {
        ArrayList<Pair<String, String>> listado = domain.listar_por_titulo(infijo);
        return listado;
    }

    /**
     * Consulta el listado de todos los archivos cuyo autor contenga 'prefijo'.
     * @param prefijo
     * @return listado
     */
    public ArrayList<Pair<String, String>> listarPorAutores(String prefijo) {
        ArrayList<Pair<String,String>> listado = new ArrayList<Pair<String,String>>();
        listado.addAll(domain.listar_por_autor(prefijo));
        return listado;
    }

    /**
     * Lista todas las expresiones existentes en el sistema
     * @return listado
     */
    public Set<String> listarExpresiones() {
        Set<String> listado = domain.listar_expresiones();
        return listado;
    }

    /**
     * Consulta el contenido del archivo identificado por los parámetros 'titulo' y 'autor'.
     * @param titulo
     * @param autor
     * @return content
     */
    public String consultaArchivo(String titulo, String autor) {
        try {
            String content = domain.consulta_contenido_archivo(titulo, autor);
            return content;
        }
        catch (BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
        return "";
    }

    /**
     * Consulta el raw del archivo identificado por los parámetros 'titulo' y 'autor'.
     * @param titulo
     * @param autor
     * @return raw
     */
    public String get_raw(String titulo, String autor) {
        try {
            String raw = domain.get_raw_archivo(titulo, autor);
            return raw;
        }
        catch (BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
        return "";
    }

    /**
     * Consulta el listado de todos los autores existentes en el sistema con el prefijo.
     * @param prefijo
     * @return listado
     */
    public Set<String> listarAutores(String prefijo) {
        Set<String> listado = domain.listar_autores(prefijo);
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
    public ArrayList<Pair<String,String>> listarPorSemejanza(String titulo, String autor, Integer num, Integer wMode) {
        try {
            ArrayList<Pair<String,String>> listado = domain.listar_por_semejanza(titulo, autor, num, wMode);
            return listado;
        }
        catch (BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
        return new ArrayList<Pair<String,String>>();
    }

    /**
     * Consulta el listado de 'num' archivos cuyo contenido cumple con la misma query que la entrada.
     * @param query
     * @param num
     * @param wMode
     * @return listado
     */
    public ArrayList<Pair<String,String>> listarPorQuery(String query, Integer num, Integer wMode) {
        try {
            ArrayList<Pair<String,String>> listado = domain.listar_por_query(query, num, wMode);
            return listado;
        }
        catch(BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
       return new ArrayList<Pair<String,String>>();
    }

    /**
     * Consulta el listado de todos los archivos que cumplan con la expresión booleana indicada. Si la expresión
     * no existía previamente, se crea.
     * @param expresion
     * @return listado
     */
    public ArrayList<Pair<String,String>> listarPorBool(String expresion) {
        try {
            ArrayList<Pair<String,String>> listado = domain.listar_por_bool(expresion);
            return listado;
        }
        catch(BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
        return new ArrayList<Pair<String,String>>();
    }

    /**
     * Borra la expresión booleana 'expresion'.
     * @param expresion
     */
    public void borrarExpresion(String expresion) {
        try {
            domain.borrar_expresion(expresion);
        }
        catch(BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
    }

    /**
     * Da de alta una nueva expresión booleana.
     * @param expresion
     */
    public void anadirExpresion(String expresion) {
        try {
            domain.alta_expresion(expresion);
        }
        catch(BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
    }

    /**
     * Cambia la contraseña del usuario identificado por 'username' por la nueva entrada.
     * @param username
     * @param contrasena
     */
    public void cambiarContrasena(String username, String contrasena, String new_contra) {
        try {
            domain.cambiar_contrasena(username, contrasena, new_contra);
        }
        catch(BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
    }

    /**
     * Elimina el usuario identificado por el nombre de usuario 'username'. Comprueba que la contraseña entrada
     * corresponda a la del usuario a borrar.
     * @param username
     * @param contrasena
     */
    public Boolean borrarPerfil(String username, String contrasena) {
        try {
            return domain.borrar_perfil(username, contrasena);
        }
        catch(BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
        return false;
    }

    /**
     * Modifica los datos (nombre de usuario y/o nombre) del usuario identificado por 'username' con los nuevos valores introducidos.
     * @param username_ant
     * @param username
     * @param nombre
     */
    public void modificarPerfil(String username_ant, String username, String nombre) throws BackendException {
        domain.modificar_perfil(username_ant, username, nombre);
    }

    /**
     * Obtiene la información del perfil del usuario identificado por 'username'.
     * @param username
     * @return nombre
     */
    public String consultarNombre(String username) {
        try {
            String nombre = domain.consulta_nombre_usuario(username);
            return nombre;
        }
        catch(BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
        return "";
    }

    /**
     * Cambia el formato del archivo identificado por 'titulo' y 'autor' por el formato especificado.
     * @param titulo
     * @param autor
     * @param formato
     */
    public void cambiarFormato(String titulo, String autor, String formato) {
        try {
            domain.cambiar_formato_archivo(titulo, autor, formato);
        }
        catch(BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
    }

    /**
     * Crea un nuevo archivo identificado por 'titulo' y 'autor'.
     * @param titulo
     * @param autor
     * @param formato
     * @param contenido
     */
    public void altaArchivo(String titulo, String autor, String formato, String contenido) throws BackendException {
        domain.alta_archivo(titulo,autor,formato,contenido);
    }

    /**
     * Modifica el archivo identificado por los parámetros 'titulo' y 'autor' con los nuevos parámetros (tituloIn, autorIn, contenidoIn).
     * @param titulo
     * @param autor
     * @param tituloIn
     * @param autorIn
     * @param contenidoIn
     */
    public void editarArchivo(String titulo, String autor, String tituloIn, String autorIn, String contenidoIn) throws BackendException {
        domain.editar_archivo(titulo, autor, tituloIn, autorIn, contenidoIn);
    }

    /**
     * Crea un nuevo usuario en el sistema con los datos entrados.
     * @param username
     * @param contrasena
     * @param contrasena2
     * @param nombre
     */
    public Boolean nuevoPerfil(String username, String contrasena, String contrasena2, String nombre) {
        try {
            return domain.crear_perfil(username, contrasena, contrasena2, nombre);
        }
        catch(BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
        return false;
    }

    /**
     * Comprueba si el username existe y la contraseña corresponde a éste.
     * @param username
     * @param contrasena
     * @return verificado
     */
    public boolean verificarContrasena(String username, String contrasena) {
        try {
            boolean verificado = domain.verificar_perfil(username, contrasena);
            return verificado;
        }
        catch(BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
        return false;
    }

    /**
     * Abre la vista de registro de usuario.
     */
    public void abrirVistaRegistro() {
        vistaRegistro.hacerVisible();
    }

    /**
     * Abre la vista del menú, indicando que el inicio de sesión ha sido realizado por el usuario identificado por 'username'.
     * @param username
     */
    public void abrirVistaMenu(String username) {
        MenuCtrl.INSTANCE.setUser(username);
        MenuCtrl.INSTANCE.abrirMenu();
    }

    /**
     * Exporta el archivo seleccionado en el sistema al directorio del equipo correspondiente al 'path'.
     * @param path
     * @param titulo
     * @param autor
     */
    public void exportarArchivo(String titulo, String autor, String path) {
        try {
            domain.exportar_archivo(titulo, autor, path);
        }
        catch (BackendException e){
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
    }

    /**
     * Importa el archivo seleccionado en el sistema al equipo.
     * @param path
     */
    public void importarArchivo(String path) {
        try {
            domain.importar_archivo(path);
        }
        catch(BackendException e) {
            VistaDialogo.INSTANCE.mensajeError(e.toString());
        }
    }

}

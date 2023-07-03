package Domain;

import java.io.IOException;
import java.util.*;

import Exceptions.*;
import Domain.Usuario;
import Persistence.GestorUsuario;

public class UsuarioCtrl {
    /**
     * Map que tiene de clave primaria el username y contenido el usuario
     */
    private Map<String, Usuario> usuarios;
    /**
     * Referencia a la clase GestorUsuario para servirse de sus funcionalidades y persistir los usuarios
     */
    private GestorUsuario GU;

    /**
     * creadora basica que crea el hash map vacío pues los usuarios se van consultando on demand
     */
    public UsuarioCtrl() {
        usuarios = new HashMap<>();
        GU = new GestorUsuario();
    }

    /**
     * Se crea un usuario con los valores asignados y se añade al set
     * @param username
     * @param contrasena1
     * @param contrasena2 : contraseña de validación (repetición de contrasena1)
     * @param nombre
     * @exception UsernameNoValidoExcepcion el nombre de usuario no es adecuado
     * @exception ContrasenaNoValidaExcepcion la contraseña no es adecuada
     */
    public boolean crear_perfil(String username, String contrasena1, String contrasena2 , String nombre) throws UsernameNoValidoExcepcion, ContrasenaNoValidaExcepcion, IOException, ContrasenaVaciaException {
        boolean usernamevalido = username_valido(username);
        if(contrasena1.equals("") || contrasena2.equals("")) throw new ContrasenaVaciaException();
        else {
            boolean contrasenavalida = contrasena_valida(contrasena1, contrasena2);
            if (!usernamevalido) throw new UsernameNoValidoExcepcion(username);
            else if (!contrasenavalida) throw new ContrasenaNoValidaExcepcion(contrasena1, contrasena2);
            else {
                Usuario usuario = new Usuario(username, contrasena1, nombre);
                usuarios.put(username, usuario);
                GU.guardar_usuario(usuario);
                return true;
            }
        }
    }

    /**
     * Se actualizan los atributos del usuario con username username_ant con los parámetros correspondientes. Si el username
     * no es válido no se realiza ninguna modificación
     * @param username_ant
     * @param username
     * @param nombre
     * @exception UsuarioNoExisteException el usuario no existe
     * @exception UsernameNoValidoExcepcion el nombre de usuario no es valido
     */
    public void modificar_perfil(String username_ant, String username, String nombre) throws UsuarioNoExisteException, UsernameNoValidoExcepcion, IOException {
        Usuario usuario = cargar_usuario(username_ant);
        boolean usernamevalido = username_valido(username);
        if ((!username_ant.equals(username)) && !usernamevalido) throw new UsernameNoValidoExcepcion(username);
        else {
            usuario.modificar(username, nombre);
            usuarios.remove(username_ant);
            usuarios.put(username, usuario);
            GU.eliminar_usuario(username_ant);
            GU.guardar_usuario(usuario);
        }
    }

    /**
     * Se actualiza la contraseña del usuario con username "username" con los parámetros correspondientes. Si la contraseña no
     * es válida no se realiza ninguna modificación
     * @param username
     * @param contrasena
     * @param nueva_contrasena
     * @exception UsuarioNoExisteException el usuario no existe
     * @exception ContrasenaNoValidaExcepcion el nombre de usuario no es valido
     */
    public void cambiar_contrasena(String username, String contrasena, String nueva_contrasena) throws UsuarioNoExisteException, ContrasenaNoValidaExcepcion, IOException, ContrasenaVaciaException {
        Usuario usuario = cargar_usuario(username);
        if(nueva_contrasena.equals("")) throw new ContrasenaVaciaException();
        else {
            boolean contrasenavalida = contrasena_valida(contrasena, usuario.consulta_contrasena());
            if (!contrasenavalida) throw new ContrasenaNoValidaExcepcion(contrasena, usuario.consulta_contrasena());
            else {
                usuario.cambiar_contrasena(nueva_contrasena);
                usuarios.put(username, usuario);
                GU.guardar_usuario(usuario);
            }
        }
    }

    /**
     *  El archivo con claves titulo y autor es quitado del set
     * @param username
     * @exception UsuarioNoExisteException el usuario no existe
     */
    public void borrar_perfil(String username) throws UsuarioNoExisteException {
        if (cargar_usuario(username) != null) {
            usuarios.remove(username);
            GU.eliminar_usuario(username);
        }
        else throw new UsuarioNoExisteException(username);
    }

    public String consulta_nombre_usuario(String username) throws UsuarioNoExisteException {
        return cargar_usuario(username).consulta_nombre();
    }
    /**
     *  Inicia sesion el usuario con username username y contraseña contraseña
     * @param username
     * @param contrasena
     * @exception UsuarioNoExisteException el usuario no existe
     */
    public boolean verificar_perfil(String username, String contrasena) throws UsuarioNoExisteException, ContrasenaNoValidaExcepcion {
        Usuario usuario = cargar_usuario(username);
        if (usuario != null && contrasena_valida(contrasena, usuario.consulta_contrasena())) return true;
        else{
            throw new ContrasenaNoValidaExcepcion(contrasena, usuario.consulta_contrasena());
        }
    }

    /**
     *  Se comprueba que el username username sea válido, es decir, que no haya ningún otro usuario que lo use. Devuelve
     *  true si no hay nadie que lo use, en caso contrario devuelve false
     * @param username
     * @return valido
     */
    private boolean username_valido(String username) {
        boolean valido = !GU.existe_usuario(username);
        valido = valido && (!username.equals(""));
        valido = valido && !username.matches(".*[<>:\"\\/|?*].*");
        return valido;
    }

    /**
     *  Se comprueba que las dos contraseñas sean iguales. Si lo son devuelve true, en caso contrario devuelve false
     * @param contrasena1
     * @param contrasena2 : contraseña de validación (repetición de contrasena1)
     * @return valida
     */
    private boolean contrasena_valida(String contrasena1, String contrasena2) {
        boolean valida = contrasena1.equals(contrasena2);
        return valida;
    }

    private Usuario cargar_usuario(String username) throws UsuarioNoExisteException {
        Usuario result = usuarios.get(username);
        try{
            if (result == null)
                result = GU.cargar_usuario(username);
                return result;
        }
        catch(Exception e){
            throw new UsuarioNoExisteException(username);
        }
    }
}
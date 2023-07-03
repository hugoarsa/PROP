package Persistence;

import Domain.Usuario;
import java.util.*;
import java.io.*;



public class GestorUsuario {
    /**
     * path donde se guardan los usuarios
     */
    private String path;
    /**
     * Referencia al gestor de entrada y salida
     */
    private GestorIO IO;

    /**
     * Creadora basica que crea el directorio inicial
     */
    public GestorUsuario(){
        path = "./bin/UsuarioSer/";
        IO = new GestorIO();
        IO.create_dir(path);
    }

    /**
     * Funcion encargada de guaradar un usuario
     * @param user usuario a guardar
     * @throws IOException error de escritura
     */
    public void guardar_usuario(Usuario user) throws IOException {
        String filename = user.consulta_username() + ".ser";
        IO.guardar_objeto(path + filename,user);
    }

    /**
     * Carga el usuario de username username
     * @param username nombre de usuario del usuario a cargar
     * @return usuario de nombre usuario username
     * @throws IOException error de lectura
     * @throws ClassNotFoundException clase no encontrada
     */
    public Usuario cargar_usuario(String username) throws IOException, ClassNotFoundException {
        String filename = username + ".ser";
        return (Usuario)IO.cargar_objeto(path + filename);
    }

    /**
     * Funcion booleana para comprobar si existe
     * @param username nombre del usuario
     * @return booleano segun si existe o no
     */
    public boolean existe_usuario(String username) {
        File file = new File(path + username + ".ser");
        return file.exists();
    }

    /**
     * Elimina el usuario en cuestion
     * @param username
     */
    public void eliminar_usuario(String username){
        String filename = username + ".ser";
        IO.eliminar_objeto(path + filename);
    }

    /**
     * Borra todos los usuarios
     */
    public void clear_usuarios(){
        IO.clear_objetos(path);
    }
}

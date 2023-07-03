package Domain;

import java.io.Serializable;

public class Usuario implements java.io.Serializable {
    /**
     * nombre de usuario
     */
    private String username;
    /**
     * contraseña de usuario
     */
    private String contrasena;
    /**
     * nombre de pila
     */
    private String nombre;

    //CREADORA

    /**
     * Se crea un usuario con los valores asignados
     *
     * @param username
     * @param contrasena
     * @param nombre
     */
    public Usuario(String username, String contrasena, String nombre) {
        setusername(username);
        setcontrasena(contrasena);
        setnombre(nombre);
    }

    //MODIFICADORAS

    /**
     * El usuario actualiza sus atributos con los parámetros correspondientes. Si el parametro es null,
     * no es necesario actualizar pues no se ha solicitado una modificacion de ese atributo
     *
     * @param username
     * @param nombre
     */
    public void modificar(String username, String nombre) {
        if (username != null) setusername(username);
        if (nombre != null) setnombre(nombre);
    }

    /**
     * El usuario actualiza su contraseña con el parámetro correspondiente. Si el parametro es null,
     * no es necesario actualizar pues no se ha solicitado una modificacion de ese atributo
     *
     * @param contrasena
     */
    public void cambiar_contrasena(String contrasena) {
        if (contrasena != null) setcontrasena(contrasena);
    }

    //CONSULTORAS

    /**
     * @return username
     */
    public String consulta_username() {
        return username;
    }

    /**
     * @return contrasena
     */
    public String consulta_contrasena() {
        return contrasena;
    }

    /**
     * @return nombre
     */
    public String consulta_nombre() {
        return nombre;
    }

    //PRIVATE
    /**
     * Se actualiza el username del usuario
     *
     * @param username
     */
    private void setusername(String username) {
        this.username = username;
    }

    /**
     * Se actualiza la contraseña del usuario
     *
     * @param contrasena
     */
    private void setcontrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Se actualiza el nombre del usuario
     *
     * @param nombre
     */
    private void setnombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof Usuario))
            return false;
        Usuario other = (Usuario)o;
        boolean usernameEquals = (this.username == null && other.username == null)
                || (this.username != null && this.username.equals(other.username));
        boolean contrasenaEquals = (this.contrasena == null && other.contrasena == null)
                || (this.contrasena != null && this.contrasena.equals(other.contrasena));
        boolean nombreEquals = (this.nombre == null && other.nombre == null)
                || (this.nombre != null && this.nombre.equals(other.nombre));
        return usernameEquals && contrasenaEquals && nombreEquals;
    }

    @Override
    public final int hashCode(){
        int result = 17;
        if(username != null){
            result = 31 * result + username.hashCode();
        }
        if(contrasena != null){
            result = 31 * result + contrasena.hashCode();
        }
        if(nombre != null){
            result = 31 * result + nombre.hashCode();
        }
        return result;
    }
}
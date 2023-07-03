import Domain.Usuario;

//package test.java.Domain;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsuarioTest {

    @Test
    public void TestCrearUsuarioYConsulta() {
        String username = "Pepito Grillo";
        String contrasena = "1234";
        String nombre = "Eric";
        Usuario usu = new Usuario(username, contrasena, nombre);
        assertNotNull(usu);
        assertEquals(username, usu.consulta_username());
        assertEquals(contrasena, usu.consulta_contrasena());
        assertEquals(nombre, usu.consulta_nombre());
    }

    @Test
    public void TestModificarUsuario() {
        //Creamos el Usuario con el que testear
        String username = "Pepito Grillo";
        String contrasena = "1234";
        String nombre = "Eric";
        Usuario usu = new Usuario(username, contrasena, nombre);

        //Modificamos todos los atributos
        String nuevo_username = "Pepito";
        String nuevo_nombre = "ERIC";
        usu.modificar(nuevo_username, nuevo_nombre);
        //Comprobamos que el username se ha cambiado correctamente
        assertEquals(nuevo_username, usu.consulta_username());
        //Comprobamos que la contraseña se ha cambiado correctamente
        assertEquals(nuevo_nombre, usu.consulta_nombre());
    }

    @Test
    public void TestModificarUsuarioNull() {
        //Creamos el Usuario con el que testear
        String username = "Pepito Grillo";
        String contrasena = "1234";
        String nombre = "Eric";
        Usuario usu = new Usuario(username, contrasena, nombre);

        //No modificamos (entran los parametros como null)
        String no_username = null;
        String no_nombre = null;
        usu.modificar(no_username, no_nombre);
        //Comprobamos que los valores no sean null
        assertNotNull(usu.consulta_username());
        assertNotNull(usu.consulta_nombre());
    }

    @Test
    public void TestCambiarContrasena() {
        //Creamos el Usuario con el que testear
        String username = "Pepito Grillo";
        String contrasena = "1234";
        String nombre = "Eric";
        Usuario usu = new Usuario(username, contrasena, nombre);

        //Modificamos todos los atributos
        String nueva_contrasena = "12345";
        usu.cambiar_contrasena(nueva_contrasena);
        //Comprobamos que la contraseña se ha cambiado correctamente
        assertEquals(nueva_contrasena, usu.consulta_contrasena());
    }

    @Test
    public void TestCambiarContrasenaNull() {
        //Creamos el Usuario con el que testear
        String username = "Pepito Grillo";
        String contrasena = "1234";
        String nombre = "Eric";
        Usuario usu = new Usuario(username, contrasena, nombre);

        //No modificamos (entran la contraseña como null)
        String no_contrasena = null;
        usu.cambiar_contrasena(no_contrasena);
        //Comprobamos que la contraseña no sean null
        assertNotNull(usu.consulta_contrasena());
    }

}
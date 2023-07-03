import Domain.UsuarioCtrl;

//package test.java.Domain;
import Exceptions.ContrasenaNoValidaExcepcion;
import Exceptions.UsernameNoValidoExcepcion;
import Exceptions.UsuarioNoExisteException;
import Persistence.GestorUsuario;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsuarioCtrlTest {

    @After
    public void ClearUsuarioSerial() throws Exception{
        GestorUsuario GU = new GestorUsuario();
        GU.clear_usuarios();
    }

    @Test
    public void TestCrearYExistePerfil() throws Exception {
        UsuarioCtrl usuarioCtrl = new UsuarioCtrl();
        String username = "huguitoBonito";
        String contrasena1 = "gatitos4ever";
        String contrasena2 = "gatitos4ever";
        String nombre = "Hugo";
        usuarioCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);

        assertTrue(usuarioCtrl.verificar_perfil(username, contrasena1));
    }

    @Test (expected = UsernameNoValidoExcepcion.class)
    public void TestCrearPerfilErrorUsername() throws Exception {
        UsuarioCtrl usuarioCtrl = new UsuarioCtrl();
        String username = "Pepito Grillo";
        String contrasena1 = "1234";
        String contrasena2 = "1234";
        String nombre = "Eric";
        usuarioCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);

        String nuevo_username = "Pepito Grillo";
        String nueva_contrasena1 = "12345";
        String nueva_contrasena2 = "12345";
        String nuevo_nombre = "Eric Riera";
        usuarioCtrl.crear_perfil(nuevo_username, nueva_contrasena1, nueva_contrasena2, nuevo_nombre);

        assertFalse(usuarioCtrl.verificar_perfil(nuevo_username, nueva_contrasena1));
    }

    @Test (expected = ContrasenaNoValidaExcepcion.class)
    public void TestCrearPerfilErrorContrasena() throws Exception {
        UsuarioCtrl usuarioCtrl = new UsuarioCtrl();
        String username = "Pepito Grillo";
        String contrasena1 = "1234";
        String contrasena2 = "12345";
        String nombre = "Eric";
        usuarioCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);

        assertFalse(usuarioCtrl.verificar_perfil(username, contrasena1));
        assertFalse(usuarioCtrl.verificar_perfil(username, contrasena2));
    }

    @Test
    public void TestModificarPerfil() throws Exception {
        UsuarioCtrl usuarioCtrl = new UsuarioCtrl();
        String username = "huguitoBonito";
        String contrasena1 = "gatitos4ever";
        String contrasena2 = "gatitos4ever";
        String nombre = "Hugo";
        usuarioCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);

        String nuevo_username = "huguitoPrecioso";
        String nuevo_nombre = "Hugo Aranda";
        usuarioCtrl.modificar_perfil(username, nuevo_username, nuevo_nombre);

        assertTrue(usuarioCtrl.verificar_perfil(nuevo_username,contrasena1));
    }

    @Test (expected = UsernameNoValidoExcepcion.class)
    public void TestModificarPerfilErrorUsername() throws Exception {
        UsuarioCtrl usuarioCtrl = new UsuarioCtrl();
        String username = "Pepito Grillo";
        String contrasena1 = "1234";
        String contrasena2 = "1234";
        String nombre = "Eric";
        usuarioCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);

        String nuevo_username = "huguitoPrecioso";
        String nueva_contrasena1 = "Haru4ever";
        String nueva_contrasena2 = "Haru4ever";
        String nuevo_nombre = "Hugo Aranda";
        usuarioCtrl.crear_perfil(nuevo_username, nueva_contrasena1, nueva_contrasena2, nuevo_nombre);

        String new_nombre = "Hugo";
        usuarioCtrl.modificar_perfil(nuevo_username, username, new_nombre);

        assertFalse(usuarioCtrl.verificar_perfil(username, contrasena1));
    }

    @Test
    public void TestCambiarContrasena() throws Exception {
        UsuarioCtrl usuarioCtrl = new UsuarioCtrl();
        String username = "huguitoBonito";
        String contrasena1 = "gatitos4ever";
        String contrasena2 = "gatitos4ever";
        String nombre = "Hugo";
        usuarioCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);

        String nueva_contrasena = "Haru4ever";
        usuarioCtrl.cambiar_contrasena(username, contrasena1, nueva_contrasena);

        assertTrue(usuarioCtrl.verificar_perfil(username, nueva_contrasena));
    }
    @Test (expected = ContrasenaNoValidaExcepcion.class)
    public void TestCambiarContrasenaError() throws Exception {
        UsuarioCtrl usuarioCtrl = new UsuarioCtrl();
        String username = "Pepito Grillo";
        String contrasena1 = "1234";
        String contrasena2 = "1234";
        String nombre = "Eric";
        usuarioCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);

        String nueva_contrasena = "12345";
        usuarioCtrl.cambiar_contrasena(username, nueva_contrasena, nueva_contrasena);

        assertFalse(usuarioCtrl.verificar_perfil(username, nueva_contrasena));
    }
    @Test(expected= UsuarioNoExisteException.class)
    public void TestBorrarPerfil() throws Exception {
        UsuarioCtrl usuarioCtrl = new UsuarioCtrl();
        String username = "huguitoBonito";
        String contrasena = "gatitos4ever";
        String nombre = "Hugo";
        usuarioCtrl.crear_perfil(username, contrasena, contrasena, nombre);

        usuarioCtrl.borrar_perfil(username);
        assertFalse(usuarioCtrl.verificar_perfil(username, contrasena));
    }

    @Test
    public void TestVerificaPerfilExiste() throws Exception{
        UsuarioCtrl usuarioCtrl = new UsuarioCtrl();
        String username = "huguitoBonito";
        String contrasena = "gatitos4ever";
        String nombre = "Hugo";
        usuarioCtrl.crear_perfil(username, contrasena, contrasena, nombre);

        assertTrue(usuarioCtrl.verificar_perfil(username, contrasena));

    }

    @Test (expected=UsuarioNoExisteException.class)
    public void TestVerificaPerfilNoExiste() throws Exception{
        UsuarioCtrl usuarioCtrl = new UsuarioCtrl();
        String username = "huguitoBonito";
        String contrasena = "gatitos4ever";
        assertFalse(usuarioCtrl.verificar_perfil(username, contrasena));

    }
}
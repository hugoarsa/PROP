import Domain.Usuario;
import Persistence.GestorUsuario;
//package test.java.Domain;
import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.*;

public class GestorUsuarioTest {

    @After
    public void ClearPersistence() throws Exception{
        GestorUsuario GU = new GestorUsuario();
        GU.clear_usuarios();
    }
    @Test
    public void TestGuardarYCargarUsuario() throws Exception{
        Usuario user = new Usuario("user", "pwd", "name");
        GestorUsuario GU = new GestorUsuario();
        GU.guardar_usuario(user);
        Usuario user2 = GU.cargar_usuario("user");
        assertEquals(user2.consulta_username(), "user");
        assertEquals(user2.consulta_nombre(), "name");
        assertEquals(user2.consulta_contrasena(), "pwd");
    }

}
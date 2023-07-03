import Domain.CjtAutores;
import Persistence.GestorAutor;
import org.junit.After;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import Exceptions.AutorNoExisteException;

import static org.junit.Assert.*;

public class CjtAutoresTest {

    @After
    public void clearAutores() throws Exception{
        CjtAutores autores = CjtAutores.getINSTANCE();
        autores.clearCache();
    }

    @After
    public void clearPersistence() throws Exception{
        GestorAutor GA = new GestorAutor();
        GA.clear_autores();
    }

    @Test
    public void TestInsertarYListarAutorSinPrefijo() throws Exception {
        String prefijo = "";
        CjtAutores autores = CjtAutores.getINSTANCE();
        assertNotNull(autores);

        //Comprobamos que se haya añadido correctamente
        String autor_ini = "Romina";
        autores.insertar_autor(autor_ini);
        assertTrue(autores.listar_autores(prefijo).contains(autor_ini));
    }

    @Test
    public void TestEliminarAutorCompletamente() throws Exception {
        String prefijo = "";
        CjtAutores autores = CjtAutores.getINSTANCE();
        String autor_ini = "Mariana";
        String titulo = "El caracol";
        autores.insertar_autor(autor_ini);
        assertTrue(autores.listar_autores(prefijo).contains(autor_ini));
        //Comprobamos que se haya eliminado el autor correctamente
        autores.eliminar_autor(autor_ini);
        assertFalse(autores.listar_autores(prefijo).contains(autor_ini));

    }

    @Test
    public void TestEliminarAutor() throws Exception {
        String prefijo = "";
        CjtAutores autores = CjtAutores.getINSTANCE();
        String autor_ini = "Mariana";
        String titulo = "El caracol";
        String titulo2 = "La hormiga";

        //Comprobamos que no se elimina completamente porque aún existe como autor de otros archivos
        autores.insertar_autor(autor_ini);
        autores.insertar_autor(autor_ini);
        autores.eliminar_autor(autor_ini);
        assertTrue(autores.listar_autores(prefijo).contains(autor_ini));

    }

    @Test (expected = AutorNoExisteException.class)
    public void TestEliminarAutorNoExistente() throws Exception {
        CjtAutores autores = CjtAutores.getINSTANCE();
        assertNotNull(autores);
        autores.eliminar_autor("Johan");
    }

    @Test
    public void TestListarAutorConPrefijo() throws Exception {
        CjtAutores autores = CjtAutores.getINSTANCE();
        Set<String> autores_comp = new HashSet<String>();

        String autor1 = "Pedrito";
        String autor2 = "Ana";
        String autor3 = "Pea";
        String autor4 = "Penelope";
        String titulo = "El caracol veloz";

        autores.insertar_autor(autor1);
        autores.insertar_autor(autor2);
        autores.insertar_autor(autor3);
        autores.insertar_autor(autor4);


        //Resultado esperado
        autores_comp.add(autor1);
        autores_comp.add(autor3);
        autores_comp.add(autor4);

        assertEquals(autores_comp, autores.listar_autores("Pe"));

    }
}

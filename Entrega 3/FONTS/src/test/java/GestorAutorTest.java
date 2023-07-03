import Domain.CjtAutores;
import Persistence.GestorAutor;
import org.junit.Test;
import org.junit.After;
import java.util.*;

import static org.junit.Assert.*;

public class GestorAutorTest {

    @After
    public void ClearPersistence() throws Exception{
        GestorAutor GA = new GestorAutor();
        GA.clear_autores();
    }

    @Test
    public void TestGuardarYCargarAutores() throws Exception{
        GestorAutor GA = new GestorAutor();
        GA.guardar_autor("pepe", 3);
        GA.guardar_autor("chema", 2);
        GA.guardar_autor("jose", 1);

        Map<String, Integer> autores_comp = new HashMap<String, Integer>();
        autores_comp.put("pepe", 3);
        autores_comp.put("chema", 2);
        autores_comp.put("jose", 1);

        assertEquals(autores_comp, GA.get_autores());
    }

    @Test
    public void TestGuardarYEliminarAutores() throws Exception{
        GestorAutor GA = new GestorAutor();
        GA.guardar_autor("pepe", 3);
        GA.guardar_autor("chema", 2);
        GA.guardar_autor("jose", 1);

        Map<String, Integer> autores_comp = new HashMap<String, Integer>();
        autores_comp.put("pepe", 3);
        autores_comp.put("chema", 2);
        autores_comp.put("jose", 1);

        assertEquals(autores_comp, GA.get_autores());

        GA.eliminar_autor("pepe");
        GA.eliminar_autor("jose");
        autores_comp.remove("pepe");
        autores_comp.remove("jose");

        assertEquals(autores_comp, GA.get_autores());
    }
}

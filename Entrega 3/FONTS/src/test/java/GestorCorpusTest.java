import Domain.Pair;
import Domain.VectorMEV;
import Persistence.GestorCorpus;
import org.junit.Test;
import org.junit.After;
import java.util.*;

import static org.junit.Assert.*;

public class GestorCorpusTest {
    @After
    public void ClearPersistence() throws Exception{
        GestorCorpus GC = new GestorCorpus();
        GC.clear_corpus();
    }

    @Test
    public void TestGuardarYCargarDC() throws Exception{
        GestorCorpus GC = new GestorCorpus();
        GC.guardar_DC("test", 3);
        GC.guardar_DC("tested", 2);
        GC.guardar_DC("testeded", 1);

        Map<String, Integer> DC_comp = new HashMap<>();
        DC_comp.put("test", 3);
        DC_comp.put("tested", 2);
        DC_comp.put("testeded", 1);

        assertEquals(DC_comp, GC.get_DC());
    }

    @Test
    public void TestGuardarYCargarArchivoVector() throws Exception{
        GestorCorpus GC = new GestorCorpus();
        String texto1 = "She sells seashells by the seashore, " +
                "the seashells she sells are seashells, " +
                "I'm sure. So, if she sells seashells by the seashore," +
                " then I'm sure she sells seashore shells";
        String texto2 = "Tres tristes tigres tragan trigo " +
                "en tres tristes trastos, sentados tras un trigal. " +
                "Sentados tras un trigal, en tres tristes trastos tragaban " +
                "trigo tres tristes tigres?";
        VectorMEV vec1 = new VectorMEV(texto1);
        VectorMEV vec2 = new VectorMEV(texto2);

        GC.guardar_archivoVector("titulo1", "autor1", vec1);
        GC.guardar_archivoVector("titulo2", "autor2", vec2);

        Map<Pair<String, String>, VectorMEV> archivoVector_comp = new HashMap<>();
        archivoVector_comp.put(new Pair<>("titulo1", "autor1"), vec1);
        archivoVector_comp.put(new Pair<>("titulo2", "autor2"), vec2);
        assertEquals(archivoVector_comp, GC.get_archivoVector());
    }

    @Test
    public void TestGuardarYCargarSize() throws Exception{
        GestorCorpus GC = new GestorCorpus();
        GC.guardar_sizeDocSum(270);
        Integer val = 270;
        assertEquals(val, GC.get_sizeDocSum());
    }
}

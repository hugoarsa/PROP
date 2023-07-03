import Domain.VectorMEV;
//package test.java.Domain;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class VectorMEVTest {

    @Test
    public void TestCreacionYConsulta() {
        String textoEnglish = "She sells seashells by the seashore, " +
                       "the seashells she sells are seashells, " +
                       "I'm sure. So, if she sells seashells by the seashore," +
                       " then I'm sure she sells seashore shells";
        String textoSpanish = "Tres tristes tigres tragan trigo " +
                "en tres tristes trastos, sentados tras un trigal. " +
                "Sentados tras un trigal, en tres tristes trastos tragaban " +
                "trigo tres tristes tigres?";
        VectorMEV vecTest = new VectorMEV(textoEnglish);
        assertNotNull(vecTest);
        Map<String, Integer> mapTest = vecTest.consulta_pesos_TC();
        Integer val = 2;
        assertEquals(val, mapTest.get("sure"));
        val = 4;
        assertEquals(val, mapTest.get("sells"));
        assertEquals(val, mapTest.get("seashells"));
        val = 1;
        assertEquals(val, mapTest.get("shells"));
        assertEquals(val, mapTest.get("then"));
        val = 2;
        assertEquals(val, mapTest.get("i'm"));
        val = 3;
        assertEquals(val, mapTest.get("the"));
        val = 4;
        assertEquals(val, mapTest.get("she"));
        val = 1;
        assertEquals(val, mapTest.get("are"));
        val = 3;
        assertEquals(val, mapTest.get("seashore"));
        val = 2;
        assertEquals(val, mapTest.get("by"));
        val = 1;
        assertEquals(val, mapTest.get("so"));
        assertEquals(val, mapTest.get("if"));
        val = 29;
        assertEquals(val, vecTest.consulta_size());
        vecTest = new VectorMEV(textoSpanish);
        assertNotNull(vecTest);
        mapTest = vecTest.consulta_pesos_TC();
        val = 2;
        assertEquals(val, mapTest.get("trastos"));
        val = 4;
        assertEquals(val, mapTest.get("tristes"));
        val = 2;
        assertEquals(val, mapTest.get("tigres"));
        assertEquals(val, mapTest.get("tras"));
        val = 1;
        assertEquals(val, mapTest.get("tragan"));
        val = 2;
        assertEquals(val, mapTest.get("en"));
        assertEquals(val, mapTest.get("un"));
        val = 4;
        assertEquals(val, mapTest.get("tres"));
        val = 2;
        assertEquals(val, mapTest.get("trigo"));
        assertEquals(val, mapTest.get("sentados"));
        assertEquals(val, mapTest.get("trigal"));
        val = 1;
        assertEquals(val, mapTest.get("tragaban"));
        val = 26;
        assertEquals(val, vecTest.consulta_size());
    }

    @Test
    public void TestCreacionYConsultaSpanish() {
        String texto = "Tres tristes tigres tragan trigo " +
                       "en tres tristes trastos, sentados tras un trigal. " +
                       "Sentados tras un trigal, en tres tristes trastos tragaban " +
                       "trigo tres tristes tigres?";
        VectorMEV vecTest = new VectorMEV(texto);
        assertNotNull(vecTest);
        Map<String, Integer> mapTest = vecTest.consulta_pesos_TC();
        Integer val = 2;
        assertEquals(val, mapTest.get("trastos"));
        val = 4;
        assertEquals(val, mapTest.get("tristes"));
        val = 2;
        assertEquals(val, mapTest.get("tigres"));
        assertEquals(val, mapTest.get("tras"));
        val = 1;
        assertEquals(val, mapTest.get("tragan"));
        val = 2;
        assertEquals(val, mapTest.get("en"));
        assertEquals(val, mapTest.get("un"));
        val = 4;
        assertEquals(val, mapTest.get("tres"));
        val = 2;
        assertEquals(val, mapTest.get("trigo"));
        assertEquals(val, mapTest.get("sentados"));
        assertEquals(val, mapTest.get("trigal"));
        val = 1;
        assertEquals(val, mapTest.get("tragaban"));
        val = 26;
        assertEquals(val, vecTest.consulta_size());
    }

}
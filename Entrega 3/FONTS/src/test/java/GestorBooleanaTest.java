import Domain.ExpBooleana;
import Domain.ExpBooleanaCtrl;
import Persistence.GestorBooleana;
//package test.java.Domain;
import org.junit.Test;
import org.junit.After;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class GestorBooleanaTest {

    @Test
    public void TestGuardarYCargarExpresion() throws Exception{
        //FUNCIONA !!!!!!
        ExpBooleana exp = new ExpBooleana("pepe & !jose | !juan & albert | maria", 3);
        GestorBooleana GB = new GestorBooleana();
        GB.guardar_expresion(exp);
        ExpBooleana exp2 = GB.cargar_expresion(3);
        Integer a = 3;
        assertEquals(exp2.to_Id(), a);
        assertEquals(exp2.to_String(), "pepe & !jose | !juan & albert | maria");
    }

    @Test
    public void getAll2() throws Exception{
        ExpBooleanaCtrl BC1 = new ExpBooleanaCtrl();
        BC1.alta_expresion("pepe & !jose | !juan & albert | maria");
        BC1.alta_expresion("pepe & !jose");
        BC1.alta_expresion("!juan & albert | maria");
        BC1.alta_expresion("!juan & albert");

        ExpBooleanaCtrl BC2 = new ExpBooleanaCtrl();
        Set<String> A = BC2.listar_expresiones();
        Set<String> B = new HashSet<String>();
        B.add("pepe & !jose | !juan & albert | maria");
        B.add("pepe & !jose");
        B.add("!juan & albert | maria");
        B.add("!juan & albert");
        assertEquals(A, B);
    }

    @After
    public void ClearBoolSerial() throws Exception{
        GestorBooleana GB = new GestorBooleana();
        GB.clear_expresiones();
    }
}
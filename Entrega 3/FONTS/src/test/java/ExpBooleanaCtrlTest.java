import Domain.ExpBooleanaCtrl;
import Domain.ExpBooleana;

import java.util.HashSet;
import java.util.Set;

import Persistence.GestorBooleana;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpBooleanaCtrlTest {

    @Test
    public void test_insertar() throws Exception { //aqui se comprueba insertar y listar a la vez
        ExpBooleanaCtrl A = new ExpBooleanaCtrl();

        Set<String> cjt_expresiones = new HashSet<String>();

        A.alta_expresion("pepe & (!jose | (!juan)) & ((albert) | maria)");
        A.alta_expresion("pepe & !jose | !juan & albert | maria");
        A.alta_expresion("pepe & ((!jose | juan))");

        cjt_expresiones.add("pepe & (!jose | (!juan)) & ((albert) | maria)");
        cjt_expresiones.add("pepe & !jose | !juan & albert | maria");
        cjt_expresiones.add("pepe & ((!jose | juan))");

        assertEquals(cjt_expresiones,A.listar_expresiones());
    }

    @Test
    public void test_borrar() throws Exception { //aqui se presenta una expresion sin parentesis para evaluar la precedencia
        ExpBooleanaCtrl A = new ExpBooleanaCtrl();

        Set<String> cjt_expresiones = new HashSet<String>();

        A.alta_expresion("pepe & (!jose | (!juan)) & ((albert) | maria)");
        A.alta_expresion("pepe & !jose | !juan & albert | maria");
        A.alta_expresion("pepe & ((!jose | juan))");

        A.borrar_expresion("pepe & !jose | !juan & albert | maria");
        A.borrar_expresion("pepe & ((!jose | juan))");

        cjt_expresiones.add("pepe & (!jose | (!juan)) & ((albert) | maria)");
        cjt_expresiones.add("pepe & !jose | !juan & albert | maria");
        cjt_expresiones.add("pepe & ((!jose | juan))");

        cjt_expresiones.remove("pepe & !jose | !juan & albert | maria");
        cjt_expresiones.remove("pepe & ((!jose | juan))");

        assertEquals(cjt_expresiones,A.listar_expresiones());
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

    @Test
    public void test_extraer() throws Exception { //aqui se presenta una expresion sin parentesis para evaluar la precedencia
        ExpBooleanaCtrl A = new ExpBooleanaCtrl();
        A.alta_expresion("pepe & (!jose | (!juan)) & ((albert) | maria)");

        ExpBooleana e1 = A.consultar_expresion("pepe & (!jose | (!juan)) & ((albert) | maria)");

        String s = "pepe & (!jose | (!juan)) & ((albert) | maria)";

        assertEquals(s,e1.to_String());
    }

    @After
    public void ClearBoolSerial() throws Exception{
        GestorBooleana GB = new GestorBooleana();
        GB.clear_expresiones();
    }
}
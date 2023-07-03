import Domain.ExpBooleana;
import org.junit.Test;

import Exceptions.MalParentesisException;
import Exceptions.MalUsoOperandosException;
import Exceptions.ComillasException;
import Exceptions.CaracterIlegalException;

import static org.junit.Assert.*;

public class ExpBooleanaTest {

    @Test
    public void test_without_parenthesis() throws Exception { //aqui se presenta una expresion sin parentesis para evaluar la precedencia
        String expresion = "pepe & !jose | !juan & albert | maria";

        String frase_falsa = "buenos dias jose y juan como va todo";
        String frase_cierta = "buenos dias pepe como va todo y tu albert";

        ExpBooleana e = new ExpBooleana(expresion, 1);

        assertNotNull(e);
        assertEquals(expresion, e.to_String());
        assertFalse(e.match(frase_falsa));
        assertTrue(e.match(frase_cierta));
    }

    @Test
    public void test_with_parenthesis() throws Exception { //aqui se presentan parentesis que alteran el orden natural de evaluacion
        String expresion = "pepe & (!jose | !juan) & (albert | maria)";

        String frase_cierta = "buenos dias pepe juan como va todo y tu maria";
        String frase_falsa = "buenos dias pepe como va todo y jose juan";

        ExpBooleana e = new ExpBooleana(expresion, 1);

        assertNotNull(e);
        assertEquals(expresion, e.to_String());
        assertFalse(e.match(frase_falsa));
        assertTrue(e.match(frase_cierta));
    }

    @Test
    public void test_too_many_parenthesis() throws Exception { //aqui hay muchos parentesis innecesarios pero estan bien parentecizados
        String expresion = "pepe & (((!jose | (!juan)))) & (((albert | maria)))";

        String frase_cierta = "buenos dias pepe juan como va todo y tu maria";
        String frase_falsa = "buenos dias pepe como va todo y jose juan";

        ExpBooleana e = new ExpBooleana(expresion, 1);

        assertNotNull(e);
        assertEquals(expresion, e.to_String());
        assertFalse(e.match(frase_falsa));
        assertTrue(e.match(frase_cierta));
    }

    @Test
    public void test_comillas() throws Exception { //aqui se presentan parentesis que alteran el orden natural de evaluacion
        String expresion = "pepe & \"(!jose | !juan)\" & (albert | maria)";

        String frase_cierta = "(!jose | !juan) pepe albert";
        String frase_falsa = "lorem ipsum lo que sea lololol lmao lololol albert maria pepe";

        ExpBooleana e = new ExpBooleana(expresion, 1);

        assertNotNull(e);
        assertEquals(expresion, e.to_String());
        assertFalse(e.match(frase_falsa));
        assertTrue(e.match(frase_cierta));
    }

    @Test
    public void test_brackets() throws Exception { //aqui se presentan parentesis que alteran el orden natural de evaluacion
        String expresion = "pepe | {juan albert  mama   papa}";

        String frase_cierta = "la mama de albert y el papa de juan se han enrollado";
        String frase_falsa = "hola que tal como estas juan y albert visteis a papa?";

        ExpBooleana e = new ExpBooleana(expresion, 1);

        assertNotNull(e);
        assertEquals(expresion, e.to_String());
        assertFalse(e.match(frase_falsa));
        assertTrue(e.match(frase_cierta));
    }

    @Test
    public void test_dumb_spacing() throws Exception { //aqui se presentan parentesis que alteran el orden natural de evaluacion
        String expresion = "   pepe    |    { juan    albert  mama   papa }   ";

        String frase_cierta = "la mama de albert y el papa de juan se han enrollado";
        String frase_falsa = "hola que tal como estas juan y albert visteis a papa?";

        ExpBooleana e = new ExpBooleana(expresion, 1);

        assertNotNull(e);
        assertEquals(expresion, e.to_String());
        assertFalse(e.match(frase_falsa));
        assertTrue(e.match(frase_cierta));
    }

    @Test (expected = Exceptions.CaracterIlegalException.class)
    public void caracter_ilegal() throws Exception{
        new ExpBooleana("pepe | {juan albert mama papa} & hugo.aranda", 1);
    }

    @Test (expected = Exceptions.MalUsoOperandosException.class)
    public void mal_uso_operandos() throws Exception {
        new ExpBooleana("pepe | {juan albert mama papa} &", 1);
    }

    @Test (expected = Exceptions.MalParentesisException.class)
    public void mal_parentesis() throws Exception {
        new ExpBooleana("pepe) | {juan { albert mama papa} & (hola", 1);
    }

    @Test (expected = Exceptions.ComillasException.class)
    public void mal_comillas() throws Exception {
        new ExpBooleana("pepe | {juan albert mama papa} & \"hola", 1);
    }

}
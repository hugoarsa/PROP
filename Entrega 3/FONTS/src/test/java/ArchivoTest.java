import Domain.Archivo;
//package test.java.Domain;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArchivoTest {

    @Test
    public void TestArchivoYConsulta() {
        String titulo = "Pepito Grillo";
        String  autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        Archivo arc = new Archivo(titulo, autor, formato, content);;

        assertNotNull(arc);
        assertEquals(titulo, arc.consulta_titulo());
        assertEquals(autor, arc.consulta_autor());
        assertEquals(formato, arc.consulta_formato());
        assertEquals(content, arc.consulta_contenido());
    }

    @Test
    public void TestModificacion() {
        //Primero creamos un archivo con el que testear
        String titulo = "La tortuga";
        String autor = "Roberto";
        String formato = "xml";
        String content = "esto es un test";
        Archivo arc1 = new Archivo(titulo, autor, formato, content);

        //MODIFICAMOS TODOS LOS ATRIBUTOS
        String nuevo_titulo = "La tortuga rápida";
        String nuevo_autor = "Roberto";
        String nuevo_formato = "prop";
        String nuevo_content = "esto es un test de modificación";
        arc1.modificar(nuevo_titulo, nuevo_autor, nuevo_formato, nuevo_content);

        Archivo arc2 = new Archivo(nuevo_titulo, nuevo_autor, nuevo_formato, nuevo_content);

        assertEquals(arc2, arc1);
    }

}
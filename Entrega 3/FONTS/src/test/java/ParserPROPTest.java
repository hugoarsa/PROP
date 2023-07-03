import Persistence.GestorArchivo;
import Persistence.GestorIO;
import Persistence.ParserPROP;
import Domain.Archivo;

import org.junit.Test;
import org.junit.After;

import java.io.File;

import static org.junit.Assert.*;

public class ParserPROPTest {
    @Test
    public void TestCargarTXT() throws Exception{
        ParserPROP pars = new ParserPROP();
        Archivo arc2 = new Archivo("hugo", "aranda", "prop", "eres\nbobo\nputa\n");
        pars.guardar_archivo(arc2, "./data/Archivos/");
        Archivo arc;
        arc = pars.cargar_archivo("./data/Archivos/hugo@aranda.prop");

        assertEquals(arc.consulta_titulo(),arc2.consulta_titulo());
        assertEquals(arc.consulta_titulo(),arc2.consulta_titulo());
        assertEquals(arc.consulta_formato(),arc2.consulta_formato());
        assertEquals(arc.consulta_contenido(),arc2.consulta_contenido());
    }

    @Test
    public void TestGuardarTXT() throws Exception{
        ParserPROP pars = new ParserPROP();
        Archivo arc2 = new Archivo("titSave", "autSave", "prop", "contSave");
        pars.guardar_archivo(arc2, "./data/Archivos/");
    }

    @After
    public void ClearArchivos() throws Exception{
        File dir = new File("./data/Archivos/");
        File filesList[] = dir.listFiles();
        for(File file : filesList){
            file.delete();
        }
    }
}
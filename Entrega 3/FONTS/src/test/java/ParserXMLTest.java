import Persistence.ParserTXT;
import Persistence.ParserXML;
import Domain.Archivo;

import org.junit.Test;
import org.junit.After;

import java.io.File;

import static org.junit.Assert.*;

public class ParserXMLTest {
    String path = "./data/Archivos/";
    @Test
    public void TestCargarXML() throws Exception{
        ParserXML pars = new ParserXML();
        Archivo arc = new Archivo("Mort", "Terry Pratchett", "xml", "template");
        pars.guardar_archivo(arc, "./data/Archivos/");
        Archivo arc2 = pars.cargar_archivo(path + "Mort@Terry Pratchett.xml");
        System.out.println(arc2.consulta_titulo());
        System.out.println(arc2.consulta_autor());
        System.out.println(arc2.consulta_contenido());
        System.out.println(arc2.consulta_formato());
    }

    @Test
    public void TestGuardarXML() throws Exception{
        ParserXML pars = new ParserXML();
        Archivo arc2 = new Archivo("titSave", "autSave", "xml", "contSave");
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

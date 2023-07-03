import Persistence.GestorArchivo;
import Persistence.GestorIO;
import Persistence.ParserTXT;
import Domain.Archivo;

import org.junit.Test;
import org.junit.After;

import java.io.File;

import static org.junit.Assert.*;

public class ParserTXTTest {
    @Test
    public void TestCargarTXT() throws Exception{
        ParserTXT pars = new ParserTXT();
        Archivo arc2 = new Archivo("hugo", "aranda", "txt", "sanchez");
        pars.guardar_archivo(arc2, "./data/Archivos/");
        Archivo arc;
        arc = pars.cargar_archivo("./data/Archivos/hugo@aranda.txt");
        System.out.println(arc.consulta_titulo());
        System.out.println(arc.consulta_autor());
        System.out.println(arc.consulta_contenido());
        System.out.println(arc.consulta_formato());
    }

    @Test
    public void TestGuardarTXT() throws Exception{
        ParserTXT pars = new ParserTXT();
        Archivo arc2 = new Archivo("titSave", "autSave", "txt", "contSave");
        pars.guardar_archivo(arc2, "./data/Archivos/");
    }

    @Test
    public void TestGuardarTXT2() throws Exception{
        ParserTXT pars = new ParserTXT();
        Archivo arc2 = new Archivo("hugo", "aranda", "txt", "sanchez");
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
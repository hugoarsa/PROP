import Domain.Archivo;
import Domain.ArchivoCtrl;
import Domain.Corpus;
import Domain.Pair;
import Domain.ExpBooleana;
import Persistence.GestorArchivo;
import Persistence.GestorCorpus;
import org.junit.Test;
import org.junit.After;

import java.util.*;

import static org.junit.Assert.*;


public class ArchivoCtrlTest {
    @After
    public void ClearPersistence() throws Exception{
        GestorCorpus GC = new GestorCorpus();
        GC.clear_corpus();
        GestorArchivo GA = new GestorArchivo();
        GA.clear_archivos();
    }
    @Test
    public void TestAltaArchivo() throws Exception {
        ArchivoCtrl archivoCtrl = new ArchivoCtrl();
        String titulo = "Pepito Grillo";
        String autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        archivoCtrl.alta_archivo(titulo, autor, formato, content);

        assertTrue(archivoCtrl.existe_archivo(titulo, autor));
        assertTrue(archivoCtrl.existe_archivo_corpus(titulo, autor));
    }

    @Test
    public void TestModificarArchivoSinCambiarID() throws Exception {
        ArchivoCtrl archivoCtrl = new ArchivoCtrl();;
        String titulo = "Pepito Grillo";
        String autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        archivoCtrl.alta_archivo(titulo, autor, formato, content);

        String nuevo_titulo = titulo;
        String nuevo_autor = autor;
        String nuevo_formato = "txt";
        String nuevo_content = "otro test";
        archivoCtrl.editar_archivo(titulo, autor, nuevo_titulo, nuevo_autor, nuevo_content);
        archivoCtrl.cambiar_formato(nuevo_titulo, nuevo_autor, nuevo_formato);

        assertEquals(nuevo_formato, archivoCtrl.consulta_formato_archivo(nuevo_titulo, nuevo_autor));
        assertEquals(nuevo_content, archivoCtrl.consulta_contenido_archivo(nuevo_titulo, nuevo_autor));
        assertEquals(nuevo_formato, archivoCtrl.consulta_formato_archivo(nuevo_titulo, nuevo_autor));
        assertEquals(nuevo_content, archivoCtrl.consulta_contenido_archivo(nuevo_titulo, nuevo_autor));
    }

    @Test
    public void TestModificarArchivoCambioID() throws Exception {
        ArchivoCtrl archivoCtrl = new ArchivoCtrl();
        String titulo = "Pepito Grillo";
        String autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        archivoCtrl.alta_archivo(titulo, autor, formato, content);

        String nuevo_titulo = "Bertus Genio";
        String nuevo_autor = "Quevedo";
        String nuevo_formato = "txt";
        String nuevo_content = "otro test";
        archivoCtrl.editar_archivo(titulo, autor, nuevo_titulo, nuevo_autor, nuevo_content);
        archivoCtrl.cambiar_formato(nuevo_titulo, nuevo_autor, nuevo_formato);

        assertTrue(archivoCtrl.existe_archivo(nuevo_titulo, nuevo_autor));
        assertTrue(archivoCtrl.existe_archivo_corpus(nuevo_titulo, nuevo_autor));
        assertEquals(nuevo_formato, archivoCtrl.consulta_formato_archivo(nuevo_titulo, nuevo_autor));
        assertEquals(nuevo_content, archivoCtrl.consulta_contenido_archivo(nuevo_titulo, nuevo_autor));
    }

    @Test
    public void TestBorrarArchivo() throws Exception{
        ArchivoCtrl archivoCtrl = new ArchivoCtrl();
        String titulo = "Pepito Grillo";
        String autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        archivoCtrl.alta_archivo(titulo, autor, formato, content);

        archivoCtrl.borrar_archivo(titulo, autor);
        assertFalse(archivoCtrl.existe_archivo(titulo, autor));
        assertFalse(archivoCtrl.existe_archivo_corpus(titulo, autor));
    }

    @Test
    public void TestConsultaFormatoArchivo() throws Exception{
        ArchivoCtrl archivoCtrl = new ArchivoCtrl();
        String titulo = "Pepito Grillo";
        String autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        archivoCtrl.alta_archivo(titulo, autor, formato, content);

        assertEquals(formato, archivoCtrl.consulta_formato_archivo(titulo, autor));
    }

    @Test
    public void TestConsultaContenidoArchivo() throws Exception{
        ArchivoCtrl archivoCtrl = new ArchivoCtrl();
        String titulo = "Pepito Grillo";
        String autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        archivoCtrl.alta_archivo(titulo, autor, formato, content);

        assertEquals(content, archivoCtrl.consulta_contenido_archivo(titulo, autor));
    }

    @Test
    public void TestListarPorAutor() throws Exception{
        ArchivoCtrl archivoCtrl = new ArchivoCtrl();
        String titulo = "Pepito Grillo";
        String autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        archivoCtrl.alta_archivo(titulo, autor, formato, content);
        Archivo a1 = archivoCtrl.get_archivo(titulo, autor);
        Pair<String, String> archivo1 = new Pair<String, String>(a1.consulta_titulo(), a1.consulta_autor());

        String titulo2 = "Bertus Genio";
        String autor2 = "Quevedo";
        String formato2 = "txt";
        String content2 = "otro test";
        archivoCtrl.alta_archivo(titulo2, autor2, formato2, content2);
        Archivo a2 = archivoCtrl.get_archivo(titulo2, autor2);
        Pair<String, String> archivo2 = new Pair<String, String>(a2.consulta_titulo(), a2.consulta_autor());

        String titulo3 = "Vista Al Mar";
        String formato3 = "txt";
        String content3 = "Rapido llegas y rapido te vas";
        archivoCtrl.alta_archivo(titulo3, autor2, formato3, content3);
        Archivo a3 = archivoCtrl.get_archivo(titulo3, autor2);
        Pair<String, String> archivo3 = new Pair<String, String>(a3.consulta_titulo(), a3.consulta_autor());

        ArrayList<Pair<String, String>> lista = archivoCtrl.listar_por_autor(autor2);
        assertFalse(lista.contains(archivo1));
        assertTrue(lista.contains(archivo2));
        assertTrue(lista.contains(archivo3));
    }

    @Test
    public void TestListarPorTitulo() throws Exception {
        ArchivoCtrl archivoCtrl = new ArchivoCtrl();
        String titulo = "Pepito Grillo";
        String autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        archivoCtrl.alta_archivo(titulo, autor, formato, content);
        Archivo a1 = archivoCtrl.get_archivo(titulo, autor);
        Pair<String, String> archivo1 = new Pair<String, String>(a1.consulta_titulo(), a1.consulta_autor());

        String titulo2 = "Bertus Genio";
        String autor2 = "Quevedo";
        String formato2 = "txt";
        String content2 = "otro test";
        archivoCtrl.alta_archivo(titulo2, autor2, formato2, content2);
        Archivo a2 = archivoCtrl.get_archivo(titulo2, autor2);
        Pair<String, String> archivo2 = new Pair<String, String>(a2.consulta_titulo(), a2.consulta_autor());

        String titulo3 = "Bertus Guapo";
        String formato3 = "txt";
        String content3 = "Rapido llegas y rapido te vas";
        archivoCtrl.alta_archivo(titulo3, autor2, formato3, content3);
        Archivo a3 = archivoCtrl.get_archivo(titulo3, autor2);
        Pair<String, String> archivo3 = new Pair<String, String>(a3.consulta_titulo(), a3.consulta_autor());

        ArrayList<Pair<String, String>> lista = archivoCtrl.listar_por_titulo("Bertus");
        assertFalse(lista.contains(archivo1));
        assertTrue(lista.contains(archivo2));
        assertTrue(lista.contains(archivo3));
    }

    @Test
    public void TestListarPorSemejanzaWeightMode0() throws Exception {
        ArchivoCtrl archivoCtrl = new ArchivoCtrl();
        String titulo = "Pepito Grillo";
        String autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        archivoCtrl.alta_archivo(titulo, autor, formato, content);

        String titulo2 = "Bertus Genio";
        String autor2 = "Quevedo";
        String formato2 = "txt";
        String content2 = "otro test";
        archivoCtrl.alta_archivo(titulo2, autor2, formato2, content2);
        Archivo a2 = archivoCtrl.get_archivo(titulo2, autor2);
        Pair<String, String> archivo2 = new Pair<String, String>(a2.consulta_titulo(), a2.consulta_autor());

        String titulo3 = "Vista Al Mar";
        String formato3 = "txt";
        String content3 = "Rapido llegas y rapido te vas";
        archivoCtrl.alta_archivo(titulo3, autor2, formato3, content3);
        Archivo a3 = archivoCtrl.get_archivo(titulo3, autor2);
        Pair<String, String> archivo3 = new Pair<String, String>(a3.consulta_titulo(), a3.consulta_autor());

        ArrayList<Pair<String, String>> lista = archivoCtrl.listar_por_semejanza(titulo, autor, 1, 0);
        assertTrue(lista.contains(archivo2));
        assertFalse(lista.contains(archivo3));
    }

    @Test
    public void TestListarPorSemejanzaWeightMode1() throws Exception{
        ArchivoCtrl archivoCtrl = new ArchivoCtrl();
        String titulo = "Pepito Grillo";
        String autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        archivoCtrl.alta_archivo(titulo, autor, formato, content);

        String titulo2 = "Bertus Genio";
        String autor2 = "Quevedo";
        String formato2 = "txt";
        String content2 = "otro test";
        archivoCtrl.alta_archivo(titulo2, autor2, formato2, content2);
        Archivo a2 = archivoCtrl.get_archivo(titulo2, autor2);
        Pair<String, String> archivo2 = new Pair<String, String>(a2.consulta_titulo(), a2.consulta_autor());

        String titulo3 = "Vista Al Mar";
        String formato3 = "txt";
        String content3 = "Rapido llegas y rapido te vas";
        archivoCtrl.alta_archivo(titulo3, autor2, formato3, content3);
        Archivo a3 = archivoCtrl.get_archivo(titulo3, autor2);
        Pair<String, String> archivo3 = new Pair<String, String>(a3.consulta_titulo(), a3.consulta_autor());

        ArrayList<Pair<String, String>> lista = archivoCtrl.listar_por_semejanza(titulo, autor, 1, 1);
        assertTrue(lista.contains(archivo2));
        assertFalse(lista.contains(archivo3));
    }

    @Test
    public void TestListarPorQuery() throws Exception{
        ArchivoCtrl archivoCtrl = new ArchivoCtrl();
        String titulo = "Pepito Grillo";
        String autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        archivoCtrl.alta_archivo(titulo, autor, formato, content);
        Archivo a1 = archivoCtrl.get_archivo(titulo, autor);
        Pair<String, String> archivo1 = new Pair<String, String>(a1.consulta_titulo(), a1.consulta_autor());

        assertTrue(archivoCtrl.existe_archivo(titulo, autor));
        assertTrue(archivoCtrl.existe_archivo_corpus(titulo, autor));

        String titulo2 = "Bertus Genio";
        String autor2 = "Quevedo";
        String formato2 = "txt";
        String content2 = "otro test";
        archivoCtrl.alta_archivo(titulo2, autor2, formato2, content2);
        Archivo a2 = archivoCtrl.get_archivo(titulo2, autor2);
        Pair<String, String> archivo2 = new Pair<String, String>(a2.consulta_titulo(), a2.consulta_autor());

        assertTrue(archivoCtrl.existe_archivo(titulo2, autor2));
        assertTrue(archivoCtrl.existe_archivo_corpus(titulo2, autor2));

        String titulo3 = "Vista Al Mar";
        String formato3 = "txt";
        String content3 = "Rapido llegas y rapido te vas";
        archivoCtrl.alta_archivo(titulo3, autor2, formato3, content3);
        Archivo a3 = archivoCtrl.get_archivo(titulo3, autor2);
        Pair<String, String> archivo3 = new Pair<String, String>(a3.consulta_titulo(), a3.consulta_autor());

        assertTrue(archivoCtrl.existe_archivo(titulo3, autor2));
        assertTrue(archivoCtrl.existe_archivo_corpus(titulo3, autor2));

        ArrayList<Pair<String, String>> lista = archivoCtrl.listar_por_query("un test", 2, 0);
        assertTrue(lista.contains(archivo1));
        assertTrue(lista.contains(archivo2));
        assertFalse(lista.contains(archivo3));
    }

    @Test
    public void TestListarPorBool() throws Exception{
        ArchivoCtrl archivoCtrl = new ArchivoCtrl();
        String titulo = "Pepito Grillo";
        String autor = "Aleee";
        String formato = "xml";
        String content = "esto es un test";
        archivoCtrl.alta_archivo(titulo, autor, formato, content);
        Archivo a1 = archivoCtrl.get_archivo(titulo, autor);
        Pair<String, String> archivo1 = new Pair<String, String>(a1.consulta_titulo(), a1.consulta_autor());

        String titulo2 = "Bertus Genio";
        String autor2 = "Quevedo";
        String formato2 = "txt";
        String content2 = "otro test";
        archivoCtrl.alta_archivo(titulo2, autor2, formato2, content2);
        Archivo a2 = archivoCtrl.get_archivo(titulo2, autor2);
        Pair<String, String> archivo2 = new Pair<String, String>(a2.consulta_titulo(), a2.consulta_autor());

        String titulo3 = "Vista Al Mar";
        String formato3 = "txt";
        String content3 = "Rapido llegas y rapido te vas";
        archivoCtrl.alta_archivo(titulo3, autor2, formato3, content3);
        Archivo a3 = archivoCtrl.get_archivo(titulo3, autor2);
        Pair<String, String> archivo3 = new Pair<String, String>(a3.consulta_titulo(), a3.consulta_autor());

        ExpBooleana e = new ExpBooleana("test & (ot | to) & !y", 1);
        ArrayList<Pair<String, String>> lista = archivoCtrl.listar_por_bool(e);
        assertTrue(lista.contains(archivo1));
        assertTrue(lista.contains(archivo2));
        assertFalse(lista.contains(archivo3));
    }
}

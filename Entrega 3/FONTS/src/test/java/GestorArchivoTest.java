import Domain.Archivo;
import Domain.ArchivoCtrl;
import Domain.Pair;
import Persistence.GestorArchivo;
//package test.java.Domain;
import org.junit.Test;
import org.junit.After;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class GestorArchivoTest {

    @Test
    public void TestGuardarYCargarArchivos() throws Exception{
        //FUNCIONA !!!!!!
        Archivo arch1 = new Archivo("The hobbit 1","JRR Tolkien","txt","In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, filled with the\n" +
                "ends of worms and an oozy smell, nor yet a dry, bare, sandy hole with nothing in it to sit\n" +
                "down on or to eat: it was a hobbit-hole, and that means comfort. \n");
        Archivo arch2 = new Archivo("The hobbit 2","JRR Tolkien","xml","Up jumped Bilbo, and putting on his dressing-gown went into the diningroom.\n" +
                "There he saw nobody, but all the signs of a large and hurried breakfast.\n" +
                "There was a fearful mess in the room, and piles of unwashed crocks in the kitchen. \n");
        Archivo arch3 = new Archivo("The hobbit 3","JRR Tolkien","prop","They did not sing or tell stories that day, even though the weather improved; nor the next\n" +
                "day, nor the day after. They had begun to feel that danger was not far away on either\n" +
                "side.");
        GestorArchivo GA = new GestorArchivo();
        GA.guardar_archivo(arch1);
        GA.guardar_archivo(arch2);
        GA.guardar_archivo(arch3);

        Archivo arch4 = GA.cargar_archivo("The hobbit 1","JRR Tolkien","txt");
        Archivo arch5 = GA.cargar_archivo("The hobbit 2","JRR Tolkien","xml");
        Archivo arch6 = GA.cargar_archivo("The hobbit 3","JRR Tolkien","prop");

        assertEquals(arch4.consulta_titulo(),arch1.consulta_titulo());
        assertEquals(arch4.consulta_titulo(),arch1.consulta_titulo());
        assertEquals(arch4.consulta_formato(),arch1.consulta_formato());
        assertEquals(arch4.consulta_contenido(),arch1.consulta_contenido());

        assertEquals(arch5.consulta_titulo(),arch2.consulta_titulo());
        assertEquals(arch5.consulta_titulo(),arch2.consulta_titulo());
        assertEquals(arch5.consulta_formato(),arch2.consulta_formato());
        assertEquals(arch5.consulta_contenido(),arch2.consulta_contenido());

        assertEquals(arch6.consulta_titulo(),arch3.consulta_titulo());
        assertEquals(arch6.consulta_titulo(),arch3.consulta_titulo());
        assertEquals(arch6.consulta_formato(),arch3.consulta_formato());
        assertEquals(arch6.consulta_contenido(),arch3.consulta_contenido());
    }

    @Test
    public void TestGuardarCargarYBorrarArchivos() throws Exception{
        //FUNCIONA !!!!!!
        Archivo arch1 = new Archivo("The hobbit 1","JRR Tolkien","txt","In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, filled with the\n" +
                "ends of worms and an oozy smell, nor yet a dry, bare, sandy hole with nothing in it to sit\n" +
                "down on or to eat: it was a hobbit-hole, and that means comfort. \n");
        Archivo arch2 = new Archivo("The hobbit 2","JRR Tolkien","xml","Up jumped Bilbo, and putting on his dressing-gown went into the diningroom.\n" +
                "There he saw nobody, but all the signs of a large and hurried breakfast.\n" +
                "There was a fearful mess in the room, and piles of unwashed crocks in the kitchen. \n");
        GestorArchivo GA = new GestorArchivo();
        GA.guardar_archivo(arch1);
        GA.guardar_archivo(arch2);
        GA.eliminar_archivo("The hobbit 2","JRR Tolkien","xml");

        //cojo todos los ids
        Map<Pair<String, String>,Pair<Archivo, Boolean>> AC = GA.get_all_ids();

        //creo el set esperado
        Set<Pair<String, String>> S = new HashSet<Pair<String, String>>();

        S.add(new Pair<>("The hobbit 1","JRR Tolkien"));

        //hago las comparaciones pertinentes
        assertEquals(AC.keySet(),S);
    }

    @Test
    public void TestGetAll() throws Exception{
        //meto dos archivos
        Archivo arch1 = new Archivo("The hobbit 1","JRR Tolkien","txt","In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, filled with the\n" +
                "ends of worms and an oozy smell, nor yet a dry, bare, sandy hole with nothing in it to sit\n" +
                "down on or to eat: it was a hobbit-hole, and that means comfort. \n");
        Archivo arch2 = new Archivo("The hobbit 2","JRR Tolkien","xml","Up jumped Bilbo, and putting on his dressing-gown went into the diningroom.\n" +
                "There he saw nobody, but all the signs of a large and hurried breakfast.\n" +
                "There was a fearful mess in the room, and piles of unwashed crocks in the kitchen. \n");
        GestorArchivo GA = new GestorArchivo();
        GA.guardar_archivo(arch1);
        GA.guardar_archivo(arch2);

        //cojo todos los ids
        Map<Pair<String, String>,Pair<Archivo, Boolean>> AC = GA.get_all_ids();

        //creo el set esperado
        Set<Pair<String, String>> S = new HashSet<Pair<String, String>>();

        S.add(new Pair<>("The hobbit 1","JRR Tolkien"));
        S.add(new Pair<>("The hobbit 2","JRR Tolkien"));

        //Creo un archivo sin contenido de prototipo
        Archivo A = new Archivo("The hobbit 2","JRR Tolkien","xml","");

        //hago las comparaciones pertinentes
        assertEquals(AC.keySet(),S);
        assertEquals(AC.get(new Pair<>("The hobbit 2","JRR Tolkien")).first().consulta_contenido(),A.consulta_contenido());
        assertEquals(AC.get(new Pair<>("The hobbit 2","JRR Tolkien")).second(),false);
    }

    @Test
    public void ExportarArchivoTest() throws Exception{
        GestorArchivo GA = new GestorArchivo();
        Archivo a = new Archivo("ExportarTEST", "PROPguapos", "txt", "esto es un test");
        GA.exportar_archivo(a, "C:\\Users\\Bertus\\Desktop" + "/");
    }

    @After
    public void ClearBoolSerial() throws Exception{
        GestorArchivo GA = new GestorArchivo();
        GA.clear_archivos();
    }
}
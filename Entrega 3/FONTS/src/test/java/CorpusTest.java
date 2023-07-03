import Domain.Corpus;
import Domain.VectorMEV;
//package test.java.Domain;
import Exceptions.*;
import Persistence.GestorCorpus;
import org.junit.Test;
import org.junit.After;
import java.util.*;

import static org.junit.Assert.*;

public class CorpusTest {
    String texto1 = "He found his art never progressed when he literally " +
            "used his sweat and tears. Clyde blue enemies flash dots wocka " +
            "maze monsters ghosts red chaser. Ghosts dots cherry Blinky Pac-Man " +
            "Power Pellets fruit. She had the gift of being able to paint songs. " +
            "He had a hidden stash underneath the floorboards in the back room of the house.";
    String texto2 = "Ghosts dots cherry Blinky Pac-Man Power Pellets fruit. " +
            "He was willing to find the depths of the rabbit hole in order to be with her. " +
            "Pac-Man Namco Toru Iwatani Pac-Man Fever maze dots.";
    String texto3 = "He had a hidden stash underneath the floorboards in the back room of the house. " +
            "The beauty of the sunset was obscured by the industrial cranes. " +
            "Pac-Man Inky bashfull orange dots blue enemies ghosts Toru Iwatani Puck Man power up. " +
            "Fluffy pink unicorns are a popular status symbol among macho men. " +
            "As she walked along the street and looked in the gutter, " +
            "she realized facemasks had become the new cigarette butts.";
    String texto4 = "She was disgusted he couldn’t tell the difference between lemonade and limeade. " +
            "He had a hidden stash underneath the floorboards in the back room of the house. " +
            "Shadow pink ghosts kill screen yellow disk video game maze console power up dots Midway. " +
            "He found his art never progressed when he literally used his sweat and tears. " +
            "Fluffy pink unicorns are a popular status symbol among macho men.";

    @After
    public void ClearPersistence() throws Exception{
        GestorCorpus GC = new GestorCorpus();
        GC.clear_corpus();
    }

    @Test
    public void TestAnadirArchivos() throws Exception{

        Corpus corpus = new Corpus();
        assertNotNull(corpus);
        assertFalse(corpus.existe_archivo("test1", "autor1"));
        assertFalse(corpus.existe_archivo("test2", "autor2"));
        assertFalse(corpus.existe_archivo("test3", "autor3"));
        assertFalse(corpus.existe_archivo("test4", "autor4"));
        corpus.anadir_archivo("test1", "autor1", texto1);
        corpus.anadir_archivo("test2", "autor2", texto2);
        corpus.anadir_archivo("test3", "autor3", texto3);
        corpus.anadir_archivo("test4", "autor4", texto4);
        assertTrue(corpus.existe_archivo("test1", "autor1"));
        assertTrue(corpus.existe_archivo("test2", "autor2"));
        assertTrue(corpus.existe_archivo("test3", "autor3"));
        assertTrue(corpus.existe_archivo("test4", "autor4"));
    }

    @Test(expected=ArchivoYaExisteException.class)
    public void TestArchivoYaExisteException() throws Exception{
        Corpus corpus = new Corpus();
        assertNotNull(corpus);
        assertFalse(corpus.existe_archivo("test1", "autor1"));
        assertFalse(corpus.existe_archivo("test2", "autor2"));
        corpus.anadir_archivo("test1", "autor1", texto1);
        corpus.anadir_archivo("test2", "autor2", texto2);
        assertTrue(corpus.existe_archivo("test1", "autor1"));
        assertTrue(corpus.existe_archivo("test2", "autor2"));
        corpus.anadir_archivo("test2", "autor2", texto3);
    }

    @Test
    public void TestAnadirYBorrarArchivos() throws Exception{

        Corpus corpus = new Corpus();
        assertNotNull(corpus);
        assertFalse(corpus.existe_archivo("test1", "autor1"));
        assertFalse(corpus.existe_archivo("test2", "autor2"));
        assertFalse(corpus.existe_archivo("test3", "autor3"));
        assertFalse(corpus.existe_archivo("test4", "autor4"));
        corpus.anadir_archivo("test1", "autor1", texto1);
        corpus.anadir_archivo("test2", "autor2", texto2);
        corpus.anadir_archivo("test3", "autor3", texto3);
        corpus.anadir_archivo("test4", "autor4", texto4);
        assertTrue(corpus.existe_archivo("test1", "autor1"));
        assertTrue(corpus.existe_archivo("test2", "autor2"));
        assertTrue(corpus.existe_archivo("test3", "autor3"));
        assertTrue(corpus.existe_archivo("test4", "autor4"));
        corpus.borrar_archivo("test1", "autor1");
        corpus.borrar_archivo("test2", "autor2");
        corpus.borrar_archivo("test3", "autor3");
        assertFalse(corpus.existe_archivo("test1", "autor1"));
        assertFalse(corpus.existe_archivo("test2", "autor2"));
        assertFalse(corpus.existe_archivo("test3", "autor3"));
        assertTrue(corpus.existe_archivo("test4", "autor4"));
    }

    @Test(expected=ArchivoNoExisteException.class)
    public void TestArchivoNoExisteException() throws Exception{
        Corpus corpus = new Corpus();
        assertNotNull(corpus);
        corpus.borrar_archivo("titulo", "autor");
    }
    @Test
    public void TestCompararArchivosBothStrategies() throws Exception{

        Corpus corpus = new Corpus();
        assertNotNull(corpus);
        corpus.anadir_archivo("test1", "autor1", texto1);
        corpus.anadir_archivo("test2", "autor2", texto2);
        corpus.anadir_archivo("test3", "autor3", texto3);
        corpus.anadir_archivo("test4", "autor4", texto4);
        assertEquals(0.5618691197126674, corpus.comparar_archivos("test1", "autor1", "test2","autor2",0), 0.00001);
        assertEquals(0.4777272810644298, corpus.comparar_archivos("test3", "autor3", "test4", "autor4",0), 0.00001);
        assertEquals(1.0, corpus.comparar_archivos("test1", "autor1", "test1","autor1",0), 0.00001);
        assertEquals(0.0, corpus.comparar_archivos("test2", "autor2", "test3", "autor3",0), 0.00001);
        assertEquals(0.5240763721439711, corpus.comparar_archivos("test1", "autor1", "test2","autor2",1), 0.00001);
        assertEquals(0.46560419968497957, corpus.comparar_archivos("test3", "autor3", "test4", "autor4",1), 0.00001);
        assertEquals(1.0, corpus.comparar_archivos("test1", "autor1", "test1","autor1",1), 0.00001);
        assertEquals(0.0, corpus.comparar_archivos("test2", "autor2", "test3", "autor3",1), 0.00001);

    }

    @Test
    public void TestCompararQueryBothStrategies() throws Exception{

        Corpus corpus = new Corpus();
        assertNotNull(corpus);
        corpus.anadir_archivo("test1", "autor1", texto1);
        corpus.anadir_archivo("test2", "autor2", texto2);
        corpus.anadir_archivo("test3", "autor3", texto3);
        corpus.anadir_archivo("test4", "autor4", texto4);
        String query = "This file talks about Pac-Man, about how he eats Power Pellets fruit and defeats the" +
                "four ghosts that chase him through the depths of the maze: Inky, Blinky, Pinky and Clyde.";
        assertEquals(0.3979804537244554, corpus.comparar_query("test1", "autor1", query,0), 0.00001);
        assertEquals(0.3308913285236342, corpus.comparar_query("test1", "autor1", query,1), 0.00001);
        assertEquals(0.0, corpus.comparar_query("test3", "autor3", query,0), 0.00001);
        assertEquals(0.0, corpus.comparar_query("test3", "autor3", query,1), 0.00001);
        assertEquals(1.0, corpus.comparar_query("test3", "autor3", texto3,0), 0.00001);
        assertEquals(1.0, corpus.comparar_query("test3", "autor3", texto3,1), 0.00001);

    }

}
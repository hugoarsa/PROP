import Domain.*;
import Persistence.*;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DomainCtrlTest {

    @After
    public void clearDomain() throws Exception{
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        domainCtrl.clearCache();
    }

    @After
    public void clearPersistence() throws Exception{
        GestorUsuario GU = new GestorUsuario();
        GU.clear_usuarios();
        GestorAutor GA = new GestorAutor();
        GA.clear_autores();
        GestorBooleana GB = new GestorBooleana();
        GB.clear_expresiones();
        GestorCorpus GC = new GestorCorpus();
        GC.clear_corpus();
        GestorArchivo GArc = new GestorArchivo();
        GArc.clear_archivos();
    }
    //Tests GESTION USUARIOS
    @Test
    public void CrearYExistePerfilTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        assertNotNull(domainCtrl);

        String username = "huguitoBonito";
        String contrasena1 = "gatitos4ever";
        String contrasena2 = "gatitos4ever";
        String nombre = "Hugo";
        domainCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);

        assertTrue(domainCtrl.verificar_perfil(username, contrasena1));
    }

    @Test
    public void ModificarPerfilTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String username = "huguitoBonito";
        String contrasena1 = "gatitos4ever";
        String contrasena2 = "gatitos4ever";
        String nombre = "Hugo";
        domainCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);

        String nuevo_username = "huguitoPrecioso";
        String nuevo_nombre = "Hugo Aranda";
        domainCtrl.modificar_perfil(username, nuevo_username, nuevo_nombre);

        assertTrue(domainCtrl.verificar_perfil(nuevo_username, contrasena1));
    }

    @Test
    public void CambiarContrasenaTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String username = "huguitoBonito";
        String contrasena1 = "gatitos4ever";
        String contrasena2 = "gatitos4ever";
        String nombre = "Hugo";
        domainCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);

        String nueva_contrasena = "Haru4ever";
        domainCtrl.cambiar_contrasena(username, contrasena1, nueva_contrasena);

        assertTrue(domainCtrl.verificar_perfil(username, nueva_contrasena));
    }
    @Test
    public void BorrarPerfilTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String username = "huguitoBonito";
        String contrasena = "gatitos4ever";
        String nombre = "Hugo";
        domainCtrl.crear_perfil(username, contrasena, contrasena, nombre);

        domainCtrl.borrar_perfil(username, contrasena);
    }

    @Test
    public void VerificarPerfilTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String username = "huguitoBonito";
        String contrasena1 = "gatitos4ever";
        String contrasena2 = "gatitos4ever";
        String nombre = "Hugo";
        domainCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);

        assertTrue(domainCtrl.verificar_perfil(username, contrasena1));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Tests GESTION ARCHIVOS
    @Test
    public void AltaArchivoYConsultasTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String titulo = "Ruso para dummies";
        String autor = "Bertus";
        String formato = "txt";
        String contenido = "Me golpeé la cabeza y ahora estoy aprendiendo ruso";
        domainCtrl.alta_archivo(titulo, autor, formato, contenido);

        assertTrue(domainCtrl.existe_archivo(titulo, autor));
        assertTrue(domainCtrl.existe_archivo_corpus(titulo, autor));

        //Depuramos
        domainCtrl.borrar_archivo(titulo, autor);
    }

    @Test
    public void ModificarArchivoSinCambiarIDTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String titulo = "Ruso para dummies";
        String autor = "Bertus";
        String formato = "txt";
        String contenido = "Me golpeé la cabeza y ahora estoy aprendiendo ruso";
        domainCtrl.alta_archivo(titulo, autor, formato, contenido);

        String nuevo_titulo = titulo;
        String nuevo_autor = autor;
        String nuevo_formato = "xml";
        String nuevo_contenido = "Me golpeé la cabeza y ahora hablo ruso";
        domainCtrl.editar_archivo(titulo, autor, nuevo_titulo, nuevo_autor, nuevo_contenido);
        domainCtrl.cambiar_formato_archivo(nuevo_titulo, nuevo_autor, nuevo_formato);

        assertTrue(domainCtrl.existe_archivo(nuevo_titulo, nuevo_autor));
        assertTrue(domainCtrl.existe_archivo_corpus(nuevo_titulo, nuevo_autor));
        assertEquals(nuevo_formato, domainCtrl.consulta_formato_archivo(nuevo_titulo, nuevo_autor));
        assertEquals(nuevo_contenido, domainCtrl.consulta_contenido_archivo(nuevo_titulo, nuevo_autor));

        //Depuramos
        domainCtrl.borrar_archivo(nuevo_titulo, nuevo_autor);
    }

    @Test
    public void ModificarArchivoCambioIDTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String titulo = "Ruso para dummies";
        String autor = "Bertus";
        String formato = "txt";
        String contenido = "Me golpeé la cabeza y ahora estoy aprendiendo ruso";
        domainCtrl.alta_archivo(titulo, autor, formato, contenido);

        String nuevo_titulo = "Ruso avanzado";
        String nuevo_autor = "Joan";
        String nuevo_formato = "xml";
        String nuevo_contenido = "Ya tengo el B1 de ruso";
        domainCtrl.editar_archivo(titulo, autor, nuevo_titulo, nuevo_autor, nuevo_contenido);
        domainCtrl.cambiar_formato_archivo(nuevo_titulo, nuevo_autor, nuevo_formato);

        assertTrue(domainCtrl.existe_archivo(nuevo_titulo, nuevo_autor));
        assertTrue(domainCtrl.existe_archivo_corpus(nuevo_titulo, nuevo_autor));
        assertEquals(nuevo_formato, domainCtrl.consulta_formato_archivo(nuevo_titulo, nuevo_autor));
        assertEquals(nuevo_contenido, domainCtrl.consulta_contenido_archivo(nuevo_titulo, nuevo_autor));

        //Depuramos
        domainCtrl.borrar_archivo(nuevo_titulo, nuevo_autor);
    }

    @Test
    public void BorrarArchivoTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String titulo = "Ruso para dummies";
        String autor = "Bertus";
        String formato = "txt";
        String contenido = "Me golpeé la cabeza y ahora estoy aprendiendo ruso";
        domainCtrl.alta_archivo(titulo, autor, formato, contenido);

        domainCtrl.borrar_archivo(titulo, autor);
        assertFalse(domainCtrl.existe_archivo(titulo, autor));
        assertFalse(domainCtrl.existe_archivo_corpus(titulo, autor));
    }

    @Test
    public void ListarAutoresTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        Set<String> autores_comp = new HashSet<String>();
        String titulo = "Ruso para dummies";
        String autor = "Bertus";
        String formato = "txt";
        String contenido = "Me golpeé la cabeza y ahora estoy aprendiendo ruso";
        domainCtrl.alta_archivo(titulo, autor, formato, contenido);

        autores_comp.add(autor);
        Set<String> aut = domainCtrl.listar_autores("");
        assertEquals(autores_comp, aut);

        //Depuramos
        domainCtrl.borrar_archivo(titulo, autor);
    }

    @Test
    public void ListarPorAutorTest() throws Exception{
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String titulo1 = "La Bella y la Bestia";
        String autor1 = "Juanito";
        String formato = "txt";
        String contenido1 = "Esto es un cuento de Disney";
        domainCtrl.alta_archivo(titulo1, autor1, formato, contenido1);
        Archivo a1 = domainCtrl.get_archivo(titulo1, autor1);
        Pair<String, String> archivo1 = new Pair<String, String>(a1.consulta_titulo(), a1.consulta_autor());

        String titulo2 = "Cenicienta";
        String contenido2 = "Cuento de Disney";
        domainCtrl.alta_archivo(titulo2, autor1, formato, contenido2);
        Archivo a2 = domainCtrl.get_archivo(titulo2, autor1);
        Pair<String, String> archivo2 = new Pair<String, String>(a2.consulta_titulo(), a2.consulta_autor());

        String titulo3 = "PROP";
        String autor2 = "Carles";
        String contenido3 = "Esto es un assignatura obligatoria del grado de ingeniería informática en la FIB";
        domainCtrl.alta_archivo(titulo3, autor2, formato, contenido3);
        Archivo a3 = domainCtrl.get_archivo(titulo3, autor2);
        Pair<String, String> archivo3 = new Pair<String, String>(a3.consulta_titulo(), a3.consulta_autor());

        ArrayList<Pair<String, String>> lista = domainCtrl.listar_por_autor(autor1);
        assertTrue(lista.contains(archivo1));
        assertTrue(lista.contains(archivo2));
        assertFalse(lista.contains(archivo3));

        //Depuramos
        domainCtrl.borrar_archivo(titulo1, autor1);
        domainCtrl.borrar_archivo(titulo2, autor1);
        domainCtrl.borrar_archivo(titulo3, autor2);
    }

    @Test
    public void ListarPorTituloTest()  throws Exception{
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String titulo1 = "La Bella y la Bestia";
        String autor1 = "Juanito";
        String formato = "txt";
        String contenido1 = "Esto es un cuento de Disney";
        domainCtrl.alta_archivo(titulo1, autor1, formato, contenido1);
        Archivo a1 = domainCtrl.get_archivo(titulo1, autor1);
        Pair<String, String> archivo1 = new Pair<String, String>(a1.consulta_titulo(), a1.consulta_autor());

        String titulo2 = "Cenicienta";
        String contenido2 = "Cuento de Disney";
        domainCtrl.alta_archivo(titulo2, autor1, formato, contenido2);
        Archivo a2 = domainCtrl.get_archivo(titulo2, autor1);
        Pair<String, String> archivo2 = new Pair<String, String>(a2.consulta_titulo(), a2.consulta_autor());

        String titulo3 = "PROP";
        String autor2 = "Carles";
        String contenido3 = "Esto es un assignatura obligatoria del grado de ingeniería informática en la FIB";
        domainCtrl.alta_archivo(titulo3, autor2, formato, contenido3);
        Archivo a3 = domainCtrl.get_archivo(titulo3, autor2);
        Pair<String, String> archivo3 = new Pair<String, String>(a3.consulta_titulo(), a3.consulta_autor());

        ArrayList<Pair<String, String>> lista = domainCtrl.listar_por_titulo("Cenicienta");
        assertFalse(lista.contains(archivo1));
        assertTrue(lista.contains(archivo2));
        assertFalse(lista.contains(archivo3));

        //Depuramos
        domainCtrl.borrar_archivo(titulo1, autor1);
        domainCtrl.borrar_archivo(titulo2, autor1);
        domainCtrl.borrar_archivo(titulo3, autor2);
    }

    @Test
    public void ListarPorSemejanzaTest() throws Exception{
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String titulo1 = "La Bella y la Bestia";
        String autor1 = "Juanito";
        String formato = "txt";
        String contenido1 = "Esto es un cuento de Disney";
        domainCtrl.alta_archivo(titulo1, autor1, formato, contenido1);
        Archivo a1 = domainCtrl.get_archivo(titulo1, autor1);
        Pair<String, String> archivo1 = new Pair<String, String>(a1.consulta_titulo(), a1.consulta_autor());

        String titulo2 = "Cenicienta";
        String contenido2 = "Cuento de Disney";
        domainCtrl.alta_archivo(titulo2, autor1, formato, contenido2);
        Archivo a2 = domainCtrl.get_archivo(titulo2, autor1);
        Pair<String, String> archivo2 = new Pair<String, String>(a2.consulta_titulo(), a2.consulta_autor());

        String titulo3 = "PROP";
        String autor2 = "Carles";
        String contenido3 = "Esto es un assignatura obligatoria del grado de ingeniería informática en la FIB";
        domainCtrl.alta_archivo(titulo3, autor2, formato, contenido3);
        Archivo a3 = domainCtrl.get_archivo(titulo3, autor2);
        Pair<String, String> archivo3 = new Pair<String, String>(a3.consulta_titulo(), a3.consulta_autor());

        ArrayList<Pair<String, String>> lista = domainCtrl.listar_por_semejanza(titulo1, autor1, 1, 0);
        assertTrue(lista.contains(archivo2));
        assertFalse(lista.contains(archivo3));

        //Depuramos
        domainCtrl.borrar_archivo(titulo1, autor1);
        domainCtrl.borrar_archivo(titulo2, autor1);
        domainCtrl.borrar_archivo(titulo3, autor2);
    }

    @Test
    public void ListarPorQueryTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String titulo1 = "La Bella y la Bestia";
        String autor1 = "Juanito";
        String formato = "txt";
        String contenido1 = "Esto es un cuento de Disney";
        domainCtrl.alta_archivo(titulo1, autor1, formato, contenido1);
        Archivo a1 = domainCtrl.get_archivo(titulo1, autor1);
        Pair<String, String> archivo1 = new Pair<String, String>(a1.consulta_titulo(), a1.consulta_autor());

        String titulo2 = "Cenicienta";
        String contenido2 = "Cuento de Disney";
        domainCtrl.alta_archivo(titulo2, autor1, formato, contenido2);
        Archivo a2 = domainCtrl.get_archivo(titulo2, autor1);
        Pair<String, String> archivo2 = new Pair<String, String>(a2.consulta_titulo(), a2.consulta_autor());

        String titulo3 = "PROP";
        String autor2 = "Carles";
        String contenido3 = "Esto es un assignatura obligatoria del grado de ingeniería informática en la FIB";
        domainCtrl.alta_archivo(titulo3, autor2, formato, contenido3);
        Archivo a3 = domainCtrl.get_archivo(titulo3, autor2);
        Pair<String, String> archivo3 = new Pair<String, String>(a3.consulta_titulo(), a3.consulta_autor());

        ArrayList<Pair<String, String>> lista = domainCtrl.listar_por_query("Cuento de Disney", 2, 0);
        assertTrue(lista.contains(archivo1));
        assertTrue(lista.contains(archivo2));
        assertFalse(lista.contains(archivo3));

        //Depuramos
        domainCtrl.borrar_archivo(titulo1, autor1);
        domainCtrl.borrar_archivo(titulo2, autor1);
        domainCtrl.borrar_archivo(titulo3, autor2);
    }

    @Test
    public void ListarPorBoolTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String titulo1 = "La Bella y la Bestia";
        String autor1 = "Juanito";
        String formato = "txt";
        String contenido1 = "Esto es un cuento de Disney";
        domainCtrl.alta_archivo(titulo1, autor1, formato, contenido1);
        Archivo a1 = domainCtrl.get_archivo(titulo1, autor1);
        Pair<String, String> archivo1 = new Pair<String, String>(a1.consulta_titulo(), a1.consulta_autor());

        String titulo2 = "Cenicienta";
        String contenido2 = "Cuento de Disney";
        domainCtrl.alta_archivo(titulo2, autor1, formato, contenido2);
        Archivo a2 = domainCtrl.get_archivo(titulo2, autor1);
        Pair<String, String> archivo2 = new Pair<String, String>(a2.consulta_titulo(), a2.consulta_autor());

        String titulo3 = "PROP";
        String autor2 = "Carles";
        String contenido3 = "Esto es un assignatura obligatoria del grado de ingeniería informática en la FIB";
        domainCtrl.alta_archivo(titulo3, autor2, formato, contenido3);
        Archivo a3 = domainCtrl.get_archivo(titulo3, autor2);
        Pair<String, String> archivo3 = new Pair<String, String>(a3.consulta_titulo(), a3.consulta_autor());

        ArrayList<Pair<String, String>> lista = domainCtrl.listar_por_bool("Disney & (de | en) & !FIB");
        assertTrue(lista.contains(archivo1));
        assertTrue(lista.contains(archivo2));
        assertFalse(lista.contains(archivo3));

        //Depuramos
        domainCtrl.borrar_archivo(titulo1, autor1);
        domainCtrl.borrar_archivo(titulo2, autor1);
        domainCtrl.borrar_archivo(titulo3, autor2);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //Tests GESTION EXPRESIONES BOOLEANAS
    @Test
    public void AltaExpresionYListarExpresionesTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String expresion = "{p1 p2 p3} & (hola adéu) | pep";
        String expresion2 = "No entiendo {} mi | proposito ()";
        domainCtrl.alta_expresion(expresion);
        domainCtrl.alta_expresion(expresion2);

        Set<String> exp_comp = new HashSet<String>();
        exp_comp.add(expresion);
        exp_comp.add(expresion2);

        assertEquals(exp_comp, domainCtrl.listar_expresiones());
    }

    @Test
    public void ModificarExpresionTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String expresion = "{p1 p2 p3} & (hola adéu) | pep";
        domainCtrl.alta_expresion(expresion);

        String expresion2 = "No entiendo {} mi | proposito ()";
        domainCtrl.modificar_expresion(expresion, expresion2);

        Set<String> exp_comp = new HashSet<String>();
        exp_comp.add(expresion2);

        assertEquals(exp_comp, domainCtrl.listar_expresiones());
    }

    @Test
    public void BorrarExpresionTest() throws Exception {
        DomainCtrl domainCtrl = DomainCtrl.getINSTANCE();
        String expresion = "{p1 p2 p3} & (hola adéu) | pep";
        domainCtrl.alta_expresion(expresion);

        Set<String> exp_comp = new HashSet<String>();

        domainCtrl.borrar_expresion(expresion);
        assertEquals(exp_comp, domainCtrl.listar_expresiones());
    }

}

package Drivers;

import Domain.Corpus;

import java.util.Scanner;

public class DriverCorpus {
    private Corpus corpus;
    private Scanner in;

    public void constructora() throws Exception {
        corpus = new Corpus();
        System.out.println("Corpus creado");
    }

    public void testAnadirArchivo() throws Exception{
        if(corpus == null) System.out.println("Primero se tiene que crear la instancia de Corpus!");
        else {
            System.out.println("Introduce el título del nuevo archivo \n");
            String titulo = in.nextLine();
            while (titulo == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                titulo = in.nextLine();
            }
            System.out.println("Introduce el autor del nuevo archivo \n");
            String autor = in.nextLine();
            while (autor == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un nombre de autor \n");
                autor = in.nextLine();
            }
            System.out.println("Introduce el contenido del nuevo archivo \n");
            String contenido = in.nextLine();
            while (contenido == null) {
                System.out.println("ERROR: contenido nulo. Por favor, vuelva a introducir un contenido \n");
                contenido = in.nextLine();
            }
            corpus.anadir_archivo(titulo, autor, contenido);
            System.out.println("Archivo añadido con éxito");
        }
    }

    public void testBorrarArchivo() throws Exception {
        if(corpus == null) System.out.println("Primero se tiene que crear la instancia de Corpus!");
        else {
            System.out.println("Introduce el título del nuevo archivo \n");
            String titulo = in.nextLine();
            while (titulo == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                titulo = in.nextLine();
            }
            System.out.println("Introduce el autor del nuevo archivo \n");
            String autor = in.nextLine();
            while (autor == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un nombre de autor \n");
                autor = in.nextLine();
            }
            corpus.borrar_archivo(titulo, autor);
            System.out.println("Archivo borrado con éxito");
        }
    }

    public void testcompararArchivos() throws Exception {
        if(corpus == null) System.out.println("Primero se tiene que crear la instancia de Corpus!");
        else {
            System.out.println("Introduce el título del archivo \n");
            String tituloA = in.nextLine();
            while (tituloA == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                tituloA = in.nextLine();
            }
            System.out.println("Introduce el título del autor \n");
            String autorA = in.nextLine();
            while (autorA == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un nombre de autor \n");
                autorA = in.nextLine();
            }
            System.out.println("Introduce el título del archivo con el que comparar \n");
            String tituloB = in.nextLine();
            while (tituloB == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                tituloB = in.nextLine();
            }
            System.out.println("Introduce el autor del archivo con el que comparar \n");
            String autorB = in.nextLine();
            while (autorB == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un nombre de autor \n");
                autorB = in.nextLine();
            }
            System.out.println("¿Cómo quieres comparar los archivos? Introduce un número: \n (0) Tf y df \n (1) Bm25 \n");
            String modo = in.nextLine();
            while (modo != "0" && modo != "1") {
                System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
                modo = in.nextLine();
            }
            int mode = Integer.parseInt(modo);
            System.out.println("Los archivos son: " + corpus.comparar_archivos(tituloA, autorA, tituloB, autorB, mode) + " de 1 en similitud \n");
        }
    }

    public void testcompararQuery() throws Exception {
        if(corpus == null) System.out.println("Primero se tiene que crear la instancia de Corpus!");
        else {
            System.out.println("Introduce el título del archivo \n");
            String titulo = in.nextLine();
            while (titulo == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                titulo = in.nextLine();
            }
            System.out.println("Introduce el autor del archivo \n");
            String autor = in.nextLine();
            while (autor == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un nombre de autor \n");
                autor = in.nextLine();
            }
            System.out.println("Introduce la query con la que comparar \n");
            String query = in.nextLine();
            while (query == null) {
                System.out.println("ERROR: query nula. Por favor, vuelva a introducir una query \n");
                query = in.nextLine();
            }
            System.out.println("¿Cómo quieres comparar la query? Introduce un número: \n (0) Tf y df \n (1) Bm25 \n");
            String modo = in.nextLine();
            while (modo != "0" && modo != "1") {
                System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
                modo = in.nextLine();
            }
            int mode = Integer.parseInt(modo);
            System.out.println("La query es: " + corpus.comparar_query(titulo, autor, query, mode) + " de 1 en similitud");
        }
    }

    public void testexisteArchivo() {
        if(corpus == null) System.out.println("Primero se tiene que crear la instancia de Corpus!");
        else {
            System.out.println("Introduce el título del archivo \n");

            String titulo = in.nextLine();
            while (titulo == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                titulo = in.nextLine();
            }
            System.out.println("Introduce el autor del archivo \n");
            String autor = in.nextLine();
            while (autor == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un nombre de autor \n");
                autor = in.nextLine();
            }
            if(corpus.existe_archivo(titulo, autor)) System.out.println("El archivo existe");
            else System.out.println("ERROR: El archivo no existe");
        }
    }

    public static void main (String[] args) throws Exception {
        DriverCorpus dv = new DriverCorpus();
        System.out.println("Driver Principal Corpus");
        System.out.println("");
        muestra_metodos();
        dv.in = new Scanner(System.in);
        String input = dv.in.nextLine();
        while(!input.equals("0") && !input.equals("sortir")) {
            switch (input) {
                case "1":
                case "constructora": {
                    dv.constructora();
                    break;
                }
                case "2":
                case "anadirArchivo": {
                    dv.testAnadirArchivo();
                    break;
                }
                case "3":
                case "borrarArchivo": {
                    dv.testBorrarArchivo();
                    break;
                }
                case "4":
                case "compararArchivos": {
                    dv.testcompararArchivos();
                    break;
                }
                case "5":
                case "compararQuery": {
                    dv.testcompararQuery();
                    break;
                }
                case "6":
                case "existeArchivo": {
                    dv.testexisteArchivo();
                    break;
                }
                default:
                    break;
            }
            dv.volver_Menu();
            muestra_metodos();
            input = dv.in.nextLine();
        }
        dv.in.close();
    }

    private static void muestra_metodos(){
        System.out.println("(1|constructora) - Constructora");
        System.out.println("(2|anadirArchivo) - Añade un archivo con el título y autor indicados");
        System.out.println("(3|borrarArchivo) - Añade el archivo con el título y autor indicados");
        System.out.println("(4|compararArchivos) - Compara dos archivos");
        System.out.println("(5|compararQuery) - Compara la query entrada con el archivo identificado por el título y autor indicados");
        System.out.println("(6|existeArchivo) - Comprueba si existe el archivo cuyo título y autor sean los entrados");
        System.out.println("");
        System.out.println("(0|salir) - Cerrar Driver");
    }

    private void volver_Menu() {
        System.out.println("Pulsa ENTER para volver al menu principal");
        in.nextLine();
    }
}


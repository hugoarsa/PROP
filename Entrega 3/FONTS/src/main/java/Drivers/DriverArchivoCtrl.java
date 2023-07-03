package Drivers;

import java.util.*;
import Domain.ArchivoCtrl;
import Domain.Archivo;
import Domain.Corpus;
import Domain.Pair;
import Domain.ExpBooleana;

public class DriverArchivoCtrl {

    ArchivoCtrl arcCtrl = null;
    Scanner in;

    public void testConstructora() throws Exception{
        arcCtrl = new ArchivoCtrl();
        System.out.println("Se ha realizado la operacion con exito");
    }

    public void testAltaArchivo() throws Exception{
        if(arcCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de ArchivoCtrl!");
            return;
        }
        System.out.println("Introduce los datos del Archivo a dar de alta: \n");
        String[] s = leer_archivo();
        arcCtrl.alta_archivo(s[0], s[1], s[2], s[3]);
        System.out.println("Se ha realizado la operacion con exito");
    }

    public void testModificarArchivo() throws Exception{
        if(arcCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de ArchivoCtrl!");
            return;
        }
        System.out.println("Introduce el ID del Archivo a modificar:\n");
        String[] s_ant = leer_archivo_id();
        System.out.println("\n");
        System.out.println("Introduce los datos modificados del Archivo:\n");
        String[] s = leer_archivo();
        //arcCtrl.modificar_archivo(s_ant[0], s_ant[1], s[0], s[1], s[2], s[3]);
        System.out.println("Se ha realizado la operacion con exito");
    }

    public void testBorrarArchivo() throws Exception{
        if(arcCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de ArchivoCtrl!");
            return;
        }
        System.out.println("Introduce el ID del Archivo a borrar: \n");
        String[] s = leer_archivo_id();
        arcCtrl.borrar_archivo(s[0], s[1]);
        System.out.println("Se ha realizado la operacion con exito");
    }

    public void testConsultaFormatoArchivo() throws Exception{
        if(arcCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de ArchivoCtrl!");
            return;
        }
        System.out.println("Introduce el ID del Archivo a consultar: \n");
        String[] s = leer_archivo_id();
        String formato = arcCtrl.consulta_formato_archivo(s[0], s[1]);
        System.out.println("El formato del Archivo <" + s[0] + ", " + s[1] + "> es: " + formato);
    }

    public void testConsultaContenidoArchivo() throws Exception{
        if(arcCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de ArchivoCtrl!");
            return;
        }
        System.out.println("Introduce el ID del Archivo a consultar: \n");
        String[] s = leer_archivo_id();
        String contenido = arcCtrl.consulta_contenido_archivo(s[0], s[1]);
        System.out.println("El contenido del Archivo <" + s[0] + ", " + s[1] + "> es: " + contenido);
    }

    public void testListarPorAutor(){
        if(arcCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de ArchivoCtrl!");
            return;
        }
        System.out.println("Introduce el nombre del Autor");
        String autor = in.nextLine();
        while(autor == null || autor == ""){
            System.out.println("ERROR: texto vacio, introduce un texto valido");
            autor = in.nextLine();
        }
        System.out.println("\n");
        System.out.println("Selecciona un modo de ordenacion: \n");
        System.out.println(" 0 - Titulo creciente\n 1 - Titulo decreciente");
        System.out.println(" 2 - Autor creciente\n 3 - Autor decreciente");
        System.out.println(" 4 - Fecha creciente\n 5 - Fecha decreciente");
        int sortMode = in.nextInt();
        while(sortMode < 0 && sortMode > 5){
            System.out.println("Modo de ordenacion invalido, selecciona un modo de la lista: \n");
            System.out.println("Selecciona un modo de ordenacion: \n");
            System.out.println(" 0 - Titulo creciente\n 1 - Titulo decreciente");
            System.out.println(" 2 - Autor creciente\n 3 - Autor decreciente");
            System.out.println(" 4 - Fecha creciente\n 5 - Fecha decreciente");
            sortMode = in.nextInt();
        }
        listar_resultados(arcCtrl.listar_por_autor(autor));
    }

    public void testListarPorTitulo(){
        if(arcCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de ArchivoCtrl!");
            return;
        }
        System.out.println("Introduce el infijo a usar como centinela");
        String infijo = in.nextLine();
        while(infijo == null || infijo == ""){
            System.out.println("ERROR: texto vacio, introduce un texto valido");
            infijo = in.nextLine();
        }
        System.out.println("\n");
        System.out.println("Selecciona un modo de ordenacion: \n");
        System.out.println(" 0 - Titulo creciente\n 1 - Titulo decreciente");
        System.out.println(" 2 - Autor creciente\n 3 - Autor decreciente");
        System.out.println(" 4 - Fecha creciente\n 5 - Fecha decreciente");
        int sortMode = in.nextInt();
        while(sortMode < 0 && sortMode > 5){
            System.out.println("Modo de ordenacion invalido, selecciona un modo de la lista: \n");
            System.out.println("Selecciona un modo de ordenacion: \n");
            System.out.println(" 0 - Titulo creciente\n 1 - Titulo decreciente");
            System.out.println(" 2 - Autor creciente\n 3 - Autor decreciente");
            System.out.println(" 4 - Fecha creciente\n 5 - Fecha decreciente");
            sortMode = in.nextInt();
        }
        listar_resultados(arcCtrl.listar_por_titulo(infijo));
    }

    public void testListarPorSemejanza() throws Exception{
        if(arcCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de ArchivoCtrl!");
            return;
        }
        System.out.println("Introduce un ID del archivo a usar como comparador");
        String[] s = leer_archivo_id();

        System.out.println("Introduce un numero n de archivos a escoger");
        int n = in.nextInt();
        while(n <= 0){
            System.out.println("ERROR: valor de n negativo, introduce un valor > 0");
            n = in.nextInt();
        }
        System.out.println("\n");
        System.out.println("Selecciona una estrategia de asignacion de pesos");
        System.out.println(" 0 - tfidf\n 1 - Okapi BM25");
        int weightMode = in.nextInt();
        while(weightMode < 0 && weightMode > 1){
            System.out.println("Selecciona una estrategia de asignacion de pesos");
            System.out.println(" 0 - tfidf\n 1 - Okapi BM25");
            weightMode = in.nextInt();
        }

        System.out.println("\n");
        System.out.println("Selecciona un modo de ordenacion: \n");
        System.out.println(" 0 - Titulo creciente\n 1 - Titulo decreciente");
        System.out.println(" 2 - Autor creciente\n 3 - Autor decreciente");
        System.out.println(" 4 - Fecha creciente\n 5 - Fecha decreciente");
        int sortMode = in.nextInt();
        while(sortMode < 0 && sortMode > 5){
            System.out.println("Modo de ordenacion invalido, selecciona un modo de la lista: \n");
            System.out.println("Selecciona un modo de ordenacion: \n");
            System.out.println(" 0 - Titulo creciente\n 1 - Titulo decreciente");
            System.out.println(" 2 - Autor creciente\n 3 - Autor decreciente");
            System.out.println(" 4 - Fecha creciente\n 5 - Fecha decreciente");
            sortMode = in.nextInt();
        }
        listar_resultados(arcCtrl.listar_por_semejanza(s[0], s[1], n, weightMode));
    }

    public void testListarPorQuery() throws Exception{
        if(arcCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de ArchivoCtrl!");
            return;
        }
        System.out.println("Introduce la secuencia de palabras a usar como comparadora");
        String query = in.nextLine();
        while(query == null || query.equals("")){
            System.out.println("ERROR: texto vacio, introduce un texto valido");
            query = in.nextLine();
        }

        System.out.println("Introduce un numero n de archivos a escoger");
        int n = in.nextInt();
        while(n <= 0){
            System.out.println("ERROR: valor de n negativo, introduce un valor > 0");
            n = in.nextInt();
        }
        System.out.println("\n");
        System.out.println("Selecciona una estrategia de asignacion de pesos");
        System.out.println(" 0 - tfidf\n 1 - Okapi BM25");
        int weightMode = in.nextInt();
        while(weightMode < 0 && weightMode > 1){
            System.out.println("Selecciona una estrategia de asignacion de pesos");
            System.out.println(" 0 - tfidf\n 1 - Okapi BM25");
            weightMode = in.nextInt();
        }

        System.out.println("\n");
        System.out.println("Selecciona un modo de ordenacion: \n");
        System.out.println(" 0 - Titulo creciente\n 1 - Titulo decreciente");
        System.out.println(" 2 - Autor creciente\n 3 - Autor decreciente");
        System.out.println(" 4 - Fecha creciente\n 5 - Fecha decreciente");
        int sortMode = in.nextInt();
        while(sortMode < 0 && sortMode > 5){
            System.out.println("Modo de ordenacion invalido, selecciona un modo de la lista: \n");
            System.out.println("Selecciona un modo de ordenacion: \n");
            System.out.println(" 0 - Titulo creciente\n 1 - Titulo decreciente");
            System.out.println(" 2 - Autor creciente\n 3 - Autor decreciente");
            System.out.println(" 4 - Fecha creciente\n 5 - Fecha decreciente");
            sortMode = in.nextInt();
        }
        listar_resultados(arcCtrl.listar_por_query(query, n, weightMode));
    }

    public void testListarPorBool() throws Exception{
        if(arcCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de ArchivoCtrl!");
            return;
        }
        System.out.println("Introduce una expresion booleana");
        String expBool = in.nextLine();
        while(expBool == null || expBool == ""){
            System.out.println("ERROR: texto vacio, introduce un texto valido");
            expBool = in.nextLine();
        }
        ExpBooleana expresion = new ExpBooleana(expBool, 1);
        System.out.println("\n");
        System.out.println("Selecciona un modo de ordenacion: \n");
        System.out.println(" 0 - Titulo creciente\n 1 - Titulo decreciente");
        System.out.println(" 2 - Autor creciente\n 3 - Autor decreciente");
        System.out.println(" 4 - Fecha creciente\n 5 - Fecha decreciente");
        int sortMode = in.nextInt();
        while(sortMode < 0 && sortMode > 5){
            System.out.println("Modo de ordenacion invalido, selecciona un modo de la lista: \n");
            System.out.println("Selecciona un modo de ordenacion: \n");
            System.out.println(" 0 - Titulo creciente\n 1 - Titulo decreciente");
            System.out.println(" 2 - Autor creciente\n 3 - Autor decreciente");
            System.out.println(" 4 - Fecha creciente\n 5 - Fecha decreciente");
            sortMode = in.nextInt();
        }
        listar_resultados(arcCtrl.listar_por_bool(expresion));
    }

    public static void main (String[] args) throws Exception{
        DriverArchivoCtrl dac = new DriverArchivoCtrl();
        System.out.println("Driver Principal ArchivoCtrl");
        System.out.println("");
        muestra_metodos();
        dac.in = new Scanner(System.in);
        String input = dac.in.nextLine();
        while(!input.equals("0") && !input.equals("sortir")){
            switch(input) {
                case "1":
                case "constructora": {
                    dac.testConstructora();
                    break;
                }
                case "2":
                case "altaArchivo":{
                    dac.testAltaArchivo();
                    break;
                }
                case "3":
                case "modificarArchivo":{
                    dac.testModificarArchivo();
                    break;
                }
                case "4":
                case "borrarArchivo":{
                    dac.testBorrarArchivo();
                    break;
                }
                case "5":
                case "consultaFormatoArchivo":{
                    dac.testConsultaFormatoArchivo();
                    break;
                }
                case "6":
                case "consultaContenidoArchivo":{
                    dac.testConsultaContenidoArchivo();
                    break;
                }
                case "7":
                case "listarPorAutor":{
                    dac.testListarPorAutor();
                    break;
                }
                case "8":
                case "listarPorTitulo":{
                    dac.testListarPorTitulo();
                    break;
                }
                case "9":
                case "listarPorSemejanza":{
                    dac.testListarPorSemejanza();
                    break;
                }
                case "10":
                case "listarPorQuery":{
                    dac.testListarPorQuery();
                    break;
                }
                case "11":
                case "listarPorBool":{
                    dac.testListarPorBool();
                    break;
                }
                default:
                    break;
            }
            dac.volver_Menu();
            muestra_metodos();
            input = dac.in.nextLine();
        }
        dac.in.close();
    }
    private static void muestra_metodos(){
        System.out.println("(1|constructora) - Constructora");
        System.out.println("(2|altaArchivo) - Dar de alta un Archivo");
        System.out.println("(3|modificarArchivo) - Modificar un Archivo");
        System.out.println("(4|borrarArchivo) - Borrar un Archivo");
        System.out.println("(5|consultaFormatoArchivo) - Consulta el formato de un Archivo");
        System.out.println("(6|consultaContenidoArchivo) - Consulta el contenido de un Archivo");
        System.out.println("(7|listarPorAutor) - Dado un autor lista todos sus Archivos creados");
        System.out.println("(8|listarPorTitulo) - Dado un infijo lista todos los Archivos que lo contengan en su titulo");
        System.out.println("(9|listarPorSemejanza) - Dado un Archivo y un entero n lista los n archivos mas parecidos a este");
        System.out.println("(10|listarPorQuery) - Dada una secuencia de palabras P y un entero k lista los k archivos mas relevantes a la query");
        System.out.println("(11|listarPorBool) - Dada una ExpBooleana lista todos los Archivos que tengan una frase que la cumpla");
        System.out.println("");
        System.out.println("(0|salir) - Cerrar Driver");
    }

    private void volver_Menu() {
        System.out.println("Pulsa ENTER para volver al menu principal");
        in.nextLine();
    }

    private String[] leer_archivo(){
        System.out.println("Introduce el titulo del Archivo");
        String titulo = in.nextLine();
        while(titulo == null || titulo == ""){
            System.out.println("ERROR: texto vacio, introduce un texto valido");
            titulo = in.nextLine();
        }
        System.out.println("Introduce el autor del Archivo");
        String autor = in.nextLine();
        while(autor == null || autor == ""){
            System.out.println("ERROR: texto vacio, introduce un texto valido");
            autor = in.nextLine();
        }
        System.out.println("Introduce el formato del Archivo");
        System.out.println("OPCIONES:\n -\"txt\"\n -\"bin\"\n -\"xml\"");
        String formato = in.nextLine();
        while(formato == null || (!formato.equals("txt") && !formato.equals("bin") && !formato.equals("xml"))){
            System.out.println("ERROR: formato no valido, introduce un formato de la lista:");
            System.out.println("OPCIONES:\n -\"txt\"\n -\"bin\"\n -\"xml\"");
            formato = in.nextLine();
        }
        System.out.println("Introduce el contenido del Archivo");
        String contenido = in.nextLine();
        while(contenido == null || contenido == ""){
            System.out.println("ERROR: texto vacio, introduce un texto valido");
            contenido = in.nextLine();
        }
        String[] s = {titulo, autor, formato, contenido};
        return s;
    }

    private String[] leer_archivo_id(){
        System.out.println("Introduce el titulo del Archivo");
        String titulo = in.nextLine();
        while(titulo == null || titulo == ""){
            System.out.println("ERROR: texto vacio, introduce un texto valido");
            titulo = in.nextLine();
        }
        System.out.println("Introduce el autor del Archivo");
        String autor = in.nextLine();
        while(autor == null || autor == ""){
            System.out.println("ERROR: texto vacio, introduce un texto valido");
            autor = in.nextLine();
        }
        String[] s = {titulo, autor};
        return s;
    }

    private void listar_resultados(ArrayList<Pair<String, String>> resultSet){
        for(Pair<String, String> entry : resultSet){
            System.out.println("titulo: " + entry.first() + " autor: " + entry.second());
        }
        System.out.println("\n\n");
    }
}

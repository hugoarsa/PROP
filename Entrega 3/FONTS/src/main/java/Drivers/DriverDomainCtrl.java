package Drivers;

import Domain.DomainCtrl;
import Exceptions.BackendException;
import Persistence.GestorAutor;
import Persistence.GestorUsuario;

import java.util.Scanner;

public class DriverDomainCtrl {
    DomainCtrl domainCtrl;
    private Scanner in;

    public void testConstructora() throws BackendException{
        domainCtrl = DomainCtrl.getINSTANCE();
        System.out.println("Instancia de DomainCtrl creado con éxito");
    }

    public void testVerificarPerfil() throws BackendException {
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce el username \n");
            String username = in.nextLine();
            while (username == null) {
                System.out.println("ERROR: username nulo. Por favor, vuelva a introducir un username \n");
                username = in.nextLine();
            }
            System.out.println("Introduce la contraseña \n");
            String contrasena = in.nextLine();
            while (contrasena == null) {
                System.out.println("ERROR: contrasena nula. Por favor, vuelva a introducir una contrasena \n");
                contrasena = in.nextLine();
            }
            if (domainCtrl.verificar_perfil(username, contrasena)) System.out.println("Inicio de sesión del usuario: " + username);
        }
    }

    public void testCrearPerfil() throws BackendException{
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce el nuevo username \n");
            String username = in.nextLine();
            while (username == null) {
                System.out.println("ERROR: username nulo. Por favor, vuelva a introducir un username \n");
                username = in.nextLine();
            }
            System.out.println("Introduce la contraseña \n");
            String contrasena = in.nextLine();
            while (contrasena == null) {
                System.out.println("ERROR: contrasena nula. Por favor, vuelva a introducir una contrasena \n");
                contrasena = in.nextLine();
            }
            System.out.println("Vuelva a introducir la contraseña \n");
            String contrasena2 = in.nextLine();
            while (contrasena2 == null) {
                System.out.println("ERROR: contrasena nula. Por favor, vuelva a introducir una contrasena \n");
                contrasena2 = in.nextLine();
            }
            System.out.println("¿Cuál es su nombre? Introduce un nombre, por favor \n");
            String nombre = in.nextLine();
            while (nombre == null) {
                System.out.println("ERROR: nombre nulo. Por favor, vuelva a introducir un nombre \n");
                nombre = in.nextLine();
            }
            domainCtrl.crear_perfil(username, contrasena, contrasena2, nombre);
            System.out.println("Usuario creado con éxito");
        }
    }

    public void testModificarPerfil() throws BackendException{
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce el username a modificar \n");
            String username_ant = in.nextLine();
            while (username_ant == null) {
                System.out.println("ERROR: username nulo. Por favor, vuelva a introducir un username \n");
                username_ant = in.nextLine();
            }
            System.out.println("Introduce el nuevo username \n");
            String username = in.nextLine();
            while (username == null) {
                System.out.println("ERROR: username nulo. Por favor, vuelva a introducir un username \n");
                username = in.nextLine();
            }
            System.out.println("¿Cuál es su nombre? Introduce un nombre, por favor \n");
            String nombre = in.nextLine();
            while (nombre == null) {
                System.out.println("ERROR: nombre nulo. Por favor, vuelva a introducir un nombre \n");
                nombre = in.nextLine();
            }
            domainCtrl.modificar_perfil(username_ant,username,nombre);
            System.out.println("Cambios realizados con éxito");
        }
    }

    public void testCambiarContrasena() throws BackendException{
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce el username a modificar \n");
            String username = in.nextLine();
            while (username == null) {
                System.out.println("ERROR: username nulo. Por favor, vuelva a introducir un username \n");
                username = in.nextLine();
            }
            System.out.println("Introduce la contraseña \n");
            String contrasena = in.nextLine();
            while (contrasena == null) {
                System.out.println("ERROR: contrasena nula. Por favor, vuelva a introducir una contrasena \n");
                contrasena = in.nextLine();
            }
            System.out.println("Introduce la nueva contraseña \n");
            String nueva_contrasena = in.nextLine();
            while (nueva_contrasena == null) {
                System.out.println("ERROR: contrasena nula. Por favor, vuelva a introducir una contrasena \n");
                nueva_contrasena = in.nextLine();
            }
            domainCtrl.cambiar_contrasena(username, contrasena ,nueva_contrasena);
            System.out.println("Contraseña cambiada con éxito");
        }
    }

    public void testBorrarPerfil() throws BackendException{
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce el username a borrar \n");
            String username = in.nextLine();
            while (username == null) {
                System.out.println("ERROR: username nulo. Por favor, vuelva a introducir un username \n");
                username = in.nextLine();
            }
            System.out.println("Para eliminar el usuario " + username + " por favor, introduce su contraseña \n");
            String contrasena = in.nextLine();
            while (contrasena == null) {
                System.out.println("ERROR: contrasena nula. Por favor, vuelva a introducir una contrasena \n");
                contrasena = in.nextLine();
            }
            domainCtrl.borrar_perfil(username, contrasena);
            System.out.println("Usuario borrado con éxito");
        }
    }

    public void testAltaArchivo() throws BackendException{
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce los datos del archivo a dar de alta: \n");
            System.out.println("Título: ");
            String titulo = in.nextLine();
            while (titulo == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                titulo = in.nextLine();
            }
            System.out.println("Autor: ");
            String autor = in.nextLine();
            while (autor == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un autor \n");
                autor = in.nextLine();
            }
            System.out.println("Formato: ");
            String formato = in.nextLine();
            while (formato == null) {
                System.out.println("ERROR: formato nulo. Por favor, vuelva a introducir un formato \n");
                formato = in.nextLine();
            }
            System.out.println("Contenido: ");
            String contenido = in.nextLine();
            while (contenido == null) {
                System.out.println("ERROR: contenido nulo. Por favor, vuelva a introducir un contenido \n");
                contenido = in.nextLine();
            }
            domainCtrl.alta_archivo(titulo, autor, formato, contenido);
            System.out.println("Archivo creado con éxito");
        }
    }

    public void testModificarArchivo() throws BackendException {
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce el título y autor del archivo a modificar");
            System.out.println("Título: ");
            String titulo_ant = in.nextLine();
            while (titulo_ant == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                titulo_ant = in.nextLine();
            }
            System.out.println("Autor: ");
            String autor_ant = in.nextLine();
            while (autor_ant == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un autor \n");
                autor_ant = in.nextLine();
            }
            System.out.println("Introduce los datos a modificar (si no quiere cambiar algún dato, pulse ENTER): \n");
            System.out.println("Título: ");
            String titulo = in.nextLine();
            while (titulo == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                titulo = in.nextLine();
            }
            System.out.println("Autor: ");
            String autor = in.nextLine();
            while (autor == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un autor \n");
                autor = in.nextLine();
            }
            System.out.println("Formato: ");
            String formato = in.nextLine();
            while (formato == null) {
                System.out.println("ERROR: formato nulo. Por favor, vuelva a introducir un formato \n");
                formato = in.nextLine();
            }
            System.out.println("Contenido: ");
            String contenido = in.nextLine();
            while (contenido == null) {
                System.out.println("ERROR: contenido nulo. Por favor, vuelva a introducir un contenido \n");
                contenido = in.nextLine();
            }
            //domainCtrl.modificar_archivo(titulo_ant, autor_ant, titulo, autor, formato, contenido);
            System.out.println("Archivo modificado con éxito");
        }
    }

    public void testBorrarArchivo()throws BackendException {
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce el título y autor del archivo a borrar");
            System.out.println("Título: ");
            String titulo = in.nextLine();
            while (titulo == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                titulo = in.nextLine();
            }
            System.out.println("Autor: ");
            String autor = in.nextLine();
            while (autor == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un autor \n");
                autor = in.nextLine();
            }
            domainCtrl.borrar_archivo(titulo, autor);
            System.out.println("Archivo borrado con éxito");
        }
    }

    public void testConsultaFormatoArchivo()throws BackendException {
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce el título y autor del archivo a consultar");
            System.out.println("Título: ");
            String titulo = in.nextLine();
            while (titulo == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                titulo = in.nextLine();
            }
            System.out.println("Autor: ");
            String autor = in.nextLine();
            while (autor == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un autor \n");
                autor = in.nextLine();
            }
            System.out.println("El formato de este archivo es: " + domainCtrl.consulta_formato_archivo(titulo, autor));
        }
    }

    public void testConsultaContenidoArchivo() throws BackendException {
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce el título y autor del archivo a consultar");
            System.out.println("Título: ");
            String titulo = in.nextLine();
            while (titulo == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                titulo = in.nextLine();
            }
            System.out.println("Autor: ");
            String autor = in.nextLine();
            while (autor == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un autor \n");
                autor = in.nextLine();
            }
            System.out.println("El contenido de este archivo es: \n" + domainCtrl.consulta_contenido_archivo(titulo, autor));
        }
    }

    public void testListarAutores() throws BackendException{
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce un prefijo para listar los autores por este (Si no se desea listar por prefijo pulse ENTER): \n");
            String prefijo = in.nextLine();
            while (prefijo == null) {
                System.out.println("ERROR: prefijo nulo. Por favor, vuelva a introducir un prefijo \n");
                prefijo = in.nextLine();
            }
            System.out.println("¿Cómo quieres listar los autores? Introduce un número \n (0) Orden alfabético \n (1) Orden alfabético reverso");
            int orden = in.nextInt();
            while (orden != 0 && orden != 1) {
                System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
                orden = in.nextInt();
            }
            System.out.println("Autores del sistema: \n" + domainCtrl.listar_autores(prefijo));
        }
    }

    public void testListarPorAutor() {
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce el autor de los títulos a listar: \n");
            String autor = in.nextLine();
            while (autor == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un autor \n");
                autor = in.nextLine();
            }
            System.out.println("¿Cómo quieres listar los archivos? Introduce un número \n (0) Orden alfabético \n (1) Orden alfabético reverso");
            String orden = in.nextLine();
            while (orden != "0" && orden != "1") {
                System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
                orden = in.nextLine();
            }
            int sortMode = Integer.parseInt(orden);
            System.out.println("Archivos " + autor + ": \n" + domainCtrl.listar_por_autor(autor));
        }
    }

    public void testListarPorTitulo() {
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce el infijo de los títulos a listar: \n");
            String infijo = in.nextLine();
            while (infijo == null) {
                System.out.println("ERROR: infijo nulo. Por favor, vuelva a introducir un infijo \n");
                infijo = in.nextLine();
            }
            System.out.println("¿Cómo quieres listar los archivos? Introduce un número \n (0) Orden alfabético \n (1) Orden alfabético reverso");
            String orden = in.nextLine();
            while (orden != "0" && orden != "1") {
                System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
                orden = in.nextLine();
            }
            int sortMode = Integer.parseInt(orden);
            System.out.println("Archivos: \n" + domainCtrl.listar_por_titulo(infijo));
        }
    }

    public void testListarPorSemejanza() throws BackendException{
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce el título y autor del archivo a comparar \n");
            System.out.println("Título: ");
            String titulo = in.nextLine();
            while (titulo == null) {
                System.out.println("ERROR: título nulo. Por favor, vuelva a introducir un título \n");
                titulo = in.nextLine();
            }
            System.out.println("Autor: ");
            String autor = in.nextLine();
            while (autor == null) {
                System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un autor \n");
                autor = in.nextLine();
            }
            System.out.println("¿Cuántos archivos similares quieres? Introduce un número: \n");
            String num = in.nextLine();
            while(num == null) {
                System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
                num = in.nextLine();
            }
            int n = Integer.parseInt(num);
            System.out.println("¿Cómo quieres comparar los archivos? Introduce un número: \n (0) Tf y df \n (1) Bm25");
            String modo = in.nextLine();
            while (modo != "0" && modo != "1") {
                System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
                modo = in.nextLine();
            }
            int weightMode = Integer.parseInt(modo);
            System.out.println("¿Cómo quieres listar los archivos? Introduce un número \n (0) Orden alfabético \n (1) Orden alfabético reverso");
            String orden = in.nextLine();
            while (orden != "0" && orden != "1") {
                System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
                orden = in.nextLine();
            }
            int sortMode = Integer.parseInt(orden);
            System.out.println("Archivos: \n" + domainCtrl.listar_por_semejanza(titulo, autor, n, weightMode));
        }
    }

    public void testListarPorQuery() throws BackendException{
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce la query a comparar: \n");
            String query = in.nextLine();
            while (query == null) {
                System.out.println("ERROR: query nula. Por favor, vuelva a introducir una query \n");
                query = in.nextLine();
            }
            System.out.println("¿Cuántos archivos con la query indicada quieres? Introduce un número: \n");
            String num = in.nextLine();
            while(num == null) {
                System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
                num = in.nextLine();
            }
            int k = Integer.parseInt(num);
            System.out.println("¿Cómo quieres comparar los archivos? Introduce un número: \n (0) Tf y df \n (1) Bm25");
            String modo = in.nextLine();
            while (modo != "0" && modo != "1") {
                System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
                modo = in.nextLine();
            }
            int weightMode = Integer.parseInt(modo);
            System.out.println("¿Cómo quieres listar los archivos? Introduce un número \n (0) Orden alfabético \n (1) Orden alfabético reverso");
            String orden = in.nextLine();
            while (orden != "0" && orden != "1") {
                System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
                orden = in.nextLine();
            }
            int sortMode = Integer.parseInt(orden);
            System.out.println("Archivos: \n" + domainCtrl.listar_por_query(query, k, weightMode));
        }
    }


    public void testListarPorBool() throws BackendException{
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce la expresión booleana a comparar: \n");
            String expresion = in.nextLine();
            while (expresion == null) {
                System.out.println("ERROR: expresión nula. Por favor, vuelva a introducir una expresión booleana \n");
                expresion = in.nextLine();
            }
            System.out.println("¿Cómo quieres listar los archivos? Introduce un número \n (0) Orden alfabético \n (1) Orden alfabético reverso");
            String orden = in.nextLine();
            while (orden != "0" && orden != "1") {
                System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
                orden = in.nextLine();
            }
            int sortMode = Integer.parseInt(orden);
            System.out.println("Archivos: \n" + domainCtrl.listar_por_bool(expresion));
        }
    }

    public void testAltaExpresion() throws BackendException{
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce la nueva expresión booleana: \n");
            String expresion = in.nextLine();
            while (expresion == null) {
                System.out.println("ERROR: expresión nula. Por favor, vuelva a introducir una expresión booleana \n");
                expresion = in.nextLine();
            }
            domainCtrl.alta_expresion(expresion);
            System.out.println("Expresión booleana creada con éxito");
        }
    }

    public void testModificarExpresion() throws BackendException{
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce la expresión booleana a modificar: \n");
            String expresion = in.nextLine();
            while (expresion == null) {
                System.out.println("ERROR: expresión nula. Por favor, vuelva a introducir una expresión booleana \n");
                expresion = in.nextLine();
            }
            System.out.println("Introduce la nueva expresión booleana: \n");
            String expresion_nueva = in.nextLine();
            while (expresion_nueva == null) {
                System.out.println("ERROR: expresión nula. Por favor, vuelva a introducir una expresión booleana \n");
                expresion_nueva = in.nextLine();
            }
            domainCtrl.modificar_expresion(expresion, expresion_nueva);
            System.out.println("Expresión booleana modificada con éxito");
        }
    }

    public void testBorrarExpresion() throws BackendException{
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else {
            System.out.println("Introduce la expresión booleana a borrar: \n");
            String expresion = in.nextLine();
            while (expresion == null) {
                System.out.println("ERROR: expresión nula. Por favor, vuelva a introducir una expresión booleana \n");
                expresion = in.nextLine();
            }
            domainCtrl.borrar_expresion(expresion);
            System.out.println("Expresión booleana borrada con éxito");
        }
    }

    public void testListarExpresiones() {
        if(domainCtrl == null){
            System.out.println("Primero se tiene que crear la instancia de DomainCtrl!");
        }
        else System.out.println("Expresiones existentes: \n" + domainCtrl.listar_expresiones());
    }

    public void clearPersistencia() throws Exception{
        GestorUsuario GU = new GestorUsuario();
        GU.clear_usuarios();
        GestorAutor GA = new GestorAutor();
        GA.clear_autores();
    }

    public static void main (String[] args) throws Exception {
        DriverDomainCtrl dv = new DriverDomainCtrl();
        System.out.println("Driver Principal DomainCtrl");
        System.out.println("");
        muestra_metodos();
        dv.in = new Scanner(System.in);
        String input = dv.in.nextLine();
        while(!input.equals("0") && !input.equals("sortir")) {
            switch (input) {
                case "1":
                case "constructora": {
                    dv.testConstructora();
                    break;
                }
                case "2":
                case "verificarPerfil": {
                    dv.testVerificarPerfil();
                    break;
                }
                case "3":
                case "crearPerfil": {
                    dv.testCrearPerfil();
                    break;
                }
                case "4":
                case "modificarPerfil": {
                    dv.testModificarPerfil();
                    break;
                }
                case "5":
                case "cambiarContrasena": {
                    dv.testCambiarContrasena();
                    break;
                }
                case "6":
                case "borrarPerfil": {
                    dv.testBorrarPerfil();
                    break;
                }
                case "7":
                case "altaArchivo": {
                    dv.testAltaArchivo();
                    break;
                }
                case "8":
                case "modificarArchivo": {
                    dv.testModificarArchivo();
                    break;
                }
                case "9":
                case "borrarArchivo": {
                    dv.testBorrarArchivo();
                    break;
                }
                case "10":
                case "consultaFormatoArchivo": {
                    dv.testConsultaFormatoArchivo();
                    break;
                }
                case "11":
                case "consultaContenidoArchivo": {
                    dv.testConsultaContenidoArchivo();
                    break;
                }
                case "12":
                case "listarAutores": {
                    dv.testListarAutores();
                    break;
                }
                case "13":
                case "listarPorAutor": {
                    dv.testListarPorAutor();
                    break;
                }
                case "14":
                case "listarPorTítulo": {
                    dv.testListarPorTitulo();
                    break;
                }
                case "15":
                case "listarPorSemejanza": {
                    dv.testListarPorSemejanza();
                    break;
                }
                case "16":
                case "listarPorQuery": {
                    dv.testListarPorQuery();
                    break;
                }
                case "17":
                case "listarPorBool": {
                    dv.testListarPorBool();
                    break;
                }
                case "18":
                case "altaExpresión": {
                    dv.testAltaExpresion();
                    break;
                }
                case "19":
                case "modificarExpresión": {
                    dv.testModificarExpresion();
                    break;
                }
                case "20":
                case "borrarExpresión": {
                    dv.testBorrarExpresion();
                    break;
                }
                case "21":
                case "listarExpresiones": {
                    dv.testListarExpresiones();
                    break;
                }
                case "22":
                case "clearPersistencia": {
                    dv.clearPersistencia();
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
        System.out.println("(2|verificarPerfil) - Verifica la existencia de un perfil en el sistema");
        System.out.println("(3|crearPerfil) - Crea un nuevo perfil");
        System.out.println("(4|modificarPerfil) - Modifica los datos del perfil indicado");
        System.out.println("(5|cambiarContrasena) - Modifica la contraseña del perfil indicado");
        System.out.println("(6|borrarPerfil) - Borra el perfil indicado del sistema");
        System.out.println("(7|altaArchivo) - Añade un archivo con el título y autor indicados");
        System.out.println("(8|modificarArchivo) - Modifica los datos de un archivo");
        System.out.println("(9|borrarArchivo) - Borra el archivo indicado");
        System.out.println("(10|consultaFormatoArchivo) - Muestra el formato del archivo indicado");
        System.out.println("(11|consultaContenidoArchivo) - Muestra el contenido del archivo indicado");
        System.out.println("(12|listarAutores) - Lista todos los autores del sistema");
        System.out.println("(13|listarPorAutor) - Lista todos los archivos cuyo autor sea el entrado");
        System.out.println("(14|listarPorTítulo) - Lista todos los archivos cuyo título cumpla con el infijo entrado");
        System.out.println("(15|listarPorSemejanza) - Lista n archivos de similares al archivo indicado");
        System.out.println("(16|listarPorQuery) - Lista k archivos cuyo contenido contenga la query entrada");
        System.out.println("(17|listarPorBool) - Lista todos los archivos cuyo contenido cumpla con la expresión booleana entrada");
        System.out.println("(18|altaExpresión) - Añade una nueva expresión booleana");
        System.out.println("(19|modificarExpresión) - Modifica los datos de la expresión booleana indicada");
        System.out.println("(20|borrarExpresión) - Borra la expresión booleana indicada del sistema");
        System.out.println("(21|listarExpresiones) - Lista todas las expresiones booleanas del sistema");
        System.out.println("(22|clearPersistencia) - Borra todos los datos persistidos");
        System.out.println("");
        System.out.println("(0|salir) - Cerrar Driver");
    }

    private void volver_Menu() {
        System.out.println("Pulsa ENTER para volver al menu principal");
        in.nextLine();
    }
}

package Drivers;

import java.util.*;

import Domain.ExpBooleana;
import Domain.ExpBooleanaCtrl;
import Persistence.GestorBooleana;

/**
 * Este driver sirve para probar los metodos relacionados con los conjuntos de expresiones booleanas.
 * Permite al usuario comprobar los diversos metodos a traves de una interfaz de terminal, ejecutando
 * los metodos proporcionados la clase.
 * @author Hugo Aranda Sanchez (hugo.aranda@estudiantat.upc.edu)
 */
public class DriverExpBooleanaCtrl {

    private ExpBooleanaCtrl expC = null;
    private Scanner in;

    public void test_constructora() throws Exception{
        expC = new ExpBooleanaCtrl();
        System.out.println("Controlador creado");
    }

    public void test_alta_expresion() throws Exception{
        if(expC == null){
            System.out.println("Primero se tiene que crear la instancia de Controlador!");
            return;
        }
        System.out.println("Introduce una expresion para introducir");
        String texto = in.nextLine();

        if(texto == null || texto.equals("")) {
            System.out.println("Error: expresion vacía");
            return;
        }
        expC.alta_expresion(texto);
        System.out.println("Expresion creada con exito");
    }

    public void test_borrar_expresion() throws Exception{
        if(expC == null){
            System.out.println("Primero se tiene que crear la instancia de Controlador!");
            return;
        }
        System.out.println("Introduce una expresion para borrar");
        String texto = in.nextLine();

        if(texto == null || texto.equals("")) {
            System.out.println("Error: expresion vacía");
            return;
        }
        expC.borrar_expresion(texto);
        System.out.println("Expresion borrada con exito");
    }

    public void test_listar_expresiones(){
        if(expC == null){
            System.out.println("Primero se tiene que crear la instancia de Controlador!");
            return;
        }
        for(String s : expC.listar_expresiones()){
            System.out.println(s);
        }
    }

    public void test_consultar_expresion() throws Exception{
        if(expC == null){
            System.out.println("Primero se tiene que crear la instancia de Controlador!");
            return;
        }
        System.out.println("Introduce una expresion para consultar");
        String texto = in.nextLine();

        if(texto == null || texto.equals("")) {
            System.out.println("Error: expresion vacía");
            return;
        }
        ExpBooleana e = expC.consultar_expresion(texto);
        System.out.println("Se devuelve una instancia de ExpBooleana y tiene string " + e.to_String());
    }

    public void clear_persistencia() throws Exception{
        GestorBooleana GB = new GestorBooleana();
        GB.clear_expresiones();
    }


    public static void main (String[] args) throws Exception{
        DriverExpBooleanaCtrl ctrl = new DriverExpBooleanaCtrl();
        System.out.println("Driver Principal ExpBooleana");
        System.out.println("");
        muestra_metodos();
        ctrl.in = new Scanner(System.in);
        String input = ctrl.in.nextLine();
        while(!input.equals("0") && !input.equals("salir")){
            switch(input) {
                case "1":
                case "constructora": {
                    ctrl.test_constructora();
                    break;
                }
                case "2":
                case "altaExpresion":{
                    ctrl.test_alta_expresion();
                    break;
                }
                case "3":
                case "borrarExpresion":{
                    ctrl.test_borrar_expresion();
                    break;
                }
                case "4":
                case "listarExpresiones":{
                    ctrl.test_listar_expresiones();
                    break;
                }
                case "5":
                case "consultarExpresion":{
                    ctrl.test_consultar_expresion();
                    break;
                }
                case "6":
                case "clearPersistencia":{
                    ctrl.clear_persistencia();
                    break;
                }
                default:
                    break;
            }
            ctrl.volver_Menu();
            muestra_metodos();
            input = ctrl.in.nextLine();
        }
        ctrl.in.close();
    }
    private static void muestra_metodos(){
        System.out.println("(1|constructora) - Construye un controlador vacio listo para recibir expresiones");
        System.out.println("(2|altaExpresion) - Da de alta una expresion si esta no existe ya en en controlador");
        System.out.println("(3|borrarExpresion) - Borra una expresion si existe en el conjunto");
        System.out.println("(4|listarExpresiones) - Lista todos los strings que identifican las expresiones del controlador");
        System.out.println("(5|consultarExpresion) - Devuelve un objeto expresion segun el string que lo identifica");
        System.out.println("(6|clearPersistencia) - Limpia la persistencia");
        System.out.println("");
        System.out.println("(0|salir) - Cerrar Driver");
    }

    private void volver_Menu() {
        System.out.println("Pulsa ENTER para volver al menu principal");
        in.nextLine();
    }
}

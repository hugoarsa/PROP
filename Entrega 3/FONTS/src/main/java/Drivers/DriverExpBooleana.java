package Drivers;

import java.util.*;
import Domain.ExpBooleana;

/**
 * Este driver sirve para probar los metodos relacionados con las expresiones booleanas. Permite al usuario comprobar
 * los diversos metodos a traves de una interfaz de terminal, ejecutando los metodos proporcionados la clase.
 * @author Hugo Aranda Sanchez (hugo.aranda@estudiantat.upc.edu)
 */
public class DriverExpBooleana {

    private ExpBooleana exp = null;
    private Scanner in;

    public void test_constructora() throws Exception{
        System.out.println("Introduce una expresion para evaluar frases");
        String texto = in.nextLine();

        if(texto == null || texto.equals("")) {
            System.out.println("Error: expresion vacía");
            return;
        }
        exp = new ExpBooleana(texto, 1);
        System.out.println("Expresion bien creada");
    }

    public void test_to_string(){
        if(exp == null){
            System.out.println("Primero se tiene que crear la instancia de Exception!");
            return;
        }
        String s = exp.to_String();
        System.out.println(s);
    }

    public void test_match(){
        if(exp == null){
            System.out.println("Primero se tiene que crear la instancia de Exception!");
            return;
        }
        System.out.println("Introduce una frase a evaluar por la expresion");
        String texto = in.nextLine();

        if(texto == null || texto.equals("")) {
            System.out.println("Error: frase vacía");
            return;
        }

        System.out.print("La frase " + texto);
        if(exp.match(texto)) System.out.print(" SI coincide con ");
        else System.out.print(" NO coincide con ");
        System.out.println("la expresion " + exp.to_String());

    }

    public static void main (String[] args) throws Exception{
        DriverExpBooleana deb = new DriverExpBooleana();
        System.out.println("Driver Principal ExpBooleana");
        System.out.println("");
        muestra_metodos();
        deb.in = new Scanner(System.in);
        String input = deb.in.nextLine();
        while(!input.equals("0") && !input.equals("salir")){
            switch(input) {
                case "1":
                case "constructora": {
                    deb.test_constructora();
                    break;
                }
                case "2":
                case "toString":{
                    deb.test_to_string();
                    break;
                }
                case "3":
                case "Match":{
                    deb.test_match();
                    break;
                }
                default:
                    break;
            }
            deb.volver_Menu();
            muestra_metodos();
            input = deb.in.nextLine();
        }
        deb.in.close();
    }
    private static void muestra_metodos(){
        System.out.println("(1|constructora) - Constructora Expresion");
        System.out.println("(2|ToString) - Devuelve el string que representa la expresion");
        System.out.println("(3|Match) - Mira si una frase cumple la condicion");
        System.out.println("");
        System.out.println("(0|salir) - Cerrar Driver");
    }

    private void volver_Menu() {
        System.out.println("Pulsa ENTER para volver al menu principal");
        in.nextLine();
    }
}

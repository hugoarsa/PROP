package Drivers;
import java.util.*;
import Domain.CjtAutores;
import Persistence.GestorAutor;

import java.util.Scanner;

public class DriverCjtAutores {
     static CjtAutores autores;
    private Scanner in;

    public void testInsertarAutor() throws Exception {
        System.out.println("Introduce un nombre de autor \n");
        String autor = in.nextLine();
        while (autor == null) {
            System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un nombre de autor \n");
            autor = in.nextLine();
        }
        autores.insertar_autor(autor);
        System.out.println("Instancia de autor insertada con éxito");
    }

    public void testEliminarAutor() throws Exception {
        System.out.println("Introduce el nombre del autor a eliminar \n");
        String autor = in.nextLine();
        while (autor == null) {
            System.out.println("ERROR: autor nulo. Por favor, vuelva a introducir un nombre de autor \n");
            autor = in.nextLine();
        }

        autores.eliminar_autor(autor);
        System.out.println("Instancia del autor " + autor + " eliminado con éxito");
    }

    public void testListarAutores() {
        System.out.println("Introduce el prefijo para listar autores \n");
        String prefijo = in.nextLine();
        while (prefijo == null) {
            System.out.println("ERROR: prefijo nulo. Por favor, vuelva a introducir un prefijo \n");
            prefijo = in.nextLine();
        }
        System.out.println("¿Cómo quieres listar los autores? Introduce un número \n (0) Orden alfabético \n (1) Orden alfabético reverso \n");
        int orden = in.nextInt();
        while (orden != 0 && orden != 1) {
            System.out.println("ERROR: número no válido. Por favor, vuelva a introducir un número \n");
            orden = in.nextInt();
        }
        System.out.println("Autores con prefijo '" + prefijo + "': \n" + autores.listar_autores(prefijo));
    }

    public void clearPersistencia() throws Exception{
        GestorAutor GA = new GestorAutor();
        GA.clear_autores();
    }

    public static void main (String[] args) throws Exception {
        autores = CjtAutores.getINSTANCE();
        DriverCjtAutores dv = new DriverCjtAutores();
        System.out.println("Driver Principal CjtAutores");
        System.out.println("");
        muestra_metodos();
        dv.in = new Scanner(System.in);
        String input = dv.in.nextLine();
        while(!input.equals("0") && !input.equals("sortir")) {
            switch (input) {
                case "1":
                case "insertarAutor": {
                    dv.testInsertarAutor();
                    break;
                }
                case "2":
                case "eliminarAutor": {
                    dv.testEliminarAutor();
                    break;
                }
                case "3":
                case "listarAutores": {
                    dv.testListarAutores();
                    break;
                }
                case "4":
                case "clearPersistencia":{
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
        System.out.println("(1|insertarAutor) - Se inserta un nuevo autor con el título de su archivo");
        System.out.println("(2|eliminarAutor) - Elimina el título del autor indicado");
        System.out.println("(3|listarAutores) - Lista todos los autores del sistema");
        System.out.println("(4|clearPersistencia) - Borra todos los datos persistidos");
        System.out.println("");
        System.out.println("(0|salir) - Cerrar Driver");
    }

    private void volver_Menu() {
        System.out.println("Pulsa ENTER para volver al menu principal");
        in.nextLine();
    }
}

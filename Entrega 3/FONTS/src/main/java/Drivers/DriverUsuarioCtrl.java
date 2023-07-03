package Drivers;

import Domain.UsuarioCtrl;
import Persistence.GestorUsuario;

import java.util.*;

public class DriverUsuarioCtrl {
    private UsuarioCtrl usuarioCtrl = null;
    private Scanner in;

    public void test_constructora() {
        usuarioCtrl = new UsuarioCtrl();
        System.out.println("UsuarioCtrl creado");
    }

    public void test_crear_perfil() throws Exception {
        if (usuarioCtrl == null) {
            System.err.println("Primero se tiene que crear el controlador!");
            return;
        }
        System.out.print("Username: ");
        String username = in.nextLine();
        while (username == null) {
            System.out.println("ERROR: username nulo. Por favor, vuelva a introducir un username");
            username = in.nextLine();
        }
        System.out.print("Contraseña: ");
        String contrasena1 = in.nextLine();
        while (contrasena1 == null) {
            System.out.println("ERROR: contraseña nula. Por favor, vuelva a introducir una contraseña");
            contrasena1 = in.nextLine();
        }
        System.out.print("Contraseña: ");
        String contrasena2 = in.nextLine();
        while (contrasena2 == null) {
            System.out.println("ERROR: contraseña nula. Por favor, vuelva a introducir una contraseña");
            contrasena2 = in.nextLine();
        }
        System.out.print("Nombre: ");
        String nombre = in.nextLine();
        while (nombre == null) {
            System.out.println("ERROR: nombre nulo. Por favor, vuelva a introducir un nombre");
            nombre = in.nextLine();
        }

        usuarioCtrl.crear_perfil(username, contrasena1, contrasena2, nombre);
        System.out.println("Usuario creado");
    }

    public void test_modificar_perfil() throws Exception {
        if (usuarioCtrl == null) {
            System.err.println("Primero se tiene que crear el controlador!");
            return;
        }
        System.out.print("Username del usuario a modificar: ");
        String user = in.nextLine();
        while (user == null) {
            System.out.println("ERROR: username nulo. Por favor, vuelva a introducir un username");
            user = in.nextLine();
        }
        System.out.print("Nuevo username: ");
        String username = in.nextLine();
        while (username == null) {
            System.out.println("ERROR: username nulo. Por favor, vuelva a introducir un username");
            username = in.nextLine();
        }
        System.out.print("Nuevo nombre: ");
        String nombre = in.nextLine();
        while (nombre == null) {
            System.out.println("ERROR: nombre nulo. Por favor, vuelva a introducir un nombre");
            nombre = in.nextLine();
        }

        usuarioCtrl.modificar_perfil(user, username, nombre);
        System.out.println("Usuario modificado");
    }

    public void test_cambiar_contrasena() throws Exception {
        if (usuarioCtrl == null) {
            System.err.println("Primero se tiene que crear el controlador!");
            return;
        }
        System.out.print("Username del usuario a modificar: ");
        String user = in.nextLine();
        while (user == null) {
            System.out.println("ERROR: username nulo. Por favor, vuelva a introducir un username");
            user = in.nextLine();
        }
        System.out.print("Contraseña actual: ");
        String contrasena = in.nextLine();
        while (contrasena == null) {
            System.out.println("ERROR: contraseña nula. Por favor, vuelva a introducir una contraseña");
            contrasena = in.nextLine();
        }
        System.out.print("Nueva contraseña: ");
        String nueva_contrasena = in.nextLine();
        while (nueva_contrasena == null) {
            System.out.println("ERROR: contraseña nula. Por favor, vuelva a introducir una contraseña");
            nueva_contrasena = in.nextLine();
        }
        usuarioCtrl.cambiar_contrasena(user, contrasena, nueva_contrasena);
        System.out.println("Contraseña modificada");
    }

    public void test_borrar_perfil() throws Exception{
        if (usuarioCtrl == null) {
            System.err.println("Primero se tiene que crear el controlador!");
            return;
        }
        System.out.print("Username del usuario a borrar: ");
        String user = in.nextLine();
        while (user == null) {
            System.out.println("ERROR: username nulo. Por favor, vuelva a introducir un username");
            user = in.nextLine();
        }

        usuarioCtrl.borrar_perfil(user);
        System.out.println("Usuario borrado");
    }

    public void test_iniciar_sesion() throws Exception{
        if (usuarioCtrl == null) {
            System.err.println("Primero se tiene que crear el controlador!");
            return;
        }
        System.out.print("Username: ");
        String username = in.nextLine();
        while (username == null) {
            System.out.println("ERROR: username nulo. Por favor, vuelva a introducir un username");
            username = in.nextLine();
        }
        System.out.print("Contraseña: ");
        String contrasena = in.nextLine();
        while (contrasena == null) {
            System.out.println("ERROR: contraseña nula. Por favor, vuelva a introducir una contraseña");
            contrasena = in.nextLine();
        }

        boolean b = usuarioCtrl.verificar_perfil(username, contrasena);
        if (b) System.out.println("Inicio sesion correcto");
    }

    public void clear_persistencia() throws Exception{
        GestorUsuario GU = new GestorUsuario();
        GU.clear_usuarios();
    }


    public static void main(String [] args) throws Exception {
        DriverUsuarioCtrl driverUsuarioCtrl = new DriverUsuarioCtrl();
        System.out.println("Driver UsuarioCtrl PROP Grup 12.3");
        System.out.println("");
        muestra_metodos();
        driverUsuarioCtrl.in = new Scanner(System.in);
        String input = driverUsuarioCtrl.in.nextLine();
        while(!input.equals("0") && !input.equals("salir")) {
            switch (input) {
                case "1":
                case "constructora" : {
                    driverUsuarioCtrl.test_constructora();
                    break;
                }
                case "2":
                case "crearperfil" : {
                    driverUsuarioCtrl.test_crear_perfil();
                    break;
                }
                case "3":
                case "modificarperfil" : {
                    driverUsuarioCtrl.test_modificar_perfil();
                    break;
                }
                case "4":
                case "cambiarcontrasena" : {
                    driverUsuarioCtrl.test_cambiar_contrasena();
                    break;
                }
                case "5":
                case "borrarperfil" : {
                    driverUsuarioCtrl.test_borrar_perfil();
                    break;
                }
                case "6":
                case "iniciarsesion" : {
                    driverUsuarioCtrl.test_iniciar_sesion();
                    break;
                }
                case "7":
                case "clearPersistencia" : {
                    driverUsuarioCtrl.clear_persistencia();
                    break;
                }
                default:
                    break;
            }
            driverUsuarioCtrl.volver_menu();
            muestra_metodos();
            input = driverUsuarioCtrl.in.nextLine();
        }
        driverUsuarioCtrl.in.close();

    }

    private static void muestra_metodos() {
        System.out.println("(1|constructora) - Constructora");
        System.out.println("(2|crearPerfil) - Crear Perfil");
        System.out.println("(3|modificarperfil) - Modificar Perfil");
        System.out.println("(4|cambiarcontrasena) - Cambiar Contraseña");
        System.out.println("(5|borrarperfil) - Borrar Perfil");
        System.out.println("(6|verificarperfil) - VerificarPerfil");
        System.out.println("(7|clearPersistencia) - Elimina todos los datos persistidos");
        System.out.println("");
        System.out.println("(0|salir) - Cerrar Driver");
    }

    private void volver_menu() {
        System.out.println("Apreta ENTER para volver al menú principal");
        in.nextLine();
    }
}

package Drivers;

import java.util.*;
import Domain.VectorMEV;

public class DriverVectorMEV {
    private VectorMEV vec = null;
    private Scanner in;

    public void testConstructora(){
        System.out.println("Introduce un texto a vectorizar");
        String texto = in.nextLine();
        if(texto == null || texto.equals("")){
            System.out.println("Error: texto vacio");
            return;
        }
        vec = new VectorMEV(texto);
    }

    public void testConsultaPesosTC(){
        if(vec == null){
            System.out.println("Primero se tiene que crear la instancia de VectorMEV!");
            return;
        }
        Map<String, Integer> returnMap = vec.consulta_pesos_TC();
        System.out.println(returnMap);
    }

    public void testConsultaSize(){
        if(vec == null){
            System.out.println("Primero se tiene que crear la instancia de VectorMEV!");
            return;
        }
        int size = vec.consulta_size();
        System.out.println("Tamano VectorMEV: " + size);
    }

    public static void main (String[] args) {
        DriverVectorMEV dv = new DriverVectorMEV();
        System.out.println("Driver Principal VectorMEV");
        System.out.println("");
        muestra_metodos();
        dv.in = new Scanner(System.in);
        String input = dv.in.nextLine();
        while(!input.equals("0") && !input.equals("sortir")){
            switch(input) {
                case "1":
                case "constructora": {
                    dv.testConstructora();
                    break;
                }
                case "2":
                case "consultarPesosTC":{
                    dv.testConsultaPesosTC();
                    break;
                }
                case "3":
                case "consultaSize":{
                    dv.testConsultaSize();
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
        System.out.println("(2|consultarPesosTC) - Consulta num. apariciones de palabras");
        System.out.println("(3|consultaSize) - Consulta tamano");
        System.out.println("");
        System.out.println("(0|salir) - Cerrar Driver");
    }

    private void volver_Menu() {
        System.out.println("Pulsa ENTER para volver al menu principal");
        in.nextLine();
    }
}
/* */
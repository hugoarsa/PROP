package Persistence;

import Domain.Archivo;
import Exceptions.FormatoInadecuadoException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ParserPROP {

    /**
     * Carga un archivo en el formato prop del path dado
     * @param path path del cual cargamos
     * @return archivo
     * @throws FileNotFoundException no encontrado el archivo
     * @throws FormatoInadecuadoException el formato es incorrecto
     */
    public Archivo cargar_archivo(String path) throws FileNotFoundException, FormatoInadecuadoException {
        //leo el contenido
        String titulo = null, autor = null, contenido = "";

        // pass the path to the file as a parameter
        File file = new File(path);
        Scanner sc = new Scanner(file);

        if(sc.hasNextLine()){
            titulo = sc.nextLine();
        } else throw new FormatoInadecuadoException(path);

        if(sc.hasNextLine()){
            autor = sc.nextLine();
        } else throw new FormatoInadecuadoException(path);

        if(!sc.hasNextLine()) throw new FormatoInadecuadoException(path);

        while(sc.hasNextLine()){
            contenido = contenido + sc.nextLine();
        }

        sc.close();
        String tituloStr = convertHexToString(titulo);
        String autorStr = convertHexToString(autor);
        if(caracter_ilegal(titulo) || caracter_ilegal(autor)) throw new FormatoInadecuadoException(path);
        else {
            return new Archivo(convertHexToString(titulo), convertHexToString(autor), "prop", convertHexToString(contenido));
        }
    }

    /**
     * Guarda un archivo a en el path path
     * @param a archivo a guardar
     * @param path lugar en el que guardarlo
     * @throws IOException error de escritura
     */
    public void guardar_archivo(Archivo a, String path) throws IOException {
        String filename = a.consulta_titulo() + "@" + a.consulta_autor() + "." + a.consulta_formato();

        File file = new File(path + filename);

        FileWriter out;
        out = new FileWriter(file);

        out.write(convertStringToHex(a.consulta_titulo()) + "\n");
        out.write(convertStringToHex(a.consulta_autor()) + "\n");
        out.write(convertStringToHex(a.consulta_contenido()) + "\n");

        out.close();
    }

    /**
     * funcion que pasa el string str a un hex
     * @param str string a pasar a hex
     * @return string en hex
     */
    private static String convertStringToHex(String str) {
        StringBuilder stringBuilder = new StringBuilder();

        char[] charArray = str.toCharArray();

        for (char c : charArray) {
            String charToHex = Integer.toHexString(c);

            if(charToHex.length()==1) charToHex = "0" + charToHex;

            stringBuilder.append(charToHex);
        }

        return stringBuilder.toString();
    }

    /**
     * funcion que pasa de hex a string
     * @param hex hex a transformar a string
     * @return string en cuestion
     */
    private static String convertHexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        char[] hexData = hex.toCharArray();
        for (int count = 0; count < hexData.length - 1; count += 2) {
            int firstDigit = Character.digit(hexData[count], 16);
            int lastDigit = Character.digit(hexData[count + 1], 16);
            int decimal = firstDigit * 16 + lastDigit;
            sb.append((char) decimal);
        }
        return sb.toString();
    }

    /**
     * el string contiene un caracter ilegal
     * @param s string a comprobar
     * @return booleano segun si tiene un caracter ilegal o no
     */
    private boolean caracter_ilegal(String s){
        String il = "<>:\"|?*\\/@";
        char[] ilegal = il.toCharArray();
        for(char c : ilegal) if(s.contains(c + "")) return true;
        return false;
    }
}

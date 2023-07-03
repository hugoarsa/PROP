package Persistence;

import Domain.Archivo;
import Exceptions.FormatoInadecuadoException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParserTXT {
    /**
     * funcion que carga un archivo txt del path
     * @param path lugar donde cargar
     * @return archivo cargad
     * @throws FileNotFoundException no encontramos el file
     * @throws FormatoInadecuadoException el formato no es correcto
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

            if(sc.hasNextLine()) contenido = contenido + "\n";
        }

        if(caracter_ilegal(titulo) || caracter_ilegal(autor)) throw new FormatoInadecuadoException(path);

        sc.close();

        return new Archivo(titulo,autor,"txt",contenido);
    }

    /**
     * guarda un archivo txt en el path
     * @param a archivo a guardar
     * @param path path donde guardarlo
     * @throws IOException error de escritura
     */
    public void guardar_archivo(Archivo a, String path) throws IOException, FormatoInadecuadoException {
        String filename = a.consulta_titulo() + "@" + a.consulta_autor() + "." + a.consulta_formato();

        File file = new File(path + filename);

        FileWriter out;
        out = new FileWriter(file);

        if(caracter_ilegal(a.consulta_titulo()) || caracter_ilegal(a.consulta_autor())) throw new FormatoInadecuadoException(path);
        out.write(a.consulta_titulo() + "\n");
        out.write(a.consulta_autor() + "\n");
        out.write(a.consulta_contenido() + "\n");
        out.close();
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

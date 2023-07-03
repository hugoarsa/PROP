package Persistence;

import Domain.Archivo;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import Exceptions.AtributoNuloException;
import Exceptions.FormatoInadecuadoException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserXML {
    /**
     * carga un archivo xml haciendo uso de parser
     * @param path path del que cargamos
     * @return archivo cargado
     * @throws ParserConfigurationException mala configuracion parser
     * @throws IOException error de lectura
     * @throws SAXException error interno de persistencia al parsear
     * @throws FormatoInadecuadoException formato inadecuado
     */
    public Archivo cargar_archivo(String path) throws ParserConfigurationException, IOException, SAXException, FormatoInadecuadoException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(path);
        doc.getDocumentElement().normalize();
        String titulo = doc.getElementsByTagName("titulo").item(0).getTextContent();
        String autor = doc.getElementsByTagName("autor").item(0).getTextContent();
        String contenido = doc.getElementsByTagName("contenido").item(0).getTextContent();
        String formato = "xml";
        if(caracter_ilegal(titulo) || caracter_ilegal(autor)) {
            throw new FormatoInadecuadoException(path);
        }
        return new Archivo(titulo, autor, formato, contenido);
    }

    /**
     * guarda un archivo en el path
     * @param arc archivo a guardar
     * @param path path en el que guardar
     * @throws ParserConfigurationException error interno
     * @throws IOException error de escritura
     * @throws TransformerException error interno
     */
    public void guardar_archivo(Archivo arc, String path) throws ParserConfigurationException, IOException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("body");
        doc.appendChild(rootElement);

        Element titulo = doc.createElement("titulo");
        titulo.setTextContent(arc.consulta_titulo());
        rootElement.appendChild(titulo);

        Element autor = doc.createElement("autor");
        autor.setTextContent(arc.consulta_autor());
        rootElement.appendChild(autor);

        Element contenido = doc.createElement("contenido");
        contenido.setTextContent(arc.consulta_contenido());
        rootElement.appendChild(contenido);

        String filename = arc.consulta_titulo() + "@" + arc.consulta_autor() + ".xml";
        write_xml(doc, new FileOutputStream(path + filename));
    }

    /**
     * escribe un xml
     * @param doc documento a escribir
     * @param output stream de output
     * @throws TransformerException error interno
     * @throws IOException error de escritura
     */
    private static void write_xml(Document doc, FileOutputStream output) throws TransformerException, IOException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

        output.close();
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

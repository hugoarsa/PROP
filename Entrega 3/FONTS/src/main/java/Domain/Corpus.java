package Domain;

import java.io.IOException;
import java.util.*;
import Exceptions.ArchivoNoExisteException;
import Exceptions.ArchivoYaExisteException;
import Persistence.GestorCorpus;


public class Corpus {
    /**
     * Mapa que registra el núm. documentos en los que aparece cada palabra del corpus
     */
    private Map<String, Integer> DC;
    /**
     * Mapa que asocia un ID de Archivo a su VectorMEV correspondiente
     */
    private Map<Pair<String, String>, VectorMEV> archivoVector;
    /**
     * Entero que registra la suma de tamaños de textos del corpus (usado para calcular medias)
     */
    private int sizeSum;
    /**
     * Referencia a la clase GestorCorpus para servirse de sus funcionalidades y persistir los valores
     */
    private GestorCorpus GC;

    /**
     * Crea un Corpus vacío 
     */
    public Corpus() throws IOException, ClassNotFoundException {
        GC = new GestorCorpus();
        DC = GC.get_DC();
        archivoVector = GC.get_archivoVector();
        sizeSum = GC.get_sizeDocSum();
    }

    /**
     * Añade un Archivo al Corpus, actualizando los contadores de DC y 
     * creando un VectorMEV que vectorize el String contenido de entrada en el proceso.
     * @param titulo
     * @param autor
     * @param contenido
     * @exception ArchivoYaExisteException Salta cuando el archivo ya existe
     * @exception IOException Salta cuando hay errores de lectura
     */
    public void anadir_archivo(String titulo, String autor, String contenido) throws ArchivoYaExisteException, IOException {
        Pair<String, String> id = new Pair<String,String>(titulo, autor);
        if(archivoVector.containsKey(id))
            throw new ArchivoYaExisteException(titulo, autor);
        else {
            VectorMEV vec = new VectorMEV(contenido);
            archivoVector.put(id, vec);
            GC.guardar_archivoVector(id.first(), id.second(), vec);
            Map<String, Integer> words = vec.consulta_pesos_TC();
            sizeSum = sizeSum + vec.consulta_size();
            GC.guardar_sizeDocSum(sizeSum);
            for (Map.Entry<String, Integer> entry : words.entrySet()) {
                Integer val = DC.get(entry.getKey());
                if (val != null) {
                    DC.put(entry.getKey(), val + 1);
                    GC.guardar_DC(entry.getKey(), val + 1);
                } else {
                    DC.put(entry.getKey(), 1);
                    GC.guardar_DC(entry.getKey(), 1);
                }
            }
        }
    }
    /**
     * Elimina un Archivo al Corpus, actualizando los contadores
     * de DC en el proceso.
     * @param titulo
     * @param autor
     * @exception ArchivoNoExisteException Salta cuando el archivo no existe
     * @exception IOException Salta cuando hay errores de lectura
     */
    public void borrar_archivo(String titulo, String autor) throws ArchivoNoExisteException, IOException {
        Pair<String, String> id = new Pair<String, String>(titulo, autor);
        if(!archivoVector.containsKey(id)) {
            throw new ArchivoNoExisteException(titulo, autor);
        }
        else {
            VectorMEV vec = archivoVector.get(id);
            Map<String, Integer> words = vec.consulta_pesos_TC();
            sizeSum = sizeSum - vec.consulta_size();
            GC.guardar_sizeDocSum(sizeSum);
            for (Map.Entry<String, Integer> entry : words.entrySet()) {
                Integer val = DC.get(entry.getKey());
                if (val > 1) {
                    DC.put(entry.getKey(), val - 1);
                    GC.guardar_DC(entry.getKey(), val - 1);
                } else {
                    DC.remove(entry.getKey());
                    GC.eliminar_DC(entry.getKey());
                }
            }
            archivoVector.remove(id);
            GC.eliminar_archivoVector(id.first(), id.second());
        }
    }

    /**
     * Compara la semejanza de los archivos identificados por {tituloA, autorA} y {tituloB, autorB}
     * según la estrategia de asignación de pesos en procesado de lenguaje seleccionada:
     * - mode = 0: TF-iDF
     * - mode = 1: Okapi BM25
     * Devuelve la semejanza como el "coseno" de los 2 archivos (flotante que va des de 0 si no son
     * nada similares a 1 si son esencialmente iguales)
     * @param tituloA
     * @param autorA
     * @param tituloB
     * @param autorB
     * @param mode
     * @return cos
     * @exception ArchivoNoExisteException Salta cuando el archivo no existe
     */
    public double comparar_archivos(String tituloA, String autorA, String tituloB, String autorB, int mode) throws ArchivoNoExisteException {
        Pair<String, String> idA = new Pair<String, String>(tituloA, autorA);
        Pair<String, String> idB = new Pair<String, String>(tituloB, autorB);
        if(!archivoVector.containsKey(idA))
            throw new ArchivoNoExisteException(tituloA, autorA);
        else if (!archivoVector.containsKey(idB))
            throw new ArchivoNoExisteException(tituloB, autorB);
        else {
            VectorMEV vecA = archivoVector.get(idA);
            VectorMEV vecB = archivoVector.get(idB);
            Map<String, Integer> TFA = vecA.consulta_pesos_TC();
            Map<String, Integer> TFB = vecB.consulta_pesos_TC();
            int sizeA = vecA.consulta_size();
            int sizeB = vecB.consulta_size();
            double cos = evaluar_pesos(TFA, TFB, sizeA, sizeB, DC, mode);
            return cos;
        }
    }

    /**
     * Compara la semejanza del archivo identificado por {titulo, autor} con el String de entrada "query"
     * según la estrategia de asignación de pesos en procesado de lenguaje seleccionada:
     * - mode = 0: TF-iDF
     * - mode = 1: Okapi BM25
     * Devuelve la semejanza como el "coseno" del archivo con la query (flotante que va des de 0 si no son
     * nada similares a 1 si son esencialmente iguales)
     * @param titulo
     * @param autor
     * @param query
     * @param mode
     * @return cos
     * @exception ArchivoNoExisteException Salta cuando el archivo no existe
     */
    public double comparar_query(String titulo, String autor, String query, int mode) throws ArchivoNoExisteException{
        Pair<String, String> idA = new Pair<String, String>(titulo, autor);
        if(!archivoVector.containsKey(idA))
            throw new ArchivoNoExisteException(titulo, autor);
        else {
            VectorMEV vecA = archivoVector.get(idA);
            Map<String, Integer> TFA = vecA.consulta_pesos_TC();
            int sizeA = vecA.consulta_size();

            VectorMEV vecQuery = new VectorMEV(query);
            Map<String, Integer> TFQuery = vecQuery.consulta_pesos_TC();
            int sizeQuery = 0;
            Map<String, Integer> TFQueryClean = new HashMap<String, Integer>();
            for (Map.Entry<String, Integer> entry : TFQuery.entrySet()) {
                if (DC.containsKey(entry.getKey())) {
                    sizeQuery = sizeQuery + entry.getValue();
                    TFQueryClean.put(entry.getKey(), entry.getValue());
                }
            }
            if (sizeQuery <= 0) return 0.0;
            double cos = evaluar_pesos(TFA, TFQueryClean, sizeA, sizeQuery, DC, mode);
            return cos;
        }
    }

    /**
     * Consulta si existe el archivo identificado por {título, autor} en el Corpus
     * @param titulo
     * @param autor
     * @return result
     */
    public boolean existe_archivo(String titulo, String autor){
        boolean result = archivoVector.containsKey(new Pair<String, String>(titulo, autor));
        return result;
    }

    /**
     * Usa los mapas de pesos TFA y TFB para construir vectores del Modelo de Espacio Vectorial y
     * poder comparar la semejanza de ellos por producto vectorial segun la estrategia
     * de asignación de pesos escogida:
     * - mode = 0: TF-iDF
     * - mode = 1: Okapi BM25
     * Devuelve la semejanza como el "coseno" de los dos vectores (flotante que va des de 0 si no son
     * nada similares a 1 si son esencialmente iguales)
     * @param TFA
     * @param TFB
     * @param sizeA
     * @param sizeB
     * @param DC
     * @param mode
     * @return cos
     */
    private Double evaluar_pesos(Map<String, Integer> TFA, Map<String, Integer> TFB, int sizeA, int sizeB, Map<String, Integer> DC, int mode){
        Double[] arrayPesosFinalesA = new Double[DC.size()];
        Double[] arrayPesosFinalesB = new Double[DC.size()];
        Iterator<Map.Entry<String, Integer>> itrA = TFA.entrySet().iterator();
        Map.Entry<String, Integer> wordTCA = itrA.next();
        Iterator<Map.Entry<String, Integer>> itrB = TFB.entrySet().iterator();
        Map.Entry<String, Integer> wordTCB = itrB.next();
        int i = 0;
        for(Map.Entry<String, Integer> wordDC : DC.entrySet()){
            if(wordTCA == null) arrayPesosFinalesA[i] = 0.0;
            else{
                if(!wordTCA.getKey().equals(wordDC.getKey())) arrayPesosFinalesA[i] = 0.0;
                else {
                    if(mode == 0) arrayPesosFinalesA[i] = valor_tfidf(wordTCA.getValue(), wordDC.getValue(), sizeA, archivoVector.size());
                    else if(mode == 1) arrayPesosFinalesA[i] = valor_bm25(wordTCA.getValue(), wordDC.getValue(), sizeA, sizeSum/archivoVector.size());
                    if(itrA.hasNext()) wordTCA = itrA.next();
                    else wordTCA = null;
                }
            }
            if(wordTCB == null) arrayPesosFinalesB[i] = 0.0;
            else{
                if(!wordTCB.getKey().equals(wordDC.getKey())) arrayPesosFinalesB[i] = 0.0;
                else {
                    if(mode == 0) arrayPesosFinalesB[i] = valor_tfidf(wordTCB.getValue(), wordDC.getValue(), sizeB, archivoVector.size());
                    else if(mode == 1) arrayPesosFinalesB[i] = valor_bm25(wordTCB.getValue(), wordDC.getValue(), sizeB, sizeSum/archivoVector.size());
                    if(itrB.hasNext()) wordTCB = itrB.next();
                    else wordTCB = null;
                }
            }
            i++;
        }
        double cos = cos(arrayPesosFinalesA, arrayPesosFinalesB);
        return cos;
    }

    /**
     * Aplica la fórmula del modelo TF-IDF para calcular el peso de una determinada palabra
     * de un texto, en función de su num. apariciones dentro del texto (tc), su num. apariciones dentro
     * del corpus (dc), el tamaño del documento (sizeDoc) y el num. de documentos del Corpus (sizeCorpus)
     * @param tc
     * @param dc
     * @param sizeDoc
     * @param sizeCorpus
     * @return valor
     */
    private Double valor_tfidf(int tc, int dc, int sizeDoc, int sizeCorpus){
        //fórmula TF-iDF
        double valor = tc/(sizeDoc*1.0)*(1+Math.log(sizeCorpus/dc));
        return valor;
    }

    /**
     * Aplica la fórmula del modelo Okapi BM25 para calcular el peso de una determinada palabra de un texto,
     * en función de su num. apariciones dentro del texto (tc), su num. apariciones dentro del corpus (dc), el
     * tamaño del documento (sizeDoc) y el tamaño medio de un documento en el corpus (avgSizeDoc)
     * @param tc
     * @param dc
     * @param sizeDoc
     * @param avgSizeDoc
     * @return tf*idf
     */
    private Double valor_bm25(int tc, int dc, int sizeDoc, int avgSizeDoc){
        //constantes de cálculo
        double k1 = 2.0;
        double b = 0.75;
        //fórmula Okapi BM25
        double tf = (tc*(k1 + 1.0))/(tc + k1*(1 - b + b*(sizeDoc/(avgSizeDoc))));
        double idf = Math.log((archivoVector.size() - dc + 0.5)/(dc + 0.5) + 1);
        return tf*idf;
    }

    /**
     * Funcion encargada de calcular el coseno de dos vectores pasados por parametro
     * @param vecA
     * @param vecB
     * @return result
     */
    private Double cos(Double[] vecA, Double[] vecB){
        //System.out.println("we calculate cosine" + vecA.length);
        double sumA = 0.0;
        double sumB = 0.0;
        double sumMerged = 0.0;
        for(int i = 0; i < vecA.length; i++){
            sumA = sumA + vecA[i]*vecA[i];
            sumB = sumB + vecB[i]*vecB[i];
            sumMerged = sumMerged + vecA[i]*vecB[i];
        }
        Double result = sumMerged/(Math.sqrt(sumA)*Math.sqrt(sumB));
        return result;
    }

}

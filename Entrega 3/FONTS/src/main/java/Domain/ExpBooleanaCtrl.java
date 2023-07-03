package Domain;

import java.io.IOException;
import java.util.*;
import java.lang.String;

import Exceptions.*;
import Domain.ExpBooleana;
import Domain.Pair;

import Persistence.GestorBooleana;

public class ExpBooleanaCtrl {
    /**
     * Map con clave el string que identifica una expresion y contenido la propia expresion
     */
    private Map<String, ExpBooleana> expresiones;

    /**
     * id maximo asignado hasta el momento, necesario para actualizar la cola con los ids disponibles
     */
    private Integer max_id;
    /**
     * queue disp de id disponibles
     */
    private Queue<Integer> disp;
    /**
     * Referencia a la clase GestorBoolrana para servirse de sus funcionalidades y persistir las expresiones
     */
    private GestorBooleana GB;

    /**
     * Creadora sin valor.
     * Crea un conjunto vacío de expresiones en ExpBoleanaCtrl,
     * siendo un hash map con los valores persistidos
     * @exception IOException exception de entrada y salida
     * @exception ClassNotFoundException clase no encontrada correctamente
     */
    public ExpBooleanaCtrl() throws IOException, ClassNotFoundException {
        //inicializar el GestorPersistencia
        GB = new GestorBooleana();

        Pair<Map<String, ExpBooleana>,Pair<Queue<Integer>,Integer>> P = GB.get_all();

        //recargo el map desde la persistencia
        expresiones = P.first();
        disp = P.second().first();
        max_id = P.second().second();
    }

    /**
     * Guarda una expresion nueva de clave exp en el conjunto de expresiones
     * si esta no está presente ya en el mismo. Con los cambios en persistencia
     * @param exp Clave primaria de la expresion a crear
     * @exception IOException exception de entrada y salida
     * @exception CaracterIlegalException hay caracteres ilegales en la expresion
     * @exception MalParentesisException hay mala parentizacion
     * @exception MalUsoOperandosException los operandos estan mal colocados
     * @exception ComillasException las comillas no se usan correctamente
     */
    public void alta_expresion(String exp) throws CaracterIlegalException, MalParentesisException, MalUsoOperandosException, ComillasException, IOException {//si no contiene la llave se la pongo
        System.out.println("alta expresion: " + exp);
        if(!expresiones.containsKey(exp)){
            //asignamos el id segun el siguente disponible para evitar fragmentacion en los indices
            Integer next_id = disp.peek();
            disp.remove();
            System.out.println("alta expresion nextid: " + next_id);
            System.out.println("alta expresion maxid: " + max_id);
            //si el elemento id que asigno es el más grande aumento en uno el mas grande
            if(Objects.equals(next_id, max_id)){
                max_id = max_id + 1;
                disp.add(max_id);
            }
            System.out.println("alta expresion new maxid: " + max_id);

            ExpBooleana temp = new ExpBooleana(exp, next_id);

            GB.guardar_expresion(temp);
            expresiones.put(exp, temp);
        }

    }

    /**
     * Elimina una expresion de clave exp en el conjunto de expresiones,
     * si no está presente en el mismo se lanza un excepcion
     * @param exp Clave primaria de la expresion a borrar
     * @exception NoExisteExpresionException La expresion que se pretende borrar no existe
     */
    public void borrar_expresion(String exp) throws NoExisteExpresionException {
        if(expresiones.containsKey(exp)) {
            //su id ahora se encuentra disponible pues ya no lo necesitamos
            disp.add(expresiones.get(exp).to_Id());

            //eliminamos de persistencia el elemento
            GB.eliminar_expresion(expresiones.get(exp).to_Id());

            //lo sacamos del conjunto
            expresiones.remove(exp);
        }
        else throw new NoExisteExpresionException(exp);
    }

    /**
     * Se genera un conjunto Set con todas las claves presentes actualmente
     * en el conjunto de expresiones
     * @return cjt_expresiones
     */
    public Set<String> listar_expresiones() {
        Set<String> cjt_expresiones = new HashSet<String>();
        for (Map.Entry<String, ExpBooleana> entry : expresiones.entrySet()) {
            cjt_expresiones.add(entry.getKey());
        }
        return cjt_expresiones;
    }

    /**
     * Devuelve la ExpBooleana asociada al string exp
     * @param exp Clave primaria de la expresion a extraer
     * @exception NoExisteExpresionException La expresion que se pretende extraer no existe
     * @return expresiones.get(exp)
     */
    public ExpBooleana consultar_expresion(String exp) throws NoExisteExpresionException {
        if(!expresiones.containsKey(exp)) throw new NoExisteExpresionException(exp);//si no existe excepcion
        return expresiones.get(exp);
    }
}




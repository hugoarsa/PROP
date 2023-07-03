package Domain;

import Exceptions.*;


public class ExpBooleana implements java.io.Serializable
{
    /**
     * Id mediante el cual identificaremos la exp booleana en persistencia y el conjunto de expresiones
     */
    Integer id = 0;
    /**
     * String que contiene la expresión inicial introducida para poder mostrarla en pantalla si es necesario
     */
    private String expStr = ""; //string que contiene la expresion natural
    /**
     * nodo raíz que contiene el arbol que evalúa la expresión booleana
     */
    Node root;             //nodo que contiene el arbol

    /**
     * Creadora de la ExpBooleana mediante una clave string exp
     * se generan todas las estructuras arborescentes internas
     * @param exp Clave a partir de la cual se crea la expresion
     * @exception MalParentesisException Salta si tenemos parentesis mal puestos
     * @exception MalUsoOperandosException Salta si tenemos operandos mal definidos
     * @exception ComillasException Salta si tenemos comillas mal definidas
     * @exception CaracterIlegalException Salta si tenemos un caracter ilegal en la expresion
     */
    public ExpBooleana(String exp, Integer identifier) throws CaracterIlegalException, MalParentesisException, MalUsoOperandosException, ComillasException {
        //Miramos que no tenga caracteres ilegales dentro de la expresion
        String il = "[.:?]+";
        char[] ilegal = il.toCharArray();
        for(char c : ilegal) if(exp.contains(c + "")) throw new CaracterIlegalException(exp,c);


        expStr=exp;
        id = identifier;
        root = new Node(expStr);
        build_tree(root);
    }

    /**
     * Se devluelve el identificador mediante el cual se crean las estructuras
     * internas de la clase en forma de string
     * @return s
     */
    public String to_String()
    {
        return expStr;
    }

    public Integer to_Id()
    {
        return id;
    }

    /**
     * Funcion encargada de evaluar la veracidad de una sentencia str_eval en
     * relacion a expresion booleana asociada a la clase.
     * Esta se sirve de una funcion de inmersion privada para hacer la recursividad
     * necesaria para evaluar en tiempo lineal la sentencia
     *
     * @return result
     */
    public boolean match(String str_eval)
    { // Return the boolean evaluation of an expression
        boolean result = false;
        result = matchH(root, str_eval);
        return result;
    }

    /**
     * Función recursiva encargada de la creacion del arbol de expresion booleana
     *
     * @param m Este es el nodo a tratar
     * @exception MalParentesisException Salta si tenemos parentesis mal puestos
     * @exception MalUsoOperandosException Salta si tenemos operandos mal definidos
     * @exception ComillasException Salta si tenemos comillas mal definidas
     */
    private void build_tree(Node m) throws MalParentesisException, MalUsoOperandosException, ComillasException { // Construye el arbol a partir de la expresion
        String x = m.getData();

        //Recorrido en O(n) para quitar espacios
        if(!x.isEmpty()){
            int t1 = 0;
            while(t1<x.length() && x.charAt(t1)==' '){
                ++t1;
            }
            int t2 = x.length()-1;
            while(t2>t1 && x.charAt(t2)==' '){
                --t2;
            }
            x = x.substring(t1,t2+1);
        }

        m.setData(x);

        //trato los parentesis con el objeto para poder modificar la clase
        //aqui quito parentesis innecesarios compruebo sintaxis etc.
        //lanza excepciones
        //tarda un tiempo O(n)
        tratar_parentesis(m);

        x = m.getData();

        //busco el splitting element, el cual es el de menos prioridad
        //tarda un tiempo O(n)
        int r = find_split(x);

        //si no hay elemento de split estamos en un caso base pues la expresion no tiene opreandos logicos
        if(r == -1)
        {
            if(m.getData().charAt(0)=='{') {
                m.setContent(1);     //tenemos una comprobacion de grupo (tipo 1)
                m.setData(x.substring(1,x.length()-1));
            }

            //fusionarlas si se puede
            else if(m.getData().charAt(0)=='\"') {
                m.setContent(2);//tenemos una comprobacion de frase (tipo 2)
                m.setData(x.substring(1,x.length()-1));
            }
            else m.setContent(3);     //tenemos una comprobacion palabra(tipo 3)
            return;
        }

        char splitChar = x.charAt(r); //El caracter menos importante para partir


        if(splitChar == '|' || splitChar == '&') //tenemos opreando binario asi que ponemos a derecha e izquierda lo que toque
        {
            String left = x.substring(0, r);
            String right = x.substring(r+1, x.length());
            m.setData(splitChar + "");
            m.setLeft(new Node(left));
            m.setRight(new Node(right));

            build_tree(m.getRight());
            build_tree(m.getLeft());
        }
        else //tenemos un not asi que hcemos una separacion unaria ya que a la izquierda no tenemos nada
        {
            String right = x.substring(r+1, x.length());
            m.setData(splitChar + "");
            m.setRight(new Node(right));
            build_tree(m.getRight());
        }
    }

    /**
     * Funcion lineal encargada de eliminar parentesis sobrantes y comprobar que esten
     * siendo usados correctamente dentro de la expresion
     *
     * @param m Este es el nodo a tratar
     * @exception MalParentesisException Salta si tenemos parentesis mal puestos
     * @exception MalUsoOperandosException Salta si tenemos operandos mal definidos
     * @exception ComillasException Salta si tenemos comillas mal definidas
     */
    private void tratar_parentesis(Node m) throws MalUsoOperandosException, MalParentesisException, ComillasException  //ahora esto devuelve expresiones
    {
        //tengo que hacer que si hay dos parentesis seguidos vuelva a comprobar if(eliminate parentesis, repetir)
        String expr = m.getData();


        if(expr.isEmpty()) throw new MalUsoOperandosException(expr); //esto es malo devolvera un void tengo que pillar la grande

        int parentesis = 0;

        boolean clau = false;
        boolean comi = false;

        boolean eliminate_par = expr.length()>2 & expr.charAt(0) == '(';//como no esta empty al menos tenemos la pos 0

        // Traversing the Expression
        for (int i = 0; i < expr.length(); i++)
        {
            char x = expr.charAt(i);

            if(clau){
                if (x == '{') throw new MalParentesisException(expr); //abrir un claudator dentro de otro, no tiene logica
                else if(x == '}') clau = false;
            } else if(comi){
                if(x == '\"') comi = false;
            } else{
                if (x == '(') parentesis = parentesis + 1;
                else if(x == ')'){
                    if(parentesis == 0) throw new MalParentesisException(expr); //cerrar un parentesis no abierto
                    else {
                        parentesis = parentesis - 1;
                        if(parentesis == 0 && (i + 1 != expr.length())) eliminate_par = false;
                    }
                }
                else if(x == '\"') comi = true;
                else if (x == '{') clau = true;
                else if (x == '}') throw new MalParentesisException(expr); //claudator cerrado sin abrirlo antes
            }
        }

        if(parentesis>0) throw new MalParentesisException(expr); //acabas parentesis abiertos
        if(comi) throw new ComillasException(expr); //si acabas en comillas abiertas

        //eliminamos los parentesis innecesarios
        if(eliminate_par && expr.charAt(expr.length()-1)==')'){
            expr = expr.substring(1,expr.length()-1);

        } else eliminate_par = false;



        if(expr.isEmpty()) throw new MalUsoOperandosException(expr);

        m.setData(expr);

        if(eliminate_par && expr.charAt(0) == '(' && expr.charAt(expr.length()-1) == ')') tratar_parentesis(m);
    }

    /**
     * Funcion lineal encargada de encontrar el elemento de menor precedencia en un
     * String s, siendo este el elemento menos prioritario
     *
     * @param s String en el cual encontraremos el split
     */
    private int find_split(String s)
    {
        char[] x = s.toCharArray();
        int[] r = new int[s.length()];
        int rank = 0;
        int rank_min = -2;
        int index = 0;

        boolean clau = false;
        boolean comi = false;

        //recorrido en O(n)
        for(char c : x)
        {
            //con esto me aseguro de no incrementar ni buscar ands dentro de los lugares especiales
            if(clau){
                if (c == '{') return 3;
                else if(c == '}') clau = false;
                r[index] = -1;
            } else if(comi){

                if(c == '\"') {
                    comi = false;
                }
                r[index] = -1;
            } else {
                //si es un operador logico definir su rango
                if(c == '&' || c == '|' || c == '!') {
                    //defino el rango del operador
                    r[index] = rank;

                    //actualizamos el rango minimo
                    if(rank_min == -2) rank_min = rank;
                    else {
                        if(rank<rank_min) rank_min = rank;
                    }
                }
                //si no lo es retocar el rango pertinente
                else
                {
                    switch(c)
                    {
                        case '(':
                            r[index] = -1;
                            rank++;
                            break;
                        case ')':
                            r[index] = -1;
                            rank--;
                            break;
                        case '{':
                            clau = true;
                            r[index] = -1;
                            break;
                        case '\"':
                            comi = true;
                            r[index] = -1;
                        default:
                            r[index] = -1;
                    }
                }
            }
            index++;
        }

        //recorrido en 0(n)
        int and_pos = -1, or_pos = -1, not_pos = -1;
        for(int i = 0; i < s.length(); ++i){
            //System.out.print(r[i]);
            if(r[i]==rank_min && r[i]!=-1){
                switch(x[i]){
                    case '|':
                        if(or_pos==-1) or_pos = i;
                        break;
                    case '&':
                        if(and_pos==-1) and_pos = i;
                        break;
                    case '!':
                        if(not_pos==-1) not_pos = i;
                }
            }
        }

        //miro en orden de prioridad cual es el primero que hay
        if (or_pos!=-1) return or_pos;
        else if (and_pos!=-1)return and_pos;
        else if (not_pos!=-1) return not_pos;
        return -1;
    }

    /**
     * Funcion de immersion encargada de evaluar string e segun el arbol
     * de expresion binaria
     *
     * @param n Nodo en el que nos encontramos del arbol
     * @param e String a evaluar
     */
    private boolean matchH(Node n, String e)
    { // Recursive helper function for evaluate()
        if(n.getLeft() == null && n.getRight() == null) { //es hoja
            switch(n.getContent()){
                case 1:
                    boolean b = true;
                    String[] words = n.getData().split("\\s",0);//separo por espacios para hacer la busqueda de cada palabra
                    for(int i = 0; i < words.length & b; ++i){//miro que contenga individualmente todas las palabras con lazy check
                        b = b && e.contains(words[i]);
                    }
                    return b;
                case 2:
                    return e.contains(n.getData());
                case 3:
                    return e.contains(n.getData());
            }
        }
        else if (n.getData().equals("&")) // And
        {
            return matchH(n.getLeft(),e) && matchH(n.getRight(),e);
        }
        else if (n.getData().equals("|")) // or
        {
            return matchH(n.getLeft(),e) || matchH(n.getRight(),e);
        }
        else if (n.getData().equals("!")) // not
        {
            return !matchH(n.getRight(),e);
        }

        // evaluates this expression expression.
        return false;
    }

    private class Node implements java.io.Serializable
    {
        Node left, right;
        String simbolo;
        int content;

        public Node(String s)
        {
            left = null;
            right = null;
            simbolo = s;
            content = 0;
        }

        public Node(String s, Node r, Node l)
        {
            left = l;
            right = r;
            simbolo = s;
            content = 0;
        }

        public void setContent(int c){content = c;}

        public int getContent()
        {
            return content;
        }

        public void setRight(Node r)
        {
            right = r;
        }

        public Node getRight()
        {
            return right;
        }

        public void setLeft(Node l)
        {
            left = l;
        }

        public Node getLeft()
        {
            return left;
        }

        public String getData()
        {
            return simbolo;
        }

        public void setData(String s)
        {
            simbolo = s;
        }

    }
}

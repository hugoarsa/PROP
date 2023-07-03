package Domain;

import java.io.*;

public class Pair<F,S>
{
    /**
     * primer elemento
     */
    private F first;
    /**
     * segundo elemento
     */
    private S second;

    /**
     * declaradora basica
     * @param first primer elem
     * @param second segundo elem
     */
    public Pair(F first, S second){
        this.first = first;
        this.second = second;
    }

    /**
     * pone first como el primer parametro
     * @param first
     */
    public void setFirst(F first){
        this.first = first;
    }

    /**
     * pone second como el segundo parametro
     * @param second
     */
    public void setSecond(S second){
        this.second = second;
    }

    /**
     * constultora del primero
     */
    public F first(){
        return first;
    }

    /**
     * consultora del segundo
     */
    public S second(){
        return second;
    }
    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof Pair))
            return false;
        Pair other = (Pair)o;
        boolean firstEquals = (this.first == null && other.first == null)
                || (this.first != null && this.first.equals(other.first));
        boolean secondEquals = (this.second == null && other.second == null)
        || (this.second != null && this.second.equals(other.second));
        return firstEquals && secondEquals;
    }

    @Override
    public final int hashCode(){
        int result = 17;
        if(first != null){
            result = 31 * result + first.hashCode();
        }
        if(second != null){
            result = 31 * result + second.hashCode();
        }
        return result;
    }

}

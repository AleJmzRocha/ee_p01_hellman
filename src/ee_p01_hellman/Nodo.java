package ee_p01_hellman;

public class Nodo<T extends Comparable<T>> {
	/**
	 * El nodo contendrá:
	 * El dato declarado como dato
	 * La liga declarado como siguiente;
	 */
    private T dato;
    private Nodo<T> siguiente;
    
    /**
     * Construtor que incializa los atributos pasando como parámetro el dato
     * @param dato
     */
    public Nodo(T dato){
        this.dato = dato;
        siguiente = null;
    }
    
    /**
     * Método que permite obtener el dato del nodo
     * @return dato
     */
    public T getDato(){
        return dato;
    }
    
    /**
     * Método que permite cambiar el dato del nodo
     * @param dato
     */
    public void setDato(T dato){
        this.dato = dato;
    }
    
    /**
     * Método que permite obtener el nodo siguiente
     * @return siguiente
     */
    public Nodo<T> getSiguiente(){
        return siguiente;
    }
    
    /**
     * Método que permite cambiar la liga del nodo
     * @param siguiente
     */
    public void setSiguiente(Nodo<T> siguiente){
        this.siguiente = siguiente;
    }
}
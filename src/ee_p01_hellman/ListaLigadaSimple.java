package ee_p01_hellman;

import javax.swing.JOptionPane;
/**
 * @authors Martinez Carrera Dulce Carolina
 * 			S�nchez Lazcares Perla Melina
 * 			Jim�nez Rocha Alejandra
 * 
 * Clase que permitir� generar listas gen�ricas.
 */

public class ListaLigadaSimple<T extends Comparable<T>> {
	/**
	 * Creaci�n del nodo que contendr� el dato y la liga.
	 */
	private Nodo<T> inicio;
	/**
	 * Constructor que incializa la lista en nula.
	 */
	public ListaLigadaSimple(){
    	inicio = null;
    }
	
	/**
	 * M�todo que permite obtener el inicio de la lista.
	 * @param inicio
	 */
	public Nodo<T> getInicio(){
        return inicio;
    }
	
	/**
	 * M�todo que permite cambiar el inicio de la lista.
	 * @param inicio
	 */
	public void setInicio(Nodo<T> inicio){
		this.inicio = inicio;
	}
	
	/**
     * Este m�todo sobrescribe el m�todo toString() de la clase Object.
     * Imprime la lista con las datos de �sta misma.
     */
    @Override
    public String toString(){
        Nodo<T> iterador = inicio;
        String s = "";
         while( iterador != null ){ 
             s += iterador.getDato() + " --> ";
             iterador = iterador.getSiguiente(); 
         } 
         s += null;
         return s;
    }
    
    /**
     * Permite agregar un elemento al final de la lista.
     * @param dato
     */
    public void inserta_final(T dato){
        Nodo<T> temporal = inicio;
        if(inicio == null)
        	inserta_inicio(dato);
        else{
             while(temporal.getSiguiente() != null)
                   temporal = temporal.getSiguiente();
             temporal.setSiguiente(new Nodo<T>(dato));
        }        
    }
    
    /**
     * Permite agregar los elementos en orden conforme se insertan en la lista.
     * @param dato
     */
    public void inserta_ordenado(T dato){
        Nodo<T> temporal = inicio, nodoEncontrado = null, nuevo;
        Boolean band = true;
        
        if(inicio == null)
        	inserta_inicio(dato);
        else{
           while((dato.compareTo(temporal.getDato()))<0 && band)
                 if(temporal.getSiguiente() != null){
                    nodoEncontrado = temporal;
                    temporal = temporal.getSiguiente();
                }else
                    band = false;
                    
           if(band)
              if(temporal == inicio)
            	  inserta_inicio(dato);
              else{
                 nuevo = new Nodo<T>(dato);
                 nuevo.setSiguiente(temporal);
                 nodoEncontrado.setSiguiente(nuevo);
              }
           else
        	   inserta_final(dato);
        }
    }
        
    /**
     *Permite agregar un elemento al inicio de la lista.
     *@param dato
     */
	public void inserta_inicio(T dato){
        Nodo<T> nuevo = new Nodo<T>(dato);
        nuevo.setSiguiente(inicio);
        inicio = nuevo;
    }
	
	/**
     * Permite retornar el n�mero de elementos de la lista.
     * @return elementos
     */
	public Integer longitud(){
        Integer elementos = 0;
        Nodo<T> aux = inicio;
        while(aux != null){
              elementos++;
              aux = aux.getSiguiente();
        }                 
        return elementos;
    }
	
	/**
     * Permite recorrer la lista de manera iterativa.
     * @return s
     */
	public String recorreIterativo(){
    	Nodo<T> temporal = inicio;
    	String s = "";
    	while(temporal != null){
    		s += temporal.getDato() + " \n ";
    		temporal = temporal.getSiguiente();
    	}
    	//s += "null";
		return s;
    }
	
	/**
	 * M�todo que permite elimar un elemento en espec�fico de la lista.
	 * @param dato
	 */
	public T elimina_elemento(T dato){
		Nodo<T> aux = inicio, temp = null;
		Boolean band = true;
		
		try{			
			while(aux.getDato() != dato & band)
				if(aux.getSiguiente() != null){
					temp = aux;
					aux = temp.getSiguiente();
				}else
					band = false;
			
			if(band){
				if(inicio == aux)
					inicio = aux.getSiguiente();
				else
					temp.setSiguiente(aux.getSiguiente());
			}else
					JOptionPane.showMessageDialog(null, "El elemento no se encuentra en la lista.");
		}catch(NullPointerException e){
            JOptionPane.showMessageDialog(null,"Lista vac�a");
        }
		return aux.getDato();
	}
}
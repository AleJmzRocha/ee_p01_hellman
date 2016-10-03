package ee_p01_hellman;

import java.math.BigInteger;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * @authors Martinez Carrera Dulce Carolina
 * 			S�nchez L�zcares Perla Melina
 * 			Jim�nez Rocha Alejandra
 * 
 * Clase que permite la implementaci�n del algoritmo de encriptaci�n de Merkle-Hellman.
 */
public class MerkleHellman {
	/**
	 * Variables que se ocupar�n en la clase.
	 * La clase BigInteger se usar� porque se generan n�meros muy grandes.
	 */
	Random rnd = new Random();
	ListaLigadaSimple<BigInteger> llavePrivada;
	ListaLigadaSimple<BigInteger> llavePublica;
	BigInteger r, q, mensajeEncriptado = new BigInteger("0");
	
	/**
	 * M�todo que genera la llave privada del algoritmo.
	 * Es decir, el elemento a insertar debe ser mayor a la sumatoria de los anteriores.
	 */
	public void generaLlavePrivada(){
		llavePrivada = new ListaLigadaSimple<BigInteger>();
		BigInteger aleatorio = new BigInteger("0"), tope = new BigInteger("100");
		Integer contador = 0;
		try{
			do{
				if(aleatorio.compareTo(tope) <= 0){
					aleatorio = new BigInteger(tope.bitLength(), rnd);
					llavePrivada.inserta_ordenado(aleatorio);
					tope = tope.add(sumatoria(llavePrivada.getInicio())).add(new BigInteger("10000"));
					contador++;
				}
			}while(contador < 8);
			System.out.println(llavePrivada);
			q = getQ();
			System.out.println("q = " + q);
			r = getR();
			System.out.println("r = " + r);
		}catch(NullPointerException npe){
			JOptionPane.showMessageDialog(null, "Lista vac�a.");
		}
	}
	
	/**
	 * M�todo que permite hacer la sumatoria total de la lista.
	 * @param aux: que ser� el inicio de la lista.
	 * @return sumatoria
	 */
	public BigInteger sumatoria(Nodo<BigInteger> aux){
		BigInteger sumatoria = new BigInteger("0");
		try{
			while(aux.getSiguiente() != null){
				sumatoria = sumatoria.add(aux.getDato());
				aux = aux.getSiguiente();
			}
		}catch(NullPointerException npe){
			JOptionPane.showMessageDialog(null, "Lista vac�a.");
		}
		return sumatoria;
	}
	
	/**
	 * M�todo que genera q, es decir un n�mero mayor a la sumatoria total de la lista.
	 * @return q
	 */
	public BigInteger getQ(){
		BigInteger aux = new BigInteger("2000");
		try{
			q = aux.add(sumatoria(llavePrivada.getInicio()));
		}catch(NullPointerException npe){
			JOptionPane.showMessageDialog(null, "Lista vac�a.");
		}
		return q;
	}
	
	/**
	 * M�todo que genera r, es decir un n�mero que est� en el rango de 1 a q.
	 * @return r
	 */
	public BigInteger getR(){
		try{
			do{
				r = aleatorio(q);
				r.gcd(q);
			}while(r.gcd(q).compareTo(new BigInteger("1")) != 0);
		}catch(NullPointerException npe){
			JOptionPane.showMessageDialog(null, "Lista vac�a.");
		}
		return r;
	}
	
	public BigInteger aleatorio(BigInteger tope){
		BigInteger ale = new BigInteger("0");
		try{
			do{
				ale = new BigInteger(tope.bitLength(), rnd);
			}while(ale.compareTo(tope) >= 0);
		}catch(NullPointerException npe){
			JOptionPane.showMessageDialog(null, "Lista vac�a.");
		}
		return ale;
	}
	
	/**
	 * M�todo que genera la llave p�blica del algoritmo.
	 * Es decir, cada elemento de la llave privada se debe multiplicar por r, y ese resultado sacarle el m�dulo con q.
	 */
	public void generaLlavePublica(){
		llavePublica = new ListaLigadaSimple<BigInteger>();
		generaLlavePrivada();
		Nodo<BigInteger> aux = llavePrivada.getInicio();
		BigInteger num;
		try{
			while(aux != null){
				num = aux.getDato().multiply(r).mod(q);
				llavePublica.inserta_final(num);
				aux = aux.getSiguiente();
			}
			System.out.println(llavePublica);
		}catch(NullPointerException npe){
			JOptionPane.showMessageDialog(null, "Lista vac�a.");
		}
	}
	
	/**
	 * M�todo que permite encriptar el mensaje.
	 * Cada elemento de la llave p�blica se debe multiplicar por cada bit del valor binario de la letra y sumar el reultado de cada multiplicaci�n.
	 * Al final, se obtiene la sumatoria.
	 * @param car
	 * @return mensajeEncriptado
	 */
	public BigInteger mensajeEncriptado(Character car){
		String binario = "";
		String bin = Integer.toBinaryString(car);
		bin = "0" + bin;
//	    Se voltea el binario porque la lista va al contrario
		for(int i = bin.length()-1; i >= 0 ; i--){
			binario += bin.charAt(i);
		}
		generaLlavePublica();
		Nodo<BigInteger> aux = llavePublica.getInicio();
		Integer i = 0;
		try{
			while(aux.getSiguiente() != null){
				mensajeEncriptado = mensajeEncriptado.add(aux.getDato().multiply(new BigInteger(String.valueOf(binario.charAt(i)))));
				aux = aux.getSiguiente();
				i++;
			}
		}catch(NullPointerException npe){
			JOptionPane.showMessageDialog(null, "Lista vac�a.");
		}
		return mensajeEncriptado;
	}
	
	/**
	 * M�todo que permite desencriptar el mensaje cifrado con la f�rmula que es el n�mero resultante por, r m�dulo inverso de q, y a esto aplicarle el m�dulo de q.
	 * El n�mero resultante se debe restar a la llave privada, se pone un 0(cero) si no se pudo hacer la resta, de lo contrario se pone un 1(uno).
	 * As� se obtiene un n�mero binario para despu�s convertirlo a la letra correpondiente. 
	 * @return c
	 */
	public Character desencriptar(){
		Nodo<BigInteger> aux = llavePrivada.getInicio();
		BigInteger var;
		String s = "";
		char c = ' ';
		try{
			var = mensajeEncriptado.multiply(r.modInverse(q)).mod(q);
			while(aux.getSiguiente() != null){
				if(var.compareTo(aux.getDato()) >= 0){
					var = var.subtract(aux.getDato());
					s = "1" + s;
				}else
					s = "0" + s;
				aux = aux.getSiguiente();
			}
			
			if(s.length() < 8)
				s = "0" + s;
			
		c =(char)Integer.parseInt(s,2); 
		}catch(NullPointerException npe){
			JOptionPane.showMessageDialog(null, "Lista vac�a.");
		}
		return c;
	}
}
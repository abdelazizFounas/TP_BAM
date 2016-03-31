package RMI.common;

import java.io.Serializable;

/**
 * Java Utilities for Students</i>
 */

/**
 * Un numéro de téléphone
 * @author Morat 
 */
public class Numero implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6781593535313002187L;
	/** le numéro de téléphone */
	private String numero;
	private String name;
	/**
	 * Construction d'un numéro de téléphone.
	 * @param numero le numéro
	 */
	public Numero(String numero, String name) { this.numero=numero; this.name=name;}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() { return name + " " + numero;}
	
	public String getName() { return name;}
	
	public String getNumero() { return numero;}
}

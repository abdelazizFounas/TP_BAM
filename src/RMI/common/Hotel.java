package RMI.common;

import java.io.Serializable;

/**
 * Java Utilities for Students
 */

/**
 * Un hotel qui est caractérisé par son nom et sa localisation.
 * @author Morat 
 */
public class Hotel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6709916791975405263L;
	/** la localisation de l'hôtel */
	private String localisation;
	/** le nom de l'hôtel */
	private String name;
	/**
	 * Définition d'un hôtel par son nom et sa localisation.
	 * @param name le nom de l'hôtel
	 * @param localisation la localisation de l'hôtel
	 */
	public Hotel(String name, String localisation) { this.name=name; this.localisation=localisation;}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Hotel{"+name+","+localisation+"}";
	}
	
	public String getName() {
		return name;
	}
	
	public String getLocal() {
		return localisation;
	}
}

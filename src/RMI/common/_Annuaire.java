package RMI.common;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Java Utilities for Students
 */

/**
 * Définit un annuaire téléphonique élémentaire permettant, étant donnée un abonné, d'obtenir son numéro de téléphone.
 * @author Morat 
 */
public interface _Annuaire extends Remote{
	/**
	 * restitue le numéro de téléphone de l'abonné
	 * @param abonne l'abonné
	 * @return le numéro de télephone de l'abonné
	 */
	public Numero get(String abonne) throws RemoteException;
}
package jus.aor.mobilagent.annuaire_services;

import java.net.URI;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;


/**
 * Java Utilities for Students
 */

/**
 * Définit un annuaire téléphonique élémentaire permettant, étant donnée un abonné, d'obtenir son numéro de téléphone.
 * @author Morat 
 */
public interface _AnnuaireServices extends Remote{
	/**
	 * restitue le numéro de téléphone de l'abonné
	 * @param abonne l'abonné
	 * @return le numéro de télephone de l'abonné
	 */
	public void BindService(URI agentServeur, String service) throws RemoteException;
	public LinkedList<URI> getBindedServices(String service) throws RemoteException;
}
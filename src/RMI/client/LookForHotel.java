package RMI.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import RMI.common.Hotel;
import RMI.common.Numero;
import RMI.common._Annuaire;
import RMI.common._Chaine;

/**
 * Java Utilities for Students
 */

/**
 * Représente un client effectuant une requête lui permettant d'obtenir les numéros de téléphone des hôtels répondant à son critère de choix.
 * @author  Morat
 */
public class LookForHotel{
	/** le critère de localisaton choisi */
	private String localisation;
	// ...
	/**
	 * Définition de l'objet représentant l'interrogation.
	 * @param args les arguments n'en comportant qu'un seul qui indique le critère
	 *          de localisation
	 */
	public LookForHotel(String arg){
		localisation = arg;
	}

	public ArrayList<Numero> get(){
		ArrayList<Hotel> lh = new ArrayList<Hotel>();
		
		for(int i = 1; i <= 2; i++){
			lh.addAll(getFromHotel("Chaine"+i,9999+i));
		}
		
		return getNum(lh);
	}
	
	private ArrayList<Hotel> getFromHotel(String h, int port){
		System.out.println(this + "->rmi://localhost:"+port+"/" + h);
		_Chaine obj = null;
		try {
			obj = (_Chaine)Naming.lookup("//localhost:"+port+"/" + h);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Hotel> lh = null;
		try {
			lh = obj.get(localisation);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lh;
	}
	
	private ArrayList<Numero> getNum(List<Hotel> lh){
		ArrayList<Numero> ln = new ArrayList<Numero>();
		
		System.out.println(this + "->rmi://localhost:"+9998+"/Annuaire");
		_Annuaire obj = null;
		try {
			obj = (_Annuaire)Naming.lookup("//localhost:"+9998+"/Annuaire");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Numero n;
		for(Hotel h : lh){
			try {
				n = obj.get(h.getName());
				if(n != null){
					ln.add(n);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ln;
	}
}

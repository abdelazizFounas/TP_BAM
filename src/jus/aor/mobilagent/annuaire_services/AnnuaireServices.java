package jus.aor.mobilagent.annuaire_services;

import java.net.URI;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;

public class AnnuaireServices extends UnicastRemoteObject implements _AnnuaireServices {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5062908594299491771L;
	
	private HashMap<String, LinkedList<URI>> hm;
	
	public static void main(final String args[]) {
		try {
			new AnnuaireServices(9999, "AnnuaireServices");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected AnnuaireServices(int port, String nom) throws RemoteException{
		hm = new HashMap<String, LinkedList<URI>>();
		
		Registry registry = null;
		
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());

		try {
			registry = LocateRegistry.createRegistry(port);
		} catch (RemoteException e1) {
			System.out.println("Error locating registry "+nom);
			System.exit(1);
		}
		try {
			registry.rebind(nom, this);
			
			System.out.println("L'objet a été enregistré dans le serveur d'objets distants de "+nom);
		} catch (Exception e) {
			System.out.println(nom+" Server err: " + e);
		}
	}

	public LinkedList<URI> getBindedServices(String service){
		System.out.println("DEMANDE "+service + " TROUVES "+ hm.get(service).size());
		return hm.get(service);
	}
	
	public void BindService(URI agentServeur, String service){
		System.out.println("ENREGISTREMENT "+agentServeur.toString() + " "+ service);
		LinkedList<URI> l = hm.get(service);
		if(l == null){
			l = new LinkedList<URI>();
			l.add(agentServeur);
			hm.put(service, l);
		}
		else{
			l.add(agentServeur);
		}
	}
}

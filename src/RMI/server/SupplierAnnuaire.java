package RMI.server;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import RMI.common.Numero;
import RMI.common._Annuaire;

public class SupplierAnnuaire extends UnicastRemoteObject implements _Annuaire {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5062908594299491771L;
	private static ArrayList<Numero> annuaire = new ArrayList<Numero>();
	
	public static void main(final String args[]) {
		DocumentBuilder docBuilder = null;
		Document doc=null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			doc = docBuilder.parse(new File("Repository/DataStore/Annuaire.xml"));
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String name, numero;
		NodeList list = doc.getElementsByTagName("Telephone");
		NamedNodeMap attrs;
		
		for(int i=0; i<list.getLength();i++) {
			attrs = list.item(i).getAttributes();
			name=attrs.getNamedItem("name").getNodeValue();
			numero=attrs.getNamedItem("numero").getNodeValue();
			annuaire.add(new Numero(numero, name));
		}
		
		try {
			new SupplierAnnuaire("Annuaire", "Annuaire");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected SupplierAnnuaire(String f, String n) throws RemoteException {
		String nom = n;
		int port = 9999-1;
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

	public Numero get(String abonne){
		Numero res = null;
		Numero temp = null;
		
		for(int i = 0; i < annuaire.size(); i++){
			
			temp = annuaire.get(i);
			
			if(temp.getName().equals(abonne)){
				res = temp;
				i = annuaire.size();
			}
		}
		
		return res;
	}
}

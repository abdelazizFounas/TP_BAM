package RMI.server;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import RMI.common.Hotel;
import RMI.common.Numero;
import RMI.common._Chaine;

public class SupplierChaine extends UnicastRemoteObject implements _Chaine {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5033955040897932644L;
	private static String fichier;
	private static ArrayList<Hotel> annuaire = new ArrayList<Hotel>();
	
	public static void main(final String args[]) {
		int i = Integer.parseInt(args[2]);
		
		fichier = args[0];
		
		DocumentBuilder docBuilder = null;
		Document doc=null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			doc = docBuilder.parse(new File("Repository/DataStore/"+fichier+i+".xml"));
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String name, localisation;
		NodeList list = doc.getElementsByTagName("Hotel");
		NamedNodeMap attrs;
		
		for(int i1=0; i1<list.getLength();i1++) {
			attrs = list.item(i1).getAttributes();
			name=attrs.getNamedItem("name").getNodeValue();
			localisation=attrs.getNamedItem("localisation").getNodeValue();
			annuaire.add(new Hotel(name, localisation));
		}
		
		try {
			SupplierChaine sc = new SupplierChaine(args[0]+i, args[1], i);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected SupplierChaine(String f, String n, int nb) throws RemoteException {
		fichier = f;
		String nom = n;
		int port = 9999+nb;
		Registry registry = null;
		
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());

		try {
			registry = LocateRegistry.createRegistry(port);
		} catch (RemoteException e1) {
			System.out.println("Error locating registry "+n+nb);
			System.exit(1);
		}
		try {
			registry.rebind(nom+nb, this);
			
			System.out.println("L'objet a été enregistré dans le serveur d'objets distants de "+nom+nb);
		} catch (Exception e) {
			System.out.println(nom+nb+" Server err: " + e);
		}
	}

	public ArrayList<Hotel> get(String localisation){
		ArrayList<Hotel> res = new ArrayList<Hotel>();
		
		Hotel h;
		for(int i = 0; i < annuaire.size(); i++){
			h = annuaire.get(i);
			if(localisation.equals(h.getLocal())){
				res.add(h);
			}
		}
		
		return res;
	}
}

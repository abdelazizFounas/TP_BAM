package jus.aor.mobilagent.hostel;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jus.aor.mobilagent.kernel._Service;

public class Annuaire implements _Service<LinkedList<Numero>>{
	private LinkedList<Numero> ln;
	
	public Annuaire(Object... args){
		String fichier = (String) args[0];
		ln = new LinkedList<Numero>();
		
		DocumentBuilder docBuilder = null;
		Document doc=null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			doc = docBuilder.parse(new File(fichier));
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
			ln.add(new Numero(numero, name));
		}
	}
	
	@Override
	public LinkedList<Numero> call(Object... params) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		LinkedList<Hotel> lh = (LinkedList<Hotel>) params[0];
		
		LinkedList<Numero> res = new LinkedList<Numero>();
		for(Hotel h : lh){
			for(Numero n : ln){
				if(h.getName().equals(n.getName())){
					res.add(n);
					break;
				}
			}
		}
		
		return res;
	}
}

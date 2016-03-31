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

import jus.aor.mobilagent.kernel.Hotel;
import jus.aor.mobilagent.kernel._Service;

public class Chaine implements _Service<LinkedList<Hotel>>{
	private LinkedList<Hotel> lh;
	
	public Chaine(Object... args){
		String fichier = (String) args[0];
		lh = new LinkedList<Hotel>();
		
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

		String name, localisation;
		NodeList list = doc.getElementsByTagName("Hotel");
		NamedNodeMap attrs;
		
		for(int i1=0; i1<list.getLength();i1++) {
			attrs = list.item(i1).getAttributes();
			name=attrs.getNamedItem("name").getNodeValue();
			localisation=attrs.getNamedItem("localisation").getNodeValue();
			lh.add(new Hotel(name, localisation));
		}
	}
	
	@Override
	public LinkedList<Hotel> call(Object... params) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		String localisation = (String) params[0];
		
		LinkedList<Hotel> res = new LinkedList<Hotel>();
		
		for(Hotel h : lh){
			if(h.getLocal().equals(localisation)){
				res.add(h);
			}
		}
		
		return res;
	}
}

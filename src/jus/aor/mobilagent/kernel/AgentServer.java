package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

public class AgentServer implements Runnable{
	
	/** le nom logique du serveur */
	protected String name;
	/** le port où sera ataché le service du bus à agents mobiles. Pafr défaut on prendra le port 10140 */
	protected int port=10140;
	
	protected HashMap<String, _Service<?>> hms;
	
	protected BAMServerClassLoader bscl;

	public AgentServer(int port, String name) {
		this.port = port;
		this.name = name;
		this.hms = new HashMap<String, _Service<?>>();
		try {
			this.bscl = new BAMServerClassLoader(new URL[]{new URL("file://MobilagentServer.jar")}, this.getClass().getClassLoader());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public void run(){
		try {
			ServerSocket ss = new ServerSocket(port, 1000);
			
			Socket client = null;
			_Agent agent = null;
			
			while(true){
				client = ss.accept();
				agent = getAgent(client);
				
				agent.reInit(this, name);
				
				new Thread(agent).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private _Agent getAgent(Socket socket){
		InputStream is;
		_Agent res = null;
		try {
			is = socket.getInputStream();
			
			ObjectInputStream ois = new ObjectInputStream(is);
			
			Jar j = null;
			try {
				j = (Jar)ois.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			BAMAgentClassLoader bacl = new BAMAgentClassLoader(this.getClass().getClassLoader());
			
			bacl.integrateCode(j);
			
			AgentInputStream ais = new AgentInputStream(is, bacl);
			
			try {
				res = (_Agent) ais.readObject();
				
				ais.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;	
	}
	
	public void addService(String name, _Service<?> service){
		
		hms.put(name, service);
	}
	
	public _Service<?> getService(String name){
		return hms.get(name);
	}
	
	public URI site(){
		try {
			return new URI("mobileagent://localhost:"+port);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String toString(){
		return name;
	}
}

package BAM.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;

public class AgentServer{
	
	/** le nom logique du serveur */
	protected String name;
	/** le port où sera ataché le service du bus à agents mobiles. Pafr défaut on prendra le port 10140 */
	protected int port=10140;

	public AgentServer(int port, String name) {
		this.port = port;
		this.name = name;
		
		run();
	}
	
	public void run(){
		BAMAgentClassLoader bacl = new BAMAgentClassLoader(this.getClass().getClassLoader());
		try {
			ServerSocket ss = new ServerSocket(port);
			Socket client = null;
			_Agent agent = null;
			
			while(true){
				client = ss.accept();
				agent = getAgent(client);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private _Agent getAgent(Socket socket){
		return null;	
	}
	
	public void addService(String name, _Service<?> service){
		
	}
	
	public _Service<?> getService(){
		return null;
	}
	
	public URI site(){
		return null;
	}
	
	public String toString(){
		return name;
	}
}

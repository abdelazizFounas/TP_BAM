package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;

public abstract class Agent implements _Agent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3847145516835348617L;
	protected Route route;
	protected transient AgentServer agentServer;
	protected String serverName;
	
	protected Etape next;
	
	public Agent(Object... args){
	}

	protected abstract _Action retour();

	@Override
	public final void run(){
		next.action.execute();
		
		if(route.hasNext() || (!route.hasNext && !agentServer.site().equals(route.get().server))){
			move();
		}
	}

	@Override
	public void init(AgentServer agentServer, String serverName) {
		this.agentServer = agentServer;
		this.serverName = serverName;
		
		route = new Route(new Etape(agentServer.site(), retour()));
		
		next = new Etape(agentServer.site(), new _Action() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -2663274152602472040L;

			@Override
			public void execute() {
				System.out.println("Debut de l'agent");
			}
		});
	}

	@Override
	public void reInit(AgentServer agentServer, String serverName) {
		this.agentServer = agentServer;
		this.serverName = serverName;
	}

	@Override
	public void addEtape(Etape etape){
		route.add(etape);
	}
	
	protected _Service<?> getService(String name){
		return agentServer.getService(name);
	}
	
	protected void move(){
		
		next = route.get();
		
		try {
			move(next.server);
		} catch (ConnectException e) {
			move();
		}
	}
	
	protected void move(URI uri) throws ConnectException{
		try {
			Socket socket = new Socket(uri.getHost(), uri.getPort());
			
			OutputStream os = socket.getOutputStream();
			
			ObjectOutputStream oos = new ObjectOutputStream(os);
			
			oos.writeObject(new BAMAgentClassLoader(this.getClass().getClassLoader()).extractCode());
			
			ObjectOutputStream ooos = new ObjectOutputStream(os);
			
			ooos.writeObject(this);
			
			socket.close();
		} catch (ConnectException e){
			throw e;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	protected String route(){
		return route.toString();
	}
	
	public String toString(){
		return "Agent";
	}
}

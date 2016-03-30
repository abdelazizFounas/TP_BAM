/**
 * Java Utilities for Students
 */

package jus.aor.mobilagent.kernel;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jus.aor.mobilagent.kernel.BAMAgentClassLoader;
import jus.aor.mobilagent.kernel._Agent;

/**
 * Le serveur principal permettant le lancement d'un serveur d'agents mobiles et les fonctions permettant de déployer des services et des agents.
 * @author     Morat
 */
public final class Server implements _Server{
	/** le nom logique du serveur */
	protected String name;
	/** le port où sera ataché le service du bus à agents mobiles. Pafr défaut on prendra le port 10140 */
	protected int port=10140;
	/** le server d'agent démarré sur ce noeud */
	protected AgentServer agentServer;
	/** le nom du logger */
	protected String loggerName;
	/** le logger de ce serveur */
	protected Logger logger=null;
	
	protected BAMServerClassLoader bscl;
	
	/**
	 * Démarre un serveur de type mobilagent 
	 * @param port le port d'écuote du serveur d'agent 
	 * @param name le nom du serveur
	 */
	public Server(final int port, final String name){
		this.name=name;
		
		try {
			this.bscl = new BAMServerClassLoader(new URL[]{new URL("file://MobilagentServer.jar")}, this.getClass().getClassLoader());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.port=port;
			/* mise en place du logger pour tracer l'application */
			loggerName = "BAM/client/"+InetAddress.getLocalHost().getHostName()+"/"+this.name;
			logger=Logger.getLogger(loggerName);
			/* démarrage du server d'agents mobiles attaché à cette machine */
			
			agentServer = new AgentServer(port, "as"+port);
			
			new Thread(agentServer).start();
			
			/* temporisation de mise en place du server d'agents */
			Thread.sleep(1000);
		}catch(Exception ex){
			logger.log(Level.FINE," erreur durant le lancement du serveur"+this,ex);
			ex.printStackTrace();
			return;
		}
	}
	/**
	 * Ajoute le service caractérisé par les arguments
	 * @param name nom du service
	 * @param classeName classe du service
	 * @param codeBase codebase du service
	 * @param args arguments de construction du service
	 */
	public final void addService(String name, String classeName, String codeBase, Object... args) {
		try {
			//A COMPLETER
			bscl.addURL(new URL(codeBase));
			Class<?> classe = Class.forName(classeName, true, bscl);
			_Service<?> serv = (_Service<?>)classe.getConstructor(Object[].class).newInstance(new Object[]{args});
		    agentServer.addService(name, serv);
		}catch(Exception ex){
			logger.log(Level.FINE," erreur durant le lancement du serveur"+this,ex);
			return;
		}
	}
	/**
	 * deploie l'agent caractérisé par les arguments sur le serveur
	 * @param classeName classe du service
	 * @param args arguments de construction de l'agent
	 * @param codeBase codebase du service
	 * @param etapeAddress la liste des adresse des étapes
	 * @param etapeAction la liste des actions des étapes
	 */
	public final void deployAgent(String classeName, Object[] args, String codeBase, List<String> etapeAddress, List<String> etapeAction) {
		//A COMPLETER en terme de startAgent
		try {
			BAMAgentClassLoader bacl = new BAMAgentClassLoader(codeBase, bscl);
			Class<?> classe = Class.forName(classeName, true, bacl);
			
			_Agent a = (_Agent) classe.getConstructor(Object[].class).newInstance(new Object[]{args});
		    
		    a.init(agentServer, agentServer.name);
		   
		    for(int i = 0; i<etapeAction.size(); i++){
		    	Field f = classe.getDeclaredField(etapeAction.get(i));
		    	f.setAccessible(true);
		    	
		    	a.addEtape(new Etape(new URI(etapeAddress.get(i)), (_Action)f.get(a)));
		    }
		    
		    startAgent(a, bacl);
		}catch(Exception ex){
			logger.log(Level.FINE," erreur durant le lancement du serveur"+this,ex);
			ex.printStackTrace();
			return;
		}
	}
	/**
	 * Primitive permettant de "mover" un agent sur ce serveur en vue de son exécution
	 * immédiate.
	 * @param agent l'agent devant être exécuté
	 * @param loader le loader à utiliser pour charger les classes.
	 * @throws Exception
	 */
	protected void startAgent(_Agent agent, BAMAgentClassLoader loader) throws Exception {
		//A COMPLETER
		
		Socket socket = new Socket("localhost", port);
		
		OutputStream os = socket.getOutputStream();
		
		ObjectOutputStream oos = new ObjectOutputStream(os);
		
		oos.writeObject(loader.extractCode());
		
		ObjectOutputStream ooos = new ObjectOutputStream(os);
		
		ooos.writeObject(agent);
		
		socket.close();
	}
}

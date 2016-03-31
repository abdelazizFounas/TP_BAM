/**
 * Java Utilities for Students
 */

package jus.aor.mobilagent.hostel;

import java.util.LinkedList;

import jus.aor.mobilagent.kernel.Agent;
import jus.aor.mobilagent.kernel.AgentServer;
import jus.aor.mobilagent.kernel.Etape;
import jus.aor.mobilagent.kernel.Hotel;
import jus.aor.mobilagent.kernel.Numero;
import jus.aor.mobilagent.kernel._Action;
import jus.aor.mobilagent.kernel._Service;

/**
 * Classe de test élémentaire pour le bus à agents mobiles
 * @author  Morat
 */
public class LookForHotel extends Agent{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -7394477634937700158L;
	
	private String localisation;
	
	private LinkedList<Hotel> lh;
	
	private LinkedList<Numero> ln;
	
	private long tpsDebut = 0;
	
	private boolean retour = false;
	/**
	  * construction d'un agent de type hello.
	  * @param args aucun argument n'est requis
	  */
	 public LookForHotel(Object... args){
		 localisation = (String) args[0];
		 
		 lh = new LinkedList<Hotel>();
	 }
	 /**
	 * l'action à entreprendre sur les serveurs visités  
	 */
	protected _Action findHotel = new _Action(){
		/**
		 * 
		 */
		private static final long serialVersionUID = -1006815193733717960L;

		@Override
		public void execute() {
			System.out.println("Hello, I was here ^^");
			_Service<LinkedList<Hotel>> serv = (_Service<LinkedList<Hotel>>) agentServer.getService("Hotels");
			if(serv != null){
				lh.addAll((LinkedList<Hotel>)serv.call(new Object[]{localisation}));
			}
		}
	};
	
	protected _Action findTelephone = new _Action(){
		/**
		 * 
		 */
		private static final long serialVersionUID = -1006815193733717960L;

		@Override
		public void execute() {
			System.out.println("Hello, I was here ^^");
			_Service<LinkedList<Numero>> serv = (_Service<LinkedList<Numero>>) agentServer.getService("Telephones");
			if(serv != null){
				
				ln = (LinkedList<Numero>)serv.call(new Object[]{lh});
			}
		}
	};
	/* (non-Javadoc)
	 * @see jus.aor.mobilagent.kernel.Agent#retour()
	 */
	@Override
	protected _Action retour(){
		return new _Action(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			
			@Override
			public void execute() {
				if(!retour){
					_Service<Long> serv = (_Service<Long>) agentServer.getService("Duration");
					if(serv != null){
						tpsDebut = serv.call(new Object[]{});
					}
					
					retour = true;
				}
				else{
					Long tpsArrive = (long) 0;
					System.out.println("En tout : " + ln.size() + " telephones d'hotels trouves.");
					_Service<Long> serv = (_Service<Long>) agentServer.getService("Duration");
					if(serv != null){
						tpsArrive = serv.call(new Object[]{});
					}
					System.out.println("Trouvés en : " + (tpsArrive-tpsDebut)/(long)1000 + " secondes.");
				}
			}
		};
	}
}

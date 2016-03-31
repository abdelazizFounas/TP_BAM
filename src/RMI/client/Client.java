package RMI.client;

import java.util.ArrayList;
import java.util.List;

import RMI.common.Numero;

/**
 * @author morat
 */
public class Client {
	/**
	 * @param args
	 */
	public static void main(String args[]) {
		String local = "", host = "localhost";
		// récupération des arguments
		if (args.length != 2) {
			System.out.println("Client <hostname> <localisation>");
			System.exit(1);
		}
		try {
			host = args[0];
			local = args[1];
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println("Client <hostname> <localisation>");
			System.exit(1);
		}
		// installation d'un securityManager
		// A COMPLETER : INSTALLATION DU SECURITYMANAGER
		// Démarrage des consommateurs

		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());

		LookForHotel lfh = new LookForHotel(local);
		
		ArrayList<Numero> ln = new ArrayList<Numero>();
		
		long tpsDebut = System.currentTimeMillis();
		ln = lfh.get();
		long tpsArrive = System.currentTimeMillis();
		
		/*System.out.println("Voila les numeros trouves :");
		for(int i = 0; i < ln.size(); i++){
			System.out.println(ln.get(i).toString());
		}**/
		
		System.out.println("En : " + (tpsArrive-tpsDebut)/(long)1000 + " secondes.");
	}
}

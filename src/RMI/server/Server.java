package RMI.server;

import java.rmi.RemoteException;

/**
 * @author morat
 */
public class Server{
	/**
	 * @param args
	 */
	public static void main(final String args[]) {
		for(int i = 1; i <= 4; i++){
			try {
				SupplierChaine sc = new SupplierChaine("Hotel"+i, "Chaine", i);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			SupplierAnnuaire sa = new SupplierAnnuaire("Annuaire", "Annuaire");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

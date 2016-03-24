/**
 * Java Utilities for Students
 */

package BAM.client;

import java.io.Serializable;

/**
 * Définit une action à exécuter par un agent.
 * @author  Morat
 */
public interface _Action extends Serializable{
	/** l'action vide */
	public static final _Action NIHIL = new _Action(){public void execute() {}};
	
	/**
	 * Exécute l'action
	 */
	public void execute();
}

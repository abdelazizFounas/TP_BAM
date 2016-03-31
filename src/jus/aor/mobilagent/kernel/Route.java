/**
 * Java Utilities for Students
 */

package jus.aor.mobilagent.kernel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Définit la feuille de route que l'agent va suivre
 * @author  Morat
 */
public class Route implements Iterable<Etape>, Serializable{
	private static final long serialVersionUID = 9081294824590167316L;
	/** la liste des étapes à parcourir autres que la dernière */
	protected HashMap<Integer, Etape> route;
	/** la dernière étape de la feuille de route de l'agent qui désigne le serveur de départ. */
	protected Etape retour;
	/** Indique si la feuille de route est épuisée ou non. */
	protected boolean hasNext;
	protected int cur;
	
	/**
	 * Construction d'une route.
	 * @param retour  le server initial et de retour.
	 */
	public Route(Etape retour) {
		route = new HashMap<Integer, Etape>();
		this.retour = retour;
		hasNext=false;
		cur = -1;
	}
	
	/**
	 * Ajoute une étape en fin de route.
	 * @param e l'étape à ajouter
	 */
	public void add(Etape e) {
		route.put(route.size(),e);
		hasNext=true;
	}
	
	/**
	 * Restitue la prochaine étape ou la dernière qui est la base de départ.
	 * @return la prochaine étape.
	 */
	Etape get() throws NoSuchElementException{
		if(hasNext){
			cur++;
			
			if(cur == route.size()-1){
				hasNext = false;
			}
			return route.get(cur);
		}
		else{
			hasNext = false;
			return retour;
		}
	}
	
	/**
	 * Restitue la prochaine étape et élimine de la route ou la dernière qui est la base de départ.
	 * @return la prochaine étape.
	 */
	Etape next() throws NoSuchElementException {
		if(hasNext()){
			cur++;
			
			if(cur == route.size()-1){
				hasNext = false;
			}
			return route.get(cur);
		}
		else{
			hasNext = false;
			return retour;
		}
	}
	
	/**
	 * Il y a-t-il encore une étape à parcourir.
	 * @return vrai si une étape est possible.
	 */
	public boolean hasNext() {
		return hasNext;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return route.toString().replaceAll(", ","->");
	}
	
	@Override
	public Iterator<Etape> iterator() {
		return route.values().iterator();
	}
}

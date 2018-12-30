package presentation;

import metier.Observable;

/**
 * Un objet qui implémente cette interface sera notifié lors d'une action sur l'objet qu'il
 * observe s'il a été préalablement "attaché" par l'objet observé (qui doit obligatoirement
 * implémenter {@code Observable}).
 * @see Observable
 */

public interface Observateur {

	/**
	 * Notifie {@code this} qu'une action a eu lieue sur l'objet observé.
	 */
	public abstract void mettreAJour();
}
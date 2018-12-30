package metier;

import presentation.Observateur;

/**
 * Un objet qui implémente cette interface peut avoir des {@code Observateur} qui
 * seront notifiés d'une action sur {@code this} au moyen d'un appel à la méthode {@code avertir}.
 * @see Observateur
 */

public interface Observable {

	/**
	 * Attache un objet {@code Observateur} qui sera informé des actions sur {@code this}
	 * en cas d'appel à la méthode {@code avertir}.
	 * @param observateur un objet implémentant {@code Observateur}.
	 */
	public abstract void attacher(Observateur observateur);

	/**
	 * Permet d'avertir les observateurs que l'on a modifié {@code this}.
	 */
	public abstract void avertir();
}
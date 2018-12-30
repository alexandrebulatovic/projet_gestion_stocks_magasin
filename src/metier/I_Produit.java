package metier;

/**
 * Cette interface fournit des méthodes permettant d'accéder aux
 * données d'un produit ou d'en modifier le stock.
 */

public interface I_Produit {

	/**
	 * Met à jour la quantité du produit.
	 * @param qteAchetee la quantité > 0 à rajouter au stock existant.
	 * @return {@code true} en cas de réussite, sinon {@code false}.
	 */
	public abstract boolean ajouter(int qteAchetee);

	/**
	 * Met à jour la quantité du produit.
	 * @param qteVendue la quantité à enlever au stock existant qui doit être
	 * {@code 0 < quantité <= stock existant}.
	 * @return {@code true} en cas de réussite, sinon {@code false}.
	 */
	public abstract boolean enlever(int qteVendue);

	/** @return le nom du produit. */
	public abstract String getNom();

	/** @return la valeur TTC (Toutes Taxes Comprises) de tout le stock du produit
	 * calculé par {@code prix*stock}.*/
	public abstract double getPrixStockTTC();

	/** @return le prix hors taxe du produit. */
	public abstract double getPrixUnitaireHT();

	/** @return le prix TTC du produit. */
	public abstract double getPrixUnitaireTTC();

	/** @return le stock actuel du produit. */
	public abstract int getStock();

	/**
	 *  Affiche le produit dans le format requis par les spécifications
	 *  (les tests unitaires du professeur).
	 * @return une chaîne {@code String} dans le format requis.
	 */
	@Override
	public abstract String toString();
}
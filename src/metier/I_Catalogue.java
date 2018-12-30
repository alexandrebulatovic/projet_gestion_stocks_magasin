package metier;
import java.util.List;

/**
 * Cette interface fournit des méthodes permettant de manipuler un catalogue de produits.
 */

public interface I_Catalogue extends Observable {

	/**
	 * Met à jour le stock du produit d'un produit.
	 * @param nomProduit le nom du produit à mettre à jour.
	 * @param qteAchetee la quantité > 0 à ajouter au stock.
	 * @return {@code true} si le produit existe et a été mis à jour, sinon {@code false}.
	 */
	public abstract boolean acheterStock(String nomProduit, int qteAchetee);

	/**
	 * Ajoute un produit dans le catalogue.
	 * @param produit un objet implémentant {@code I_Produit} à ajouter.
	 * @return {@code true} en cas de réussite, sinon {@code false} si le produit
	 * existe déjà, est {@code null} ou ne respecte pas les conditions de création d'un produit : un
	 * prix > 0 et un stock >= 0.
	 */
	public abstract boolean addProduit(I_Produit produit);

	/**
	 * Crée un nouveau produit dans le catalogue.
	 * @param nomProduit le nom du produit.
	 * @param prixHT le prix HT du produit et > 0.
	 * @param stock le stock du produit qui doit être >= 0.
	 * @return {@code true} en cas réussite, sinon {@code false}.
	 */
	public abstract boolean addProduit(String nomProduit, double prixHT, int stock);

	/**
	 * Ajoute une liste de produits au catalogue.
	 * @param liste un objet {@code List} contenant des produits.
	 * @return le nombre de produits effectivement ajoutés au catalogue, 0 si le
	 * paramètre est un {@code null}.
	 */
	public abstract int addProduits(List<I_Produit> liste);

	/**
	 * Vide le contenu du catalogue tel que {@code getNbProduit} retourne 0.
	 */
	public abstract void clear();

	/**
	 * Permet d'obtenir un objet {@code List} contenant les produits du catalogue.
	 * @return un objet {@code List} de produits.
	 */
	public List<I_Produit> getListeProduits();

	/**
	 * Permet de calculer la valeur totale TTC (Toutes Taxes Comprises) d'un catalogue
	 * qui correspond à la somme des {@code prix*stock} pour chaque produit différent.
	 * @return la valeur TTC du catalogue.
	 */
	public abstract double getMontantTotalTTC();

	/**
	 * Sert à obtenir le nombre de produits différents (et non le stock) présents
	 * dans le catalogue.
	 * @return le nombre de produits.
	 */
	public abstract int getNbProduit();

	/**
	 * Sert à obtenir le nom du catalogue.
	 * @return le nom du catalogue.
	 */
	public abstract String getNom();

	/**
	 * Permet d'obtenir les noms des produits du catalogue triés dans l'ordre alphabétique.
	 * @return un tableau de {@code String} avec les noms des produits dans l'ordre alphabétique.
	 */
	public abstract String[] getNomProduits();

	/**
	 * Recherche un produit dans le catalogue.
	 * @param nomProduit le nom du produit à rechercher.
	 * @return un objet {@code I_Produit} du produit recherché s'il existe, sinon {@code null}.
	 */
	public I_Produit getProduit(String nomProduit);

	/**
	 * Retire un produit du catalogue.
	 * @param nomProduit le nom du produit à retirer.
	 * @return {@code true} si le produit existe et a été retiré, sinon {@code false} si
	 * le produit n'existe pas ou s'il est {@code null}.
	 */
	public abstract boolean removeProduit(String nomProduit);

	/**
	 *  Affiche le produit dans le format requis par les spécifications
	 *  (les tests unitaires du professeur).
	 * @return une chaîne {@code String} dans le format requis, non vide.
	 */
	@Override
	public abstract String toString();

	/**
	 * Met à jour le stock d'un produit.
	 * @param nomProduit le nom du produit à mettre à jour.
	 * @param qteVendue la quantité à enlever au stock tel que {@code 0 < quantité <= stock existant}.
	 * @return {@code true} si le produit existe et a été mis à jour, sinon {@code false}.
	 */
	public abstract boolean vendreStock(String nomProduit, int qteVendue);
}
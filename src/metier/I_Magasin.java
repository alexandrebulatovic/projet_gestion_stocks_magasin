package metier;

import java.util.List;

/**
 * Cette interface fournit des méthodes permettant de manipuler les catalogues
 * d'un conteneur de catalogues que l'on appelle {@code Magasin}.
 */

public interface I_Magasin extends Observable{

	/**
	 * Ajoute un catalogue non existant au magasin.
	 * @param catalogue le catalogue à ajouter.
	 * @return {@code true} en cas de réussite, sinon {@code false} si le catalogue existe déjà.
	 */
	public abstract boolean addCatalogue(I_Catalogue catalogue);

	/**
	 * Ajoute une liste de catalogues au magasin.
	 * @param listeCatalogues une {@code List} contenant les catalogues à ajouter.
	 * @return le nombre de catalogues effectivement ajoutés, 0 si le
	 * paramètre est un {@code null}.
	 */
	public abstract int addCatalogues(List<I_Catalogue> listeCatalogues);

	/**
	 * Efface tous les catalogues du magasin.
	 */
	public abstract void clear();

	/**
	 * Permet d'obtenir tous les catalogues dans ce magasin.
	 * @return un objet {@code List} contenant tous les catalogues, qui peut être
	 * vide (la méthode {@code size} retourne 0).
	 */
	public List<I_Catalogue> getListeCatalogues();

	/**
	 * Permet d'obtenir tous les noms des catalogues dans ce magasin triés dans l'ordre alphabétique.
	 * @return un tableau de {@code String} contenant les noms des catalogues dans l'ordre
	 * alphabétique, qui peut être vide s'il n'y a pas de catalogues.
	 */
	public abstract String[] getNomCatalogues();

	/**
	 * Retire le catalogue du magasin.
	 * @param nom le nom du catalogue à retirer.
	 * @return {@code true} si le catalogue existe et a été retiré, sinon {@code false}.
	 */
	public abstract boolean removeCatalogue(String nom);
}
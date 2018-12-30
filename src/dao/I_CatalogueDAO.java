package dao;

import java.util.List;

import metier.Catalogue;
import metier.I_Catalogue;

/**
 * Cette interface fournit des méthodes {@code Create, Read, Delete} sur les données persistantes
 * du magasin ainsi qu'une méthode pour se déconnecter du système de persistance de données.
 * <P>
 * On veut traiter les éventuelles erreurs qui se déclenchent directement dans l'objet {@code DAO}
 * et on souhaite, lorsque cela est possible, transmettre seulement un {@code boolean} qui indique
 * la réussite ou l'échec de l'opération.
 * <P>
 * Les éventuels contrôle de cohérence (par exemple vérification de doublon, prix non nul, etc.) sont
 * effectués par les objets du package {@code metier} "interne" (non persistant) ou du package {@code controller}
 * avant que les données soient stockés de façon permanente.
 * @see Catalogue
 */
public interface I_CatalogueDAO {

	/**
	 * Crée un nouveau catalogue dans la base de données.
	 * @param catalogue un objet implémentant {@code I_Catalogue} à ajouter.
	 * @return {@code true} en cas de réussite, sinon {@code false} en cas d'erreur avec la base de données.
	 */
	public abstract boolean create(I_Catalogue catalogue);

	/**
	 * Supprime un catalogue de la base de données.
	 * @param nom le nom du catalogue à supprimer.
	 * @return {@code true} en cas de réussite, sinon {@code false} en cas d'erreur avec la base de données.
	 */
	public abstract boolean delete(String nom);

	/**
	 * Essaie de fermer la connexion à la base de données.
	 * @return {@code true} en cas de succès, sinon {@code false} en cas d'erreur avec la base de données.
	 */
	public abstract boolean disconnect();

	/**
	 * Crée une liste contenant tous les catalogues et leurs produits stockés dans la
	 * base de données.
	 * @return un conteneur {@code List} de catalogues qui peut être vide (la
	 * méthode {@code size} renvoie 0) sinon {@code null} en cas d'erreur avec la base de données.
	 */
	public abstract List<I_Catalogue> findAll();

	/**
	 * Donne le nombre de produits différents d'un catalogue.
	 * @param nomCatalogue le nom du catalogue à utiliser.
	 * @return le nombre de produits différents, qui peut être égal à 0.
	 * @throws Exception en cas d'erreur avec la base de données.
	 */
	public abstract int findNbProduits(String nomCatalogue) throws Exception;
}
package dao;

import java.util.List;

import metier.I_Produit;
import metier.Produit;

/**
 * Cette interface fournit principalement des méthodes {@code Create, Read, Delete, Update}
 * sur les données persistantes des catalogues de l'application.
 * <P>
 * On veut traiter les éventuelles erreurs qui se déclenchent directement dans l'objet {@code DAO}
 * et on souhaite, lorsque cela est possible, transmettre seulement un {@code boolean} qui indique
 * la réussite ou l'échec de l'opération.
 * <P>
 * Les éventuels contrôle de cohérence (par exemple vérification de doublon, prix non nul, etc.) sont
 * effectués par les objets du package {@code metier} "interne" (non persistant) ou du package {@code controller}
 * avant que les données soient stockés de façon permanente.
 * @see Produit
 */

public interface I_ProduitDAO {

	/**
	 * Met à jour le stock du produit dans la base de données.
	 * @param nomProduit le nom du produit à mettre à jour.
	 * @param quantite la quantité à rajouter au stock existant.
	 * @return {@code true} en cas de réussite, sinon {@code false} en cas d'erreur avec la base de données.
	 */
	public abstract boolean addQuantite(String nomProduit, int quantite);

	/**
	 * Crée un nouveau produit dans un catalogue dans la base de données.
	 * @param produit un objet implémentant {@code I_Produit} à ajouter.
	 * @param nomCatalogue le nom du catalogue dans lequel créer le nouveau produit.
	 * @return {@code true} en cas de réussite, sinon {@code false} en cas d'erreur avec la base de données.
	 */
	public abstract boolean create(I_Produit produit, String nomCatalogue);

	/**
	 * Supprime un produit de la base de données.
	 * @param nomProduit le nom du produit à supprimer.
	 * @return {@code true} en cas de réussite, sinon {@code false} en cas d'erreur avec la base de données.
	 */
	public abstract boolean delete(String nomProduit);

	/**
	 * Recherche un produit dans la base de données.
	 * @param nomProduit le nom du produit à rechercher.
	 * @return un objet implémentant {@code I_Produit} si le produit existe,
	 * sinon {@code null} s'il n'existe pas.
	 * @throws Exception en cas d'erreur avec la base de données .
	 */
	public abstract I_Produit find(String nomProduit) throws Exception;

	/**
	 * Crée une liste de tous les produits d'un catalogue stocké dans la base de données.
	 * @param catalogue le nom du catalogue à utiliser.
	 * @return un conteneur {@code List} de produits qui peut être vide (la méthode
	 * {@code size} renvoie 0) sinon {@code null} en cas d'erreur avec la base de données.
	 */
	public abstract List<I_Produit> findAll(String catalogue);

	/**
	 * Met à jour le stock du produit dans la base de données.
	 * @param nomProduit le nom du produit à mettre à jour.
	 * @param quantite la quantité à enlever du stock existant.
	 * @return {@code true} en cas de réussite, sinon {@code false} en cas d'erreur avec la base de données.
	 */
	public abstract boolean removeQuantite(String nomProduit, int quantite);
}
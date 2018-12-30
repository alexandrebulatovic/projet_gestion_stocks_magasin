package factory;

import dao.I_CatalogueDAO;
import dao.I_ProduitDAO;

/**
 * Cette interface fournit des méthodes qui permettent d'instancier un
 * objet {@code DAO} pour l'accès aux données des produits ou des catalogues.
 * <P>
 * Le développeur qui souhaite étendre les types de base de données gérées par
 * l'application doit créer une implémentation spécifique de cette interface pour
 * la base de données qu'il souhaite intégrer.
 * @see I_ProduitDAO
 * @see I_CatalogueDAO
 */

public interface I_DAOFactory {
	/**
	 * Crée une nouvelle instance de {@code CatalogueDAO}.
	 * @return une nouvelle instance de {@code CatalogueDAO}.
	 * @throws Exception en cas d'échec de connexion au système de persistance des données.
	 * @see I_CatalogueDAO
	 */
	public abstract I_CatalogueDAO createCatalogueDAO() throws Exception;

	/**
	 * Crée une nouvelle instance de {@code ProduitDAO}.
	 * @return une nouvelle instance de {@code ProduitDAO}.
	 * @throws Exception en cas d'échec de connexion au système de persistance des données.
	 * @see I_ProduitDAO
	 */
	public abstract I_ProduitDAO createProduitDAO() throws Exception;
}
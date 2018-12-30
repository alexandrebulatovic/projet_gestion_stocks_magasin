package factory;

import dao.CatalogueDAO_Oracle;
import dao.I_CatalogueDAO;
import dao.I_ProduitDAO;
import dao.ProduitDAO_Oracle;

/**
 * Cette classe est l'implémentation de {@code I_DAOFactory} pour le SGBDR Oracle.
 * @see I_DAOFactory
 */

public class OracleFactory implements I_DAOFactory {

	private static OracleFactory INSTANCE;

	/**
	 * Essaie d'obtenir la référence à l'instance unique de {@code OracleFactory}.
	 * @return une référence vers l'instance unique.
	 */
	public static synchronized I_DAOFactory getInstance() {
		if (OracleFactory.INSTANCE == null)
			OracleFactory.INSTANCE = new OracleFactory();
		return OracleFactory.INSTANCE;
	}

	protected OracleFactory() {}

	@Override
	public I_CatalogueDAO createCatalogueDAO() throws Exception {
		return new CatalogueDAO_Oracle();
	}

	@Override
	public I_ProduitDAO createProduitDAO() throws Exception {
		return new ProduitDAO_Oracle();
	}
}
package factory;

import dao.CatalogueDAO_XML;
import dao.I_CatalogueDAO;
import dao.I_ProduitDAO;
import dao.ProduitDAO_XML;

/**
 * Cette classe est l'implémentation de {@code I_DAOFactory} pour une base de données XML.
 * @see I_DAOFactory
 */

public class XMLFactory implements I_DAOFactory{

	private static XMLFactory INSTANCE;

	/**
	 * Essaie d'obtenir la référence à l'instance unique de {@code XMLFactory}.
	 * @return une référence vers l'instance unique.
	 */
	public static synchronized I_DAOFactory getInstance() {
		if (XMLFactory.INSTANCE == null)
			XMLFactory.INSTANCE = new XMLFactory();
		return XMLFactory.INSTANCE;
	}

	protected XMLFactory() {}

	@Override
	public I_CatalogueDAO createCatalogueDAO() throws Exception {
		return new CatalogueDAO_XML();
	}

	@Override
	public I_ProduitDAO createProduitDAO() throws Exception {
		return new ProduitDAO_XML();
	}
}
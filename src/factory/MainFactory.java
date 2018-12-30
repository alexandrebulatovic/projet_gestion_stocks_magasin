package factory;

/**
 * Cette classe permet d'obtenir une implémentation de {@code I_DAOFactory} en fonction
 * du type de base de données que l'on utilise et que l'on va passer en paramètre à la méthode
 * principale qui est {@code createDAO}.
 * <P>
 * Les constantes qui peuvent être utilisées lors du passage de paramètres sont {@code TYPE_XML}
 * ou {@code TYPE_SQL_ORACLE}.
 * <P>
 * Si le développeur souhaite intégrer un nouveau type de base de données à l'application, il doit
 * rajouter une constante dans cette classe et prendre en compte son éventuelle utilisation dans la
 * méthode {@code createDAO}.
 * @see I_DAOFactory
 */

public class MainFactory {

	private static MainFactory INSTANCE;

	/**
	 * Indique à {@code createDAO} que l'on souhaite des objets {@code DAO} pour une base de données XML.
	 */
	public static final int TYPE_XML = 0;

	/**
	 * Indique à {@code createDAO} que l'on souhaite des objets {@code DAO} pour une base de données Oracle.
	 */
	public static final int TYPE_SQL_ORACLE = 1;

	/**
	 * Essaie d'obtenir la référence à l'instance unique de {@code MainFactory}.
	 * @return une référence vers l'instance unique.
	 */
	public static synchronized MainFactory getInstance() {
		if (MainFactory.INSTANCE == null)
			MainFactory.INSTANCE = new MainFactory();
		return MainFactory.INSTANCE;
	}

	protected MainFactory() {}

	/**
	 * Crée une instance de fabrique implémentant {@code I_DAOFactory} pour le type de base de données donné en paramètre.
	 * @param databaseType le type de base de données que l'on utilise parmi les constantes de {@code MainFactory}.
	 * @return une instance de fabrique du type demandé.
	 * @exception IllegalArgumentException si le type n'est pas renseigné.
	 */
	public I_DAOFactory createDAO(int databaseType){
		switch (databaseType){
		case TYPE_XML:
			return new XMLFactory();
		case TYPE_SQL_ORACLE:
			return new OracleFactory();
		default:
			throw new IllegalArgumentException("Le type de base de données indiqué n'existe pas.");
		}
	}
}
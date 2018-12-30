package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Cette classe permet aux objets {@code DAO} qui utilisent la base de
 * données Oracle d'obtenir un objet {@code Connection} vers le SGBDR en
 * utilisant la méthode {@code getConnexion}.
 * @see Connection
 */

public final class ConnexionDAO_Oracle {

	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String ADRESSE = "jdbc:oracle:thin:@162.38.222.149:1521:IUT"; // paramètres de connexion pour l'IUT
	private static final String USER = ""; // entrez votre nom d'utilisateur
	private static final String PASSWORD = ""; // entrez votre mot de passe

	private static ConnexionDAO_Oracle INSTANCE;
	private static Connection connexion;

	/**
	 * Essaie de fermer la connexion à la base de données Oracle.
	 * @return {@code true} en cas de succès sinon {@code false}.
	 */
	public static boolean fermerConnexion() {
		boolean fermeture_connexion = false;

		try {
			ConnexionDAO_Oracle.connexion.close();
			fermeture_connexion = true;
			ConnexionDAO_Oracle.INSTANCE = null;
		} catch (SQLException e) {}

		return fermeture_connexion;
	}

	/**
	 * Essaie d'obtenir la référence à l'instance unique de {@code ConnexionDAO_Oracle}.
	 * @return une référence vers l'instance unique de {@code ConnexionDAO_Oracle}.
	 * @throws Exception en cas d'échec de connexion à la base de données Oracle.
	 */
	public static synchronized ConnexionDAO_Oracle getInstance() throws Exception {
		if (ConnexionDAO_Oracle.INSTANCE == null)
			ConnexionDAO_Oracle.INSTANCE = new ConnexionDAO_Oracle();
		return ConnexionDAO_Oracle.INSTANCE;
	}

	/**
	 * Instancie un objet {@code Connection} pour la base de données Oracle.
	 * @throws Exception en cas d'échec de création de la connexion à la base de données Oracle.
	 */
	private ConnexionDAO_Oracle() throws Exception {
		Class.forName(ConnexionDAO_Oracle.DRIVER);
		ConnexionDAO_Oracle.connexion = DriverManager.getConnection(ConnexionDAO_Oracle.ADRESSE, ConnexionDAO_Oracle.USER, ConnexionDAO_Oracle.PASSWORD);
	}

	public Connection getConnexion() {return ConnexionDAO_Oracle.connexion;}
}
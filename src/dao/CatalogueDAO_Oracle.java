package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import metier.Catalogue;
import metier.I_Catalogue;
import metier.I_Produit;
import metier.Produit;

/**
 * Cette classe est l'implémentation de {@code I_CatalogueDAO} pour le SGBDR Oracle.
 * <P>
 * Elle utilise le langage SQL et l'API {@code JDBC}.
 * @see I_CatalogueDAO
 */

public class CatalogueDAO_Oracle implements I_CatalogueDAO {

	private Connection conn;
	private PreparedStatement prepstat;
	private Statement stat;
	private ResultSet rs;

	/**
	 * Essaie d'obtenir une connexion à la base de données Oracle qui permettra
	 * l'accès aux autres méthodes de la classe pour manipuler la base de données.
	 * @throws Exception en cas d'échec de connexion à la base de données Oracle.
	 */
	public CatalogueDAO_Oracle() throws Exception  {this.conn = ConnexionDAO_Oracle.getInstance().getConnexion();}

	@Override
	public boolean create(I_Catalogue catalogue) {
		String sql = "INSERT INTO Catalogues(nomCatalogue) VALUES (?)";

		try {
			this.prepstat = this.conn.prepareStatement(sql);
			this.prepstat.setString(1, catalogue.getNom());
			this.prepstat.executeUpdate();
			return true;

		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean delete(String nom) {
		String sql = "DELETE FROM Catalogues WHERE nomCatalogue = ?;";

		try {
			this.prepstat = this.conn.prepareStatement(sql);
			this.prepstat.setString(1, nom);
			this.prepstat.executeUpdate();
			return true;

		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean disconnect() {
		return ConnexionDAO_Oracle.fermerConnexion();
	}

	@Override
	public List<I_Catalogue> findAll() {

		// on crée la liste contenant tous les noms de catalogue de la base de données

		List<I_Catalogue> catalogues = new ArrayList<>();

		String sql = "SELECT nomCatalogue FROM Catalogues";

		try {
			this.stat = this.conn.createStatement();
			this.rs = this.stat.executeQuery(sql);

			while(this.rs.next()){
				I_Catalogue catalogue = new Catalogue(this.rs.getString(1));
				catalogues.add(catalogue);
			}

			// et ensuite on ajoute tous les produits des catalogues

			sql = "SELECT nomProduit, prixHT, quantite FROM Produits NATURAL JOIN Catalogues WHERE nomCatalogue = ?";
			this.prepstat = this.conn.prepareStatement(sql);

			for (int i = 0; i < catalogues.size(); i++) {
				List<I_Produit> produits = new ArrayList<>();

				this.prepstat.setString(1, catalogues.get(i).getNom());
				this.rs = this.prepstat.executeQuery();

				while(this.rs.next()){
					I_Produit produit = new Produit(this.rs.getString(1), this.rs.getDouble(2), this.rs.getInt(3));
					produits.add(produit);
				}
				catalogues.get(i).addProduits(produits);
			}

			return catalogues;

		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public int findNbProduits(String catalogue) throws Exception {
		String sql = "SELECT COUNT(idProduit) FROM Produits NATURAL JOIN Catalogues WHERE nomCatalogue = ?";

		this.prepstat = this.conn.prepareStatement(sql);
		this.prepstat.setString(1, catalogue);
		this.rs = this.prepstat.executeQuery();
		this.rs.next();

		return this.rs.getInt(1);
	}
}
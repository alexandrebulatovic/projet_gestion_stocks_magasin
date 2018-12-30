package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import metier.I_Produit;
import metier.Produit;


/**
 * Cette classe est l'implémentation de {@code I_ProduitDAO} pour le SGBDR Oracle.
 * <P>
 * Elle utilise le langage SQL et l'API {@code JDBC}.
 * @see I_ProduitDAO
 */

public class ProduitDAO_Oracle implements I_ProduitDAO {

	/**
	 * Correspond à l'opérateur d'addition.
	 */
	private static final char AJOUT = '+';
	/**
	 * Correspond à l'opérateur de soustraction.
	 */
	private static final char RETRAIT = '-';

	private Connection conn;
	private PreparedStatement prepstat;
	private ResultSet rs;

	/**
	 * Essaie d'obtenir une connexion à la base de données Oracle qui permettra
	 * l'accès aux autres méthodes de la classe pour manipuler la base de données.
	 * @throws Exception en cas d'échec de connexion à la base de données Oracle.
	 */
	public ProduitDAO_Oracle() throws Exception{this.conn = ConnexionDAO_Oracle.getInstance().getConnexion();}

	@Override
	public boolean addQuantite(String nomProduit, int quantite) {
		return this.UpdateStockProduit(nomProduit, quantite, ProduitDAO_Oracle.AJOUT);
	}

	@Override
	public boolean create(I_Produit produit, String nomCatalogue) {

		String sql = "INSERT INTO Produits(nomProduit, prixHT, quantite, idCatalogue) VALUES (?, ?, ?, ?)";

		try {
			this.prepstat = this.conn.prepareStatement(sql);

			int idCatalogue = this.getIdCatalogue(nomCatalogue);

			this.prepstat.setString(1, produit.getNom());
			this.prepstat.setDouble(2, produit.getPrixUnitaireHT());
			this.prepstat.setInt(3, produit.getStock());
			this.prepstat.setInt(4, idCatalogue);

			this.prepstat.executeUpdate();

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(String nomProduit) {
		// nomProduit est un attribut de type SQL UNIQUE donc on se permet ici de l'utiliser comme une PRIMARY KEY
		String sql = "DELETE FROM Produits WHERE nomProduit = ?";

		try {
			this.prepstat = this.conn.prepareStatement(sql);
			this.prepstat.setString(1, nomProduit);
			this.prepstat.executeUpdate();

			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public I_Produit find(String nomProduit) throws Exception {

		String sql = "SELECT nomProduit, prixHT, quantite FROM Produits WHERE nomProduit = ?";

		this.prepstat = this.conn.prepareStatement(sql);
		this.prepstat.setString(1, nomProduit);
		this.rs = this.prepstat.executeQuery();

		if (this.rs.next()){

			I_Produit produit = new Produit(this.rs.getString(1), this.rs.getDouble(2), this.rs.getInt(3));
			return produit;

		} else
			return null;
	}

	@Override
	public List<I_Produit> findAll(String nomCatalogue) {
		List<I_Produit> produits = new ArrayList<I_Produit>();

		String sql = "SELECT nomProduit, prixHT, quantite FROM Produits NATURAL JOIN Catalogues WHERE nomCatalogue = ?";

		try {
			this.prepstat = this.conn.prepareStatement(sql);
			this.prepstat.setString(1, nomCatalogue);
			this.rs = this.prepstat.executeQuery();

			while(this.rs.next()){
				I_Produit produit = new Produit(this.rs.getString(1), this.rs.getDouble(2), this.rs.getInt(3));
				produits.add(produit);
			}

			return produits;

		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public boolean removeQuantite(String nomProduit, int quantite) {
		return this.UpdateStockProduit(nomProduit, quantite, ProduitDAO_Oracle.RETRAIT);
	}

	/**
	 * Permet d'obtenir le numéro de {@code Catalogue} (qui est notre {@code FOREIGN KEY} dans la base
	 * de données) à partir de son nom.
	 * @param nomCatalogue le nom du catalogue dont on veut obtenir le numéro.
	 * @return le numéro du catalogue.
	 * @throws Exception si le catalogue que l'on recherche n'existe pas.
	 */
	private int getIdCatalogue(String nomCatalogue) throws Exception {
		String sql = "SELECT idCatalogue FROM Catalogues WHERE nomCatalogue = ?";

		this.prepstat = this.conn.prepareStatement(sql);
		this.prepstat.setString(1, nomCatalogue);
		this.rs = this.prepstat.executeQuery();

		if (this.rs.next())
			return this.rs.getInt(1);
		else
			throw new Exception();
	}

	/**
	 * Met à jour le stock du produit dans la base de données.
	 * @param nomProduit le nom du produit à mettre à jour.
	 * @param quantite la valeur à ajouter ou à enlever du stock actuel.
	 * @param operation l'opération à effectuer parmi les constantes {@code AJOUT}
	 * ou {@code RETRAIT}.
	 * @return {@code true} en cas de réussite, sinon {@code false} en cas d'erreur
	 * avec la base de données.
	 */
	private boolean UpdateStockProduit(String nomProduit, int quantite, char operation) {

		String sql = "UPDATE Produits SET quantite = quantite"+ operation + "? WHERE nomProduit = ?";

		try {
			this.prepstat = this.conn.prepareStatement(sql);
			this.prepstat.setInt(1, quantite);
			this.prepstat.setString(2, nomProduit);
			this.prepstat.executeUpdate();

			return true;

		} catch (SQLException e) {
			return false;
		}
	}
}
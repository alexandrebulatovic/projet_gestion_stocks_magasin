package controller;

import javax.swing.JOptionPane;

import dao.I_ProduitDAO;
import factory.MainFactory;
import metier.I_Catalogue;
import metier.I_Produit;
import metier.Produit;
import presentation.FenetreGestionProduits;

/**
 * Cette classe joue le rôle d'intermédiaire entre ce qui se passe dans la couche
 * d'accès aux données (objets {@code DAO}) et l'interface graphique (objets {@code Fenetre}).
 * <P>
 * {@code ControleurCatalogue} instancie et gère un objet {@code Catalogue} qui est un conteneur
 * de plusieurs produits qui ont chacun un nom, un stock et un prix de vente. Il y a donc des
 * méthodes pour supprimer, modifier ou créer un produit dans le {@code Catalogue} qui font appel à un objet
 * {@code ProduitDAO}.
 * <P>
 * Les exceptions lancées par l'objet {@code DAO} et les {@code Fenetre} sont gérées ici.
 */

public class ControleurCatalogue {

	private I_Catalogue catalogue;
	private I_ProduitDAO produitDAO;

	private FenetreGestionProduits fenetreGestionProduits;

	private boolean afficherMsgErreur = true;

	/**
	 * Crée un objet {@code ControleurCatalogue} qui va avoir accès à un catalogue qui
	 * sera déjà initialisé. Il essaie également d'obtenir un objet {@code ProduitDAO} qui servira
	 * à rendre persistantes les manipulations sur les produits du catalogue. Un message d'erreur
	 * s'affiche en cas d'échec.
	 * <P>
	 * Ce constructeur instancie également une fenêtre de gestion de catalogue pour le
	 * catalogue choisie préalablement par l'utilisateur.
	 * @param nomCatalogue le catalogue à utiliser par le contrôleur.
	 * @param typeBaseDeDonnees le type de base de données à utiliser parmi les constantes de {@code MainFactory}.
	 * @see MainFactory
	 */
	public ControleurCatalogue(I_Catalogue catalogue, int typeBaseDeDonnees){
		this.catalogue = catalogue;

		try {
			this.produitDAO = MainFactory.getInstance().createDAO(typeBaseDeDonnees).createProduitDAO();
		} catch (Exception e) {
			this.showMessage("Il y a eu un problème pour se connecter au système de persistance des données.");
			System.exit(1); // on arrête le programme car on ne peut rien faire sans accès à la base de données
		}

		this.fenetreGestionProduits = new FenetreGestionProduits(this);
	}

	/**
	 * Crée un objet {@code ControleurCatalogue} qui va avoir accès à un catalogue qui
	 * sera déjà initialisé mais également un objet {@code DAO} qui permet la persistance
	 * des données.
	 * <P>
	 * Ce constructeur sert aux tests automatisés et n'instancie pas de fenêtre comparé
	 * à {@link #ControleurCatalogue(I_Catalogue, int)}.
	 * @param produitDAO l'objet {@code DAO} pour les produits à utiliser.
	 * @param catalogue le cataloguer à utiliser.
	 */
	public ControleurCatalogue(I_ProduitDAO produitDAO, I_Catalogue catalogue){
		this.catalogue = catalogue;
		this.produitDAO = produitDAO;
	}

	/**
	 * Met à jour le stock d'un produit.
	 * @param nomProduit le nom du produit à mettre à jour.
	 * @param qteAchetee la quantité > 0 à rajouter au stock.
	 * @return {@code true} si la mise à jour du stock a réussie, {@code false} sinon
	 * et ouvre un message d'erreur.
	 */
	public boolean acheterStock(String nomProduit, int qteAchetee){

		if (nomProduit == null || nomProduit.equals("")) {

			this.showMessage("Veuillez entrer un nom de produit valide.");
			return false;

		} else if (qteAchetee <= 0){

			this.showMessage("Veuillez entrer une quantité valide.");
			return false;

		} else {

			if (this.catalogue.acheterStock(nomProduit, qteAchetee)){

				if (!this.produitDAO.addQuantite(nomProduit, qteAchetee)){

					this.showMessage("Erreur lors de la mise à jour du stock du produit dans la base de données.");
					return false;

				} else
					return true;

			} else {

				this.showMessage("Ce produit n'existe pas.");
				return false;
			}
		}
	}

	/**
	 * Ajoute un nouveau produit au {@code Catalogue}.
	 * @param nomProduit le nom du produit à ajouter.
	 * @param prixHT le prix hors taxe (HT) > 0 du produit à ajouter.
	 * @param quantite la quantité en stock >= 0 du produit à ajouter.
	 * @return {@code true} si l'ajout a réussi, {@code false} sinon
	 * et ouvre un message d'erreur.
	 */
	public boolean addProduit(String nomProduit, double prixHT, int quantite) {

		if (nomProduit == null || nomProduit.equals("")) {

			this.showMessage("Veuillez entrer un nom de produit valide.");
			return false;

		} else if (prixHT <= 0){

			this.showMessage("Veuillez entrer un prix valide.");
			return false;

		} else if (quantite < 0) {

			this.showMessage("Veuillez entrer une quantité valide.");
			return false;

		} else {

			I_Produit produit = new Produit(nomProduit, prixHT, quantite);

			try {
				if (this.produitDAO.find(nomProduit) != null || !this.catalogue.addProduit(produit)) {

					this.showMessage("Ce produit existe déjà.");
					return false;

				} else {

					if (!this.produitDAO.create(produit, this.catalogue.getNom())) {

						this.showMessage("Erreur lors de la création du nouveau produit dans la base de données.");
						return false;

					} else
						return true;
				}
			} catch (Exception e) {
				this.showMessage("Erreur lors de l'accès à la base de données.");
				return false;
			}
		}
	}

	public I_Catalogue getCatalogue() {return this.catalogue;}

	/**
	 * Permet d'obtenir une description du contenu du catalogue.
	 * @return une {@code String} représentant le contenu du catalogue, non vide.
	 */
	public String getDescriptionCatalogue() {return this.catalogue.toString();}

	public FenetreGestionProduits getFenetreGestionProduits() {return this.fenetreGestionProduits;}

	/**
	 * Permet d'obtenir le nom du catalogue utilisé par le contrôleur.
	 * @return une {@code String} nommant le catalogue actuellement utilisé.
	 */
	public String getNomCatalogue() {return this.catalogue.getNom();}

	/**
	 * Permet d'obtenir un tableau des noms de produits contenus dans le catalogue
	 * actuellement utilisé.
	 * @return un tableau de {@code String} où chaque case est le nom d'un produit.
	 */
	public String[] getNomProduits(){return this.catalogue.getNomProduits();}

	/**
	 * Supprime un produit du {@code Catalogue}.
	 * @param nomProduit le nom du produit à supprimer.
	 * @return {@code true} si la suppression du produit a réussi, {@code false}
	 * sinon et ouvre un message d'erreur.
	 */
	public boolean removeProduit(String nomProduit){

		if (nomProduit == null || nomProduit.equals("")) {

			this.showMessage("Veuillez entrer un nom de produit valide.");
			return false;

		} else {

			if (this.catalogue.removeProduit(nomProduit)) {

				if (!this.produitDAO.delete(nomProduit)) {

					this.showMessage("Erreur lors de la suppression du produit dans la base de données.");
					return false;

				} else
					return true;

			}  else {

				this.showMessage("Ce produit n'existe pas.");
				return false;
			}
		}
	}

	/**
	 * Sert à désactiver les pop-up lorsque l'on lance les tests unitaires
	 * pour éviter des affichages intempestifs pour chaque test {@code assertFalse}.
	 * @param afficherMsgErreur {@code true} pour activer les messages d'erreur, {@code false}
	 * pour les désactiver.
	 */
	public void setAfficherMsgErreur(boolean afficherMsgErreur) {
		this.afficherMsgErreur = afficherMsgErreur;
	}

	/**
	 * Affiche un message d'erreur dans une fenêtre pop-up si l'attribut
	 * {@code afficherMsgErreur} a la valeur {@code true}.
	 * @param msgErreur le message d'erreur à afficher.
	 */
	public void showMessage(String msgErreur) {
		if (this.afficherMsgErreur)
			JOptionPane.showMessageDialog(null, msgErreur, "	Erreur", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Met à jour le stock d'un produit.
	 * @param nomProduit le nom du produit à mettre à jour.
	 * @param qteVendue la quantité à enlever du stock tel que {@code 0 < quantité <= stock existant}.
	 * @return {@code true} si la mise à jour du stock a réussie, {@code false}
	 * sinon et ouvre un message d'erreur.
	 */
	public boolean vendreStock(String nomProduit, int qteVendue){

		if (nomProduit == null || nomProduit.equals("")) {

			this.showMessage("Veuillez entrer un nom de produit valide.");
			return false;

		} else if (qteVendue <= 0){

			this.showMessage("Veuillez entrer une quantité valide.");
			return false;

		} else {

			int stockDisponible = this.catalogue.getProduit(nomProduit).getStock();

			if (qteVendue > stockDisponible) {

				this.showMessage("Veuillez entrer une quantité inférieure au stock.");
				return false;

			} else {

				if (this.catalogue.vendreStock(nomProduit, qteVendue)){

					if (!this.produitDAO.removeQuantite(nomProduit, qteVendue)) {

						this.showMessage("Erreur lors de la mise à jour du stock du produit dans la base de données.");
						return false;

					} else
						return true;


				} else {

					this.showMessage("Ce produit n'existe pas.");
					return false;
				}
			}
		}
	}
}
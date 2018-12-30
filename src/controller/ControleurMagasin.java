package controller;

import java.util.List;

import javax.swing.JOptionPane;

import dao.I_CatalogueDAO;
import factory.MainFactory;
import metier.Catalogue;
import metier.I_Catalogue;
import metier.I_Magasin;
import metier.Magasin;
import presentation.FenetrePrincipale;


/**
 * Cette classe joue le rôle d'intermédiaire entre ce qui se passe dans la couche
 * d'accès aux données (objets {@code DAO}) et l'interface graphique (objets {@code Fenetre}).
 * <P>
 * {@code ControleurMagasin} est le premier objet lancé au démarrage du programme et il instancie et
 * gère un objet {@code Magasin} qui est un conteneur de plusieurs catalogues, eux-même pouvant contenir
 * plusieurs produits qui ont chacun un nom de produit, un stock et un prix de vente. Il y a donc des
 * méthodes pour supprimer ou créer un catalogue dans le {@code Magasin} qui font appel à un objet
 * {@code CatalogueDAO}.
 * <P>
 * Le {@code ControleurMagasin} va aussi permettre l'accès aux autres fonctionnalités du programme avec la
 * méthode {@code selectCatalogue} qui crée un objet {@code ControleurCatalogue} une fois que l'utilisateur
 * aura sélectionné un catalogue à ouvrir dans l'interface graphique.
 * <P>
 * Les exceptions lancées par l'objet {@code DAO} et la {@code Fenetre} sont gérées ici.
 * @see ControleurCatalogue
 */

public class ControleurMagasin {

	private I_Magasin magasin;
	private I_CatalogueDAO catalogueDAO;

	private ControleurCatalogue controleurCatalogue;

	private int typeBaseDeDonneesUtilisee;

	private boolean afficherMsgErreur = true;

	private FenetrePrincipale fenetrePrincipale;

	/**
	 * Crée l'objet conteneur {@code Magasin} et essaie d'obtenir une connexion à la
	 * base de données dont le type (XML, Oracle ou autres..) est passé en paramètre dans
	 * la classe {@code Main} du programme.
	 * <P>
	 * Le constructeur récupère ensuite les catalogues enregistrés dans la base de données
	 * et les insère dans l'objet {@code Magasin} puis il instancie la {@code FenetrePrincipale}
	 * du programme. Un message d'erreur s'affiche en cas d'échec d'accès à la base de données.
	 */
	public ControleurMagasin(int typeBaseDeDonnees) {
		this.magasin = new Magasin();
		this.typeBaseDeDonneesUtilisee = typeBaseDeDonnees;

		try {
			this.catalogueDAO = MainFactory.getInstance().createDAO(typeBaseDeDonnees).createCatalogueDAO(); // changer le type de base de données dans la classe Main si nécessaire
		} catch (Exception e) {
			this.showMessage("Il y a eu un problème pour se connecter à la base de données.");
			System.exit(1); // on arrête le programme car on ne peut rien faire sans accès à la base de données
		}

		this.remplirMagasin();

		this.fenetrePrincipale = new FenetrePrincipale(this);

		this.magasin.attacher(this.fenetrePrincipale); // on attache notre "view" à notre "model"
	}

	/**
	 * Pour créer un nouveau catalogue dans le {@code Magasin} et
	 * ouvre un message d'erreur en cas d'échec.
	 * @param nomCatalogue le nom du catalogue à créer.
	 */
	public void addCatalogue(String nomCatalogue) {

		if (nomCatalogue == null || nomCatalogue.equals(""))
			this.showMessage("Veuillez entrer un nom de catalogue valide.");
		else {

			Catalogue catalogue = new Catalogue(nomCatalogue);

			if (this.magasin.addCatalogue(catalogue)){

				if (!this.catalogueDAO.create(catalogue))
					this.showMessage("Erreur lors de la création du catalogue dans la base de données.");

			} else
				this.showMessage("Ce catalogue existe déjà.");
		}
	}

	/**
	 * Essaie de fermer proprement la connexion à la base de données.
	 */
	public void disconnect() {
		/*
		 * on n'utilise pas le retour de la méthode car la machine virtuelle JAVA
		 * est arrêtée dans tous les cas après l'appel à la méthode.
		 */

		this.catalogueDAO.disconnect();
	}

	/**
	 * Permet d'obtenir un tableau de tous les catalogues existants avec le nombre de produits différents par catalogue.
	 * @return un tableau de {@code String}, qui peut être vide (l'attribut {@code length} du tableau retourne 0).
	 */
	public String[] getDetailsCatalogue() {

		List<I_Catalogue> listeCatalogues = this.magasin.getListeCatalogues();
		int nbCataloguesMagasin = listeCatalogues.size();

		String[] detailsCatalogue = new String[nbCataloguesMagasin];

		for (int i = 0; i < nbCataloguesMagasin; i++){
			detailsCatalogue[i] = listeCatalogues.get(i).getNom() + " : " + listeCatalogues.get(i).getNbProduit() + " Produit(s)";
		}

		return detailsCatalogue;
	}

	/**
	 * Permet d'obtenir un tableau avec les noms des catalogues contenus dans le magasin.
	 * @return un tableau de {@code String}, qui peut être vide (l'attribut {@code length} du tableau retourne 0).
	 */
	public String[] getNomCatalogues() {return this.magasin.getNomCatalogues();}

	/**
	 * Pour supprimer un catalogue du {@code Magasin} et ouvre un message d'erreur
	 * en cas d'échec.
	 * @param nomCatalogue : le nom du catalogue à supprimer.
	 */
	public void removeCatalogue(String nomCatalogue) {

		if (nomCatalogue == null || nomCatalogue.equals(""))
			this.showMessage("Veuillez sélectionner un catalogue à supprimer.");
		else {

			if (this.magasin.removeCatalogue(nomCatalogue)){

				if (!this.catalogueDAO.delete(nomCatalogue))
					this.showMessage("Erreur lors de la suppression du catalogue dans la base de données.");
				else {
					if (this.controleurCatalogue != null) {

						// si on a une fenêtre de gestion produits ouverte pour le catalogue désormais supprimé
						if (this.controleurCatalogue.getCatalogue().getNom() == nomCatalogue)
							this.controleurCatalogue.getFenetreGestionProduits().dispose(); // on la ferme
					}
				}
			} else
				this.showMessage("Ce catalogue n'existe pas.");
		}
	}

	/**
	 * Permet d'ouvrir les fonctionnalités supplémentaires pour le {@code Catalogue} indiqué par l'utilisateur.
	 * @param nomCatalogue le nom du catalogue à utiliser.
	 */
	public void selectCatalogue(String nomCatalogue) {

		if (nomCatalogue == null || nomCatalogue.equals(""))
			this.showMessage("Veuillez sélectionner un catalogue à ouvrir.");
		else {

			I_Catalogue catalogue = this.getCatalogue(nomCatalogue);

			this.controleurCatalogue = new ControleurCatalogue(catalogue, this.typeBaseDeDonneesUtilisee);
			catalogue.attacher(this.fenetrePrincipale);
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
	 * Recherche un catalogue dans la liste de catalogues du magasin.
	 * @param nomCatalogue le catalogue à rechercher.
	 * @return l'objet {@code Catalogue} recherché ou {@code null} s'il n'existe pas.
	 */
	private I_Catalogue getCatalogue(String nomCatalogue) {
		List<I_Catalogue> listeCatalogues = this.magasin.getListeCatalogues();

		for (int i = 0; i < listeCatalogues.size(); i++) {
			if (listeCatalogues.get(i).getNom() == nomCatalogue) {
				return listeCatalogues.get(i);
			}
		}
		return null;
	}

	/**
	 * Pour insérer tous les catalogues enregistrés en base de données dans
	 * notre magasin. Un message d'erreur s'affiche en cas d'échec.
	 * @see Catalogue
	 * @see Magasin
	 */
	private void remplirMagasin() {

		List<I_Catalogue> listeCatalogues = this.catalogueDAO.findAll();

		if (listeCatalogues == null)
			this.showMessage("Erreur lors de la recupération des catalogues dans la base de données.");
		else
			this.magasin.addCatalogues(listeCatalogues);
	}

	/**
	 * Affiche un message d'erreur dans une fenêtre pop-up si l'attribut
	 * {@code afficherMsgErreur} a la valeur {@code true}.
	 * @param msgErreur le message d'erreur à afficher.
	 */
	private void showMessage(String msgErreur) {
		if (this.afficherMsgErreur)
			JOptionPane.showMessageDialog(null, msgErreur, "	Erreur", JOptionPane.ERROR_MESSAGE);
	}
}
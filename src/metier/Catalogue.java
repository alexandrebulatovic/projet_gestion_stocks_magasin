package metier;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import presentation.Observateur;

/**
 * Cette classe est une implémentation de {@code I_Catalogue} qui sert de conteneur à des objets {@code Produit}.
 * <P>
 * Elle possède également des méthodes pour manipuler les données au sein du conteneur.
 * @see I_Catalogue
 */

public class Catalogue implements I_Catalogue {

	private List<I_Produit> listeProduits;
	private List<Observateur> observateurs = new ArrayList<Observateur>();
	private String nom;

	public Catalogue(String nom) {
		this.listeProduits = new ArrayList<>();
		this.nom = nom;
	}

	@Override
	public boolean acheterStock(String nomProduit, int qteAchetee){

		if (this.produitExiste(nomProduit)) {

			I_Produit produit = this.getProduit(nomProduit);

			if (produit.ajouter(qteAchetee)) {
				this.avertir();
				return true;
			} else
				return false;
		} else
			return false;
	}

	@Override
	public boolean addProduit(I_Produit produit) {
		/*
		 * les 3 premières conditions sont déjà vérifiées dans le contrôleur du catalogue et
		 * servent ici seulement à faire passer les tests unitaires qui ont été créés
		 * préalablement à mon choix d'afficher des message d'erreur précis à l'utilisateur (ajouté
		 * lors de l'apport de quelques améliorations avant de partager le projet dans le e-portfolio)
		 */
		if (produit == null || produit.getPrixUnitaireHT() <= 0	|| produit.getStock() < 0 || this.produitExiste(produit))
			return false;
		else {
			this.listeProduits.add(produit);
			this.avertir();
			return true;
		}
	}

	@Override
	public boolean addProduit(String nomProduit, double prixHT, int stock){
		I_Produit produit = new Produit(nomProduit, prixHT, stock);

		if (this.addProduit(produit))
			return true;
		else
			return false;
	}

	@Override
	public int addProduits(List<I_Produit> produits) {
		int nbProduitsAjoutes = 0;

		if (produits != null) {
			for (int i = 0; i < produits.size(); i++) {
				if (this.addProduit(produits.get(i)))
					nbProduitsAjoutes++;
			}
		}

		this.avertir();
		return nbProduitsAjoutes;
	}

	@Override
	public void attacher(Observateur observateur){this.observateurs.add(observateur);}

	@Override
	public void avertir(){
		for(int i = 0 ; i < this.observateurs.size() ; i++)
			this.observateurs.get(i).mettreAJour();
	}

	@Override
	public void clear() {this.listeProduits.clear();}

	@Override
	public List<I_Produit> getListeProduits() {return this.listeProduits;}

	@Override
	public double getMontantTotalTTC() {
		double somme = 0;

		for (int i = 0; i < this.listeProduits.size(); i++)
			somme = somme + this.listeProduits.get(i).getPrixStockTTC();


		BigDecimal montantTotal = new BigDecimal(somme);
		montantTotal = montantTotal.setScale(2, RoundingMode.HALF_UP); // on arrondit à 2 chiffres après la virgule

		return montantTotal.doubleValue();
	}

	@Override
	public int getNbProduit() {return this.listeProduits.size();}

	@Override
	public String getNom() {return this.nom;}

	@Override
	public String[] getNomProduits() {
		int taille = this.listeProduits.size();

		String[] tabNomProduits = new String[taille];

		for (int i = 0; i < taille; i++)
			tabNomProduits[i] = this.listeProduits.get(i).getNom();

		Arrays.sort(tabNomProduits); // tri du tableau dans l'ordre alphabétique

		return tabNomProduits;
	}

	@Override
	public I_Produit getProduit(String nomProduit) {

		for (int i = 0; i < this.listeProduits.size(); i++){
			if (this.listeProduits.get(i).getNom().equals(nomProduit))
				return this.listeProduits.get(i);
		}

		return null;
	}

	@Override
	public boolean removeProduit(String nomProduit){
		boolean isDeleted = false;

		if (nomProduit != null){
			for (int i=0; i < this.listeProduits.size() && !isDeleted ; i++){
				if (this.listeProduits.get(i).getNom().equals(nomProduit)){
					this.listeProduits.remove(i);
					isDeleted = true;
				}
			}
		}

		if (isDeleted) // si on a supprimé un produit, on veut mettre à jour la fenetre principale
			this.avertir();

		return isDeleted;
	}

	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();

		for (int i=0; i < this.listeProduits.size(); i++){
			stringBuilder.append(this.listeProduits.get(i).toString());
			stringBuilder.append("\n");
		}

		stringBuilder.append("\n");
		stringBuilder.append("Montant total TTC du stock : " +	this.formaterNombreVirgule(this.getMontantTotalTTC()) + " €");

		return stringBuilder.toString();
	}

	@Override
	public boolean vendreStock(String nomProduit, int qteVendue) {

		if (this.produitExiste(nomProduit)) {

			I_Produit produit = this.getProduit(nomProduit);

			if (produit.enlever(qteVendue)) {

				this.avertir();
				return true;

			} else
				return false;
		} else
			return false;
	}

	/**
	 * Permet de formater un nombre à virgule pour afficher
	 * dans tous les cas 2 chiffres après la virgule.
	 * @param valeur un {@code double} à formater.
	 * @return un chaîne {@code String} avec le format requis.
	 */
	private String formaterNombreVirgule(double valeur) {
		DecimalFormat df = new DecimalFormat() ;

		df.setMaximumFractionDigits(2) ;
		df.setMinimumFractionDigits(2) ;
		df.setDecimalSeparatorAlwaysShown(true) ; // pour toujours afficher le prix au format décimal

		return df.format(valeur);
	}

	/**
	 * Recherche dans le catalogue si le produit existe déjà.
	 * @param produit le produit à rechercher.
	 * @return {@code true} si le produit existe déjà, sinon {@code false}.
	 */
	private boolean produitExiste(I_Produit produit) {return this.produitExiste(produit.getNom());}

	/**
	 * Recherche dans le catalogue si le produit existe déjà.
	 * @param nomProduit le nom du produit à rechercher.
	 * @return {@code true} si le produit existe déjà, sinon {@code false}.
	 */
	private boolean produitExiste(String nomProduit) {
		if (this.getProduit(nomProduit) == null)
			return false;
		else
			return true;
	}
}
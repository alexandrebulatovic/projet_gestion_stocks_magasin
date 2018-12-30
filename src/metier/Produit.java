package metier;

import java.text.DecimalFormat;

/**
 * Cette classe est une implémentation de {@code I_Produit} et correspond
 * à un produit facticement en vente dans un magasin.
 * @see I_Produit
 */

public class Produit implements I_Produit {

	private static final double tauxTVA = 0.2;
	private int quantiteStock;
	private String nom;
	private double prixUnitaireHT;

	/**
	 * Crée un nouveau produit.
	 * @param nom le nom du produit.
	 * @param prixUnitaireHT le prix HT (Hors Taxes) du produit.
	 * @param quantiteStock le stock du produit.
	 */
	public Produit(String nom, double prixUnitaireHT, int quantiteStock) {
		this.quantiteStock = quantiteStock;
		this.nom = this.formaterNom(nom);
		this.prixUnitaireHT = prixUnitaireHT;
	}

	@Override
	public boolean ajouter(int qteAchetee) {
		if (qteAchetee <= 0)
			return false;
		else {
			this.quantiteStock = this.quantiteStock + qteAchetee;
			return true;
		}
	}

	@Override
	public boolean enlever(int qteVendue) {
		if (qteVendue > this.quantiteStock || qteVendue <= 0)
			return false;
		else {
			this.quantiteStock = this.quantiteStock - qteVendue;
			return true;
		}
	}

	/**
	 *  Permet de supprimer les espaces et tabulations d'une chaîne de caractères.
	 *  @param text la chaîne de caractères à traiter.
	 *  @return une chaîne {@code String} sans espaces ni tabulations.
	 **/
	public String formaterNom(String text){
		text = text.replaceAll("\t"," ");
		text = text.trim();
		return text;
	}

	/**
	 * Permet de formater un nombre à virgule pour afficher
	 * dans tous les cas 2 chiffres après la virgule.
	 * @param valeur un {@code double} à formater.
	 * @return un chaîne {@code String} avec le format requis.
	 */
	public String formaterNombreVirgule(double valeur) {
		DecimalFormat df = new DecimalFormat() ;

		df.setMaximumFractionDigits(2) ;
		df.setMinimumFractionDigits(2) ;
		df.setDecimalSeparatorAlwaysShown(true) ; // pour toujours afficher le prix au format décimal

		return df.format(valeur);
	}

	@Override
	public String getNom() {return this.nom;}

	@Override
	public double getPrixStockTTC() {
		return (this.getPrixUnitaireTTC()*this.quantiteStock);
	}

	@Override
	public double getPrixUnitaireHT() {return this.prixUnitaireHT;}

	@Override
	public double getPrixUnitaireTTC() {
		return this.prixUnitaireHT*(1 + Produit.tauxTVA);
	}

	@Override
	public int getStock() {return this.quantiteStock;}

	@Override
	public String toString(){
		return this.nom + " - prix HT : " + this.formaterNombreVirgule(this.getPrixUnitaireHT()) +
				" € - prix TTC : " + this.formaterNombreVirgule(this.getPrixUnitaireTTC()) + " €"+
				" - quantité en stock : "+ this.getStock();
	}
}
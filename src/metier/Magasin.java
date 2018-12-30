package metier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import presentation.Observateur;

/**
 * Cette classe est une implémentation de {@code I_Magasin} et correspond
 * à un conteneur de catalogues.
 * @see I_Magasin
 */

public class Magasin implements I_Magasin {

	private List<I_Catalogue> listeCatalogues;
	private List<Observateur> observateurs = new ArrayList<Observateur>();

	public Magasin() {this.listeCatalogues = new ArrayList<>();}

	@Override
	public boolean addCatalogue(I_Catalogue catalogue) {
		if (this.catalogueExiste(catalogue))
			return false;
		else {
			this.listeCatalogues.add(catalogue);
			this.avertir();
			return true;
		}
	}

	@Override
	public int addCatalogues(List<I_Catalogue> catalogues){
		int nbCataloguesAjoutes = 0;

		if (catalogues != null) {
			for (int i = 0; i < catalogues.size(); i++) {
				if (this.addCatalogue(catalogues.get(i)))
					nbCataloguesAjoutes++;
			}
		}
		this.avertir();
		return nbCataloguesAjoutes;
	}

	@Override
	public void attacher(Observateur observateur){this.observateurs.add(observateur);}

	@Override
	public void avertir(){
		for(int i = 0 ; i < this.observateurs.size() ; i++)
			this.observateurs.get(i).mettreAJour();
	}

	@Override
	public void clear() {
		this.listeCatalogues.clear();
		this.avertir();
	}

	@Override
	public List<I_Catalogue> getListeCatalogues() {
		return this.listeCatalogues;
	}

	@Override
	public String[] getNomCatalogues() {
		int taille = this.listeCatalogues.size();

		String[] tabNomCatalogues = new String[taille];

		for (int i = 0; i < taille; i++)
			tabNomCatalogues[i] = this.listeCatalogues.get(i).getNom();

		Arrays.sort(tabNomCatalogues); // tri du tableau dans l'ordre alphabétique

		return tabNomCatalogues;
	}

	@Override
	public boolean removeCatalogue(String nom) {

		for (int i = 0; i < this.listeCatalogues.size(); i++) {

			if (this.listeCatalogues.get(i).getNom().equals(nom)){
				this.listeCatalogues.remove(i);
				this.avertir();
				return true;
			}
		}
		return false;
	}

	/**
	 * Recherche dans le magasin si un catalogue existe déjà.
	 * @param catalogue le catalogue à rechercher.
	 * @return {@code true} si le catalogue existe déjà, sinon {@code false}.
	 */
	private boolean catalogueExiste(I_Catalogue catalogue) {

		for (int i = 0; i < this.listeCatalogues.size() ; i++){
			if (this.listeCatalogues.get(i).getNom().equals(catalogue.getNom()))
				return true;
		}
		return false;
	}
}
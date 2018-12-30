package dao;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import metier.Catalogue;
import metier.I_Catalogue;
import metier.I_Produit;
import metier.Produit;

/**
 * Cette classe est l'implémentation de {@code I_CatalogueDAO} pour une base de données XML.
 * <P>
 * Elle utilise l'API {@code JDOM}.
 * @see I_CatalogueDAO
 */

public class CatalogueDAO_XML implements I_CatalogueDAO {

	/**
	 * Le chemin de fichier des données persistantes XML.
	 */
	private String nomFichier = "Catalogues.xml"; // fichier a placer dans le dossier du mini projet

	private Document doc;
	private Element root;
	private SAXBuilder saxBuilder;

	/**
	 * Essaie de créer un objet {@code org.jdom.Document} à partir du fichier XML de données persistantes.
	 * @throws Exception en cas d'échec de construction de l'arbre {@code JDOM}.
	 */
	public CatalogueDAO_XML() throws Exception {
		this.saxBuilder = new SAXBuilder();
		this.buildDoc();
	}

	@Override
	public boolean create(I_Catalogue catalogue) {

		try {
			this.buildDoc();

			Element nomCatalogue = new Element("nomCatalogue");
			nomCatalogue.setText(catalogue.getNom());

			Element nouveauCatalogue = new Element("catalogue");
			nouveauCatalogue.addContent(nomCatalogue);

			this.root.addContent(nouveauCatalogue);

			if (this.save())
				return true;
			else
				return false;

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(String nom) {

		Element catalogue  = this.find(nom);

		if (catalogue == null)
			return false;
		else {

			try {
				boolean isDeleted = this.root.removeContent(catalogue);

				if (!isDeleted)
					return false; // si la suppression par la méthode interne à JDOM échoue
				else {
					if (this.save())
						return true;
					else
						return false;
				}

			} catch (Exception e) {
				return false;
			}
		}
	}

	@Override
	public boolean disconnect() {return true;} // il n'y a pas de procédure de déconnexion pour le fichier XML donc on renvoie directement true

	@Override
	public List<I_Catalogue> findAll() {

		try {
			this.buildDoc();

			// on crée la liste de tous les catalogues dans la base de données

			List<I_Catalogue> catalogues = new ArrayList<>();

			List<Element> listCatalogue = this.root.getChildren("catalogue");
			Iterator<Element> iteratorCatalogue = listCatalogue.iterator();

			int positionListeCatalogues = 0;

			while(iteratorCatalogue.hasNext()) {
				Element elementCatalogue = iteratorCatalogue.next();
				String nom = elementCatalogue.getChildText("nomCatalogue");

				catalogues.add(new Catalogue(nom));

				// on rajoute ensuite les produits de ce catalogue

				List<I_Produit> produits = new ArrayList<>();

				List<Element> listProduit = elementCatalogue.getChildren("produit");
				Iterator<Element> iteratorProduit = listProduit.iterator();

				while(iteratorProduit.hasNext()) {

					Element elementProduit = iteratorProduit.next();

					String nomProduit = elementProduit.getChildText("nom");
					double prix = Double.parseDouble(elementProduit.getChildText("prixHT"));
					int quantite = Integer.parseInt(elementProduit.getChildText("quantite"));

					produits.add(new Produit(nomProduit, prix, quantite));
				}

				catalogues.get(positionListeCatalogues).addProduits(produits);
				positionListeCatalogues++;
			}

			return catalogues;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int findNbProduits(String catalogue) throws Exception {
		this.buildDoc();

		List<Element> list = this.root.getChildren("catalogue");
		Iterator<Element> itr = list.iterator();

		while(itr.hasNext()) {
			Element element = itr.next();

			if (element.getChildText("nomCatalogue").equals(catalogue)){

				List<Element> elements = element.getChildren("produit");
				return elements.size();
			}
		}
		// si on ne trouve pas de balise "catalogue" ou le nom du catalogue passé en paramètre, il y a un problème avec le fichier XML
		throw new Exception();
	}

	/**
	 * Sert à construire un arbre {@code JDOM} synchronisé avec le fichier XML
	 * dans notre objet attribut {@code Document}.
	 * <P>
	 * La méthode doit être appelée avant chaque accès à la racine {@code root}
	 * du fichier XML pour être sûr que l'on a la dernière version.
	 * @throws Exception en cas d'échec de construction de l'arbre {@code JDOM}.
	 */
	private void buildDoc() throws Exception {
		this.doc = this.saxBuilder.build(new File(this.nomFichier));
		this.root = this.doc.getRootElement();
	}

	/**
	 * Permet d'obtenir une référence (dans le fichier XML) vers un catalogue.
	 * <P>
	 * Cette méthode appelle {@code buildDoc} et un appel ultérieur n'est donc
	 * pas nécessaire.
	 * @param nomCatalogue le nom du catalogue à utiliser.
	 * @return soit une référence {@code org.jdom.Element} ou {@code null} en
	 * cas de problème avec le fichier XML.
	 */
	private Element find(String nomCatalogue){

		try {
			this.buildDoc();

			List<Element> list = this.root.getChildren("catalogue");
			Iterator<Element> itr = list.iterator();

			while(itr.hasNext()) {
				Element element = itr.next();
				if (element.getChildText("nomCatalogue").equals(nomCatalogue))
					return element;
			}
			// si on ne trouve pas le nom du catalogue qui pourtant existe dans les données "interne", il y a un probleme avec le fichier XML
			return null;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Enregistre les modifications apportées au fichier XML.
	 * @return {@code true} en cas de succès, sinon {@code false} en cas d'erreur.
	 */
	private boolean save() {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setExpandEmptyElements(true));

		try {
			FileOutputStream outputStream = new FileOutputStream(this.nomFichier);
			outputter.output(this.doc, outputStream);
			outputStream.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
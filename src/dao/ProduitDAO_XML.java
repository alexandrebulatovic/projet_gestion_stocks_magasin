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

import metier.I_Produit;
import metier.Produit;

/**
 * Cette classe est l'implémentation de {@code I_ProduitDAO} pour une base de données XML.
 * <P>
 * Elle utilise l'API {@code JDOM}.
 * @see I_ProduitDAO
 */
public class ProduitDAO_XML implements I_ProduitDAO {

	/**
	 * Correspond à l'opérateur d'addition.
	 */
	private static final char AJOUT = '+';
	/**
	 * Correspond à l'opérateur de soustraction.
	 */
	private static final char RETRAIT = '-';

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
	public ProduitDAO_XML() throws Exception {
		this.saxBuilder = new SAXBuilder();
		this.buildDoc();
	}

	@Override
	public boolean addQuantite(String nomProduit, int quantite) {
		return this.UpdateQuantiteProduit(nomProduit, quantite, ProduitDAO_XML.AJOUT);
	}

	@Override
	public boolean create(I_Produit produit, String nomCatalogue) {

		try {
			this.buildDoc();

			List<Element> list = this.root.getChildren("catalogue");
			Iterator<Element> itr = list.iterator();

			while(itr.hasNext()) { // on va parcourir les catalogues jusqu'à trouver celui passé en paramètre
				Element element = itr.next();

				if (element.getChildText("nomCatalogue").equals(nomCatalogue)){ // quand on trouve le catalogue, on crée un nouveau produit

					Element produitXML = new Element("produit");
					Element nom = new Element("nom");
					Element prix = new Element("prixHT");
					Element quantite = new Element("quantite");

					produitXML.addContent(nom.setText(produit.getNom()));
					produitXML.addContent(prix.setText(String.valueOf(produit.getPrixUnitaireHT())));
					produitXML.addContent(quantite.setText(String.valueOf(produit.getStock())));

					element.addContent(produitXML);

					if (this.save())
						return true;
					else
						return false;
				}
			}
			// si on ne trouve pas de balise "catalogue" ou le nom du catalogue passé en paramètre, il y a un problème avec le fichier XML
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(String nomProduit) {

		Element element = this.findProduitXML(nomProduit);

		if (element == null)
			return false;
		else {
			boolean isDeleted = element.getParent().removeContent(element);

			if (!isDeleted)
				return false; // si la suppression par la méthode interne à JDOM échoue
			else {
				if (this.save())
					return true;
				else
					return false;
			}
		}
	}

	@Override
	public I_Produit find(String nomProduit) throws Exception {
		this.buildDoc();

		// on va utiliser deux itérateurs pour parcourir tous les catalogues et tous les produits dans ces catalogues

		List<Element> listCatalogue = this.root.getChildren("catalogue");

		Iterator<Element> iteratorCatalogue = listCatalogue.iterator();

		while(iteratorCatalogue.hasNext()) {
			Element elementCatalogue = iteratorCatalogue.next();

			List<Element> listProduit = elementCatalogue.getChildren("produit"); // on rentre encore un niveau "en profondeur" pour chaque catalogue trouvé

			Iterator<Element> iteratorProduit = listProduit.iterator();

			while(iteratorProduit.hasNext()) {
				Element elementProduit = iteratorProduit.next();

				// et si on trouve le produit, on crée un objet produit qu'on renvoie

				if (elementProduit.getChildText("nom").equals(nomProduit)) {

					double prix = Double.parseDouble(elementProduit.getChildText("prixHT"));
					int stock = Integer.parseInt(elementProduit.getChildText("quantite"));

					I_Produit produit = new Produit(nomProduit, prix, stock);
					return produit;
				}
			}
		}

		return null;
	}

	@Override
	public List<I_Produit> findAll(String nomCatalogue) {

		try {
			this.buildDoc();

			// on crée la liste de produits
			List<I_Produit> produits = new ArrayList<>();

			List<Element> listCatalogue = this.root.getChildren("catalogue");
			Iterator<Element> iteratorCatalogue = listCatalogue.iterator();

			// on va rechercher le catalogue qui correspond à celui donné en paramètre
			while(iteratorCatalogue.hasNext()) {

				Element elementCatalogue = iteratorCatalogue.next();

				if (elementCatalogue.getChildText("nomCatalogue").equals(nomCatalogue)){

					// et quand on trouve ce catalogue, on va ajouter tous ses produits dans notre liste de produits

					List<Element> listProduit = elementCatalogue.getChildren("produit");
					Iterator<Element> iteratorProduit = listProduit.iterator();

					while(iteratorProduit.hasNext()) {

						Element elementProduit = iteratorProduit.next();

						String nomProduit = elementProduit.getChildText("nom");
						double prix = Double.parseDouble(elementProduit.getChildText("prixHT"));
						int quantite = Integer.parseInt(elementProduit.getChildText("quantite"));

						produits.add(new Produit(nomProduit, prix, quantite));
					}
				}
			}

			return produits;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean removeQuantite(String nomProduit, int quantite) {
		return this.UpdateQuantiteProduit(nomProduit, quantite, ProduitDAO_XML.RETRAIT);
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
	 * Permet d'obtenir une référence (dans le fichier XML) vers un produit.
	 * @param nomProduit le nom du produit à utiliser.
	 * @return soit une référence {@code org.jdom.Element} ou {@code null} en
	 * cas de problème avec le fichier XML.
	 */
	private Element findProduitXML(String nomProduit) {

		try {
			this.buildDoc();

			// on va utiliser deux itérateurs pour parcourir tous les catalogues et tous les produits dans ces catalogues

			List<Element> listCatalogue = this.root.getChildren("catalogue");

			Iterator<Element> iteratorCatalogue = listCatalogue.iterator();

			while(iteratorCatalogue.hasNext()) {
				Element elementCatalogue = iteratorCatalogue.next();

				List<Element> listProduit = elementCatalogue.getChildren("produit"); // on rentre encore un niveau "en profondeur" pour chaque catalogue trouvé

				Iterator<Element> iteratorProduit = listProduit.iterator();

				while(iteratorProduit.hasNext()) {
					Element elementProduit = iteratorProduit.next();
					if (elementProduit.getChildText("nom").equals(nomProduit))
						return elementProduit;
				}
			}
			// si on ne trouve pas le nom du produit qui pourtant existe dans les données "interne", il y a un probleme avec le fichier XML
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

	/**
	 * Met à jour le stock du produit dans la base de données.
	 * @param nomProduit le nom du produit à mettre à jour.
	 * @param quantite la valeur à ajouter ou à enlever du stock actuel.
	 * @param operation l'opération à effectuer parmi les constantes {@code AJOUT}
	 * ou {@code RETRAIT}.
	 * @return {@code true} en cas de réussite, sinon {@code false} en cas d'erreur
	 * avec la base de données.
	 */
	private boolean UpdateQuantiteProduit(String nomProduit, int quantite, char operation) {

		Element produit = this.findProduitXML(nomProduit);

		if (produit == null)
			return false;
		else {

			int stock = Integer.parseInt(produit.getChildText("quantite"));

			if (operation == ProduitDAO_XML.AJOUT)
				produit.getChild("quantite").setText(String.valueOf(stock + quantite));
			else if (operation == ProduitDAO_XML.RETRAIT)
				produit.getChild("quantite").setText(String.valueOf(stock - quantite));

			if (this.save())
				return true;
			else
				return false;
		}
	}
}
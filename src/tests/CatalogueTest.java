package tests;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import metier.Catalogue;
import metier.I_Catalogue;
import metier.I_Produit;
import metier.Produit;

/**
 * Cette classe a été fournie par le professeur et sert à vérifier que le programme
 * répond à ses spécifications. Elle a, par conséquent, été laissé telle quelle.
 */

public class CatalogueTest {

	I_Catalogue cat;

	@Before
	public void setUp() {
		this.cat = new Catalogue("Catalogue_1");
	}

	@Test
	public void testAcheterProduit_existe() {
		this.cat.addProduit("Mars", 10, 1);
		this.cat.addProduit("Treets", 10, 1);
		this.cat.addProduit("Raider", 12, 2);
		Assert.assertTrue("acheter produit qui existe", this.cat.acheterStock("Raider", 3));
	}

	@Test
	public void testAcheterProduit_existePas() {
		this.cat.addProduit("Mars", 10, 1);
		this.cat.addProduit("Treets", 10, 1);
		this.cat.addProduit("Raider", 12, 2);
		Assert.assertFalse("acheter produit qui n'existe pas", this.cat.acheterStock("Nuts", 3));
	}

	@Test
	public void testAcheterProduit_quantiteNegative() {
		this.cat.addProduit("Mars", 10, 1);
		this.cat.addProduit("Treets", 10, 1);
		this.cat.addProduit("Raider", 12, 2);
		Assert.assertFalse("acheter quantité négative", this.cat.acheterStock("Mars", -4));
	}

	@Test
	public void testAcheterProduit_quantiteNulle() {
		this.cat.addProduit("Mars", 10, 1);
		this.cat.addProduit("Treets", 10, 1);
		this.cat.addProduit("Raider", 12, 2);
		Assert.assertFalse("acheter quantité nulle", this.cat.acheterStock("Treets", 0));
	}

	@Test
	public void testAddProduitIProduit_deuxFoisMemeProduitConsecutif() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		I_Produit p2 = this.createProduit("Treets", 10, 1);
		this.cat.addProduit(p2);
		Assert.assertFalse("ajout deux fois même produit consécutif", this.cat.addProduit(p2));
	}

	@Test
	public void testAddProduitIProduit_deuxFoisMemeProduitNonConsecutif() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		I_Produit p2 = this.createProduit("Treets", 10, 1);
		this.cat.addProduit(p2);
		Assert.assertFalse("ajout deux fois même produit non consécutif", this.cat.addProduit(p1));
	}

	@Test
	public void testAddProduitIProduit_deuxFoisProduitMemeNomConsecutif() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		I_Produit p2 = this.createProduit("Treets", 10, 1);
		this.cat.addProduit(p2);
		I_Produit p3 = this.createProduit("Treets", 15, 2);
		Assert.assertFalse("ajout deux produits avec même nom consécutif", this.cat.addProduit(p3));
	}

	@Test
	public void testAddProduitIProduit_deuxFoisProduitMemeNomEspacesALaFin() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		I_Produit p2 = this.createProduit("Mars ", 15, 2);
		Assert.assertFalse("ajout deux produits avec même nom mais un avec des espaces à la fin", this.cat.addProduit(p2));
	}

	@Test
	public void testAddProduitIProduit_deuxFoisProduitMemeNomEspacesAuDebut() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		I_Produit p2 = this.createProduit(" Mars", 15, 2);
		Assert.assertFalse("ajout deux produits avec même nom mais un avec des espaces au début", this.cat.addProduit(p2));
	}

	@Test
	public void testAddProduitIProduit_deuxFoisProduitMemeNomNonConsecutif() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		I_Produit p2 = this.createProduit("Treets", 10, 1);
		this.cat.addProduit(p2);
		I_Produit p3 = this.createProduit("Mars", 15, 2);
		Assert.assertFalse("ajout deux produits avec même nom non consecutif", this.cat.addProduit(p3));
	}

	@Test
	public void testAddProduitIProduit_deuxFoisProduitMemeNomTabulationsALaFin() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		I_Produit p2 = this.createProduit("Mars	", 15, 2);
		Assert.assertFalse("ajout deux produits avec même nom mais un avec des tabulations à la fin", this.cat.addProduit(p2));
	}

	@Test
	public void testAddProduitIProduit_deuxFoisProduitMemeNomTabulationsAuDebut() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		I_Produit p2 = this.createProduit("	Mars", 15, 2);
		Assert.assertFalse("ajout deux produits avec même nom mais un avec des tabulations au début", this.cat.addProduit(p2));
	}

	@Test
	public void testAddProduitIProduit_deuxProduits() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		I_Produit p2 = this.createProduit("Treets", 10, 1);
		Assert.assertTrue("ajout deux produits", this.cat.addProduit(p2));
	}

	@Test
	public void testAddProduitIProduit_null() {
		I_Produit p1 = null;
		Assert.assertFalse("ajout produit null", this.cat.addProduit(p1));
	}

	@Test
	public void testAddProduitIProduit_prixNegatif() {
		I_Produit p1 = this.createProduit("Bounty", -5, 4);
		Assert.assertFalse("ajout produit avec un prix négatif", this.cat.addProduit(p1));
	}


	@Test
	public void testAddProduitIProduit_prixNul() {
		I_Produit p1 = this.createProduit("Lion", 0, 3);
		Assert.assertFalse("ajout produit avec un prix nul", this.cat.addProduit(p1));
	}

	@Test
	public void testAddProduitIProduit_stockNegatif() {
		I_Produit p1 = this.createProduit("Raider", 10, -1);
		Assert.assertFalse("ajout produit avec un stock négatif", this.cat.addProduit(p1));
	}

	@Test
	public void testAddProduitIProduit_stockNul() {
		I_Produit p1 = this.createProduit("Snickers", 1, 0);
		Assert.assertTrue("ajout produit avec un stock nul", this.cat.addProduit(p1));
	}

	@Test
	public void testAddProduitIProduit_unProduit() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		Assert.assertTrue("ajout un produit", this.cat.addProduit(p1));
	}

	@Test
	public void testAddProduits_avecPrixNegatif() {
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p1 = this.createProduit("Topset", -8, 3);
		I_Produit p2 = this.createProduit("Nuts", 4, 6);
		liste.add(p1);
		liste.add(p2);
		Assert.assertEquals("ajout liste produit avec prix négatif",1, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_avecPrixNul() {
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p1 = this.createProduit("Nuts", 0, 1);
		liste.add(p1);
		Assert.assertEquals("ajout liste produit avec prix nul",0, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_avecStocksNegatifs() {
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p1 = this.createProduit("Mars", 15, 2);
		I_Produit p2 = this.createProduit("Kit Kat", 8, -3);
		I_Produit p3 = this.createProduit("Lion", 4, 6);
		liste.add(p1);
		liste.add(p2);
		liste.add(p3);
		Assert.assertEquals("ajout liste produit avec stock négatif",2, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_avecStocksNull() {
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p1 = this.createProduit("Mars", 15, 2);
		I_Produit p2 = this.createProduit("Snickers", 1, 0);
		I_Produit p3 = this.createProduit("Lion", 4, 6);
		liste.add(p1);
		liste.add(p2);
		liste.add(p3);
		Assert.assertEquals("ajout liste produit avec stock nul",3, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_null() {
		List<I_Produit> liste = null;
		Assert.assertEquals("ajout liste null", 0, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_produitsAvecCertainsDoublons() {
		I_Produit p1 = this.createProduit("Twix", 10, 4);
		I_Produit p2 = this.createProduit("Bounty", 11, 2);
		this.cat.addProduit(p1);
		this.cat.addProduit(p2);
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p3 = this.createProduit("Mars", 15, 2);
		I_Produit p4 = this.createProduit("Twix", 10, 6);
		I_Produit p5 = this.createProduit("M&M's", 8, 1);
		I_Produit p6 = this.createProduit("Bounty", 4, 2);
		liste.add(p3);
		liste.add(p4);
		liste.add(p5);
		liste.add(p6);
		liste.add(p2);
		liste.add(p4);
		Assert.assertEquals("ajout liste avec plusieurs produits déjà dans le catalogue",2, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_produitsAvecDoublonsNomProduitsEspacesALaFin() {
		I_Produit p1 = this.createProduit("Mars", 10, 4);
		I_Produit p2 = this.createProduit("Treets", 11, 2);
		this.cat.addProduit(p1);
		this.cat.addProduit(p2);
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p3 = this.createProduit("Mars ", 15, 2);
		I_Produit p4 = this.createProduit("Twix", 10, 6);
		I_Produit p5 = this.createProduit("M&M's", 8, 1);
		I_Produit p6 = this.createProduit("Bounty", 4, 2);
		liste.add(p3);
		liste.add(p4);
		liste.add(p5);
		liste.add(p6);
		liste.add(p2);
		liste.add(p4);
		Assert.assertEquals("ajout liste avec produit espace à la fin du nom",3, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_produitsAvecDoublonsNomProduitsEspacesAuDebut() {
		I_Produit p1 = this.createProduit("Mars", 10, 4);
		I_Produit p2 = this.createProduit("Treets", 11, 2);
		this.cat.addProduit(p1);
		this.cat.addProduit(p2);
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p3 = this.createProduit(" Mars", 15, 2);
		I_Produit p4 = this.createProduit("Twix", 10, 6);
		I_Produit p5 = this.createProduit("M&M's", 8, 1);
		I_Produit p6 = this.createProduit("Bounty", 4, 2);
		liste.add(p3);
		liste.add(p4);
		liste.add(p5);
		liste.add(p6);
		liste.add(p2);
		liste.add(p4);
		Assert.assertEquals("ajout liste avec produit espaces au début du nom",3, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_produitsAvecDoublonsNomProduitsTabulationsALaFin() {
		I_Produit p1 = this.createProduit("Mars", 10, 4);
		I_Produit p2 = this.createProduit("Treets", 11, 2);
		this.cat.addProduit(p1);
		this.cat.addProduit(p2);
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p3 = this.createProduit("Mars ", 15, 2);
		I_Produit p4 = this.createProduit("Twix", 10, 6);
		I_Produit p5 = this.createProduit("M&M's", 8, 1);
		I_Produit p6 = this.createProduit("Bounty", 4, 2);
		liste.add(p3);
		liste.add(p4);
		liste.add(p5);
		liste.add(p6);
		liste.add(p2);
		liste.add(p4);
		Assert.assertEquals("ajout liste avec produit tabulation à la fin du nom",3, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_produitsAvecDoublonsNomProduitsTabulationsAuDebut() {
		I_Produit p1 = this.createProduit("Mars", 10, 4);
		I_Produit p2 = this.createProduit("Treets", 11, 2);
		this.cat.addProduit(p1);
		this.cat.addProduit(p2);
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p3 = this.createProduit("	Mars", 15, 2);
		I_Produit p4 = this.createProduit("Twix", 10, 6);
		I_Produit p5 = this.createProduit("M&M's", 8, 1);
		I_Produit p6 = this.createProduit("Bounty", 4, 2);
		liste.add(p3);
		liste.add(p4);
		liste.add(p5);
		liste.add(p6);
		liste.add(p2);
		liste.add(p4);
		Assert.assertEquals("ajout liste avec produit tabulations au début du nom",3, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_produitsAvecNomsDoublons() {
		I_Produit p1 = this.createProduit("Mars", 10, 4);
		I_Produit p2 = this.createProduit("Treets", 11, 2);
		this.cat.addProduit(p1);
		this.cat.addProduit(p2);
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p3 = this.createProduit("Mars", 15, 2);
		I_Produit p4 = this.createProduit("Treets", 10, 6);
		liste.add(p3);
		liste.add(p4);
		Assert.assertEquals("ajout liste produits dont tous les noms dans le catalogue",0, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_produitsAvecQueDesDoublons() {
		I_Produit p1 = this.createProduit("Mars", 10, 4);
		I_Produit p2 = this.createProduit("Treets", 11, 2);
		this.cat.addProduit(p1);
		this.cat.addProduit(p2);
		List<I_Produit> liste = new ArrayList<I_Produit>();
		liste.add(p1);
		liste.add(p2);
		Assert.assertEquals("ajout liste avec tous les produits déjà dans le catalogue",0, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_produitsAvecUnSeulDoublon() {
		I_Produit p1 = this.createProduit("Mars", 10, 4);
		I_Produit p2 = this.createProduit("Treets", 11, 2);
		this.cat.addProduit(p1);
		this.cat.addProduit(p2);
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p3 = this.createProduit("Mars", 15, 2);
		I_Produit p4 = this.createProduit("Twix", 10, 6);
		I_Produit p5 = this.createProduit("M&M's", 8, 1);
		I_Produit p6 = this.createProduit("Bounty", 4, 2);
		liste.add(p3);
		liste.add(p4);
		liste.add(p5);
		liste.add(p6);
		liste.add(p2);
		liste.add(p4);
		Assert.assertEquals("ajout liste avec un seul des produits déjà dans le catalogue",3, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_produitsSansDoublonsAvecCatalogueDejaRempli() {
		I_Produit p0 = this.createProduit("Twix", 10, 6);
		this.cat.addProduit(p0);
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p1 = this.createProduit("Mars", 10, 4);
		I_Produit p2 = this.createProduit("Treets", 11, 2);
		liste.add(p1);
		liste.add(p2);
		Assert.assertEquals("ajout liste avec deux produits dans un catalogue plein",2, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_produitsSansDoublonsAvecCatalogueVide() {
		List<I_Produit> liste = new ArrayList<I_Produit>();
		I_Produit p1 = this.createProduit("Mars", 10, 4);
		I_Produit p2 = this.createProduit("Treets", 11, 2);
		liste.add(p1);
		liste.add(p2);
		Assert.assertEquals("ajout liste avec deux produits dans un catalogue vide",2, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduits_vide() {
		List<I_Produit> liste = new ArrayList<I_Produit>();
		Assert.assertEquals("ajout liste vide", 0, this.cat.addProduits(liste));
	}

	@Test
	public void testAddProduitStringDoubleInt_deuxFoisMemeNomConsecutif() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		I_Produit p2 = this.createProduit("Treets", 10, 1);
		this.cat.addProduit(p2);
		Assert.assertFalse("ajout deux fois même produit consécutif", this.cat.addProduit("Treets", 10, 1));
	}

	@Test
	public void testAddProduitStringDoubleInt_deuxFoisMemeNomNonConsecutif() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		I_Produit p2 = this.createProduit("Treets", 10, 1);
		this.cat.addProduit(p2);
		Assert.assertFalse("ajout deux fois même produit non consécutif", this.cat.addProduit("Mars", 10, 1));
	}

	@Test
	public void testAddProduitStringDoubleInt_deuxFoisProduitMemeNomEspacesALaFin() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		Assert.assertFalse("ajout deux fois même produit mais un avec espaces à la fin", this.cat.addProduit("Mars ", 10, 1));
	}

	@Test
	public void testAddProduitStringDoubleInt_deuxFoisProduitMemeNomEspacesAuDebut() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		Assert.assertFalse("ajout deux fois même produit mais un avec espaces au début", this.cat.addProduit(" Mars", 10, 1));
	}

	@Test
	public void testAddProduitStringDoubleInt_deuxFoisProduitMemeNomTabulationsALaFin() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		Assert.assertFalse("ajout deux fois même produit mais un avec tabulations à la fin", this.cat.addProduit("Mars	", 10, 1));
	}

	@Test
	public void testAddProduitStringDoubleInt_deuxFoisProduitMemeNomTabulationsAuDebut() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		Assert.assertFalse("ajout deux fois même produit mais un avec tabulations au début", this.cat.addProduit("	Mars", 10, 1));
	}

	@Test
	public void testAddProduitStringDoubleInt_deuxProduit() {
		I_Produit p1 = this.createProduit("Mars", 10, 1);
		this.cat.addProduit(p1);
		Assert.assertTrue("ajout deux produits", this.cat.addProduit("Treets", 10, 1));
	}

	@Test
	public void testAddProduitStringDoubleInt_prixNegatif() {
		Assert.assertFalse("ajout produit avec prix négatif", this.cat.addProduit("Bounty", -5, 4));
	}

	@Test
	public void testAddProduitStringDoubleInt_prixNul() {
		Assert.assertFalse("ajout produit avec prix nul", this.cat.addProduit("Lion", 0, 3));
	}

	@Test
	public void testAddProduitStringDoubleInt_stockNegatif() {
		Assert.assertFalse("ajout produit avec stock négatif", this.cat.addProduit("Raider", 10, -1));
	}

	@Test
	public void testAddProduitStringDoubleInt_stockNul() {
		Assert.assertTrue("ajout produit avec stock nul", this.cat.addProduit("Snickers", 1, 0));
	}

	@Test
	public void testAddProduitStringDoubleInt_unProduit() {
		Assert.assertTrue("ajout un produit", this.cat.addProduit("Mars", 10, 1));
	}

	@Test
	public void testClear() {
		List<I_Produit> p = new ArrayList<I_Produit>();
		this.cat.clear();
		Assert.assertEquals("non egal",this.cat.getListeProduits(), p);
	}

	@Test
	public void testConstructeurCatalogue() {
		Assert.assertNotNull("créer catalogue", this.cat);
	}

	@Test
	public void testGetNomProduits_deuxProduits() {
		String[] tab = {"Mars", "Treets"};
		this.cat.addProduit("Mars", 10, 1);
		this.cat.addProduit("Treets", 10, 1);
		Assert.assertArrayEquals("recupère nom de deux produits", tab, this.cat.getNomProduits());
	}

	@Test
	public void testGetNomProduits_plusieursProduitsInseresOrdreAleatoire() {
		String[] tab = {"Bounty", "Mars", "Raider", "Treets"};
		this.cat.addProduit("Mars", 10, 1);
		this.cat.addProduit("Treets", 10, 1);
		this.cat.addProduit("Raider", 12, 2);
		this.cat.addProduit("Bounty", 12, 2);
		Assert.assertArrayEquals("recupère nom de plusieurs produits ajoutés dans ordre aléatoire (doivent être retournés dans l'ordre alphabétique)", tab, this.cat.getNomProduits());
	}

	@Test
	public void testGetNomProduits_plusieursProduitsInseresOrdreAlphabetique() {
		String[] tab = {"Mars", "Raider", "Treets"};
		this.cat.addProduit("Mars", 10, 1);
		this.cat.addProduit("Raider", 12, 2);
		this.cat.addProduit("Treets", 10, 1);
		Assert.assertArrayEquals("recupère nom de plusieurs produits ajoutés dans ordre alphabétique", tab, this.cat.getNomProduits());
	}

	@Test
	public void testGetNomProduits_unProduit() {
		String[] tab = {"Mars"};
		this.cat.addProduit("Mars", 10, 1);
		Assert.assertArrayEquals("recupère nom produits avec un seul produit", tab, this.cat.getNomProduits());
	}

	@Test
	public void testGetNomProduits_unProduitAvecNomEspacesALaFin() {
		String[] tab = {"Mars"};
		this.cat.addProduit("Mars ", 10, 1);
		Assert.assertArrayEquals("recupère nom produit avec espace fin ; les espaces à la fin ne doivent pas être stockés", tab, this.cat.getNomProduits());
	}

	@Test
	public void testGetNomProduits_unProduitAvecNomEspacesAuDebut() {
		String[] tab = {"Mars"};
		this.cat.addProduit(" Mars", 10, 1);
		Assert.assertArrayEquals("recupère nom produit avec espace debut ; les espaces au début ne doivent pas être stockés", tab, this.cat.getNomProduits());
	}

	@Test
	public void testGetNomProduits_unProduitAvecNomEspacesAuMilieu() {
		String[] tab = {"Kit Kat"};
		this.cat.addProduit("Kit Kat", 10, 1);
		Assert.assertArrayEquals("recupère nom produit avec des espace au milieu", tab, this.cat.getNomProduits());
	}

	@Test
	public void testGetNomProduits_unProduitAvecNomTabulationsALaFin() {
		String[] tab = {"Mars"};
		this.cat.addProduit("Mars	", 10, 1);
		Assert.assertArrayEquals("recupère nom produit avec tabulation fin ; les tabulations à la fin ne doivent pas être stockés", tab, this.cat.getNomProduits());
	}

	@Test
	public void testGetNomProduits_unProduitAvecNomTabulationsAuDebut() {
		String[] tab = {"Mars"};
		this.cat.addProduit("	Mars", 10, 1);
		Assert.assertArrayEquals("recupère nom produit avec tabulation debut ; les tabulations au début ne doivent pas être stockés", tab, this.cat.getNomProduits());
	}

	@Test
	public void testGetNomProduits_unProduitAvecNomTabulationsAuMilieu() {
		String[] tab = {"Kit Kat"};
		this.cat.addProduit("Kit	Kat", 10, 1);
		Assert.assertArrayEquals("recupère nom produit avec des tabulations au milieu ; les tabulations au milieu doivent être remplacées par des espaces", tab, this.cat.getNomProduits());
	}

	@Test
	public void testGetNomProduits_vide() {
		String[] tab0 = new String[0];
		Assert.assertArrayEquals("recupère nom produits catalogue vide", tab0, this.cat.getNomProduits());
	}

	@Test
	public void testMontantTotalTTC_avecVirgule_AvecArrondiInferieur_TroisChiffresApresLaVirgule() {
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 4);
		this.cat.addProduit("Raider", 1, 10);
		this.cat.addProduit("Twix", 12.66, 1);
		Assert.assertEquals("montant TTC avec virgule ; 135.192 doit être arrondi à 135.19",135.19,this.cat.getMontantTotalTTC(),0);
	}

	@Test
	public void testMontantTotalTTC_avecVirgule_AvecArrondiSuperieur_TroisChiffresApresLaVirgule() {
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 4);
		this.cat.addProduit("Raider", 1, 10);
		this.cat.addProduit("Twix", 12.69, 1);
		Assert.assertEquals("montant TTC avec virgule ; 135.228 doit être arrondi à 135.23",135.23,this.cat.getMontantTotalTTC(),0);
	}

	@Test
	public void testMontantTotalTTC_avecVirgule_AvecArrondiSuperieur_TroisChiffresApresLaVirgule_IlNeFautPasArrondirLePrixDuStockUnitaireMaisLePrixDuStockTotal() {
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 4);
		this.cat.addProduit("Raider", 1, 10);
		this.cat.addProduit("Twix", 12.67, 1);
		this.cat.addProduit("Nuts", 12.67, 1);
		Assert.assertEquals("c'est le montant total TTC qu'il faut arrondir, pas les prix TTC des différents produits",150.41,this.cat.getMontantTotalTTC(),0);
	}

	@Test
	public void testMontantTotalTTC_avecVirgule_SansArrondi_DeuxChiffresApresLaVirgule() {
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 4);
		this.cat.addProduit("Raider", 1, 10);
		this.cat.addProduit("Twix", 12.6, 1);
		Assert.assertEquals("montant TTC avec virgule ; 2 chiffres",135.12,this.cat.getMontantTotalTTC(),0);
	}

	@Test
	public void testMontantTotalTTC_avecVirgule_SansArrondi_UnChiffreApresLaVirgule() {
		this.cat.addProduit("Mars", 10, 6);
		this.cat.addProduit("Treets", 10, 4);
		this.cat.addProduit("Raider", 1, 12);
		Assert.assertEquals("montant TTC avec virgule ; 1 chiffre",134.4,this.cat.getMontantTotalTTC(),0);
	}

	@Test
	public void testMontantTotalTTC_catalogueVide() {
		Assert.assertEquals("montant TTC catalogue vide",0,this.cat.getMontantTotalTTC(),0);
	}

	@Test
	public void testMontantTotalTTC_pasDeStock() {
		this.cat.addProduit("Nuts", 1, 0);
		Assert.assertEquals("montant TTC sans stock",0,this.cat.getMontantTotalTTC(),0);
	}

	@Test
	public void testMontantTotalTTC_sansVirgule() {
		this.cat.addProduit("Mars", 100, 4);
		this.cat.addProduit("Raider", 20, 5);
		Assert.assertEquals("montant TTC sans virgule ",600,this.cat.getMontantTotalTTC(),0);
	}

	@Test
	public void testRemoveProduit_existe() {
		this.cat.addProduit("Mars", 10, 1);
		this.cat.addProduit("Treets", 10, 1);
		this.cat.addProduit("Raider", 12, 2);
		Assert.assertTrue("suppression produit existant", this.cat.removeProduit("Mars"));
	}

	@Test
	public void testRemoveProduit_existePas() {
		this.cat.addProduit("Mars", 10, 1);
		this.cat.addProduit("Treets", 10, 1);
		this.cat.addProduit("Raider", 12, 2);
		Assert.assertFalse("suppression produit qui n'existe pas", this.cat.removeProduit("Lion"));
	}

	@Test
	public void testRemoveProduit_null() {
		this.cat.addProduit("Mars", 10, 1);
		this.cat.addProduit("Treets", 10, 1);
		this.cat.addProduit("Raider", 12, 2);
		Assert.assertFalse("suppression avec un nom null", this.cat.removeProduit(null));
	}

	@Test
	public void testToString_CatalogueAvecDesProduits_AvecDesEspaceDansLesNomsDesProduit() {
		String resultatAttendu = "Mars - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 5" + "\n" +
				"Treets - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 4" + "\n" +
				"Kit Kat - prix HT : 1,00 € - prix TTC : 1,20 € - quantité en stock : 10" + "\n" +
				"\n" +
				"Montant total TTC du stock : 120,00 €";
		this.cat.addProduit("Mars ", 10, 5);
		this.cat.addProduit(" Treets", 10, 4);
		this.cat.addProduit("Kit Kat", 1, 10);
		Assert.assertEquals("toString avec des espaces dans les noms des produits", resultatAttendu, this.cat.toString());
	}

	@Test
	public void testToString_CatalogueAvecDesProduits_AvecDesTabulationsDansLesNomsDesProduit() {
		String resultatAttendu = "Mars - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 5" + "\n" +
				"Treets - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 4" + "\n" +
				"Kit Kat - prix HT : 1,00 € - prix TTC : 1,20 € - quantité en stock : 10" + "\n" +
				"\n" +
				"Montant total TTC du stock : 120,00 €";
		this.cat.addProduit("Mars	", 10, 5);
		this.cat.addProduit("	Treets", 10, 4);
		this.cat.addProduit("Kit	Kat", 1, 10);
		Assert.assertEquals("toString avec des tabulations dans les noms des produits", resultatAttendu, this.cat.toString());
	}

	@Test
	public void testToString_CatalogueAvecDesProduits_TotalAvecAucunChiffreApresVirgule() {
		String resultatAttendu = "Mars - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 5" + "\n" +
				"Treets - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 4" + "\n" +
				"Raider - prix HT : 1,00 € - prix TTC : 1,20 € - quantité en stock : 10" + "\n" +
				"\n" +
				"Montant total TTC du stock : 120,00 €";
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 4);
		this.cat.addProduit("Raider", 1, 10);
		Assert.assertEquals("toString catalogue sans virgule", resultatAttendu, this.cat.toString());
	}

	@Test
	public void testToString_CatalogueAvecDesProduits_TotalAvecDeuxChiffresApresVirgule() {
		String resultatAttendu = "Mars - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 5" + "\n" +
				"Treets - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 4" + "\n" +
				"Raider - prix HT : 1,00 € - prix TTC : 1,20 € - quantité en stock : 10" + "\n" +
				"Twix - prix HT : 10,40 € - prix TTC : 12,48 € - quantité en stock : 1" + "\n" +
				"\n" +
				"Montant total TTC du stock : 132,48 €";
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 4);
		this.cat.addProduit("Raider", 1, 10);
		this.cat.addProduit("Twix", 10.4, 1);
		Assert.assertEquals("toString catalogue avec un total de deux chiffres après virgule", resultatAttendu, this.cat.toString());
	}

	@Test
	public void testToString_CatalogueAvecDesProduits_TotalAvecTroisChiffresApresVirguleArrondiInferieur() {
		String resultatAttendu = "Mars - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 5" + "\n" +
				"Treets - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 4" + "\n" +
				"Raider - prix HT : 1,00 € - prix TTC : 1,20 € - quantité en stock : 10" + "\n" +
				"Twix - prix HT : 10,47 € - prix TTC : 12,56 € - quantité en stock : 1" + "\n" +
				"\n" +
				"Montant total TTC du stock : 132,56 €";
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 4);
		this.cat.addProduit("Raider", 1, 10);
		this.cat.addProduit("Twix", 10.47, 1);
		Assert.assertEquals("on affiche que deux chiffres après la virgule dans le prix unitaires TTC, mais le montant total TTC du catalogue est calculé avec les prix unitaires TTC non arrondis",resultatAttendu, this.cat.toString());
	}

	@Test
	public void testToString_CatalogueAvecDesProduits_TotalAvecTroisChiffresApresVirguleArrondiSuperieur() {
		String resultatAttendu = "Mars - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 5" + "\n" +
				"Treets - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 4" + "\n" +
				"Raider - prix HT : 1,00 € - prix TTC : 1,20 € - quantité en stock : 10" + "\n" +
				"Twix - prix HT : 10,47 € - prix TTC : 12,56 € - quantité en stock : 2" + "\n" +
				"\n" +
				"Montant total TTC du stock : 145,13 €";
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 4);
		this.cat.addProduit("Raider", 1, 10);
		this.cat.addProduit("Twix", 10.47, 2);
		Assert.assertEquals("on affiche que deux chiffres après la virgule dans le prix unitaires TTC, mais le montant total TTC du catalogue est calculé avec les prix unitaires TTC non arrondis",resultatAttendu, this.cat.toString());
	}

	@Test
	public void testToString_CatalogueAvecDesProduits_TotalAvecUnChiffreApresVirgule() {
		String resultatAttendu = "Mars - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 5" + "\n" +
				"Treets - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 4" + "\n" +
				"Raider - prix HT : 1,00 € - prix TTC : 1,20 € - quantité en stock : 10" + "\n" +
				"Twix - prix HT : 10,45 € - prix TTC : 12,54 € - quantité en stock : 5" + "\n" +
				"\n" +
				"Montant total TTC du stock : 182,70 €";
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 4);
		this.cat.addProduit("Raider", 1, 10);
		this.cat.addProduit("Twix", 10.45, 5);
		Assert.assertEquals("toString catalogue avec un total d'un chiffre après la virgule", resultatAttendu, this.cat.toString());
	}

	@Test
	public void testToString_CatalogueVide() {
		String resultatAttendu = "\n" +
				"Montant total TTC du stock : 0,00 €";
		Assert.assertEquals("toString catalogue vide", resultatAttendu, this.cat.toString());
	}

	@Test
	public void testVendreProduit_existe() {
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 0);
		this.cat.addProduit("Raider", 12, 3);
		Assert.assertTrue("vendre produit qui existe", this.cat.vendreStock("Raider", 1));
	}

	@Test
	public void testVendreProduit_existePas() {
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 0);
		this.cat.addProduit("Raider", 12, 3);
		Assert.assertFalse("vendre produit qui n'existe pas", this.cat.vendreStock("Nuts", 3));
	}

	@Test
	public void testVendreProduit_quantiteNegative() {
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 0);
		this.cat.addProduit("Raider", 12, 3);
		Assert.assertFalse("vendre quantité négative", this.cat.vendreStock("Mars", -4));
	}

	@Test
	public void testVendreProduit_quantiteNulle() {
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 0);
		this.cat.addProduit("Raider", 12, 3);
		Assert.assertFalse("vendre quantité nulle", this.cat.vendreStock("Treets", 0));
	}

	@Test
	public void testVendreProduit_stockInsuffisant() {
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 0);
		this.cat.addProduit("Raider", 12, 3);
		Assert.assertFalse("vendre produit stock insuffisant", this.cat.vendreStock("Raider", 10));
	}

	@Test
	public void testVendreProduit_stockNul() {
		this.cat.addProduit("Mars", 10, 5);
		this.cat.addProduit("Treets", 10, 0);
		this.cat.addProduit("Raider", 12, 3);
		Assert.assertFalse("vendre produit sans stock", this.cat.vendreStock("Treets", 4));
	}

	private I_Produit createProduit(String nom, double prixHT, int quantite) {
		try {
			return new Produit(nom, prixHT, quantite);
		}
		catch (Exception e) { return null; }
	}
}
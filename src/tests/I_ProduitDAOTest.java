package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import controller.ControleurCatalogue;
import dao.I_ProduitDAO;
import metier.Catalogue;
import metier.I_Catalogue;

/**
 * Cette classe a été fournie par le professeur et sert à vérifier que le programme
 * répond à ses spécifications. Elle a, par conséquent, été laissé telle quelle.
 */

public class I_ProduitDAOTest {


	private I_Catalogue catalogue;
	private I_ProduitDAO dao;
	private ControleurCatalogue controleur_catalogue;

	@Before
	public void setUp() throws Exception {
		this.catalogue = new Catalogue("test");
		this.dao = Mockito.mock(I_ProduitDAO.class);
		this.controleur_catalogue = new ControleurCatalogue(this.dao, this.catalogue);
		this.controleur_catalogue.setAfficherMsgErreur(false);
	}

	@After
	public void tearDown() throws Exception {
		// on remet à zero le catalogue
		this.catalogue = null;
		this.controleur_catalogue = null;
		this.controleur_catalogue = null;
	}

	@Test
	public void testAchatProduitNonExistant(){
		this.catalogue.addProduit("Mars", 3.85 , 45);
		Assert.assertFalse("acheter quantité positive", this.catalogue.acheterStock("Twix", 1));
	}

	@Test
	public void testAchatStockNegatif(){
		this.catalogue.addProduit("Mars", 3.85 , 45);
		Assert.assertFalse("acheter quantité negative", this.catalogue.acheterStock("Mars", -1));
	}


	@Test
	public void testAchatStockNul(){
		this.catalogue.addProduit("Mars", 3.85 , 45);
		Assert.assertFalse("acheter quantité nulle", this.catalogue.acheterStock("Mars", 0));
	}


	@Test
	public void testAchatStockPositif(){
		this.catalogue.addProduit("Mars", 3.85 , 45);
		Assert.assertTrue("acheter quantité positive", this.catalogue.acheterStock("Mars", 1));
	}

	@Test
	public void testAddProduitDAO_KO() {
		this.controleur_catalogue.addProduit("Mars", 2.35, 25);

		Assert.assertFalse("ajout produit qui n'existe pas",
				this.controleur_catalogue.addProduit("Mars", 2.35, 25));
	}

	@Test
	public void testAddQuantiteProduitDAO_KO() {
		Assert.assertFalse("achat quantite sur produit qui n'existe pas",
				this.controleur_catalogue.acheterStock("Twix", 19));
	}

	@Test
	public void testAffichageCatalogueControleur() {
		this.catalogue.addProduit("Oreo", 1.65 , 30);
		this.catalogue.addProduit("Mars", 3.85 , 45);
		Assert.assertEquals(this.catalogue.toString(),
				this.controleur_catalogue.getDescriptionCatalogue());
	}

	@Test
	public void testConstructeurControleurAchatVente() {
		Assert.assertNotNull("créer controleur achat vente", this.controleur_catalogue);
	}

	@Test
	public void testConstructeurControleurCatalogue() {
		Assert.assertNotNull("créer controleur catalogue", this.controleur_catalogue);
	}

	@Test
	public void testConstructeurStocks() {
		Assert.assertNotNull("créer controleur stocks", this.controleur_catalogue);
	}

	@Test
	public void testRemoveProduitDAO_KO() {
		Assert.assertFalse("supprime produit qui n'existe pas",
				this.controleur_catalogue.removeProduit("Mars"));
	}

	@Test
	public void testRemoveQuantiteSuperieurAuStockProduitDAO_KO() {
		this.controleur_catalogue.addProduit("Twix", 2.35, 15);

		Assert.assertFalse("vente quantite supérieur au stock sur produit qui existe",
				this.controleur_catalogue.vendreStock("Twix", 16));
	}
}
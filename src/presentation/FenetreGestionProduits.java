package presentation;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.ControleurCatalogue;

/**
 * Cette classe correspond à un objet de genre {@code view} de l'architecture MVC.
 * <P>
 * Elle affiche à l'utilisateur plusieurs boutons qui permettent de modifier le contenu
 * d'un catalogue, c'est elle qui reçoit en premier les demandes de création de produit,
 * suppression de produit, modification de stock et affichage des détails du catalogue.
 */

@SuppressWarnings("serial")
public class FenetreGestionProduits extends JFrame implements ActionListener {

	private JButton btAfficher;
	private JButton btNouveauProduit;
	private JButton btSupprimerProduit;
	private JButton btAchat;
	private JButton btVente;
	private JButton btQuitter;
	private ControleurCatalogue controleur_catalogue;

	public FenetreGestionProduits(ControleurCatalogue controleurCatalogue) {

		this.controleur_catalogue = controleurCatalogue;

		this.setTitle("Produits");
		this.setBounds(900, 500, 320, 200);

		JPanel panAffichage = new JPanel();
		JPanel panNouveauSupprimerProduit = new JPanel();
		JPanel panAchatVente = new JPanel();
		JPanel panQuitter = new JPanel();

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new FlowLayout());

		this.btAfficher = new JButton("Quantités en stock");
		this.btNouveauProduit = new JButton("Nouveau Produit");
		this.btSupprimerProduit = new JButton("Supprimer Produit");
		this.btAchat = new JButton("Achat Produits");
		this.btVente = new JButton("Vente Produits");
		this.btQuitter = new JButton("Quitter");

		panAffichage.add(this.btAfficher);
		panNouveauSupprimerProduit.add(this.btNouveauProduit);
		panNouveauSupprimerProduit.add(this.btSupprimerProduit);
		panAchatVente.add(this.btAchat);
		panAchatVente.add(this.btVente);
		panQuitter.add(this.btQuitter);

		contentPane.add(panAffichage);
		contentPane.add(panNouveauSupprimerProduit);
		contentPane.add(panAchatVente);
		contentPane.add(panQuitter);

		this.btAfficher.addActionListener(this);
		this.btNouveauProduit.addActionListener(this);
		this.btSupprimerProduit.addActionListener(this);
		this.btAchat.addActionListener(this);
		this.btVente.addActionListener(this);
		this.btQuitter.addActionListener(this);

		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btAfficher) {

			String stocksCatalogue = this.controleur_catalogue.getDescriptionCatalogue();
			String nomCatalogue = this.controleur_catalogue.getNomCatalogue();

			new FenetreDetailsCatalogue(stocksCatalogue, nomCatalogue);

		} else if (e.getSource() == this.btNouveauProduit)
			new FenetreNouveauProduit(this.controleur_catalogue);

		else if(e.getSource() == this.btSupprimerProduit)
			new FenetreSuppressionProduit(this.controleur_catalogue, this.controleur_catalogue.getNomProduits());

		else if (e.getSource() == this.btAchat)
			new FenetreAchat(this.controleur_catalogue, this.controleur_catalogue.getNomProduits());

		else if (e.getSource() == this.btVente)
			new FenetreVente(this.controleur_catalogue, this.controleur_catalogue.getNomProduits());

		else if (e.getSource() == this.btQuitter)
			this.dispose();
	}
}
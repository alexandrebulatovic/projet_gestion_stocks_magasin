package presentation;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.ControleurCatalogue;

/**
 * Cette classe est une fenêtre qui s'affiche à l'utilisateur lorsque
 * il demande la création d'un nouveau produit dans le catalogue.
 */

@SuppressWarnings("serial")
public class FenetreNouveauProduit extends JFrame implements ActionListener {

	private JTextField txtPrixHT;
	private JTextField txtNom;
	private JTextField txtQte;
	private ControleurCatalogue controleur_catalogue;

	public FenetreNouveauProduit(ControleurCatalogue controleurCatalogue) {
		this.controleur_catalogue = controleurCatalogue;
		this.setTitle("Creation de produit");
		this.setSize(250, 230);
		this.setLocationRelativeTo(null); // centre la fenêtre sur l'écran

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new FlowLayout());

		JButton btValider = new JButton("Valider");
		btValider.addActionListener(this);

		JLabel labNom = new JLabel("Nom produit");
		JLabel labPrixHT = new JLabel("Prix Hors Taxe");
		JLabel labQte = new JLabel("Quantité en stock");

		FieldsKeyAdapter stringKey = new FieldsKeyAdapter("STRING");
		this.txtNom = new JTextField(15);
		this.txtNom.addKeyListener(stringKey);

		FieldsKeyAdapter intKey = new FieldsKeyAdapter("INT");
		this.txtPrixHT = new JTextField(15);
		this.txtPrixHT.addKeyListener(intKey);

		this.txtQte = new JTextField(15);
		this.txtQte.addKeyListener(intKey);

		contentPane.add(labNom);
		contentPane.add(this.txtNom);
		contentPane.add(labPrixHT);
		contentPane.add(this.txtPrixHT);
		contentPane.add(labQte);
		contentPane.add(this.txtQte);
		contentPane.add(btValider);

		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String nomProduit = this.txtNom.getText();
		String textPrixHT = this.txtPrixHT.getText();
		String textStock = this.txtQte.getText();
		double prixHT;
		int stock;

		if (textStock.equals("") || textStock == null)
			stock = -1;
		else
			stock = Integer.parseInt(textStock);

		if (textPrixHT.equals("") || textPrixHT == null)
			prixHT = -1;
		else
			prixHT = Double.parseDouble(textPrixHT);

		if (this.controleur_catalogue.addProduit(nomProduit, prixHT, stock))
			this.dispose(); // ferme la fenêtre
	}
}
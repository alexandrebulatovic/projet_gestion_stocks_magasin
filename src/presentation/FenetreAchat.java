package presentation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.ControleurCatalogue;

/**
 * Cette classe qui représente une fenêtre s'affiche à l'utilisateur lorsque
 * il veut enregistrer un achat pour mettre à jour le stock d'un produit.
 */

@SuppressWarnings("serial")
public class FenetreAchat extends JFrame implements ActionListener {

	private JTextField txtQuantite;
	private JComboBox<String> combo;
	private ControleurCatalogue controleur_catalogue;

	public FenetreAchat(ControleurCatalogue controleurCatalogue, String[] produits) {
		this.controleur_catalogue = controleurCatalogue;
		this.setTitle("Achat");
		this.setSize(250, 125);
		this.setLocationRelativeTo(null); // centre la fenêtre sur l'écran

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new FlowLayout());

		JButton btAchat = new JButton("Achat");
		btAchat.addActionListener(this);

		FieldsKeyAdapter intKey = new FieldsKeyAdapter("INT");
		this.txtQuantite = new JTextField(5);
		this.txtQuantite.addKeyListener(intKey);
		this.txtQuantite.setText("0");

		this.combo = new JComboBox<>(produits);
		this.combo.setPreferredSize(new Dimension(100, 20));

		contentPane.add(new JLabel("Produit"));
		contentPane.add(this.combo);
		contentPane.add(new JLabel("Quantité achetée"));
		contentPane.add(this.txtQuantite);
		contentPane.add(btAchat);

		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String nomProduit;

		if (this.combo.getSelectedItem() == null) // s'il n'y a aucun produit dans le catalogue, on empêche toString (qui lève une exception sinon)
			nomProduit = null;
		else
			nomProduit = this.combo.getSelectedItem().toString();

		String textQuantite = this.txtQuantite.getText();
		int quantite;

		if (textQuantite.equals("") || textQuantite == null)
			quantite = -1;
		else
			quantite = Integer.parseInt(textQuantite);

		if (this.controleur_catalogue.acheterStock(nomProduit, quantite))
			this.dispose(); // ferme la fenêtre
	}
}
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

import controller.ControleurCatalogue;

/**
 * Cette classe est une fenêtre qui s'affiche à l'utilisateur lorsque
 * il demande la suppression d'un produit du catalogue.
 */

@SuppressWarnings("serial")
public class FenetreSuppressionProduit extends JFrame implements ActionListener {

	private JComboBox<String> combo;
	private ControleurCatalogue controleur_catalogue;

	public FenetreSuppressionProduit(ControleurCatalogue controleurCatalogue, String produits[]) {
		this.controleur_catalogue = controleurCatalogue;
		this.setTitle("Suppression de produit");
		this.setSize(310, 80);
		this.setLocationRelativeTo(null); // centre la fenêtre sur l'écran

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new FlowLayout());

		JButton btSupprimer = new JButton("Supprimer");
		btSupprimer.addActionListener(this);

		this.combo = new JComboBox<>(produits);
		this.combo.setPreferredSize(new Dimension(100, 20));

		contentPane.add(new JLabel("Produit"));
		contentPane.add(this.combo);
		contentPane.add(btSupprimer);

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

		if (this.controleur_catalogue.removeProduit(nomProduit))
			this.dispose(); // ferme la fenêtre
	}
}
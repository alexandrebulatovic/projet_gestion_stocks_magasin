package presentation;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Cette classe affiche une fenêtre à l'utilisateur lorsque il demande l'affichage des détails d'un catalogue.
 */

@SuppressWarnings("serial")
public class FenetreDetailsCatalogue extends JFrame implements ActionListener {

	public FenetreDetailsCatalogue(String description, String nomCatalogue) {

		this.setTitle("Affichage du catalogue : "+ nomCatalogue);
		this.setSize(450, 250);
		this.setLocationRelativeTo(null); // centre la fenêtre sur l'écran

		JPanel panHaut = new JPanel();
		JPanel panBas = new JPanel();

		panHaut.setLayout(new BorderLayout());
		panBas.setLayout(new FlowLayout());

		JTextArea jtaSortie = new JTextArea(description,10,5);
		jtaSortie.setFocusable(false);

		JButton btOK = new JButton("Quitter");
		btOK.addActionListener(this);

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		panHaut.add(jtaSortie);
		panBas.add(btOK);

		contentPane.add(panHaut,"North");
		contentPane.add(panBas, "South");

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {this.dispose();}
}
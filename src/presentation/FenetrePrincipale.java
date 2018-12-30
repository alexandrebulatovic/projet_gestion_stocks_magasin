package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import controller.ControleurMagasin;

/**
 * Cette classe correspond à un objet de genre {@code view} de l'architecture MVC.
 * <P>
 * Elle affiche à l'utilisateur la liste des catalogues avec le
 * nombre de produits différents par catalogue, et c'est également cette fenêtre qui reçoit en premier
 * les demandes pour créer un catalogue, supprimer un catalogue ou ouvrir un catalogue.
 */

@SuppressWarnings("serial")
public class FenetrePrincipale extends JFrame implements ActionListener, Observateur, WindowListener {

	private JButton btAjouter, btSupprimer, btSelectionner;
	private JTextField txtAjouter;
	private JLabel lbNbCatalogues;
	private JComboBox<String> cmbSupprimer, cmbSelectionner;
	private JTextArea taDetailCatalogues;

	private ControleurMagasin controleur_magasin;

	public FenetrePrincipale(ControleurMagasin controleurMagasin) {
		this.controleur_magasin = controleurMagasin;
		this.setTitle("Catalogues");
		this.setSize(200, 125);
		this.setLocationRelativeTo(null); // centre la fenêtre sur l'écran

		Container contentPane = this.getContentPane();
		JPanel panInfosCatalogues = new JPanel();
		JPanel panNbCatalogues = new JPanel();
		JPanel panDetailCatalogues = new JPanel();
		JPanel panGestionCatalogues = new JPanel();
		JPanel panAjouter = new JPanel();
		JPanel panSupprimer = new JPanel();
		JPanel panSelectionner = new JPanel();

		panNbCatalogues.setBackground(Color.white);
		panDetailCatalogues.setBackground(Color.white);
		panAjouter.setBackground(Color.gray);
		panSupprimer.setBackground(Color.lightGray);
		panSelectionner.setBackground(Color.gray);

		panNbCatalogues.add(new JLabel("Nous avons actuellement : "));
		this.lbNbCatalogues = new JLabel();
		panNbCatalogues.add(this.lbNbCatalogues);

		this.taDetailCatalogues = new JTextArea();
		this.taDetailCatalogues.setEditable(false);
		this.taDetailCatalogues.setFocusable(false);
		this.taDetailCatalogues.setBackground(new Color(240, 240, 240));
		JScrollPane jScrollPane = new JScrollPane(this.taDetailCatalogues);
		this.taDetailCatalogues.setPreferredSize(new Dimension(300, 100));
		panDetailCatalogues.add(jScrollPane);

		FieldsKeyAdapter stringKey = new FieldsKeyAdapter("STRING");

		panAjouter.add(new JLabel("Ajouter un catalogue : "));
		this.txtAjouter = new JTextField(10);
		this.txtAjouter.addKeyListener(stringKey);
		panAjouter.add(this.txtAjouter);
		this.btAjouter = new JButton("Ajouter");
		panAjouter.add(this.btAjouter);

		panSupprimer.add(new JLabel("Supprimer un catalogue : "));
		this.cmbSupprimer = new JComboBox<>();
		this.cmbSupprimer.setPreferredSize(new Dimension(100, 20));
		panSupprimer.add(this.cmbSupprimer);
		this.btSupprimer = new JButton("Supprimer");
		panSupprimer.add(this.btSupprimer);

		panSelectionner.add(new JLabel("Selectionner un catalogue : "));
		this.cmbSelectionner = new JComboBox<>();
		this.cmbSelectionner.setPreferredSize(new Dimension(100, 20));
		panSelectionner.add(this.cmbSelectionner);
		this.btSelectionner = new JButton("Selectionner");
		panSelectionner.add(this.btSelectionner);

		panGestionCatalogues.setLayout (new BorderLayout());
		panGestionCatalogues.add(panAjouter, "North");
		panGestionCatalogues.add(panSupprimer);
		panGestionCatalogues.add(panSelectionner, "South");

		panInfosCatalogues.setLayout(new BorderLayout());
		panInfosCatalogues.add(panNbCatalogues, "North");
		panInfosCatalogues.add(panDetailCatalogues, "South");

		contentPane.add(panInfosCatalogues, "North");
		contentPane.add(panGestionCatalogues, "South");
		this.pack();

		this.btAjouter.addActionListener(this);
		this.btSupprimer.addActionListener(this);
		this.btSelectionner.addActionListener(this);

		String[] listeCatalogues = this.controleur_magasin.getNomCatalogues();
		String[] detailsCatalogues = this.controleur_magasin.getDetailsCatalogue();

		this.afficherListesCatalogues(listeCatalogues);
		this.afficherDetailCatalogues(detailsCatalogues);
		this.afficherNbCatalogues(listeCatalogues.length);

		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.btAjouter){ // en cas d'ajout de nouveau catalogue

			String texteAjout = this.txtAjouter.getText();
			this.controleur_magasin.addCatalogue(texteAjout);
			this.txtAjouter.setText(null);

		} else if (e.getSource() == this.btSupprimer)	{ // en cas de suppression d'un catalogue

			String texteSupprime = (String)this.cmbSupprimer.getSelectedItem();
			this.controleur_magasin.removeCatalogue(texteSupprime);

		} else if (e.getSource() == this.btSelectionner){ // si on ouvre un catalogue

			String texteSelection = (String)this.cmbSelectionner.getSelectedItem();
			this.controleur_magasin.selectCatalogue(texteSelection);
		}
	}

	@Override
	public void mettreAJour() {
		String[] listeCatalogues = this.controleur_magasin.getNomCatalogues();

		this.afficherListesCatalogues(listeCatalogues);

		String[] detailsCatalogues = this.controleur_magasin.getDetailsCatalogue();

		this.afficherDetailCatalogues(detailsCatalogues);
		this.afficherNbCatalogues(listeCatalogues.length);
	}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {this.fermerApplication();}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}

	/**
	 * Modifie l'affichage des détails sur les catalogues.
	 * @param detailCatalogues le tableau de {@code String} contenant les noms des
	 * catalogues et le nombre de produits différents pour chaque catalogue.
	 */
	private void afficherDetailCatalogues(String[] detailCatalogues) {
		this.taDetailCatalogues.setText(""); // on enlève le texte présent

		for (int i = 0 ; i < detailCatalogues.length; i++)
			this.taDetailCatalogues.append(detailCatalogues[i] + "\n");
	}

	/**
	 * Modifie les liste déroulantes "Supprimer un catalogue" et "Sélectionner un catalogue"
	 * pour afficher les noms des catalogues.
	 * @param nomsCatalogues un tableau de {@code String} contenant les noms des catalogues.
	 */
	private void afficherListesCatalogues(String[] nomsCatalogues) {
		this.cmbSupprimer.removeAllItems();
		this.cmbSelectionner.removeAllItems();

		for (int i = 0 ; i < nomsCatalogues.length; i++) {
			this.cmbSupprimer.addItem(nomsCatalogues[i]);
			this.cmbSelectionner.addItem(nomsCatalogues[i]);
		}
	}

	/**
	 * Modifie l'étiquette du nombre de catalogues présents.
	 * @param nbCatalogues le nombre de catalogues présents.
	 */
	private void afficherNbCatalogues(int nbCatalogues){
		if (nbCatalogues <= 1)
			this.lbNbCatalogues.setText(nbCatalogues + " catalogue");
		else
			this.lbNbCatalogues.setText(nbCatalogues + " catalogues");
	}

	/**
	 * Ferme correctement les objets de la couche d'accès au données
	 * puis arrête la machine virtuelle JAVA.
	 */
	private void fermerApplication() {
		this.controleur_magasin.disconnect();
		System.exit(0);
	}
}
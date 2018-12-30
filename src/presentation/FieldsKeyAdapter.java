package presentation;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Cette classe est une implémentation partielle (via la classe abstraite {@code KeyAdapter}) de {@code KeyListener}.
 * Elle sert à recevoir les saisies au clavier en filtrant les caractères que l'on ne souhaite pas accepter.
 * <P>
 * Pour l'utiliser, il faut créer un objet en donnant le type de filtre à utiliser parmi {@code INT} ou {@code STRING}
 * et ensuite l'enregistrer avec un composant recevant des saisies clavier en utilisant la méthode {@code addKeyListener} du composant.
 * @see KeyListener
 */

public class FieldsKeyAdapter extends KeyAdapter{

	private String type;

	/**
	 * Crée un objet {@code FieldsKeyAdapter} avec le filtre donné en paramètre.
	 * @param typeFiltre le type de filtre à mettre en place parmi {@code INT} ou {@code STRING}.
	 */
	public FieldsKeyAdapter(String typeFiltre){this.type = typeFiltre;}

	@Override
	public void keyTyped(KeyEvent event) {

		if(this.type.equals("INT")){

			char c = event.getKeyChar();

			// si l'entrée clavier n'est pas 0-9 ou un point (séparation décimale) alors on ne l'interprète pas
			if (!((c >= '0' && c <= '9') || (c == '.')))
				event.consume();

		} else if (this.type.equals("STRING")) {

			char c = event.getKeyChar();

			// si l'entrée n'est pas A-Z, a-z, underscore, 0-9 alors alors on ne l'interprète pas
			if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c == '_') || (c >= '0' && c <= '9')))
				event.consume();
		}
	}
}
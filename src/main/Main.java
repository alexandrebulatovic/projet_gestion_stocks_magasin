package main;

import controller.ControleurMagasin;
import factory.MainFactory;

/**
 * Cette classe sert à lancer le programme. Il faut préciser le type de base de données
 * que l'on souhaite utilise parmi les constantes de la classe {@code MainFactory} lorsque
 * l'on crée le contrôleur principal, qui est un objet de type {@code ControleurMagasin}.
 */
public class Main {
	public static void main(String[] args) {
		new ControleurMagasin(MainFactory.TYPE_XML);
	}
}
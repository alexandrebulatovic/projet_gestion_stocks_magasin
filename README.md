# Projet Java de LP Info (Gestion des stocks d'un magasin)

Copyright © 2017 Alexandre BULATOVIC & Adrian MAURY

Le but de ce projet était de réaliser une application Java avec une architecture “en couches” (proche du MVC) qui permettrait facticement à un magasinier de gérer ses stocks de produits, tout en nous permettant d’expérimenter la persistance des données à l’aide de fichiers XML et avec la bibliothèque [JDOM](https://fr.wikipedia.org/wiki/JDOM), et plus classiquement à l’aide d’une base de données Oracle et son implémentation de l'API [JDBC](https://fr.wikipedia.org/wiki/Java_Database_Connectivity).

Ce projet a été développé avec une méthode proche du [Test-Driven Development (TDD)](https://fr.wikipedia.org/wiki/Test_driven_development). Le "squelette" du projet était fourni par le professeur avec des tests unitaires utilisant le framework [JUnit](https://fr.wikipedia.org/wiki/JUnit) et il fallait créer les tests unitaires manquants et implémenter les fonctionnalités nécessaires pour que l'application soit fonctionnelle, tout en utilisant les compétences acquises en cours de modélisation avec le langage UML, base de données et programmation orientée objet.

Le fichier [script_creation_BD.sql](https://raw.githubusercontent.com/alexandrebulatovic/projet_gestion_stocks_magasin/master/script_creation_BD.sql) contient le script SQL à lancer sur son SGBDR Oracle si c'est le système de persistance des données que l'on utilise (il faudra modifier le paramètre passé au contrôleur dans la [méthode main](https://github.com/alexandrebulatovic/projet_gestion_stocks_magasin/blob/master/src/main/Main.java) dans ce cas). 
L'application utilise des interfaces pour sa [couche d'accès aux données](https://raw.githubusercontent.com/alexandrebulatovic/projet_gestion_stocks_magasin/master/diagrammes-conception/controller-DAO-light.png) et permet donc au développeur d'implémenter d'autres systèmes de base de données lorsque s'il le souhaite.

Le fichier [resultats-tests-fonctionnels.txt](https://raw.githubusercontent.com/alexandrebulatovic/projet_gestion_stocks_magasin/master/resultats-tests-fonctionnels.txt) correspond à une liste de fonctionnalités du programme qui ont été testées, en plus des tests unitaires qui sont eux lancés automatiquement.

Les diagrammes de classes de conception fournis pour comprendre l'architecture (cliquez sur une image pour l'agrandir) :
* Couche d'accès aux données (DAO) :
<a href="https://raw.githubusercontent.com/alexandrebulatovic/projet_gestion_stocks_magasin/master/diagrammes-conception/controller-DAO-light.png"> 
	<img src="https://github.com/alexandrebulatovic/projet_gestion_stocks_magasin/blob/master/diagrammes-conception/controller-DAO-light.png" width="150">
</a>
<a href="https://raw.githubusercontent.com/alexandrebulatovic/projet_gestion_stocks_magasin/master/diagrammes-conception/controller-DAO-complet.png"> 
	<img src="https://github.com/alexandrebulatovic/projet_gestion_stocks_magasin/blob/master/diagrammes-conception/controller-DAO-complet.png" width="150">
</a>

* Couche "View" :
<a href="https://raw.githubusercontent.com/alexandrebulatovic/projet_gestion_stocks_magasin/master/diagrammes-conception/controller-view-light.PNG"> 
	<img src="https://github.com/alexandrebulatovic/projet_gestion_stocks_magasin/blob/master/diagrammes-conception/controller-view-light.PNG" width="150">
</a>
<a href="https://raw.githubusercontent.com/alexandrebulatovic/projet_gestion_stocks_magasin/master/diagrammes-conception/controller-view-complet.PNG"> 
	<img src="https://github.com/alexandrebulatovic/projet_gestion_stocks_magasin/blob/master/diagrammes-conception/controller-view-complet.PNG" width="150">
</a>

* Couche "Métier" :
<a href="https://raw.githubusercontent.com/alexandrebulatovic/projet_gestion_stocks_magasin/master/diagrammes-conception/controller-model-light.PNG"> 
	<img src="https://github.com/alexandrebulatovic/projet_gestion_stocks_magasin/blob/master/diagrammes-conception/controller-model-light.PNG" width="150">
</a>
<a href="https://raw.githubusercontent.com/alexandrebulatovic/projet_gestion_stocks_magasin/master/diagrammes-conception/controller-model-complet.PNG"> 
	<img src="https://github.com/alexandrebulatovic/projet_gestion_stocks_magasin/blob/master/diagrammes-conception/controller-model-complet.PNG" width="150">
</a>


## Utilisation

Le programme nécessite Java 7 (version disponible depuis automne 2011) pour fonctionner.

Pour pouvoir compiler les fichiers (et exécuter le .jar), il faut donc au minimum [JDK 7](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), sinon il faudra corriger les incompatibilités manuellement dans le code. Pour lancer l'application, la [méthode main](https://github.com/alexandrebulatovic/projet_gestion_stocks_magasin/blob/master/src/main/Main.java) se trouve dans la classe "Main.java" et le package "main". Le type de base de données utilisé par défaut est le [fichier XML](https://github.com/alexandrebulatovic/projet_gestion_stocks_magasin/blob/master/Catalogues.xml).

Ou sinon pour simplement exécuter le programme déjà compilé (fichier .jar), il faut au minimum [JRE 7](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) et il faut télécharger [l'exécutable .JAR](https://github.com/alexandrebulatovic/projet_gestion_stocks_magasin/blob/master/launch-gestion-magasin.jar) et mettre le [fichier XML qui sert de base de données](https://github.com/alexandrebulatovic/projet_gestion_stocks_magasin/blob/master/Catalogues.xml) dans le même dossier que l'exécutable.

*Il y a d'autres réalisations ici : https://alexandrebulatovic.github.io/*

DROP TABLE Produits;
DROP TABLE Catalogues;
DROP SEQUENCE id_catalogue_sequence;
DROP SEQUENCE id_produit_sequence;

CREATE TABLE Catalogues
(idCatalogue INT NOT NULL,
nomCatalogue VARCHAR(255) NOT NULL UNIQUE,
CONSTRAINT pk_id_catalogue PRIMARY KEY (idCatalogue));


CREATE TABLE Produits
(idProduit INT NOT NULL,
nomProduit VARCHAR(255) NOT NULL UNIQUE,
prixHT FLOAT NOT NULL,
quantite INT NOT NULL,
idCatalogue INT NOT NULL,
CONSTRAINT pk_produit PRIMARY KEY (idProduit),
CONSTRAINT fk_produit_catalogue FOREIGN KEY (idCatalogue) REFERENCES Catalogues(idCatalogue) ON DELETE CASCADE);


/* 
il y a une meilleure façon de créer un attribut qui s'incrémente automatiquement depuis la version Oracle 12c mais on garde ici cette version compatible avec Oracle 11g
*/
CREATE SEQUENCE id_catalogue_sequence;
CREATE SEQUENCE id_produit_sequence;

CREATE OR REPLACE TRIGGER auto_increment_id_catalogue
  BEFORE INSERT ON Catalogues
  FOR EACH ROW
BEGIN
  SELECT id_catalogue_sequence.NEXTVAL
  INTO :new.idCatalogue
  FROM dual;
END;

-- laisser ce slash entre les triggers pour qu'oracle accepte d'en créer plusieurs dans un même script
/

CREATE OR REPLACE TRIGGER auto_increment_id_produit
  BEFORE INSERT ON Produits
  FOR EACH ROW
BEGIN
  SELECT id_produit_sequence.NEXTVAL
  INTO :new.idProduit
  FROM dual;
END;
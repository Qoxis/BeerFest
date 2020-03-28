BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "beer_flavour" (
	"id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"beerId"	INTEGER NOT NULL,
	"flavourId"	INTEGER NOT NULL
);
CREATE TABLE IF NOT EXISTS "flavours" (
	"id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"name"	TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS "rating" (
	"id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"beerId"	INTEGER NOT NULL,
	"rateLove"	INTEGER NOT NULL,
	"rateBitter"	INTEGER NOT NULL,
	"rateFruitness"	INTEGER NOT NULL,
	"rateLight"	INTEGER NOT NULL
);
CREATE TABLE IF NOT EXISTS "events" (
	"id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"name"	TEXT NOT NULL,
	"place"	TEXT NOT NULL,
	"date"	TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS "brewery" (
	"id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"name"	TEXT NOT NULL,
	"country"	TEXT NOT NULL,
	"state"	TEXT NOT NULL,
	"city"	TEXT NOT NULL,
	"thumbnaill"	TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS "beer" (
	"id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"name"	TEXT NOT NULL,
	"type"	TEXT NOT NULL,
	"brewery"	INTEGER NOT NULL,
	"acool"	REAL NOT NULL,
	"IBU"	REAL NOT NULL,
	"description"	TEXT NOT NULL,
	"color"	TEXT NOT NULL,
	"imageUUID"	TEXT NOT NULL
);
INSERT INTO "beer_flavour" VALUES (1,1,11);
INSERT INTO "beer_flavour" VALUES (2,1,5);
INSERT INTO "beer_flavour" VALUES (3,1,6);
INSERT INTO "beer_flavour" VALUES (4,0,7);
INSERT INTO "beer_flavour" VALUES (5,2,11);
INSERT INTO "beer_flavour" VALUES (6,2,9);
INSERT INTO "beer_flavour" VALUES (7,2,8);
INSERT INTO "beer_flavour" VALUES (8,3,4);
INSERT INTO "beer_flavour" VALUES (9,4,1);
INSERT INTO "beer_flavour" VALUES (10,4,9);
INSERT INTO "beer_flavour" VALUES (11,5,2);
INSERT INTO "beer_flavour" VALUES (12,5,3);
INSERT INTO "beer_flavour" VALUES (13,7,9);
INSERT INTO "beer_flavour" VALUES (14,8,11);
INSERT INTO "beer_flavour" VALUES (15,8,8);
INSERT INTO "beer_flavour" VALUES (16,8,5);
INSERT INTO "beer_flavour" VALUES (17,10,9);
INSERT INTO "beer_flavour" VALUES (18,11,11);
INSERT INTO "beer_flavour" VALUES (19,11,7);
INSERT INTO "beer_flavour" VALUES (20,11,8);
INSERT INTO "beer_flavour" VALUES (21,12,10);
INSERT INTO "beer_flavour" VALUES (22,13,4);
INSERT INTO "beer_flavour" VALUES (23,13,8);
INSERT INTO "beer_flavour" VALUES (24,14,9);
INSERT INTO "beer_flavour" VALUES (25,15,4);
INSERT INTO "beer_flavour" VALUES (26,16,7);
INSERT INTO "beer_flavour" VALUES (27,17,4);
INSERT INTO "beer_flavour" VALUES (28,18,1);
INSERT INTO "beer_flavour" VALUES (29,19,11);
INSERT INTO "beer_flavour" VALUES (30,20,10);
INSERT INTO "beer_flavour" VALUES (31,21,5);
INSERT INTO "beer_flavour" VALUES (32,22,9);
INSERT INTO "beer_flavour" VALUES (33,23,9);
INSERT INTO "beer_flavour" VALUES (34,23,5);
INSERT INTO "beer_flavour" VALUES (35,23,8);
INSERT INTO "flavours" VALUES (1,'Miel');
INSERT INTO "flavours" VALUES (2,'Cacao');
INSERT INTO "flavours" VALUES (3,'Café');
INSERT INTO "flavours" VALUES (4,'Fruits exotiques');
INSERT INTO "flavours" VALUES (5,'Caramel');
INSERT INTO "flavours" VALUES (6,'Fruits secs');
INSERT INTO "flavours" VALUES (7,'Fleurie');
INSERT INTO "flavours" VALUES (8,'Résineuse');
INSERT INTO "flavours" VALUES (9,'Fruité');
INSERT INTO "flavours" VALUES (10,'Agrumes');
INSERT INTO "flavours" VALUES (11,'Malt');
INSERT INTO "events" VALUES (0,'Brewing lesson','A4','{"iChronology":{"iBase":{"iMinDaysInFirstWeek":4}},"iMillis":1585314274250}');
INSERT INTO "events" VALUES (1,'Brewing lesson','A4','{"iChronology":{"iBase":{"iMinDaysInFirstWeek":4}},"iMillis":1585313908703}');
INSERT INTO "brewery" VALUES (0,'Whitefrontier','Suisse','VS','Martigny','');
INSERT INTO "brewery" VALUES (1,'Bier Factory','Suisse','SG','Rapperswil','');
INSERT INTO "brewery" VALUES (3,'Dr. Brauwolf','Suisse','ZH','Zurich','');
INSERT INTO "brewery" VALUES (4,'7 Peaks','Suisse','VS','Morgins','');
INSERT INTO "beer" VALUES (0,'WANDERLUST','Swiss Pale Ale',1,4.8,2.0,'Pour le pick-nick ou la randonnée, elle te saura désaltérer. Délicatement houblonnée avec des notes de grain et de fruit et une touche fleurie. ','Ambrée clair','');
INSERT INTO "beer" VALUES (1,'STALLFUCHS','Red Ale',1,5.0,0.0,'Notes de malt, fruits secs et du caramel. Une bière ronde et souple avec une petite sucrosité du malt.','Ambrée','');
INSERT INTO "beer" VALUES (3,'OH','India Pale Ale',1,6.5,'3-4','Elle rappelle les origines de Gabe. Typée West Coast Style, maltée, fruitée et résineuse et dotée d''une amertume "comme au bon vieux temps". ','Ambrée clair','');
INSERT INTO "beer" VALUES (4,'EASY','India Pale Ale',1,3.75,'1-2','Une mini-IPA de nature rafraîchissante et légère. Finément épicée et impressions de fruits exotiques. ','Ambrée clair','');
INSERT INTO "beer" VALUES (5,'CORPORATE MONKEY','Lager',1,4.8,2.0,'Équilibrée et ronde aux notes de grain, miel et fruits du verger. ','Ambrée clair','');
INSERT INTO "beer" VALUES (6,'BLACKBIER','Dry Stout ',1,5.0,1.0,'Le parfait accompagnon pour les premières BBQs ou tes désserts au chocolat. C''est le malt qui parle, avec des notes de torréfaction et chocolat. En bouche petite touche fumée et sensations de café et de cacao.','Foncée','');
INSERT INTO "beer" VALUES (7,'LAGER','Lager',3,4.6,1.0,'Marquée par des notes de fruits et de grain. Gourmande et rafraîchissante. La bière profite, comme il se doit, d''un temps de repos pour une clarification naturelle et plus de saveurs. ','Jaune / dorée','');
INSERT INTO "beer" VALUES (8,'RED ALE','Red Ale',3,4.8,2.0,'Brassée selon la méthode nord-américaine, la recette contient une portion supplémentaire de houblon. Ronde et souple, marquée par le malt, des notes de caramel et une touche fruitée.','Ambrée','');
INSERT INTO "beer" VALUES (9,'RÖSTIGRABEN 2020','Kveik Saison',3,4.0,2.0,'La nouvelle bière de la brasserie, un clin d''oeil au Röstigraben ou dans ce cas, le  Polentagraben ;-) C''est une collab entre trois brasseries suisses et chacune a apporté un ingrédient spécial: Officina della Birra de la Farina Bona (maïs) du Tessin, Dr.Gab''s une souche de levure de type Kveik créée dans le canton de Vaud et Dr.Brauwolf du houblon zurichois. ','Jaune / dorée','');
INSERT INTO "beer" VALUES (10,'SUMMER ALE','Pale Ale ',3,3.8,1.0,'Le goût de l''été avant l''heure. :-) Légère et rafraîchissante, avec de subtiles notes de fruits. ','Jaune claire','');
INSERT INTO "beer" VALUES (11,'WINTER IPA','India Pale Ale',3,6.1,3.0,'De type West Coast, maltée, fruitée et résineuse avec une amertume plus soutenue. Le compagnon parfait pour ton burger maison!','Ambrée Claire','');
INSERT INTO "beer" VALUES (12,'WITBIER','Blanche',3,4.6,1.0,'Aromatique avec des notes d''agrumes. Désaltérante et ronde, le blé utilisé dans la recette lui confère un aspect légèrement trouble. ','Jaune foncée','');
INSERT INTO "beer" VALUES (13,'HOW SOON IS NOW?','Double IPA',0,8.2,3.0,'Basée à Newcastle, Wylam fait vibrer les amateurs depuis bientôt 20 ans. How soon is now - c’est du houblon ! Wow ! La signature de Wylam est clairement là. Entre résine et notes d’ananas séché, bien fluide avec l’amertume au rendez-vous.','Claire','');
INSERT INTO "beer" VALUES (14,'TO BE KIND','New England IPA',0,6.0,2.0,'Parfois, il faut juste être gentil. Oli, le brasseur magicien de chez Stigbergets est l’une de ces personnes inconditionnellement douces. Tout comme cette New England IPA, enrobée dans une amertume équilibrée, le houblon apporte un bon bol de fruits à noyaux, de melon mûr et de pêche. ','Jaune paille','');
INSERT INTO "beer" VALUES (15,'WHAMMY','New England IPA',0,7.8,2.0,'Fuerst Wiacek de Berlin fait partie du top des brasseries allemandes. Le résultat de la collab est une New England Double IPA aux notes de citrus et de noix de coco. Un concert de houblon, dans lequel les variétés Sorachi Ace Hop Hash, Idaho 7 Cryo, Simcoe, Mosaic et Chinook s''enchaînent. Enfonce la pédale à fond, et ressens la vibe de cette collab’ germano-suisse.','Jaune paille','');
INSERT INTO "beer" VALUES (16,'FWT SESSION IPA','Session IPA',0,3.5,1.0,'La Freeride World Tour Session IPA est celle qui te ramène sur terre après tes aventures. Rafraîchissante et croquante, on en redemande! ','Jaune paille','');
INSERT INTO "beer" VALUES (17,'MOROCCAN GOSE','Gose',0,5.0,0.0,'Gose est un vieux style de bière allemand, redécouvert par le mouvement des bières artisanales. Très rafraîchissante avec son côté acidulé et en finale une typique salinité. La version du jour est pimpée à la purée de mangue et dotée d''une acidité énergique et vive. Pour prolonger l''été, en apéritif ou avec des desserts aux fruits.','Orange','');
INSERT INTO "beer" VALUES (18,'DENT JAUNE
','Pale Ale',4,5.2,1.0,'Univers marqué par des notes de grain, quelques notes de foin et de miel, en bouche l’intensité des saveurs fait un crescendo et la bière montre plusieurs facettes, pas d’amertume.','Dorée','');
INSERT INTO "beer" VALUES (19,'ÉPERON','Americain Pale Ale',4,4.5,2.0,'Une version avec un caractère personnel sans abondance de houblon. Au nez, un profil plutôt belge qu''américain avec des notes de grain/céréales. Une mini-amertume qui monte en finale.','Dorée','');
INSERT INTO "beer" VALUES (20,'CATHÉDRALE','American Wheat',4,4.8,1.0,'Entre agrumes et notes épicées avec un gaz fin et élégant. Bien équilibrée avec une toute petite amertume qui se pointe en finale.','Claire','');
INSERT INTO "beer" VALUES (21,'LES DOIGTS','Extra Special Bitter',4,4.4,1.0,'Légères notes de torréfaction et de caramel brûlé, avec un peu d''oxygène l''aromatique devient plus maltée et fruitée. Typée Bitter à la manière anglaise, l''amertume se retrouve davantage dans le nom que dans le goût. Fluide et digeste en bouche pour finir sur de belles petites notes de torréfaction.','Brune','');
INSERT INTO "beer" VALUES (22,'FORTERESSE','Bières aux fruits',4,5.5,0.0,'Elle sent les framboises et myrtilles, les arômes sont agréables et naturels. Une bière fluide et rafraîchissante avec une toute légère acidité qui provient du fruit.','Rougeâtre','');
INSERT INTO "beer" VALUES (23,'CIME DE L’EST','West Coast IPA',4,7.0,3.0,'Le houblon est mûre et fruité au nez et en bouche, accompagné de notes de caramel. Si tu laisses respirer la bière dans le verre, des notes de résine se réveillent. Le gaz amène du peps, l''amertume typée pour une West Coast IPA est là, mais tout en restant accessible.','Ambrée','');
COMMIT;

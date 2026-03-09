-- Adminer 4.8.1 MySQL 5.5.5-10.5.29-MariaDB-0+deb11u1 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `idArticle` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `taille` varchar(10) DEFAULT NULL,
  `etat` varchar(50) DEFAULT NULL,
  `idCategorie` int(11) DEFAULT NULL,
  `idVente` int(11) DEFAULT NULL,
  `idDon` int(11) DEFAULT NULL,
  `idUtilisateur` int(11) DEFAULT NULL,
  `couleur` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `prix` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`idArticle`),
  KEY `idCategorie` (`idCategorie`),
  KEY `idVente` (`idVente`),
  KEY `idDon` (`idDon`),
  KEY `idUtilisateur` (`idUtilisateur`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`idCategorie`) REFERENCES `categorie` (`idCategorie`),
  CONSTRAINT `article_ibfk_2` FOREIGN KEY (`idVente`) REFERENCES `vente` (`idVente`),
  CONSTRAINT `article_ibfk_3` FOREIGN KEY (`idDon`) REFERENCES `don` (`idDon`),
  CONSTRAINT `article_ibfk_4` FOREIGN KEY (`idUtilisateur`) REFERENCES `utilisateur` (`idUtilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `article` (`idArticle`, `nom`, `description`, `taille`, `etat`, `idCategorie`, `idVente`, `idDon`, `idUtilisateur`, `couleur`, `url`, `prix`) VALUES
(1,	'nomArticle',	NULL,	'1',	NULL,	1,	1,	1,	3,	NULL,	NULL,	NULL),
(2,	'1',	NULL,	'1',	NULL,	1,	1,	2,	3,	NULL,	NULL,	NULL),
(3,	'',	NULL,	'',	NULL,	2,	1,	3,	3,	NULL,	NULL,	NULL),
(4,	'Pantalon court',	NULL,	'63',	NULL,	1,	1,	4,	3,	NULL,	NULL,	NULL),
(6,	'z',	'z',	'z',	NULL,	NULL,	1,	11,	3,	NULL,	NULL,	NULL),
(7,	'adddddddddddddd',	'addddddddddddd',	'addd',	'Etat correct',	1,	1,	14,	3,	NULL,	NULL,	NULL),
(8,	'aaaa',	'aa',	'aa',	'Bon état',	1,	1,	15,	3,	NULL,	NULL,	NULL),
(9,	'Ousmane',	'Manteau confortable ',	'XL',	'Etat correct',	NULL,	1,	16,	3,	NULL,	NULL,	NULL),
(14,	'Chemise',	'ds',	'XS',	'Etat correct',	2,	1,	35,	3,	'rouge',	NULL,	NULL),
(15,	'1',	'1',	'L',	'Bon état',	2,	1,	36,	3,	'rouge',	NULL,	NULL),
(19,	'',	'',	'L',	'Bon état',	1,	2,	40,	3,	'orange',	NULL,	NULL),
(20,	't shirt ',	'a',	'L',	'Comme neuf',	2,	1,	41,	3,	'bleu',	NULL,	NULL),
(21,	'',	'',	'XL',	'Bon état',	2,	1,	42,	3,	'bleu',	NULL,	NULL),
(22,	'',	'',	'XL',	'Comme neuf',	2,	1,	43,	3,	'bleu',	NULL,	NULL),
(23,	'dsd',	'',	'3XL',	'Bon état',	2,	1,	44,	3,	'bleu',	NULL,	NULL),
(24,	'short',	'short court en bon étatt',	'XS',	'Etat correct',	1,	2,	45,	3,	'bleu',	NULL,	NULL),
(25,	's',	's',	'L',	'Bon état',	1,	2,	46,	3,	'gris',	NULL,	NULL),
(26,	'aaa',	'aaa',	'4XL',	'Comme neuf',	1,	1,	47,	3,	'gris',	NULL,	NULL),
(27,	'fzefezf',	'ezfzef',	'L',	'Bon état',	3,	2,	49,	3,	'rose',	'',	0.00),
(28,	'584',	'',	'L',	'Bon état',	3,	2,	50,	3,	'rose',	'',	0.00),
(29,	'',	'',	'L',	'Bon état',	3,	2,	51,	3,	'rose',	'',	0.00),
(30,	'',	'',	'L',	'Bon état',	3,	2,	52,	3,	'rose',	'',	0.00),
(36,	'Pantalon rouge',	'Pantalon jaune',	'L',	'Comme neuf',	1,	6,	58,	3,	'jaune',	'',	0.00);

DROP TABLE IF EXISTS `categorie`;
CREATE TABLE `categorie` (
  `idCategorie` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  PRIMARY KEY (`idCategorie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `categorie` (`idCategorie`, `nom`, `description`) VALUES
(1,	'Haut',	'T-shirts, Chemises, Pulls'),
(2,	'Accessoire',	'Ceintures, chapeaux, écharpes'),
(3,	'Bas',	'Pantalons, Jupes, Shorts');

DROP TABLE IF EXISTS `don`;
CREATE TABLE `don` (
  `idDon` int(11) NOT NULL AUTO_INCREMENT,
  `nom_donneur` varchar(100) DEFAULT NULL,
  `email_donneur` varchar(100) DEFAULT NULL,
  `date_don` date DEFAULT NULL,
  PRIMARY KEY (`idDon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `don` (`idDon`, `nom_donneur`, `email_donneur`, `date_don`) VALUES
(1,	'D',	NULL,	NULL),
(2,	'D',	NULL,	NULL),
(3,	'D',	NULL,	NULL),
(4,	'D',	NULL,	NULL),
(10,	'D',	NULL,	NULL),
(11,	'D',	NULL,	NULL),
(14,	'D',	NULL,	NULL),
(15,	'D',	NULL,	NULL),
(16,	'D',	NULL,	NULL),
(29,	'D',	NULL,	NULL),
(30,	'D',	NULL,	NULL),
(31,	'D',	NULL,	NULL),
(34,	'D',	NULL,	NULL),
(35,	'D',	NULL,	NULL),
(36,	'D',	NULL,	NULL),
(37,	'D',	NULL,	NULL),
(38,	'D',	NULL,	NULL),
(39,	'D',	NULL,	NULL),
(40,	'D',	NULL,	NULL),
(41,	'D',	NULL,	NULL),
(42,	'D',	NULL,	NULL),
(43,	'D',	NULL,	NULL),
(44,	'D',	NULL,	NULL),
(45,	'D',	NULL,	NULL),
(46,	'D',	NULL,	NULL),
(47,	'D',	NULL,	NULL),
(48,	'D',	NULL,	NULL),
(49,	'fefefezfzef',	NULL,	'2025-12-19'),
(50,	'654654',	NULL,	'2025-12-19'),
(51,	'',	NULL,	'2025-12-19'),
(52,	'',	NULL,	'2025-12-19'),
(53,	'Jean Donneur',	NULL,	'2025-12-19'),
(54,	'Jean Donneur',	NULL,	'2025-12-19'),
(55,	'Jean Donneur',	NULL,	'2025-12-19'),
(56,	'Jean Donneur',	NULL,	'2025-12-19'),
(57,	'Jean Donneur',	NULL,	'2025-12-19'),
(58,	'Samet',	NULL,	'2025-12-19'),
(59,	'a',	NULL,	'2025-12-19');

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE `utilisateur` (
  `idUtilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `mdp` varchar(255) NOT NULL,
  `role` varchar(50) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `prenom` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idUtilisateur`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `utilisateur` (`idUtilisateur`, `login`, `mdp`, `role`, `nom`, `prenom`, `email`, `telephone`) VALUES
(1,	'maire',	'123',	'MAIRE',	'B',	'Ousmane',	'ousmane.b@ville.fr',	NULL),
(2,	'sec',	'123',	'SECRETAIRE',	'R',	'Samet',	'samet.r@ville.fr',	NULL),
(3,	'ben',	'123',	'BENEVOLE',	'A',	'Kumaran',	'kumaran.a@email.fr',	NULL),
(4,	'sas',	'sa',	'BENEVOLE',	'sas',	'sa',	'',	''),
(5,	'log_test',	'mdp_test',	'BENEVOLE',	'NomTest',	'PreTest',	'test@mail.com',	'0102030405'),
(10,	'fezf',	'fezf',	'BENEVOLE',	'zfz',	'ef',	'',	'');

DROP TABLE IF EXISTS `vente`;
CREATE TABLE `vente` (
  `idVente` int(11) NOT NULL AUTO_INCREMENT,
  `titre` varchar(100) NOT NULL,
  `date_vente` date DEFAULT NULL,
  `lieu` varchar(100) DEFAULT NULL,
  `statut` varchar(50) DEFAULT 'EN_PREPARATION',
  PRIMARY KEY (`idVente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `vente` (`idVente`, `titre`, `date_vente`, `lieu`, `statut`) VALUES
(1,	'Vente de Printemps',	'2025-04-12',	'Salle 1',	'EN_PREPARATION'),
(2,	'Vente de Printemps',	'2025-04-12',	'Salle 2',	'EN_PREPARATION'),
(4,	'Vente de Test',	'2025-12-25',	'Mairie',	'EN_PREPARATION'),
(5,	'Vente de Test',	'2025-12-25',	'Mairie',	'EN_PREPARATION'),
(6,	'Vente de Test',	'2025-12-25',	'Mairie',	'EN_PREPARATION'),
(7,	'Vente de Test',	'2025-12-25',	'Mairie',	'EN_PREPARATION'),
(9,	'fra',	'2006-11-11',	'paris',	'EN_PREPARATION');

-- 2026-03-09 09:59:34 c ma bd je crsi

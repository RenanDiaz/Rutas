# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.6.24)
# Database: rutas
# Generation Time: 2016-06-27 13:59:55 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table marcas
# ------------------------------------------------------------

DROP TABLE IF EXISTS `marcas`;

CREATE TABLE `marcas` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `marcas` WRITE;
/*!40000 ALTER TABLE `marcas` DISABLE KEYS */;

INSERT INTO `marcas` (`id`, `nombre`)
VALUES
	(0,'Honda');

/*!40000 ALTER TABLE `marcas` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table vehiculos
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vehiculos`;

CREATE TABLE `vehiculos` (
  `placa` varchar(6) NOT NULL DEFAULT '',
  `marca` smallint(6) unsigned DEFAULT NULL,
  `modelo` varchar(20) DEFAULT NULL,
  `anno` int(11) unsigned DEFAULT NULL,
  `tipo` smallint(6) unsigned DEFAULT NULL,
  PRIMARY KEY (`placa`),
  KEY `fk_id_marcas` (`marca`),
  CONSTRAINT `fk_id_marcas` FOREIGN KEY (`marca`) REFERENCES `marcas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `vehiculos` WRITE;
/*!40000 ALTER TABLE `vehiculos` DISABLE KEYS */;

INSERT INTO `vehiculos` (`placa`, `marca`, `modelo`, `anno`, `tipo`)
VALUES
	('123456',0,'City',2016,0);

/*!40000 ALTER TABLE `vehiculos` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table rutas
# ------------------------------------------------------------

DROP TABLE IF EXISTS `rutas`;

CREATE TABLE `rutas` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table ubicacion
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ubicacion`;

CREATE TABLE `ubicacion` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

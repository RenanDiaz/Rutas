# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.6.24)
# Database: rutas
# Generation Time: 2016-06-28 06:23:15 +0000
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
	(1,'Honda'),
	(2,'Nissan'),
	(3,'Toyota'),
	(4,'Hyundai');

/*!40000 ALTER TABLE `marcas` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table rutas
# ------------------------------------------------------------

DROP TABLE IF EXISTS `rutas`;

CREATE TABLE `rutas` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `rutas` WRITE;
/*!40000 ALTER TABLE `rutas` DISABLE KEYS */;

INSERT INTO `rutas` (`id`, `descripcion`)
VALUES
	(1,'San Carlitos - Mall Chiriqui');

/*!40000 ALTER TABLE `rutas` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ubicacion
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ubicacion`;

CREATE TABLE `ubicacion` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `fechahora` datetime DEFAULT NULL,
  `ruta` int(11) unsigned DEFAULT NULL,
  `vehiculo` varchar(6) DEFAULT NULL,
  `latitud` varchar(20) DEFAULT NULL,
  `longitud` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ruta` (`ruta`),
  KEY `fk_vehiculo` (`vehiculo`),
  CONSTRAINT `fk_ruta` FOREIGN KEY (`ruta`) REFERENCES `rutas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_vehiculo` FOREIGN KEY (`vehiculo`) REFERENCES `vehiculos` (`placa`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `ubicacion` WRITE;
/*!40000 ALTER TABLE `ubicacion` DISABLE KEYS */;

INSERT INTO `ubicacion` (`id`, `fechahora`, `ruta`, `vehiculo`, `latitud`, `longitud`)
VALUES
	(1,'2016-06-27 22:20:17',1,'123456','8.43206926379766','-82.4187118038335'),
	(2,'2016-06-27 23:57:04',1,'123456','8.43207640','-82.41858114'),
	(3,'2016-06-28 01:10:24',1,'837173','0','0');

/*!40000 ALTER TABLE `ubicacion` ENABLE KEYS */;
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
	('123456',1,'City',2016,0),
	('321123',2,'Versa',2014,0),
	('837173',4,'Elantra',2013,0),
	('RDR123',1,'Civic',2015,0),
	('RDR321',3,'Yaris',1999,0);

/*!40000 ALTER TABLE `vehiculos` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

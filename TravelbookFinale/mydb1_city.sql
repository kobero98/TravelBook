-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.1.1    Database: mydb1
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `NameC` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `State` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`NameC`,`State`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES ('Asunción','Paraguay'),('Aswan','Egypt'),('Aydın','Turkey'),('Bacău','Romania'),('Baden-Württemberg','Germany'),('Barcelona','Spain'),('California','United States'),('Capital','Venezuela'),('Capital RegionDenmark','Denmark'),('Connecticut','United States'),('Dublin','Ireland'),('England','United Kingdom'),('Florence','Italy'),('Florida','United States'),('Galway','Ireland'),('Giza','Egypt'),('Gujarat','India'),('Istanbul','Turkey'),('Jharkhand','India'),('Laguna','Philippines'),('Limburg','Belgium'),('Luxor','Egypt'),('Marne','France'),('Massachusetts','United States'),('México','Mexico'),('Naples','Italy'),('New York','United States'),('North Carolina','United States'),('North Holland','Netherlands'),('Nuevo León','Mexico'),('Pennsylvania','United States'),('Riyadh','Saudi Arabia'),('Rome','Italy'),('Scotland','United Kingdom'),('Seine-et-Marne','France'),('South Aegean','Greece'),('South Holland','Netherlands'),('Tokat','Turkey'),('Turin','Italy'),('unkown','France'),('unkown','Mexico'),('Val-d\'Oise','France'),('Venice','Italy');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-13 18:14:42

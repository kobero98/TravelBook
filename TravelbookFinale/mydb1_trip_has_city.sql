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
-- Table structure for table `trip_has_city`
--

DROP TABLE IF EXISTS `trip_has_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trip_has_city` (
  `CodiceViaggi` int NOT NULL,
  `CodiceCreatore` int NOT NULL,
  `City_NameC` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `City_State` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`CodiceViaggi`,`CodiceCreatore`,`City_NameC`,`City_State`),
  KEY `fk_Trip_has_City_City1_idx` (`City_NameC`,`City_State`),
  KEY `fk_Trip_has_City_Trip1_idx` (`CodiceViaggi`,`CodiceCreatore`),
  CONSTRAINT `fk_Trip_has_City_City1` FOREIGN KEY (`City_NameC`, `City_State`) REFERENCES `city` (`NameC`, `State`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Trip_has_City_Trip1` FOREIGN KEY (`CodiceViaggi`, `CodiceCreatore`) REFERENCES `trip` (`idTrip`, `CreatorTrip`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_has_city`
--

LOCK TABLES `trip_has_city` WRITE;
/*!40000 ALTER TABLE `trip_has_city` DISABLE KEYS */;
INSERT INTO `trip_has_city` VALUES (76,1,'Marne','France'),(76,1,'Seine-et-Marne','France'),(76,1,'unkown','France'),(76,1,'Val-d\'Oise','France'),(78,1,'Rome','Italy'),(83,1,'New York','United States'),(84,6,'North Holland','Netherlands'),(84,6,'South Holland','Netherlands'),(85,7,'Venice','Italy'),(86,8,'Capital RegionDenmark','Denmark'),(87,8,'England','United Kingdom'),(88,12,'Scotland','United Kingdom'),(89,12,'Florida','United States'),(90,11,'unkown','France'),(91,11,'Naples','Italy'),(92,13,'Rome','Italy'),(93,13,'Florence','Italy'),(97,53,'Aswan','Egypt'),(97,53,'Giza','Egypt'),(97,53,'Luxor','Egypt'),(98,53,'South Aegean','Greece'),(99,54,'Rome','Italy'),(100,55,'Naples','Italy'),(101,55,'Turin','Italy'),(102,56,'Barcelona','Spain'),(103,56,'Dublin','Ireland'),(103,56,'Galway','Ireland'),(104,1,'Laguna','Philippines');
/*!40000 ALTER TABLE `trip_has_city` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-13 18:14:44

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
-- Table structure for table `step`
--

DROP TABLE IF EXISTS `step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `step` (
  `Number` int NOT NULL,
  `GroupDay` int NOT NULL,
  `Place` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DescriptionStep` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'description step',
  `CodiceTrip` int NOT NULL,
  `CodiceCreatore` int NOT NULL,
  `NumberDay` int NOT NULL,
  `PrecisionInformation` varchar(500) COLLATE utf8_czech_ci DEFAULT 'informazioni piu precise',
  PRIMARY KEY (`Number`,`CodiceTrip`,`CodiceCreatore`),
  KEY `fk_Step_Trip1_idx` (`CodiceTrip`,`CodiceCreatore`),
  CONSTRAINT `fk_Step_Trip1` FOREIGN KEY (`CodiceTrip`, `CodiceCreatore`) REFERENCES `trip` (`idTrip`, `CreatorTrip`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `step`
--

LOCK TABLES `step` WRITE;
/*!40000 ALTER TABLE `step` DISABLE KEYS */;
INSERT INTO `step` VALUES (1,0,'Firenze, Florence, Italy','bella città',67,5,0,'informazioni piu precise'),(1,0,'Vienna, Austria','bel posto',68,7,0,'informazioni piu precise'),(1,0,'Colosseo, Piazza del Colosseo, Roma, Rome 00184, Italy','Posto Incantevole bellissimo vista da dio',69,6,0,'informazioni piu precise'),(1,0,'Roma, Rome, Italy',NULL,70,6,0,'informazioni piu precise'),(2,0,'Thailand','vicina e probabile',67,5,0,'informazioni piu precise'),(2,0,'Schönbrunner Panorama Bahn, Schonbrunner, Hietzing, Vienna 1130, Austria','divertente motlo bella la vista',68,7,0,'informazioni piu precise'),(2,0,'Foro della Pace, Via dei Fori Imperiali, Roma, Rome 00186, Italy','test',69,6,0,'informazioni piu precise'),(2,0,'Colosseo, Piazza del Colosseo, Roma, Rome 00184, Italy','colosseo	',70,6,0,'informazioni piu precise'),(3,1,'Cracow City Tours, Matejki 2, Kraków, Lesser Poland Voivodeship 31-157, Poland',NULL,68,7,0,'informazioni piu precise'),(3,1,'Firenze, Florence, Italy','citta bellissima da amare ',69,6,0,'informazioni piu precise'),(3,0,'Musei Capitolini, Piazza del Campidoglio 1, Roma, Rome 00186, Italy',NULL,70,6,0,'informazioni piu precise'),(4,1,'Giardino di Boboli, Via del Forte di San Giorgio, Firenze, Florence 50125, Italy','entrata costo 10 euro',69,6,0,'informazioni piu precise'),(4,0,'Pincio, Via Capo Le Case 50, Roma, Rome 00187, Italy','fdsagasg',70,6,0,'informazioni piu precise'),(5,1,'Musei Capitolini, Piazza del Campidoglio 1, Roma, Rome 00186, Italy','test',69,6,0,'informazioni piu precise'),(5,0,'Gianicolo, Roma, Rome 00165, Italy','rrefag',70,6,0,'informazioni piu precise');
/*!40000 ALTER TABLE `step` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-21 11:40:39

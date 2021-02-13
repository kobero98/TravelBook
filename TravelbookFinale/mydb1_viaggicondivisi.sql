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
-- Table structure for table `viaggicondivisi`
--

DROP TABLE IF EXISTS `viaggicondivisi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `viaggicondivisi` (
  `ChiCondivide` int NOT NULL,
  `AchiVieneCondiviso` int NOT NULL,
  `ViaggioCondiviso` int NOT NULL,
  `CreatoreViaggioCondiviso` int NOT NULL,
  PRIMARY KEY (`ChiCondivide`,`AchiVieneCondiviso`,`ViaggioCondiviso`,`CreatoreViaggioCondiviso`),
  KEY `fk_ViaggiCondivisi_user2_idx` (`AchiVieneCondiviso`),
  KEY `fk_ViaggiCondivisi_trip1_idx` (`ViaggioCondiviso`,`CreatoreViaggioCondiviso`),
  CONSTRAINT `fk_ViaggiCondivisi_trip1` FOREIGN KEY (`ViaggioCondiviso`, `CreatoreViaggioCondiviso`) REFERENCES `trip` (`idTrip`, `CreatorTrip`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ViaggiCondivisi_user1` FOREIGN KEY (`ChiCondivide`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ViaggiCondivisi_user2` FOREIGN KEY (`AchiVieneCondiviso`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viaggicondivisi`
--

LOCK TABLES `viaggicondivisi` WRITE;
/*!40000 ALTER TABLE `viaggicondivisi` DISABLE KEYS */;
/*!40000 ALTER TABLE `viaggicondivisi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-13 18:14:43

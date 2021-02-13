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
-- Table structure for table `messaggio`
--

DROP TABLE IF EXISTS `messaggio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messaggio` (
  `idmessaggio` int NOT NULL AUTO_INCREMENT,
  `Destinatario` int NOT NULL,
  `Mittente` int NOT NULL,
  `Testo` mediumtext COLLATE utf8_czech_ci,
  `data` timestamp NOT NULL,
  `NomeViaggio` varchar(30) COLLATE utf8_czech_ci DEFAULT NULL,
  `letto` int DEFAULT '0',
  PRIMARY KEY (`idmessaggio`,`Destinatario`,`Mittente`),
  UNIQUE KEY `idmessaggio_UNIQUE` (`idmessaggio`),
  UNIQUE KEY `data_UNIQUE` (`data`),
  KEY `fk_messaggio_Follow1_idx` (`Mittente`),
  KEY `fk_messaggio_Follow2_idx` (`Destinatario`),
  CONSTRAINT `fk_messaggio_Follow1` FOREIGN KEY (`Mittente`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_messaggio_Follow2` FOREIGN KEY (`Destinatario`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messaggio`
--

LOCK TABLES `messaggio` WRITE;
/*!40000 ALTER TABLE `messaggio` DISABLE KEYS */;
INSERT INTO `messaggio` VALUES (25,9,1,'ciao','2021-02-04 10:17:03',NULL,1),(26,9,1,'come stai?\r<BR>','2021-02-04 10:17:19',NULL,1),(27,9,1,'ciao\n','2021-02-04 10:38:07',NULL,1),(28,1,9,'come stai\n','2021-02-04 10:40:26',NULL,1),(29,9,1,'ei\n','2021-02-04 10:40:42',NULL,1),(30,1,9,'come stai?\n','2021-02-04 10:40:52',NULL,1),(31,9,1,'ciao','2021-02-04 13:13:57',NULL,1),(32,1,9,'ei\n','2021-02-04 13:15:56',NULL,1),(33,9,1,'come stai?\r<BR>','2021-02-04 13:16:08',NULL,1),(34,9,1,'io sono al settimo celo\r<BR>per tutta questa applicazione','2021-02-04 13:16:39',NULL,1),(63,8,1,'ciao daniel\r<BR>','2021-02-05 09:47:02',NULL,1),(129,1,12,'ciao\n','2021-02-06 20:52:36',NULL,1),(131,1,6,'ciao','2021-02-08 16:35:01',NULL,1),(133,1,6,'ciao','2021-02-08 16:36:20',NULL,1),(135,1,6,'ciao','2021-02-08 16:36:39',NULL,1),(137,1,6,'ciao','2021-02-08 16:40:07',NULL,1),(139,1,6,'ciao','2021-02-08 16:44:42',NULL,1),(141,1,6,'ciao','2021-02-08 17:51:40',NULL,1),(142,9,1,'ciao','2021-02-10 18:19:16',NULL,1),(144,1,6,'ciao','2021-02-10 21:17:42',NULL,1),(145,50,1,'ciao\n','2021-02-11 10:26:41',NULL,1),(146,50,1,'ciao\n','2021-02-11 10:30:00',NULL,1),(147,50,1,'ciao\n','2021-02-11 10:32:38',NULL,1),(148,50,1,'test\n','2021-02-11 10:43:38',NULL,1),(149,50,1,'ciao\n','2021-02-11 10:44:49',NULL,1),(150,50,1,'ciao\n','2021-02-11 10:46:13',NULL,1),(151,50,1,'ds\n','2021-02-11 10:47:09',NULL,1),(152,50,1,'ey\n','2021-02-11 10:49:29',NULL,1),(153,50,1,'ciao\n','2021-02-11 10:58:42',NULL,1),(154,50,1,'yo\n','2021-02-11 10:58:56',NULL,1),(155,50,1,'hg\n','2021-02-11 11:00:28',NULL,1),(156,50,1,'gfgfj\n','2021-02-11 11:00:56',NULL,1),(157,50,1,'duplichi?\n','2021-02-11 11:02:43',NULL,1),(158,1,50,'yo\r\nno non duplico','2021-02-11 11:04:08',NULL,1),(159,1,50,'ciao\n','2021-02-11 11:11:58',NULL,1),(160,1,50,'va be funziona\n','2021-02-11 11:12:09',NULL,1),(161,50,1,'ciao\n','2021-02-11 11:12:12',NULL,1),(162,50,1,'come stai?\n','2021-02-11 11:12:22',NULL,1),(163,1,50,'io bene te?\n','2021-02-11 11:12:25',NULL,1),(164,50,1,'ciao\n','2021-02-11 11:30:06',NULL,1),(165,50,1,'ciao\n','2021-02-11 11:48:12',NULL,1),(166,50,1,'ei\n','2021-02-11 11:51:37',NULL,1),(167,50,1,'yo\n','2021-02-11 11:52:51',NULL,1),(168,50,1,'ei sir\n','2021-02-11 11:54:25',NULL,1),(169,1,50,'ciao\r\n','2021-02-11 16:39:54',NULL,1),(170,1,50,'yo\n','2021-02-11 23:05:03',NULL,1),(171,50,1,'bela bro\n','2021-02-12 09:28:14',NULL,1),(172,50,1,'yo\n','2021-02-12 09:34:56',NULL,1),(173,50,1,'ciao\r\ncome stai?','2021-02-12 17:11:58',NULL,1),(174,50,1,'ciao\r\ncome stai?','2021-02-12 17:30:49',NULL,1),(175,50,1,'ciao\r\ncome stai?','2021-02-12 17:31:33',NULL,1),(176,50,1,'ciao\r\ncome stai?','2021-02-12 17:32:26',NULL,1),(177,50,1,'potrei','2021-02-12 17:37:07',NULL,1),(178,50,1,'potrei','2021-02-12 17:38:14',NULL,1),(179,50,1,'potrei','2021-02-12 17:39:23',NULL,1),(180,50,1,'re','2021-02-12 17:40:33',NULL,1),(181,50,1,'re','2021-02-12 17:41:02',NULL,1),(182,50,1,'re','2021-02-12 17:42:26',NULL,1),(183,50,1,'potrei','2021-02-12 17:42:49',NULL,1),(184,50,1,'potrei','2021-02-12 17:43:47',NULL,1),(185,12,1,'ciao come stai te?\n','2021-02-13 14:49:52',NULL,1),(186,6,1,'ciao anche a te\n','2021-02-13 14:49:58',NULL,1),(187,6,1,'non spammare\n','2021-02-13 14:50:03',NULL,1);
/*!40000 ALTER TABLE `messaggio` ENABLE KEYS */;
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

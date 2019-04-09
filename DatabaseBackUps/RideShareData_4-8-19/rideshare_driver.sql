-- MySQL dump 10.13  Distrib 5.5.62, for Win32 (AMD64)
--
-- Host: localhost    Database: rideshare
-- ------------------------------------------------------
-- Server version	5.5.62

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driver` (
  `driverID` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Every DRIVER will have an email upon registration.',
  `firstName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Every DRIVER will have a first name upon registering',
  `lastName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Every DRIVER will have a last name upon registering.',
  `password` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Every DRIVER will have a password that is hashed and salted upon storage.',
  PRIMARY KEY (`driverID`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES (1,'macchiaa8@students.rowan.edu','Anthony','Macchia','$2y$10$s05UeX/m7W3.N0LsicWipuNSoMmhozpx7bxYhddNiKUo2rEzS1/l6'),(2,'Jsmith@gmail.com','Jack','Smith','123'),(3,'Bjones@gmail.com','Bob','Jones','123'),(4,'Smiller@gmail.com','Suzie','Miller','123'),(5,'Rharris@gmail.com','Rachael','Harris','123'),(6,'Eperez@gmail.com','Emily','Perez','123'),(7,'Ewright@gmail.com','Emma','Wright','123'),(8,'Jdiaz@gmail.com','John','Diaz','123'),(9,'Jdoe@gmail.com','Doe','Jane','123'),(10,'Kloe@gmail.com','Loe','Kane','123'),(11,'Mnoe@gmail.com','Noe','Mane','123');
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-08 21:00:14

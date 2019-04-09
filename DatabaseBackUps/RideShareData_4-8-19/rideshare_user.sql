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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Every PASSENGER will have an email upon registration.',
  `firstName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Every PASSENGER will have a first name upon registration.',
  `lastName` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Every PASSENGER will have a last name upon registration.',
  `password` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Every PASSENGER will have a Hash and Salted password.',
  `creationDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `passenger_id_UNIQUE` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Driver1@gmail.com','Driver1','Driver1','password','2019-03-24 01:08:12'),(2,'Driver2@gmail.com','Driver2','Driver2','password','2019-03-24 01:08:12'),(3,'Driver3@gmail.com','Driver3','Driver3','password','2019-03-24 01:08:12'),(4,'Driver4@gmail.com','Driver4','Driver4','password','2019-03-24 01:08:12'),(5,'Driver5@gmail.com','Driver5','Driver5','password','2019-03-24 01:08:12'),(6,'Passenger1@gmail.com','Passenger1','Passenger1','password','2019-03-24 01:08:12'),(7,'Passenger10@gmail.com','Passenger10','Passenger10','password','2019-03-24 01:08:12'),(8,'Passenger2@gmail.com','Passenger2','Passenger2','password','2019-03-24 01:08:12'),(9,'Passenger3@gmail.com','Passenger3','Passenger3','password','2019-03-24 01:08:12'),(10,'Passenger4@gmail.com','Passenger4','Passenger4','password','2019-03-24 01:08:12'),(11,'Passenger5@gmail.com','Passenger5','Passenger5','password','2019-03-24 01:08:12'),(12,'Passenger6@gmail.com','Passenger6','Passenger6','password','2019-03-24 01:08:12'),(13,'Passenger7@gmail.com','Passenger7','Passenger7','password','2019-03-24 01:08:12'),(14,'Passenger8@gmail.com','Passenger6','Passenger6','password','2019-03-24 01:08:12'),(15,'Passenger9@gmail.com','Passenger9','Passenger9','password','2019-03-24 01:08:12');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-08 21:00:19

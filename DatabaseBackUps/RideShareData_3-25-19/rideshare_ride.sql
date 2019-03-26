-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: rideshare
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ride`
--

DROP TABLE IF EXISTS `ride`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ride` (
  `rideID` int(11) NOT NULL AUTO_INCREMENT COMMENT '(PK) Each RIDE will have a ride id automatically generated upon storage.',
  `userID` int(11) NOT NULL COMMENT '(FK) Each RIDE will be associated with PASSENGER.',
  `originID` int(11) NOT NULL COMMENT 'Each ride has a destination address which is associated with LOCATION.',
  `destinationID` int(11) NOT NULL,
  `rideDate` datetime DEFAULT NULL,
  `rideTimestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`rideID`),
  UNIQUE KEY `ride_id_UNIQUE` (`rideID`),
  KEY `passenger_id_idx` (`userID`),
  KEY `pickup_id_idx` (`originID`),
  KEY `dropoff_id_idx` (`destinationID`),
  CONSTRAINT `destinationID` FOREIGN KEY (`destinationID`) REFERENCES `location` (`locationID`),
  CONSTRAINT `orginID` FOREIGN KEY (`originID`) REFERENCES `location` (`locationID`),
  CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ride`
--

LOCK TABLES `ride` WRITE;
/*!40000 ALTER TABLE `ride` DISABLE KEYS */;
INSERT INTO `ride` VALUES (1,6,6,2,'2019-03-24 08:00:00','2019-03-24 00:51:05'),(2,7,1,3,'2019-03-24 08:00:00','2019-03-24 00:51:05'),(3,8,5,8,'2019-03-24 08:15:00','2019-03-24 00:51:05'),(4,9,9,7,'2019-03-24 08:15:00','2019-03-24 00:51:05'),(5,10,4,16,'2019-03-24 08:30:00','2019-03-24 00:51:05'),(6,11,11,10,'2019-03-24 08:30:00','2019-03-24 00:51:05'),(7,12,15,12,'2019-03-24 08:45:00','2019-03-24 00:51:05'),(8,13,13,14,'2019-03-24 08:45:00','2019-03-24 00:51:05'),(9,14,3,1,'2019-03-24 09:00:00','2019-03-24 00:51:05'),(10,15,14,4,'2019-03-24 09:00:00','2019-03-24 00:51:05');
/*!40000 ALTER TABLE `ride` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-25 18:57:57

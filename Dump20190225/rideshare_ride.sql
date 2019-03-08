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
  `ride_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '(PK) Each RIDE will have a ride id automatically generated upon storage.',
  `passenger_id` int(11) NOT NULL COMMENT '(FK) Each RIDE will be associated with PASSENGER.',
  `pickup_id` int(11) NOT NULL COMMENT 'Each ride has a destination address which is associated with LOCATION.',
  `dropoff_id` int(11) NOT NULL,
  `pick_up_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`ride_id`),
  UNIQUE KEY `ride_id_UNIQUE` (`ride_id`),
  KEY `passenger_id_idx` (`passenger_id`),
  KEY `pickup_id_idx` (`pickup_id`),
  KEY `dropoff_id_idx` (`dropoff_id`),
  CONSTRAINT `dropoff_id` FOREIGN KEY (`dropoff_id`) REFERENCES `location` (`location_id`),
  CONSTRAINT `passenger_id` FOREIGN KEY (`passenger_id`) REFERENCES `passenger` (`passenger_id`),
  CONSTRAINT `pickup_id` FOREIGN KEY (`pickup_id`) REFERENCES `location` (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ride`
--

LOCK TABLES `ride` WRITE;
/*!40000 ALTER TABLE `ride` DISABLE KEYS */;
INSERT INTO `ride` VALUES (1,6,6,2,NULL),(2,7,1,3,NULL),(3,8,5,8,NULL),(4,9,9,7,NULL),(5,10,4,16,NULL),(6,11,11,10,NULL),(7,12,15,12,NULL),(8,13,13,14,NULL),(9,14,3,1,NULL),(10,15,14,4,NULL);
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

-- Dump completed on 2019-02-25 18:51:13

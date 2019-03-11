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
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `location` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Each LOCATION will have a location id automatically generated upon storage.',
  `address` varchar(128) DEFAULT NULL COMMENT 'Address of the LOCATION.',
  `latitude` double DEFAULT NULL COMMENT 'Latitude of the LOCATION',
  `longitude` double DEFAULT NULL COMMENT 'Longitude of the LOCATION.',
  `street_address` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `zipcode` int(11) NOT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,NULL,32.37744722,86.30083333,'600 Dexter Ave','Montgomery','AL',36130),(2,NULL,34.74675833,92.28861111,'500 Woodlane St','Little Rock','AR',72201),(3,NULL,38.57657222,121.49333333,'1315 10th St','Sacramento','CA',95814),(4,NULL,41.76413611,72.68277778,'210 Capitol Ave','Hartford','CT',6106),(5,NULL,30.43811111,84.28166667,'400 S Monroe St','Tallahassee','FL',32399),(6,NULL,43.61769722,116.19972222,'700 W Jefferson St','Boise','ID',83702),(7,NULL,39.04800833,95.67805556,'SW 8th & SW Van Buren','Topeka','KS',66612),(8,NULL,30.45707222,91.1875,'900 North Third Street','Baton Rouge','LA',70802),(9,NULL,44.30723611,69.78166667,'210 State St','Augusta','ME',4330),(10,NULL,44.95514722,93.10222222,'75 Rev Dr Martin Luther King Jr Boulevard.','St Paul','MN',55155),(11,NULL,40.22043611,74.77,'125 W State St','Trenton','NJ',8608),(12,NULL,35.68228056,105.93972222,'490 Old Santa Fe Trail','Santa Fe','NM',87501),(13,NULL,35.78027778,78.63916667,'1 E Edenton St','Raleigh','NC',27601),(14,NULL,81.03305556,97.74055556,'1100 Congress Ave','Austin','TX',78701),(15,NULL,38.33638889,81.61222222,'1900 Kanawha Boulevard East','Charleston','WV',25305),(16,NULL,39.164075,118.76638889,'101 N Carson St','Carson City','NV',89701);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-25 18:51:14

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
  `locationID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Each LOCATION will have a location id automatically generated upon storage.',
  `latitude` double DEFAULT NULL COMMENT 'Latitude of the LOCATION',
  `longitude` double DEFAULT NULL COMMENT 'Longitude of the LOCATION.',
  `streetAddress` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `zipcode` int(11) NOT NULL,
  PRIMARY KEY (`locationID`),
  UNIQUE KEY `Coordinate_UQ` (`latitude`,`longitude`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,32.37744722,-86.30083333,'600 Dexter Ave','Montgomery','AL',36130),(2,34.74675833,-92.28861111,'500 Woodlane St','Little Rock','AR',72201),(3,38.57657222,-121.49333333,'1315 10th St','Sacramento','CA',95814),(4,41.76413611,-72.68277778,'210 Capitol Ave','Hartford','CT',6106),(5,30.43811111,-84.28166667,'400 S Monroe St','Tallahassee','FL',32399),(6,43.61769722,-116.19972222,'700 W Jefferson St','Boise','ID',83702),(7,39.04800833,-95.67805556,'SW 8th & SW Van Buren','Topeka','KS',66612),(8,30.45707222,-91.1875,'900 North Third Street','Baton Rouge','LA',70802),(9,44.30723611,-69.78166667,'210 State St','Augusta','ME',4330),(10,44.95514722,-93.10222222,'75 Rev Dr Martin Luther King Jr Boulevard.','St Paul','MN',55155),(11,40.22043611,-74.77,'125 W State St','Trenton','NJ',8608),(12,35.68228056,-105.93972222,'490 Old Santa Fe Trail','Santa Fe','NM',87501),(13,35.78027778,-78.63916667,'1 E Edenton St','Raleigh','NC',27601),(14,30.2747222,-97.74055556,'1100 Congress Ave','Austin','TX',78701),(15,38.33638889,-81.61222222,'1900 Kanawha Boulevard East','Charleston','WV',25305),(16,39.164075,-119.7662917,'101 N Carson St','Carson City','NV',89701),(18,33.4480972,-112.0970944,'1700 W Washington','Phoenix','AZ',85007),(19,39.7390944,-104.9848972,'200 E Colfax Ave','Denver','CO',80203),(20,33.7492722,-84.3882611,'206 Washington St SW','Atlanta','GA',30334),(21,39.7985167,-89.6548889,'401 S. 2nd St.','Springfield','IL',62707),(22,46.5857,-112.0184,'1301 E 6th Ave','Helena','MT',59601),(23,40.8080889,-96.6995861,'1445 K St','Lincoln','NE',68508),(24,42.6525528,-73.7573222,'State St. and, Washington Ave','Albany','NY',12224),(25,39.9613889,-82.9988889,'1 Capitol Square','Columbus','OH',43215),(26,44.9387306,-123.0300972,'900 Court St NE','Salem','OR',97301),(27,40.2644444,-76.8666667,'501 N 3rd St','Harrisburg','PA',17120),(28,40.7772222,-111.8880556,'350 State St','Salt Lake City','UT',84111);
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

-- Dump completed on 2019-03-25 18:57:58

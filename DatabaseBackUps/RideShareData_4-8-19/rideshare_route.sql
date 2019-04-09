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
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route` (
  `routeID` int(11) NOT NULL,
  `requestID` int(11) NOT NULL,
  `driverID` int(11) DEFAULT NULL,
  `locationID` int(11) NOT NULL,
  `orderInRoute` int(11) NOT NULL,
  PRIMARY KEY (`routeID`,`requestID`,`locationID`),
  KEY `FK_location_locationID_idx` (`locationID`),
  KEY `FK_request_requestID_idx` (`requestID`),
  KEY `FK_driver_driverID_idx` (`driverID`),
  CONSTRAINT `FK_driver_driverID` FOREIGN KEY (`driverID`) REFERENCES `driver` (`driverID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_location_locationID` FOREIGN KEY (`locationID`) REFERENCES `location` (`locationID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_request_requestID` FOREIGN KEY (`requestID`) REFERENCES `request` (`requestID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (1,15,2,56,9),(1,15,2,67,16),(1,19,2,43,19),(1,19,2,123,17),(1,20,2,65,5),(1,20,2,146,2),(1,23,2,62,1),(1,23,2,167,15),(1,24,2,38,6),(1,24,2,66,18),(1,33,2,56,10),(1,33,2,91,7),(1,34,2,134,8),(1,34,2,154,13),(1,35,2,129,3),(1,35,2,182,4),(1,38,2,159,20),(1,38,2,187,11),(1,39,2,145,12),(1,39,2,154,14),(2,18,3,72,3),(2,18,3,90,1),(2,21,3,47,9),(2,21,3,59,2),(2,28,3,56,8),(2,28,3,87,6),(2,29,3,162,12),(2,29,3,173,14),(2,31,3,84,10),(2,31,3,195,7),(2,36,3,36,13),(2,36,3,98,4),(2,40,3,101,5),(2,40,3,176,11),(3,16,4,35,1),(3,16,4,93,3),(3,22,4,156,5),(3,22,4,192,2),(3,32,4,53,4),(3,32,4,98,6),(4,17,5,37,3),(4,17,5,142,2),(4,25,5,90,1),(4,25,5,158,5),(4,37,5,54,6),(4,37,5,198,4),(5,26,6,84,1),(5,26,6,178,6),(5,27,6,210,4),(5,27,6,222,5),(5,30,6,85,3),(5,30,6,200,2);
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-08 21:00:12

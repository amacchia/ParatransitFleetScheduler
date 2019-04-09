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
-- Table structure for table `driverschedule`
--

DROP TABLE IF EXISTS `driverschedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driverschedule` (
  `driverID` int(11) NOT NULL,
  `dayOfWeek` enum('SUNDAY','MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY') COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'An enumerated type that can only consist of a day of the week. SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY.',
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  PRIMARY KEY (`driverID`,`dayOfWeek`),
  UNIQUE KEY `driverID_dayOfWeek_UQ` (`driverID`,`dayOfWeek`) COMMENT 'A Driver can only appear in the driverSchedule table once a day. ',
  KEY `driverID_driver_idx` (`driverID`),
  CONSTRAINT `driverID_driver` FOREIGN KEY (`driverID`) REFERENCES `driver` (`driverID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driverschedule`
--

LOCK TABLES `driverschedule` WRITE;
/*!40000 ALTER TABLE `driverschedule` DISABLE KEYS */;
INSERT INTO `driverschedule` VALUES (2,'SUNDAY','08:00:00','17:00:00'),(2,'MONDAY','08:00:00','17:00:00'),(2,'TUESDAY','08:00:00','17:00:00'),(2,'WEDNESDAY','08:00:00','17:00:00'),(2,'THURSDAY','08:00:00','17:00:00'),(2,'FRIDAY','08:00:00','17:00:00'),(3,'MONDAY','08:00:00','17:00:00'),(3,'TUESDAY','08:00:00','17:00:00'),(3,'FRIDAY','08:00:00','17:00:00'),(4,'MONDAY','08:00:00','17:00:00'),(4,'TUESDAY','08:00:00','17:00:00'),(4,'FRIDAY','08:00:00','17:00:00'),(5,'MONDAY','08:00:00','17:00:00'),(5,'TUESDAY','08:00:00','17:00:00'),(6,'MONDAY','08:00:00','17:00:00'),(6,'TUESDAY','08:00:00','17:00:00'),(6,'FRIDAY','08:00:00','17:00:00'),(6,'SATURDAY','08:00:00','17:00:00'),(7,'WEDNESDAY','08:00:00','17:00:00'),(7,'SATURDAY','08:00:00','17:00:00'),(8,'WEDNESDAY','08:00:00','17:00:00'),(8,'SATURDAY','08:00:00','17:00:00'),(9,'WEDNESDAY','08:00:00','17:00:00'),(9,'SATURDAY','08:00:00','17:00:00'),(10,'SUNDAY','08:00:00','17:00:00'),(10,'WEDNESDAY','08:00:00','17:00:00'),(10,'THURSDAY','08:00:00','17:00:00'),(11,'SUNDAY','08:00:00','17:00:00'),(11,'WEDNESDAY','08:00:00','17:00:00'),(11,'THURSDAY','08:00:00','17:00:00');
/*!40000 ALTER TABLE `driverschedule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-08 21:00:18

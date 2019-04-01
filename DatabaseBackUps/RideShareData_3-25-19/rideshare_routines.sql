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
-- Temporary view structure for view `ridedetails`
--

DROP TABLE IF EXISTS `ridedetails`;
/*!50001 DROP VIEW IF EXISTS `ridedetails`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8mb4;
/*!50001 CREATE VIEW `ridedetails` AS SELECT 
 1 AS `rideID`,
 1 AS `rideDate`,
 1 AS `originLatitude`,
 1 AS `originLongitude`,
 1 AS `originAddress`,
 1 AS `destinationLatitude`,
 1 AS `destinationLongitude`,
 1 AS `destinationAddress`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `ridedetails`
--

/*!50001 DROP VIEW IF EXISTS `ridedetails`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ridedetails` AS select `r`.`rideID` AS `rideID`,`r`.`rideDate` AS `rideDate`,`l`.`longitude` AS `originLatitude`,`l`.`latitude` AS `originLongitude`,concat(`l`.`streetAddress`,', ',`l`.`city`,', ',`l`.`state`,' ',`l`.`zipcode`) AS `originAddress`,`l2`.`longitude` AS `destinationLatitude`,`l2`.`latitude` AS `destinationLongitude`,concat(`l2`.`streetAddress`,', ',`l2`.`city`,', ',`l2`.`state`,' ',`l2`.`zipcode`) AS `destinationAddress` from ((`ride` `r` join `location` `l` on((`r`.`originID` = `l`.`locationID`))) join `location` `l2` on((`r`.`destinationID` = `l2`.`locationID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-25 18:57:59

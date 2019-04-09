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
-- Temporary table structure for view `requestdetails`
--

DROP TABLE IF EXISTS `requestdetails`;
/*!50001 DROP VIEW IF EXISTS `requestdetails`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `requestdetails` (
  `requestID` tinyint NOT NULL,
  `requestDate` tinyint NOT NULL,
  `originLatitude` tinyint NOT NULL,
  `originLongitude` tinyint NOT NULL,
  `originAddress` tinyint NOT NULL,
  `destinationLatitude` tinyint NOT NULL,
  `destinationLongitude` tinyint NOT NULL,
  `destinationAddress` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `morris_requestdetails`
--

DROP TABLE IF EXISTS `morris_requestdetails`;
/*!50001 DROP VIEW IF EXISTS `morris_requestdetails`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `morris_requestdetails` (
  `requestID` tinyint NOT NULL,
  `userID` tinyint NOT NULL,
  `originID` tinyint NOT NULL,
  `destinationID` tinyint NOT NULL,
  `requestDate` tinyint NOT NULL,
  `originLatitude` tinyint NOT NULL,
  `originLongitude` tinyint NOT NULL,
  `originAddress` tinyint NOT NULL,
  `destinationLatitude` tinyint NOT NULL,
  `destinationLongitude` tinyint NOT NULL,
  `destinationAddress` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `morris_location`
--

DROP TABLE IF EXISTS `morris_location`;
/*!50001 DROP VIEW IF EXISTS `morris_location`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `morris_location` (
  `locationID` tinyint NOT NULL,
  `latitude` tinyint NOT NULL,
  `longitude` tinyint NOT NULL,
  `streetAddress` tinyint NOT NULL,
  `city` tinyint NOT NULL,
  `state` tinyint NOT NULL,
  `zipcode` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `requestdetails`
--

/*!50001 DROP TABLE IF EXISTS `requestdetails`*/;
/*!50001 DROP VIEW IF EXISTS `requestdetails`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `requestdetails` AS select `r`.`requestID` AS `requestID`,`r`.`requestDate` AS `requestDate`,`l`.`longitude` AS `originLatitude`,`l`.`latitude` AS `originLongitude`,concat(`l`.`streetAddress`,', ',`l`.`city`,', ',`l`.`state`,' ',`l`.`zipcode`) AS `originAddress`,`l2`.`longitude` AS `destinationLatitude`,`l2`.`latitude` AS `destinationLongitude`,concat(`l2`.`streetAddress`,', ',`l2`.`city`,', ',`l2`.`state`,' ',`l2`.`zipcode`) AS `destinationAddress` from ((`request` `r` join `location` `l` on((`r`.`originID` = `l`.`locationID`))) join `location` `l2` on((`r`.`destinationID` = `l2`.`locationID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `morris_requestdetails`
--

/*!50001 DROP TABLE IF EXISTS `morris_requestdetails`*/;
/*!50001 DROP VIEW IF EXISTS `morris_requestdetails`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `morris_requestdetails` AS select `r`.`requestID` AS `requestID`,`r`.`userID` AS `userID`,`r`.`originID` AS `originID`,`r`.`destinationID` AS `destinationID`,`r`.`requestDate` AS `requestDate`,`l`.`latitude` AS `originLatitude`,`l`.`longitude` AS `originLongitude`,concat(`l`.`streetAddress`,', ',`l`.`city`,', ',`l`.`state`,' ',`l`.`zipcode`) AS `originAddress`,`l2`.`latitude` AS `destinationLatitude`,`l2`.`longitude` AS `destinationLongitude`,concat(`l2`.`streetAddress`,', ',`l2`.`city`,', ',`l2`.`state`,' ',`l2`.`zipcode`) AS `destinationAddress` from ((`request` `r` join `location` `l` on((`r`.`originID` = `l`.`locationID`))) join `location` `l2` on((`r`.`destinationID` = `l2`.`locationID`))) where ((`r`.`requestID` >= 15) and (`r`.`requestID` <= 40)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `morris_location`
--

/*!50001 DROP TABLE IF EXISTS `morris_location`*/;
/*!50001 DROP VIEW IF EXISTS `morris_location`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `morris_location` AS select `location`.`locationID` AS `locationID`,`location`.`latitude` AS `latitude`,`location`.`longitude` AS `longitude`,`location`.`streetAddress` AS `streetAddress`,`location`.`city` AS `city`,`location`.`state` AS `state`,`location`.`zipcode` AS `zipcode` from `location` where ((`location`.`locationID` >= 35) and (`location`.`locationID` <= 234)) */;
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

-- Dump completed on 2019-04-08 21:00:22

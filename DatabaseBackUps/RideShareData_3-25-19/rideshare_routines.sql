CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `ridedetails` AS
    SELECT 
        `r`.`rideID` AS `rideID`,
        `r`.`rideDate` AS `rideDate`,
        `l`.`locationID` AS `originID`,
        `l`.`longitude` AS `originLatitude`,
        `l`.`latitude` AS `originLongitude`,
        CONCAT(`l`.`streetAddress`,
                ', ',
                `l`.`city`,
                ', ',
                `l`.`state`,
                ' ',
                `l`.`zipcode`) AS `originAddress`,
		`l2`.`locationID` AS `destinationID`,
        `l2`.`longitude` AS `destinationLatitude`,
        `l2`.`latitude` AS `destinationLongitude`,
        CONCAT(`l2`.`streetAddress`,
                ', ',
                `l2`.`city`,
                ', ',
                `l2`.`state`,
                ' ',
                `l2`.`zipcode`) AS `destinationAddress`
    FROM
        ((`ride` `r`
        JOIN `location` `l` ON ((`r`.`originID` = `l`.`locationID`)))
        JOIN `location` `l2` ON ((`r`.`destinationID` = `l2`.`locationID`)))
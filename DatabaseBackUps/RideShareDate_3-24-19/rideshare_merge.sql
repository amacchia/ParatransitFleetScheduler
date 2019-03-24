CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `merge` AS
    SELECT 
        `r`.`rideID` AS `rideID`,
        `r`.`rideDate` AS `rideDate`,
        `l`.`longitude` AS `originLatitude`,
        `l`.`latitude` AS `originLongitude`,
        CONCAT(`l`.`streetAddress`,
                ', ',
                `l`.`city`,
                ', ',
                `l`.`state`,
                ' ',
                `l`.`zipcode`) AS `originAddress`,
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
    WHERE
        ((`r`.`rideDate` >= '2019-03-24 08:00:00')
            AND (`r`.`rideDate` <= '2019-03-25 08:45:00'))
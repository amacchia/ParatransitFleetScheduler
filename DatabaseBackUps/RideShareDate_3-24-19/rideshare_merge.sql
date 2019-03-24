CREATE VIEW `new_view` AS
SELECT  r.rideID, r.rideDate,
		l.longitude as 'originLatitude', l.latitude as 'originLongitude', CONCAT(l.streetAddress, ", ", l.city, ", ", l.state, " ", l.zipcode) as 'originAddress',
		l2.longitude as 'destinationLatitude', l2.latitude as 'destinationLongitude', CONCAT(l2.streetAddress, ", ", l2.city, ", ", l2.state, " ", l2.zipcode) as 'destinationAddress'
FROM rideshare.ride r
	JOIN rideshare.location l ON r.originID = l.locationID
    JOIN rideshare.location l2 ON r.destinationID = l2.locationID
WHERE r.rideDate >= '2019-03-24 08:00:00' AND r.rideDate <= '2019-03-24 08:45:00';
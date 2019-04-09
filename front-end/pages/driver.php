<?php
    create_connection();

    $currentRoute = "";
    $driverID = $_SESSION['currUserID'];
    //TODO: Only get dates in the future
    $sqlLocations = "SELECT 
                        streetAddress, city, latitude, longitude 
                    FROM 
                            route r 
                        JOIN 
                            location l on r.locationID = l.locationID
                    WHERE 
                        driverID = '$driverID'";
    $result = $GLOBALS['conn']->query($sqlLocations);
    if($result) {
        $waypointCounter = 1;
        $waypointString = "";
        while ($row = $result->fetch_assoc()) {
            $street = $row["streetAddress"];
            $city = $row['city'];
            $latitude = $row['latitude'];
            $longitude = $row['longitude'];

            $name = $street.", ".$city;
            $currentRoute .= "pos.".$latitude."_".$longitude."_".$name."~";

            $waypointString .= "wp.".$waypointCounter."=".$latitude.",".$longitude."&";
            $waypointCounter++;
        }
        $url = "https://bing.com/maps/default.aspx?rtp=".$currentRoute;
        $imgUrl = "https://dev.virtualearth.net/REST/v1/Imagery/Map/Road/Routes?".$waypointString."&key=Atln3dXDjP_wk0q4Ba52kPUzFGpwtp0TPTIGCfhn0MAR9vLxeohAemnWmigTyyk5";
    } else {
        $currentRoute = "None";
    }
?>

<p>Current Route:
    <a target="_blank" href="<?php echo $url; ?>">Click for directions</a>
</p>

<div>
<img src="<?php echo $imgUrl ?>" width="350" height="350">
</div>
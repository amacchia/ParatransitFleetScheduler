<?php
    create_connection();

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

        $numberOfBatches = ceil(mysqli_num_rows($result) / 25);
        $counter = 0;
        $routeUrls = array();
        $imgUrls = array();

        for ($i = 0; $i < $numberOfBatches; $i++) {
            $waypointCounter = 1;
            $waypointString = "";
            $currentRoute = "";
            for ($j = 0; $j < 25; $j++) {
                if ($row = $result->fetch_assoc()) {
                    $street = $row["streetAddress"];
                    $city = $row['city'];
                    $latitude = $row['latitude'];
                    $longitude = $row['longitude'];
                    $name = $street.", ".$city;
                    $currentRoute .= "pos.".$latitude."_".$longitude."_".$name."~";
                
                    $waypointString .= "wp.".$waypointCounter."=".$latitude.",".$longitude."&";
                    $waypointCounter++;  
                } else {
                    break;
                }      
            }

            $url = "https://bing.com/maps/default.aspx?rtp=".$currentRoute;
            $imgUrl = "https://dev.virtualearth.net/REST/v1/Imagery/Map/Road/Routes?".$waypointString."key=Atln3dXDjP_wk0q4Ba52kPUzFGpwtp0TPTIGCfhn0MAR9vLxeohAemnWmigTyyk5";
            $routeUrls[] = $url;
            $imgUrls[] = $imgUrl;
        }
    } else {
        $currentRoute = "None";
    }
?>

<div class="route-container">
<?php 
    for ($i = 0; $i < count($routeUrls); $i++) {
        $routeNum = $i + 1;
        echo '<div class="route_display">'.PHP_EOL;
        echo "<p>Route ". $routeNum .": <a target=\"_blank\" href=\"".$routeUrls[$i]."\">Click for directions</a></p>".PHP_EOL;

        echo '<div>'.PHP_EOL;
        echo "<img src=\"".$imgUrls[$i]."\" width=\"350\" height=\"350\">".PHP_EOL;
        echo '</div>'.PHP_EOL;
        echo '</div>'.PHP_EOL;
    }
?>
</div>
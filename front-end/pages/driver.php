<?php
    create_connection();
    $currDate = date("Y-m-d")."%";
    //$currDate = '2019-04-29%';
    $driverID = $_SESSION['currUserID'];
    
    $sqlLocations = 
    "SELECT 
        l.streetAddress, l.city, l.latitude, l.longitude
    FROM
        route r
            JOIN
        location l ON r.locationID = l.locationID
		    JOIN
        request req ON r.requestID = req.requestID
    WHERE
        driverID = '$driverID'
		    AND req.requestDate LIKE '$currDate'
    ORDER BY r.orderInRoute";

    $result = $GLOBALS['conn']->query($sqlLocations);
    $noRouteMsg = "";
    if($result) {
        $numberOfBatches = ceil(mysqli_num_rows($result) / 25);
        $counter = 0;
        $routeUrls = array();
        $imgUrls = array();
        
        if (mysqli_num_rows($result) < 1) {
            $noRouteMsg = 'You do not have any routes today!';
        }

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
        $noRouteMsg = "You do not have any routes today!";
    }
?>
<div class="container">
    <h3>Hello, <?php echo $_SESSION['fname']; ?></h3>
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

        if ($noRouteMsg != "") {
            echo $noRouteMsg;
        }
    ?>
    </div>
</div>

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

    // Update driver schedules
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $startTime = "08:00:00";
        $endTime = "17:00:00";
        $mon = $tue = $wed = $thr = $fri = $sat = $sun = FALSE;
        if (!empty($_POST["mon"])) {
            $mon = TRUE;
        }

        if (!empty($_POST["tue"])) {
            $tue = TRUE;
        }

        if (!empty($_POST["wed"])) {
            $wed = TRUE;
        }

        if (!empty($_POST["thr"])) {
            $thr = TRUE;
        }

        if (!empty($_POST["fri"])) {
            $fri = TRUE;
        }

        if (!empty($_POST["sat"])) {
            $sat = TRUE;
        }

        if (!empty($_POST["sun"])) {
            $sun = TRUE;
        }

        // Delete old schedule
        $deleteSQL = "DELETE FROM driverschedule WHERE driverID = '$driverID'";
        $deleteResult = $GLOBALS['conn']->query($deleteSQL);
        if (!$deleteResult) {
            echo 'Error removing old schedule';
        }

        $insertSQL = "";
        if ($mon) {
            $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                        VALUES ('$driverID', 'MONDAY', '$startTime', '$endTime')";
            $monResult = $GLOBALS['conn']->query($insertSQL);
        } else {
            $monResult = TRUE;
        }

        if ($tue) {
            $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                        VALUES ('$driverID', 'TUESDAY', '$startTime', '$endTime')";
            $tueResult = $GLOBALS['conn']->query($insertSQL);
        } else {
            $tueResult = TRUE;
        }

        if ($wed) {
            $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                        VALUES ('$driverID', 'WEDNESDAY', '$startTime', '$endTime')";
            $wedResult = $GLOBALS['conn']->query($insertSQL);
        } else {
            $wedResult = TRUE;
        }

        if ($thr) {
            $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                        VALUES ('$driverID', 'THURSDAY', '$startTime', '$endTime')";
            $thrResult = $GLOBALS['conn']->query($insertSQL);
        } else {
            $thrResult = TRUE;
        }

        if ($fri) {
            $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                        VALUES ('$driverID', 'FRIDAY', '$startTime', '$endTime')";
            $friResult = $GLOBALS['conn']->query($insertSQL);
        } else {
            $friResult = TRUE;
        }

        if ($sat) {
            $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                        VALUES ('$driverID', 'SATURDAY', '$startTime', '$endTime')";
            $satResult = $GLOBALS['conn']->query($insertSQL);
        } else {
            $satResult = TRUE;
        }

        if ($sun) {
            $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                        VALUES ('$driverID', 'SUNDAY', '$startTime', '$endTime')";
            $sunResult = $GLOBALS['conn']->query($insertSQL);
        } else {
            $sunResult = TRUE;
        }
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

    if ($noRouteMsg != "") {
        echo $noRouteMsg;
    }
?>
</div>

<div class="sch-form">
<h3> Driver Shcedule: </h3>
<form action="./index1.php?page=driver" method="post" id="driver-sch-form">

    <input type="checkbox" name="mon" id="mon" value="MONDAY">
        <label for="mon"> Monday </label>

    <input type="checkbox" name="tue" id="tue" value="TUESDAY">
        <label for="tue"> Tuesday </label>

    <input type="checkbox" name="wed" id="wed" value="WEDNESDAY">
        <label for="wed"> Wednesday </label>

    <input type="checkbox" name="thr" id="thr" value="THURSDAY">
        <label for="thr"> Thursday </label>

    <input type="checkbox" name="fri" id="fri" value="FRIDAY">
        <label for="fri"> Friday </label>

    <input type="checkbox" name="sat" id="sat" value="SATURDAY">
        <label for="sat"> Saturday </label>

    <input type="checkbox" name="sun" id="sun" value="SUNDAY">
        <label for="sunn"> Sunday </label>
    
    <input type="submit">
</form>
<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
   if ($monResult && $tueResult && $wedResult && $thrResult && $friResult && $satResult && $sunResult) {
        echo 'Your schedule has been updated!';
    } else {
        echo 'Error updating schedule.';
        echo $GLOBALS['conn']->error;
    } 
}
?>
</div>
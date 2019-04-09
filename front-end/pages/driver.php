<?php
    create_connection();

    $currentRoute = "";
    $driverID = $_SESSION['currUserID'];
    $sqlLocations = "SELECT 
                        streetAddress, city, state, zipcode 
                    FROM 
                            route r 
                        JOIN 
                            location l on r.locationID = l.locationID 
                    WHERE 
                        driverID = '$driverID'";
    $result = $GLOBALS['conn']->query($sqlLocations);
    if($result) {
        while ($row = $result->fetch_assoc()) {
            $street = $row["streetAddress"];
            $city = $row['city'];
            $state = $row['state'];
            $zip = $row['zipcode'];
            // TODO: add to array for easier manipulation when displaying rows
            $currentRoute .= "'$street', '$city', '$state', '$zip'";
        }
    } else {
        $currentRoute = "None";
    }
?>

<p>Current Route: 
    <?php
        echo $currentRoute;
    ?>
</p>

<button>Request New Route</button>
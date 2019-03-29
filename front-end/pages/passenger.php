<?php
        // define variables and set to empty values
        $originAddr = $originCity = $originZip = $originState =
        $destAddr = $destCity = $destZip = $destState =
        $date = $time = $errMsg = "";
        $conn = null;
    
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            if (empty($_POST["origin-addr"])) {
                $errMsg .= "Please enter an origin address\n";
            } else {
                $originAddr = clean_input($_POST["origin-addr"]);
            }

            if (empty($_POST["origin-city"])) {
                $errMsg .= "Please enter an origin city\n";
            } else {
                $originCity = clean_input($_POST["origin-city"]);
            }

            if (empty($_POST["origin-zip"])) {
                $errMsg .= "Please enter an origin zip code\n";
            } else {
                $originZip = clean_input($_POST["origin-zip"]);
            }

            if (empty($_POST["origin-state"])) {
                $errMsg .= "Please select an origin state\n";
            } else {
                $originState = clean_input($_POST["origin-state"]);
            }

            if (empty($_POST["destination-addr"])) {
                $errMsg .= "Please enter a destination address\n";
            } else {
                $destAddr = clean_input($_POST["destination-addr"]);
            }

            if (empty($_POST["destination-city"])) {
                $errMsg .= "Please enter a destination city\n";
            } else {
                $destCity = clean_input($_POST["destination-city"]);
            }

            if (empty($_POST["destination-zip"])) {
                $errMsg .= "Please enter a destination zip code\n";
            } else {
                $destZip = clean_input($_POST["destination-zip"]);
            }

            if (empty($_POST["destination-state"])) {
                $errMsg .= "Please select a destination state\n";
            } else {
                $destState = clean_input($_POST["destination-state"]);
            }

            if (empty($_POST["date"])) {
                $errMsg .= "Please enter a pickup date\n";
            } else {
                $date = clean_input($_POST["date"]);
            }

            if (empty($_POST["time"])) {
                $errMsg .= "Please enter a pickup time\n";
            } else {
                $time = clean_input($_POST["time"]);
            }

            if ($errMsg == "") {
                create_connection();
                $res = get_coordinates($originAddr, $originCity, $originZip, $originState);
                $res = json_decode($res);
                $originCoordinates = $res->resourceSets[0]->resources[0]->point->coordinates;
                $originLat = $originCoordinates[0];
                $originLong = $originCoordinates[1];

                $resDest = get_coordinates($destAddr, $destCity, $destZip, $destState);
                $resDest = json_decode($resDest);
                $destCoordinates = $resDest->resourceSets[0]->resources[0]->point->coordinates;
                $destLat = $destCoordinates[0];
                $destLong = $destCoordinates[1];
                
                $originLocationId = 
                    insert_location($originLat, $originLong, $originAddr, $originCity, $originState, $originZip);

                $destLocationId = 
                    insert_location($destLat, $destLong, $destAddr, $destCity, $destState, $destZip);

                $rideInserted = insert_ride(6, $originLocationId, $destLocationId);
                var_dump($rideInserted);
            } else {
                echo $errMsg;
            }
        }
            
        function create_connection()
        {
            // $servername = "localhost";
            // $password = "root";
            $servername = "http://ec2-3-81-8-187.compute-1.amazonaws.com/";
            $username = "root";
            $password = "senproj19";
            $dbname = "rideshare";
    
            // Create connection
            $GLOBALS['conn'] = new mysqli($servername, $username, $password, $dbname);
    
            // Check connection
            if ($GLOBALS['conn']->connect_error) {
                die("Connection failed: " . $GLOBALS['conn']->connect_error);
            }  
        }
    
        function clean_input($data) {
            $data = trim($data);
            $data = stripslashes($data);
            $data = htmlspecialchars($data);
            return $data;
        }

        /**
         * Get Coordinates from Bing API
         */
        function get_coordinates($addr, $city, $zip, $state) {
            $url = "https://dev.virtualearth.net/REST/v1/Locations?addressLine=".$addr."&countryregion=US&adminDistrict=".$state."&locality=".$city."&postalcode=".$zip."&maxRes=1&key=Atln3dXDjP_wk0q4Ba52kPUzFGpwtp0TPTIGCfhn0MAR9vLxeohAemnWmigTyyk5";
            $url = str_replace(' ', '%20', $url); // Remove spaces

            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, $url);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_SSL_VERIFYSTATUS, false);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

            $result = curl_exec($ch);

            if($result === false)
            {
                echo "Error Number:".curl_errno($ch)."<br>";
                echo "Error String:".curl_error($ch);
            }
            
            curl_close($ch);
            return $result;
        }

        /**
         * Inserts location into database if it does not exist
         * Returns the location id of the location
         */
        function insert_location($lat, $long, $addr, $city, $state, $zip) {
            $sql = "SELECT * FROM location WHERE latitude = '$lat' AND longitude = '$long' LIMIT 1";
            $result = $GLOBALS['conn']->query($sql);
            $locationId = -1;
            if ($result->num_rows > 0) {
                $locationId = (int) $result->fetch_row()[0];
            } else {
                $insertLoc = "INSERT INTO 
                                location (latitude, longitude, street_address, city, state, zipcode) 
                            VALUES
                                ('$lat', '$long', '$addr', '$city', '$state', '$zip')";
                $insertResult = $GLOBALS['conn']->query($insertLoc);
                if ($insertResult === true) {
                    $insertedRow = $GLOBALS['conn']->query($sql);
                    $locationId = (int) $insertedRow->fetch_row()[0];
                }
            }

            return $locationId;            
        }

        function insert_ride($userId, $originId, $destId) {
            $sql = "INSERT INTO ride (userID, originID, destinationID) 
                    VALUES ('$userId', '$originId', '$destId')";
            $result = $GLOBALS['conn']->query($sql);
            if ($result) {
                return true;
            } else {
                var_dump($GLOBALS['conn']->error);
                return false;
            }
        }
    ?>


<!-- Ride Request Form -->
<form action="passenger.php" method="post" id="passenger-request-form">
    Pick-up Address: <input type="text" name="origin-addr"> 
    Pick-up: City: <input type="text" name="origin-city">
    Pick-up: Zip: <input type="text" name="origin-zip">
    Pick-up: State: 
    <select name="origin-state" form="passenger-request-form">
        <option value="AL">AL</option>
        <option value="AK">AK</option>
        <option value="AR">AR</option>	
        <option value="AZ">AZ</option>
        <option value="CA">CA</option>
        <option value="CO">CO</option>
        <option value="CT">CT</option>
        <option value="DC">DC</option>
        <option value="DE">DE</option>
        <option value="FL">FL</option>
        <option value="GA">GA</option>
        <option value="HI">HI</option>
        <option value="IA">IA</option>	
        <option value="ID">ID</option>
        <option value="IL">IL</option>
        <option value="IN">IN</option>
        <option value="KS">KS</option>
        <option value="KY">KY</option>
        <option value="LA">LA</option>
        <option value="MA">MA</option>
        <option value="MD">MD</option>
        <option value="ME">ME</option>
        <option value="MI">MI</option>
        <option value="MN">MN</option>
        <option value="MO">MO</option>	
        <option value="MS">MS</option>
        <option value="MT">MT</option>
        <option value="NC">NC</option>	
        <option value="NE">NE</option>
        <option value="NH">NH</option>
        <option value="NJ" selected>NJ</option>
        <option value="NM">NM</option>			
        <option value="NV">NV</option>
        <option value="NY">NY</option>
        <option value="ND">ND</option>
        <option value="OH">OH</option>
        <option value="OK">OK</option>
        <option value="OR">OR</option>
        <option value="PA">PA</option>
        <option value="RI">RI</option>
        <option value="SC">SC</option>
        <option value="SD">SD</option>
        <option value="TN">TN</option>
        <option value="TX">TX</option>
        <option value="UT">UT</option>
        <option value="VT">VT</option>
        <option value="VA">VA</option>
        <option value="WA">WA</option>
        <option value="WI">WI</option>	
        <option value="WV">WV</option>
        <option value="WY">WY</option>
    </select> <br>

    Destination Address: <input type="text" name="destination-addr">
    Destination City: <input type="text" name="destination-city">
    Destination Zip: <input type="text" name="destination-zip">
    Destination State: 
    <select name="destination-state" form="passenger-request-form">
        <option value="AL">AL</option>
        <option value="AK">AK</option>
        <option value="AR">AR</option>	
        <option value="AZ">AZ</option>
        <option value="CA">CA</option>
        <option value="CO">CO</option>
        <option value="CT">CT</option>
        <option value="DC">DC</option>
        <option value="DE">DE</option>
        <option value="FL">FL</option>
        <option value="GA">GA</option>
        <option value="HI">HI</option>
        <option value="IA">IA</option>	
        <option value="ID">ID</option>
        <option value="IL">IL</option>
        <option value="IN">IN</option>
        <option value="KS">KS</option>
        <option value="KY">KY</option>
        <option value="LA">LA</option>
        <option value="MA">MA</option>
        <option value="MD">MD</option>
        <option value="ME">ME</option>
        <option value="MI">MI</option>
        <option value="MN">MN</option>
        <option value="MO">MO</option>	
        <option value="MS">MS</option>
        <option value="MT">MT</option>
        <option value="NC">NC</option>	
        <option value="NE">NE</option>
        <option value="NH">NH</option>
        <option value="NJ" selected>NJ</option>
        <option value="NM">NM</option>			
        <option value="NV">NV</option>
        <option value="NY">NY</option>
        <option value="ND">ND</option>
        <option value="OH">OH</option>
        <option value="OK">OK</option>
        <option value="OR">OR</option>
        <option value="PA">PA</option>
        <option value="RI">RI</option>
        <option value="SC">SC</option>
        <option value="SD">SD</option>
        <option value="TN">TN</option>
        <option value="TX">TX</option>
        <option value="UT">UT</option>
        <option value="VT">VT</option>
        <option value="VA">VA</option>
        <option value="WA">WA</option>
        <option value="WI">WI</option>	
        <option value="WV">WV</option>
        <option value="WY">WY</option>
    </select> <br>

    Date: <input type="date" name="date"><br>
    Time: <input type="time" name="time"><br>
    <input type="submit">
</form>
<?php
        // define variables and set to empty values
        $originAddr = $originCity = $originZip = $originState =
        $destAddr = $destCity = $destZip = $destState =
        $date = $time = $errMsg = "";
    
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

                $passengerId = $_SESSION['currUserID'];
                if ($originLocationId != -1 && $destLocationId != -1) {
                    $datetime = $date." ".$time;
                    $rideInserted = insert_ride($passengerId, $originLocationId, $destLocationId, $datetime);
                } else {
                    echo 'Error inserting request';
                }
            } else {
                echo $errMsg;
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
                                location (latitude, longitude, streetAddress, city, state, zipcode) 
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

        function insert_ride($userId, $originId, $destId, $date) {
            $sql = "INSERT INTO request (userID, originID, destinationID, requestDate) 
                    VALUES ('$userId', '$originId', '$destId', '$date')";
            $result = $GLOBALS['conn']->query($sql);
            if ($result) {
                return true;
            } else {
                var_dump($GLOBALS['conn']->error);
                return false;
            }
        }
    ?>


<div class="container">
    <h2> Ride Request Form </h2>
    <div id="request-form">
        <!-- Ride Request Form -->
        <form action="./index1.php?page=passenger" method="post" id="passenger-request-form">
            <div class="row, form-row">
                <div class="col">
                    Pick-up Address: <input type="text" name="origin-addr">
                </div>
                <div class="col">
                    Pick-up: City: <input type="text" name="origin-city">
                </div>
                <div class="col">
                    Pick-up: Zip: <input type="text" name="origin-zip">
                </div>
                <div class="col">
                    <br>
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
                    </select>
                </div>
            </div>

            <div class="row, form-row">
                <div class="col">
                    Destination Address: <input type="text" name="destination-addr">
                </div>
                <div class="col">
                    Destination City: <input type="text" name="destination-city">
                </div>
                <div class="col">
                    Destination Zip: <input type="text" name="destination-zip">
                </div>
                <div class="col">
                    <br>
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
                    </select>
                </div>
            </div>

            <div class="row justify-content-start, form-row">
                <div class="col-2">
                    Date: <input type="date" name="date">
                </div>

                <div class="col-2">
                    Time: <select name="time" form="passenger-request-form">
                        <option value="09:00:00">9:00am - 10:00am</option>
                        <option value="10:00:00">10:00am - 11:00am</option>
                        <option value="11:00:00">11:00am - 12:00pm</option>	
                        <option value="12:00:00">12:00pm - 1:00pm</option>
                        <option value="13:00:00">1:00pm - 2:00pm</option>
                        <option value="14:00:00">2:00pm - 3:00pm</option>
                        <option value="15:00:00">3:00pm - 4:00pm</option>
                        <option value="16:00:00">4:00pm - 5:00pm</option>
                    </select>
                </div>
            </div>

            <div class="row">
                <div class="col">
                    <input type="submit">
                </div>
            </div>

        </form>
    </div>

    <div class="success-msg">
        <?php
            if ($rideInserted){
                echo 'Your ride has been scheduled!';
            }
        ?>
    </div>

</div>
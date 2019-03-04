<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Paratransit Fleet Scheduler</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link type="text/css" rel="stylesheet" href="../css/main.css" />
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <script src="../scripts/index.js"></script>
</head>

<body>
    <?php
        // define variables and set to empty values
        $fname = $lname = $email = $pword = $service = $errMsg = "";
        $conn = null;
    
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            if (empty($_POST["fname"])) {
                $errMsg = "Please enter a first name";
            }

            if (empty($_POST["lname"])) {
                $errMsg = "Please enter a last name";
            }

            if (empty($_POST["email"])) {
                $errMsg = "Please enter an email";
            }

            if (empty($_POST["pword"])) {
                $errMsg = "Please enter a password";
            }

            if (empty($_POST["service"])) {
                $errMsg = "Please select a service";
            }

            $fname = test_input($_POST["fname"]);
            $lname = test_input($_POST["lname"]);
            $email = test_input($_POST["email"]);
            $pword = test_input($_POST["pword"]);
            $service = test_input($_POST["service"]);
            
            if ($errMsg == "") {
                create_connection();

                if ($service === "driver") {
                    insert_driver($fname, $lname, $email, $pword, $service);
                } else {
                    insert_passenger($fname, $lname, $email, $pword, $service);
                }
            }
        }
        
        function insert_driver($first_name, $last_name, $email, $pword, $service) {
            echo "Driver";
    
            $hash = password_hash($pword, PASSWORD_DEFAULT);
    
            $sql = "INSERT INTO 
                        Drivers (first_name, last_name, email, pword) 
                    VALUES
                        ('$first_name', '$last_name', '$email', '$hash')";
    
            if ($GLOBALS['conn']->query($sql) === TRUE) {
                echo "New record created successfully";
            } else {
                echo "Error: " . $sql . "<br>" . $GLOBALS['conn']->error;
            }
    
            $GLOBALS['conn']->close();
        }
    
        function insert_passenger($first_name, $last_name, $email, $pword, $service) {
            echo "Passenger";
    
            $hash = password_hash($pword, PASSWORD_DEFAULT);
    
            $sql = "INSERT INTO 
                        Passengers (first_name, last_name, email, pword) 
                    VALUES
                        ('$first_name', '$last_name', '$email', '$hash')";
    
            if ($GLOBALS['conn']->query($sql) === TRUE) {
                echo "New record created successfully";
            } else {
                echo "Error: " . $sql . "<br>" . $GLOBALS['conn']->error;
            }
    
            $GLOBALS['conn']->close();
        }
    
        function create_connection()
        {
            $servername = "localhost";
            $username = "root";
            $password = "";
            $dbname = "rideshare";
    
            // Create connection
            $GLOBALS['conn'] = new mysqli($servername, $username, $password, $dbname);
    
            // Check connection
            if ($GLOBALS['conn']->connect_error) {
                die("Connection failed: " . $GLOBALS['conn']->connect_error);
            } 
            echo "Connected successfully";    
        }
    
        function test_input($data) {
            $data = trim($data);
            $data = stripslashes($data);
            $data = htmlspecialchars($data);
            return $data;
        }
    ?>


    <h1 id="sign-up-form-title">Sign Up</h1>
    <div id="sign-up-form">
        <form method="POST" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
            <label for="fname" class="input-label">First Name</label>
            <input type="text" name="fname" class="form-input"><br>

            <label for="lname" class="input-label">Last Name</label>
            <input type="text" name="lname" class="form-input"><br>

            <label for="email" class="input-label">Email</label>
            <input type="email" name="email" class="form-input"><br>

            <label for="pword" class="input-label">Password</label>
            <input type="password" name="pword" class="form-input"><br>

            <label for="service" class="input-label">Service</label>
            <div class="rd-input">
                <input type="radio" name="service" value="driver">Driver
                <input type="radio" name="service" value="passenger">Passenger <br>
            </div>

            <input type="submit" value="Sign Up" id="sub-btn">
            <p>Already a user? <a href="./login.html">Login</a></p>
        </form>
    </div>

    <div class="errorMsg">
        <?php 
            if ($errMsg != "") {
                echo 'Error: '. $errMsg;
            }
        ?>
    </div>

</body>

</html>
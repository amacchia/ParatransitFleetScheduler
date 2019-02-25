<?php
    // define variables and set to empty values
    $fname = $lname = $email = $pword = $service = "";
    $conn = null;

    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $fname = test_input($_POST["fname"]);
        $lname = test_input($_POST["lname"]);
        $email = test_input($_POST["email"]);
        $pword = test_input($_POST["pword"]);
        $service = test_input($_POST["service"]);

        echo "$fname  $lname <br>";
        echo "$service";
        create_connection();

        if ($service === "driver") {
            insert_driver($fname, $lname, $email, $pword, $service);
        } else {
            insert_passenger($fname, $lname, $email, $pword, $service);
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
        $dbname = "sp";

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
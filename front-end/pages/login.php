<?php
    // define variables and set to empty values
    $email = $pword = $service = "";
    $conn = null;

    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $email = test_input($_POST["email"]);
        $pword = test_input($_POST["pword"]);
        $service = test_input($_POST["service"]);

        create_connection();

        $userExists;
        if ($service === "driver") {
            $userExists = loginDriver($email, $pword);
        } else {
            $userExists = loginPassenger($email, $pword);
        }

        echo '<br>';
        if ($userExists) {
            echo 'User exists';
        } else {
            echo 'User Not Found';
        }
        $GLOBALS['conn']->close(); 
    }

    function loginDriver($email, $pword)
    {
        $sql = "SELECT pword FROM Drivers WHERE email = '$email'";
        $result = $GLOBALS['conn']->query($sql);
        
        $hash = mysqli_fetch_row($result)[0];
        return password_verify($pword, $hash);
    }

    function loginPassenger($email, $pword)
    {
        sql = "SELECT pword FROM Passengers WHERE email = '$email'";
        $result = $GLOBALS['conn']->query($sql);
        
        $hash = mysqli_fetch_row($result)[0];
        return password_verify($pword, $hash);
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
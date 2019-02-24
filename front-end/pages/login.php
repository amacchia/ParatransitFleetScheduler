<?php
    // define variables and set to empty values
    $fname = $lname = $email = $pword = $service = "";

    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $fname = test_input($_POST["fname"]);
        $lname = test_input($_POST["lname"]);
        $email = test_input($_POST["email"]);
        $pword = test_input($_POST["pword"]);
        $service = test_input($_POST["service"]);

        echo "$fname  $lname";
    }

    function test_input($data) {
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }
?>
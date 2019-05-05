<?php
    create_connection();
    
    function create_connection()
    {
        //$servername = "localhost";
        // $password = "root";
        $servername = "3.81.8.187";
        $username = "root";
        $password = "senproj19";
        $dbname = "rideshare";
    
        // Create connection
        $GLOBALS['conn'] = new mysqli($servername, $username, $password, $dbname);
    
        // Check connection
        if ($GLOBALS['conn']->connect_error) {
            die("Connection failed: " . $GLOBALS['conn']->connect_error);
        } else {
            if (session_status() == PHP_SESSION_NONE) {
                session_start();
            }
        } 
    }
?>    

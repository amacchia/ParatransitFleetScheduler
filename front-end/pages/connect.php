<?php
    create_connection();
    
    function create_connection()
    {
        $servername = "localhost";
        $password = "root";
        // $servername = "http://ec2-3-81-8-187.compute-1.amazonaws.com/";
        $username = "root";
        // $password = "senproj19";
        $dbname = "rideshare";
    
        // Create connection
        $GLOBALS['conn'] = new mysqli($servername, $username, $password, $dbname);
    
        // Check connection
        if ($GLOBALS['conn']->connect_error) {
            die("Connection failed: " . $GLOBALS['conn']->connect_error);
        } else {
            session_start();
        } 
    }
?>    
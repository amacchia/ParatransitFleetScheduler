<?php
    // define variables and set to empty values
    $email = $pword = $service = $errMsg = "";

    if ($_SERVER["REQUEST_METHOD"] == "POST") {

        $email = clean_input($_POST["email"]);
        $pword = clean_input($_POST["pword"]);
        $service = clean_input($_POST["service"]);

        $userExists;
        if ($service === "driver") {
            $userExists = loginDriver($email, $pword);
        } else {
            $userExists = loginPassenger($email, $pword);
        }
        echo $userExists;
        echo '<br>';
        if ($userExists) {
            echo 'User exists';
            $_SESSION['userEmail'] = $email;
            if ($service === "driver") {
                $_SESSION['currUserEmail'] = $email; 
                header("Location: ./index1.php?page=driver");
            } else {
                header("Location: ./index1.php?page=passenger");
            }
        } else {
            $errMsg = 'User Not Found';
        }
        $GLOBALS['conn']->close(); 
    }

    function loginDriver($email, $pword)
    {
        $sql = "SELECT password, driverID FROM driver WHERE email = '$email'";
        
        $result = $GLOBALS['conn']->query($sql);
        if($result) {
            $row = mysqli_fetch_row($result);
            $hash = $row[0];
            $_SESSION['currUserID'] = $row[1];
            return password_verify($pword, $hash);
        }
        return false;
    }

    function loginPassenger($email, $pword)
    {
        $sql = "SELECT password, userID FROM user WHERE email = '$email'";
        $result = $GLOBALS['conn']->query($sql);
        if ($result) {
            $row = mysqli_fetch_row($result);
            $hash = $row[0];
            $_SESSION['currUserID'] = $row[1];
            return password_verify($pword, $hash);
        }
        return false;
    }

    function clean_input($data) {
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }
?>

<h1 id="sign-up-form-title">Login</h1>
<div id="sign-up-form">
    <form method="POST" action="./index1.php?page=login">
        <label for="email" class="input-label">Email</label>
        <input type="email" name="email" class="form-input"><br>

        <label for="pword" class="input-label">Password</label>
        <input type="password" name="pword" class="form-input"><br>

        <label for="service" class="input-label">Service</label>
        <div class="rd-input">
            <input type="radio" name="service" value="driver">Driver
            <input type="radio" name="service" value="passenger">Passenger <br>
        </div>

        <input type="submit" value="Login" id="sub-btn">
        <p>Not a user? <a href="./index1.php?page=sign-up">Sign Up</a></p>
    </form>
</div>

<div class="errorMsg">
<?php
    if ($errMsg != "") {
	    echo 'Error: ' . $errMsg;
    }
?>
</div>
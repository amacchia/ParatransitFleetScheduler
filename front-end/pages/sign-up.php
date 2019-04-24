<?php
// define variables and set to empty values
$fname = $lname = $email = $pword = $service = $errMsg = "";

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

    $fname = clean_input($_POST["fname"]);
    $lname = clean_input($_POST["lname"]);
    $email = clean_input($_POST["email"]);
    $pword = clean_input($_POST["pword"]);
    $service = clean_input($_POST["service"]);

	if ($errMsg == "") {
		if ($service === "driver") {
			insert_driver($fname, $lname, $email, $pword, $service);
		} else {
			insert_passenger($fname, $lname, $email, $pword, $service);
		}
	}
}

function insert_driver($first_name, $last_name, $email, $pword, $service)
{
	$hash = password_hash($pword, PASSWORD_DEFAULT);

	$sql = "INSERT INTO 
                        driver (firstName, lastName, email, password) 
                    VALUES
                        ('$first_name', '$last_name', '$email', '$hash')";
    
    $result = $GLOBALS['conn']->query($sql);
	if ($result) {
		header('Location: index.php?page=login');
	} else {
		echo "Error: " . $sql . "<br>" . $GLOBALS['conn']->error;
	}

	$GLOBALS['conn']->close();
}

function insert_passenger($first_name, $last_name, $email, $pword, $service)
{
	$hash = password_hash($pword, PASSWORD_DEFAULT);

	$sql = "INSERT INTO 
                        user (firstName, lastName, email, password) 
                    VALUES
                        ('$first_name', '$last_name', '$email', '$hash')";

    $result = $GLOBALS['conn']->query($sql);
	if ($result) {
		header('Location: index.php?page=login');
	} else {
		echo "Error: " . $sql . "<br>" . $GLOBALS['conn']->error;
	}

	$GLOBALS['conn']->close();
}

function clean_input($data)
{
	$data = trim($data);
	$data = stripslashes($data);
	$data = htmlspecialchars($data);
	return $data;
}
?>


<h1 id="sign-up-form-title">Sign Up</h1>
<div id="sign-up-form">
    <form method="POST" action="./index.php?page=sign-up">
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
        <p>Already a user? <a href="./index.php?page=login">Login</a></p>
    </form>
</div>

<div class="errorMsg">
<?php
    if ($errMsg != "") {
	    echo 'Error: ' . $errMsg;
    }
?>
</div>
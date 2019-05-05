<?php
$driverID = $_SESSION['currUserID'];

// Update driver schedules
if ($_SERVER["REQUEST_METHOD"] == "POST") {

    // Delete old schedule
    $deleteSQL = "DELETE FROM driverschedule WHERE driverID = '$driverID'";
    $deleteResult = $GLOBALS['conn']->query($deleteSQL);
    if (!$deleteResult) {
        echo 'Error removing old schedule';
    }

    $startTime = "08:00:00";
    $endTime = "17:00:00";
    $mon = $tue = $wed = $thr = $fri = $sat = $sun = FALSE;
    if (!empty($_POST["mon"])) {
        $mon = TRUE;
    }

    if (!empty($_POST["tue"])) {
        $tue = TRUE;
    }

    if (!empty($_POST["wed"])) {
        $wed = TRUE;
    }

    if (!empty($_POST["thr"])) {
        $thr = TRUE;
    }

    if (!empty($_POST["fri"])) {
        $fri = TRUE;
    }

    if (!empty($_POST["sat"])) {
        $sat = TRUE;
    }

    if (!empty($_POST["sun"])) {
        $sun = TRUE;
    }

    $insertSQL = "";
    if ($mon) {
        $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                    VALUES ('$driverID', 'MONDAY', '$startTime', '$endTime')";
        $monResult = $GLOBALS['conn']->query($insertSQL);
    } else {
        $monResult = TRUE;
    }

    if ($tue) {
        $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                    VALUES ('$driverID', 'TUESDAY', '$startTime', '$endTime')";
        $tueResult = $GLOBALS['conn']->query($insertSQL);
    } else {
        $tueResult = TRUE;
    }

    if ($wed) {
        $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                    VALUES ('$driverID', 'WEDNESDAY', '$startTime', '$endTime')";
        $wedResult = $GLOBALS['conn']->query($insertSQL);
    } else {
        $wedResult = TRUE;
    }

    if ($thr) {
        $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                    VALUES ('$driverID', 'THURSDAY', '$startTime', '$endTime')";
        $thrResult = $GLOBALS['conn']->query($insertSQL);
    } else {
        $thrResult = TRUE;
    }

    if ($fri) {
        $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                    VALUES ('$driverID', 'FRIDAY', '$startTime', '$endTime')";
        $friResult = $GLOBALS['conn']->query($insertSQL);
    } else {
        $friResult = TRUE;
    }

    if ($sat) {
        $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                    VALUES ('$driverID', 'SATURDAY', '$startTime', '$endTime')";
        $satResult = $GLOBALS['conn']->query($insertSQL);
    } else {
        $satResult = TRUE;
    }

    if ($sun) {
        $insertSQL = "INSERT INTO driverschedule (driverID, dayOfWeek, startTime, endTime)
                    VALUES ('$driverID', 'SUNDAY', '$startTime', '$endTime')";
        $sunResult = $GLOBALS['conn']->query($insertSQL);
    } else {
        $sunResult = TRUE;
    }
}
    // Get current sch
    $currentSchSQL = "SELECT dayOfWeek FROM driverschedule WHERE driverID = '$driverID'";
    $currResult = $GLOBALS['conn']->query($currentSchSQL);
?>

<div class="container">
    <div class="sch-form">
        <div>
            <?php
                if ($currResult) {
                    echo 'Your current work days: ';
                    while ($row = $currResult->fetch_assoc()) {
                        echo $row['dayOfWeek'].' '; 
                    }
                }
            ?>
        </div>
        <h3> Update Driver Schedule: </h3>
        <form action="./index.php?page=driver-sch" method="post" id="driver-sch-form">

            <label for="mon"> Monday </label>
            <input type="checkbox" name="mon" id="mon" value="MONDAY">
            <br>

            <label for="tue"> Tuesday </label>
            <input type="checkbox" name="tue" id="tue" value="TUESDAY">
            <br>

            <label for="wed"> Wednesday </label>
            <input type="checkbox" name="wed" id="wed" value="WEDNESDAY">
            <br>

            <label for="thr"> Thursday </label>
            <input type="checkbox" name="thr" id="thr" value="THURSDAY">
            <br>   

            <label for="fri"> Friday </label>
            <input type="checkbox" name="fri" id="fri" value="FRIDAY">
            <br>   

            <label for="sat"> Saturday </label>
            <input type="checkbox" name="sat" id="sat" value="SATURDAY">
            <br>  

            <label for="sunn"> Sunday </label>
            <input type="checkbox" name="sun" id="sun" value="SUNDAY">
            <br>
            
            <input type="submit">
        </form>
        <?php
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
        if ($monResult && $tueResult && $wedResult && $thrResult && $friResult && $satResult && $sunResult) {
                echo 'Your schedule has been updated!';
            } else {
                echo 'Error updating schedule.';
                echo $GLOBALS['conn']->error;
            } 
        }
        ?>
    </div>
</div>
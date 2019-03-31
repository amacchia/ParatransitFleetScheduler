<!DOCTYPE html>
<html lang="en-US">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Paratransit Fleet Scheduler</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link type="text/css" rel="stylesheet" href="../css/main.css" />
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <script src="../scripts/index.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
    <?php 
        include 'connect.php';
    ?>

    <div class="wrapper">
        <header>
            <?php
                include 'header.php';
            ?>
        </header>
        
        <?php
            if (isset($_GET['page'])) {
                switch ($_GET['page']) {
                case 'login':
                    include 'login.php';
                    break;
                case 'driver':
                    include 'driver.php';
                    break;
                case 'sign-up':
                    include 'sign-up.php';
                    break;
                case 'passenger':
                    include 'passenger.php';
                    break;
                default:
                    include 'login.php';
                    break;
                } 
            } else {
                include 'login.php';
            }
        ?>

        <footer>
            <?php
                include 'footer.php';
            ?>
        </footer>
    <div>
</body>
</html>
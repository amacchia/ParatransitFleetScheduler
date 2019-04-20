<!-- <div class="header-container">
    <span>
        <p class="header-text">
            <h3 class="header-text">Paratransit Fleet Scheduler</h3>
        </p>
        <a href="./index1.php?page=login">Sign Out</a>
    </span>
</div> -->

<nav class="navbar navbar-dark bg-dark" id="header">
  <a class="navbar-brand">Paratransit Fleet Scheduler</a>
  <div class="nav-actions justify-content-end">
  <?php
    if (isset($_GET["page"])) {
        $pg = $_GET["page"];

        if ($pg === 'driver') {
            echo '<a href="./index1.php?page=driver-sch" class="btn btn-secondary btn-lg active" role="button">Update Schedule</a> &nbsp;';
        }

        if ($pg === 'driver' || $pg === 'passenger' || $pg === 'driver-sch') {
            echo '<a href="./index1.php?page=login" class="btn btn-secondary btn-lg active" role="button">Sign-Out</a>';
        }
    }
  ?>
  </div>
</nav>
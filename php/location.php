<?php
  $username = "root";
  $password = "guchenji";

  // echo "Connecting to database\n";
  $con=mysqli_connect("localhost","$username","$password","pokemon_hunter");

  if (mysqli_connect_errno($con)) {
    // echo "Failed to connect to MySQL: " . mysqli_connect_error();
    return;
  }
  // echo "Connecting to database successfully\n";
  $query = "select * from id_location_time;";
  $result = mysqli_query($con, $query);
  $response = array();
  $response["pokemon"] = array();
  if(mysqli_num_rows($result) > 0){
    $i = 0;
    while($row = mysqli_fetch_array($result)){
      $pokemon = array();
      $pokemon["pokemon_id"] = $row["pokemon_id"];
      $pokemon["latitude"] = $row["latitude"];
      $pokemon["longitude"] = $row["longitude"];
      $pokemon["time"] = $row["time"];
      array_push($response["pokemon"], $pokemon);
      $i = $i + 1;
      if( $i == 100) break;

    }
    echo json_encode($response);
  }
  mysqli_close($con);
?>

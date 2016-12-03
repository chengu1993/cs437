<?php
$username = "root";
$password = "guchenji";

// echo "Connecting to database\n";
$con=mysqli_connect("localhost","$username","$password","pokemon_hunter");

if (mysqli_connect_errno($con)) {
    // echo "Failed to connect to MySQL: " . mysqli_connect_error();
    return;
}

$pokemon_id = $_POST['pokemon_id'];

//$pokemon_id = 1;
// echo "Connecting to database successfully\n";
$response = array();
$response["evolve_sequence"] = array();

$query = "select B.from_id from evolve_from as A, evolve_from as B where $pokemon_id = A.pokemon_id and A.from_id = B.pokemon_id;";
$result = mysqli_query($con, $query);
if(mysqli_num_rows($result) > 0){
    $row = mysqli_fetch_array($result);
    $res = array();
    $res["pokemon_id"] = $row["from_id"];
    array_push($response["evolve_sequence"], $res);
}

$query = "select from_id from evolve_from where $pokemon_id = evolve.pokemon_id;";
$result = mysqli_query($con, $query);
if(mysqli_num_rows($result) > 0){
    $row = mysqli_fetch_array($result);
    $res = array();
    $res["pokemon_id"] = $row["from_id"];
    array_push($response["evolve_sequence"], $res);
}

$res = array();
$res["pokemon_id"] = $pokemon_id;
array_push($response["evolve_sequence"], $res);

$query = "select * from evolve_to where $pokemon_id = evolve_to.pokemon_id;";
$result = mysqli_query($con, $query);
if(mysqli_num_rows($result) > 0){
    $row = mysqli_fetch_array($result);
    $res = array();
    $res["pokemon_id"] = $row["to_id"];
    array_push($response["evolve_sequence"], $res);
}


$query = "select B.to_id from evolve_to as A, evolve_to as B where $pokemon_id = A.pokemon_id and A.to_id = B.pokemon_id;";
$result = mysqli_query($con, $query);
if(mysqli_num_rows($result) > 0){
    $row = mysqli_fetch_array($result);
    $res = array();
    $res["pokemon_id"] = $row["to_id"];
    array_push($response["evolve_sequence"], $res);
}

echo json_encode($response);
mysqli_close($con);
?>
<?php
$username = "root";
$password = "guchenji";

// echo "Connecting to database\n";
$con=mysqli_connect("localhost","$username","$password","pokemon_hunter");

if (mysqli_connect_errno($con)) {
//     echo "Failed to connect to MySQL: " . mysqli_connect_error();
    return;
}

$pokemon_id = $_POST["pokemon_id"];
//echo $pokemon_id;
//$pokemon_id = 1;
//echo "pokemon_id: $pokemon_id";

//echo "Connecting to database successfully\n";
$response = array();
$response["type"] = array();

$query = "select * from has_type, type where $pokemon_id = has_type.pokemon_id and has_type.type_id = type.type_id;";
$result = mysqli_query($con, $query);
if(mysqli_num_rows($result) > 0){
    $i = 0;
    while($row = mysqli_fetch_array($result)){
        $type = array();
        $type["type_name"] = $row["type_name"];
        array_push($response["type"], $type);
    }
}

//find advantage
$query = "select * from has_type, type_against where $pokemon_id = has_type.pokemon_id and has_type.type_id = type_against.type_id;";
$result = mysqli_query($con, $query);
if(mysqli_num_rows($result) > 0){
    $i = 0;
    $row = mysqli_fetch_array($result);
    $response["advantage"] = $row["advantage"];
    $response["disadvantage"] = $row["disadvantage"];
}

echo json_encode($response);
mysqli_close($con);
?>

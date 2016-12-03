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
$query = "select * from has_charge_attack, charge_attack where $pokemon_id = has_charge_attack.pokemon_id and has_charge_attack.attack_id = charge_attack.attack_id;";
$result = mysqli_query($con, $query);
$response = array();
$response["charge_attack"] = array();
if(mysqli_num_rows($result) > 0){
    while($row = mysqli_fetch_array($result)){
        $charge_attack = array();
        $charge_attack["attack_name"] = $row["attack_name"];
        $charge_attack["dps"] = $row["dps"];
        array_push($response["charge_attack"], $charge_attack);
    }
}

$query = "select * from has_fast_attack, fast_attack where $pokemon_id = has_fast_attack.pokemon_id and has_fast_attack.attack_id = fast_attack.attack_id;";
$result = mysqli_query($con, $query);
$response["fast_attack"] = array();
if(mysqli_num_rows($result) > 0){
    while($row = mysqli_fetch_array($result)){
        $fast_attack = array();
        $fast_attack["attack_name"] = $row["attack_name"];
        $fast_attack["dps"] = $row["dps"];
        array_push($response["fast_attack"], $fast_attack);
    }
}
echo json_encode($response);
mysqli_close($con);
?>
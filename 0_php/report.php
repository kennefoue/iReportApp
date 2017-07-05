<?php
	define("HOST", "localhost");
	define("USER", "id1811016_avikenz");
	define("PASS", "fouemene");
	define("DB", "id1811016_ireportdb");
    $con = mysqli_connect(HOST, USER, PASS, DB);
     
	$reportJsonString = isset($_POST["reportJsonObjString"]) ? $_POST["reportJsonObjString"] : '{"userId": 1, "latitude": 10, "longitude": 10, "reference": "null", "image1": "null", "image2": "null", "image3": "null"}';
	
	$reportObj = json_decode($reportJsonString);
	
	
	$userId = $reportObj->userId;
	$typ = 1;
	$long = $reportObj->longitude;
	$lat= $reportObj->latitude;
	$img1= $reportObj->image1;
	$img2= $reportObj->image2;
	$img3= $reportObj->image3;
	$ref= $reportObj->reference;
	$statement1 = mysqli_prepare($con, "INSERT INTO Reports(User_Id, Typ, Longitude, Latitude, Image1, Image2, Image3, Reference) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");	
    mysqli_stmt_bind_param($statement1, "ssssssss", $userId, $typ, $long, $lat, $img1, $img2, $img3, $ref);
	mysqli_stmt_execute($statement1);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>
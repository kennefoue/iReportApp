<?php
	$img1Name = isset($_POST["image1Name"]) ? $_POST["image1Name"] : "No Name Posted";
	$img2Name = isset($_POST["image2Name"]) ? $_POST["image2Name"] : "No Name Posted 2";
	$img3Name = isset($_POST["image3Name"]) ? $_POST["image3Name"] : "No Name Posted 3";
	
	$img1 = isset($_POST["image1"]) ? $_POST["image1"] : "No Image Posted";
	$img2 = isset($_POST["image2"]) ? $_POST["image2"] : "No Image Posted";
	$img3 = isset($_POST["image3"]) ? $_POST["image3"] : "No Image Posted";
	
	$decodedImg1 = base64_decode("$img1");
	$decodedImg2 = base64_decode("$img2");
	$decodedImg3 = base64_decode("$img3");
	
	file_put_contents("iReport_images/" . $img1Name . ".jpg", $decodedImg1);
	file_put_contents("iReport_images/" . $img2Name . ".jpg", $decodedImg2);
	file_put_contents("iReport_images/" . $img3Name . ".jpg", $decodedImg3);
	
	$responce = array();
	$responce["success"] = true;
	echo json_encode($responce);
?>
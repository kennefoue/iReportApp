<?php
	$img1Name = isset($_POST["image1Name"]) ? $_POST["image1Name"] : "NoNamePosted";
	$img2Name = isset($_POST["image2Name"]) ? $_POST["image2Name"] : "NoNamePosted2";
	$img3Name = isset($_POST["image3Name"]) ? $_POST["image3Name"] : "NoNamePosted3";
	
	$img1 = isset($_POST["image1"]) ? $_POST["image1"] : "NoImagePosted";
	$img2 = isset($_POST["image2"]) ? $_POST["image2"] : "NoImagePosted";
	$img3 = isset($_POST["image3"]) ? $_POST["image3"] : "NoImagePosted";
	
	$decodedImg1 = base64_decode("$img1");
	$decodedImg2 = base64_decode("$img2");
	$decodedImg3 = base64_decode("$img3");
	
	file_put_contents(dirname(getcwd(),1) . "/iReport_images/" . $img1Name . ".jpg", $decodedImg1);
	file_put_contents(dirname(getcwd(),1) . "/iReport_images/" . $img2Name . ".jpg", $decodedImg2);
	file_put_contents(dirname(getcwd(),1) . "/iReport_images/" . $img3Name . ".jpg", $decodedImg3);
	
	$responce = array();
	$responce["success"] = true;
	echo json_encode($responce);
?>
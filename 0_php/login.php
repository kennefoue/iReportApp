<?php
	$con = mysqli_connect("localhost", "id1811016_avikenz", "fouemene", "id1811016_ireportdb");

    $Email = isset($_POST["email"]) ? $_POST["email"] : "kenne";
    $Password = isset($_POST["password"]) ? $_POST["password"] : "foue";
    
    $statement = mysqli_prepare($con, "SELECT  User_Id, Full_Name, Email, Password, Birthdate, Mobile, Street, House_Number, Post_Code, City, Country, Iban FROM Users WHERE Email = ? AND Password = ?");
    mysqli_stmt_bind_param($statement, "ss", $Email, $Password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $User_Id, $Full_Name, $Email, $Password, $Birthdate, 
							$Mobile, $Street, $House_Number, $Post_Code, $City, $Country, $Iban);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;  
		$response["userId"] = $User_Id;
        $response["fullName"] = $Full_Name;
        $response["email"] = $Email;
		$response["password"] = $Password;
		$response["birthdate"] = $Birthdate;
        $response["mobile"] = $Mobile;
		$response["street"] = $Street;
		$response["houseNumber"] = $House_Number;
		$response["postCode"] = $Post_Code;
		$response["city"] = $City;
		$response["country"] = $Country;
		$response["iban"] = $Iban;
		$response["mobile"] = $Mobile;		
    }
    
    echo json_encode($response);
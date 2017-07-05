<?php
    $con = mysqli_connect("localhost", "id1811016_avikenz", "fouemene", "id1811016_ireportdb");
    
    $Full_Name = isset($_POST["fullName"]) ? $_POST["fullName"] : "fullName";
    $Email = isset($_POST["email"]) ? $_POST["email"] : "email";
    $Password = isset($_POST["password"]) ? $_POST["password"] : "password";
    $Mobile = isset($_POST["mobile"]) ? $_POST["mobile"] : "mobile";
    $statement = mysqli_prepare($con, "INSERT INTO Users (Full_Name, Email, Password, Mobile) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssss", $Full_Name, $Email, $Password, $Mobile);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>

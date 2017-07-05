<?php
	$con = mysqli_connect("localhost", "id1811016_avikenz", "fouemene", "id1811016_ireportdb");
	
	$userId = isset($_POST["userId"]) ? $_POST["userId"] : "1";
	$yesStat = "Yes";
	$noStat = "No";
	$waitStat = "Waiting";
	
	$totalRprtStmt = mysqli_prepare($con, "SELECT COUNT(*) FROM Reports WHERE User_Id = ?; ");
	$yesRprtStmt = mysqli_prepare($con, "SELECT COUNT(*) FROM Reports WHERE User_Id = ? AND Status = ?; ");
	$noRprtStmt = mysqli_prepare($con, "SELECT COUNT(*) FROM Reports WHERE User_Id = ? AND Status = ?; ");
	$waitRprtStmt = mysqli_prepare($con, "SELECT COUNT(*) FROM Reports WHERE User_Id = ? AND Status = ?; ");
	
	mysqli_stmt_bind_param($totalRprtStmt, "s", $userId);
	mysqli_stmt_bind_param($yesRprtStmt, "ss", $userId, $yesStat);
	mysqli_stmt_bind_param($noRprtStmt, "ss", $userId, $noStat);
	mysqli_stmt_bind_param($waitRprtStmt, "ss", $userId, $waitStat);
	
	$response = array();
	$response["success"] = false;
	
	if($totalRprtStmt->execute()) {
		$response["success"] = true;
		mysqli_stmt_store_result($totalRprtStmt);
		mysqli_stmt_bind_result($totalRprtStmt, $totalReports);
		mysqli_stmt_fetch($totalRprtStmt);		
		$response["totalReports"] = $totalReports;
		$totalRprtStmt->free_result();
	}
	
	if($yesRprtStmt->execute()) {
		$response["success"] = true;
		mysqli_stmt_store_result($yesRprtStmt);
		mysqli_stmt_bind_result($yesRprtStmt, $acceptedReports);
		mysqli_stmt_fetch($yesRprtStmt);		
		$response["acceptedReports"] = $acceptedReports;
		$yesRprtStmt->free_result();
	}
	
	if($noRprtStmt->execute()) {
		mysqli_stmt_store_result($noRprtStmt);
		mysqli_stmt_bind_result($noRprtStmt, $refusedReports);
		mysqli_stmt_fetch($noRprtStmt);		
		$response["refusedReports"] = $refusedReports;
		$noRprtStmt->free_result();
	}
	
	if($waitRprtStmt->execute()) {
		mysqli_stmt_store_result($waitRprtStmt);
		mysqli_stmt_bind_result($waitRprtStmt, $waitReports);
		mysqli_stmt_fetch($waitRprtStmt);		
		$response["waitingReports"] = $waitReports;
		$waitRprtStmt->free_result();
	}
	
	echo json_encode($response);
	
?>
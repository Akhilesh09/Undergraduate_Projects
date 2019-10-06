<?php
// define variables and set to empty values
include 'config.php';
$unameErr = $pswErr = $cpwErr =$N=$error =$result1="";
$uname = $psw = $cpw ="";
$unameflag = $pswflag = $cpwflag=TRUE;
if ($_SERVER["REQUEST_METHOD"] == "POST"){
/************************************************/
if (empty($_POST["uname"])){
    $unameErr = "Name is required";
	$unameflag=FALSE;
	}
     else
	 {
     $uname = test_input($_POST["uname"]);
     if (!preg_match("/^[a-zA-Z0-9]*$/",$uname)){
       $unameErr = "Only letters and numbers allowed";
	   $unameflag=FALSE;
       }
}

  if (empty($_POST["psw"])){
    $passErr = "Password is required";
	$pswflag=FALSE;
	}
     else{
     $psw = test_input($_POST["psw"]);
     // check if name only contains letters and whitespace
     if (!preg_match("/^[a-zA-Z0-9$#]*/",$psw)){
       $passErr = "Only letters numbers and #$ allowed";
	   $pswflag=FALSE;
       }
     } 
if($unameflag and $pswflag){//valid user name and password format
$sql1 = "SELECT upper(user_name),password,user_status FROM socntwk.blogusers WHERE upper(user_name)=upper('$uname') AND password='$psw'";
$result1=mysqli_query($con,$sql1) or die(mysqli_fatal_error($con,"No such User"));
//echo mysql_num_rows($result1);
if (mysqli_num_rows($result1)==0){ //invalid user name/password
$error = "Username/Password invalid";
//echo $error;
$loggedin=FALSE;
if(!isset($_SESSION)) 
session_start();
//else
//{
//if(isset($_SESSION['uname']))
//{
//unset $_SESSION['uname'];
//$loggedin=FALSE;
//}
$unameflag=$pswflag=FALSE;
$_SESSION['error']= $error;
mysqli_close($con);
header('Location:login.php');
exit();
}
else // valid user name
{
while($row = mysqli_fetch_array($result1)) {
    $user_status = $row['user_status'];
}
if(($user_status=='Y') or ($user_status=='y'))
{
$unameflag=$pswflag=FALSE;
$unameErr=$passErr="";
mysqli_close($con);
if(!isset($_SESSION)) session_start();
$_SESSION['uname']= $uname;
$_SESSION['psw']=  $psw;
header('Location:index.php');
exit();
}
else //user status is not approved
{
$loggedin=FALSE;
$unameflag=$pswflag=FALSE;
$error = "Account not approved";
if(!isset($_SESSION)) session_start();
$_SESSION['error']= $error;
mysqli_close($con);
header('Location:login.php');
exit();
}
}
}	 
else //invalid user name or password format
{
if(!isset($_SESSION))  session_start();
//else
///{
//if(isset($_SESSION['uname']))
//{
//unset $_SESSION['uname'];
//$loggedin=FALSE;
//}
$loggedin=FALSE;
$_SESSION['unameErr']=$unameErr;
$_SESSION['passErr']=$passErr;
header('Location:login.php');
exit();
}
}
?>
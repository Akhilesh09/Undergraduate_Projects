<?php
include_once 'header.php';
if (!$loggedin) die();
$blogtitleErr = $msgtextErr="";
$blogtitle=$msgtext="";
$blogtitleflag = $msgtextflag =true;
if ($_SERVER["REQUEST_METHOD"] == "POST"){
$uname= $_SESSION['uname'];

if(empty($_POST["msgtext"])){
  $msgtextErr = "Message Post is required";
  $msgtextflag=FALSE;
  }
else{
$msgtext = test_input($_POST["msgtext"]);
$msgtext=mysql_fix_string($con,$msgtext);
}
echo $msgtext;

if (empty($_POST["blogtitle"])){
$blogtitleErr = "Response Blog Title is required"; 
$blogtitleflag=FALSE;}
else 
{
$blogtitle = test_input($_POST["blogtitle"]);
$blogtitle=mysql_fix_string($con,$blogtitle);
}
echo $blogtitle;
echo $blogtitleErr;
echo $blogtitleflag;
/****************************************************/
if($blogtitleflag AND $msgtextflag)
{ // all data are in valid format 
echo "all flags are true";
mysqli_select_db($con,'socntwk')  or die("Unable to select database: " . mysqli_error($con));
if (!$con) {mysqli_fatal_error($con,"Invalid username or password FOR DATABASE");}
$sql1="INSERT INTO socntwk.THREAD_MESSAGES VALUES(NULL,upper('$uname'),upper('$uname'),'$msgtext',now(),'$blogtitle')";
$result1 = mysqli_query($con,$sql1) or die(mysqli_fatal_error($con,"Could not insert the record"));
mysqli_close($con);
$blogtitleflag = $msgtextflag=false;
if(!isset($_SESSION)) session_start();
$_SESSION['view']=$uname;
$_SESSION['uname']=$uname;
header('Location:myblog.php');
exit();
} // successfuly posted
else
{ 
// one or more data is not in valid format
//echo "one of the data is invalid format";
$blogtitleflag = $msgtextflag=false;
if(!isset($_SESSION))  session_start();
$_SESSION['msgtextErr']=$msgtextErr;
header('Location:myblog.php');
exit();
}
}
?>
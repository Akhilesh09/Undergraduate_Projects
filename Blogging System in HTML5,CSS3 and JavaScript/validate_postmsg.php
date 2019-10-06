<?php
include_once 'header.php';
if (!$loggedin) die();
$unameErr = $msgtypeErr = $msgtextErr=$blogtitleErr="";
$uname = $msgtype = $msgtext ="";
$unameflag = $msgtypeflag = $msgtextflag =$blogtitleflag=true;
if ($_SERVER["REQUEST_METHOD"] == "POST"){
$uname= $_SESSION['uname'];
echo $uname;
//echo $blogdate;
if (empty($_POST["blogtitle"])){
$blogtitleErr = "Blog title is required";
$blogtitleflag=FALSE;
}
else{
$blogtitle = test_input($_POST["blogtitle"]);
$blogtitle=mysql_fix_string($con,$blogtitle);
}

if (empty($_POST["msgtext"])){
$msgtextErr = "Message Post is required";
$msgtextflag=FALSE;
}
else{
$msgtext = test_input($_POST["msgtext"]);
$msgtext=mysql_fix_string($con,$msgtext);
}
echo $msgtext;
if(empty($_POST["msgtype"])){
$msgtypeErr = "Message Post is required"; 
$msgtypeflag=FALSE;}
else {$msgtype = test_input($_POST["msgtype"]);}
echo $msgtype;
/****************************************************/
if($msgtypeflag and $msgtextflag and $unameflag and $blogtitleflag)
{ // all data are in valid format 
echo "all flags are true";

/**
if(isset($_POST['form_field_name'])) {
    $variable_name = $_POST['form_field_name'];
}
*/

if(isset($_POST['EDIT'])) 
//{
//    $variable_name = $_POST['form_field_name'];
//}
//if($_POST['EDIT']=="EDIT")
{
$author = test_input($_POST["author"]);
$blogtitle = test_input($_POST["blogtitle"]);
$blogdate = test_input($_POST["blogdate"]);
mysqli_select_db($con,'socntwk')  or die("Unable to select database: " . mysqli_error($con));
if (!$con) {mysqli_fatal_error($con,"Invalid username or password");}
$sql1="UPDATE socntwk.user_messages set user_message='$msgtext',user_msg_status='$msgtype' 
where upper(user_name)=upper('$author') and blog_title='$blogtitle' and user_msg_date='$blogdate'";
$result1= mysqli_query($con,$sql1) or die(mysqli_fatal_error($con,"Could not update the record"));
if(!$result1) {die('Could not update data: ' . mysqli_error($con)); }
mysqli_close($con);
$msgtypeflag = $msgtextflag =false;
if(!isset($_SESSION)) session_start();
$error="$uname's Message has been successfuly updated";
$_SESSION['error']=$error;
header('Location:members.php?view='.$author);
exit();
}
/**
elseif(isset($_POST['DELETE'])) {
    $variable_name = $_POST['form_field_name'];
}
*/
//elseif($_POST['DELETE']=="DELETE")
elseif(isset($_POST['DELETE']))
{
$author = test_input($_POST["author"]);
$blogtitle = test_input($_POST["blogtitle"]);
$blogdate = test_input($_POST["blogdate"]);
mysqli_select_db($con,'socntwk')  or die("Unable to select database: " . mysqli_error($con));
if (!$con) {mysqli_fatal_error($con,"Invalid username or password");}
$sql1="DELETE from socntwk.user_messages where upper(user_name)=upper('$author') AND blog_title='$blogtitle' AND user_msg_date='$blogdate'";
$result1 = mysqli_query($con,$sql1) or die(mysqli_fatal_error($con,"Could not delete the record"));
if(!$result1) {die('Could not delete data: ' . mysqli_error($con)); }
mysqli_close($con);
$msgtypeflag = $msgtextflag =false;
if(!isset($_SESSION)) session_start();
$error="$uname's Message has been successfuly deleted";
$_SESSION['error']=$error;
header('Location:members.php?view='.$author);
exit();
}
elseif(isset($_POST['THREADEDIT']))
//elseif($_POST['THREADEDIT']=="THREADEDIT")
{
$threadauthor = test_input($_POST["threadauthor"]);
$threaddate = test_input($_POST["threaddate"]);
mysqli_select_db($con,'socntwk')  or die("Unable to select database: " . mysqli_error($con));
if (!$con) {mysqli_fatal_error($con,"Invalid username or password");}
$sql1="UPDATE socntwk.msg_followers set user_thread_txt='$msgtext'
where upper(user_follower)=upper('$threadauthor') and user_thread_date='$threaddate'";
$result1= mysqli_query($con,$sql1) or die(mysqli_fatal_error($con,"Could not update the record"));
if(!$result1) {die('Could not update data: ' . mysqli_error($con)); }
mysqli_close($con);
$msgtypeflag = $msgtextflag =false;
if(!isset($_SESSION)) session_start();
$error="$threadauthor's thread has been successfuly updated";
$_SESSION['error']=$error;
header('Location:members.php?view='.$threadauthor);
exit();
}
elseif(isset($_POST['THREADDELETE']))
//elseif($_POST['THREADDELETE']=="THREADDELETE")
{
$threadauthor = test_input($_POST["threadauthor"]);
$threaddate = test_input($_POST["threaddate"]);
mysqli_select_db($con,'socntwk')  or die("Unable to select database: " . mysqli_error($con));
if (!$con) {mysqli_fatal_error($con,"Invalid username or password");}
$sql1="DELETE from socntwk.msg_followers where upper(user_follower)=upper('$threadauthor') and user_thread_date='$threaddate'";
$result1 = mysqli_query($con,$sql1) or die(mysqli_fatal_error($con,"Could not delete the record"));
if(!$result1) {die('Could not delete data: ' . mysqli_error($con)); }
mysqli_close($con);
$msgtypeflag = $msgtextflag =false;
if(!isset($_SESSION)) session_start();
$error="$threadauthor' has deleted the thread successfuly";
$_SESSION['error']=$error;
header('Location:members.php?view='.$threadauthor);
exit();
}
else
{
//$blogdate = test_input($_POST["blogdate"]);	
$mydate=date("Y:m:d H:i:s");
mysqli_select_db($con,'socntwk')  or die("Unable to select database: " . mysqli_error($con));
if (!$con) {mysqli_fatal_error($con,"Invalid username or password");}
$sql1="INSERT INTO socntwk.user_messages VALUES(NULL,upper('$uname'),'$msgtext','$mydate','$msgtype','$blogtitle')";
$result1 = mysqli_query($con,$sql1) or die(mysqli_fatal_error($con,"Could not insert the record"));
mysqli_close($con);
$msgtypeflag = $msgtextflag =false;
if(!isset($_SESSION)) session_start();
$error="$uname's Message has been successfuly posted";
$_SESSION['error']=$error;
header('Location:index.php');
exit();
}
} // successfuly posted
else
{ // one or more data is not in valid format
echo "one of the data is invalid format";
$unameflag = $msgtypeflag = $msgtextflag=false;
if(!isset($_SESSION))  session_start();
$_SESSION['unameErr']=$unameErr;
///echo $emailErr;
$_SESSION['msgtypeErr']=$msgtypeErr;
$_SESSION['msgtextErr']=$msgtextErr;
$_SESSION['blogtitleErr']=$blogtitleErr;
header('Location:user_registration.php');
exit();
}
}
?>
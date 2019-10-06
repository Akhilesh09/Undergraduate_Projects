<?php
include_once 'header.php';
if (!$loggedin) die();
$blogtitleErr = $authorErr = $msgtextErr=$blogdateErr=$followerErr="";
$blogtitle=$author=$msgtext=$blogdate=$follower="";
$blogtitleflag = $authorflag=$msgtextflag=$blogdateflag=$followerflag =true;
if ($_SERVER["REQUEST_METHOD"] == "POST"){
$uname= $_SESSION['uname'];
echo $uname;
if (empty($_POST["msgtext"])){
  $msgtextErr = "Message Post is required";
  $msgtextflag=FALSE;
  }
else{
$msgtext = test_input($_POST["msgtext"]);
$msgtext=mysql_fix_string($con,$msgtext);
echo " THREAD TEXT: $msgtext";
}


if (empty($_POST["blogtitle"])){
$blogtitleErr = "Blog Title is required"; 
$blogtitleflag=FALSE;}
else {
$blogtitle = test_input($_POST["blogtitle"]);
$blogtitle=mysql_fix_string($con,$blogtitle);
}
echo "  blog TITLE:  $blogtitle";

if (empty($_POST["author"])){
$authorErr = "Author id is required"; 
$authorflag=FALSE;}
else {
	$author = test_input($_POST["author"]);
	$author=mysql_fix_string($con,$author);
	}
//echo "  $author";
if (empty($_POST["follower"])){
$followerErr = "Follower id is required"; 
$followerflag=FALSE;}
else {$follower = test_input($_POST["follower"]);}


if (empty($_POST["blogdate"])){
$blogdateErr = "blog date is required"; 
$blogdateflag=FALSE;}
else 
{
$blogdate = test_input($_POST["blogdate"]);
$blogdate=mysql_fix_string($con,$blogdate);
}
echo " BLOG DATE:  $blogdate";
}
//echo "thread date is required-". $_POST["threaddate"];
/****************************************************/
if($blogtitleflag AND $authorflag AND $msgtextflag AND $blogdateflag AND $followerflag)
{ // all data are in valid format 
echo "all flags are true insert into msg-followers";
mysqli_select_db($con,'socntwk')  or die("Unable to select database: " . mysqli_error($con));
if (!$con) {mysqli_fatal_error($con,"Invalid username or password FOR DATABASE");}
$sql1="INSERT INTO socntwk.msg_followers VALUES(NULL,upper('$author'),'$blogdate',upper('$follower'),'$msgtext',now(),'$blogtitle')";
$result1 = mysqli_query($con,$sql1) or die(mysqli_fatal_error($con,"Could not insert the record"));
mysqli_close($con);
$blogtitleflag = $authorflag = $msgtextflag = $blogdateflag = $followerflag =false;
if(!isset($_SESSION)) session_start();
$follower=$uname;
$_SESSION['view']=$follower;
$_SESSION['uname']=$follower;
header('Location:myblog.php');
exit();
} // successfuly posted
elseif
($blogtitleflag  AND $msgtextflag){ // all data are in valid format 
echo "all flags are true FOR UPDATING THE the blog";
mysqli_select_db($con,'socntwk')  or die("Unable to select database: " . mysqli_error($con));
if (!$con) {mysqli_fatal_error($con,"Invalid username or password FOR DATABASE");}
$sql1="UPDATE socntwk.thread_messages SET user_thread_txt='$msgtext' WHERE upper(user_thread_author)=upper('$author')  and user_thread_date='$blogdate' and blog_title='$blogtitle'";
$result1 = mysqli_query($con,$sql1) or die(mysqli_fatal_error($con,"Could not insert the record"));
mysqli_close($con);
$blogtitleflag = $authorflag = $msgtextflag = $blogdateflag = $followerflag =false;
if(!isset($_SESSION)) session_start();
$follower=$uname;
$_SESSION['view']=$follower;
$_SESSION['uname']=$follower;
header('Location:myblog.php');
exit();
} // successfuly updated
else
{ // one or more data is not in valid format
echo "one of the data is invalid format";
$blogtitleflag = $authorflag = $msgtextflag = $blogdateflag = $followerflag=false;
if(!isset($_SESSION))  session_start();
$follower=$uname;
$_SESSION['msgtextErr']=$msgtextErr;
$_SESSION['view']= $author;
$_SESSION['uname']=$follower;
header('Location:myblog.php');
exit();
}
?>
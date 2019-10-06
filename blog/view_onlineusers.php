<?php // view_onlineusers.php
include_once 'header.php';
if(!$loggedin){
header('Location:login.php');
exit();
$error="";
$userstr="";
$uname="";
}
else
{
if(isset($_GET['view']))
{
$view=$_GET['view'];
echo $uname;
echo $view;
}
if($view==$uname)
{
header("Location:myblog.php");
exit();
}
else
{
echo "entering chat view profile";
header("Location:viewprofile.php?profile=$view&chatview=$view");
exit();
}
}

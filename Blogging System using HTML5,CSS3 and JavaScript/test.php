<?php
if(!isset($_SESSION)) session_start();
if(isset($_SESSION['uname'])){
$uname= $_SESSION['uname'];
$loggedin=TRUE;
$userstr=" ($uname)";
}
else
{
$loggedin=FALSE;
$error="";
$userstr="";
$uname="";
}
//$name = date('YmdHis');
$filename="photos/".$uname.".jpg";
$result = file_put_contents( $filename, file_get_contents('php://input') );
if (!$result) 
{
print "ERROR: Failed to write data to $filename, check permissions\n";
exit();
}
$url = 'http://' . $_SERVER['HTTP_HOST'] . dirname($_SERVER['REQUEST_URI']) . '/' . $filename;
print "$url\n";
?>

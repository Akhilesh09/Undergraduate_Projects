<html>
<body style="background-color:Orange   ;">
<?php

$target_dir = "/wamp64/www/";
$target_file = $target_dir.'lab.txt';
$uploadOk = 1;
$imageFileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));

set_time_limit(10);
$c=$_POST["number"];
if(is_numeric($c))
{
if (($socket = socket_create(AF_INET, SOCK_STREAM, 0)) === false) {
    die("Could not create socket\n");
}
 
if (($connection = socket_connect($socket, "127.0.0.1", 25000)) === false) {
    die("Could not connect to server\n");
}
        
socket_write($socket,$c, strlen($c)); // ? use a larger value
// TODO Error test the read and write operations
socket_close($socket);
}
header('Location: index.php');
?>
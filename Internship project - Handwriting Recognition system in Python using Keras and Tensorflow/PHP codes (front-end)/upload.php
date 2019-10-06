<!DOCTYPE html>
<html>
<body style="background-color:Cyan  ;">
<form action="upload.php" method="post" enctype="multipart/form-data">
<center>
<h2 style="color:Blue  ;">
 <b><i>  
<?php
$target_dir = "/wamp64/www/";
$target_file = $target_dir.'img.png';
$uploadOk = 1;
$imageFileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));
// Check if image file is a actual image or fake image
if(isset($_POST["submit"])) {
    $check = getimagesize($_FILES["image"]["tmp_name"]);
    if($check !== false) {
        //echo "File is an image - " . $check["mime"] . ".";
        $uploadOk = 1;
    } else {
        echo "File is not an image.";
        $uploadOk = 0;
    }
}
// Check if file already exists
/*if (file_exists($target_file)) {
    echo "Sorry, file already exists. <br>";
	$z=basename($_FILES["image"]["name"]).'<br>';
	echo $z;
	//$a='<img src="'.$z.'"/>';
	//echo $a.'<br>';
	echo "<img src='".$z."'>";
    $uploadOk = 0;
}*/
// Check file size
if ($_FILES["image"]["size"] > 500000) {
    echo "Sorry, your file is too large.";
    $uploadOk = 0;
}
// Allow certain file formats
if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg"
&& $imageFileType != "gif" ) {
    echo "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
    $uploadOk = 0;
}
// Check if $uploadOk is set to 0 by an error
if ($uploadOk == 0) {
    echo "Sorry, your file was not uploaded.";
// if everything is ok, try to upload file
} else {
    if (move_uploaded_file($_FILES["image"]["tmp_name"], $target_file)) {
        echo "Your file has been uploaded.";//. basename( $_FILES["image"]["name"]). " 
    } else {
        echo "Sorry, there was an error uploading your file.";
    }
}
set_time_limit(20);




if($uploadOk==1)
{
 
if (($socket = socket_create(AF_INET, SOCK_STREAM, 0)) === false) {
    die("Could not create socket\n");
}
 
if (($connection = socket_connect($socket, "127.0.0.1", 35000)) === false) {
    die("Could not connect to server\n");
}
$data = fopen($target_file, 'rb'); 
    while (!feof($data)) {
		$x=fread($data, 128);
        socket_write($socket,$x, strlen($x)); // ? use a larger value
        // TODO Error test the read and write operations
    }
fclose($data);
socket_close($socket);
$address = '127.0.0.1';
$port = 10000;
// Create a TCP Stream socket
$sock = socket_create(AF_INET, SOCK_STREAM, 0); // 0 for  SQL_TCP
// Bind the socket to an address/port
socket_bind($sock, 0, $port) or die('Could not bind to address');  //0 for localhost
// Start listening for connections
socket_listen($sock);
//loop and listen
/* Accept incoming  requests and handle them as child processes */
$client =  socket_accept($sock);
// Read the input  from the client – 1024000 bytes
$y =  socket_read($client, 1024000);
// Strip all white  spaces from input
socket_close($client);
// Close the r sockets
socket_close($sock);
echo "<br>".$y;	
echo "<br> <img src='img.png' width=400 height=400>";
}
else
	header('Location: index.php');


?>
</i></b></center></h2>
</form>
<form>
<center>
<a href="index.php">If correct click here</a></center>
</form>
<form action="update.php" method="post">
<center>
<h3 style="color:Indigo;">
 <b><i> Enter correct number if wrong. </i></b></center></h3>
 <h2 style="color:DarkRed ;">
  <center> <input type="text" name="number"></center></h2>
  <center> <input type="submit" value="Update" name="submit"></center>
  </center>
</form>
</body>
</html>

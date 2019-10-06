<?php
set_time_limit(5);
 
if (($socket = socket_create(AF_INET, SOCK_STREAM, 0)) === false) {
    die("Could not create socket\n");
}
 
if (($connection = socket_connect($socket, "127.0.0.1", 9000)) === false) {
    die("Could not connect to server\n");
}
$data = fopen("3.png", 'rb'); 
    while (!feof($data)) {
		$x=fread($data, 128);
        socket_write($socket,$x, strlen($x)); // ? use a larger value
        // TODO Error test the read and write operations
    }
    fclose($data);
socket_close($socket);
?>
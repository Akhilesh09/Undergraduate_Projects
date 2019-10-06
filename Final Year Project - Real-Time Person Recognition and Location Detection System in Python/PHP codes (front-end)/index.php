<!DOCTYPE html>
<html>
<head>
	<link href='https://fonts.googleapis.com/css?family=Lato:300,400,700,900' rel='stylesheet' type='text/css'>
<style>
table {
	display:none;
}

.container{
	display: grid;
	grid-template-columns: auto auto;
	width: 60%;
	margin: auto;
}

.list-of-people{
background-color: #EDECEC;
padding: 10px;
display: grid;
grid-template-columns: auto auto;
width: 60%;
margin: auto;
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 5px;
  overflow: hidden;
  background-color: #333;
  height: 20%;
  text-align: center;
}

.person,list-of-people{
background-color: #EDECEC;
width: 25%;
margin: auto;
}

h2,h3,p{
	font-family: 'Lato', sans-serif;
	font-weight: 400;
	color: #000;
	letter-spacing:2px; 
}

#name{
	width: 70%;
	margin: auto;
	height: 29px;		
}

.btn, .btn:link, .btn:visited {
  margin-top: 3%;
  background-color: #1263a1;
  color: #fff;
  text-transform: uppercase;
  text-decoration: none;
  padding: 20px 40px;
  display: inline-block;
  border-radius: 5px;
  transition: all .2s;
  position: relative;
  font-size: 17px;
  border: none;
  cursor: pointer; 
  }

.btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 1rem 2rem rgba(0, 0, 0, 0.2); }
  .btn:hover::after {
    transform: scaleX(1.4) scaleY(1.6);
    opacity: 0; 
	}

.btn:active, .btn:focus {
  outline: none;
  transform: translateY(-1px);
  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.2); 
  }

ul img{
	position: absolute;
	top: 15px;
	left: 15px;
}

li {
  display: block;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 20 em;
}

li a:hover:not(.active) {
  background-color: #111;
}

.center {
  text-align: center;
  font-size: 44px;
  }

.active {
  background-color: #4CAF50;
}
</style>
</head>
<body>

<ul>
  <li><img src="rnsitlogo.jpg" height="100" width="100"></li>
  <div class = "center"><li>REAL TIME PERSON LOCATION</li></div>		
 <!--  <li><a href="#news">News</a></li>
  <li><a href="#contact">Contact</a></li> -->
  <!-- <li style="float:right"><a class="active" href="#about">About</a></li> -->
</ul>
<br>
<br>
<br>
<br>
<center>
<form method="post" action="index3.php" id="form2">
</form>	
<form method="post" action="index.php" id="form1">
<h3>Enter Name:</h3><input type="text" id="name" name="person" required/> <br>
<center><input type="submit" value="submit" class="btn" id="submit" onclick="clr()"/></center>
<br>

</form>
</center>

<center>
<div>
<?php
$room=array();
$people=array();
$times=array();
if(isset($_POST["person"])&& preg_match("/^[a-zA-Z\s]+$/",$_POST["person"])){
$host = "127.0.0.1";
$port = 12350;
set_time_limit(0);
$socket = socket_create(AF_INET, SOCK_STREAM, 0) or die (" Could not create socket\n ");
$result = socket_connect($socket, $host, $port) or die (" <h2> <b> Could not connect to server\n </b> </h2>");
socket_write($socket, $_POST["person"], strlen($_POST["person"])) or die ("Could not send data to server\n");
$result = socket_read ($socket, 1024) or die (" <h2> <b> Could not read server response\n </b> </h2>");
//echo "Reply From Server  :".$result."<br>";
socket_close($socket);
$name=strtoupper($_POST["person"]);
$i=0;
$j=0;
$fh = fopen("output.txt", 'r') or die(" <h2> <b> Output file not found </b> </h2>"); 
while(!feof($fh))
  {
	  $n= strtoupper(trim(fgets($fh)));
	  while(($line=trim(fgets($fh)))!=false )
	  {
		  if($line=="*")
			  break;
		  $l=explode('at',$line);
		  $x=trim(strtoupper($l[0]));
		  $t=trim($l[1]);
		  if(array_key_exists($x,$people))
			  array_push($people[$x],$n);
		  else
			  $people[$x]=array($n);
		  if(array_key_exists($n,$room))
			  array_push($room[$n],$x);
		  else
			  $room[$n]=array($x);
		  if(array_key_exists($x,$times))
			  array_push($times[$x],$t);
		  else
			  $times[$x]=array($t);
		  
	  }
  }
fclose($fh);
//print_r($room);
//print_r($people);
//print_r($times);
if(array_key_exists($name,$people)){
	echo "<div class=\"person\"><h2>".$_POST["person"]." was found"."</h2>";
	echo "<img src='".strtolower($name).".jpg'></div>";
	$i=0;
	foreach ($people[$name] as $value){ 
		if(count($room[$value])>1)
		{
		echo "<h3> In ".$value." , at ".$times[$name][$i]." along with </h3> ";
		foreach ($room[$value] as $r){ 
		if($r!=$name){
		echo "<div class=\"person\"><p>".$r." at ".$times[$r][array_search($value,array_values($people[$r]))]."</h3> <p>";
		echo "<img src='".strtolower($r).".jpg' alt='".strtolower($r)."'></div>";
		}
		}
		}
		else
		{
			echo "<h3> In ".$value." at ".$times[$name][$i]." along with </h3>";
			echo "<p> Noone else </p>";
		}
		$i=$i+1;
	}
}
else{
	echo "<h2> <b>".$_POST["person"]." was not found in any room"."</b> </h2><br>";
}
}
else if(isset($_POST["person"]))
{
	echo "<h2> <b> Names can only have letters and spaces </b> </h2><br>";
}
?>
</center>
</body>
</html>
<?php //config.php
$dbhost  = '127.0.0.1';    // Unlikely to require changing
$dbname  = 'socntwk'; // Modify these...
$dbuser  = 'root';   // ...variables according
$dbpass  = 'root';    // ...to your installation
$appname = "Blogging Portal"; // ...and preference
$con=mysqli_connect($dbhost, $dbuser, $dbpass,$dbname);
$AVfilelimit=20000000;
$Pfilelimit=200000;
$topbloggercount=3;
$topthreadcount=3;
if (mysqli_connect_errno()) {
echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
class usersOnline 
{
	var $timeout = 600;
	var $count=0;
	var $error;
	var $i = 0;
function usersOnline($con,$onlineuser,$logstatus) 
{
        $x=$this->count_users($con);
		$this->timestamp=time();
		$this->ip=$this->ipCheck();
		if(($onlineuser!="") AND ($logstatus)){
		$this->new_user($con,$onlineuser);
		//echo "new user logged in";
		}
		$this->delete_user($con,$onlineuser,$x);
	 //   $this->purge_user($con,$onlineuser);		
}
	
function ipCheck(){
	/*
	This function will try to find out if user is coming behind proxy server. Why is this important?
	If you have high traffic web site, it might happen that you receive lot of traffic
	from the same proxy server (like AOL). In that case, the script would count them all as 1 user.
	This function tryes to get real IP address.
	Note that getenv() function doesn't work when PHP is running as ISAPI module
	*/
		if (getenv('HTTP_CLIENT_IP')) {
			$ip = getenv('HTTP_CLIENT_IP');
		}
		elseif (getenv('HTTP_X_FORWARDED_FOR')) {
			$ip = getenv('HTTP_X_FORWARDED_FOR');
		}
		elseif (getenv('HTTP_X_FORWARDED')) {
			$ip = getenv('HTTP_X_FORWARDED');
		}
		elseif (getenv('HTTP_FORWARDED_FOR')) {
			$ip = getenv('HTTP_FORWARDED_FOR');
		}
		elseif (getenv('HTTP_FORWARDED')) {
			$ip = getenv('HTTP_FORWARDED');
		}
		else {
		$ip = $_SERVER['REMOTE_ADDR'];
		}
	//	echo $ip ."<br />";
		return $ip;
}
	
function new_user($con,$username){
        $page=$_SERVER['REQUEST_URI'];
		//echo $page;
		$replace=$insert=false;
//		echo $username ."<br />";
		$sql2="SELECT * FROM socntwk.WHOISONLINE WHERE upper(user_name)=upper('$username')";
		$result2=mysqli_query($con,$sql2);
//		echo "No of rows: " . mysqli_num_rows($result2);
		if (mysqli_num_rows($result2)!=0)
		{
//		$replace=mysqli_query($con,"UPDATE socntwk.WHOISONLINE set time = NOW(),page ='$page' where user_name='$username'"); 
		$replace=mysqli_query($con,"UPDATE socntwk.WHOISONLINE set time = NOW() where upper(user_name)=upper('$username')"); 

//		echo "user already existing '$replace'";
		$replace=true;
		}
		else
		{
		// echo "New user being inserted";	
		$insert = mysqli_query($con,"INSERT INTO socntwk.WHOISONLINE VALUES (upper('$username'),NOW(),'$page','$this->ip')");
		}
		if (!$insert and !$replace) {
			$this->error[$this->i] = "Unable to record new visitor\r\n";			
			$this->i++;
		}
}	
function delete_user($con,$username,$dcount) {
      //  echo "delete" . $dcount ."<br />";
        if($dcount!=0){
		$delete = mysqli_query ($con,"delete from socntwk.WHOISONLINE 
		where TIMESTAMPDIFF(MINUTE,time,NOW()) >10");
		if (!$delete) {
			$this->error[$this->i] = "Unable to delete visitors";
			$this->i++;
		}
		}
		else
		$this->i;
}

function purge_user($con,$username) {
      //  echo "purge" . $dcount ."<br />";
		$delete = mysqli_query ($con,"delete from socntwk.WHOISONLINE where upper(user_name)=upper('$username')");
		if (!$delete) {
			$this->error[$this->i] = "Unable to delete visitors";
			$this->i++;
		}
}
	
function count_users($con) {
		if (count($this->error) == 0) {
			$count = mysqli_num_rows(mysqli_query($con,"SELECT DISTINCT upper(user_name) FROM socntwk.WHOISONLINE"));
			//echo "Count <br />" .$count;
			return $count;
		}
}
}//end of class
function mysqli_fatal_error($con,$msg){
$msg2 = mysqli_error($con);
echo <<< _END
We are sorry, but it was not possible to complete
the requested task. The error message we got was:
<p>$msg: $msg2</p>
Please click the back button on your browser
and try again. If you are still having problems,
please <a href="mailto:admin@server.com">email
our administrator</a>. Thank you.
_END;
}

function validateDate($date,$format='DD/MM/YYYY'){
        switch( $format )
        {
            case 'DD-MM-YYYY':
            case 'DD/MM/YYYY':
            list( $d, $m, $y ) = preg_split( '/[-\.\/ ]/', $date );
            break;
            default:
            return TRUE;
        }
        return FALSE;
    }
	
function createTable($con,$name,$query){
    queryMysql($con,"CREATE TABLE IF NOT EXISTS $name($query)");
    echo "Table '$name' created or already exists.<br />";
}

function queryMysql($con,$query){
    $result = mysqli_query($con,$query) or die(mysqli_error($con));
     return $result;
}

function destroySession(){
    $_SESSION=array();
    if (session_id() != "" || isset($_COOKIE[session_name()]))
        setcookie(session_name(), '', time()-2592000, '/');
    session_destroy();
}

function   mysql_fix_string($con,$string){
if(get_magic_quotes_gpc()) $string = stripslashes($string);
return mysqli_real_escape_string($con,$string);
}

function mysql_entities_fix_string($con,$string){
return htmlentities(mysqli_fix_string($con,$string));
}

function sanitizeString($con,$var){
    $var = strip_tags($var);
    $var = htmlentities($var);
    $var = stripslashes($var);
    return mysqli_real_escape_string($con,$var);
}

function test_input($data){
     $data = strip_tags($data);
     $data = htmlentities($data);
     $data = trim($data);
     $data = stripslashes($data);
     $data = htmlspecialchars($data);
     return $data;
}

function displayphoto($con,$myname)
{
$sql5= "SELECT user_photo,user_video,user_audio FROM socntwk.user_profile WHERE upper(user_name)=upper('$myname')";
$result5=mysqli_query($con,$sql5) or die(mysqli_fatal_error($con,"No such User"));
if (mysqli_num_rows($result5)==1){
while($row = mysqli_fetch_array($result5)) 
{
$user_photo = $row['user_photo'];
showphoto($myname,$user_photo);
$user_video = $row['user_video'];
showvideo($myname,$user_video);
$user_audio = $row['user_audio'];
showaudio($myname,$user_audio);
}
}
}
function showphoto($name,$myuserphoto)
{
if (file_exists("$myuserphoto"))
echo "<p class='photo'><img src='$myuserphoto' width='70' height='100' /></p>";
}

function showvideo($name,$myuservideo)
{
if (file_exists("$myuservideo"))
echo "<fieldset><p style='text-align:center'><a href='$myuservideo' target='_blank'>" .$name ."'s profile video</a></p></fieldset>";

/*<p id='video'><video src='$myuservideo' type='video/mp4'  controls='controls'></video></p>";*/
}

function showaudio($name,$myuseraudio)
{
if (file_exists("$myuseraudio"))
echo "<fieldset><p style='text-align:center'><a href='$myuseraudio' target='_blank'>" .$name  ."'s profile audio</a></p></fieldset>";
/*echo "<p id='audio'><audio autoplay='autoplay' controls='controls'> <source src='$myuseraudio' /></audio></p>";*/
}

function showProfile($uname)
{
    if (file_exists("$uname.jpg"))
    echo "<img src='$uname.jpg' align='left' />";
    $result = queryMysql($con,"SELECT * FROM socntwk.USER_MESSAGES WHERE upper(user_name)=upper('$uname') and user_message is not null");
    $num    = mysqli_num_rows($result);
    for ($j=0 ; $j < $num ; ++$j)
    {
     $row = mysqli_fetch_row($result);
     echo "Message-".$j .":" .stripslashes($row[2]) . "<br clear='left' /><br/>";
    }
}

function showmythread($con,$blogid,$userstatus)
{
    $result = queryMysql($con,"SELECT * FROM socntwk.USER_MESSAGES WHERE upper(user_name)=upper('$blogid') and user_message is not null");
    $num= mysqli_num_rows($result);
    if($num <=0) 
	return FALSE;
	else
	{
    for ($j=0; $j < $num ; ++$j)
	{
	$row= mysqli_fetch_row($result);
	$blogauthor=$row[1];
	$blogtext=$row[2];
	$blogmsgdate=$row[3];
	$blogmsgstatus=$row[4];
	$blogtitle=$row[5];
    echo  "Blogger Name: " .$blogauthor ."&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Blog Post Date: ".$blogmsgdate."<br clear='left' />";
    echo  "<fieldset>";
	if($userstatus)
	{
	echo  "<legend>$blogtitle</legend>";
	echo  "My blog: " .$blogtext ."<br clear='left' /><br/>";;
	}
	else
	{
	echo  "<legend>$blogtitle";
	echo  "</legend>";
	echo  "My blog: " .$blogtext ."<br clear='left' /><br/>";;
	}
	$result1 = queryMysql($con,"SELECT * FROM socntwk.blogthread_view WHERE upper(user_author)=upper('$blogid') AND blog_title='$blogtitle' ORDER BY user_thread_date DESC,user_author ASC");
    $num1 = mysqli_num_rows($result1);
    for ($k=0 ; $k < $num1; ++$k)
	{
	$row1 = mysqli_fetch_row($result1);
	echo  "<fieldset>";
 	if($userstatus)
	{
	if ($blogauthor!=$row1[1]){
	echo  "<legend>Follower Name:".$row1[1];
	echo  "</legend>";
	}
	echo  "Follower Response Text: " .$row1[3] ."<br clear='left' /><br/>";;
	}
	else
	{	
    
	echo  "<legend>Follower Name:" .$row1[1]  ."&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Response Post Date: ".$row1[2]."</legend>";
	echo  "Follower response:" .$row1[3]  ."  <br clear='left' /><br/>";
	/*if($userstatus)
	{
		
	echo  "Your Response to follower:<br />";
	echo "<textarea  name='msgtext' rows='10' cols='50'  value='' required  autofocus='true'></textarea><br />";
    echo "<input  type='submit' name='submit' value='Submit' />";
	}*/
	
	}
	/*if($userstatus)
	{
		
	echo  "Your Response to follower:<br />";
	echo "<textarea  name='msgtext' rows='10' cols='50'  value='' required  autofocus='true'></textarea><br />";
    echo "<input  type='submit' name='submit' value='Submit' />";
	}*/
	echo  "</fieldset>";
	}
	echo  "</fieldset>";
	}
	}
	
		return $userstatus;
}

/*******************************************/
function quote($strText){
$Mstr = addslashes($strText);
return "'" . $Mstr . "'";
}

function isdate($d)
{
$ret = true;
try{
$x = date("d",$d);
}
catch (Exception $e){
$ret = false;
}
//echo $x;
return $ret;
}
/************************************/
function uploadfile($mypath,$myname,$filetype,$myfilelimit){
//echo $filetype."<br/>";
//echo $mypath ."<br/>";
if($filetype=="P"){
$allowedExts = array("gif","jpeg","jpg","png","tiff","bmp","jpe","ico","tif","svg","svgz");
$myfilesize=$myfilelimit;
//echo $myfilesize ."<br/>";
}
if($filetype=="V"){
$allowedExts = array("rm","mov","mkv","wmv","avi","mpeg","mpg","ogg","ogv","ogm","mp4","flv","3g2","3gp","swf");
$myfilesize=$myfilelimit;
//echo $myfilesize ."<br/>";
}

if($filetype=="A"){
$allowedExts = array("mp3","mp2","ogg","wma","aif","aiff","qt","aac");
$myfilesize=$myfilelimit;
//echo $myfilesize ."<br/>";
}
$temp = explode(".",$_FILES["file"]["name"]);
$extension = end($temp);
//echo $_FILES["file"]["type"];
//echo $_FILES["file"]["size"];
/********************image types*********************************/
if ((($_FILES["file"]["type"]=="image/gif")
|| ($_FILES["file"]["type"] == "image/bmp")
|| ($_FILES["file"]["type"] == "image/jpeg")
|| ($_FILES["file"]["type"] == "image/tiff")  
|| ($_FILES["file"]["type"] == "image/jpg") 
|| ($_FILES["file"]["type"] == "image/pjpeg") 
|| ($_FILES["file"]["type"] == "image/x-png") 
|| ($_FILES["file"]["type"] == "image/png")
|| ($_FILES["file"]["type"] == "image/vnd.microsoft.icon")
|| ($_FILES["file"]["type"] == "image/svg+xml")
|| ($_FILES["file"]["type"] == "video/rm")
|| ($_FILES["file"]["type"] == "video/mov")
|| ($_FILES["file"]["type"] == "video/mkv")
|| ($_FILES["file"]["type"] == "video/wmv")
|| ($_FILES["file"]["type"] == "video/avi")   
|| ($_FILES["file"]["type"] == "video/mpeg")
|| ($_FILES["file"]["type"] == "video/mpg")
|| ($_FILES["file"]["type"] == "video/ogg")
|| ($_FILES["file"]["type"] == "video/ogm")
|| ($_FILES["file"]["type"] == "video/ogv")    
|| ($_FILES["file"]["type"] == "video/mp4")
|| ($_FILES["file"]["type"] == "video/x-ms-wmv")
|| ($_FILES["file"]["type"] == "video/quicktime")
|| ($_FILES["file"]["type"] == "video/msvideo")
|| ($_FILES["file"]["type"] == "video/x-flv")
|| ($_FILES["file"]["type"] == "video/3gpp2")
|| ($_FILES["file"]["type"] == "video/3gpp")
|| ($_FILES["file"]["type"] == "application/x-shockwave-flash")
|| ($_FILES["file"]["type"] == "audio/mp3")
|| ($_FILES["file"]["type"] == "audio/mp2")
|| ($_FILES["file"]["type"] == "audio/mpeg")
|| ($_FILES["file"]["type"] == "audio/mpeg2")
|| ($_FILES["file"]["type"] == "audio/mpeg3")
|| ($_FILES["file"]["type"] == "audio/wav")
|| ($_FILES["file"]["type"] == "audio/aiff")
|| ($_FILES["file"]["type"] == "audio/ogg")
|| ($_FILES["file"]["type"] == "audio/quicktime")
|| ($_FILES["file"]["type"] == "audio/wma")
|| ($_FILES["file"]["type"] == "audio/x-aac"))
&& ($_FILES["file"]["size"] < $myfilesize) && in_array($extension,$allowedExts))
{
  if ($_FILES["file"]["error"] > 0)
    {
    return "ERROR";
    }
  else
    {
	$saveto = $mypath .$myname .".".$extension;
// echo "<br/> photo to be saved as:".$saveto;
// echo "Upload: " . $_FILES["file"]["name"] . "<br>";
// echo "Type: " . $_FILES["file"]["type"] . "<br>";
 ////echo "Size: " . ($_FILES["file"]["size"] / 1024) . " kB<br/>";
 //echo "Temp file: " . $_FILES["file"]["tmp_name"] . "<br>";
    if (file_exists("$mypath" . $_FILES["file"]["name"]))
      {
      move_uploaded_file($_FILES["file"]["tmp_name"],$saveto);
	  return $saveto;
      }
    else
      {
      move_uploaded_file($_FILES["file"]["tmp_name"],$saveto);
      return $saveto;
      }
    }
  }
else
  {
  return "INVALID";
  }
}



function uploadphoto($mypath,$myname)
{
$allowedExts = array("gif", "jpeg", "jpg", "png");
$temp = explode(".",$_FILES["file"]["name"]);
$extension = end($temp);
echo "<br/> photo to be saved as:".$saveto;
if ((($_FILES["file"]["type"] == "image/gif")
|| ($_FILES["file"]["type"] == "image/jpeg")
|| ($_FILES["file"]["type"] == "image/jpg")
|| ($_FILES["file"]["type"] == "image/pjpeg")
|| ($_FILES["file"]["type"] == "image/x-png")
|| ($_FILES["file"]["type"] == "image/png"))
&& ($_FILES["file"]["size"] < 50000)
&& in_array($extension, $allowedExts))
  {
  if ($_FILES["file"]["error"] > 0)
    {
    return "ERROR";
    }
  else
    {
	$saveto = $mypath .$myname .".".$extension;
//    echo "Upload: " . $_FILES["file"]["name"] . "<br>";
//    echo "Type: " . $_FILES["file"]["type"] . "<br>";
//    echo "Size: " . ($_FILES["file"]["size"] / 1024) . " kB<br/>";
//    echo "Temp file: " . $_FILES["file"]["tmp_name"] . "<br>";
    if (file_exists("$mypath" . $_FILES["file"]["name"]))
      {
      move_uploaded_file($_FILES["file"]["tmp_name"],$saveto);
	  return $saveto;
      }
    else
      {
      move_uploaded_file($_FILES["file"]["tmp_name"],$saveto);
      return $saveto;
      }
    }
  }
else
  {
  return "INVALID";
  }
}


?>
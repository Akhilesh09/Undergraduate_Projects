<?php // header.php
if(!isset($_SESSION)) 
session_start();
include 'config.php';
$userstr = ' (Guest)';
if(isset($_SESSION['uname']))
{
$uname= $_SESSION['uname'];
$loggedin = TRUE;
$userstr  = strtoupper(" -    $uname  ");
}
else
{
$loggedin=FALSE;
$error="";
$userstr="";
$uname="";
}
?>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="css/app.css" media="all" type="text/css" />
<script type="text/javascript" src="js/webcam.js"></script>
<?php
#$x="\blog\photos\\".strtolower($uname).".jpg"; <img src='$x' width='40'/>
echo "<title>$appname$userstr</title></head><body><div class='appname'>$appname$userstr</div><header>";
if ($loggedin)
{
?>
<div class="hmenu">
<ul>
<li><a href="myblog.php">My Blog</a></li>
<li><a href="postmessage.php">New Blog</a></li>
<li><a href="members.php">Members</a></li>	
<li><a href="viewfriends.php">Followers</a></li>
<li><a href="editprofile.php">Edit Profile</a></li>
<li><a href="webcam.php">Webcam</a></li>
<li><a href="logout.php">Log out</a></li>
</ul>
</div>
<?php		 
}
else
{
?>
<div class="hmenu">
<ul>
<li><a href="index.php">Home</a></li>
<li><a href="user_registration.php">Sign up</a></li>
<li><a href="login.php">Login</a></li>
</ul>
</div>
<?php
}
?>
</header>
<div class="bannerbodybox">
<div class="banner">
<div id="vanilla-slideshow-container">
<div id="vanilla-slideshow">
<div class="vanilla-slide"><img src="images/blog1.jpg" width="720" height="280" alt="blog1" /></div>
<div class="vanilla-slide"><img src="images/blog2.jpg" width="720" height="280" alt="blog2" /></div>
<div class="vanilla-slide"><img src="images/blog3.jpg" width="720" height="280" alt="blog3" /></div>		
<div class="vanilla-slide"><img src="images/blog4.jpg" width="720" height="280" alt="blog4" /></div>
<div class="vanilla-slide"><img src="images/blog5.jpg" width="720" height="280" alt="blog5" /></div>
</div>
<div id="vanilla-indicators"></div>
<div id="vanilla-slideshow-previous"><img src="images/arrow-previous.png" alt="slider arrow"></div>
<div id="vanilla-slideshow-next"><img src="images/arrow-next.png" alt="slider arrow"></div>
</div>
<script src="js/vanillaSlideshow.min.js"></script>
<script>
	vanillaSlideshow.init({
		slideshow: true,
		delay: 3000,
		arrows: true,
		indicators: true,
		random: false,
		animationSpeed: '1s'
	});
</script>
</div>


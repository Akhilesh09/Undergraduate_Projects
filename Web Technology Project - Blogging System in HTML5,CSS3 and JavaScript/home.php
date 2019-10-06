<?php
session_start();
include 'config.php';
$userstr = ' (Guest)';
if (isset($_SESSION['uname'])){
    $uname     = strtoupper($_SESSION['uname']);
    $loggedin = TRUE;
    $userstr  = $uname;
}
else 
$loggedin = FALSE;
?>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/app.css" media="all" type="text/css" />
<script type="text/javascript" src="js/webcam.js"></script>
<?php
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
<div class="banner"></div>
<div class="mycontentbox"><span class="info">&#8658; You must be logged in to view this page.</span><br />
<?php
if ($loggedin) 
echo " $uname, you are logged in.<br />";
else
{
?>           
<?php
}
echo "<h2>ABOUT SOCIAL NETWORKING ON INTERNET</h2>";
echo "<p>Interest in blogs and blogging has increased dramatically in recent years. Weblogs or blogs can be described as a form of personal, easy–to–manage Web sites with content presented in reverse chronological order. Bloggers are also frequently described as influential agenda setters. For instance, blogs have been found to have influence on media coverage of politics  as well as facilitating communication among individuals and organizations. It follows from these observations that the blog as a form of mediated human expression and blogging as a human activity is of interest to academics from a variety of scientific disciplines. Although research projects interested in various aspects of blogs and blogging are on the rise, few articles have looked at blog research in a cumulative manner. As far as we know, no major review of methodologies, research topics and disciplinary perspectives in blog research seems to have been undertaken</p>";
include_once 'posts.php';
?>
</body></html>
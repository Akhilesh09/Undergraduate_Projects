<?php // logout.php
include_once 'header.php';
if (isset($_SESSION['uname'])){
$visitors_online=new usersOnline($con,$uname,$loggedin);
$visitors_online->purge_user($con,$uname);
destroySession();
$loggedin = FALSE;
echo "<div class='mycontentbox'>You have been logged out. Please " . "<a href='index.php'>click here</a> to refresh the screen.";
}
else 
{
echo "<div class='mycontentbox'><br />" . "You cannot log out because you are not logged in";
}
include_once 'posts.php';
?>
</body></html>
<?php
header('Location:home.php');
exit();
?>

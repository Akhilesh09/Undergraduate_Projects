<?php //index.php
include_once 'header.php';
echo "<div class='mycontentbox'>";
if ($loggedin)
{
$uname=strtoupper($uname);
echo " $uname , you are logged in.";
echo "<h2>ABOUT SOCIAL NETWORKING ON INTERNET</h2>";
echo "<p>Interest in blogs and blogging has increased dramatically in recent years. Weblogs or blogs can be described as a form of personal, easy–to–manage Web sites with content presented in reverse chronological order. Bloggers are also frequently described as influential agenda setters. For instance, blogs have been found to have influence on media coverage of politics  as well as facilitating communication among individuals and organizations. It follows from these observations that the blog as a form of mediated human expression and blogging as a human activity is of interest to academics from a variety of scientific disciplines. Although research projects interested in various aspects of blogs and blogging are on the rise, few articles have looked at blog research in a cumulative manner. As far as we know, no major review of methodologies, research topics and disciplinary perspectives in blog research seems to have been undertaken</p>";
#echo "<p>please sign up and/or log in to join in.</p>";
if(!isset($_SESSION)) session_start();
$_SESSION['uname']= $uname;
}
else
{
echo "<h2>ABOUT SOCIAL NETWORKING ON INTERNET</h2>";
echo "<p>Interest in blogs and blogging has increased dramatically in recent years. Weblogs or blogs can be described as a form of personal, easy–to–manage Web sites with content presented in reverse chronological order. Bloggers are also frequently described as influential agenda setters. For instance, blogs have been found to have influence on media coverage of politics  as well as facilitating communication among individuals and organizations. It follows from these observations that the blog as a form of mediated human expression and blogging as a human activity is of interest to academics from a variety of scientific disciplines. Although research projects interested in various aspects of blogs and blogging are on the rise, few articles have looked at blog research in a cumulative manner. As far as we know, no major review of methodologies, research topics and disciplinary perspectives in blog research seems to have been undertaken</p>";
echo "<p>please sign up and/or log in to join in.</p>";
}
include_once 'posts.php';
?>
</body></html>
<?php // membersblog.php
include_once 'header.php';
if (!$loggedin) {
header('Location:login.php');
exit();
};
echo "<div class='mycontentbox'>";
$view="";
$followstatus=FALSE;
if (isset($_GET['view'])){
$view=sanitizeString($con,$_GET['view']);
$follower=$author="";
$blogauthor=$view;
$author=$follower=$uname;
if($view!=$uname){
//LOGIN USER IS THE FOLLOWER CAN ONLY EDIT THE OWN THREAD  
$follower=$uname;
$author=$view;
if (mysqli_num_rows(queryMysql($con,"SELECT * FROM socntwk.USER_FRIENDS  WHERE user_name='$view' AND friend_name='$uname'"))!=0){
$followstatus=TRUE;
}
else{
$followstatus=FALSE;
}

}
echo "<h3>$view Blog Posts and $uname Thread</h3>";

$status=showmythread($con,$view,$followstatus);
if(($view!=$uname) && ($followstatus==TRUE))
{//login user is a blog follower or thread editor
$result3 = queryMysql($con,"SELECT upper(a.user_name),a.blog_title,a.user_msg_date FROM socntwk.USER_MESSAGES a  WHERE  a.blog_title is not null and upper(a.user_name)=upper('$author') and a.user_message is not null order by a.user_msg_date desc");
$num3= mysqli_num_rows($result3);
// echo "nubmer rows=" .$num3;
for($j=0; $j < $num3; ++$j)
{
$row3= mysqli_fetch_row($result3);
$blogauthor=$row3[0];
$blogtitle=$row3[1];
$blogmsgdate=$row3[2];
$resblogtitle[$j]=$blogtitle;
//echo "blog title=" .$blogtitle;
}
if($status)
{
if (isset($_SESSION['msgtypeErr']))
{
echo $_SESSION['msgtypeErr']; 
unset ($_SESSION['msgtypeErr']);
}
echo "<form name='post_thread' method='post' action='post_thread.php'>";
echo "<label>Blog Title:</label><select name='blogtitle'>";
$lenarray=sizeof($resblogtitle);
for($m=0; $m < $lenarray; $m++)
{ 
echo  "<option value='$resblogtitle[$m]'>$resblogtitle[$m]</option>";
}
echo "</select><br /><br/>";
echo "<fieldset><legend>Post Message</legend><label>New Thread Text:</label><br />"; 
echo "<textarea  name='msgtext' rows='10' cols='50'  value='' required  autofocus='true'></textarea><br />";
echo "<input  type='submit' name='submit' value='Submit' />";
echo "<input  name='author' value='$blogauthor' required  hidden />";
/*echo "<input  name='blogtitle' value='$blogtitle' required  hidden />";*/
echo "<input  name='blogdate' value='$blogmsgdate' required  hidden />";
echo "<input  name='follower' value='$follower' required  hidden />";
echo "</fieldset></form>";
}
else
{
echo $view ." has not created any blog";
}
}
include_once 'posts.php';
echo "</body></html>";
}
?>
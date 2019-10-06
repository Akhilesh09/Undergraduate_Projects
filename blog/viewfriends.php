<?php // viewfriends.php
include_once 'header.php';
if (!$loggedin) die();
//echo "user has logged in";
if (isset($_GET['view'])) 
$view = sanitizeString($con,$_GET['view']);
else                      
$view = $uname;
if ($view==$uname)
{
    $name1 = $name2 = $view;
    $name3 =          "You are";
}
else
{
    $name1 = "<a href='membersblog.php?view=$view'>$view</a>'s";
    $name2 = "$view's";
    $name3 = "$view is";
}
//echo " as $view";
echo "<div class='mycontentbox'><fieldset><legend>Followers List</legend>";
// Uncomment this line if you wish the user's profile to show here
//showProfile($view);
$followers = array();
$following = array();
$result = queryMysql($con,"SELECT * FROM socntwk.user_friends WHERE upper(user_name)=upper('$view')");
$num = mysqli_num_rows($result);
//echo $num;
for ($j=0 ; $j < $num ; ++$j)
{
    $row= mysqli_fetch_row($result);
    $followers[$j] = $row[2];
}
$result = queryMysql($con,"SELECT upper(b.user_fname),upper(b.user_lname),upper(a.user_name) 
FROM socntwk.user_friends a,socntwk.user_profile b 
WHERE upper(a.user_name)=upper(b.user_name) AND upper(a.friend_name)=upper('$view')");
$num = mysqli_num_rows($result);
for ($j=0 ; $j < $num ; ++$j)
{
$row = mysqli_fetch_row($result);
$following[$j]=$row[2];
}
$mutual= array_intersect($followers,$following);
$followers = array_diff($followers, $mutual);
$following = array_diff($following, $mutual);
$friends=FALSE;
if (sizeof($mutual))
{
echo "<fieldset><legend><span class='subhead'>$name2 mutual followers</span></legend><ul style='list-style-type:none'>";

foreach($mutual as $friend)
{
//echo $friend;
$result2 = queryMysql($con,"SELECT upper(user_fname),upper(user_lname) FROM socntwk.user_profile WHERE upper(user_name)=upper('$friend')");
$num2 = mysqli_num_rows($result2);
//echo $num2;
for ($j=0 ; $j < $num2 ; ++$j)
{
    $row2= mysqli_fetch_row($result2);
    $fname = $row2[0];
	$lname = $row2[1];
echo "<li>View   <a href='membersblog.php?view=$friend'>$fname  $lname  Blog</a></li>";
}
$friends = TRUE;
}
echo "</ul></fieldset>";
/******************************************/
}
if (sizeof($followers))
{
echo "<fieldset><legend><span class='subhead'>Your Followers</span></legend><ul style='list-style-type:none'>";
foreach($followers as $friend)
{
$result3 = queryMysql($con,"SELECT upper(user_fname),upper(user_lname) FROM socntwk.user_profile WHERE upper(user_name)=upper('$friend')");
$num3 = mysqli_num_rows($result3);
//echo $num;
for ($j=0 ; $j < $num3 ; ++$j)
{
    $row3= mysqli_fetch_row($result3);
    $fname = $row3[0];
	$lname = $row3[1];
echo "<li><a href='membersblog.php?view=$friend'>$fname $lname</a></li>";
}
$friends = TRUE;
}
echo "</ul></fieldset>";
}
if (sizeof($following))
{
echo "<fieldset><legend><span class='subhead'>$name3 following</span></legend><ul style='list-style-type:none'>";
foreach($following as $friend)
{
$result4 = queryMysql($con,"SELECT upper(user_fname),upper(user_lname) FROM socntwk.user_profile WHERE upper(user_name)=upper('$friend')");
$num4 = mysqli_num_rows($result4);
//echo $num;
for ($j=0 ; $j < $num4 ; ++$j)
{
    $row4= mysqli_fetch_row($result4);
    $fname = $row4[0];
	$lname = $row4[1];
echo "<li><a href='membersblog.php?view=$friend'>$fname $lname</a></li>";
}
$friends = TRUE;
}
echo "</ul></fieldset>";
}
if (!$friends) echo "<br />You don't have any friends yet.<br /><br />";
//echo "<a class='button' href='viewmessages.php?view=$view'>" .
//     "View $name2 Blog</a>";
?>
</fieldset>

<?php
include_once 'posts.php';
?>
</body></html>
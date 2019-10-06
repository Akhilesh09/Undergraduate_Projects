<?php // members.php
include_once 'header.php';
if (!$loggedin) {
header('Location:login.php');
exit();
};
echo "<div class='mycontentbox'>";
if (isset($_GET['add'])){
$add=sanitizeString($con,$_GET['add']);
if (!mysqli_num_rows(queryMysql($con,"SELECT * FROM socntwk.USER_FRIENDS  WHERE user_name='$add' AND friend_name='$uname'")))
queryMysql($con,"INSERT INTO socntwk.USER_FRIENDS VALUES(null,'$add', '$uname')");
}
elseif (isset($_GET['remove']))
{
$remove = sanitizeString($con,$_GET['remove']);
queryMysql($con,"DELETE FROM socntwk.USER_FRIENDS WHERE upper(user_name)=('$remove') AND friend_name='$uname'");
}
$result = queryMysql($con,"SELECT * FROM socntwk.user_profile where user_verified='Y' ORDER BY user_name");
$num = mysqli_num_rows($result);
echo "<fieldset><legend>Other Members</legend><ul style='list-style-type:none'>";
for ($j = 0 ; $j < $num ; ++$j)
{
    $row = mysqli_fetch_row($result);
    if($row[1]==$uname) 
	continue;
    echo "<li><a href='membersblog.php?view=$row[1]'>$row[2] $row[3]</a>";
    $follow = "follow";
    $t1 = mysqli_num_rows(queryMysql($con,"SELECT b.user_fname,b.user_lname,a.user_name 
	FROM socntwk.user_profile b,socntwk.user_friends a WHERE a.user_name=b.user_name AND a.user_name='$row[1]' AND a.friend_name='$uname'"));
	$t2 = mysqli_num_rows(queryMysql($con,"SELECT b.user_fname,b.user_lname,a.user_name 
	FROM socntwk.user_profile b,socntwk.user_friends a 
	WHERE a.user_name=b.user_name AND a.user_name='$uname' AND a.friend_name='$row[1]'"));

if (($t1 + $t2) > 1) 
    echo " &harr; is a mutual follower";
    elseif ($t1)  echo " &larr; you are following";
    elseif ($t2){ echo " &rarr; is following you";  $follow = "add"; }
    if (!$t1) 
	echo " [<a href='members.php?add=".$row[1]    . "'>$follow</a>]</li>";
    else      
	echo " [<a href='members.php?remove=".$row[1] . "'>drop</a>]</li>";
}
$uname=$_SESSION['uname']; 
echo $uname;
//$_SESSION['psw']=  $psw;
?>
</ul>
<?php
include_once 'posts.php';
?>
</body></html>
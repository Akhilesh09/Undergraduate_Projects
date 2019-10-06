<?php // myblog.php
include_once 'header.php';
if (!$loggedin) {
header('Location:login.php');
exit();
};
if(isset($_SESSION)) 
$uname=$_SESSION['uname'];
echo "<div class='mycontentbox'>";
$author=$follower=$uname;
//$_SESSION['uname']=$uname;
//LOGIN USER IS THE AUTHOR CAN ONLY EDIT HIS OWN BLOG 
echo "<h3>$uname Blog Posts and Thread</h3>";
$result = queryMysql($con,"SELECT * FROM socntwk.USER_MESSAGES WHERE upper(user_name)=upper('$uname') and user_message is not null order by user_msg_date desc");
$num= mysqli_num_rows($result);
if($num >0)
{
for ($j=0; $j < $num ; ++$j)
{
$row= mysqli_fetch_row($result);
$blogauthor=$row[1];
$blogtext=$row[2];
$blogmsgdate=$row[3];
$blogmsgstatus=$row[4];
$blogtitle=$row[5];
$_SESSION['uname']= $uname;
echo  "Blogger Name: " .$uname ."&nbsp;&nbsp;&nbsp;&nbsp; Blog Post Date: ".$blogmsgdate."<br clear='left' />\n";
echo  "<fieldset>";
echo  "<legend>$blogtitle\n";
echo  "</legend>\n";
echo  "My blog: " .$blogtext ."<br clear='left' /><br/>\n";
$result1 = queryMysql($con,"SELECT * FROM socntwk.blogthread_view WHERE upper(user_author)=upper('$uname') AND blog_title='$blogtitle' ORDER BY user_thread_date DESC,user_author ASC");
$num1 = mysqli_num_rows($result1);
$resfoll[0]="";
$resdate[0]="";
for ($k=0 ; $k < $num1; ++$k)
{
$row1 = mysqli_fetch_row($result1);
echo  "<fieldset>\n";
echo  "<legend>Follower Name:" .$row1[1]  ."&nbsp;&nbsp; Response Post Date: ".$row1[2]."";
echo  "</legend>Follower response:" .$row1[3]  ."  <br clear='left' /><br/>\n";
$resarray[$k]= $row1[1];
$resdate[$k]= $row1[2];
}
$resblogtitle[$j]=$blogtitle;
echo  "</fieldset>";
}
echo  "</fieldset>";
echo "<form name='mythreadresponse' method='post' action='threadresponse.php'>\n";
$lenarray=sizeof($resblogtitle);
echo "<label>Blog Title:</label><select name='blogtitle'>";
for($m=0; $m < $lenarray; $m++)
{ 
echo  "<option value='$resblogtitle[$m]'>$resblogtitle[$m]</option>";
}
echo "</select><br /><br/>";
echo  "<label>Your Response to follower:</label><br />";
echo "<textarea  name='msgtext' rows='10' cols='50'  value='' required  autofocus='true'></textarea><br />";
echo "<span class='error'>";
?>
<?php
if (isset($_SESSION['$msgtextErr']))
{ 
echo $_SESSION['$msgtextErr'] ."</span><br />";
unset ($_SESSION['$msgtextErr']);
}
echo "<label>&nbsp;</label><input  type='submit' name='submit' value='submit' />\n</form>\n";
}
include_once 'posts.php';
?>
</body></html>
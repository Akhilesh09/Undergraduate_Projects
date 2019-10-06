<?php
echo "<aside class='asidebox'>";
$visitors_online = new usersOnline($con,$uname,$loggedin);
if (count($visitors_online->error)==0) {
$resultonline=queryMysql($con,"SELECT upper(user_name),upper(user_fname), upper(user_lname) FROM socntwk.bloguser_view ORDER BY time DESC");
$numonline = mysqli_num_rows($resultonline);
echo  "<fieldset><legend>Online users</legend>";
for ($k=0 ; $k < $numonline; ++$k)
{
$rowonline = mysqli_fetch_row($resultonline);
$onlineuser=$rowonline[0];  //user name
$onlinefname=$rowonline[1]; //user_fname
$onlinelname=$rowonline[2]; //user_lastname
echo "<p><a href='view_onlineusers.php?view=$onlineuser'>" .$onlinefname ."  " .$onlinelname ."</a></p>";
}
echo  "</fieldset></aside>";
}
else 
{
    echo "<b>Users online class errors:</b><br /><ul>\r\n";
	for ($i = 0; $i < count($visitors_online->error); $i ++ ) {
		echo "<li>" . $visitors_online->error[$i] . "</li>\r\n";
	}
echo "</aside>";
}
?>

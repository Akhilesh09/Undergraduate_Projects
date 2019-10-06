<?php
echo "</div></div><div class='mysidebox'>";
if($loggedin) {include_once 'online_users.php';}
include_once 'most_recent_blog.php';
if($loggedin) {include_once 'most_recent_messagepost.php';}
include_once 'top_bloggers.php';
include_once 'top_threads.php';	
echo "</div>";
include_once 'footer.php';
?>
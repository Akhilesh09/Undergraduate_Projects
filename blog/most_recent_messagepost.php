<?php //most_recent_messagepost.php
echo "<aside class='asidebox'>";
$sql1=queryMysql($con,"select count(b.user_thread_txt) topthreads,upper(a.user_name),upper(a.user_fname),upper(a.user_lname) from socntwk.user_profile a,
socntwk.msg_followers b 
where upper(b.user_follower)=upper(a.user_name) AND upper(b.user_author)=upper('$uname')
group by b.user_follower,a.user_fname,a.user_lname
order by topthreads desc
limit $topthreadcount");
$numtop = mysqli_num_rows($sql1);
echo  "<fieldset><legend>My Blog Followers</legend>";
for ($k=0 ; $k < $numtop; ++$k)
{
$result1 = mysqli_fetch_row($sql1);
$threadcount=$result1[0];
$threaduname=$result1[1];
$threadfname=$result1[2];
$threadlname=$result1[3];
echo "<p><a href='membersblog.php?view=$threaduname'>" .$threadfname ."  " .$threadlname ."(".$threadcount   .")</a></p>";
}
echo  "</fieldset></aside>";
?>

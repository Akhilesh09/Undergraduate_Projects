<?php
echo "<aside class='asidebox'>";
$sql1=queryMysql($con,"select count(b.user_thread_txt) topthreads,upper(a.user_name),upper(a.user_fname),upper(a.user_lname) from socntwk.user_profile a,
socntwk.msg_followers b 
where b.user_follower=a.user_name
group by b.user_follower,a.user_fname,a.user_lname
order by topthreads desc, upper(a.user_name) asc
limit $topthreadcount");
$numtop = mysqli_num_rows($sql1);
echo  "<fieldset><legend>Top followers</legend>";
for ($k=0 ; $k < $numtop; ++$k)
{
$result1 = mysqli_fetch_row($sql1);
$threadcount=$result1[0];
$threaduname=$result1[1];
$threadfname=$result1[2];
$threadlname=$result1[3];
echo "<p>" .$threadfname ." " .$threadlname ."(".$threadcount   .")</p>";
}
echo  "</fieldset></aside>";
?>

<?php //most_recent_blog.php
echo "<aside class='asidebox'>";
$sql1=queryMysql($con,"select upper(a.user_name),upper(a.user_fname),upper(a.user_lname) from socntwk.user_profile a,
socntwk.user_messages b 
where upper(a.user_name)=upper(b.user_name) and STR_TO_DATE(b.user_msg_date,'%Y-%m-%d')=STR_TO_DATE(now(),'%Y-%m-%d')
group by b.user_name,a.user_fname,a.user_lname
order by upper(a.user_name) asc
limit $topthreadcount");
$numtoday = mysqli_num_rows($sql1);
echo  "<fieldset><legend>Today's Bloggers</legend>";
for ($k=0 ; $k < $numtoday; ++$k)
{
$result1 = mysqli_fetch_row($sql1);
$todayuname=$result1[0];
$todayfname=$result1[1];
$todaylname=$result1[2];
echo "<p>" .$todayfname ." " .$todaylname ."</p>";
}
echo  "</fieldset></aside>";
?>

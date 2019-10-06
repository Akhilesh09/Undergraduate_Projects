<?php
echo "<aside class='asidebox'>";
$sql1=queryMysql($con,"select count(b.user_message) topbloggers,upper(a.user_name),upper(a.user_fname),upper(a.user_lname) from socntwk.user_profile a,socntwk.user_messages b  where a.user_name=b.user_name group by a.user_name,a.user_fname,a.user_lname order by topbloggers desc, upper(a.user_name) asc limit $topbloggercount");
$numtop = mysqli_num_rows($sql1);
echo  "<fieldset><legend>Top bloggers</legend>";
for ($k=0 ; $k < $numtop; ++$k)
{
$result1 = mysqli_fetch_row($sql1);
$blogcount=$result1[0];
$bloguname=$result1[1];
$blogfname=$result1[2];
$bloglname=$result1[3];
echo "<p>" .$blogfname ." " .$bloglname ."(".$blogcount   .")</p>";
}
echo  "</fieldset></aside>";
?>

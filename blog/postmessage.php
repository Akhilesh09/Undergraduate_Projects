<?php // postmessage.php
include_once 'header.php';
if (!$loggedin) die();
$button="NONE";
$blogtext=NULL;
$threadtext=NULL;
$threadauthor=NULL;
$threaddate=NULL;
$author=NULL;
$blogdate=NULL;
$blogtitle=NULL;
if($button!="NONE")
{
if (isset($_GET['author'])) 
$author=sanitizeString($con,$_GET['author']);
if (isset($_GET['threadauthor'])) 
$threadauthor=sanitizeString($con,$_GET['threadauthor']);
if (isset($_GET['blogtitle'])){ 
$blogtitle=sanitizeString($con,$_GET['blogtitle']);
$myblogtitle=$_GET['blogtitle'];
echo $blogtitle;
}
if (isset($_GET['threadtext'])) 
$threadtext=sanitizeString($con,$_GET['threadtext']);
if (isset($_GET['blogdate'])) 
$blogdate=sanitizeString($con,$_GET['blogdate']);
if (isset($_GET['threaddate'])) 
$threaddate=sanitizeString($con,$_GET['threaddate']);
$result1 = queryMysql($con,"SELECT user_message,user_msg_status FROM socntwk.user_messages 
WHERE upper(user_name)=upper('$author') 
AND user_msg_date='$blogdate'  
AND blog_title='$blogtitle' 
ORDER BY  user_msg_date DESC");
$num1 = mysqli_num_rows($result1);
if($num1==1){
$row1=mysqli_fetch_row($result1);
$blogtext=$row1[0];
$blogstatus=$row1[1];
$legend="<legend>Edit My Blog</legend>";
if ($blogstatus=='PR') {$msgstatus1="'PR' checked"; $msgstatus2="'PU'";}
else{$msgstatus1="'PR'"; $msgstatus2="'PU' checked";}
}
else
{
$legend="<legend>Edit My Thread Text</legend>";
$blogtext=$threadtext;
//echo $threadauthor;
}
}
else
{
$legend="<legend>Post Message</legend>";
$msgstatus1="'PR' checked";
$msgstatus2="'PU'";
$button="NONE";
}
?>
<div class="mycontentbox">
<form name="post_message" method="post" action="validate_postmsg.php">
<fieldset>
<?php  echo "$legend"; ?>
<label>Blog Title:</label>
<input type="text"  name="blogtitle" value="<?php echo $blogtitle; ?>" size="60" width="120" required  autofocus="true" />
<span class="error">*<?php if(isset($_SESSION['blogtitleErr'])){ echo $_SESSION['blogtitleErr']; unset ($_SESSION['blogtitleErr']);} ?></span><br />
<label>Blog Text:</label> 
<textarea  name="msgtext" rows="10" cols="50"  value="" required  autofocus="true">
<?php echo "$blogtext" ?></textarea>
<span class="error">*<?php if(isset($_SESSION['msgtextErr'])){echo $_SESSION['msgtextErr']; unset ($_SESSION['msgtextErr']);}  ?></span><br />
<?php 
if (($button!="THREADEDIT") AND ($button!="THREADDELETE")){
//echo "<label>Private:</label>";
echo "<input type='radio' name='msgtype' value=$msgstatus1 checked hidden />";
echo "<input type='radio' name='msgtype' value=$msgstatus2 hidden />";
echo "<span class='error'>"; 
?>
<?php 
if(isset($_SESSION['msgtypeErr']))
{
echo $_SESSION['msgtypeErr'];
unset($_SESSION['msgtypeErr']); 
}
echo "</span><br />";
}
else
{
echo "<input type='radio' name='msgtype' value='PR' checked hidden />";
}  
?>
<input type="submit" name="submit" value="Submit" />
<input  type="text" name="<?php echo $button; ?>" value="<?php echo $button; ?>" hidden />
<?php                       
if($button!="NONE")
{
echo "<input  type='text' name='author' value='$author' hidden />"; 
echo "<input  type='text' name='blogtitle' value='$blogtitle' hidden />";
echo "<input  type='text' name='blogdate' value='$blogdate' hidden />";  
echo "<input  type='text' name='threadauthor' value='$threadauthor' hidden />"; 
echo "<input  type='text' name='threaddate' value='$threaddate' hidden />";
}
?>
</fieldset>
</form>
<?php
include_once 'posts.php';
?>
</body>
</html>

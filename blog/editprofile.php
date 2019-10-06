<?php //editprofile.php
include_once 'header.php';
if(!$loggedin){
header('Location:login.php');
exit();
}
$sql1="SELECT DATE_format(user_dob,'%d-%m-%Y') as dob,user_fname,user_lname,user_email,user_mobile,user_phone,user_address from socntwk.user_profile WHERE upper(user_name)=upper('$uname') and user_verified='Y'";
$result1=mysqli_query($con,$sql1) or die(mysqli_fatal_error($con,"No such User"));
if (mysqli_num_rows($result1)==1){
while($row = mysqli_fetch_array($result1)) 
{
$user_fname = $row['user_fname'];
$user_lname = $row['user_lname'];
$user_dob =   $row['dob'];
$user_email = $row['user_email'];
$user_mobile = $row['user_mobile'];
$user_phone = $row['user_phone'];
$user_address = $row['user_address'];
}
echo "<div class='mycontentbox'>";
}
else
{
header('Location:login.php');
exit();
}
?>
<form name="edit_profile" method="post" action="update_profile.php" enctype="multipart/form-data">
<fieldset>
<legend>Edit <?php echo $uname; ?> Profile</legend>
<label>UserName:</label><input id="test1" type="text"  name="uname" value="<?php echo $uname; ?>" readonly required  autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['unameErr'])){echo $_SESSION['unameErr']; unset ($_SESSION['unameErr']);} ?></span><br />
<label>First Name</label><input id="test4" type="text" name="fname" value="<?php echo $user_fname; ?>"  required autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['fnameErr'])){echo $_SESSION['fnameErr']; unset ($_SESSION['fnameErr']);} ?></span><br />
<label>Last Name</label><input id="test5" type="text" name="lname" value="<?php echo $user_lname; ?>"  required />
<span class="error">*<?php if (isset($_SESSION['lnameErr'])){echo $_SESSION['lnameErr']; unset ($_SESSION['lnameErr']); } ?></span><br />
<label>Date of Birth</label><input id="test6" type="text" name="dob" value="<?php echo $user_dob; ?>"  required autofocus="true" />(DD-MM-YYYY format) 
<span class="error">*<?php if (isset($_SESSION['dobErr'])){ echo $_SESSION['dobErr']; unset ($_SESSION['dobErr']); } ?></span><br />
<label>Phone No.</label><input id="test7" type="text" name="phone" value="<?php echo $user_phone; ?>" autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['phoneErr'])){echo $_SESSION['phoneErr']; unset ($_SESSION['phoneErr']); } ?></span><br />
<label>Mobile No.</label><input id="test8" type="text" name="mobile" value="<?php echo $user_mobile; ?>" required  autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['mobileErr'])){echo $_SESSION['mobileErr']; unset ($_SESSION['mobileErr']); } ?></span><br />
<label>E-mail</label><input id="test9" type="text" name="email" value="<?php echo $user_email; ?>" required  autofocus="true" />
<span class="error">* <?php if (isset($_SESSION['emailErr'])){echo $_SESSION['emailErr']; unset ($_SESSION['emailErr']);  } ?></span><br />
<label>Address:</label> <textarea id="test10" name="address" rows="5" cols="40" required  autofocus="true"><?php echo $user_address; ?></textarea>
<span class="error">*<?php if (isset($_SESSION['addressErr'])){echo $_SESSION['addressErr']; unset ($_SESSION['addressErr']);  } ?></span><br />
<label>Image Type</label><input type="radio" name="filetype" value="photos" checked="checked" />&nbsp;&nbsp;Image&nbsp;&nbsp; (gif,jpeg,jpg,png,tiff,bmp,jpe,ico,tif,svg,svgz)<br />
<!--
<label>Video Type</label><input type="radio" name="filetype" value="videos"  />&nbsp;&nbsp;Video&nbsp;&nbsp; (rm,mov,mkv,wmv,avi,mpeg,mpg,ogg,ogv,ogm,mp4,flv,3g2,3gp,swf)<br />
<label>Audio Type</label><input type="radio" name="filetype" value="audios"  />&nbsp;&nbsp;Audio&nbsp;&nbsp; (mp3,mp2,ogg,wma,aif,aiff,qt,aac)<br />
-->
<input id="file" type="file" name="file" size="50" maxlength="50" />
<span class="error"><?php if (isset($_SESSION['fileErr'])){echo $_SESSION['fileErr']; unset ($_SESSION['fileErr']); } ?></span>
<input id="test13" type="submit" name="submit" value="Submit" />
</fieldset>
</form>
<?php  displayphoto($con,$uname); ?>
<?php
include_once 'posts.php';
?>
</body>
</html>
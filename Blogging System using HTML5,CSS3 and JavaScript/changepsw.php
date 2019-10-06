<?php //editprofile.php
include_once 'header.php';
?>
<form name="reset_psw" method="post" action="resetpsw.php">
<fieldset>
<legend>Reset/Change Password<?php echo $uname; ?></legend>
<label>User Name:</label><input type="text"  name="uname" value="<?php echo $uname; ?>" required  autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['unameErr'])){echo $_SESSION['unameErr']; unset ($_SESSION['unameErr']);}  ?></span><br />
<label>Date of Birth</label><input type="text" name="dob" value="<?php if (isset($user_dob)) echo $user_dob; ?>" required  autofocus="true" />(DD-MM-YYYY format) 
<span class="error">*<?php if (isset($_SESSION['dobErr'])){ echo $_SESSION['dobErr']; unset ($_SESSION['dobErr']); } ?></span><br />
<label>Gender:</label><input type="radio" name="gender" value="female" />Female<input id="test12" type="radio" name="gender" value="male" />Male
<span class="error">*<?php if (isset($_SESSION['genderErr'])){echo $_SESSION['cpwErr']; unset ($_SESSION['genderErr']); } ?></span><br />
<label>Mobile No.</label><input type="text" name="mobile" value="<?php if (isset($user_mobile)) echo $user_mobile; ?>" required  autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['mobileErr'])){echo $_SESSION['mobileErr']; unset ($_SESSION['mobileErr']); }  ?></span><br />
<label>E-mail</label><input  type="text" name="email" value="<?php if (isset($user_email))   echo $user_email; ?>" required  autofocus="true" />
<span class="error">* <?php if (isset($_SESSION['emailErr'])){echo $_SESSION['emailErr']; unset ($_SESSION['emailErr']);  }  ?></span><br />
<label>Password:</label><input  type="password"  name="psw"  value=""  required  autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['pswErr'])){echo $_SESSION['pswErr']; unset ($_SESSION['pswErr']);}  ?></span><br />
<label>Retype Password:</label><input  type="password" name="cpw" value=""  required  autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['cpwErr'])){echo $_SESSION['cpwErr']; unset ($_SESSION['cpwErr']);} ?></span><br />
<label>&nbsp;</label><input  type="submit" name="submit" value="Submit" />
</fieldset>
</form>
<?php
include_once 'posts.php';
?>
</body>
</html>
<?php
include_once 'header.php';
?>
<div class="mycontentbox"><h3>Please enter your details to log in</h3>
<form method="post" name="login" action="validate_login.php"><span class="error">
<?php if (isset($_SESSION['error'])) {echo $_SESSION['error']; unset($_SESSION['error']);} ?>
</span>
<fieldset>
<legend></legend>
<label>Username</label><input type="text" maxlength="16" name="uname" value="" required  autofocus="true" />
<span class="error">*
<?php if(isset($_SESSION['unameErr'])){echo $_SESSION['unameErr']; unset ($_SESSION['unameErr']); }?></span>
<br /><br />
<label>Password</label><input type="password"  maxlength="16" name="psw" value="" required  autofocus="true" />
<span class="error">*<?php if(isset($_SESSION['pswErr'])){echo $_SESSION['pswErr']; unset ($_SESSION['pswErr']);} ?></span><br />
<label>&nbsp;</label><a href="changepsw.php" >Reset/change Password</a></p>
<span class="fieldname">&nbsp;</span>
<input type="submit" name="submit" value="Submit">
</fieldset>
</form>
<?php
include_once 'posts.php';
?>
</body>
</html>

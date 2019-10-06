<?php // user_registration.php
include_once 'header.php';
?>
<div class="mycontentbox">
<form name="user_registration" method="post" action="validate_registration.php">
<fieldset>
<legend>User Registration</legend>
<label>UserName:</label><input id="test1" type="text"  name="uname" value=""  required  autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['unameErr'])){echo $_SESSION['unameErr']; unset ($_SESSION['unameErr']);} ?></span><br />
<label>Password:</label><input id="test2" type="password"  name="psw"  value=""  required  autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['pswErr'])){echo $_SESSION['pswErr']; unset ($_SESSION['pswErr']);} ?></span><br />
<label>Retype Password:</label><input id="test3" type="password" name="cpw" value=""  required  autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['cpwErr'])){echo $_SESSION['cpwErr']; unset ($_SESSION['cpwErr']);} ?></span><br />
<label>First Name</label><input id="test4" type="text" name="fname" value="" required  autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['fnameErr'])){echo $_SESSION['fnameErr']; unset ($_SESSION['fnameErr']);} ?></span><br />
<label>Last Name</label><input id="test5" type="text" name="lname" value=""  required  autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['lnameErr'])){echo $_SESSION['lnameErr']; unset ($_SESSION['lnameErr']); }  ?></span><br />
<label>Date of Birth</label><input id="test6" type="text" name="dob"  value="" " required  autofocus="true" /> (DD-MM-YYYY format) 
<span class="error">*<?php if (isset($_SESSION['dobErr'])){ echo $_SESSION['dobErr']; unset ($_SESSION['dobErr']); }  ?></span><br />
<p title=" 8 digit landline no:"><label>Phone No.</label></p><input id="test7" type="text"   name="phone" value="" required autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['phoneErr'])){echo $_SESSION['phoneErr']; unset ($_SESSION['phoneErr']); }  ?></span><br />
<p title=" 10 digit landline no:"><label>Mobile No.</label></p><input id="test8" type="text"   name="mobile" value="" required  autofocus="true" />
<span class="error">*<?php if (isset($_SESSION['mobileErr'])){echo $_SESSION['mobileErr']; unset ($_SESSION['mobileErr']); }  ?></span><br />
<label>E-mail</label><input id="test9" type="text" name="email" value="" required  autofocus="true" />
<span class="error">* <?php if (isset($_SESSION['emailErr'])){echo $_SESSION['emailErr']; unset ($_SESSION['emailErr']);  }  ?></span><br />
<label>Address:</label> <textarea id="test10" name="address" rows="5" cols="40"  value="" required  autofocus="true"></textarea>
<span class="error">*<?php if (isset($_SESSION['addressErr'])){echo $_SESSION['addressErr']; unset ($_SESSION['addressErr']);  }  ?></span><br />
<label>Gender:</label>
<input id="test11" type="radio" name="gender" value="female" />Female
<input id="test12" type="radio" name="gender" value="male" />Male
<span class="error">*<?php if (isset($_SESSION['genderErr'])){echo $_SESSION['genderErr']; unset ($_SESSION['genderErr']); } ?></span><br />
<!--Image: <input id="file" type="file" name="file" size='50' maxlength='50' /> -->
<input id="test13" type="submit" name="submit" value="Submit" />
</fieldset>
</form>
<?php
include_once 'posts.php';
?>
</body>
</html>

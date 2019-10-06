<?php
include 'config.php';
$unameErr=$pswErr = $cpwErr = $dobErr= $mobileErr= $emailErr = $genderErr ="";
$uname=$psw = $cpw = $dob = $mobile = $email = $gender ="";
$unameflag=$pswflag = $cpwflag = $dobflag = $mobileflag= $emailflag = $genderflag=true;
if ($_SERVER["REQUEST_METHOD"] == "POST"){
if(empty($_POST["uname"])){
    $unameErr = "Name is required";
	$unameflag=FALSE;
	}
     else{
     $uname = test_input($_POST["uname"]);
     // check if name only contains letters and whitespace
     if (!preg_match("/^[a-zA-Z0-9]*$/",$uname)){
       $unameErr = "Only letters and numbers allowed";
	   $unameflag=FALSE;
       }
}	 
echo $uname;
echo $unameflag;	 
if (empty($_POST["psw"])){
    $pswErr = "Password is required";
	$pswflag=FALSE;
	}
     else{
     $psw = test_input($_POST["psw"]);
     // check if name only contains letters and whitespace
     if (!preg_match("/^[a-zA-Z0-9$#]*/",$psw)){
       $pswErr = "Only letters numbers and #$ allowed";
	   $pswflag=FALSE;
       }
     }
	 
echo $psw;
echo $pswflag;	 
  if (empty($_POST["cpw"])){
    $cpwErr = "Confirm Password is required";
	$cpwflag=FALSE;
	}
     else{
     $cpw = test_input($_POST["cpw"]);
     // check if name only contains letters and whitespace
     if (!preg_match("/^[a-zA-Z0-9$#]*/",$cpw)){
       $cpwErr = "Only letters numbers and #$ allowed";
	   $cpwflag=FALSE;
       }
     }
echo $cpw;
echo $cpwflag;			 
if($psw!=$cpw){
 $cpwErr=$pswErr="Passwords did not match";
 $cpwflag=$pswflag=FALSE;
}

 if (empty($_POST["dob"])){
    $dobErr = "Date of Birth Required";
	$dobflag=FALSE;
	}
     else{
     $dob = test_input($_POST["dob"]);
     // check if the date is in correct format
      if(!preg_match("/^[0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9]$/",$dob)){
       $dobErr = "Only date in DD-MM-YYYY format allowed";
	   $dobflag=FALSE;
       }
	   if($dobflag==true)
	   {
		   list( $d, $m, $y ) = preg_split( '/-/', $dob );
		  /* if($m<10)
		   {
			   $m=$m/10;
		   }*/
		   if(!(checkdate($m, $d, $y)==1 && $y<=2000))
		   {
		   $dobErr = "Invalid date";
	       $dobflag=FALSE;
		   }
	   }
     }	 
	 
echo $dob;
echo $dobflag;
if (empty($_POST["mobile"])){
    $mobileErr = "Mobile Number is required";
	$mobileflag=FALSE;
	}
  else{
     $mobile = test_input($_POST["mobile"]);
     // check if mobile syntax is valid
     if (!preg_match("/^[0-9]*$/",$mobile)){
	        $mobileErr = "Only numbers allowed";
			$mobileflag=FALSE;
	        }
			if($mobileflag!=FALSE)
		{
		if(strlen($mobile)!=10)
		{
			$mobileErr = "Must be 10 digits";
			$mobileflag=FALSE;
		}
		}
     }	 
echo $mobile;
echo $mobileflag;
 if (empty($_POST["email"])){
  $emailErr = "Email is required"; 
  $emailflag=FALSE;
  }
  else{
     $email = test_input($_POST["email"]);
     // check if e-mail address syntax is valid
     if (!preg_match("/([\w\-]+\@[\w\-]+\.[\w\-]+)/",$email)){
       $emailErr = "Invalid email format";
	   $emailflag=FALSE;
       }
  }
echo $email;
echo $emailflag;
if (empty($_POST["gender"])){
$genderErr = "Gender is required"; 
$genderflag=FALSE;}
else {$gender = test_input($_POST["gender"]);}
echo $gender;
echo $genderflag;
/****************************************************/
if($genderflag and $emailflag and $mobileflag and $dobflag and $cpwflag  and $pswflag  and $unameflag)
{
echo "all flags set";
mysqli_select_db($con,'socntwk')  or die("Unable to select database: " . mysqli_error($con));
if (!$con) {mysqli_fatal_error($con,"Invalid username or password");}
$sql2="SELECT * FROM socntwk.user_profile WHERE upper(user_name)=upper('$uname') AND user_email='$email' AND user_dob=STR_TO_DATE('$dob','%d-%m-%Y') AND user_gender='$gender' and user_mobile=$mobile";
$result2=mysqli_query($con,$sql2);
if (mysqli_num_rows($result2)==1)
{
echo "valid user";
$sql3="update socntwk.blogusers set password='$psw', cpassword='$cpw' where upper(user_name)=upper('$uname')";
$result3 = mysqli_query($con,$sql3) or die(mysqli_fatal_error($con,"Could not update the record"));
mysqli_close($con);
$pswflag = $cpwflag = $dobflag = $mobileflag= $emailflag = $genderflag =false;
$loggedin=FALSE;
if(!isset($_SESSION)) session_start();
$error="Password successfuly changed, try to login now with new password";
$_SESSION['error']=$error;
header('Location:login.php');
exit();
}
else
{// user does not exist
echo "Invalid user";
$unameErr = "Unregistered username or invalid username";
$unameflag=FALSE;
mysqli_close($con);
unset ($_SESSION['uname']);
$loggedin=FALSE;
if(!isset($_SESSION)) session_start();
$_SESSION['unameErr']=$unameErr;
header('Location:changepsw.php');
exit();
}
}
else
{ // one or more data is not in valid format
echo "one of the data is invalid format";
$pswflag = $cpwflag = $dobflag = $mobileflag= $emailflag = $genderflag = false;
$loggedin=FALSE;
unset ($_SESSION['uname']);
if(!isset($_SESSION))  session_start();
$_SESSION['pswErr']= $pswErr;
$_SESSION['cpwErr']= $cpwErr;
$_SESSION['dobErr']=$dobErr;
$_SESSION['mobileErr']=$mobileErr;
$_SESSION['emailErr']=$emailErr;
$_SESSION['genderErr']=$genderErr;
header('Location:changepsw.php');
exit();
}
}
?>
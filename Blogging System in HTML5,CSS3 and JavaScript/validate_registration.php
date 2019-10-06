<?php
include 'config.php';
$unameErr = $fnameErr =$lnameErr = $pswErr = $cpwErr = $dobErr= $phoneErr = $mobileErr= $emailErr = $genderErr = $addressErr =$N="";
$uname = $fname =$lname = $psw = $cpw = $dob =  $phone = $mobile = $email = $gender = $address ="";
$unameflag = $fnameflag =$lnameflag = $pswflag = $cpwflag = $dobflag = $phoneflag = $mobileflag= $emailflag = $genderflag = $addressflag =true;
$uploadstatus=NULL;
if ($_SERVER["REQUEST_METHOD"] == "POST"){
//echo "post method";
if (empty($_POST["uname"])){
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
//echo $uname;	 
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
//echo $psw;	 
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
// echo $cpw;		 
if($psw!=$cpw){
 $cpwErr=$pswErr="Passwords did not match";
 $cpwflag=$pswflag=FALSE;
}

if (empty($_POST["fname"])){
    $fnameErr = "First Name is required";
	$fnameflag=FALSE;
	}
else
	 {
     $fname = test_input($_POST["fname"]);
     // check if name only contains letters and whitespace
     if (!preg_match("/^[a-zA-Z]*$/",$fname)){
       $fnameErr = "Only letters allowed";
	   $fnameflag=FALSE;
       }
     }  
//echo $fname; 
if (empty($_POST["lname"])){
    $lnameErr = "Last Name is required";
	$lnameflag=FALSE;
	}
     else{
     $lname = test_input($_POST["lname"]);
     // check if name only contains letters and whitespace
     if (!preg_match("/^[a-zA-Z]*$/",$lname)){
       $lnameErr = "Only letters with no spaces allowed";
	   $lnameflag=FALSE;
       }
     }
//echo $lname;
 if (empty($_POST["dob"])){
    $dobErr = "Date of Birth Required";
	$dobflag=FALSE;
	}
     else{
     $dob = test_input($_POST["dob"]);
	 //$dob1=$dob;
     // check if the date is in correct format
       if(!preg_match("/^[0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9]$/",$dob)){
       $dobErr = "DD-MM-YYYY only";
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
//echo $dob;
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
//echo $mobile; 
if (empty($_POST["phone"])){ 
    $phoneErr = "Phone Number is required";
	$phoneflag=FALSE;
	}
  else{
     $phone = test_input($_POST["phone"]);
     // check if phone syntax is valid
     if (!preg_match("/^[0-9]*$/",$phone)){
	        $phoneErr = "Only numbers allowed";
			$phoneflag=FALSE;
	    }
		if($phoneflag!=FALSE)
		{
		if(strlen($phone)!=8)
		{
			$phoneErr = "Must be 8 digits";
			$phoneflag=FALSE;
		}
		}
  }
//echo $phone; 
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
//echo $email;   
if (empty($_POST["address"])){
  $addressErr = "Address is required";
  $addressflag=FALSE;
  }
  else{$address = test_input($_POST["address"]);
  $address=mysql_fix_string($con,$address);
  }
 //echo $address;  
if (empty($_POST["gender"])){
$genderErr = "Gender is required"; 
$genderflag=FALSE;}
else {$gender = test_input($_POST["gender"]);}
//echo $gender;
//if($_FILES['file']['name'])
//{
//echo "before uploadphotos<br/>";
//$uploadstatus=uploadphoto("photos/",$uname);
//}  

/****************************************************/
if($genderflag and $addressflag and $emailflag and $phoneflag and $mobileflag and $dobflag and $lnameflag  and $fnameflag  and $cpwflag  and $pswflag  and $unameflag)
{ // all data are in valid format 
//echo "all flags are true";
mysqli_select_db($con,'socntwk')  or die("Unable to select database: " . mysqli_error($con));
if (!$con) {mysqli_fatal_error($con,"Invalid username or password");}
$sql2="SELECT * FROM socntwk.user_profile WHERE UPPER(user_name)=UPPER('$uname') OR (user_email='$email' AND user_dob=STR_TO_DATE('$dob','%d/%m/%Y'))";
$result2=mysqli_query($con,$sql2) or die(mysqli_fatal_error($con,"Could not read the record"));
if(mysqli_num_rows($result2)!=0)
{// user already exists do allow to insert
$unameErr = "This user already exists, Please choose different username";
$unameflag=FALSE;
mysqli_close($con);
unset ($_SESSION['uname']);
$loggedin=FALSE;
if(!isset($_SESSION)) session_start();
$_SESSION['unameErr']=$unameErr;
header('Location:user_registration.php');
exit();
}
else //register this new user
{
if($uploadstatus!="INVALID" AND  $uploadstatus!="ERROR")
$sql3="INSERT INTO socntwk.user_profile VALUES(NULL,upper('$uname'),upper('$fname'),upper('$lname'),STR_TO_DATE('$dob','%d-%m-%Y'),'$gender','$email',$mobile,$phone,'N','$address',NULL,NULL,NULL)";
$result3 = mysqli_query($con,$sql3) or   die(mysqli_fatal_error($con,"Could not insert the record1"));
if ($result3){
$sql4="INSERT INTO socntwk.blogusers VALUES (NULL, upper('$uname'), '$psw', '$cpw',now(), 'N')";
$result4 = mysqli_query($con,$sql4) or  die(mysqli_fatal_error($con,"Could not insert the record2"));
}
mysqli_close($con);
$unameflag = $fnameflag =$lnameflag = $pswflag = $cpwflag = $dobflag = $phoneflag = $mobileflag= $emailflag = $genderflag = $addressflag=false;
$loggedin=FALSE;
if(!isset($_SESSION)) session_start();
$error="You have been successfuly registered, please may login now";
$_SESSION['error']=$error;
header('Location:login.php');
exit();
} // registration of new user completed
}
else
{ // one or more data is not in valid format
//echo "one of the data is invalid format";
$unameflag = $fnameflag =$lnameflag = $pswflag = $cpwflag = $dobflag = $phoneflag = $mobileflag= $emailflag = $genderflag = $addressflag=false;
$loggedin=FALSE;
unset ($_SESSION['uname']);
if(!isset($_SESSION))  session_start();
$_SESSION['unameErr']=$unameErr;
//echo $unameErr;
$_SESSION['fnameErr']=$fnameErr;
//echo $fnameErr;
$_SESSION['lnameErr']=$lnameErr;
$_SESSION['pswErr']= $pswErr;
$_SESSION['cpwErr']= $cpwErr;
$_SESSION['dobErr']=$dobErr;
$_SESSION['phoneErr']= $phoneErr;
$_SESSION['mobileErr']=$mobileErr;
//echo $mobileErr;
$_SESSION['emailErr']=$emailErr;
///echo $emailErr;
$_SESSION['genderErr']=$genderErr;
$_SESSION['addressErr']=$addressErr;
header('Location:user_registration.php');
exit();
}
}
?>
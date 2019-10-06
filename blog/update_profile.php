<?php
include_once 'config.php';
$unameErr =$fnameErr =$lnameErr = $dobErr= $phoneErr = $mobileErr= $emailErr = $addressErr =$N=$fileErr="";
$uname = $fname =$lname = $dob =  $phone = $mobile = $email = $address ="";
$unameflag = $fnameflag =$lnameflag = $dobflag = $phoneflag = $mobileflag= $emailflag = $addressflag =$fileflag=true;
$uploadstatus="jpg";
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
if (empty($_POST["fname"])){
    $fnameErr = "First Name is required";
	$fnameflag=FALSE;
	}
else
	 {
     $fname = test_input($_POST["fname"]);
     // check if name only contains letters and whitespace
     if (!preg_match("/^[a-z A-Z ]*$/ ",$fname)){
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
     if (!preg_match("/^[a-z A-Z ]*$/",$lname)){
       $lnameErr = "Only letters allowed";
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
  else{
  $address = test_input($_POST["address"]);
  $address=mysql_fix_string($con,$address);
  }
 
//echo $address;
//echo $_FILES['file']['name'];
//echo $_FILES['file']['size'] ;
//echo $filesize;  
if($_FILES['file']['name'])
{
$selected_radio=$_POST['filetype'];
if($selected_radio=="photos") {$type="P";$filesize=$Pfilelimit;}
elseif($selected_radio=="videos") {$type="V";$filesize=$AVfilelimit;}
elseif($selected_radio=="audios") {$type="A";$filesize=$AVfilelimit;}
else {$type="N";$filesize=0;}
//echo $selected_radio ."<br/>";
//echo $filesize ."<br/>";
$uploadstatus=uploadfile($selected_radio ."/",$uname,$type,$filesize);
if($uploadstatus!="INVALID" AND  $uploadstatus!="ERROR")
{
mysqli_select_db($con,'socntwk')  or die("Unable to select database: " . mysqli_error($con));
if (!$con) {mysqli_fatal_error($con,"Invalid username or password");}
if($type=="P")
$sql3="UPDATE socntwk.user_profile SET user_photo='$uploadstatus'  where upper(user_name)=upper('$uname')";
elseif($type=="V")
$sql3="UPDATE socntwk.user_profile SET user_video='$uploadstatus'  where upper(user_name)=upper('$uname')";
elseif($type=="A")
$sql3="UPDATE socntwk.user_profile SET user_audio='$uploadstatus'  where upper(user_name)=upper('$uname')";
else
$sql3=NULL;
$result3=mysqli_query($con,$sql3) or die("Unable to update the database: " . mysqli_error($con));
}
else//invalid file upload format or error in uploading
{
$fileflag=false;
$fileErr="Invalid file format or file exceeded the size limit of '$filesize'";
}
//echo $uploadstatus;
}
}  
/****************************************************/
if($addressflag and $emailflag and $phoneflag and $mobileflag and $dobflag and $lnameflag  and $fnameflag and $unameflag and $fileflag)
{ // all data are in valid format 
//echo "all flags are true";
mysqli_select_db($con,'socntwk')  or die("Unable to select database: " . mysqli_error($con));
if (!$con) {mysqli_fatal_error($con,"Invalid username or password");}
$sql1="UPDATE socntwk.user_profile SET user_fname=upper('$fname'), user_lname=upper('$lname'),user_dob=STR_TO_DATE('$dob','%d-%m-%Y'), user_email='$email',user_mobile=$mobile,user_phone=$phone,user_address='$address' WHERE upper(user_name)=upper('$uname')";
// upper(user_name)=upper('$uname') AND upper(user_fname)=upper('$fname') AND upper(user_lname)=upper('$lname')  AND user_dob=STR_TO_DATE('$dob','%d/%m/%Y')"; */
$result1=mysqli_query($con,$sql1) or die("Unable to update the database: " . mysqli_error($con));
mysqli_close($con);
$unameflag = $fnameflag =$lnameflag = $pswflag = $cpwflag = $dobflag = $phoneflag = $mobileflag= $emailflag = $genderflag = $addressflag=false;
if(!isset($_SESSION)) session_start();
header('Location:index.php');
exit();
} 
// profile updated successfully
else  // one or more data is not in valid format
{
//echo "one of the data is invalid format";
$unameflag = $fnameflag =$lnameflag = $dobflag = $phoneflag = $mobileflag= $emailflag = $addressflag=$fileflag=false;
if(!isset($_SESSION))  session_start();
$_SESSION['unameErr']=$unameErr;
$_SESSION['fnameErr']=$fnameErr;
$_SESSION['lnameErr']=$lnameErr;
$_SESSION['dobErr']=$dobErr;
$_SESSION['phoneErr']= $phoneErr;
$_SESSION['mobileErr']=$mobileErr;
$_SESSION['emailErr']=$emailErr;
$_SESSION['addressErr']=$addressErr;
$_SESSION['fileErr']=$fileErr;
header('Location:editprofile.php');
exit();
}
?>
<?php //editprofile.php
include_once 'header.php';
if (!$loggedin) die();
?>
<div class='mycontentbox'>
<table>
<tr>
<td valign=top>
<h3>Photo using laptop webcam</h3>
<script language="JavaScript">
webcam.set_api_url( 'test.php' );
webcam.set_quality(100); // JPEG quality (1 - 100)
webcam.set_shutter_sound( true ); // play shutter click sound
</script>
<!-- Next, write the movie to the page at 320x240 -->
<script language="JavaScript">
document.write( webcam.get_html(300,300,300,300) );
</script>
<!-- Some buttons for controlling things -->
<br/>
<form>
<input type=button value="Configure..." onClick="webcam.configure()">&nbsp;&nbsp;
<input type=button value="Take Snapshot" onClick="take_snapshot()">
</form>
<!-- Code to handle the server response (see test.php) -->
<script language="JavaScript">
webcam.set_hook( 'onComplete', 'my_completion_handler' );		
function take_snapshot() {
// take snapshot and upload to server
document.getElementById('upload_results').innerHTML = '<h1>Uploading...</h1>';
webcam.snap();
}
		
function my_completion_handler(msg) {
			// extract URL out of PHP output
if (msg.match(/(http\:\/\/\S+)/)) {
var image_url = RegExp.$1;
// show JPEG image in page
document.getElementById('upload_results').innerHTML = 
'<h3>---->Snap saved</h3>' + 
//	'<h3>JPEG URL: ' + image_url + '</h3>' + 
'<img src="' + image_url + '">';		
// reset camera for another shot
webcam.reset();
}
else alert("PHP Error: " + msg);
}
</script>
</td>
<td valign=top>
<div id="upload_results" style="background-color:#eee;"></div>
</td>
</tr>
</table>
<?php
include_once 'posts.php';
?>
</body>
</html>
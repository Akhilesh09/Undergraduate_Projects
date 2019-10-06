<?php
if (!isset($_SESSION)) {
session_start();
}
while ($var = each($_SESSION)) {
printf ("Key <b>%s</b> has the value of: <b>%s</b><br>", $var['key'], $var['value']);
}
?>
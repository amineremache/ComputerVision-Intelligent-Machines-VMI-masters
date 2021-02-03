<?php
include ('function.php');
//guys put your python calls here...
$query = $_GET['query'];
//ye variable ka jo karna hai woh karo

//write to origina.txt
$myfile = fopen("original.txt", "w") or die("Unable to open file!");
fwrite($myfile, $query);
fclose($myfile);
//---

//explodes the query and passes the first two words
$pieces = explode(" ", $query);

//this part easily runs the python code and passes all the arguments...good
$result = exec('python firethis.py '); //should be retrieve.py

?>

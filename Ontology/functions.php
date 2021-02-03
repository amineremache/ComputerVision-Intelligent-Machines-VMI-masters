<?php


function display(){



//put the next part in a while loop while taking stuff out from data base

echo "<li><a href=\"#\">link1</a></li>
    <li><a href=\"#\">link2</a></li>
    <li><a href=\"#\">link3</a></li>
    <li><a href=\"#\">link4</a></li>
    <li><a href=\"#\">link5</a></li>";
}



function summary(){

  //this will take care of sumarization and searching
  //after expanded query is generated, we finally exxecute
  //this python function to get the summary
  $output = exec('python showthis.py'); //should be summary.py
  echo utf8_decode(readfile("example.txt"));


}

function emptythefile(){
  $f = @fopen("example.txt", "r+");
if ($f !== false) {
    ftruncate($f, 0);
    fclose($f);

}

}

?>

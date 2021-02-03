
<?php
include 'functions.php';
ini_set('max_execution_time', 300);
$query = $_GET['query'];
$pieces = explode(" ", $query);


 ?>

<html>
<head>
<title>SearchSci</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="/Ontology/scripts/main.js"></script>

</head>
<link href="/Ontology/styles/main.css" type="text/css" rel="stylesheet" />
<body >

<div>
  <br>

<h1>Results for
<?php
echo $query;
?><br></h1>
<br><br>

<div class="aboutpane"><p>
<?php
//display here
//this function glues all the pieces together
summary();
//after display, it empties the text file so that
//file is ready for next result
emptythefile();
 ?>
  <br><br>
</p></div>
</div>
<a class ="spbtn" href="index.php">Home</a>


</body>
</html>

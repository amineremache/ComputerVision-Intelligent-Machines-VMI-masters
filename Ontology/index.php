
<?php
include 'functions.php';
 ?>

<html>
<head>
<title>SearchSci</title>
<script src="/Ontology/scripts/main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>


</head>
<link href="/Ontology/styles/main.css" type="text/css" rel="stylesheet" />
<body >
<div>
<img class="containerlogo" src="http://localhost/Ontology/images/scienceyaya.jpg" alt="error">
</div>
<br><br>
<button class ="myButton" onclick="SearchQuery()">search</button>
<br><br><br>
<br><br><br><br><br>
<br><br><br><br><br><br><br>

<input class="textbox" id="query" type="text">

<br>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<br><br>

<div>
  <br>
<h1>About</h1>
<div class="aboutpane"><p>
  This is a smart website that follows the concepts of semantic web. The website uses an ontology to make the web architecture more smart.<br>
This ontology based website is aimed at helping students with Science related searches. This website uses a comprehensive Ontology to generate more meaning full search results.
<br><br>
</p></div>
</div>
<br>
<div>
  <br>
<h1>Popular</h1>
<div class="aboutpane"><p>
  store some preprocessed summerized content in a database to display here guys<br>
  <div class="list">
    <ul>
<?php

display();
 ?>
 </ul>
 </div>
  <br><br>
</p></div>
</div>

</body>
</html>

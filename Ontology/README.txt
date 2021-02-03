notes about the front end:
1) Open in chrome with 90% magnification (its usually default) for the right view.
2) The "search" button will access the SearchQuery()function in Ontology->scripts->main.js .
 it will read the search query from the search box (id="query") and pass that variable to fire.php where you can write functions
 to do any processing that you need on the $query (call python scripts and what not).
3) in the "popular" division tag, put some preprocessed search query in the data base and show it using display() function.
4) put the folder Ontology in c->xampp->htdocs and to access it just type:localhost/Ontology/
5) SOme notes on python to php on passing variables and vice-versa:
  ---------------------------------------------------------------------------------------------------
   Although netcoder pretty much gave you the answer in his comment, here's an example:

   Python->PHP

   example.py

   import os
   os.system("/usr/bin/php example2.php whatastorymark")
   example2.php

   <?php
   echo $argv[1];

   ?>
   PHP->Python

   <?php
   $item='example';
   $tmp = exec("python testscriptphp.py .$item");
   echo $tmp;

   ?>
   testscriptphp.py

   import sys
   print sys.argv[1]
  --------------------------------------------------------------------------------------------------
6) The files have been integrated. The show.php page will execute the summary() function that takes expanded queries from
   expansion.txt. After the summarization is done, the emptythefile() function will truncate the file from which
   the summary text was being extracted so that it stays blank for the next search.
7) the wikipedia library is very limited and doesnt contain result for all the search queries.
8)List of tested and working queries:
  --Names of scientists in ontology
  --Newton discovered
  --Einstein discovered
  --motion
  --ampere
  --m (not recommended)
  --sec
  --velocity

  9) IMPORTANT! to switch between google and wikipedia search go to summary.py: make the following changes:
    line 45: codew = True(or False)
    line 46: codeg = False(or True)
    -Switch the booleans to change the search method

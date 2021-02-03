function SearchQuery(){
  var query = document.getElementById('query').value;
  alert(query);
  //^for testing purpose

if(query == '')
{
alert ("Please Enter a search query!");
//document.getElementById("titlearea").style.display='none';
}
else{
//this part will send the query to fire.php where yall can work on it and process it
  jQuery.ajax({
           type: "POST",
           url: 'fire.php?&query='+query,
           data: {action: 'post'},
           success: function(output) {
            document.getElementById("query").value='Loading...';
            window.location.href = 'show.php?&query='+query;
            }

  });
}

}

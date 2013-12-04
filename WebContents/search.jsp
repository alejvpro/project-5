<%@ page import="edu.ucla.cs.cs144.SearchResult" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Search Results</title>
		<meta name="viewport" content="width=device-width">
		<link rel="stylesheet" type="text/css" href="style.css" />
		<link rel="stylesheet" type="text/css" href="autosuggest.css" />
		<script type="text/javascript" src="autosuggest.js"></script>
		<script type="text/javascript" src="suggestions.js"></script>
		<script type="text/javascript">
    		window.onload = function () {
    	 	   var oTextbox = new AutoSuggestControl(document.getElementById("txt1"), new GoogleSuggestions());        
    		}
		</script> 
	</head>
    
    
	<body>

		<div id="nav-bar">
			<div id="logo-container">
				<a href="index.html"><img src="images/logo.png" id="logo"></a>
			</div>
			<div id="menu-button">
				Menu
			</div>
			<div id="menu">
				<ul>
					<li><a href="index.html">Home</a></li>
					<li><a href="keywordSearch.html">Search</a></li>
				<ul>
			</div>
		</div>
	
		<div id="content">

			<p>Search a new item below and hit submit!</p>

			<form action="search" method="GET">
  				Keywords: <input type="text" name="q" id="txt1" style="max-width:100%;"><br>
  				<input type="hidden" name="numResultsToSkip" value="0" /> 
  				<input type="hidden" name="numResultsToReturn" value="20" /> 
  				<input type="submit" /> 
			</form>

			<h1>Search Results</h1>

			<%
			SearchResult[] results = (SearchResult[])request.getAttribute("results");
			String range = (String)request.getAttribute("range");
	
			if(results.length == 0)
				out.println("No results found! Please try searching again!");
			else
				out.println("Showing results: " + range);
	
    		for (int i = 0; i < results.length; i++) {
    			SearchResult result = results[i];
        		%>
        		<p>
   					Item: <%= result.getItemId() %>. Name: <%= result.getName() %>. <br>
   					<a href="item?id=<%= result.getItemId() %>">More info</a>
				</p>
        	<%
    		}
			%>

			<br>
			<br>


			<%
			String prev = (String)request.getAttribute("prev") ;
			if(prev != null) {
				out.println("<a href=\"search?" + prev + "\">Previous</a> ");
			}
			%>

			<%
			String next = (String)request.getAttribute("next") ;
			if(next != null) {
				out.println("<a href=\"search?" + next + "\">Next</a>");
			}
			%>

		</div>

		<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
		<script>
			$(document).ready(
			    function(){
			        $("#menu-button").click(function () {
			            $("#menu").toggle();
			        });
			    });
			var $window = $(window);
			$window.resize(
				function() {
			    	if($window.width() >= 600)
			         	$("#menu").show();
			         else
			         	$("#menu").hide();
			    });
		</script>	
	</body>
	    
    
</html>


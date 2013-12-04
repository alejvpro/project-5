<%@ page import="edu.ucla.cs.cs144.SearchResult" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Pay Now</title>
		<meta name="viewport" content="width=device-width">
		<link rel="stylesheet" type="text/css" href="style.css" />
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
			<h1>Pay Now</h1>
			
			<%
			String link = (String)request.getAttribute("link");
			%>

			<form action="<%= link %>" method="POST">
				
				Keywords: <input type="text" name="q" id="txt1">
				<input type="hidden" name="numResultsToSkip" value="0" /> 
				<input type="hidden" name="numResultsToReturn" value="20" /> 
				<input type="submit" /> 
			</form>

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


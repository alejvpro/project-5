<%@ page import="edu.ucla.cs.cs144.SearchResult" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Confirmation</title>
		<meta name="viewport" content="initial-scale=1">
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
			<h1>Confirmation</h1>
			Under construction. <br>
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


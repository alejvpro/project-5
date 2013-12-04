<%@ page import="edu.ucla.cs.cs144.ItemBean" %>
<%@ page import="edu.ucla.cs.cs144.UserBean" %>
<%@ page import="edu.ucla.cs.cs144.BidBean" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Item Result</title>
		<meta name="viewport" content="initial-scale=1">
		<link rel="stylesheet" type="text/css" href="style.css" />
		<style type="text/css">
			#google-map {height: 300px; width: 400px;}
		</style>
		<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
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

			<p>Enter an item ID below to see it's info</p>

			<form action="item" method="GET">
  				Item Id: <input type="text" name="id"><br>
  				<input type="submit" /> 
			</form>

			<h1>Item Info</h1>

			<%
			ItemBean item = (ItemBean)request.getAttribute("item");
			UserBean seller = item.getSeller();
			%>
	
			<p>
				<h2> Item </h2>
   				Id: <%= item.getItemId() %>. <br>
   				Name: <%= item.getName() %>. <br>
   				Location: <%= item.getLocation() %>. <br>
   				Country: <%= item.getCountry() %>. <br>
   				Started: <%= item.getStarted().toString() %>. <br>
   				Ends: <%= item.getEnds().toString() %>. <br>
   			</p>
   	
   			<p>
   				<h2> Description </h2>
   				<%= item.getDescription() %>. <br>
   			</p>
   	
   			<p>
   				<h2> Categories </h2>
   				<% String[] categories = item.getCategories();
    			for (int i = 0; i < categories.length; i++) {
        		%>
   					<%= categories[i] %>.<br>
        		<%
        		}
        		%>
   			
   			</p>
   	
   			<p>
   				<h2> Seller </h2>
   				Id: <%= seller.getUserId() %>. <br>
   				Rating: <%= seller.getRating() %>. <br>
   				Location: <%= item.getLocation() %>. <br>
   				Country: <%= item.getCountry() %>. <br>
   			</p>
   	
   			<p>	
   				<h2> Bidding Info </h2>
   				Currently: <%= item.getCurrently() %>. <br>
   				First Bid: <%= item.getFirstBid() %>. <br>
   				<% if(item.getBuyPrice() != null && !item.getBuyPrice().equals("")) {
   				%>
   					Buy Price: <%= item.getBuyPrice() %>. <br>
   					<a href="pay?id=<%= item.getItemId()%>">Pay Now</a> <br>
   				<%}%>
   			</p>

   			<p>
   				
   				
   				<% BidBean[] bids = item.getBids();
   				if(bids.length > 0) {
   				%>
   					<h2> Bids </h2>
   				<% }
   				for (int i = 0; i < bids.length; i++) {
   					BidBean bid = bids[i];
   					UserBean bidder = bid.getBidder();
   				%>
   				
   				<p>
   					<b>Bid</b><br>
   				
   					Bidder<br>
   					Id: <%= bidder.getUserId() %>. <br>
   					Rating: <%= bidder.getRating() %>. <br>
   					Location: <%= bidder.getLocation() %>. <br>
   					Country: <%= bidder.getCountry() %>. <br>
   				
   					<br>
   				
   					Time: <%= bid.getTime().toString() %>. <br>
   					Amount: <%= bid.getAmount() %>. <br>
   				
   				</p>
   				<%
   				}
   				%>
			</p>

			<h2> Map </h2>
			
			<div id="google-map"></div>

 			<script>
            	var geocoder;
            	var map;
            	function initialize() {
            		geocoder = new google.maps.Geocoder();
            		var latlng = new google.maps.LatLng(-34.397, 150.644);
            		var mapOptions = {
            	    	zoom: 10,
            	    	center: latlng
            		}
            	  	map = new google.maps.Map(document.getElementById('google-map'), mapOptions);
            	  	codeAddress();
            	}

            	function codeAddress() {
            		var address = '<%= item.getLocation() %>';
            	  	geocoder.geocode( { 'address': address}, function(results, status) {
            	    	if (status == google.maps.GeocoderStatus.OK) {
            	      		map.setCenter(results[0].geometry.location);
            	      		var marker = new google.maps.Marker(
            	      			{
            	      		    	map: map,
            	        	 		position: results[0].geometry.location
            	      			});	
            	    	} else {
            	      		document.getElementById('google-map').style.display = 'none';
            	    	}
            	  	});
            	}
            	google.maps.event.addDomListener(window, 'load', initialize);
        	</script>

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



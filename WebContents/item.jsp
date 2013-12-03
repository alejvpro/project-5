<%@ page import="edu.ucla.cs.cs144.ItemBean" %>
<%@ page import="edu.ucla.cs.cs144.UserBean" %>
<%@ page import="edu.ucla.cs.cs144.BidBean" %>

<html>
<head>
    <title>Item result</title>
</head>

<style type="text/css">
#google-map {height: 300px; width: 400px;}
</style>

<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>

<body>

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
			Item <br>
   			Id: <%= item.getItemId() %>. <br>
   			Name: <%= item.getName() %>. <br>
   			Location: <%= item.getLocation() %>. <br>
   			Country: <%= item.getCountry() %>. <br>
   			Started: <%= item.getStarted().toString() %>. <br>
   			Ends: <%= item.getEnds().toString() %>. <br>
   	</p>
   	
   	<p>
   			Description<br>
   			<%= item.getDescription() %>. <br>
   	</p>
   	
   	<p>
   			Categories <br>
   			<% String[] categories = item.getCategories();
    		for (int i = 0; i < categories.length; i++) {
        	%>
   				<%= categories[i] %>.<br>
        	<%
        	}
        	%>
   			
   	</p>
   	
   	<p>
   			Seller <br>
   			Id: <%= seller.getUserId() %>. <br>
   			Rating: <%= seller.getRating() %>. <br>
   			Location: <%= item.getLocation() %>. <br>
   			Country: <%= item.getCountry() %>. <br>
   	</p>
   	
   	<p>	
   			Bidding Info <br>
   			Currently: <%= item.getCurrently() %>. <br>
   			First Bid: <%= item.getFirstBid() %>. <br>
   			Number of Bids: <%= item.getNumberOfBids() %>. <br>
   	</p>

   	<p>
   			Bids <br>
   			<% BidBean[] bids = item.getBids(); 
   			for (int i = 0; i < bids.length; i++) {
   			BidBean bid = bids[i];
   			UserBean bidder = bid.getBidder();
   			%>
   				<p>
   				Bid<br>
   				
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
                  var marker = new google.maps.Marker({
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



	
</body>
</html>

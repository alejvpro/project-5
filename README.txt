Alejandro Veloz
003796497

Jay Singh
603812809



Q1: For which communication(s) do you use the SSL encryption? If you are 
encrypting the communication from (1) to (2) in Figure 2, for example, write 
(1)→(2) in your answer.
(4->5), (5-6)



Q2: How do you ensure that the item was purchased exactly at the Buy_Price of 
that particular item?

When a user accesses an immediately purchasable item's page (item w/ 
Buy_Price), the server creates a session, or accesses a previously existing 
session. The server maintains a HashMap of item prices per each session, 
adding entries for immediately purchasable items. When a user pays for an item,
the server uses the server generated price contained in the HashMap for the 
corresponding item.



Q3: How do you guarantee that the user cannot scroll horizontally?

We set the html "body" element to hide all overflow in the x direction, 
disabling scrolling:

	body {overflow-x:hidden;}

We also have done lots of mobile-friendly CSS to avoid any overflow from 
happening in most cases. The above CSS property is mostly to handle 
unavoidable cases where the browser becomes small enough to "break" the layout
(overlapping elements at around 50px width).



Q4: How do you guarantee that the width of your textbox component(s) can fit 
the screen width of a mobile device? Note: you have to explain "how", and you 
can't simply state that "we use a XXX downloaded from YYY, and it magically 
solve the problem."

First, to be mobile friendly, we first set the viewport to the device's width:
<meta name="viewport" content="width=device-width">

Most text elements wrap naturally to fit the screen, however input elements 
have fixed widths by default. We overcame this by the CSS rule:

	input {max-width:100%;}

We also made the Google maps element responsive to the screen's width and added
a media query to use a different "drop down" menu for smaller screens to 
contain text.
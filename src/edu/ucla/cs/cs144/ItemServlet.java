package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String itemId = request.getParameter("id");
    	
    	String itemXML = AuctionSearchClient.getXMLDataForItemId(itemId);
    	if(itemXML != null && !itemXML.equals(""))
    	{
    		// Create an item based on the XML
    		ItemBean item = BeanParser.processItemXMLString(itemXML);
    		
    		// Check if this item can be purchased immediately
    		String buyPrice = item.getBuyPrice();
    		if(buyPrice != null && buyPrice != "")
    		{
    			// Get the previous session or create a new one
    			HttpSession session = request.getSession(true);
    			
    			// Check if session already has previous items
    			HashMap<String, String> itemPrices = (HashMap<String, String>)session.getAttribute("itemPrices");
    			if (itemPrices == null) 
    			{
    				// Create a new session if neccessary
    				itemPrices = new HashMap<String, String>();
    			}
    			
    			// Add this item's price
				itemPrices.put(item.getItemId(), item.getBuyPrice());
				
				// Set or reset the session's item HashMap
				session.setAttribute("itemPrices", itemPrices);
    		}
    		
    		// Set the current item attribute and forward the request to the view
    		request.setAttribute("item", BeanParser.processItemXMLString(itemXML));
    		request.getRequestDispatcher("/item.jsp").forward(request, response);
    	}
    	else
    	{
    		PrintWriter out = response.getWriter();
    		out.println("<html>");
    		out.println("<body>");
    		out.println("<h1>No results found! Please try searching again</h1>");
    		
    		out.println("<p>Enter an item ID below to see it's info</p>");

    		out.println("<form action=\"item\" method=\"GET\">");
    		out.println("Item Id: <input type=\"text\" name=\"id\"><br>");
    		out.println("<input type=\"submit\" />");
    		out.println("</form>");
    		
    		out.println("</body>");
    		out.println("</html>");	
    	}
    }
}

package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String itemId = request.getParameter("id");
    	
    	String itemXML = AuctionSearchClient.getXMLDataForItemId(itemId);
    	if(itemXML != null && !itemXML.equals(""))
    	{
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

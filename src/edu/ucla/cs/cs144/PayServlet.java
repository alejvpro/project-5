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

public class PayServlet extends HttpServlet implements Servlet {
       
    public PayServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        String itemId = request.getParameter("id");

        // basically it first gets

        HttpSession session = request.getSession(false);

        // check if session variable is null

        if (session == null)
        {
            request.setAttribute("error", "No previous session detected. How did you get here?");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return; 
        }
        else
        {
            HashMap<String, ItemBean> itemPrices = (HashMap<String, ItemBean>)session.getAttribute("itemPrices");

            if (itemPrices == null)
            {
                request.setAttribute("error", "No items previously viewed");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;

            }

            // RIGHT HERE..need to display itemPrices price.?

            ItemBean item = itemPrices.get(itemId);

            if (item == null)
            {
                request.setAttribute("error", "No previous session detected. How did you get here?");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return; 
            }

            // Display normally using itemPRices Price
            // CHECK
            request.setAttribute("link", "https://localhost:8443/eBay/confirmation");
            request.setAttribute("item", item);
            request.getRequestDispatcher("/pay.jsp").forward(request, response);




        }


        // else




    	/*String itemId = request.getParameter("id");
    	
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
    	}*/
    }
}

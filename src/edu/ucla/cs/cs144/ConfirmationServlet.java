package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Date;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConfirmationServlet extends HttpServlet implements Servlet {
       
    public ConfirmationServlet() {}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	// Confirm the connection is secure
    	if(!request.isSecure())
    	{
    		request.setAttribute("error", "This connection was detected to be insecure. Please try again.");
    		request.getRequestDispatcher("/error.jsp").forward(request, response);
    		return;
    	}
    	
    	// Confirm a previous session existed
		HttpSession session = request.getSession(false);
		if(session == null)
		{
			request.setAttribute("error", "No previous session detected. How did you get here?");
    		request.getRequestDispatcher("/error.jsp").forward(request, response);
    		return;
		}
		
		// Confirm the session had items associated with it
		HashMap<String, ItemBean> itemPrices = (HashMap<String, ItemBean>)session.getAttribute("itemPrices");
		if (itemPrices == null) 
		{
			request.setAttribute("error", "No items previously viewed, cannot confirm transaction");
    		request.getRequestDispatcher("/error.jsp").forward(request, response);
    		return;
		}
    	
    	// Get the id and credit card
    	String itemId = request.getParameter("id");
    	String creditCard = request.getParameter("card");
    	
    	// Confirm the requested item exists
    	ItemBean item = itemPrices.get(itemId);
    	if(item == null)
    	{
    		request.setAttribute("error", "No matching item found, please try purchasing again");
    		request.getRequestDispatcher("/error.jsp").forward(request, response);
    		return;
    	}
    	
    	// TODO: Check credit card information
    	
    	// Set attributes for the view and forward the request
    	request.setAttribute("item", item);
    	request.setAttribute("card", creditCard);
    	request.setAttribute("time", new Date().toString());
		request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
    }
}

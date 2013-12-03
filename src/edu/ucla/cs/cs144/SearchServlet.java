package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.URIUtil;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String query = request.getParameter("q");
    	int numResultsToSkip = Integer.valueOf(request.getParameter("numResultsToSkip"));
    	int numResultsToReturn = Integer.valueOf(request.getParameter("numResultsToReturn"));
    	
    	SearchResult results[] = AuctionSearchClient.basicSearch(query, numResultsToSkip, numResultsToReturn);
    	request.setAttribute("results", results);
    	
    	request.setAttribute("range", (numResultsToSkip +1) + " - " + (numResultsToSkip + results.length));
    	
    	if(numResultsToSkip > 0)
    	{
    		int newNumResultsToSkip;
    		
    		if(numResultsToSkip - numResultsToReturn < 0)
    			newNumResultsToSkip = 0;
    		else
    			newNumResultsToSkip = numResultsToSkip - numResultsToReturn;
    		
    		request.setAttribute("prev", "q=" + URIUtil.encodeQuery(query) + 
    				"&numResultsToSkip=" + newNumResultsToSkip +
    				"&numResultsToReturn=" + numResultsToReturn);
    	}
    	
    	if(results.length == numResultsToReturn)
    	{
    		int newNumResultsToSkip = numResultsToSkip + numResultsToReturn;
    		
    		request.setAttribute("next", "q=" + URIUtil.encodeQuery(query) + 
    				"&numResultsToSkip=" + newNumResultsToSkip +
    				"&numResultsToReturn=" + numResultsToReturn);
    	}
    	
    	
    	
    	request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}

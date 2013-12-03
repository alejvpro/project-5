package edu.ucla.cs.cs144;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.URIUtil;

import com.sun.mail.iap.ProtocolException;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String query = request.getParameter("q");
    	
        response.setContentType("text/xml");
    	
    	HttpURLConnection connection = null;
        BufferedReader rd  = null;
        StringBuilder sb = null;
        String line = null;
      
        URL serverAddress = null;
      
        try {
            serverAddress = new URL("http://google.com/complete/search?output=toolbar&q=" + URIUtil.encodeQuery(query));
            //set up out communications stuff
            connection = null;
          
            //Set up the initial connection
            connection = (HttpURLConnection)serverAddress.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);
                      
            connection.connect();
          
            //read the result from the server
            rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
          
            while ((line = rd.readLine()) != null)
            {
                sb.append(line + '\n');
            }
            
            PrintWriter out = response.getWriter();
            out.println(sb.toString());
            out.close();
            return;
                      
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            //close the connection, set all objects to null
            connection.disconnect();
            rd = null;
            sb = null;
            connection = null;
        }
        
        PrintWriter out = response.getWriter();
        out.println("<error></error>");
        out.close();
    }
}

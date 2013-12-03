package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;


public class BeanParser {
	
	static final String columnSeparator = "|*|";
    static DocumentBuilder builder;
    
    static final String[] typeName = {
	"none",
	"Element",
	"Attr",
	"Text",
	"CDATA",
	"EntityRef",
	"Entity",
	"ProcInstr",
	"Comment",
	"Document",
	"DocType",
	"DocFragment",
	"Notation",
    };

    static SimpleDateFormat javaFormat;
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    /* Process item XML in string form
     */
    static ItemBean processItemXMLString(String itemXML) {
    	
    	/* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        Document doc = null;
        try {
        	doc = builder.parse(new InputSource(new StringReader(itemXML)));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on item string");
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        

        /* Initialize date format */
    	javaFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
    	
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed string into DOM document");
        

        /**************************************************************/
        
        // Create our item
        ItemBean itemBean = new ItemBean();
        
        // Get the Item element at the root
        Element itemElement = doc.getDocumentElement();
        
        // Parse the item fields one by one
        itemBean.setItemId(itemElement.getAttribute("ItemID"));
        itemBean.setName(getElementTextByTagNameNR(itemElement, "Name"));
        itemBean.setCategories(parseCategories(getElementsByTagNameNR(itemElement, "Category")));
        itemBean.setCurrently(getElementTextByTagNameNR(itemElement, "Currently"));
        itemBean.setFirstBid(getElementTextByTagNameNR(itemElement, "First_Bid"));
        itemBean.setNumberOfBids(getElementTextByTagNameNR(itemElement, "Number_of_Bids"));
        itemBean.setBids(parseBids(getElementByTagNameNR(itemElement, "Bids")));
        itemBean.setLocation(getElementTextByTagNameNR(itemElement, "Location"));
        itemBean.setCountry(getElementTextByTagNameNR(itemElement, "Country"));
        itemBean.setStarted(createJavaDate(getElementTextByTagNameNR(itemElement, "Started")));
        itemBean.setEnds(createJavaDate(getElementTextByTagNameNR(itemElement, "Ends")));
        itemBean.setSeller(parseUser(getElementByTagNameNR(itemElement, "Seller")));
        itemBean.setDescription(getElementTextByTagNameNR(itemElement, "Description"));
 
        return itemBean;
    }
    
    static BidBean[] parseBids(Element bidsElement)
    {
    	ArrayList<BidBean> bidBeans = new ArrayList<BidBean>();
    	Element[] bidElements = getElementsByTagNameNR(bidsElement, "Bid");
    	for(int i = 0; i < bidElements.length; i ++)
    	{
    		bidBeans.add(parseBid(bidElements[i]));
    	}
    	return bidBeans.toArray(new BidBean[bidBeans.size()]);
    }
    
    static BidBean parseBid(Element bidElement)
    {
    	BidBean bidBean = new BidBean();
    	
    	// Bidder
    	bidBean.setBidder(parseUser(getElementByTagNameNR(bidElement, "Bidder")));
    	
    	// Time
    	bidBean.setTime(createJavaDate(getElementTextByTagNameNR(bidElement, "Time")));
    	
    	// Amount
    	bidBean.setAmount(getElementTextByTagNameNR(bidElement, "Amount"));
    	
    	return bidBean;
    }
    
    static UserBean parseUser(Element userElement)
    {
    	UserBean userBean = new UserBean();
    	
    	// UserId
    	userBean.setUserId(userElement.getAttribute("UserID"));
    	
    	// Rating
    	userBean.setRating(userElement.getAttribute("Rating"));
    	
    	// Location
    	userBean.setLocation(getElementTextByTagNameNR(userElement, "Location"));
    	
    	// Country
    	userBean.setCountry(getElementTextByTagNameNR(userElement, "Country"));
    	
    	return userBean;
    }
    
    static String[] parseCategories(Element[] categoryElements)
    {
        ArrayList<String> categories = new ArrayList<String>();
        for(int i = 0; i < categoryElements.length; i++)
        {
        	categories.add(getElementText(categoryElements[i]));
        }
        
        return categories.toArray(new String[categories.size()]);
    }
    
    static Date createJavaDate(String dateString)
    {
    	Date parsed = new Date();
    	
    	try {
            parsed = javaFormat.parse(dateString);
        }
        catch(ParseException pe) {
            System.out.println("ERROR: Cannot parse \"" + dateString + "\"");
        }
    	
    	return parsed;
    }
    
    public BeanParser() {}

}

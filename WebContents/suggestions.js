
/**
 * Provides suggestions for state names (USA).
 * @class
 * @scope public
 */
function GoogleSuggestions() {}

/**
 * Request suggestions for the given autosuggest control. 
 * @scope protected
 * @param oAutoSuggestControl The autosuggest control to provide suggestions for.
 */
GoogleSuggestions.prototype.requestSuggestions = function (oAutoSuggestControl /*:AutoSuggestControl*/,
                                                          bTypeAhead /*:boolean*/) {
    var aSuggestions = [];
    var sTextboxValue = oAutoSuggestControl.textbox.value;
    
    if (sTextboxValue.length > 0){
    
        // Create an request
        xmlhttp = new XMLHttpRequest();
        
        // Assign a callback function
        xmlhttp.onreadystatechange = function()
        	{
        		if (xmlhttp.readyState==4 && xmlhttp.status==200)
        		{
        			xmlDoc = xmlhttp.responseXML;
        			
        			suggestions = xmlDoc.getElementsByTagName("suggestion");
					for (i = 0; i < suggestions.length; i++)
  					{
  						aSuggestions.push(suggestions[i].getAttribute('data'));
  					}
  					
    				//provide suggestions to the control
    				oAutoSuggestControl.autosuggest(aSuggestions, bTypeAhead);
        		}
        	}
        	
        // Open and send the request
        xmlhttp.open("GET","suggest?q=" + encodeURIComponent(sTextboxValue), true);
		xmlhttp.send();
    }
    else
    {
    	//provide suggestions to the control
    	oAutoSuggestControl.autosuggest(aSuggestions, bTypeAhead);
    }

};

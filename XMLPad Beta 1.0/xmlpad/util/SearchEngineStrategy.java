/*
 * Created on 08/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.util;

import xmlpad.util.searchmeths.SearchAnyText;
import xmlpad.util.searchmeths.SearchAttributeName;
import xmlpad.util.searchmeths.SearchAttributeValue;
import xmlpad.util.searchmeths.SearchComment;
import xmlpad.util.searchmeths.SearchElementName;
import xmlpad.util.searchmeths.SearchElementValue;

/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchEngineStrategy{
	private Search searchStrategy = null;
	private SearchConfig config = null;
	public SearchConfig getConfig() {
		return config;
	}
	public SearchEngineStrategy(SearchConfig config){
		setSearchConfig(config);
	}
	public SearchEngineStrategy(XMLTreeNode node){
		config = new SearchConfig(node,"");
	}
	public void setSearchConfig(SearchConfig config){
		this.config = config;
		if(config.isMatchingElementName()){
			searchStrategy = new SearchElementName(config);
		}else if(config.isMatchingElementValue()){
			searchStrategy = new SearchElementValue(config);
		}else if(config.isMatchingComment()){
			searchStrategy = new SearchComment(config);
		}else if(config.isMatchingAttributeName()){
			searchStrategy = new SearchAttributeName(config);
		}else if(config.isMatchingAttributeValue()){
			searchStrategy = new SearchAttributeValue(config);
		}else if(config.isMatchingAny()){
			searchStrategy = new SearchAnyText(config);
		}
	}
	public Search getSearch(){
		return searchStrategy;
	}
}

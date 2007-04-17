/*
 * Created on 08/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.util;

/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchConfig {
	public static final int STR_CASESENSITIVE = 1;
	public static final int STR_WHOLEWORD = 10;
	public static final int STR_FRAGMENT = 0;
	public static final int MATCHING_TYPE_ELEMENT_NAME = 1;
	public static final int MATCHING_TYPE_ELEMENT_VALUE = 2;
	public static final int MATCHING_TYPE_ATTRIBUTE_NAME = 3;
	public static final int MATCHING_TYPE_ATTRIBUTE_VALUE = 4;
	public static final int MATCHING_TYPE_COMMENT = 5;
	public static final int MATCHING_TYPE_ANY = 6;
	private XMLTreeNode searchFrom = null;
	private int strType = STR_FRAGMENT;
	private int matchingType = MATCHING_TYPE_ELEMENT_NAME;
	protected String searchString = "";
	public SearchConfig(XMLTreeNode searchFromNode, String searchString){
		this.searchFrom = searchFromNode;
		this.searchString = searchString;
	}
	public XMLTreeNode getSearchFromNode(){
		return searchFrom;
	}
	public boolean compare(String value){
		if(isCaseSensitiveWholeWord()){
			return value.equals(searchString);
		}else if(isCaseSensitiveFragment()){
			return value.indexOf(searchString)!=-1;
		}else if(isWholeWordFragment()){
			return value.toLowerCase().equals(searchString.toLowerCase());
		}else if(isFragment()){
			return value.toLowerCase().indexOf(searchString.toLowerCase())!=-1;
		}
		return false;
	}
	public boolean isMatchingElementName(){
		return matchingType==MATCHING_TYPE_ELEMENT_NAME;
	}
	public boolean isMatchingElementValue(){
		return matchingType==MATCHING_TYPE_ELEMENT_VALUE;
	}
	public boolean isMatchingAttributeName(){
		return matchingType==MATCHING_TYPE_ATTRIBUTE_NAME;
	}
	public boolean isMatchingAttributeValue(){
		return matchingType==MATCHING_TYPE_ATTRIBUTE_VALUE;
	}
	public boolean isMatchingComment(){
		return matchingType==MATCHING_TYPE_COMMENT;
	}
	public boolean isMatchingAny(){
		return matchingType==MATCHING_TYPE_ANY;
	}
	public int getMatchingType() {
		return matchingType;
	}
	public void setMatchingType(int matchingType) {
		this.matchingType = matchingType;
	}
	public boolean isCaseSensitiveWholeWord(){
		return strType==STR_CASESENSITIVE+STR_WHOLEWORD;
	}
	public void setCaseSensitiveWholeWord(){
		strType=STR_CASESENSITIVE+STR_WHOLEWORD;
	}
	public boolean isCaseSensitiveFragment(){
		return strType==STR_CASESENSITIVE+STR_FRAGMENT;
	}
	public void setCaseSensitiveFragment(){
		strType=STR_CASESENSITIVE+STR_FRAGMENT;
	}
	public boolean isWholeWordFragment(){
		return strType==STR_WHOLEWORD+STR_FRAGMENT;
	}
	public void setWholeWordFragment(){
		strType=STR_WHOLEWORD+STR_FRAGMENT;
	}
	public boolean isFragment(){
		return strType==STR_FRAGMENT;
	}
	public void setFragment(){
		strType=STR_FRAGMENT;
	}
}

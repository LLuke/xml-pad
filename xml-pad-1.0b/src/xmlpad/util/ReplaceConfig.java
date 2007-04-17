/*
 * Created on 10/04/2007
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
public class ReplaceConfig {
	public static final int REPLACE_ELEMENT_METHOD = 1;
	public static final int REPLACE_PLAIN_TEXT_METHOD = 2;
	public static final int PLAIN_REPLACE_WHOLE_TEXT = 1;
	public static final int PLAIN_REPLACE_MATCHING_TEXT = 2;
	private SearchConfig searchConfig = null;
	protected String replaceString = null;
	protected int replaceMethod = REPLACE_PLAIN_TEXT_METHOD;
	protected int matchingText = PLAIN_REPLACE_MATCHING_TEXT;
	public ReplaceConfig(SearchConfig config){
		this.searchConfig = config;
	}
	public int getMatchingText() {
		return matchingText;
	}
	public void setMatchingText(int matchingText) {
		this.matchingText = matchingText;
	}
	public int getReplaceMethod() {
		return replaceMethod;
	}
	public void setReplaceMethod(int replaceMethod) {
		this.replaceMethod = replaceMethod;
	}
	public SearchConfig getSearchConfig() {
		return searchConfig;
	}
	public void setSearchConfig(SearchConfig searchConfig) {
		this.searchConfig = searchConfig;
	}
	public boolean isReplaceElementMethod(){
		return replaceMethod==REPLACE_ELEMENT_METHOD;
	}
	public boolean isReplacePlainTextMethod(){
		return replaceMethod==REPLACE_PLAIN_TEXT_METHOD;
	}
	public boolean isReplaceTextWholeWordText(){
		return matchingText==PLAIN_REPLACE_WHOLE_TEXT;
	}
	public boolean isReplaceMatchingText(){
		return matchingText==PLAIN_REPLACE_MATCHING_TEXT;
	}
	public String getReplaceString() {
		return replaceString;
	}
	public void setReplaceString(String replaceString) {
		this.replaceString = replaceString;
	}
}

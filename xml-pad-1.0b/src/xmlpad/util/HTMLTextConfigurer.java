/*
 * Created on 09/04/2007
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
class HTMLTextConfigurer implements TextConfigurer{
	private static final String fontCourier = "<FONT face=\"Courier New\" ";
	private static final String fontClose = "</FONT>";
	private static final String closeBracket = ">";
	private static final String fontDarkCyan = "color=darkcyan";
	private static final String fontDarkViolet = "color=darkviolet";
	private static final String fontBlue = "color=blue";
	private static final String fontDarkBlue = "color=darkblue";
	private static final String fontBlack = "color=black";
	private static final String space="&nbsp;&nbsp;&nbsp;&nbsp;";
	public String getCommentText(XMLTreeNode node) {
		return fontCourier+fontDarkBlue+closeBracket+"&lt;!--"+node.getText()+"--&gt;"+fontClose+"<br>";
	}
	public String getEntity(XMLTreeNode node, boolean breakLine, int spaces) {
		return XMLUtil.tabCount(spaces,space)+fontCourier+fontDarkViolet+closeBracket+"&lt;"+
			node.toString()+getInLineAttributeText(node)+"&gt;"+fontClose+(breakLine?"<br>":"");
	}
	public String getEntityClosing(XMLTreeNode node,int spaces){
		return XMLUtil.tabCount(spaces,space)+fontCourier+fontDarkViolet+closeBracket+"&lt;/"+node.toString()+"&gt;"+fontClose+"<br>";
	}
	public String getEntityText(XMLTreeNode node) {
		return fontCourier+fontBlack+closeBracket+node.getText()+fontClose;
	}
	public String getEntityText(XMLTreeNode node, boolean breakLine, int spaces){
		return XMLUtil.tabCount(spaces,space)+fontCourier+fontBlack+closeBracket+node.getText()+fontClose;
	}
	public String getFileText(XMLTreeNode node) {
		return fontCourier+fontDarkViolet+closeBracket+"&lt;"+"?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;"+fontClose+"<br>";
	}
	public String getInLineAttributeText(XMLTreeNode node) {
		return node.getInlineAttributes();
	}
}

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
class PlainTextConfigurer implements TextConfigurer{

	public String getCommentText(XMLTreeNode node) {
		return "<!--"+node.getText()+"-->"+System.getProperty("line.separator");
	}
	public String getEntity(XMLTreeNode node, boolean breakLine, int spaces) {
		return XMLUtil.tabCount(spaces)+"<"+node.toString()+getInLineAttributeText(node)+">"+(breakLine?System.getProperty("line.separator"):"");
	}
	public String getEntityClosing(XMLTreeNode node,int spaces){
		return XMLUtil.tabCount(spaces)+"</"+node.toString()+">"+System.getProperty("line.separator");
	}
	public String getEntityText(XMLTreeNode node) {
		return node.getText();
	}
	public String getEntityText(XMLTreeNode node, boolean breakLine, int spaces){
		return XMLUtil.tabCount(spaces)+node.getText().trim()+(breakLine?System.getProperty("line.separator"):"");
	}
	public String getFileText(XMLTreeNode node) {
		return "<?xml"+node.getInlineAttributes()+"?>"+System.getProperty("line.separator");
	}
	public String getInLineAttributeText(XMLTreeNode node) {
		return node.getInlineAttributes();
	}
}

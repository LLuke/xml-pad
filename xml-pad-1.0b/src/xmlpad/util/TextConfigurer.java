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
public interface TextConfigurer {
	public String getInLineAttributeText(XMLTreeNode node);
	public String getEntity(XMLTreeNode node, boolean breakLine, int spaces);
	public String getEntityClosing(XMLTreeNode node,int spaces);
	public String getEntityText(XMLTreeNode node);
	public String getEntityText(XMLTreeNode node, boolean breakLine, int spaces);
	public String getCommentText(XMLTreeNode node);
	public String getFileText(XMLTreeNode node);
}

/*
 * Created on 05/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.util;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TreeIconRenderer extends DefaultTreeCellRenderer{

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
                        int row, boolean hasFocus) 
    {
        	super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        	if(isChildRoot(value)){
        		setIcon(new ImageIcon(getClass().getResource("/icons/xml-main.gif")));
        	}else if(isEntity(value)){
        		if(leaf) setIcon(new ImageIcon(getClass().getResource("/icons/element.gif")));
        		else setIcon(new ImageIcon(getClass().getResource("/icons/element-forder.gif")));
        	}else if(isRoot(value)){
        		setIcon(new ImageIcon(getClass().getResource("/icons/xml.gif")));
        	}else if(isComment(value)){
        		setIcon(new ImageIcon(getClass().getResource("/icons/xml-tag.gif")));
        	}

        return this;
    }

    protected boolean isRoot(Object value) {
    	XMLTreeNode node = (XMLTreeNode)value;
        return (node.getType()==XMLTreeNode.TYPE_FILE);
    }
    protected boolean isChildRoot(Object value) {
    	XMLTreeNode node = (XMLTreeNode)value;
    	XMLTreeNode parent = (XMLTreeNode)node.getParent();
    	if(parent==null) return false;
        return (parent.getType()==XMLTreeNode.TYPE_FILE);
    }
    protected boolean isEntity(Object value) {
    	XMLTreeNode node = (XMLTreeNode)value;
        return (node.getType()==XMLTreeNode.TYPE_ENTITY);
    }
    protected boolean isComment(Object value) {
    	XMLTreeNode node = (XMLTreeNode)value;
        return (node.getType()==XMLTreeNode.TYPE_COMMENT);
    }


}

/*
 * Created on 06/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.util.searchmeths;

import java.util.ArrayList;

import xmlpad.util.SearchConfig;
import xmlpad.util.SearchEngine;
import xmlpad.util.XMLTreeNode;


/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchElementValue extends SearchEngine{
	public SearchElementValue(SearchConfig config){
		super(config);
	}
	public ArrayList search(){
			if(config.compare(node.getText())){
				addPath(node);
			}
			for(int i=0;i<node.getChildCount();i++){
				XMLTreeNode child = (XMLTreeNode)node.getChildAt(i);
				if(config.compare(child.getText())){
					addPath(child);
				}
				searchNextChild(child);
			}
			return list;
	}
	private void searchNextChild(XMLTreeNode node){
		for(int i=0;i<node.getChildCount();i++){
			XMLTreeNode child = (XMLTreeNode)node.getChildAt(i);
			if(config.compare(child.getText())){
				addPath(child);
			}
			searchNextChild(child);
		}
	}
	
}

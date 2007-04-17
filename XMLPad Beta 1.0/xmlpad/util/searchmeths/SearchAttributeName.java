/*
 * Created on 06/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.util.searchmeths;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import xmlpad.util.SearchConfig;
import xmlpad.util.SearchEngine;
import xmlpad.util.XMLTreeNode;


/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchAttributeName extends SearchEngine{
	public SearchAttributeName(SearchConfig config){
		super(config);
	}
	public ArrayList search(){
			Set set = node.getAttributeName();
			Iterator itr = set.iterator();
			while(itr.hasNext()){
				String name = (String)itr.next();
				if(config.compare(name)){
					node.setSelectedAttName(name);
					addPath(node);
				}
			}
			for(int i=0;i<node.getChildCount();i++){
				XMLTreeNode child = (XMLTreeNode)node.getChildAt(i);
				set = child.getAttributeName();
				itr = set.iterator();
				while(itr.hasNext()){
					String name = (String)itr.next();
					if(config.compare(name)){
						child.setSelectedAttName(name);
						addPath(child);
					}
				}
				searchNextChild(child);
			}
			return list;
	}
	private void searchNextChild(XMLTreeNode node){
		for(int i=0;i<node.getChildCount();i++){
			XMLTreeNode child = (XMLTreeNode)node.getChildAt(i);
			Set set = child.getAttributeName();
			Iterator itr = set.iterator();
			while(itr.hasNext()){
				String name = (String)itr.next();
				if(config.compare(name)){
					child.setSelectedAttName(name);
					addPath(child);
				}
			}
			searchNextChild(child);
		}
	}
	
}

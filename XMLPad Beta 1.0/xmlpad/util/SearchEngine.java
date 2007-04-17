/*
 * Created on 08/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.util;

import java.util.ArrayList;

import javax.swing.tree.TreePath;


/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class SearchEngine implements Search{
	protected SearchConfig config = null;
	protected XMLTreeNode node = null;
	protected ArrayList list = null;
	public SearchEngine(SearchConfig config){
		setSearchConfig(config);
		node = config.getSearchFromNode();
		list = new ArrayList();
	}
	/*protected boolean compare(String value){
		if(config.isCaseSensitiveWholeWord()){
			return value.equals(config.searchString);
		}else if(config.isCaseSensitiveFragment()){
			return value.indexOf(config.searchString)!=-1;
		}else if(config.isWholeWordFragment()){
			return value.toLowerCase().equals(config.searchString.toLowerCase());
		}else if(config.isFragment()){
			return value.toLowerCase().indexOf(config.searchString.toLowerCase())!=-1;
		}
		return false;
	}*/
	protected void addPath(XMLTreeNode node){
		TreePath path = new TreePath(node.getPath());
		if(!list.contains(path))
			list.add(path);
	}
	public void setSearchConfig(SearchConfig config){
		this.config = config;
	}
	public abstract ArrayList search();
}

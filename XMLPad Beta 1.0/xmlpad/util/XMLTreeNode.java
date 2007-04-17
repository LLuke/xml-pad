/*
 * Created on 02/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMLTreeNode extends DefaultMutableTreeNode {
	private Hashtable attributes = new Hashtable();
	private String text ="";
	private int type;
	public static final int TYPE_ENTITY = 1;
	public static final int TYPE_COMMENT = 2;
	public static final int TYPE_FILE = 3;
	private String encoding = "";
	private ArrayList selectedAttNames = new ArrayList();
	private Hashtable selectedAttValues = new Hashtable();
	/*public void add(MutableTreeNode node){
		super.add(node);
		if(type==TYPE_FILE){
			attributes.put("version","1.0");
			attributes.put("encoding","UTF-8");
		}
	}*/
	public void setSelectedAttName(String name){
		selectedAttNames.add(name);
	}
	public void setSelectedAttValues(String name, String value){
		selectedAttValues.put(name,value);
	}
	public Set getSelectedAttValuesKeys(){
		return selectedAttValues.keySet();
	}
	public String getSelectedAttValues(String key){
		return (String)selectedAttValues.get(key);
	}
	public ArrayList getSelectedAttNames(){
		return selectedAttNames;
	}
	public void blankSelected(){
		selectedAttNames = new ArrayList();
		selectedAttValues = new Hashtable();
		for(int i=0;i<getChildCount();i++){
			((XMLTreeNode)getChildAt(i)).blankSelected();
		}
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Set getAttributeName(){
		return attributes.keySet();
	}
	public Object getAttributeValue(String key){
		return attributes.get(key);
	}
	public void addAttribute(String key, String value){
		attributes.put(key,value);
	}
	public XMLTreeNode(String s){
		super(s);
		type = TYPE_ENTITY;
	}
	public TreePath[] getAllTreePaths(){
		ArrayList list = new ArrayList();
		getAllTreePaths(this,list);
		TreePath paths[] = new TreePath[list.size()];
		for(int i=0;i<list.size();i++){
			paths[i] = new TreePath((TreeNode[])list.get(i));
		}
		return paths;
	}
	private void getAllTreePaths(XMLTreeNode node, ArrayList list){
		list.add(node.getPath());
		for(int i=0;i<node.getChildCount();i++){
			getAllTreePaths((XMLTreeNode)node.getChildAt(i),list);
		}
	}
	public void add(DefaultMutableTreeNode node){
		if(type==TYPE_FILE && hasEntityChildren() && ((XMLTreeNode)node).type==TYPE_ENTITY){
			return;
		}else{
			super.add(node);
		}
	}
	public XMLTreeNode getNextNonCommentSibling(){
		XMLTreeNode sibling = (XMLTreeNode)getNextSibling();
		while(sibling!=null){
			if(sibling.getType()==XMLTreeNode.TYPE_ENTITY || 
					sibling.getType()==XMLTreeNode.TYPE_FILE){
				return sibling;
			}
			sibling = (XMLTreeNode)sibling.getNextSibling();
		}
		return null;
	}
	public boolean hasEntityChildren(){
		boolean bool = false;
		for(int i=0;i<getChildCount();i++){
			XMLTreeNode node = (XMLTreeNode)getChildAt(i);
			if(node.type==TYPE_ENTITY){
				bool = true;
				break;
			}
		}
		return bool;
	}
	public void deleteAttributes(){
		Enumeration enum = attributes.keys();
		while(enum.hasMoreElements())
			attributes.remove(enum.nextElement());
	}
	public String getInlineAttributes(){
		Enumeration enum = attributes.keys();
		String s="";
		while(enum.hasMoreElements()){
			String attn = (String)enum.nextElement();
			s+=" "+attn+"=\""+attributes.get(attn)+"\"";
		}
		return s;
	}
	public XMLTreeNode copy(){
		XMLTreeNode newNode = softCopy(this);
		copyChildren(newNode, this);
		return newNode;
	}
	private void copyChildren(XMLTreeNode newNode, XMLTreeNode current){
		for(int i=0;i<current.getChildCount();i++){
			XMLTreeNode child = (XMLTreeNode)current.getChildAt(i);
			XMLTreeNode newChild = softCopy(child);
			newNode.add(newChild);
			copyChildren(newChild,child);
		}
	}
	public static XMLTreeNode softCopy(XMLTreeNode node){
		XMLTreeNode newNode = new XMLTreeNode(node.getUserObject().toString());
		newNode.setText(node.getText());
		newNode.type = node.type;
		Enumeration enum = node.attributes.keys();
		while(enum.hasMoreElements()){
			String att = enum.nextElement().toString();
			newNode.addAttribute(att,(String)node.attributes.get(att));
		}
		return newNode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
		if(type==TYPE_FILE){
			attributes.clear();
			attributes.put("version","1.0");
			attributes.put("encoding","UTF-8");
		}
	}
	private TextConfigurer textConf = null;
	public String getXMLText(){
		return getXMLText(TextConfigurerFactory.TYPE_XML);
	}
	public String getXMLText(int renderType){
		textConf = TextConfigurerFactory.createConfigurer(renderType);
		StringBuffer buf = new StringBuffer();
		int spaces = 0;
		if(type==TYPE_FILE){
			//buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+System.getProperty("line.separator"));
			buf.append(textConf.getFileText(this));
			if(this.getChildCount()>0)
				getXMLText((XMLTreeNode)getFirstChild(),buf,0);
		}else{
			getXMLText(this,buf,0);
		}
		
		return buf.toString();
	}
	private void getXMLText(XMLTreeNode child, StringBuffer buf, int spaces){
		if(child==null) return;
		if(child.isFileChild()){
			do{
				if(child.isComment()){
					//buf.append("<!--"+child.getText()+"-->"+System.getProperty("line.separator"));
					buf.append(textConf.getCommentText(child));
				}else if(child.isEntity()){
					appendXMLEntity(child,buf,spaces);
				}
				child = (XMLTreeNode)child.getNextSibling();
			}while(child!=null);
		}else if(child.isEntity()){
			appendXMLEntity(child,buf,spaces);
		}else if(child.isComment()){
			buf.append(textConf.getCommentText(child));
		}
	}
	private void appendXMLEntity(XMLTreeNode child, StringBuffer buf, int spaces){
		if(child.hasEntityChildren()){
			buf.append(textConf.getEntity(child,false,spaces)+textConf.getEntityText(child,true,0));
			for(int i=0;i<child.getChildCount();i++)
				getXMLText((XMLTreeNode)child.getChildAt(i),buf,spaces+1);
			buf.append(textConf.getEntityClosing(child,spaces));
		}else{
			buf.append(textConf.getEntity(child,false,spaces)+textConf.getEntityText(child)+textConf.getEntityClosing(child,0));
		}
	}
	public boolean isComment(){
		return type==TYPE_COMMENT;
	}
	public boolean isEntity(){
		return type==TYPE_ENTITY;
	}
	public boolean isFile(){
		return type==TYPE_FILE;
	}
	public boolean isFileChild(){
		return getParent()!=null&&((XMLTreeNode)getParent()).isFile();
	}
	private void replaceName(ReplaceConfig config){
		String name = (String)getUserObject();
		SearchConfig searchConfig = config.getSearchConfig();
		if(config.isReplaceMatchingText()){
			//name.replaceAll(searchConfig.searchString,config.replaceString);
			name = XMLUtil.replaceAll(config,name);
			setUserObject(name);
		}else if(config.isReplaceTextWholeWordText()){
			setUserObject(config.replaceString);
		}
	}
	private void replaceText(ReplaceConfig config){
		if(getText()==null || getText().trim().equals("")) return;
		String name = (String)getText();
		SearchConfig searchConfig = config.getSearchConfig();
		if(config.isReplaceMatchingText()){
			//name.replaceAll(searchConfig.searchString,config.replaceString);
			name = XMLUtil.replaceAll(config,name);
			setText(name);
		}else if(config.isReplaceTextWholeWordText()){
			setText(config.replaceString);
		}
	}
	private void replaceAttributeName(ReplaceConfig config){
		SearchConfig searchConfig = config.getSearchConfig();
		Set set = attributes.keySet();
		Iterator itr = set.iterator();
		ArrayList nameList = new ArrayList();
		ArrayList attList = new ArrayList();
		while(itr.hasNext()){
			String name = (String)itr.next();
			if(searchConfig.compare(name)){
				if(config.isReplaceMatchingText()){
					Object att = attributes.get(name);
					itr.remove();
					name = XMLUtil.replaceAll(config,name);
					nameList.add(name);
					attList.add(att);
					//attributes.put(name,att);
				}else if(config.isReplaceTextWholeWordText()){
					Object att = attributes.get(name);
					itr.remove();
					name = config.replaceString;
					nameList.add(name);
					attList.add(att);
				}
			}
		}
		for(int i=0;i<nameList.size();i++){
			attributes.put(nameList.get(i),attList.get(i));
		}
	}
	private void replaceAttributeValue(ReplaceConfig config){
		SearchConfig searchConfig = config.getSearchConfig();
		Set set = attributes.keySet();
		Iterator itr = set.iterator();
		ArrayList nameList = new ArrayList();
		ArrayList attList = new ArrayList();
		while(itr.hasNext()){
			String name = (String)itr.next();
			if(searchConfig.compare((String)attributes.get(name))){
				if(config.isReplaceMatchingText()){
					String att = (String)attributes.get(name);
					//att.replaceAll(searchConfig.searchString,config.replaceString);
					att = XMLUtil.replaceAll(config,att);
					nameList.add(name);
					attList.add(att);
					itr.remove();
					//attributes.put(name,att);
				}else if(config.isReplaceTextWholeWordText()){
					//String att = (String)attributes.get(name);
					//attributes.put(name,config.replaceString);
					nameList.add(name);
					attList.add(config.replaceString);
					itr.remove();
				}
			}
		}
		for(int i=0;i<nameList.size();i++){
			attributes.put(nameList.get(i),attList.get(i));
		}
	}
	private void replaceAny(ReplaceConfig config){
		replaceName(config);
		replaceText(config);
		replaceAttributeName(config);
		replaceAttributeValue(config);
	}
	public void replace(ReplaceConfig config){
		SearchConfig s = config.getSearchConfig();
		if(s.isMatchingAttributeName()){
			replaceAttributeName(config);
		}else if(s.isMatchingAttributeValue()){
			replaceAttributeValue(config);
		}else if(s.isMatchingComment() || s.isMatchingElementValue()){
			replaceText(config);
		}else if(s.isMatchingElementName()){
			replaceName(config);
		}else if(s.isMatchingAny()){
			replaceAny(config);
		}
	}
}

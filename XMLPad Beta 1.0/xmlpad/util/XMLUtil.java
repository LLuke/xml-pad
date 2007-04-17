/*
 * Created on 02/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.util;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;




/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMLUtil {

	public static void main(String[] args) {
	    try {
	    	String file = "C:\\temp\\test.xml";
	        DOMParser p = new DOMParser();
	        p.parse(file);
	        Document doc = p.getDocument();
	        Node n = doc.getDocumentElement().getFirstChild();
	        printNode(n);
	        /*System.out.println(n.getNodeName());
	        Node child = n.getFirstChild();
	        //n.getNextSibling()
	        while((child!=null)){
	        	System.out.println(child.getNodeName());
	        	child = child.getNextSibling();
	        }

	        //out.println("</collection>");*/
	      } catch (Exception e) {e.printStackTrace();}

	}
	public static String replaceAll(ReplaceConfig replace, String text){
		SearchConfig search = replace.getSearchConfig();
		String s = "";
		String replacingText = replace.getReplaceString();
		String searchingText = search.searchString;
		if(search.isCaseSensitiveFragment() || search.isCaseSensitiveWholeWord()){
			text.replaceAll(searchingText,replacingText);
			s = text;
		}else if(search.isFragment() || search.isWholeWordFragment()){
			int initIndex = text.toLowerCase().indexOf(searchingText.toLowerCase());
			int endIndex = initIndex+searchingText.length();
			int prevIndex = 0;
			if(initIndex==-1) return text;
			while(true){
				s+=text.substring(prevIndex,initIndex);
				s+=replacingText;
				prevIndex = initIndex+searchingText.length();
				initIndex = text.substring(prevIndex).indexOf(searchingText);
				if(initIndex==-1){
					s+=text.substring(prevIndex);
					break;
				}
			}
		}
		return s;
	}
	public static void save(File f, XMLTreeNode root){
		try{
			FileOutputStream fout = new FileOutputStream(f);
			fout.write(root.getXMLText().getBytes());
			fout.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static String tabCount(int n){
		return tabCount(n,"\t");
	}
	public static String tabCount(int n, String tab){
		String s = "";
		for(int i=0;i<n;i++){
			s+=tab;
		}
		return s;
	}
	public static XMLTreeNode mountTreeFromFile(File f) throws Exception{
        DOMParser p = new DOMParser();
        p.parse(f.getPath());
        //Document doc = p.getDocument();
        DocumentImpl doc = (DocumentImpl) p.getDocument();
        
        NodeList list = doc.getChildNodes();
        XMLTreeNode root = new XMLTreeNode(f.getName());
        root.addAttribute("version",doc.getXmlVersion());
        root.addAttribute("encoding",doc.getXmlEncoding());        
        root.setType(XMLTreeNode.TYPE_FILE);
        //root.setEncoding(doc.)
        for(int i=0;i<list.getLength();i++){
        	Node n1 = list.item(i);
        	if(Node.COMMENT_NODE==n1.getNodeType()){
        		XMLTreeNode c1 = new XMLTreeNode(n1.getNodeName());
        		c1.setText(n1.getNodeValue());
        		c1.setType(XMLTreeNode.TYPE_COMMENT);
        		root.add(c1);
        	}else{
        		XMLTreeNode c1 = new XMLTreeNode(n1.getNodeName());
        		root.add(c1);
        		mountNode(n1.getFirstChild(),c1);
        	}
        }
        /*Node n = doc.getDocumentElement().getFirstChild();
        XMLTreeNode root2 = new XMLTreeNode(doc.getDocumentElement().getNodeName());
        mountNode(n,root2);*/
        return root;
	}
	private static void mountNode(Node n, XMLTreeNode treeNode){
		if(n==null) return;
		//Node.ENTITY_NODE 6
		//Node.DOCUMENT_NODE 9
		//Node.ELEMENT_NODE 1
		//Node.TEXT_NODE 3
		//n.getNodeName()+" "+n.getNodeValue()
		if(n.getNodeType()==Node.ELEMENT_NODE){
			XMLTreeNode newNode = new XMLTreeNode(n.getNodeName());
			treeNode.add(newNode);
			mountAtts(n.getAttributes(),newNode);
			Node child = n.getFirstChild();
			mountNode(child, newNode);
		}else if(n.getNodeType()==Node.TEXT_NODE){
			if(n.getNodeValue()!=null && (!n.getNodeValue().trim().equals("")))
				treeNode.setText(n.getNodeValue());
			Node child = n.getFirstChild();
			mountNode(child, treeNode);
		}else if(n.getNodeType()==Node.COMMENT_NODE){
    		XMLTreeNode newNode = new XMLTreeNode(n.getNodeName());
    		newNode.setText(n.getNodeValue());
    		newNode.setType(XMLTreeNode.TYPE_COMMENT);
    		treeNode.add(newNode);
		}
		mountNode(n.getNextSibling(),treeNode);
	}
	private static void mountAtts(NamedNodeMap m, XMLTreeNode treeNode){
		for(int i=0;i<m.getLength();i++){
			Node n = m.item(i);
			treeNode.addAttribute(n.getNodeName(),n.getNodeValue());
		}
	}
	private static void printNode(Node n){
		if(n==null) return;
		if(n.getNodeType()==Node.ELEMENT_NODE){
			System.out.println("<"+n.getNodeName()+printAtts(n.getAttributes())+">");
			Node child = n.getFirstChild();
			printNode(child);
			System.out.println("</"+n.getNodeName()+">");
		}else if(n.getNodeType()==Node.TEXT_NODE){
			System.out.println(n.getNodeValue());
		}
		printNode(n.getNextSibling());
	}
	private static String printAtts(NamedNodeMap m){
		String s = "";
		for(int i=0;i<m.getLength();i++){
			Node n = m.item(i);
			s = " "+n.getNodeName()+"=\""+n.getNodeValue()+"\"";
		}
		return s;
	}
	private static void print(Node n){
		if(n==null) return;
		System.out.println(n);
		print(n.getNextSibling());
		Node child = n.getFirstChild();
		print(child);
	}

}

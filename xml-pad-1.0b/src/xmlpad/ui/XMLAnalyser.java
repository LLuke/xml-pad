/*
 * Created on 02/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import javax.swing.JToggleButton;

import xmlpad.util.TreeIconRenderer;
import xmlpad.util.XMLTreeNode;
import xmlpad.util.XMLUtil;
/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMLAnalyser extends JPanel implements MouseListener, 
												ActionListener, 
												KeyListener, 
												TreeModelListener,												
												TableModelListener,
												Runnable{

	private JSplitPane jSplitPane = null;
	private JScrollPane xmlTreeScrollPane = null;
	private JTree xmlTree = null;
	private JScrollPane xmlTextScrollPane = null;
	private JTextArea xmlTextArea = null;
	private JSplitPane xmlContentsSplitPane = null;
	private File xmlFile = null;
	private XMLTreeNode rootNode = null;
	//private DefaultTableModel xmlTableModel = null;
	private DefaultTableModel childTableModel = null;
	private JSplitPane jSplitPane1 = null;
	private JPanel childOverviewPanel = null;
	private JTable childOverviewTable = null;
	private JScrollPane jScrollPane = null;
	private JPanel controlPanel = null;
	private JButton childInsertButton = null;
	private JButton childRemoveButton = null;
	private JButton childApplyButton = null;
	private JPanel childFieldPanel = null;
	private JLabel childNameValue = null;
	private JTextField childNameField = null;
	private JLabel childValueLabel = null;
	private JTextField childValueField = null;
	private JPanel nodeNamePanel = null;
	private String fileName = "";
	private JPopupMenu treePopupMenu = null;
	private JMenuItem copyMenuItem = null;
	private JMenuItem pasteMenuItem = null;
	private JCheckBoxMenuItem fullPasteCheckBoxMenuItem = null;
	private JMenuItem deleteMenuItem = null;
	private JMenuItem moveUpMenuItem = null;
	private JMenuItem moveDownMenuItem = null;
	private JMenuItem cutMenuItem = null;
	private JToolBar jToolBar = null;
	private JButton toolCopyButton = null;
	private JButton toolCutButton = null;
	private JButton toolPasteButton = null;
	private JButton toolDeleteButton = null;
	private JButton toolMoveUpButton = null;
	private JButton toolMoveDownButton = null;
	private JMenuItem addChildMenuItem = null;
	private JButton toolAddChildButton = null;
	private AttributeTablePanel attributeTablePanel = null;
	private JMenuItem addCommentMenuItem = null;
	private JButton toolAddCommentButton = null;
	private JButton toolExpandAllButton = null;
	private JButton toolCollapseAllButton = null;
	private JMenuItem expandAllMenuItem = null;
	private JMenuItem collpaseAllMenuItem = null;
	private JButton toolAddSiblingButton = null;
	private JMenu addMenu = null;
	private JMenuItem addSiblingMenuItem = null;
	private XMLReaderFrame parent = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane1 = null;
	private XMLTextPane xmlEditorPane = null;
	private JToolBar jToolBar1 = null;
	private JPanel jPanel1 = null;
	private JProgressBar jProgressBar = null;
	private JPanel jPanel2 = null;
	private JToggleButton decorateXMLToggleButton = null;
	/**
	 * This is the default constructor
	 */
	public XMLAnalyser(File file, XMLReaderFrame parent) throws Exception{
		super();
		this.xmlFile = file;
		this.fileName = file.getPath();
		this.parent = parent;
		rootNode = XMLUtil.mountTreeFromFile(file);
		initialize();
		getXmlTree().setSelectionPath(new TreePath(rootNode.getPath()));
		refreshControlsFromTree();
	}
	
	public XMLAnalyser(XMLReaderFrame parent) throws Exception{
		super();
		this.parent = parent;
		rootNode = new XMLTreeNode("new-xml.xml");
		rootNode.setType(XMLTreeNode.TYPE_FILE);
		initialize();
		getXmlTree().setSelectionPath(new TreePath(rootNode.getPath()));
		refreshButtons();
		attributeTablePanel.setTreeNode(rootNode);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		/*xmlTableModel = new DefaultTableModel();
		Object o[] = {"Attribute name", "Attribute value"};
		xmlTableModel.setColumnIdentifiers(o);*/
		this.setLayout(new BorderLayout());
		this.setSize(764, 416);
		this.setPreferredSize(new java.awt.Dimension(764,416));
		this.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
	}
	public void save(File f){
		XMLUtil.save(f,rootNode);
		this.xmlFile = f;
		rootNode.setUserObject(f.getName());
	}
	public void treeNodesChanged(TreeModelEvent e) {
        XMLTreeNode node;
        node = (XMLTreeNode)(e.getTreePath().getLastPathComponent());

        /*
         * If the event lists children, then the changed
         * node is the child of the node we've already
         * gotten.  Otherwise, the changed node and the
         * specified node are the same.
         */
        try {
            int index = e.getChildIndices()[0];
            node = (XMLTreeNode)(node.getChildAt(index));
        } catch (NullPointerException exc) {}
	}
	public void treeNodesInserted(TreeModelEvent e) {
		// TODO Auto-generated method stub

	}
	public void treeNodesRemoved(TreeModelEvent e) {
		// TODO Auto-generated method stub

	}
	public void treeStructureChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub

	}
	public void tableChanged(TableModelEvent e) {
		
		if(e.getSource()==childTableModel){
			if(e.getType()==TableModelEvent.UPDATE){
				String newVal = (String)childTableModel.getValueAt(e.getFirstRow(),e.getColumn());
				XMLTreeNode node = (XMLTreeNode)getSelectedNode().getChildAt(e.getFirstRow());
				if(e.getColumn()==0){
					node.setUserObject(newVal);
				}else if(e.getColumn()==1){
					node.setText(newVal);
				}
				//paintAll(getGraphics());
				int row = e.getFirstRow();
				refreshControlsFromTree();
			}
		}
	}
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	public void keyReleased(KeyEvent e) {
		XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
		node.setText(xmlTextArea.getText());
	}
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	public void actionPerformed(ActionEvent evt){
		Object source = evt.getSource();
		if(source==childRemoveButton){
			int rows[] = childOverviewTable.getSelectedRows();
			XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
			TreePath path = getXmlTree().getSelectionPath();
			for(int i=rows.length-1;i>=0;i--){
				childTableModel.removeRow(rows[i]);
				node.remove(rows[i]);
			}
			xmlTree.setModel(new DefaultTreeModel(rootNode));
			xmlTree.setSelectionPath(path);
			xmlTree.expandPath(path);
		}else if(source==decorateXMLToggleButton){
			if(decorateXMLToggleButton.isSelected()){
				xmlEditorPane.setDecoratedText(true);
			}else{
				xmlEditorPane.setDecoratedText(false);
			}
		}else if(source==childInsertButton){
			/*controlPanel.setVisible(false);
			childFieldPanel.setVisible(true);*/
			Object o[] = {"new-node",""};
			
			childTableModel.addRow(o);			
			getSelectedNode().add(new XMLTreeNode("new-node"));
			TreePath path = new TreePath(getSelectedNode().getPath());
			xmlTree.setModel(new DefaultTreeModel(rootNode));
			xmlTree.expandPath(path);
			xmlTree.setSelectionPath(path);
			
			//getXmlTree().paintAll(getXmlTree().getGraphics());
			//this.paintAll(this.getGraphics());
		}else if(source==childApplyButton){
			Object o[] = {childNameField.getText(),childValueField.getText()};
			childTableModel.addRow(o);
			XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
			TreePath path = getXmlTree().getSelectionPath();
			XMLTreeNode newNode = new XMLTreeNode(childNameField.getText());
			newNode.setText(childValueField.getText());
			node.add(newNode);
			controlPanel.setVisible(true);
			childFieldPanel.setVisible(false);
			xmlTree.setModel(new DefaultTreeModel(rootNode));
			xmlTree.setSelectionPath(path);
			childNameField.setText("");
			childValueField.setText("");
		}else if(source==copyMenuItem || source==toolCopyButton){
			TreePath[] paths = getXmlTree().getSelectionPaths();
			XMLReaderFrame.copy = new XMLTreeNode[paths.length];
			for(int i=0;i<paths.length;i++){
				XMLTreeNode newNode = (XMLTreeNode)paths[i].getLastPathComponent();
				XMLReaderFrame.copy[i] = newNode.copy();
			}			
		}else if(source==addChildMenuItem || source==toolAddChildButton){
			XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
			//TreePath path = getXmlTree().getSelectionPath();
			XMLTreeNode newNode = new XMLTreeNode("new-node");
			node.add(newNode);
			TreePath path = new TreePath(newNode.getPath());
			xmlTree.setModel(new DefaultTreeModel(rootNode));
			xmlTree.setSelectionPath(path);
			xmlTree.expandPath(path);
		}else if(source==addSiblingMenuItem || source==toolAddSiblingButton){
			/*XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
			//TreePath path = getXmlTree().getSelectionPath();
			XMLTreeNode newNode = new XMLTreeNode("new node");
			node.add(newNode);
			TreePath path = new TreePath(newNode.getPath());
			xmlTree.setModel(new DefaultTreeModel(rootNode));
			xmlTree.setSelectionPath(path);
			xmlTree.expandPath(path);
			refreshButtons();*/
			XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
			XMLTreeNode newNode = new XMLTreeNode("new-node");
			XMLTreeNode parent = (XMLTreeNode)node.getParent();
			int currPos = parent.getIndex(node);
			ArrayList list = new ArrayList();
			for(int i=0;i<parent.getChildCount();i++){
				if(i==currPos){
					list.add(parent.getChildAt(i));
					list.add(newNode);
				}else{
					list.add(parent.getChildAt(i));
				}
			}
			parent.removeAllChildren();
			for(int i=0;i<list.size();i++){
				parent.add((XMLTreeNode)list.get(i));
			}
			TreePath path = new TreePath(newNode.getPath());
			xmlTree.setModel(new DefaultTreeModel(rootNode));
			xmlTree.setSelectionPath(path);
			xmlTree.expandPath(path);
		}else if(source==addCommentMenuItem || source==toolAddCommentButton){
			XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
			XMLTreeNode newNode = new XMLTreeNode("#comment");
			newNode.setType(XMLTreeNode.TYPE_COMMENT);
			if(node.getType()==XMLTreeNode.TYPE_FILE){
				node.add(newNode);
			}else{
				XMLTreeNode parent = (XMLTreeNode)node.getParent();
				int currPos = parent.getIndex(node);
				ArrayList list = new ArrayList();
				if(node.getType()==XMLTreeNode.TYPE_COMMENT){
					for(int i=0;i<parent.getChildCount();i++){
						if(i==currPos){
							list.add(parent.getChildAt(i));
							list.add(newNode);
						}else{
							list.add(parent.getChildAt(i));
						}
					}
				}else{
					for(int i=0;i<parent.getChildCount();i++){
						if(i==currPos){
							list.add(newNode);
							list.add(parent.getChildAt(i));
						}else{
							list.add(parent.getChildAt(i));
						}
					}
				}
				parent.removeAllChildren();
				for(int i=0;i<list.size();i++){
					parent.add((XMLTreeNode)list.get(i));
				}
			}
			TreePath path = new TreePath(newNode.getPath());
			xmlTree.setModel(new DefaultTreeModel(rootNode));
			xmlTree.setSelectionPath(path);
			xmlTree.expandPath(path);
		}else if(source==cutMenuItem || source==toolCutButton){
			//XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
			TreePath path = getXmlTree().getSelectionPath().getParentPath();
			TreePath[] paths = getXmlTree().getSelectionPaths();
			XMLReaderFrame.copy = new XMLTreeNode[paths.length];
			for(int i=0;i<paths.length;i++){
				XMLTreeNode newNode = (XMLTreeNode)paths[i].getLastPathComponent();
				XMLReaderFrame.copy[i] = newNode.copy();
				if(newNode.getParent()!=null){
					((XMLTreeNode)newNode.getParent()).remove(newNode);
				}
			}			
			xmlTree.setModel(new DefaultTreeModel(rootNode));
			xmlTree.setSelectionPath(path);
			xmlTree.expandPath(path);
		}else if(source==pasteMenuItem || source==toolPasteButton){
			XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
			TreePath path = getXmlTree().getSelectionPath();
			XMLTreeNode newNode = null;
			TreePath paths[] = new TreePath[XMLReaderFrame.copy.length];
			ArrayList list = new ArrayList();
			if(node.getType()==XMLTreeNode.TYPE_FILE && node.hasEntityChildren()){
				for(int i=0;i<XMLReaderFrame.copy.length;i++){
					if(XMLReaderFrame.copy[i].getType()==XMLTreeNode.TYPE_ENTITY){
						return;
					}
				}
			}else if(node.getType()==XMLTreeNode.TYPE_COMMENT){
				XMLTreeNode parent = (XMLTreeNode)node.getParent();
				if(parent.getType()==XMLTreeNode.TYPE_FILE && parent.hasEntityChildren()){
					for(int i=0;i<XMLReaderFrame.copy.length;i++){
						if(XMLReaderFrame.copy[i].getType()==XMLTreeNode.TYPE_ENTITY){
							return;
						}
					}
				}
			}
			for(int i=0;i<XMLReaderFrame.copy.length;i++){
				if(getFullPasteCheckBoxMenuItem().isSelected()){
					newNode = XMLReaderFrame.copy[i].copy();
				}else{
					newNode = XMLTreeNode.softCopy(XMLReaderFrame.copy[i]);
				}
				if(node.getType()==XMLTreeNode.TYPE_COMMENT){
					list.add(newNode);
				}else{
					node.add(newNode);
					paths[i] = new TreePath(newNode.getPath());
				}
			}
			if(node.getType()==XMLTreeNode.TYPE_COMMENT){
				XMLTreeNode parent = (XMLTreeNode)node.getParent();
				int curPos = parent.getIndex(node);
				ArrayList parentsChildren = new ArrayList();
				for(int i=0;i<parent.getChildCount();i++){
					parentsChildren.add(parent.getChildAt(i));
				}
				parent.removeAllChildren();
				for(int i=0;i<parentsChildren.size();i++){
					parent.add((XMLTreeNode)parentsChildren.get(i));
					if(curPos==i){
						for(int j=0;j<list.size();j++){
							newNode = (XMLTreeNode)list.get(j);
							parent.add(newNode);
							paths[j] = new TreePath(newNode.getPath());
						}
					}
				}
			}
			xmlTree.setModel(new DefaultTreeModel(rootNode));
			xmlTree.setSelectionPaths(paths);
			xmlTree.expandPath(path);
		}else if(source==deleteMenuItem || source==toolDeleteButton){
			XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
			TreePath path = getXmlTree().getSelectionPath().getParentPath();
			if(node.getParent()!=null){
				TreePath[] paths = getXmlTree().getSelectionPaths();
				for(int i=0;i<paths.length;i++){
					XMLTreeNode newNode = (XMLTreeNode)paths[i].getLastPathComponent();
					if(newNode.getParent()!=null){
						((XMLTreeNode)newNode.getParent()).remove(newNode);
					}
				}				
				xmlTree.setModel(new DefaultTreeModel(rootNode));
				xmlTree.setSelectionPath(path);
				xmlTree.expandPath(path);
			}
		}else if(source==moveDownMenuItem || source==toolMoveDownButton){
			XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
			if(node.getNextSibling()!=null){
				XMLTreeNode nextSibling = (XMLTreeNode)node.getNextSibling();
				XMLTreeNode parent = (XMLTreeNode)node.getParent();
				int nodePos = parent.getIndex(node);
				ArrayList list = new ArrayList();
				for(int i=0;i<parent.getChildCount();i++){
					list.add(parent.getChildAt(i));					
				}
				parent.removeAllChildren();
				for(int i=0;i<list.size();i++){
					if(i==nodePos){
						parent.add((XMLTreeNode)list.get(nodePos+1));
						parent.add((XMLTreeNode)list.get(nodePos));
						i++;
					}else{
						parent.add((XMLTreeNode)list.get(i));
					}
				}
				xmlTree.setModel(new DefaultTreeModel(rootNode));
				TreePath path = new TreePath(node.getPath());
				xmlTree.setSelectionPath(path);
				xmlTree.expandPath(path);
			}
		}else if(source==moveUpMenuItem || source==toolMoveUpButton){
			XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
			//TreePath path = getXmlTree().getSelectionPath().getParentPath();
			if(node.getPreviousSibling()!=null){
				XMLTreeNode prevSibling = (XMLTreeNode)node.getPreviousSibling();
				XMLTreeNode parent = (XMLTreeNode)node.getParent();
				int nodePos = parent.getIndex(prevSibling);
				ArrayList list = new ArrayList();
				for(int i=0;i<parent.getChildCount();i++){
					list.add(parent.getChildAt(i));					
				}
				parent.removeAllChildren();
				for(int i=0;i<list.size();i++){
					if(i==nodePos){
						parent.add((XMLTreeNode)list.get(nodePos+1));
						parent.add((XMLTreeNode)list.get(nodePos));
						i++;
					}else{
						parent.add((XMLTreeNode)list.get(i));
					}
				}
				xmlTree.setModel(new DefaultTreeModel(rootNode));
				TreePath path = new TreePath(node.getPath());
				xmlTree.setSelectionPath(path);
				xmlTree.expandPath(path);
			}
		}else if(source==expandAllMenuItem || source==toolExpandAllButton){
			TreePath paths[] = rootNode.getAllTreePaths();
			for(int i=0;i<paths.length;i++){
				xmlTree.expandPath(paths[i]);
			}
		}else if(source==collpaseAllMenuItem || source==toolCollapseAllButton){
			TreePath paths[] = rootNode.getAllTreePaths();
			for(int i=paths.length-1;i>=0;i--){
				xmlTree.collapsePath(paths[i]);
			}
			//xmlTree.collapsePath(new TreePath(rootNode.getPath()));
		}
		refreshControlsFromTree();
	}

	void setFileView(){
		getAttributeTablePanel().setEnabled(true);
		getXmlTextArea().setEditable(false);
		getXmlTextArea().setText("");
		getChildOverviewPanel().setEnabled(false);
		this.paintAll(this.getGraphics());
	}
	void setEntityView(){
		getAttributeTablePanel().setEnabled(true);
		getChildOverviewPanel().setEnabled(true);
		getXmlTextArea().setEditable(true);
		this.paintAll(this.getGraphics());
	}
	void setCommentView(){
		getAttributeTablePanel().setEnabled(false);
		getXmlTextArea().setEditable(true);
		getChildOverviewPanel().setEnabled(false);
		this.paintAll(this.getGraphics());
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==getXmlTree()){
			refreshButtons();
			//this.getParent().getParent().getParent().getParent().getParent()
			/*Component parent = this.getParent();
			while(!(parent instanceof XMLReaderFrame)){
				parent = parent.getParent();
				if(parent==null) break;
			}
			if(parent!=null){
				updatedEditMenu(((XMLReaderFrame)parent).getEditMenu());
			}*/
			//this.updatedEditMenu()
			if(e.getSource()==getXmlTree()){
				XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
				if(node.getType()==XMLTreeNode.TYPE_FILE){
					setFileView();
				}else if(node.getType()==XMLTreeNode.TYPE_COMMENT){
					setCommentView();
					xmlTextArea.setText((String)node.getText());
				}else if(node.getType()==XMLTreeNode.TYPE_ENTITY){
					setEntityView();
					//nodeNameField.setText(node.toString());
				}
				refreshControlsFromTree();
				if(e.getButton()==MouseEvent.BUTTON3){
					TreePath path = getXmlTree().getSelectionPath().getParentPath();
					JPopupMenu popMenu = getTreePopupMenu();

					popMenu.show(e.getComponent(),e.getX(),e.getY());
				}
			}

		}

	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	public void refreshControlsFromTree(){
		XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
		attributeTablePanel.setTreeNode(node);
		/*for(int i=xmlTableModel.getRowCount()-1;i>=0;i--)
			xmlTableModel.removeRow(i);*/
		for(int i=childTableModel.getRowCount()-1;i>=0;i--)
			childTableModel.removeRow(i);			
		Set atts = node.getAttributeName();
		if(!atts.isEmpty()){
			Iterator itr = atts.iterator();
			while(itr.hasNext()){
				String key = (String)itr.next();
				Object o[] = {key,node.getAttributeValue(key)};
				//xmlTableModel.addRow(o);
			}
		}
		xmlTextArea.setText((String)node.getText());
		for(int i=0;i<node.getChildCount();i++){
			XMLTreeNode child = (XMLTreeNode)node.getChildAt(i);
			Object o[] = {child.toString(),child.getText()};
			childTableModel.addRow(o);
		}
		getXmlEditorPane().setText(node.getXMLText());
		getXmlEditorPane().setCaretPosition(0);
		refreshButtons();
		paintAll(getGraphics());
	}

	public void run(){

	}
	void refreshButtons(){
		XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
		if(XMLReaderFrame.copy!=null && XMLReaderFrame.copy.length>0){
			if(node.getType()==XMLTreeNode.TYPE_FILE && node.getChildCount()>0){
				getToolPasteButton().setEnabled(false);
				getPasteMenuItem().setEnabled(false);
			}else{
				getToolPasteButton().setEnabled(true);
				getPasteMenuItem().setEnabled(true);
			}
		}else{
			getToolPasteButton().setEnabled(false);
			getPasteMenuItem().setEnabled(false);
		}
		if(node.getType()==XMLTreeNode.TYPE_COMMENT){
			getAddChildMenuItem().setEnabled(false);
			getToolAddChildButton().setEnabled(false);
			getAddSiblingMenuItem().setEnabled(false);
			getToolAddSiblingButton().setEnabled(false);
			getToolCopyButton().setEnabled(true);
			getCopyMenuItem().setEnabled(true);
			getToolCutButton().setEnabled(true);
			getCutMenuItem().setEnabled(true);
			getToolDeleteButton().setEnabled(true);
			getDeleteMenuItem().setEnabled(true);
		}else if(node.getType()==XMLTreeNode.TYPE_ENTITY){
			getAddChildMenuItem().setEnabled(true);
			getToolAddChildButton().setEnabled(true);
			getToolCopyButton().setEnabled(true);
			getCopyMenuItem().setEnabled(true);
			getToolCutButton().setEnabled(true);
			getCutMenuItem().setEnabled(true);
			getToolDeleteButton().setEnabled(true);
			getDeleteMenuItem().setEnabled(true);
			if(((XMLTreeNode)node.getParent()).getType()==XMLTreeNode.TYPE_FILE){
				getToolAddSiblingButton().setEnabled(false);
				getAddSiblingMenuItem().setEnabled(false);
			}else{
				getToolAddSiblingButton().setEnabled(true);
				getAddSiblingMenuItem().setEnabled(true);
			}
		}else if(node.getType()==XMLTreeNode.TYPE_FILE){
			getToolAddSiblingButton().setEnabled(false);
			getAddSiblingMenuItem().setEnabled(false);
			getToolCopyButton().setEnabled(false);
			getCopyMenuItem().setEnabled(false);
			getToolCutButton().setEnabled(false);
			getCutMenuItem().setEnabled(false);
			getToolDeleteButton().setEnabled(false);
			getDeleteMenuItem().setEnabled(false);
			if(node.hasEntityChildren()){
				getAddChildMenuItem().setEnabled(false);
				getToolAddChildButton().setEnabled(false);
			}else{
				getAddChildMenuItem().setEnabled(true);
				getToolAddChildButton().setEnabled(true);	
			}
			getToolAddSiblingButton().setEnabled(false);
			getAddSiblingMenuItem().setEnabled(false);
		}
		if(node.getNextSibling()!=null){
			getMoveDownMenuItem().setEnabled(true);
			getToolMoveDownButton().setEnabled(true);
		}else{
			getMoveDownMenuItem().setEnabled(false);
			getToolMoveDownButton().setEnabled(false);
		}
		if(node.getPreviousSibling()!=null){
			getMoveUpMenuItem().setEnabled(true);
			getToolMoveUpButton().setEnabled(true);
		}else{
			getMoveUpMenuItem().setEnabled(false);
			getToolMoveUpButton().setEnabled(false);
		}
		JMenu editMenu = parent.getEditMenu();
		editMenu.removeAll();
		editMenu.add(getCopyMenuItem());
		editMenu.add(getCutMenuItem());
		editMenu.add(getPasteMenuItem());
		editMenu.add(getFullPasteCheckBoxMenuItem());
		editMenu.add(getAddMenu());
		editMenu.add(getAddCommentMenuItem());
		editMenu.add(getDeleteMenuItem());
		editMenu.add(getMoveUpMenuItem());
		editMenu.add(getMoveDownMenuItem());
		editMenu.add(getExpandAllMenuItem());
		editMenu.add(getCollpaseAllMenuItem());
	}
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(240);
			jSplitPane.setLeftComponent(getNodeNamePanel());
			jSplitPane.setContinuousLayout(true);
			jSplitPane.setOneTouchExpandable(false);
			jSplitPane.setRightComponent(getJTabbedPane());
		}
		return jSplitPane;
	}
	/**
	 * This method initializes xmlTreeScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getXmlTreeScrollPane() {
		if (xmlTreeScrollPane == null) {
			xmlTreeScrollPane = new JScrollPane();
			xmlTreeScrollPane.setViewportView(getXmlTree());
		}
		return xmlTreeScrollPane;
	}
	/**
	 * This method initializes xmlTree	
	 * 	
	 * @return javax.swing.JTree	
	 */    
	public JTree getXmlTree() {
		if (xmlTree == null) {
			xmlTree = new JTree(new DefaultTreeModel(rootNode));
			xmlTree.setEditable(true);
			DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
			treeModel.addTreeModelListener(this);

			/*TreeSelectionModel model = xmlTree.getSelectionModel();
			model.setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);*/
			xmlTree.addMouseListener(this);
			xmlTree.setCellRenderer(new TreeIconRenderer());
		}
		return xmlTree;
	}
	/**
	 * This method initializes xmlTextScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getXmlTextScrollPane() {
		if (xmlTextScrollPane == null) {
			xmlTextScrollPane = new JScrollPane();
			xmlTextScrollPane.setViewportView(getXmlTextArea());
		}
		return xmlTextScrollPane;
	}
	/**
	 * This method initializes xmlTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getXmlTextArea() {
		if (xmlTextArea == null) {
			xmlTextArea = new JTextArea();
			xmlTextArea.addKeyListener(this);
		}
		return xmlTextArea;
	}
	/**
	 * This method initializes xmlContentsSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getXmlContentsSplitPane() {
		if (xmlContentsSplitPane == null) {
			xmlContentsSplitPane = new JSplitPane();
			xmlContentsSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			xmlContentsSplitPane.setDividerLocation(130);
			xmlContentsSplitPane.setBottomComponent(getJSplitPane1());
			xmlContentsSplitPane.setTopComponent(getAttributeTablePanel());
		}
		return xmlContentsSplitPane;
	}
	/**
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setTopComponent(getXmlTextScrollPane());
			jSplitPane1.setDividerLocation(50);
			jSplitPane1.setBottomComponent(getChildOverviewPanel());
		}
		return jSplitPane1;
	}
	/**
	 * This method initializes childOverviewPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getChildOverviewPanel() {
		if (childOverviewPanel == null) {
			childOverviewPanel = new JPanel();
			childOverviewPanel.setLayout(new BorderLayout());
			childOverviewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Child overview", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			childOverviewPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			childOverviewPanel.add(getControlPanel(), java.awt.BorderLayout.NORTH);
			childOverviewPanel.add(getChildFieldPanel(), java.awt.BorderLayout.SOUTH);
		}
		return childOverviewPanel;
	}
	/**
	 * This method initializes childOverviewTable	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	private JTable getChildOverviewTable() {
		if (childOverviewTable == null) {
			Object o[] = {"Node name","Node value"};
			childTableModel = new DefaultTableModel();
			childTableModel.setColumnIdentifiers(o);
			childOverviewTable = new JTable(childTableModel);
			childTableModel.addTableModelListener(this);
		}
		return childOverviewTable;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getChildOverviewTable());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes controlPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getControlPanel() {
		if (controlPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			controlPanel = new JPanel();
			controlPanel.setLayout(flowLayout1);
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			controlPanel.add(getJToolBar1(), null);
		}
		return controlPanel;
	}
	/**
	 * This method initializes childInsertButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getChildInsertButton() {
		if (childInsertButton == null) {
			childInsertButton = new JButton();
			childInsertButton.setText("");
			childInsertButton.setEnabled(true);
			childInsertButton.setIcon(new ImageIcon(getClass().getResource("/icons/add.gif")));
			childInsertButton.setVisible(true);
			childInsertButton.addActionListener(this);
		}
		return childInsertButton;
	}
	/**
	 * This method initializes childRemoveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getChildRemoveButton() {
		if (childRemoveButton == null) {
			childRemoveButton = new JButton();
			childRemoveButton.setText("");
			childRemoveButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.gif")));
			childRemoveButton.setToolTipText("Remove selected");
			childRemoveButton.setPreferredSize(new java.awt.Dimension(28,28));
			childRemoveButton.addActionListener(this);
		}
		return childRemoveButton;
	}
	/**
	 * This method initializes childApplyButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getChildApplyButton() {
		if (childApplyButton == null) {
			childApplyButton = new JButton();
			childApplyButton.setText("Apply");
			childApplyButton.addActionListener(this);
		}
		return childApplyButton;
	}
	/**
	 * This method initializes childFieldPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getChildFieldPanel() {
		if (childFieldPanel == null) {
			childValueLabel = new JLabel();
			childNameValue = new JLabel();
			FlowLayout flowLayout4 = new FlowLayout();
			childFieldPanel = new JPanel();
			childFieldPanel.setLayout(flowLayout4);
			flowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			childNameValue.setText("Name:");
			childValueLabel.setText("Value:");
			childFieldPanel.add(childNameValue, null);
			childFieldPanel.add(getChildNameField(), null);
			childFieldPanel.add(childValueLabel, null);
			childFieldPanel.add(getChildValueField(), null);
			childFieldPanel.add(getChildApplyButton(), null);
			childFieldPanel.setVisible(false);
		}
		return childFieldPanel;
	}
	/**
	 * This method initializes childNameField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getChildNameField() {
		if (childNameField == null) {
			childNameField = new JTextField();
			childNameField.setColumns(15);
		}
		return childNameField;
	}
	/**
	 * This method initializes childValueField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getChildValueField() {
		if (childValueField == null) {
			childValueField = new JTextField();
			childValueField.setColumns(15);
		}
		return childValueField;
	}
	/**
	 * This method initializes nodeNamePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getNodeNamePanel() {
		if (nodeNamePanel == null) {
			nodeNamePanel = new JPanel();
			nodeNamePanel.setLayout(new BorderLayout());
			nodeNamePanel.add(getXmlTreeScrollPane(), java.awt.BorderLayout.CENTER);
			nodeNamePanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return nodeNamePanel;
	}
	public String getFileName() {
		return fileName;
	}
	/**
	 * This method initializes treePopupMenu	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */    
	private JPopupMenu getTreePopupMenu() {
		if (treePopupMenu == null || treePopupMenu.getComponentCount()==0) {
			treePopupMenu = new JPopupMenu();
			treePopupMenu.add(getCopyMenuItem());
			treePopupMenu.add(getCutMenuItem());
			treePopupMenu.add(getPasteMenuItem());
			treePopupMenu.add(getFullPasteCheckBoxMenuItem());
			treePopupMenu.add(getAddMenu());
			treePopupMenu.add(getAddCommentMenuItem());
			treePopupMenu.add(getDeleteMenuItem());
			treePopupMenu.add(getMoveUpMenuItem());
			treePopupMenu.add(getMoveDownMenuItem());
			treePopupMenu.add(getExpandAllMenuItem());
			treePopupMenu.add(getCollpaseAllMenuItem());
		}
		return treePopupMenu;
	}
	/*public void updatedEditMenu(JMenu menu){
		menu.add(getCopyMenuItem());
		menu.add(getCutMenuItem());
		menu.add(getPasteMenuItem());
		menu.add(getFullPasteCheckBoxMenuItem());
		menu.add(getDeleteMenuItem());
		menu.add(getMoveUpMenuItem());
		menu.add(getMoveDownMenuItem());
	}*/
	/**
	 * This method initializes copyMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem("Copy");
			copyMenuItem.addActionListener(this);
		}
		return copyMenuItem;
	}
	/**
	 * This method initializes pasteMenuItem	
	 * 	
	 * @return javax.swing.JMenu	
	 */    
	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem("Paste");
			if(XMLReaderFrame.copy==null) pasteMenuItem.setEnabled(false);
			pasteMenuItem.addActionListener(this);
		}
		return pasteMenuItem;
	}
	/**
	 * This method initializes fullPasteCheckBoxMenuItem	
	 * 	
	 * @return javax.swing.JCheckBoxMenuItem	
	 */    
	private JCheckBoxMenuItem getFullPasteCheckBoxMenuItem() {
		if (fullPasteCheckBoxMenuItem == null) {
			fullPasteCheckBoxMenuItem = new JCheckBoxMenuItem("Paste with children");
			fullPasteCheckBoxMenuItem.setSelected(true);
		}
		return fullPasteCheckBoxMenuItem;
	}
	/**
	 * This method initializes deleteMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getDeleteMenuItem() {
		if (deleteMenuItem == null) {
			deleteMenuItem = new JMenuItem("Delete");
			deleteMenuItem.addActionListener(this);
		}
		return deleteMenuItem;
	}
	/**
	 * This method initializes moveUpMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getMoveUpMenuItem() {
		if (moveUpMenuItem == null) {
			moveUpMenuItem = new JMenuItem("Move Up");
			moveUpMenuItem.addActionListener(this);
		}
		return moveUpMenuItem;
	}
	/**
	 * This method initializes moveDownMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getMoveDownMenuItem() {
		if (moveDownMenuItem == null) {
			moveDownMenuItem = new JMenuItem("Move Down");
			moveDownMenuItem.addActionListener(this);
		}
		return moveDownMenuItem;
	}
	/**
	 * This method initializes cutMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem("Cut");
			cutMenuItem.addActionListener(this);
		}
		return cutMenuItem;
	}
	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */    
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.setToolTipText("");
			jToolBar.add(getToolCopyButton());
			jToolBar.add(getToolCutButton());
			jToolBar.add(getToolPasteButton());
			jToolBar.add(getToolAddChildButton());
			jToolBar.add(getToolAddSiblingButton());
			jToolBar.add(getToolAddCommentButton());
			jToolBar.add(getToolDeleteButton());
			jToolBar.add(getToolMoveUpButton());
			jToolBar.add(getToolMoveDownButton());
			jToolBar.add(getToolExpandAllButton());
			jToolBar.add(getToolCollapseAllButton());
		}
		return jToolBar;
	}
	/**
	 * This method initializes toolCopyButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getToolCopyButton() {
		if (toolCopyButton == null) {
			toolCopyButton = new JButton();
			toolCopyButton.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/copy_disabled.gif")));
			toolCopyButton.setIcon(new ImageIcon(getClass().getResource("/icons/copy.gif")));
			toolCopyButton.setToolTipText("Copy");
			toolCopyButton.setPreferredSize(new java.awt.Dimension(20,20));
			toolCopyButton.addActionListener(this);
		}
		return toolCopyButton;
	}
	/**
	 * This method initializes toolCutButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getToolCutButton() {
		if (toolCutButton == null) {
			toolCutButton = new JButton();
			toolCutButton.setToolTipText("Cut");
			toolCutButton.setIcon(new ImageIcon(getClass().getResource("/icons/cut.gif")));
			toolCutButton.setPreferredSize(new java.awt.Dimension(20,20));
			toolCutButton.addActionListener(this);
		}
		return toolCutButton;
	}
	/**
	 * This method initializes toolPasteButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getToolPasteButton() {
		if (toolPasteButton == null) {
			toolPasteButton = new JButton();
			toolPasteButton.setToolTipText("Paste");
			if(XMLReaderFrame.copy==null) toolPasteButton.setEnabled(false);
			toolPasteButton.setIcon(new ImageIcon(getClass().getResource("/icons/paste.gif")));
			toolPasteButton.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/paste_disabled.gif")));
			toolPasteButton.setPreferredSize(new java.awt.Dimension(20,20));
			toolPasteButton.addActionListener(this);
		}
		return toolPasteButton;
	}
	/**
	 * This method initializes toolDeleteButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getToolDeleteButton() {
		if (toolDeleteButton == null) {
			toolDeleteButton = new JButton();
			toolDeleteButton.setToolTipText("Delete");
			toolDeleteButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.gif")));
			toolDeleteButton.setPreferredSize(new java.awt.Dimension(20,20));
			toolDeleteButton.addActionListener(this);
		}
		return toolDeleteButton;
	}
	/**
	 * This method initializes toolMoveUpButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getToolMoveUpButton() {
		if (toolMoveUpButton == null) {
			toolMoveUpButton = new JButton();
			toolMoveUpButton.setIcon(new ImageIcon(getClass().getResource("/icons/moveup.gif")));
			toolMoveUpButton.setToolTipText("Move up");
			toolMoveUpButton.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/moveup_disabled.gif")));
			toolMoveUpButton.setPreferredSize(new java.awt.Dimension(20,20));
			toolMoveUpButton.addActionListener(this);
		}
		return toolMoveUpButton;
	}
	/**
	 * This method initializes toolMoveDownButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getToolMoveDownButton() {
		if (toolMoveDownButton == null) {
			toolMoveDownButton = new JButton();
			toolMoveDownButton.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/movedown_disabled.gif")));
			toolMoveDownButton.setToolTipText("Move down");
			toolMoveDownButton.setIcon(new ImageIcon(getClass().getResource("/icons/movedown.gif")));
			toolMoveDownButton.setPreferredSize(new java.awt.Dimension(20,20));
			toolMoveDownButton.addActionListener(this);
		}
		return toolMoveDownButton;
	}
	/**
	 * This method initializes addChildMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getAddChildMenuItem() {
		if (addChildMenuItem == null) {
			addChildMenuItem = new JMenuItem("Child node");
			addChildMenuItem.addActionListener(this);
		}
		return addChildMenuItem;
	}
	/**
	 * This method initializes toolAddChildButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getToolAddChildButton() {
		if (toolAddChildButton == null) {
			toolAddChildButton = new JButton();
			toolAddChildButton.setToolTipText("Add child node");
			toolAddChildButton.setIcon(new ImageIcon(getClass().getResource("/icons/add_child.gif")));
			toolAddChildButton.setPreferredSize(new java.awt.Dimension(20,20));
			toolAddChildButton.addActionListener(this);
		}
		return toolAddChildButton;
	}
	/**
	 * This method initializes attributeTablePanel	
	 * 	
	 * @return com.ibm.ecc.ui.AttributeTablePanel	
	 */    
	private AttributeTablePanel getAttributeTablePanel() {
		if (attributeTablePanel == null) {
			attributeTablePanel = new AttributeTablePanel();
		}
		return attributeTablePanel;
	}
	/**
	 * This method initializes addCommentMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getAddCommentMenuItem() {
		if (addCommentMenuItem == null) {
			addCommentMenuItem = new JMenuItem("Add comment");
			addCommentMenuItem.addActionListener(this);
		}
		return addCommentMenuItem;
	}
	/**
	 * This method initializes toolAddCommentButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getToolAddCommentButton() {
		if (toolAddCommentButton == null) {
			toolAddCommentButton = new JButton();
			toolAddCommentButton.setToolTipText("Add Comment");
			toolAddCommentButton.setIcon(new ImageIcon(getClass().getResource("/icons/add-comment.gif")));
			toolAddCommentButton.setPreferredSize(new java.awt.Dimension(20,20));
			toolAddCommentButton.addActionListener(this);
		}
		return toolAddCommentButton;
	}
	public XMLTreeNode getRootNode() {
		return rootNode;
	}
	public XMLTreeNode getSelectedNode(){
		XMLTreeNode node = (XMLTreeNode)getXmlTree().getSelectionPath().getLastPathComponent();
		return node;
	}
	/**
	 * This method initializes toolExpandAllButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getToolExpandAllButton() {
		if (toolExpandAllButton == null) {
			toolExpandAllButton = new JButton();
			toolExpandAllButton.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/expandall_disabled.gif")));
			toolExpandAllButton.setToolTipText("Expand All");
			toolExpandAllButton.setIcon(new ImageIcon(getClass().getResource("/icons/expandall.gif")));
			toolExpandAllButton.setPreferredSize(new java.awt.Dimension(20,20));
			toolExpandAllButton.addActionListener(this);
		}
		return toolExpandAllButton;
	}
	/**
	 * This method initializes toolCollapseAllButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getToolCollapseAllButton() {
		if (toolCollapseAllButton == null) {
			toolCollapseAllButton = new JButton();
			toolCollapseAllButton.setIcon(new ImageIcon(getClass().getResource("/icons/collapseall.gif")));
			toolCollapseAllButton.setPreferredSize(new java.awt.Dimension(20,20));
			toolCollapseAllButton.setToolTipText("Collapse All");
			toolCollapseAllButton.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/collapseall_disabled.gif")));
			toolCollapseAllButton.addActionListener(this);
		}
		return toolCollapseAllButton;
	}
	/**
	 * This method initializes expandAllMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getExpandAllMenuItem() {
		if (expandAllMenuItem == null) {
			expandAllMenuItem = new JMenuItem("Expand all");
			expandAllMenuItem.addActionListener(this);
		}
		return expandAllMenuItem;
	}
	/**
	 * This method initializes collpaseAllMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getCollpaseAllMenuItem() {
		if (collpaseAllMenuItem == null) {
			collpaseAllMenuItem = new JMenuItem("Collapse all");
			collpaseAllMenuItem.addActionListener(this);
		}
		return collpaseAllMenuItem;
	}
	/**
	 * This method initializes toolAddSiblingButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getToolAddSiblingButton() {
		if (toolAddSiblingButton == null) {
			toolAddSiblingButton = new JButton();
			toolAddSiblingButton.setIcon(new ImageIcon(getClass().getResource("/icons/add_sibling.gif")));
			toolAddSiblingButton.setPreferredSize(new java.awt.Dimension(20,20));
			toolAddSiblingButton.setToolTipText("Add sibling node");
			toolAddSiblingButton.addActionListener(this);
		}
		return toolAddSiblingButton;
	}
	/**
	 * This method initializes addMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */    
	private JMenu getAddMenu() {
		if (addMenu == null) {
			addMenu = new JMenu("Add");
			addMenu.add(getAddChildMenuItem());
			addMenu.add(getAddSiblingMenuItem());
		}
		return addMenu;
	}
	/**
	 * This method initializes addSiblingMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getAddSiblingMenuItem() {
		if (addSiblingMenuItem == null) {
			addSiblingMenuItem = new JMenuItem("Sibling node");
			addSiblingMenuItem.addActionListener(this);
		}
		return addSiblingMenuItem;
	}
	public File getXmlFile() {
		return xmlFile;
	}
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */    
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Node Editor", null, getXmlContentsSplitPane(), null);
			jTabbedPane.addTab("Node code preview", null, getJPanel(), null);
		}
		return jTabbedPane;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
			jPanel.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
			jPanel.add(getJPanel2(), java.awt.BorderLayout.NORTH);
		}
		return jPanel;
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getXmlEditorPane());
		}
		return jScrollPane1;
	}
	/**
	 * This method initializes xmlEditorPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */    
	private XMLTextPane getXmlEditorPane() {
		if (xmlEditorPane == null) {
			xmlEditorPane = new XMLTextPane();
			xmlEditorPane.setEditable(false);
			xmlEditorPane.setProgressBar(getJProgressBar());
			//xmlEditorPane.setFont(new java.awt.Font("Courier New", java.awt.Font.PLAIN, 12));
		}
		return xmlEditorPane;
	}
	/**
	 * This method initializes jToolBar1	
	 * 	
	 * @return javax.swing.JToolBar	
	 */    
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.setFloatable(false);
			jToolBar1.add(getChildInsertButton());
			jToolBar1.add(getChildRemoveButton());
		}
		return jToolBar1;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			FlowLayout flowLayout11 = new FlowLayout();
			jPanel1 = new JPanel();
			jPanel1.setLayout(flowLayout11);
			flowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel1.add(getJProgressBar(), null);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */    
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setPreferredSize(new java.awt.Dimension(250,14));
			jProgressBar.setName("");
		}
		return jProgressBar;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			FlowLayout flowLayout3 = new FlowLayout();
			jPanel2 = new JPanel();
			jPanel2.setLayout(flowLayout3);
			flowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel2.add(getDecorateXMLToggleButton(), null);
		}
		return jPanel2;
	}
	/**
	 * This method initializes decorateXMLToggleButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */    
	private JToggleButton getDecorateXMLToggleButton() {
		if (decorateXMLToggleButton == null) {
			decorateXMLToggleButton = new JToggleButton();
			decorateXMLToggleButton.setText("Decorate XML");
			decorateXMLToggleButton.setIcon(new ImageIcon(getClass().getResource("/icons/xml.gif")));
			decorateXMLToggleButton.addActionListener(this);
		}
		return decorateXMLToggleButton;
	}
            }  //  @jve:decl-index=0:visual-constraint="10,10"

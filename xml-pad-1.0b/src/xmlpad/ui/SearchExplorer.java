/*
 * Created on 06/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.tree.TreePath;

import xmlpad.util.ReplaceConfig;
import xmlpad.util.SearchConfig;
import xmlpad.util.SearchEngineStrategy;
import xmlpad.util.XMLTreeNode;

/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchExplorer extends JPanel implements ActionListener, KeyListener, MouseListener{
	private JTabbedPane jTabbedPane = null;
	private JPanel searchPanel = null;
	private JPanel replacePanel = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JTextField textField = null;
	private JCheckBox caseSensitiveCheckBox = null;
	private JCheckBox wholeWordCheckBox = null;
	private JPanel jPanel2 = null;
	private JRadioButton startRootRadioButton = null;
	private JRadioButton startSelectedRadioButton = null;
	private JPanel jPanel3 = null;
	private JRadioButton searchElementRadioButton = null;
	private JRadioButton searchAttNameRadioButton = null;
	private JRadioButton searchAttValueRadioButton = null;
	private JButton searchButton = null;
	private XMLReaderFrame parent = null;
	//private ArrayList searchList = null;
	private SearchEngineStrategy searchModel = null;
	private int currPos = -1;
	private boolean newSearch = false;
	private JPanel jPanel4 = null;
	private JScrollPane jScrollPane = null;
	private JLabel jLabel = null;
	private JRadioButton searchElementValueRadioButton = null;
	private JRadioButton searchCommentRadioButton = null;
	private JRadioButton searchAnyRadioButton = null;
	private JPanel jPanel5 = null;
	private JScrollPane jScrollPane1 = null;
	private JList searchResultsList = null;
	private JToolBar jToolBar = null;
	private JButton searchNextButton = null;
	private JButton searchPrevButton = null;
	private DefaultListModel searchList = null;
	private JPanel replacePlainTextPanel = null;
	private JPanel replaceEntityPanel = null;
	private JPanel replacingTextControlPanel = null;
	private JTextField replacingTextField = null;
	private JRadioButton replaceMatchingTextRadioButton = null;
	private JRadioButton replaceWholeTextRadioButton = null;
	private JPanel replaceControlsPanel = null;
	private JPanel replaceButtonsPanel = null;
	private JButton replaceAllButton = null;
	private JButton replaceButton = null;
	private JButton replaceSearchNextButton = null;
	private JPanel jPanel6 = null;
	private XMLSamplePanel xmlSamplePanel = null;
	private JPanel jPanel7 = null;
	/**
	 * This is the default constructor
	 */
	public SearchExplorer(XMLReaderFrame parent) {
		super();
		this.parent = parent;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(714, 366);
		this.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		this.add(getJPanel5(), java.awt.BorderLayout.SOUTH);
		searchModel = new SearchEngineStrategy(parent.getSelectedTreeModel());
		enableReplaceButtons(false);
	}
	public void keyPressed(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
		newSearch = true;
		if(e.getSource()==getSearchResultsList()){
			currPos = getSearchResultsList().getSelectedIndex();
			syncParentWithList();
		}
	}
	public void keyTyped(KeyEvent e) {
	}
	private ReplaceConfig extractReplaceConfig(SearchConfig sConfig){
		ReplaceConfig config = new ReplaceConfig(sConfig);
		config.setReplaceString(getReplacingTextField().getText());
		config.setReplaceMethod(ReplaceConfig.REPLACE_PLAIN_TEXT_METHOD);
		if(getReplaceMatchingTextRadioButton().isSelected())
			config.setMatchingText(ReplaceConfig.PLAIN_REPLACE_MATCHING_TEXT);
		else if(getReplaceWholeTextRadioButton().isSelected())
			config.setMatchingText(ReplaceConfig.PLAIN_REPLACE_WHOLE_TEXT);
		return config;
	}
	private SearchConfig extractSearchConfig(){
		SearchConfig config = null;
		if(getStartRootRadioButton().isSelected()){
			config = new SearchConfig(parent.getSelectedTreeModel(), getTextField().getText());
		}else{
			config = new SearchConfig(parent.getSelectedTreeModelSelectedNode(), getTextField().getText());
		}
		if(getSearchElementRadioButton().isSelected()){
			config.setMatchingType(SearchConfig.MATCHING_TYPE_ELEMENT_NAME);
		}else if(getSearchElementValueRadioButton().isSelected()){
			config.setMatchingType(SearchConfig.MATCHING_TYPE_ELEMENT_VALUE);
		}else if(getSearchAttValueRadioButton().isSelected()){
			config.setMatchingType(SearchConfig.MATCHING_TYPE_ATTRIBUTE_VALUE);
		}else if(getSearchAttNameRadioButton().isSelected()){
			config.setMatchingType(SearchConfig.MATCHING_TYPE_ATTRIBUTE_NAME);
		}else if(getSearchCommentRadioButton().isSelected()){
			config.setMatchingType(SearchConfig.MATCHING_TYPE_COMMENT);
		}else if(getSearchAnyRadioButton().isSelected()){
			config.setMatchingType(SearchConfig.MATCHING_TYPE_ANY);
		}
		if(getCaseSensitiveCheckBox().isSelected() && getWholeWordCheckBox().isSelected()){
			config.setCaseSensitiveWholeWord();
		}else if(getCaseSensitiveCheckBox().isSelected() && !getWholeWordCheckBox().isSelected()){
			config.setCaseSensitiveFragment();
		}else if(!getCaseSensitiveCheckBox().isSelected() && getWholeWordCheckBox().isSelected()){
			config.setWholeWordFragment();
		}else if(!getCaseSensitiveCheckBox().isSelected() && !getWholeWordCheckBox().isSelected()){
			config.setFragment();
		}
		return config;
	}
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource()==getSearchButton()){
			XMLTreeNode tree = null;
			if(getStartRootRadioButton().isSelected()){
				tree = parent.getSelectedTreeModel();
			}else{
				tree = parent.getSelectedTreeModelSelectedNode();
			}
			tree.blankSelected();
			searchModel.setSearchConfig(extractSearchConfig());
			refreshSearchList(searchModel.getSearch().search());
		}else if(evt.getSource()==getSearchNextButton() ||
					evt.getSource()==getReplaceSearchNextButton()){
			selectNext();
		}else if(evt.getSource()==getSearchPrevButton()){
			selectPrevious();
		}else if(evt.getSource()==getReplaceButton()){
			XMLTreeNode selNode = parent.getSelectedTreeModelSelectedNode();
			ReplaceConfig config = extractReplaceConfig(searchModel.getConfig());
			selNode.replace(config);
			parent.refreshSelectedTreeView();
		}else if(evt.getSource()==getReplaceAllButton()){
			ReplaceConfig config = extractReplaceConfig(searchModel.getConfig());
			for(int i=0;i<searchList.size();i++){
				TreePath path = (TreePath)searchList.get(i);
				XMLTreeNode n = parent.getXMLTreeNode(path);
				n.replace(config);
			}
			parent.getSelectedTree().setSelectionPath((TreePath)searchList.get(currPos));
			parent.refreshSelectedTreeView();
			paintAll(getGraphics());
		}else if(evt.getSource()==getSearchElementRadioButton()){
			xmlSamplePanel.setImage(xmlSamplePanel.elementName);
		}else if(evt.getSource()==getSearchElementValueRadioButton()){
			xmlSamplePanel.setImage(xmlSamplePanel.elementValue);
		}else if(evt.getSource()==getSearchCommentRadioButton()){
			xmlSamplePanel.setImage(xmlSamplePanel.comment);
		}else if(evt.getSource()==getSearchAttNameRadioButton()){
			xmlSamplePanel.setImage(xmlSamplePanel.attName);
		}else if(evt.getSource()==getSearchAttValueRadioButton()){
			xmlSamplePanel.setImage(xmlSamplePanel.attValue);
		}else if(evt.getSource()==getSearchAnyRadioButton()){
			xmlSamplePanel.setImage(xmlSamplePanel.any);
		}
		
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==getSearchResultsList()){
			if(searchList.size()==0) return;
			currPos = getSearchResultsList().getSelectedIndex();
			syncParentWithList();
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	private void refreshSearchList(ArrayList list){
		searchList.removeAllElements();
		for(int i=0;i<list.size();i++){
			searchList.addElement(list.get(i));
		}
		if(searchList.size()>0){
			getSearchPrevButton().setEnabled(true);
			getSearchNextButton().setEnabled(true);
			currPos = -1;
			getSearchResultsList().setSelectedIndex(currPos);
			selectNext();
		}else{
			getSearchPrevButton().setEnabled(false);
			getSearchNextButton().setEnabled(false);
		}
	}
	private void syncParentWithList(){
		parent.getSelectedTree().setSelectionPath((TreePath)searchList.get(currPos));
		parent.getSelectedTree().scrollPathToVisible((TreePath)searchList.get(currPos));
		parent.refreshSelectedTreeView();
	}
	void enableReplaceButtons(boolean bool){
		getReplaceAllButton().setEnabled(bool);
		getReplaceButton().setEnabled(bool);
		getReplaceSearchNextButton().setEnabled(bool);
	}
	void blankSelectedTree(){
		searchList.removeAllElements();
		parent.getSelectedTreeModel().blankSelected();
		parent.refreshSelectedTreeView();
	}
	private void selectNext(){
		if(searchList==null || searchList.size()==0) {
			enableReplaceButtons(false);
			return;
		}
		enableReplaceButtons(true);
		currPos++;
		if(currPos==searchList.size()) currPos = 0;		
		getSearchResultsList().setSelectedIndex(currPos);
		getSearchResultsList().ensureIndexIsVisible(currPos);
		syncParentWithList();
	}
	private void selectPrevious(){
		if(searchList==null || searchList.size()==0){
			enableReplaceButtons(false);
			return;
		}
		enableReplaceButtons(true);
		currPos--;
		if(currPos<0) currPos = searchList.size()-1;
		getSearchResultsList().setSelectedIndex(currPos);
		getSearchResultsList().ensureIndexIsVisible(currPos);
		syncParentWithList();
	}
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */    
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.setPreferredSize(new java.awt.Dimension(80,37));
			jTabbedPane.addTab("Search", new ImageIcon(getClass().getResource("/icons/search-tab.gif")), getSearchPanel(), null);
			jTabbedPane.addTab("Replace", new ImageIcon(getClass().getResource("/icons/replace.gif")), getReplacePanel(), null);
		}
		return jTabbedPane;
	}
	/**
	 * This method initializes searchPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getSearchPanel() {
		if (searchPanel == null) {
			searchPanel = new JPanel();
			searchPanel.setLayout(new BorderLayout());
			searchPanel.add(getJPanel4(), java.awt.BorderLayout.SOUTH);
			searchPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return searchPanel;
	}
			

	/**
	 * This method initializes replacePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getReplacePanel() {
		if (replacePanel == null) {
			replacePanel = new JPanel();
			replacePanel.setLayout(new BorderLayout());
			replacePanel.add(getReplaceControlsPanel(), java.awt.BorderLayout.CENTER);
			replacePanel.add(getReplaceButtonsPanel(), java.awt.BorderLayout.SOUTH);
		}
		return replacePanel;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJPanel2(), null);
			jPanel.add(getJPanel6(), null);
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			FlowLayout flowLayout4 = new FlowLayout();
			jPanel1 = new JPanel();
			jPanel1.setLayout(flowLayout4);
			flowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Text to search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel1.add(getTextField(), null);
			jPanel1.add(getCaseSensitiveCheckBox(), null);
			jPanel1.add(getWholeWordCheckBox(), null);
		}
		return jPanel1;
	}
	/**
	 * This method initializes textField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(25);
			textField.addKeyListener(this);
		}
		return textField;
	}
	/**
	 * This method initializes caseSensitiveCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getCaseSensitiveCheckBox() {
		if (caseSensitiveCheckBox == null) {
			caseSensitiveCheckBox = new JCheckBox();
			caseSensitiveCheckBox.setText("Case-sensitive");
			caseSensitiveCheckBox.addActionListener(this);
		}
		return caseSensitiveCheckBox;
	}
	/**
	 * This method initializes wholeWordCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getWholeWordCheckBox() {
		if (wholeWordCheckBox == null) {
			wholeWordCheckBox = new JCheckBox();
			wholeWordCheckBox.setText("Whole word");
			wholeWordCheckBox.addActionListener(this);
		}
		return wholeWordCheckBox;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			FlowLayout flowLayout5 = new FlowLayout();
			jPanel2 = new JPanel();
			jPanel2.setLayout(flowLayout5);
			flowLayout5.setAlignment(java.awt.FlowLayout.LEFT);
			jLabel.setText("Start searching from:");
			jPanel2.add(jLabel, null);
			jPanel2.add(getStartRootRadioButton(), null);
			jPanel2.add(getStartSelectedRadioButton(), null);
			ButtonGroup group = new ButtonGroup();
			group.add(getStartRootRadioButton());
			group.add(getStartSelectedRadioButton());
			getStartRootRadioButton().setSelected(true);
		}
		return jPanel2;
	}
	/**
	 * This method initializes startRootRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getStartRootRadioButton() {
		if (startRootRadioButton == null) {
			startRootRadioButton = new JRadioButton();
			startRootRadioButton.setText("XML root");
			startRootRadioButton.addActionListener(this);
		}
		return startRootRadioButton;
	}
	/**
	 * This method initializes startSelectedRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getStartSelectedRadioButton() {
		if (startSelectedRadioButton == null) {
			startSelectedRadioButton = new JRadioButton();
			startSelectedRadioButton.setText("XML selected node");
			startSelectedRadioButton.addActionListener(this);
		}
		return startSelectedRadioButton;
	}
	/**
	 * This method initializes jPanel3	
	 *			flowLayout7.setAlignment(java.awt.FlowLayout.LEFT);
 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			GridLayout gridLayout3 = new GridLayout();
			jPanel3 = new JPanel();
			jPanel3.setLayout(gridLayout3);
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search for", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			gridLayout3.setColumns(2);
			gridLayout3.setRows(3);
			jPanel3.add(getSearchElementRadioButton(), null);
			jPanel3.add(getSearchElementValueRadioButton(), null);
			jPanel3.add(getSearchCommentRadioButton(), null);
			jPanel3.add(getSearchAnyRadioButton(), null);
			jPanel3.add(getSearchAttNameRadioButton(), null);
			jPanel3.add(getSearchAttValueRadioButton(), null);
			ButtonGroup group = new ButtonGroup();
			group.add(getSearchElementRadioButton());
			group.add(getSearchAttNameRadioButton());
			group.add(getSearchAttValueRadioButton());
			group.add(getSearchElementValueRadioButton());
			group.add(getSearchCommentRadioButton());
			group.add(getSearchAnyRadioButton());
			getSearchAnyRadioButton().setSelected(true);
		}
		return jPanel3;
	}
	/**
	 * This method initializes searchElementRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getSearchElementRadioButton() {
		if (searchElementRadioButton == null) {
			searchElementRadioButton = new JRadioButton();
			searchElementRadioButton.setText("An element name");
			searchElementRadioButton.addActionListener(this);
			//searchElementRadioButton.setIcon(new ImageIcon(getClass().getResource("/icons/element.gif")));
		}
		return searchElementRadioButton;
	}
	/**
	 * This method initializes searchAttNameRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getSearchAttNameRadioButton() {
		if (searchAttNameRadioButton == null) {
			searchAttNameRadioButton = new JRadioButton();
			searchAttNameRadioButton.setText("An attribute name");
			searchAttNameRadioButton.addActionListener(this);
			//searchAttNameRadioButton.setIcon(new ImageIcon(getClass().getResource("/icons/attribute.gif")));
		}
		return searchAttNameRadioButton;
	}
	/**
	 * This method initializes searchAttValueRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getSearchAttValueRadioButton() {
		if (searchAttValueRadioButton == null) {
			searchAttValueRadioButton = new JRadioButton();
			searchAttValueRadioButton.setText("An attribute value");
			searchAttValueRadioButton.addActionListener(this);
			//searchAttValueRadioButton.setIcon(new ImageIcon(getClass().getResource("/icons/attribute_value.gif")));
		}
		return searchAttValueRadioButton;
	}
	/**
	 * This method initializes searchButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getSearchButton() {
		if (searchButton == null) {
			searchButton = new JButton();
			searchButton.setText("Search");
			searchButton.addActionListener(this);
		}
		return searchButton;
	}
	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			jPanel4 = new JPanel();
			jPanel4.setLayout(flowLayout2);
			flowLayout2.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel4.add(getSearchButton(), null);
		}
		return jPanel4;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJPanel());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes searchElementValueRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getSearchElementValueRadioButton() {
		if (searchElementValueRadioButton == null) {
			searchElementValueRadioButton = new JRadioButton();
			searchElementValueRadioButton.setText("An element value");
			searchElementValueRadioButton.addActionListener(this);
		}
		return searchElementValueRadioButton;
	}
	/**
	 * This method initializes searchCommentRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getSearchCommentRadioButton() {
		if (searchCommentRadioButton == null) {
			searchCommentRadioButton = new JRadioButton();
			searchCommentRadioButton.setText("A comment");
			searchCommentRadioButton.addActionListener(this);
		}
		return searchCommentRadioButton;
	}
	/**
	 * This method initializes searchAnyRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getSearchAnyRadioButton() {
		if (searchAnyRadioButton == null) {
			searchAnyRadioButton = new JRadioButton();
			searchAnyRadioButton.setText("Any text (element, comment, etc)");
			searchAnyRadioButton.addActionListener(this);
		}
		return searchAnyRadioButton;
	}
	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Results", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel5.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
			jPanel5.add(getJToolBar(), java.awt.BorderLayout.WEST);
		}
		return jPanel5;
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getSearchResultsList());
			jScrollPane1.setPreferredSize(new java.awt.Dimension(259,40));
			jScrollPane1.setAutoscrolls(true);
		}
		return jScrollPane1;
	}
	/**
	 * This method initializes searchResultsList	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getSearchResultsList() {
		if (searchResultsList == null) {
			searchList = new DefaultListModel();
			searchResultsList = new JList(searchList);
			searchResultsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			searchResultsList.addMouseListener(this);
			searchResultsList.addKeyListener(this);
		}
		return searchResultsList;
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
			jToolBar.setOrientation(javax.swing.JToolBar.VERTICAL);
			jToolBar.add(getSearchPrevButton());
			jToolBar.add(getSearchNextButton());
		}
		return jToolBar;
	}
	/**
	 * This method initializes searchNextButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getSearchNextButton() {
		if (searchNextButton == null) {
			searchNextButton = new JButton();
			searchNextButton.setToolTipText("Next");
			searchNextButton.setIcon(new ImageIcon(getClass().getResource("/icons/movedown.gif")));
			searchNextButton.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/movedown_disabled.gif")));
			searchNextButton.addActionListener(this);
		}
		return searchNextButton;
	}
	/**
	 * This method initializes searchPrevButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getSearchPrevButton() {
		if (searchPrevButton == null) {
			searchPrevButton = new JButton();
			searchPrevButton.setToolTipText("Previous");
			searchPrevButton.setIcon(new ImageIcon(getClass().getResource("/icons/moveup.gif")));
			searchPrevButton.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/moveup_disabled.gif")));
			searchPrevButton.addActionListener(this);
		}
		return searchPrevButton;
	}
	/**
	 * This method initializes replacePlainTextPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getReplacePlainTextPanel() {
		if (replacePlainTextPanel == null) {
			replacePlainTextPanel = new JPanel();
			replacePlainTextPanel.setLayout(new BoxLayout(replacePlainTextPanel, BoxLayout.Y_AXIS));
			replacePlainTextPanel.add(getReplacingTextControlPanel(), null);
		}
		return replacePlainTextPanel;
	}
	/**
	 * This method initializes replaceEntityPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getReplaceEntityPanel() {
		if (replaceEntityPanel == null) {
			replaceEntityPanel = new JPanel();
			replaceEntityPanel.setVisible(false);
		}
		return replaceEntityPanel;
	}
	/**
	 * This method initializes replacingTextControlPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getReplacingTextControlPanel() {
		if (replacingTextControlPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			replacingTextControlPanel = new JPanel();
			replacingTextControlPanel.setLayout(flowLayout1);
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			replacingTextControlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Replacing text", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			replacingTextControlPanel.add(getReplacingTextField(), null);
			replacingTextControlPanel.add(getReplaceMatchingTextRadioButton(), null);
			replacingTextControlPanel.add(getReplaceWholeTextRadioButton(), null);
			ButtonGroup group = new ButtonGroup();
			group.add(getReplaceMatchingTextRadioButton());
			group.add(getReplaceWholeTextRadioButton());			
		}
		return replacingTextControlPanel;
	}
	/**
	 * This method initializes replacingTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getReplacingTextField() {
		if (replacingTextField == null) {
			replacingTextField = new JTextField();
			replacingTextField.setColumns(25);
		}
		return replacingTextField;
	}
	/**
	 * This method initializes replaceMatchingTextRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getReplaceMatchingTextRadioButton() {
		if (replaceMatchingTextRadioButton == null) {
			replaceMatchingTextRadioButton = new JRadioButton();
			replaceMatchingTextRadioButton.setText("Matching search text");
			replaceMatchingTextRadioButton.setSelected(true);
		}
		return replaceMatchingTextRadioButton;
	}
	/**
	 * This method initializes replaceWholeTextRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getReplaceWholeTextRadioButton() {
		if (replaceWholeTextRadioButton == null) {
			replaceWholeTextRadioButton = new JRadioButton();
			replaceWholeTextRadioButton.setText("Replacing whole text");
		}
		return replaceWholeTextRadioButton;
	}
	/**
	 * This method initializes replaceControlsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getReplaceControlsPanel() {
		if (replaceControlsPanel == null) {
			replaceControlsPanel = new JPanel();
			replaceControlsPanel.setLayout(new BoxLayout(replaceControlsPanel, BoxLayout.Y_AXIS));
			replaceControlsPanel.add(getReplacePlainTextPanel(), null);
			replaceControlsPanel.add(getReplaceEntityPanel(), null);
		}
		return replaceControlsPanel;
	}
	/**
	 * This method initializes replaceButtonsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getReplaceButtonsPanel() {
		if (replaceButtonsPanel == null) {
			FlowLayout flowLayout21 = new FlowLayout();
			replaceButtonsPanel = new JPanel();
			replaceButtonsPanel.setLayout(flowLayout21);
			flowLayout21.setAlignment(java.awt.FlowLayout.RIGHT);
			replaceButtonsPanel.add(getReplaceButton(), null);
			replaceButtonsPanel.add(getReplaceSearchNextButton(), null);
			replaceButtonsPanel.add(getReplaceAllButton(), null);
		}
		return replaceButtonsPanel;
	}
	/**
	 * This method initializes replaceAllButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getReplaceAllButton() {
		if (replaceAllButton == null) {
			replaceAllButton = new JButton();
			replaceAllButton.setText("Replace All");
			replaceAllButton.addActionListener(this);
		}
		return replaceAllButton;
	}
	/**
	 * This method initializes replaceButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getReplaceButton() {
		if (replaceButton == null) {
			replaceButton = new JButton();
			replaceButton.setText("Replace");
			replaceButton.addActionListener(this);
		}
		return replaceButton;
	}
	/**
	 * This method initializes replaceSearchNextButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getReplaceSearchNextButton() {
		if (replaceSearchNextButton == null) {
			replaceSearchNextButton = new JButton();
			replaceSearchNextButton.setText("Search Next");
			replaceSearchNextButton.addActionListener(this);
		}
		return replaceSearchNextButton;
	}
	/**
	 * This method initializes jPanel6	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BoxLayout(jPanel6, BoxLayout.X_AXIS));
			jPanel6.add(getJPanel3(), null);
			jPanel6.add(getJPanel7(), null);
		}
		return jPanel6;
	}
	/**
	 * This method initializes XMLSamplePanel	
	 * 	
	 * @return com.ibm.ecc.ui.XMLSamplePanel	
	 */    
	private XMLSamplePanel getXMLSamplePanel() {
		if (xmlSamplePanel == null) {
			xmlSamplePanel = new XMLSamplePanel();
		}
		return xmlSamplePanel;
	}
	/**
	 * This method initializes jPanel7	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preview", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel7.add(getXMLSamplePanel(), null);
		}
		return jPanel7;
	}
        }  //  @jve:decl-index=0:visual-constraint="10,10"

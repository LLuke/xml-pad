/*
 * Created on 02/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.ui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.TreePath;

import xmlpad.util.XMLTreeNode;
/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMLReaderFrame extends JFrame implements ActionListener, ChangeListener{


	private javax.swing.JPanel jContentPane = null;
	private String lastDir = "";
	private JMenuBar mainMenuBar = null;
	private JMenu fileMenu = null;
	private JMenuItem fileOpenMenuItem = null;
	private JTabbedPane filesTabbedPane = null;
	private JMenuItem fileNewMenuItem = null;
	private JToolBar searchToolBar = null;
	static XMLTreeNode copy[] = null;
	/**
	 * This is the default constructor
	 */
	public XMLReaderFrame() {
		super();
		initialize();
	}
	public XMLTreeNode getSelectedTreeModel(){
		XMLAnalyser xmlPanel = (XMLAnalyser)getFilesTabbedPane().getComponentAt(getFilesTabbedPane().getSelectedIndex());
		return xmlPanel.getRootNode();
	}
	public XMLTreeNode getXMLTreeNode(TreePath path){
		XMLAnalyser xmlPanel = (XMLAnalyser)getFilesTabbedPane().getComponentAt(getFilesTabbedPane().getSelectedIndex());
		getSelectedTree().setSelectionPath(path);
		return xmlPanel.getSelectedNode();
	}
	public XMLTreeNode getSelectedTreeModelSelectedNode(){
		XMLAnalyser xmlPanel = (XMLAnalyser)getFilesTabbedPane().getComponentAt(getFilesTabbedPane().getSelectedIndex());
		return xmlPanel.getSelectedNode();
	}
	public JTree getSelectedTree(){
		XMLAnalyser xmlPanel = (XMLAnalyser)getFilesTabbedPane().getComponentAt(getFilesTabbedPane().getSelectedIndex());
		return xmlPanel.getXmlTree();
	}
	public void refreshSelectedTreeView(){
		XMLAnalyser xmlPanel = (XMLAnalyser)getFilesTabbedPane().getComponentAt(getFilesTabbedPane().getSelectedIndex());
		xmlPanel.refreshControlsFromTree();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setJMenuBar(getMainMenuBar());
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setSize(300,200);
		this.setContentPane(getJContentPane());
		this.setTitle("XMLPad 1.0");
		//searchPanel.add(new SearchExplorer(this),BorderLayout.CENTER);
	}
	private static int untitles = 1;
	private JMenuItem fileCloseTabMenuItem = null;
	private JMenuItem fileSaveMenuItem = null;
	private JMenuItem fileExitMenuItem = null;
	private JPanel mainSplitPane = null;
	private JToolBar mainToolBar = null;
	private JButton newButton = null;
	private JButton openButton = null;
	private JButton saveButton = null;
	private JButton closeButton = null;
	private JButton searchButton = null;
	private SearchDialog searchDialog = null;
	private JMenu editMenu = null;
	private JPanel statusBarPanel = null;
	private AnimatedProgressBar animatedProgressBar = null;
	private JMenu aboutMenu = null;
	private JMenuItem aboutMenuItem = null;
	public void stateChanged(ChangeEvent e) {
		/*getEditMenu().setEnabled(false);
		getEditMenu().removeAll();*/
		/*getFileCloseTabMenuItem().setEnabled(true);
		getFileSaveMenuItem().setEnabled(true);
		/*getEditMenu().setEnabled(true);
		Component c = getFilesTabbedPane().getSelectedComponent();
		if(c instanceof XMLAnalyser){
			XMLAnalyser xp = (XMLAnalyser)c;
			xp.updatedEditMenu(getEditMenu());
		}*/
		resetButtons();
	}
	void enabledSearch(boolean bool){
		getSearchButton().setEnabled(bool);
	}
	private void resetButtons(){
		if(getFilesTabbedPane().getComponentCount()<=0){
			getFileCloseTabMenuItem().setEnabled(false);
			getFileSaveMenuItem().setEnabled(false);
			getCloseButton().setEnabled(false);
			getSaveButton().setEnabled(false);
			getSearchButton().setEnabled(false);
			getEditMenu().removeAll();
			getEditMenu().setEnabled(false);
		}else{
			getFileCloseTabMenuItem().setEnabled(true);
			getFileSaveMenuItem().setEnabled(true);
			getCloseButton().setEnabled(true);
			getSaveButton().setEnabled(true);
			getSearchButton().setEnabled(true);
			getEditMenu().setEnabled(true);
		}
		if(getFilesTabbedPane().getSelectedIndex()>=0){
			XMLAnalyser xmlPanel = (XMLAnalyser)getFilesTabbedPane().getComponentAt(getFilesTabbedPane().getSelectedIndex());
			xmlPanel.refreshButtons();	
		}
	}

	public void actionPerformed(ActionEvent evt){
		if(evt.getSource()==getFileOpenMenuItem() ||
				evt.getSource()==getOpenButton()){
			JFileChooser chooser = new JFileChooser();
			FileFilter filter = new FileFilter(){
				public boolean accept(File f){
					if(f.getName().endsWith(".xml") || f.isDirectory()){
						return true;
					}else return false;
				}
				public String getDescription(){
					return "XML files (.xml)";
				}
			};
			chooser.addChoosableFileFilter(filter);

			//chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if(!lastDir.equals(""))
				chooser.setCurrentDirectory(new File(lastDir));
			int returnVal = chooser.showOpenDialog(this);
			
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				//getAnimatedProgressBar().setVisible(true);
				paintAll(getGraphics());
				File xmlFile = chooser.getSelectedFile();
				lastDir = chooser.getSelectedFile().getAbsolutePath();
			/*	getEditMenu().setEnabled(false);
				getEditMenu().removeAll();*/
				try{
					getFilesTabbedPane().add(xmlFile.getName(), new XMLAnalyser(xmlFile,this));
					getFilesTabbedPane().setSelectedIndex(getFilesTabbedPane().getComponentCount()-1);
					resetButtons();
				}catch(Exception e){
					e.printStackTrace();
				}
				//getAnimatedProgressBar().setVisible(false);
			}
			
		}else if(evt.getSource()==getFileNewMenuItem() ||
				evt.getSource()==getNewButton()){
			try{
				XMLAnalyser panel = new XMLAnalyser(this);
				getFilesTabbedPane().add("Untitled-"+(untitles++), panel);
				getFilesTabbedPane().setSelectedIndex(getFilesTabbedPane().getComponentCount()-1);
				resetButtons();
				panel.setFileView();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(evt.getSource()==getFileCloseTabMenuItem() ||
				evt.getSource()==getCloseButton()){
			getFilesTabbedPane().remove(getFilesTabbedPane().getSelectedIndex());
			resetButtons();
		}else if(evt.getSource()==getSearchButton()){
			getSearchDialog().setVisible(true);
			getSearchButton().setEnabled(false);
			/*getSearchToolBar().setSize(400,300);
			getSearchToolBar().setVisible(true);
			jContentPane.add(getSearchToolBar(), java.awt.BorderLayout.SOUTH);*/
		}else if(evt.getSource()==getFileSaveMenuItem() ||
				evt.getSource()==getSaveButton()){
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileFilter filter = new FileFilter(){
				public boolean accept(File f){
					if(f.getName().endsWith(".xml")  || f.isDirectory()){
						return true;
					}else return false;
				}
				public String getDescription(){
					return "XML files (.xml)";
				}
			};
			chooser.addChoosableFileFilter(filter);
			XMLAnalyser xmlPanel = (XMLAnalyser)getFilesTabbedPane().getComponentAt(getFilesTabbedPane().getSelectedIndex());
			if(xmlPanel.getXmlFile()!=null){
				//chooser.setCurrentDirectory(xmlPanel.getXmlFile());
				chooser.setSelectedFile(xmlPanel.getXmlFile());
			}
			int returnVal = chooser.showSaveDialog(this);

			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File xmlFile = chooser.getSelectedFile();
				xmlPanel.save(xmlFile);
				int currIndex = getFilesTabbedPane().getSelectedIndex();
				ArrayList list = new ArrayList();
				for(int i=0;i<getFilesTabbedPane().getComponentCount();i++){
					Object o[] = {
							getFilesTabbedPane().getComponentAt(i),
							currIndex==i?xmlFile.getName():getFilesTabbedPane().getTitleAt(i)
						};
					list.add(o);
				}
				getFilesTabbedPane().removeAll();
				for(int i=0;i<list.size();i++){
					Object o[] = (Object[])list.get(i);
					getFilesTabbedPane().addTab((String)o[1],(Component)o[0]);
				}
				getFilesTabbedPane().setSelectedIndex(currIndex);
				//getFilesTabbedPane().getcom
			}
		}else if(evt.getSource()==aboutMenuItem){
			JOptionPane.showMessageDialog(this,
					"This tool is designed by Aurélio Calegari. For more info, please contact aureliocalegari@hotmail.com",
					"About XMLPad",
					JOptionPane.INFORMATION_MESSAGE);
		}else if(evt.getSource()==fileExitMenuItem){
			this.dispose();
			System.exit(0);
		}
	}
	private SearchDialog getSearchDialog() {
		if(searchDialog == null) {
			searchDialog = new SearchDialog(this);
		}
		return searchDialog;
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getFilesTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getMainToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getStatusBarPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	/**
	 * This method initializes mainMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */    
	private JMenuBar getMainMenuBar() {
		if (mainMenuBar == null) {
			mainMenuBar = new JMenuBar();
			mainMenuBar.add(getFileMenu());
			mainMenuBar.add(getEditMenu());
			mainMenuBar.add(getAboutMenu());
		}
		return mainMenuBar;
	}
	/**
	 * This method initializes fileMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */    
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getFileNewMenuItem());
			fileMenu.add(getFileSaveMenuItem());
			fileMenu.add(getFileOpenMenuItem());
			fileMenu.add(getFileCloseTabMenuItem());
			fileMenu.add(getFileExitMenuItem());
		}
		return fileMenu;
	}
	/**
	 * This method initializes fileOpenMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getFileOpenMenuItem() {
		if (fileOpenMenuItem == null) {
			fileOpenMenuItem = new JMenuItem("Open");
			fileOpenMenuItem.addActionListener(this);
		}
		return fileOpenMenuItem;
	}
	/**
	 * This method initializes filesTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */    
	private JTabbedPane getFilesTabbedPane() {
		if (filesTabbedPane == null) {
			filesTabbedPane = new JTabbedPane();
			filesTabbedPane.addChangeListener(this);
		}
		return filesTabbedPane;
	}
	/**
	 * This method initializes fileNewMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getFileNewMenuItem() {
		if (fileNewMenuItem == null) {
			fileNewMenuItem = new JMenuItem("New");
			fileNewMenuItem.addActionListener(this);
		}
		return fileNewMenuItem;
	}
	/**
	 * This method initializes fileCloseTabMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getFileCloseTabMenuItem() {
		if (fileCloseTabMenuItem == null) {
			fileCloseTabMenuItem = new JMenuItem("Close current...");
			fileCloseTabMenuItem.setEnabled(false);
			fileCloseTabMenuItem.addActionListener(this);
		}
		return fileCloseTabMenuItem;
	}
	/**
	 * This method initializes fileSaveMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getFileSaveMenuItem() {
		if (fileSaveMenuItem == null) {
			fileSaveMenuItem = new JMenuItem("Save");
			fileSaveMenuItem.addActionListener(this);
			fileSaveMenuItem.setEnabled(false);
		}
		return fileSaveMenuItem;
	}
	/**
	 * This method initializes fileExitMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getFileExitMenuItem() {
		if (fileExitMenuItem == null) {
			fileExitMenuItem = new JMenuItem("Exit");
			fileExitMenuItem.addActionListener(this);
		}
		return fileExitMenuItem;
	}
	/**
	 * This method initializes mainSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JPanel getMainSplitPane() {
		if (mainSplitPane == null) {
			mainSplitPane = new JPanel();
			/*mainSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			mainSplitPane.setBottomComponent(getFilesTabbedPane());
			mainSplitPane.setDividerLocation(0);
			mainSplitPane.setResizeWeight(0.3D);
			mainSplitPane.setOneTouchExpandable(true);
			mainSplitPane.setTopComponent(getSearchPanel());
			mainSplitPane.setDividerSize(15);*/
		}
		return mainSplitPane;
	}
	/**
	 * This method initializes mainToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */    
	private JToolBar getMainToolBar() {
		if (mainToolBar == null) {
			mainToolBar = new JToolBar();
			mainToolBar.add(getSaveButton());
			mainToolBar.add(getNewButton());
			mainToolBar.add(getOpenButton());
			mainToolBar.add(getCloseButton());
			mainToolBar.add(getSearchButton());
		}
		return mainToolBar;
	}
	/*private JToolBar getSearchToolBar() {
		if (searchToolBar == null) {
			searchToolBar = new JToolBar();
			searchToolBar.add(new SearchExplorer(this));
		}
		return searchToolBar;
	}*/
	/**
	 * This method initializes newButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getNewButton() {
		if (newButton == null) {
			newButton = new JButton();
			newButton.setToolTipText("New XML");
			newButton.setIcon(new ImageIcon(getClass().getResource("/icons/newxmlfile.gif")));
			newButton.setPreferredSize(new java.awt.Dimension(20,20));
			newButton.addActionListener(this);
		}
		return newButton;
	}
	/**
	 * This method initializes openButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getOpenButton() {
		if (openButton == null) {
			openButton = new JButton();
			openButton.setPreferredSize(new java.awt.Dimension(20,20));
			openButton.setIcon(new ImageIcon(getClass().getResource("/icons/open.gif")));
			openButton.setToolTipText("Open");
			openButton.addActionListener(this);
		}
		return openButton;
	}
	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setToolTipText("Save");
			saveButton.setIcon(new ImageIcon(getClass().getResource("/icons/save.gif")));
			saveButton.setPreferredSize(new java.awt.Dimension(20,20));
			saveButton.setEnabled(false);
			saveButton.setDisabledIcon(new ImageIcon(getClass().getResource("/icons/save-disabled.gif")));
			saveButton.addActionListener(this);
		}
		return saveButton;
	}
	/**
	 * This method initializes closeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new JButton();
			closeButton.setIcon(new ImageIcon(getClass().getResource("/icons/closed_folder.gif")));
			closeButton.setPreferredSize(new java.awt.Dimension(20,20));
			closeButton.setToolTipText("Close");
			closeButton.setEnabled(false);
			closeButton.addActionListener(this);
		}
		return closeButton;
	}
	/**
	 * This method initializes searchButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getSearchButton() {
		if (searchButton == null) {
			searchButton = new JButton();
			searchButton.setToolTipText("Search");
			searchButton.setEnabled(false);
			searchButton.setIcon(new ImageIcon(getClass().getResource("/icons/searchres.gif")));
			searchButton.setPreferredSize(new java.awt.Dimension(20,20));
			searchButton.addActionListener(this);
		}
		return searchButton;
	}
	/**
	 * This method initializes editMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */    
	JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu("Edit");
			editMenu.setEnabled(false);
		}
		return editMenu;
	}
	/**
	 * This method initializes statusBarPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getStatusBarPanel() {
		if (statusBarPanel == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			statusBarPanel = new JPanel();
			statusBarPanel.setLayout(flowLayout2);
			flowLayout2.setAlignment(java.awt.FlowLayout.RIGHT);
			statusBarPanel.add(getAnimatedProgressBar(), null);
		}
		return statusBarPanel;
	}
	/**
	 * This method initializes animatedProgressBar	
	 * 	
	 * @return com.ibm.ecc.ui.AnimatedProgressBar	
	 */    
	private AnimatedProgressBar getAnimatedProgressBar() {
		if (animatedProgressBar == null) {
			animatedProgressBar = new AnimatedProgressBar();
			animatedProgressBar.setVisible(false);
		}
		return animatedProgressBar;
	}
	/**
	 * This method initializes aboutMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */    
	private JMenu getAboutMenu() {
		if (aboutMenu == null) {
			aboutMenu = new JMenu("About");
			aboutMenu.add(getAboutMenuItem());
			
		}
		return aboutMenu;
	}
	/**
	 * This method initializes aboutMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem("About");
			aboutMenuItem.addActionListener(this);
		}
		return aboutMenuItem;
	}
     }

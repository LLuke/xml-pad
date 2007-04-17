/*
 * Created on 05/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import xmlpad.util.XMLTreeNode;

/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AttributeTablePanel extends JPanel implements ActionListener{
	private JButton addButton = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JPanel attributesPanel = null;
	private ArrayList list = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JTextField jTextField = null;
	private JTextField jTextField1 = null;
	private JPanel jPanel3 = null;
	private XMLTreeNode node = null;
	/**
	 * This is the default constructor
	 */
	public AttributeTablePanel() {
		super();
		initialize();
	}
	public void setEnabled(boolean bool){
		getAddButton().setEnabled(bool);
	}
	public AttributeTablePanel(XMLTreeNode node){
		super();
		this.node = node;
		initialize();
		refreshAttributes();
	}
	public void setTreeNode(XMLTreeNode node){
		this.node = node;
		refreshAttributes();
		this.paintAll(this.getGraphics());
	}
	private void refreshAttributes(){
		attributesPanel.removeAll();
		list = new ArrayList();
		Set set = node.getAttributeName();
		Iterator itr = set.iterator();
		if(node.getType()==XMLTreeNode.TYPE_FILE) addButton.setEnabled(false);
		while(itr.hasNext()){
			String key=(String)itr.next();
			AttributeRowPanel row = new AttributeRowPanel(this);
			if(node.getSelectedAttNames().contains(key)) row.setNameSelected();
			if(node.getSelectedAttValues(key)!=null) row.setValueSelected();
			row.setName(key);
			row.setValue((String)node.getAttributeValue(key));
			if(node.getType()==XMLTreeNode.TYPE_FILE){
				row.setXMLDefDocument(true);
			}
			list.add(row);
			getAttributesPanel().add(row);
			this.paintAll(this.getGraphics());
		}
	}
	void saveValues(){
		node.deleteAttributes();
		for(int i=0;i<list.size();i++){
			AttributeRowPanel pan = (AttributeRowPanel)list.get(i);
			node.addAttribute(pan.getName(),pan.getValue());
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(300,200);
		this.add(getJPanel(), java.awt.BorderLayout.NORTH);
		this.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		list = new ArrayList();
	}
	public void setAttNameSelected(String name){
		for(int i=0;i<list.size();i++){
			AttributeRowPanel row = (AttributeRowPanel)list.get(i);
			if(row.getName().equals(name)) row.setNameSelected();
		}
	}
	public void setAttValueSelected(String name){
		for(int i=0;i<list.size();i++){
			AttributeRowPanel row = (AttributeRowPanel)list.get(i);
			if(row.getName().equals(name)) row.setValueSelected();
		}
	}
	public void resetAttributesSelected(){
		for(int i=0;i<list.size();i++){
			AttributeRowPanel row = (AttributeRowPanel)list.get(i);
			row.setFieldsUnselected();
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==getAddButton()){
			AttributeRowPanel row = new AttributeRowPanel(this);
			list.add(row);
			getAttributesPanel().add(row);
			this.paintAll(this.getGraphics());
		}
	}
	void deleteRow(AttributeRowPanel row){
		list.remove(row);
		getAttributesPanel().remove(row);
		saveValues();
		this.paintAll(this.getGraphics());
	}
	/**
	 * This method initializes addButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setIcon(new ImageIcon(getClass().getResource("/icons/add.gif")));
			addButton.setToolTipText("Add attribute");
			addButton.setText("Add");
			addButton.addActionListener(this);
		}
		return addButton;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout1);
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel.add(getAddButton(), null);
		}
		return jPanel;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getAttributesPanel());
			jScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
		return jScrollPane;
	}
	/**
	 * This method initializes attributesPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getAttributesPanel() {
		if (attributesPanel == null) {
			attributesPanel = new JPanel();
			attributesPanel.setLayout(new BoxLayout(attributesPanel, BoxLayout.Y_AXIS));
		}
		return attributesPanel;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel1.add(getJPanel2(), java.awt.BorderLayout.NORTH);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.X_AXIS));
			jPanel2.add(getJTextField(), null);
			jPanel2.add(getJTextField1(), null);
			jPanel2.add(getJPanel3(), null);
		}
		return jPanel2;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setColumns(25);
			jTextField.setText("Attribute Name");
			jTextField.setEnabled(true);
			jTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			jTextField.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jTextField.setEditable(false);
		}
		return jTextField;
	}
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setColumns(25);
			jTextField1.setText("Attribute Value");
			jTextField1.setEditable(false);
			jTextField1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		}
		return jTextField1;
	}
	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setPreferredSize(new java.awt.Dimension(60,10));
		}
		return jPanel3;
	}
 }

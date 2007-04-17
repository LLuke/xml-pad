/*
 * Created on 05/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.BorderUIResource;
/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AttributeRowPanel extends JPanel implements ActionListener, KeyListener{

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	public void keyReleased(KeyEvent e) {
		if(e.getSource()==getNameField()){
			table.saveValues();
		}else if(e.getSource()==getValueField()){
			table.saveValues();
		}
	}
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	private JTextField nameField = null;
	private JTextField valueField = null;
	private JButton removeButton = null;
	private AttributeTablePanel table = null;
	/**
	 * This is the default constructor
	 */
	public AttributeRowPanel(AttributeTablePanel table) {
		super();
		this.table = table;
		initialize();
		
	}
	public void setXMLDefDocument(boolean bool){
			nameField.setEnabled(!bool);
			removeButton.setEnabled(!bool);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBackground(java.awt.Color.white);
		this.setBounds(0, 0, 620, 20);
		this.add(getNameField(), null);
		this.add(getValueField(), null);
		this.add(getRemoveButton(), null);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==getRemoveButton()){
			table.deleteRow(this);
		}
	}
	/**
	 * This method initializes nameField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getNameField() {
		if (nameField == null) {
			nameField = new JTextField();
			nameField.setColumns(25);
			nameField.setBackground(java.awt.Color.white);
			nameField.setBorder(BorderUIResource.getEtchedBorderUIResource());
			nameField.addKeyListener(this);
		}
		return nameField;
	}
	public void setNameSelected(){
		nameField.setBackground(java.awt.Color.lightGray);
	}
	public void setValueSelected(){
		valueField.setBackground(java.awt.Color.lightGray);
	}
	public void setFieldsUnselected(){
		valueField.setBackground(java.awt.Color.white);
		nameField.setBackground(java.awt.Color.white);
	}
	/**
	 * This method initializes valueField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getValueField() {
		if (valueField == null) {
			valueField = new JTextField();
			valueField.setColumns(25);
			valueField.setBorder(BorderUIResource.getEtchedBorderUIResource());
			valueField.addKeyListener(this);
		}
		return valueField;
	}
	public void setName(String s){
		nameField.setText(s);
	}
	public void setValue(String s){
		valueField.setText(s);
	}
	public String getValue(){
		return valueField.getText();
	}
	public String getName(){
		return nameField.getText();
	}
	/**
	 * This method initializes removeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getRemoveButton() {
		if (removeButton == null) {
			removeButton = new JButton();
			removeButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete.gif")));
			removeButton.setToolTipText("Delete");
			removeButton.setBackground(java.awt.Color.white);
			removeButton.setPreferredSize(new java.awt.Dimension(40,19));
			removeButton.addActionListener(this);
		}
		return removeButton;
	}
   }  //  @jve:decl-index=0:visual-constraint="10,10"

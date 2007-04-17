/*
 * Created on 08/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchDialog extends JDialog {

	private javax.swing.JPanel jContentPane = null;
	private XMLReaderFrame parent = null;
	private SearchExplorer searchExplorer = null;
	/**
	 * This is the default constructor
	 */
	public SearchDialog(XMLReaderFrame parent) {
		super(parent);
		this.parent = parent;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("Search/Replace/Add");
		this.setSize(658,400);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent evt){
					parent.enabledSearch(true);
					getSearchExplored().blankSelectedTree();
					getSearchExplored().enableReplaceButtons(false);
				}
			});
	}
	public void setEnabled(boolean bool){
		super.setEnabled(bool);
		parent.enabledSearch(bool);
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
			jContentPane.add(getSearchExplored(),BorderLayout.CENTER);
		}
		return jContentPane;
	}
	private SearchExplorer getSearchExplored(){
		if(searchExplorer==null){
			searchExplorer = new SearchExplorer(parent);
		}
		return searchExplorer;
	}
}

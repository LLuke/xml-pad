/*
 * Created on 11/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMLSamplePanel extends JPanel /*implements ImageObserver*/{
	public Image comment;;
	public Image elementName;
	public Image elementValue;
	public Image any;
	public Image attName;
	public Image attValue;
	private Image current;
	/**
	 * This is the default constructor
	 */
	public XMLSamplePanel() {
		super();
		comment = (new ImageIcon(getClass().getResource("/pics/comment.gif"))).getImage();
		elementName = (new ImageIcon(getClass().getResource("/pics/element-name.gif"))).getImage();
		elementValue = (new ImageIcon(getClass().getResource("/pics/element-value.gif"))).getImage();
		any = (new ImageIcon(getClass().getResource("/pics/any.gif"))).getImage();
		attName = (new ImageIcon(getClass().getResource("/pics/att-name.gif"))).getImage();
		attValue = (new ImageIcon(getClass().getResource("/pics/att-value.gif"))).getImage();
		current = any;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		this.setPreferredSize(new java.awt.Dimension(195,99));
		this.setSize(195,99);
	}
	public void setImage(Image i){
		current = i;
		paintAll(getGraphics());
	}
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(current,0,0,this);
	}
}

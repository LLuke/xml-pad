/*
 * Created on 12/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.ui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMLTextPane extends JTextPane implements Runnable{
	private int ELEMENT = 0;
	private int COMMENT = 1;
	private int TEXT = 2;
	private int ATTNAME = 3;
	private int ATTVALUE = 4;
	private int type;
	private StyledDocument doc = null;
	private String text = "";
	private JProgressBar bar = null;
	private boolean decoratedText = false;
	public XMLTextPane(){
		super();
		doc = getStyledDocument();
	}
	public void setDecoratedText(boolean bool){
		decoratedText = bool;
		this.setText(text);
	}
	public void setProgressBar(JProgressBar bar){
		this.bar = bar;
		bar.setVisible(false);
	}
	public void setText(String text){
		//remountText(text);
		this.text = text;
		if(decoratedText){
			Thread t = new Thread(this);
			t.start();
		}else{
			setFont(new java.awt.Font("Courier New", java.awt.Font.PLAIN, 12));
			super.setText(text);
		}
	}
	public void run(){
		/*remountText(text);
		paintAll(getGraphics());*/
		try{
			if(bar!=null){
				bar.setVisible(true);
				bar.setMaximum(text.length());
				bar.setMinimum(0);
				bar.setValue(0);
				bar.setString("Rendering 0%");
			}
			doc.remove(0,doc.getLength());
			char[] c = text.toCharArray();
			for(int i=0;i<c.length;i++){
				if(text.length()>i+4 && text.substring(i,i+4).indexOf("<!--")!=-1){
					type = COMMENT;
					doc.insertString(i,"<",buildComment());
				}
				else if(c[i]=='<'){
					type = ELEMENT;
					doc.insertString(i,"<",buildElement());
				}else if(c[i]=='>'){
					if(text.length()>i+1 && i-2>=0 && text.substring(i-2,i+1).indexOf("-->")!=-1){
						doc.insertString(i,">",buildComment());
					}else{
						doc.insertString(i,">",buildElement());
					}
					type = TEXT;
				}else if(type==TEXT){
					doc.insertString(i,c[i]+"",buildText());
				}else if(type==ELEMENT){
					if(c[i]==' ') type = ATTNAME;
					doc.insertString(i,c[i]+"",buildElement());
				}else if(type==COMMENT){
					doc.insertString(i,c[i]+"",buildComment());
				}else if(type==ATTNAME){
					if(c[i]=='"'){
						doc.insertString(i,c[i]+"",buildAttributeValue());
						type=ATTVALUE;
					}else{
						doc.insertString(i,c[i]+"",buildAttributeName());
					}
				}else if(type==ATTVALUE){
					if(c[i]=='"'){
						type = ATTNAME;
					}
					doc.insertString(i,c[i]+"",buildAttributeValue());
				}
				if(bar!=null){
					bar.setString("Rendering "+((int)(i/text.length()))+"%");
					bar.setValue(i);
					
				}
			}
			if(bar!=null) bar.setVisible(false);
			paintAll(getGraphics());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void remountText(String text){
		try{
			if(bar!=null){
				bar.setVisible(true);
				bar.setMaximum(text.length());
				bar.setMinimum(0);
				bar.setValue(0);
				bar.setString("Rendering 0%");
			}
			doc.remove(0,doc.getLength());
			char[] c = text.toCharArray();
			for(int i=0;i<c.length;i++){
				if(text.length()>i+4 && text.substring(i,i+4).indexOf("<!--")!=-1){
					type = COMMENT;
					doc.insertString(i,"<",buildComment());
				}
				else if(c[i]=='<'){
					type = ELEMENT;
					doc.insertString(i,"<",buildElement());
				}else if(c[i]=='>'){
					if(text.length()>i+1 && i-2>=0 && text.substring(i-2,i+1).indexOf("-->")!=-1){
						doc.insertString(i,">",buildComment());
					}else{
						doc.insertString(i,">",buildElement());
					}
					type = TEXT;
				}else if(type==TEXT){
					doc.insertString(i,c[i]+"",buildText());
				}else if(type==ELEMENT){
					if(c[i]==' ') type = ATTNAME;
					doc.insertString(i,c[i]+"",buildElement());
				}else if(type==COMMENT){
					doc.insertString(i,c[i]+"",buildComment());
				}else if(type==ATTNAME){
					if(c[i]=='"'){
						doc.insertString(i,c[i]+"",buildAttributeValue());
						type=ATTVALUE;
					}else{
						doc.insertString(i,c[i]+"",buildAttributeName());
					}
				}else if(type==ATTVALUE){
					if(c[i]=='"'){
						type = ATTNAME;
					}
					doc.insertString(i,c[i]+"",buildAttributeValue());
				}
				if(bar!=null){
					bar.setString("Rendering "+((int)(i/text.length()))+"%");
					bar.setValue(i);
					
				}
			}
			if(bar!=null) bar.setVisible(false);
			paintAll(getGraphics());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private AttributeSet buildBasic(){
	    SimpleAttributeSet attrs = new SimpleAttributeSet();
		StyleConstants.setFontFamily(attrs, "Courier New");
		StyleConstants.setFontSize(attrs, 12);
		return attrs;
	}
	private AttributeSet buildElement(){		
		SimpleAttributeSet attrs = (SimpleAttributeSet)buildBasic();
		StyleConstants.setForeground(attrs,new Color(0,128,128));
		return attrs;
	}
	private AttributeSet buildText(){		
		SimpleAttributeSet attrs = (SimpleAttributeSet)buildBasic();
		StyleConstants.setForeground(attrs,Color.black);
		return attrs;
	}
	private AttributeSet buildComment(){		
		SimpleAttributeSet attrs = (SimpleAttributeSet)buildBasic();
		StyleConstants.setForeground(attrs,new Color(63,95,191));
		return attrs;
	}
	private AttributeSet buildAttributeName(){		
		SimpleAttributeSet attrs = (SimpleAttributeSet)buildBasic();
		StyleConstants.setForeground(attrs,new Color(127,0,127));
		return attrs;
	}
	private AttributeSet buildAttributeValue(){		
		SimpleAttributeSet attrs = (SimpleAttributeSet)buildBasic();
		StyleConstants.setForeground(attrs,new Color(42,0,255));
		return attrs;
	}
	public static void main(String a[]){
		JFrame frame = new JFrame();
		XMLTextPane pane = new XMLTextPane();
		frame.getContentPane().add(pane);
		frame.setSize(400,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		pane.setText("<test att=\"tes\"><!-- comment -->aaaa<test>");
	}
	public void sss(){
		StyledDocument doc = getStyledDocument();
		
	}
}

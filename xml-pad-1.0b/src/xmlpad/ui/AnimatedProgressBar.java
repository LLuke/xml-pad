/*
 * Created on 12/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.ui;

import javax.swing.JProgressBar;

/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AnimatedProgressBar extends JProgressBar implements Runnable{
	private boolean running = false;
	public AnimatedProgressBar(){
		super();
		setMinimum(0);
		setMaximum(9);
		setValue(0);
		setVisible(isVisible());
	}
	public void setVisible(boolean bool){
		super.setVisible(bool);
		running = bool;
		if(bool){
			Thread t = new Thread(this);
			t.start();
		}
	}
	public void run(){
		int v = getValue();
		while(running){
			try{
				v++;
				if(v==10) v=0;
				setValue(v);
				Thread.sleep(250);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}

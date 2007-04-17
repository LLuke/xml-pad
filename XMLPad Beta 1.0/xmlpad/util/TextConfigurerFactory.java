/*
 * Created on 09/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xmlpad.util;

/**
 * @author xp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TextConfigurerFactory {
	public static final int TYPE_XML = 1;
	public static final int TYPE_HTML = 2;
	public static TextConfigurer createConfigurer(int i){
		TextConfigurer text = null;
		if(i==TYPE_XML) text = new PlainTextConfigurer();
		if(i==TYPE_HTML) text = new HTMLTextConfigurer();
		return text;
	}
}

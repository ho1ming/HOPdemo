package datafeed;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class LiveTwitterFeeder implements Runnable {
	
	int port = 9999;
	JTextPane tp;
	Socket s;
	ServerSocket ss;
	
	public LiveTwitterFeeder(JTextPane tp){
		
		this.tp = tp;
		try {
			ss = new ServerSocket(9999);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			s = ss.accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true){
				
				String line;
				
				line = br.readLine();
				
				//System.out.println("Twitter feed: get line: " + line);
				
				StyleContext sc = StyleContext.getDefaultStyleContext();
			    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
			        StyleConstants.Foreground, Color.yellow);

			    int len = tp.getDocument().getLength(); // same value as
			                       // getText().length();
			    tp.setCaretPosition(len); // place caret at the end (with no selection)
			    tp.setCharacterAttributes(aset, false);
			    tp.replaceSelection(line+"\n"); // there is no selection, so inserts at caret
				
				tp.validate();
				tp.setCaretPosition(tp.getText().length()-1);
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

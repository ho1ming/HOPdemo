package datafeed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextPane;

public class LiveTwitterFeeder implements Runnable {
	
	public final int LENGTH_LIMIT = 5000;
	
	int port = 5999;
	JTextPane tp;
	Socket s;
	ServerSocket ss;
	
	StringBuffer text;
	
	public LiveTwitterFeeder(JTextPane tp){
		text = new StringBuffer();
		this.tp = tp;
		try {
			ss = new ServerSocket(port);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			s = ss.accept();
			System.out.println(" accepted socket");
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true){
				
				String line;
				
				line = br.readLine();
				line = line.trim();
				line += "\n\n";
				//System.out.println("Twitter feed got line: " + line);
				
				text.insert(0, line);
				tp.setText(text.toString());
				
				if (text.length() > LENGTH_LIMIT){
					String sub = text.substring(0, LENGTH_LIMIT);
					text = new StringBuffer(sub);
				}
				/* 
				StyleContext sc = StyleContext.getDefaultStyleContext();
			    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
			        StyleConstants.Foreground, Color.yellow);

			    int len = tp.getDocument().getLength(); // same value as
			                       // getText().length();
			    tp.setCaretPosition(len); // place caret at the end (with no selection)
			    tp.setCharacterAttributes(aset, false);
			    tp.replaceSelection(line+"\n"); // there is no selection, so inserts at caret
				
				tp.validate();
				tp.setCaretPosition(Math.max(0, tp.getText().length()-1));
				*/
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

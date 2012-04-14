package datafeed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.PriorityQueue;

import processing.core.*; 

public class HopDataFeeder implements Runnable{

	String fileName = "";
	File inputFile;
	private PriorityQueue<TwitterWord> wordList = new PriorityQueue<TwitterWord>();
	
	public synchronized PriorityQueue<TwitterWord> getWordList(){
		return this.wordList;
	}

	public void run(){
		//PApplet pa = new PApplet();
		//InputStream is = pa.createInput( fileName );
		
		while(true){ //keep polling
			
			wordList = new PriorityQueue<TwitterWord>();
			
			inputFile = new File(fileName);

			if(inputFile.exists()){

				BufferedReader buf = null;
				try {
					buf = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				boolean eof = false;
				while( !eof ) {
					String line = null;
					try {
						line = buf.readLine();
					}
					catch( IOException ex ) {
						ex.printStackTrace();
						eof = true;
					}

					if ( line != null ) {
						line = line.trim();
						if ( line.length() != 2 ) //should be: text frequency (e.g. has 10)
							continue;
						String [] tokens = line.split("\\s+");
						String text = tokens[0];
						int frequency = Integer.parseInt(tokens[1]);
						TwitterWord newWord = new TwitterWord(text, frequency);
						wordList.add(newWord);

					}else {
						eof = true;
					}
				}

			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//end while
	}

}

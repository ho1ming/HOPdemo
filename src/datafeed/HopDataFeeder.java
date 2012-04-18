package datafeed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import wordookie.DynamicWordookie;
import chart.DynamicChart;

public class HopDataFeeder implements Runnable{

	static final String pathName = "/home/towlarn/hop/output/";
	static final String fileName = "part-";
	
	static final NumberFormat nf = new DecimalFormat("00000");
	
	private final List<List<TwitterWord>> wordlists = new ArrayList<List<TwitterWord>>();
	
	private int fileNum;
	private DynamicWordookie wordle;
	private DynamicChart chart;
	private int waitTime;
	
	public HopDataFeeder(DynamicWordookie wordle, DynamicChart chart){
		this(wordle, chart, 5000);
	}
	
	public HopDataFeeder(DynamicWordookie wordle, DynamicChart chart, int waitTime){
		this.wordle = wordle;
		this.chart = chart;
		this.waitTime = waitTime;
	}

	public List<List<TwitterWord>> getWordList(){
		return this.wordlists;
	}

	public void run(){
		//PApplet pa = new PApplet();
		//InputStream is = pa.createInput( fileName );
		fileNum = 0;
		
		while(true){ //keep polling
			
			final List<TwitterWord> words = new ArrayList<TwitterWord>();
			final String fnum = nf.format(fileNum);
			final File inputFile = new File(pathName+fileName+fnum);
			
			System.out.println("file:  "+inputFile.toString());
			if(inputFile.exists()){

				BufferedReader buf = null;
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(inputFile);
					buf = new BufferedReader(new InputStreamReader(fis));
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
						
						String [] tokens = line.split("\\s+");
						if ( tokens.length != 2 ) //should be: text frequency (e.g. has 10)
							continue;
						
						String text = tokens[0];
						int frequency = Integer.parseInt(tokens[1]);
						TwitterWord newWord = new TwitterWord(text, frequency);
						words.add(newWord);

					}else {
						eof = true;
					}
				}
				Collections.sort(words, Collections.reverseOrder());

				/* update datasets for wordle and chart */
				//System.out.println("update chart dataset");
				chart.updateDataset(words);
				//System.out.println("update wordle dataset");
				wordle.updateDataset(words);
				fileNum ++;
				
				if (buf != null){
					try { buf.close();} catch (IOException e) {e.printStackTrace();}
				}
			}
			
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} //end while
	}

}

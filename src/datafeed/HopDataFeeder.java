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
	
	public HopDataFeeder(DynamicWordookie wordle, DynamicChart chart) {
		this.wordle = wordle;
		this.chart = chart;
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
				chart.updateDataset(words);
				
				fileNum += 80;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} //end while
	}
	
	public static void main(String[] args){
		//HopDataFeeder hdf = new HopDataFeeder();
		//hdf.run();
	}

}

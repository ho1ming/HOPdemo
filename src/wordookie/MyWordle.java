package wordookie;
import processing.core.*; 
import processing.xml.*; 

import wordookie.*; 
import wordookie.util.ColorStuff; 
import wordookie.parsers.DumbParser; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

import datafeed.HopDataFeeder;
import datafeed.LiveTwitterFeeder;
import datafeed.TwitterWord;

public class MyWordle extends PApplet {


	final String INPUT_FILE = "/home/yatmingho/Desktop/sampletxt";
	final String FONT_NAME = "Serif";
	final int BACKGROUND = color( 0 );
	final int MAX_FONTSIZE = 100;
	final int MIN_FONTSIZE = 16;
	final int FADE = 2;
	final boolean TAKE_SNAPSHOTS = false;

	java.util.List stringList;
	Iterator itr;
	Map wordMap;
	Layout layout;
	PFont font;
	boolean looping = true;
	
	PriorityQueue<TwitterWord> realTimeWordList = new PriorityQueue<TwitterWord>();
	
	HopDataFeeder feeder;
	
	
	public MyWordle(HopDataFeeder feeder){
		
		this.feeder = feeder;
		
	}

	public void setup()
	{
		size( 666, 335 );
		frameRate( 15 );

		DumbParser parser = new DumbParser();
		parser.load( createInput( INPUT_FILE ) );
		stringList = parser.getWords();
		itr = stringList.iterator();

		/* Ivan */
		realTimeWordList = null; //feeder.getWordList();
		
		wordMap = new HashMap();

		font = createFont( FONT_NAME, 48 );
		layout = new Layout( this, BACKGROUND );
		//layout.setAngleType( Layout.ANYWAY );
		background( BACKGROUND );
		
		
		
	}

	public void draw()
	{
		if ( itr.hasNext() )
		{
			String token = (String)itr.next();
			token.trim();
			token = token.toLowerCase();
			ColoredWord word = (ColoredWord)wordMap.get( token );

			// redraw the whole thing except for word
			background( BACKGROUND );
			for( Iterator witr = wordMap.values().iterator(); witr.hasNext(); )
			{
				ColoredWord w = (ColoredWord)witr.next();
				if ( w != word )
				{
					fill( w.col, w.life );
					layout.paintWord( w );
				}
			}

			if ( word == null ) //new word
			{
				word = new ColoredWord( token, 1f );
				word.font = font;
				word.col = ColorStuff.BlueIce[ (int)random(ColorStuff.BlueIce.length) ];
				wordMap.put( token, word );
				word.fontSize = (int)min( word.weight + MIN_FONTSIZE, MAX_FONTSIZE );
				layout.doLayout( word );
			}
			else //repeating word, increase the size
			{
				// fit word in
				word.weight += 1;
				word.fontSize = (int)min( word.weight + MIN_FONTSIZE, MAX_FONTSIZE );
				if ( layout.intersects( word ) )
					layout.doLayout( word );
			}

			word.life = 255;
			fill( word.col, word.life );
			layout.paintWord( word );

			// decrease life
			for( Iterator witr = wordMap.values().iterator(); witr.hasNext(); )
			{
				ColoredWord w = (ColoredWord)witr.next();
				w.life -= FADE;
				if ( w.life < 0 ) w.life = 0;
			}

			if ( TAKE_SNAPSHOTS )
				snap();
		}
		else
		{
			boolean anyleft = false;
			background( BACKGROUND );
			for( Iterator witr = wordMap.values().iterator(); witr.hasNext(); )
			{
				ColoredWord w = (ColoredWord)witr.next();
				if ( w.life > 0 )
				{
					anyleft = true;
					fill( w.col, w.life );
					layout.paintWord( w );
					w.life -= FADE;
					if ( w.life < 0 ) w.life = 0;
				}
			}
			if ( !anyleft )
			{
				println( "Done." );
				noLoop();
			}
		}
	}

	public void snap()
	{
		saveFrame( "frames/frame-######.png" );
	}

	public void keyPressed()
	{
		switch( key )
		{
		case ' ':
			looping = !looping;
			break;
		}
	}





	class ColoredWord extends Word
	{
		public int col;
		public int life;

		public ColoredWord( String t, float w )
		{
			super( t, w );
		}
	}

}

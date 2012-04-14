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

import datafeed.TwitterWord;

public class DynamicWordookie extends PApplet {

	/*
    Copyright 2009 Michael Ogawa

 This file is part of Wordookie.

 Wordookie is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Wordookie is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Wordookie.  If not, see <http://www.gnu.org/licenses/>.
	 */





	final String INPUT_FILE = "/home/towlarn/hop/output/part-00010";
	final String FONT_NAME = "Serif";
	final int BACKGROUND = color( 0 );
	final int MAX_FONTSIZE = 100;
	final int MIN_FONTSIZE = 16;
	final int FADE = 2;
	final boolean TAKE_SNAPSHOTS = false;

	Iterator itr;
	Map wordMap;
	Layout layout;
	PFont font;
	boolean looping = true;

	public void setup()
	{
		size( 666, 335 );
		frameRate( 15 );

		itr = null;

		wordMap = new HashMap();

		font = createFont( FONT_NAME, 48 );
		layout = new Layout( this, BACKGROUND );
		//layout.setAngleType( Layout.ANYWAY );
		background( BACKGROUND );
	}
	
	public synchronized void updateDataset(List<TwitterWord> words) {
		itr = words.iterator();
	}

	public synchronized void draw()
	{
		System.out.println("drawing");
		if (itr == null) return;
		
		if ( itr.hasNext() )
		{
			TwitterWord tword = (TwitterWord) itr.next();
			
			String token = tword.getText();
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

			if ( word == null )
			{
				word = new ColoredWord( token, 1f );
				word.font = font;
				word.col = ColorStuff.BlueIce[ (int)random(ColorStuff.BlueIce.length) ];
				wordMap.put( token, word );
				word.fontSize = (int)min( word.weight + MIN_FONTSIZE, MAX_FONTSIZE );
				layout.doLayout( word );
			}
			else
			{
				// fit word in
				word.weight += tword.getFrequency();
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
			/*
			if ( !anyleft )
			{
				println( "Done." );
				noLoop();
			}
			*/
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

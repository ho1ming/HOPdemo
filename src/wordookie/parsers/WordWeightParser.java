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

package wordookie.parsers;

import wordookie.*;
import processing.core.PApplet;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

/**
 * This parses the "advanced" text input of word:weight pairs.
 * An example input:
 * <p>
 * <code>
 * Tokyo:32450<br>
 * Seoul:20550<br>
 * Mexico City:20450<br>
 * New York:19750<br>
 * Mumbai:19200<br>
 * </code>
 */
public class WordWeightParser {

	protected ArrayList<Word> wordList;
	protected float minWeight = Float.MAX_VALUE;
	protected float maxWeight = Float.MIN_VALUE;

	/**
	 * Constructs a new WordWeightParser.
	 */
	public WordWeightParser() {
		wordList = new ArrayList<Word>();
	}

	/**
	 * Tells the WordWeightParser to process the InputStream.
	 */
	public void load( InputStream in ) {
		String [] lines = PApplet.loadStrings( in );
		for( int i = 0; i < lines.length; i++ ) {
			String line = lines[i];
			String [] tokens = line.split( ":" );
			String text = tokens[0];
			float weight = Float.parseFloat( tokens[1] );
			Word word = new Word( text, weight );
			wordList.add( word );

			// update min/max weights
			if ( weight < minWeight ) minWeight = weight;
			if ( weight > maxWeight ) maxWeight = weight;
		}
	}

	/**
	 * Returns an (unsorted) list of Words encountered with
	 * their weights set.
	 */
	public List<Word> getWords() {
		return wordList;
	}

	/**
	 * Returns the smallest weight encountered.
	 */
	public float getMinWeight() {
		return minWeight;
	}

	/**
	 * Returns the largest weight encountered.
	 */
	public float getMaxWeight() {
		return maxWeight;
	}
}


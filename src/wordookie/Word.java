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

package wordookie;

import processing.core.PFont;

/**
 * Words contain parameters needed by the Layout.
 * <p>
 * Extend this class if you want Words to have more associated attributes,
 * like color.
 *
 * @author Michael Ogawa
 *
 * @see Layout
 */
public class Word implements Comparable {

	/**
	 * The word (or phrase).
	 */
	public String text;

	/**
	 * Some measure of the word's importance.
	 */
	public float weight;

	protected float x,y;

	/**
	 * What font to use when drawing.
	 */
	public PFont font;

	/**
	 * Size of the font.
	 * Related to PApplet.fontSize().
	 * @see processing.core.PApplet#textSize(float)
	 */
	public int fontSize;

	/**
	 * The angle, in radians, at which to draw the Word.
	 * Zero angle is normal (horizontal).
	 */
	public float angle;

	/**
	 * Constructs a new Word with the given text.
	 */
	public Word( String text ) {
		this( text, 1 );
	}

	/**
	 * Constructs a new Word with the given text and weight.
	 */
	public Word( String text, float weight ) {
		this.text = text;
		this.weight = weight;
		this.font = null;
		this.fontSize = 0;
		this.angle = 0f;
	}

	/**
	 *  For times when display parameters are known,
	 *  constructs a new Word with the given text and font.
	 */
	public Word( String text, PFont font ) {
		this( text, font, 0 );
	}

	/**
	 *  For times when display parameters are known,
	 *  constructs a new Word with the given text, font and font size.
	 */
	public Word( String text, PFont font, int fontSize ) {
		this.text = text;
		this.font = font;
		this.fontSize = fontSize;
		this.angle = 0f;
	}

	/**
	 * Returns the word's text.
	 */
	public String toString() {
		return text;
	}

	/**
	 * Compares two Words' weight, the larger being ordered first.
	 * @see java.util.Collections#sort( List )
	 */
	public int compareTo( Object obj ) {
		Word w2 = (Word)obj;
		if ( w2.weight > this.weight )
			return 1;
		else if ( w2.weight < this.weight )
			return -1;
		else
			return 0;
	}
}


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

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PFont;
import processing.core.PConstants;
import java.awt.Point;
import java.awt.Dimension;
import java.util.Random;

/**
 * The Wordookie layout engine.
 * <p>
 * TODO: give a short example of Layout being used.
 * <p>
 * Credit goes to Jonathan Feinberg for developing the original Wordle layout.
 * He is not affiliated with Wordookie.
 *
 * @author Michael Ogawa
 */
public class Layout implements PConstants {

	/**
	 * Words are angled "any which way."
	 */
	public static final int ANYWAY = 1;

	/**
	 * All words are angled horizontally.
	 */
	public static final int HORIZONTAL = 2;

	/**
	 * Most words are angled horizontally; the rest are vertical.
	 */
	public static final int MOSTLY_HORIZONTAL = 3;

	/**
	 * Half the words are angled horizontally 
	 * and the other half vertically.
	 * This is the default.
	 */
	public static final int HALF_AND_HALF = 4;

	/**
	 * Most of the words are angled vertically; the rest are horizontal.
	 */
	public static final int MOSTLY_VERTICAL = 5;

	/**
	 * All words are angled vertically.
	 */
	public static final int VERTICAL = 6;

	/**
	 * The parent PApplet. Set in the constructor.
	 */
	protected PApplet applet;

	/**
	 * The color that Wordookie is allowed to draw text in.
	 * All other colors are off limits.
	 * <p>
	 * By filling in areas with colors not equal to backgroundColor,
	 * you can restrict the text drawing area to an arbitrary shape.
	 */
	protected int backgroundColor;

	/**
	 * The allowed angles at which Layout can draw words.
	 */
	protected int angleType;

	private Random rand;

	/**
	 * Used with the spiralling algorithm.
	 */
	private Point centerPoint;

	/**
	 * Constructs a new Layout.
	 */
	public Layout( PApplet app ) {
		this( app, app.color(0) );
	}

	/**
	 * Constructs a new Layout with a backgroundColor.
	 */
	public Layout( PApplet app, int bkgrnd ) {
		this.applet = app;
		this.backgroundColor = bkgrnd;
		this.angleType = HALF_AND_HALF;
		this.rand = new Random();
	}

	/**
	 * Sets the background color in which Layout may draw.
	 */
	public void setBackgroundColor( int bkgrnd ) {
		backgroundColor = bkgrnd;
	}

	/**
	 * Returns the current background color.
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Sets the allowed word angles.
	 * @see #ANYWAY
	 * @see #HORIZONTAL
	 * @see #MOSTLY_HORIZONTAL
	 * @see #HALF_AND_HALF
	 * @see #MOSTLY_VERTICAL
	 * @see #VERTICAL
	 */
	public void setAngleType( int angleType ) {
		this.angleType = angleType;
	}

	/**
	 * Main entry to the layout engine.
	 * User passes in a Word and Layout places it somewhere nice.
	 */
	public void doLayout( Word word ) {
		if ( word.font == null )
			return; // TODO: throw missing font exception
		applet.textFont( word.font );
		if ( word.fontSize > 0 )
			applet.textSize( word.fontSize );
		applet.textAlign( CENTER, CENTER );

		makeInitialPosition( word );

		centerPoint = new Point( (int)word.x, (int)word.y );
		int r = 1;
		int i = 0;
		
		while( intersects( word ) && i < 100 ) {
			//updatePosition( word, r++ );

			// makeInitialPosition() is faster
			i++;
			makeInitialPosition( word );
		}
	}

	/**
	 * Choose a random point on screen based on a distribution.
	 * Right now the Gaussian distribution is used.
	 * Sets the Word's x and y position when a valid one is found.
	 */
	private void makeInitialPosition( Word word ) {
		// generate angle based on angleType
		word.angle = generateAngle();

		// get width & height of word
		Dimension dim = wordHalfDimensions( word );

		int iter = 0;
		float x, y;
		do {
			x = (float)rand.nextGaussian() * applet.width/8 + applet.width/2;
			y = (float)rand.nextGaussian() * applet.height/8 + applet.height/2;
			iter++;
			if (iter > 1000) break;
		}
		while( x > applet.width - dim.width || x < dim.width
		       || y < dim.height || y > applet.height - dim.height );

		word.x = (float)x;
		word.y = (float)y;
	}

	/**
	 * Randomly "spirals" the Word position from a central point.
	 * This is called for in the original Wordle algorithm,
	 * but calling makeInitialPosition() instead is sufficient.
	 */
	private void updatePosition( Word word, int radius ) {
		// randomly spiral out
		double theta = Math.random() * PApplet.TWO_PI;
		int x = (int)( radius * Math.cos( theta ) );
		int y = (int)( radius * Math.sin( theta ) );
		word.x = centerPoint.x + x;
		word.y = centerPoint.y + y;

		// TODO: check bounds
	}

	/**
	 * Tests whether the Word will cover existing drawings.
	 * Will return false if the Word is drawn completely in an
	 * area colored with backgroundColor.
	 */
	public boolean intersects( Word word ) {
		// create new word buffer
		PGraphics newWordBuffer = applet.createGraphics( applet.width, applet.height, JAVA2D );
		drawBackground( newWordBuffer );
		newWordBuffer.textAlign( CENTER, CENTER );
		newWordBuffer.fill( 255 );
		paintWord( word, newWordBuffer );

		// test each pixel
		// optimize by searching within new word's bounding box
		int xStart, xEnd, yStart, yEnd;
		int halfTextWidth = (int)(applet.textWidth( word.toString() ) / 2);
		int halfTextHeight = (int)applet.textAscent();
		
		Dimension dim = wordHalfDimensions( word );

		xStart = (int)( word.x - dim.width );
		xEnd = (int)( word.x + dim.width );
		yStart = (int)( word.y - dim.height );
		yEnd = (int)( word.y + dim.height );

		// sanitize bounds
		yStart = applet.max( yStart, 0 );
		xStart = applet.max( xStart, 0 );
		yEnd = applet.min( yEnd, applet.height );
		xEnd = applet.min( xEnd, applet.width );

		// test each pixel for overlap
		// note: using pixels[] is faster than get()
		applet.loadPixels();
		newWordBuffer.loadPixels();
		for( int y = yStart; y < yEnd; y++ ) {
			for( int x = xStart; x < xEnd; x++ ) {
				int c1 = applet.pixels[ y * applet.width + x ];
				int c2 = newWordBuffer.pixels[ y * newWordBuffer.width + x ];
				if ( c1 != backgroundColor && c2 != backgroundColor )
					return true;	// short circuit
			}
		}
		return false;	// no intersection found
	}

	/**
	 * Draws the Word in the parent PApplet.
	 * Should be called after doLayout().
	 */
	public void paintWord( Word word ) {
		paintWord( word, applet.g );

		/*
		// draw bounding box for debugging
		Dimension dim = wordHalfDimensions( word );
		applet.stroke( 255 );
		applet.noFill();
		applet.rectMode( RADIUS );
		applet.rect( word.x, word.y, dim.width, dim.height );
		*/
	}

	/**
	 * Advanced method to draw a Word in a PGraphics context.
	 * Most users should use paintWord( Word ) instead.
	 */
	public void paintWord( Word word, PGraphics graphics ) {
		graphics.beginDraw();
		graphics.pushMatrix();
		graphics.translate( word.x, word.y );
		graphics.rotate( word.angle );
		graphics.textFont( word.font );
		if ( word.fontSize > 0 )
			graphics.textSize( word.fontSize );
		graphics.textAlign( CENTER, CENTER );
		graphics.text( word.toString(), 0, 0 );
		graphics.popMatrix();
		graphics.endDraw();
	}

	private void drawBackground( PGraphics buffer ) {
		buffer.beginDraw();
		buffer.background( 0 );
		buffer.endDraw();
	}

	/**
	 * Generates a word angle depending on the current angleType.
	 */
	private float generateAngle() {
		switch ( angleType ) {
			case ANYWAY:
				return (float)( Math.random() * PI - HALF_PI );
			case HORIZONTAL:
				return 0f;
			case MOSTLY_HORIZONTAL:
				if ( Math.random() < 0.75 )
					return 0f;
				else
					return -HALF_PI;
			case HALF_AND_HALF:
				if ( Math.random() < 0.5 )
					return 0f;
				else
					return -HALF_PI;
			case MOSTLY_VERTICAL:
				if ( Math.random() < 0.25 )
					return 0f;
				else
					return -HALF_PI;
			case VERTICAL:
				return -HALF_PI;
		}
		return 0f;
	}

	/**
	 *  Returns half the width and half the height of the Word.
	 */
	private Dimension wordHalfDimensions( Word word ) {
		float wWidth = applet.textWidth( word.toString() ) / 2 + 1;
		float wHeight = applet.textAscent();

		// tilted word dimensions
		float aAngle = word.angle;
		float bAngle = word.angle > HALF_PI ? word.angle - HALF_PI : HALF_PI - word.angle;
		float cosA = PApplet.abs( PApplet.cos( aAngle ) );
		float cosB = PApplet.abs( PApplet.cos( bAngle ) );
		float sinA = PApplet.abs( PApplet.sin( aAngle ) );
		float sinB = PApplet.abs( PApplet.sin( bAngle ) );
		float e = wWidth * cosA;
		float f = wHeight * cosB;
		float h = wWidth * sinA;
		float g = wHeight * sinB;
		float aWidth = e + f;
		float aHeight = h + g;
		return new Dimension( (int)aWidth, (int)aHeight );
	}
}


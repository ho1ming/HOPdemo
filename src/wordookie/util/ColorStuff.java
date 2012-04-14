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

package wordookie.util;

import processing.core.*;

/**
 * Contains predefined color combinations.
 * <p>
 * A typical usage example which picks a random color:
 * <p>
 * <code>
 * int myColor = ColorStuff.BlueIce[ (int)random(ColorStuff.BlueIce.length) ];
 * </code>
 */
public abstract class ColorStuff {
	/**
	 * A white-to-blue theme.
	 */
	public static final int [] BlueIce = { 0xfff1eef6, 0xffbdc9e1, 0xff74a9cf, 0xff2b8cbe, 0xff045a8d };

	/**
	 * Reds and oranges.
	 */
	public static final int [] Autumn = { 0xfffef0d9, 0xfffdcc8a, 0xfffc8d59, 0xffe34a33, 0xffb30000 };

	/**
	 * Pastel colors.
	 */
	public static final int [] EasterEgg = { 0xfffbb4ae, 0xffb3cde3, 0xffccebc5, 0xffdecbe4, 0xfffed9a6 };
}

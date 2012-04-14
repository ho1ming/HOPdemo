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
import java.util.List;
import java.util.LinkedList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * This parser collects words (tokens) from an InputStream without regard to
 * repetition. This is useful for sequential and dynamic wordles.
 */
public class DumbParser {

	private LinkedList<String> wordList;

	public DumbParser() {
		wordList = new LinkedList<String>();
	}

	public void load( InputStream in ) {
		InputStreamReader isr = new InputStreamReader( in );
		BufferedReader buf = new BufferedReader( isr );

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
				if ( line.length() == 0 )
					continue;
				String [] tokens = line.split( " " );
				for( int i = 0; i < tokens.length; i++ ) {
					wordList.add( tokens[i] );
				}
			}
			else {
				eof = true;
			}
		}
	}

	public List<String> getWords() {
		return wordList;
	}
}


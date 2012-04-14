package chart;


import processing.core.*; 
import processing.xml.*; 

import wordookie.*; 
import wordookie.util.ColorStuff; 
import wordookie.parsers.DumbParser; 

import java.applet.*; 
import java.awt.Color;
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import java.util.Random;

public class DynamicChart extends PApplet {


	final int BACKGROUND = color( 0 );
	final boolean TAKE_SNAPSHOTS = false;

	java.util.List stringList;
	Iterator itr;
	Map wordMap;
	Layout layout;
	PFont font;
	boolean looping = true;
	PImage chartImage;
	int width = 666;
	int length = 260;
	
	int intput1 = 0;
	int intput2 = 0;
	int intput3 = 0;
	int intput4 = 0;
	int intput5 = 0;
	int intput6 = 0;
	int intput7 = 0;
	int intput8 = 0;
	int intput9 = 0;
	int intput0 = 0;
	
	Random generator = new Random();

	public void setup()
	{
		size(width, length);
		frameRate( 15 );

		layout = new Layout( this, BACKGROUND );
		//layout.setAngleType( Layout.ANYWAY );
		background( BACKGROUND );
		generateChart();
	}

	public void draw()
	{
		generateChart();
		image(chartImage, 1, 1);
	}
	
	public void generateChart(){
		

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			
			intput1 = generator.nextInt(50);
			intput2 = generator.nextInt(50);
			intput3 = generator.nextInt(50);
			intput4 = generator.nextInt(50);
			intput5 = generator.nextInt(50);
			intput6 = generator.nextInt(50);
			intput7 = generator.nextInt(50);
			intput8 = generator.nextInt(50);
			intput9 = generator.nextInt(50);
			intput0 = generator.nextInt(50);
			
			

			final String series1 = "s1";
			final String series2 = "s2";
			final String series3 = "s3";
			final String series4 = "s4";
			final String series5 = "s5";
			final String series6 = "s6";
			final String series7 = "s7";
			final String series8 = "s8";
			final String series9 = "s9";
			final String series0 = "s0";

			// column keys...
			final String category1 = "Category 1";
			final String category2 = "Category 2";
			final String category3 = "Category 3";
			final String category4 = "Category 4";
			final String category5 = "Category 5";
			final String category6 = "Category 6";
			final String category7 = "Category 7";
			final String category8 = "Category 8";
			final String category9 = "Category 9";
			final String category10 = "Category 10";

			dataset.addValue(intput1, series1, category1);
			dataset.addValue(intput2, series2, category2);
			dataset.addValue(intput3, series3, category3);
			dataset.addValue(intput4, series4, category4);
			dataset.addValue(intput5, series5, category5);
			dataset.addValue(intput6, series6, category6);
			dataset.addValue(intput7, series7, category7);
			dataset.addValue(intput8, series8, category8);
			dataset.addValue(intput9, series9, category9);
			dataset.addValue(intput0, series0, category10);

			JFreeChart chart = ChartFactory.createBarChart3D("Live Twitter Feed Statistics", "top 10 words", "frequency", dataset, PlotOrientation.VERTICAL, false, true, true);
			int colorCode = Integer.parseInt("CCCCCC", 16);
			chart.setBackgroundPaint(new Color(colorCode));
			chartImage = new PImage(chart.createBufferedImage(width, length));
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

}

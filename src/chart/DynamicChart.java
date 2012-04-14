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

import datafeed.HopDataFeeder;
import datafeed.TwitterWord;

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


	int topK = 10;

	private DefaultCategoryDataset dataset;
	private final String[] series;
	private final String[] categories;
	private final double[] input_values;

	public DynamicChart(){
		series = new String[topK];
		categories = new String[topK];
		input_values = new double[topK];
		clearDataset();

		// dont change series names
		for (int i = 0; i < series.length; i++){
			series[i] = "s"+i;
		}
	}

	public void clearDataset(){
		for (int i = 0; i < categories.length; i++){
			categories[i] = "";
		}
		for (int i = 0; i < input_values.length; i++){
			input_values[i] = 0.0;
		}
		dataset = new DefaultCategoryDataset();
	}

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

	public void updateDataset(List<TwitterWord> sorted_words){
		clearDataset();
		int numwords = Math.min(topK, sorted_words.size());
		
		for (int i = 0; i < numwords; i++){
			categories[i] = sorted_words.get(i).getText();
		}
		for (int i = 0; i < numwords; i++){
			input_values[i] = sorted_words.get(i).getFrequency();
		}
	}

	public void generateChart(){
		// add values to chart dataset
		for (int i = 0; i < topK; i++){
			dataset.addValue(input_values[i], series[i], categories[i]);
		}

		JFreeChart chart = ChartFactory.createBarChart3D("Live Twitter Feed Statistics", "top "+topK+" words", "frequency", dataset, PlotOrientation.VERTICAL, false, true, true);
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

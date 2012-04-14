/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ContactEditorUI.java
 *
 * Created on Apr 12, 2012, 7:43:21 PM
 */

package demogui;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import chart.DynamicChart;
import datafeed.LiveTwitterFeeder;

import wordookie.DynamicWordookie;

/**
 *
 * @author Hadoop
 */
public class MyDemo extends javax.swing.JFrame {

	private final int FONT_SIZE = 20;
	private final int NUMBER_OF_REPEATS = 3;
	private final String TEXT = new String("0123456789/*-+/<>?;:[]~!@#$%^&*()+=abcdefghijklmnopqrstuvwxyz");
	private Random random = new Random();
	private JLabel label[] = new JLabel[NUMBER_OF_REPEATS];

	// Variables declaration - do not modify
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel chartPanel;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextPane jTextPane1;
	private javax.swing.JPanel wordlePanel;
	// End of variables declaration

	//an array of String objects
	private static String strings[] = {
		"Alpha", "Composite", "#Src", "@SrcOver",
		"SrcIn", "SrcOut", "Cl34ear", "DstOver", "235234"};

	private DynamicWordookie wordle;
	private DynamicChart chart;

	/** Creates new form ContactEditorUI */
	public MyDemo() {
		initComponents();
		initProgressing();
	}

	public void initProgressing(){
		wordle = new DynamicWordookie();
		wordlePanel.add(wordle);
		wordle.init();

		chart = new DynamicChart();
		chartPanel.add(chart);
		chart.init();
		
		LiveTwitterFeeder twitterFeed = new LiveTwitterFeeder(jTextPane1);
		Thread twitterFeedThread = new Thread(twitterFeed);
		twitterFeedThread.start();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextPane1 = new javax.swing.JTextPane();
		wordlePanel = new javax.swing.JPanel();
		chartPanel = new javax.swing.JPanel();
		
		this.setTitle("Real Time MapReduce: Twitter Feed Count Demo");
		jTextPane1.setBackground(Color.BLACK);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Live Twitter Feed", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 12))); // NOI18N

		jScrollPane1.setViewportView(jTextPane1);

		org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
				);
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
				);

		wordlePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Twitter Wordle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 12))); // NOI18N

		org.jdesktop.layout.GroupLayout wordlePanelLayout = new org.jdesktop.layout.GroupLayout(wordlePanel);
		wordlePanel.setLayout(wordlePanelLayout);
		wordlePanelLayout.setHorizontalGroup(
				wordlePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(0, 654, Short.MAX_VALUE)
				);
		wordlePanelLayout.setVerticalGroup(
				wordlePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(0, 308, Short.MAX_VALUE)
				);

		chartPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Live Twitter Word Count ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 12))); // NOI18N

		org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(chartPanel);
		chartPanel.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(
				jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(0, 654, Short.MAX_VALUE)
				);
		jPanel3Layout.setVerticalGroup(
				jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(0, 233, Short.MAX_VALUE)
				);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout.createSequentialGroup()
						.add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(chartPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.add(wordlePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.add(layout.createSequentialGroup()
						.add(wordlePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(chartPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		pack();
	}// </editor-fold>

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		MyDemo ui = new MyDemo();
		ui.setVisible(true);
	}
}

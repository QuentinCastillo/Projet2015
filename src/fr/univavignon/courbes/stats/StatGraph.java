package fr.univavignon.courbes.stats;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

import fr.univavignon.courbes.common.Profile;
import fr.univavignon.courbes.network.central.DatabaseCommunication; 

public class StatGraph extends ApplicationFrame{
	
	/**
	 * Constructor
	 * @param applicationTitle
	 * @param chartTitle
	 * @param stat 
	 */
	public StatGraph( String applicationTitle, String chartTitle, String stat, Profile player1, Profile player2) 
	   {
	      super(applicationTitle);
	      JFreeChart xylineChart = ChartFactory.createXYLineChart(
	         chartTitle ,
	         "Time" ,
	         stat ,
	         createDataset(player1, player2, stat) ,
	         PlotOrientation.VERTICAL ,
	         true , true , false);
	         
	      ChartPanel chartPanel = new ChartPanel( xylineChart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      final XYPlot plot = xylineChart.getXYPlot( );
	      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
	      renderer.setSeriesPaint( 0 , Color.RED );
	      renderer.setSeriesPaint( 1 , Color.GREEN );
	      renderer.setSeriesPaint( 2 , Color.YELLOW );
	      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
	      renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
	      renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
	      plot.setRenderer( renderer ); 
	      setContentPane( chartPanel ); 
	   }

	/**
	 * @param player2 
	 * @param player1 
	 * @return 
	 */
	private XYDataset createDataset(Profile player1, Profile player2, String stat) {
		//ResulSet result = DatabaseCommunication.getStat(stat, player1);
		
		return null;
		
	}
}

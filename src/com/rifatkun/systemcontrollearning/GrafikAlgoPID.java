package com.rifatkun.systemcontrollearning;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.rifatkun.systemcontrollearning.R.color;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;


public class GrafikAlgoPID {

	private GraphicalView view;
	
	private TimeSeries dataset = new TimeSeries("Algoritma PID");
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	
	private XYSeriesRenderer renderer = new XYSeriesRenderer();
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	
	private double tempXmax;
	private double Xmax;
	private double Xmin;
	
	public GrafikAlgoPID()
	{
		Xmax = 60;
		Xmin = 0;
		mDataset.addSeries(dataset);
		
		renderer.setColor(Color.RED);
		renderer.setPointStyle(PointStyle.POINT);
		renderer.setFillPoints(true);
		
		mRenderer.setZoomButtonsVisible(false);
		mRenderer.setZoomEnabled(false, false);
		mRenderer.setXTitle("Waktu(ms)");
		mRenderer.setYTitle("Sudut(rad)");
		mRenderer.setPanEnabled(true, false);
		//mRenderer.setPanLimits()
		mRenderer.setZoomRate(1);
		mRenderer.setYAxisMax(0.35);
		mRenderer.setYAxisMin(-0.35);
		mRenderer.setXAxisMin(Xmin);
		mRenderer.setXAxisMax(Xmax);
		
		mRenderer.setFitLegend(true); 
		mRenderer.setXLabels(10);
	    mRenderer.setYLabels(10);
	    mRenderer.setYLabelsPadding(2);
	    mRenderer.setAxisTitleTextSize(15);
	    mRenderer.setLabelsTextSize(12);
	    mRenderer.setShowGrid(true);
	    mRenderer.setYLabelsAlign(Align.RIGHT);
		
	    mRenderer.setBackgroundColor(color.bgTab);

		mRenderer.addSeriesRenderer(renderer);
	}
	
	public GraphicalView getView(Context context)
	{
		view = ChartFactory.getLineChartView(context, mDataset, mRenderer);
		return view;
	}
	
	public void addViewPoints(Point p)
	{
		dataset.add(p.getX(), p.getY());
		//scroll auto
		tempXmax = Xmax-(Xmax*(50/100));
		if(p.getX()>=tempXmax)
		{
			Xmax = Xmax+2;
			Xmin = Xmin+2;
			mRenderer.setXAxisMin(Xmin);
			mRenderer.setXAxisMax(Xmax);
		}
	}
	
	
}

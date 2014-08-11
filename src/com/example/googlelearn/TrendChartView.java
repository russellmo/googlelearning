package com.example.googlelearn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

public class TrendChartView extends View {

	private Paint mLinePaint;
	private Paint mTextPaint;
	private Paint mBgPaint ;
	
	
	public TrendChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);  //setPathEffect起作用，这个需要大量测试，有兼容问题
		}
	}
	
	private void init() {
		PathEffect effect = new DashPathEffect(new float[]{5,5,5,5}, 1);
		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mLinePaint.setColor(Color.BLACK);
		mLinePaint.setStrokeWidth(1.0f);
		mLinePaint.setAntiAlias(true);
		mLinePaint.setPathEffect(effect);

		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(Color.BLACK);
		mTextPaint.setStrokeWidth(4.0f);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(20.0f);

		mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBgPaint.setColor(Color.RED);
		
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int height = getHeight();
		int width = getWidth();
		
		canvas.drawLine(0, height/2.0f, width, height/2.0f, mLinePaint);
		
	}
}
